package com.blackHook.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.compress.utils.IOUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry
import static org.objectweb.asm.ClassReader.EXPAND_FRAMES

class BlackHookPlugin extends Transform implements Plugin<Project> {

    Project project

    BlackHook blackHook

    @Override
    void apply(Project target) {
        println("注册了")
        project = target
        target.extensions.getByType(BaseExtension).registerTransform(this)
        target.extensions.create("blackHook", BlackHook.class)
    }

    @Override
    String getName() {
        return "BlackHookPlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        if (BlackHook.CONTENT_CLASS == project.extensions.blackHook.inputTypes) {
            return TransformManager.CONTENT_CLASS
        } else if (BlackHook.CONTENT_JARS == project.extensions.blackHook.inputTypes) {
            return TransformManager.CONTENT_JARS
        } else if (BlackHook.CONTENT_RESOURCES == project.extensions.blackHook.inputTypes) {
            return TransformManager.CONTENT_RESOURCES
        }
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        if (BlackHook.SCOPE_FULL_PROJECT == project.extensions.blackHook.scopes) {
            return TransformManager.SCOPE_FULL_PROJECT
        } else if (BlackHook.PROJECT_ONLY == project.extensions.blackHook.scopes) {
            return TransformManager.PROJECT_ONLY
        }
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return project.extensions.blackHook.isIncremental
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        Collection<TransformInput> inputs = transformInvocation.inputs
        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        if (outputProvider != null) {
            outputProvider.deleteAll()
        }
        if (blackHook == null) {
            blackHook = new BlackHook()
            blackHook.methodHooker = project.extensions.blackHook.methodHooker

            for (int i = 0; i < project.extensions.blackHook.hookMethodList.size(); i++) {
                HookMethod hookMethod = new HookMethod()
                hookMethod.className = project.extensions.blackHook.hookMethodList.get(i).className
                hookMethod.methodName = project.extensions.blackHook.hookMethodList.get(i).methodName
                hookMethod.descriptor = project.extensions.blackHook.hookMethodList.get(i).descriptor
                hookMethod.createBytecode = project.extensions.blackHook.hookMethodList.get(i).createBytecode
                blackHook.hookMethodList.add(hookMethod)
            }

//            blackHook.hookMethodList = project.extensions.blackHook.hookMethodList
        }
        inputs.each { input ->
            input.directoryInputs.each { directoryInput ->
                handleDirectoryInput(directoryInput, outputProvider)

            }

            //遍历jarInputs
            input.jarInputs.each { JarInput jarInput ->
                //处理jarInputs
                handleJarInputs(jarInput, outputProvider)
            }
        }
//        println "cost time ${(System.currentTimeMillis() - startTime) / 1000}"
        //       println("====>project.extensions.black.methodHooker"+project.extensions.blackHook.methodHooker)
        super.transform(transformInvocation)

    }

    void handleDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        if (directoryInput.file.isDirectory()) {
            directoryInput.file.eachFileRecurse { file ->
                String name = file.name
//                println("====>file name is1 $name")
                if (name.endsWith(".class") && !name.startsWith("R\$drawable")
                        && !"R.class".equals(name) && !"BuildConfig.class".equals(name)) {
//                    println '----------- deal with "class" file <' + name + '> -----------'
                    ClassReader classReader = new ClassReader(file.bytes)
                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                    ClassVisitor classVisitor = new AllClassVisitor(classWriter, blackHook)
                    classReader.accept(classVisitor, EXPAND_FRAMES)
                    byte[] code = classWriter.toByteArray()
                    FileOutputStream fos = new FileOutputStream(
                            file.parentFile.absolutePath + File.separator + name)
                    fos.write(code)
                    fos.close()
                }
            }
        }

        //处理完输入文件之后，要把输出给下一个任务
        def dest = outputProvider.getContentLocation(directoryInput.name,
                directoryInput.contentTypes, directoryInput.scopes,
                Format.DIRECTORY)
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

    void handleJarInputs(JarInput jarInput, TransformOutputProvider outputProvider) {
//        println("jar")
        if (jarInput.file.getAbsolutePath().endsWith(".jar")) {
            //重名名输出文件,因为可能同名,会覆盖
            def jarName = jarInput.name

            def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
            if (jarName.endsWith(".jar")) {
                jarName = jarName.substring(0, jarName.length() - 4)
            }
            JarFile jarFile = new JarFile(jarInput.file)
            Enumeration enumeration = jarFile.entries()
            File tmpFile = new File(jarInput.file.getParent() + File.separator + "classes_temp.jar")
            //避免上次的缓存被重复插入
            if (tmpFile.exists()) {
                tmpFile.delete()
            }
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tmpFile))
            //用于保存
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()
                ZipEntry zipEntry = new ZipEntry(entryName)
                InputStream inputStream = jarFile.getInputStream(jarEntry)
                //插桩class
//                println( "jarname:$entryName")
//                println("====>file name is2 $entryName")
                if (entryName.endsWith(".class") && !entryName.startsWith("R\$")
                        && !"R.class".equals(entryName) && !"BuildConfig.class".equals(entryName)) {
                    //class文件处理
//                    println '----------- deal with "jar" class file <' + entryName + '> -----------'
                    jarOutputStream.putNextEntry(zipEntry)
                    ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream))
                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                    ClassVisitor cv = new AllClassVisitor(classWriter, blackHook)
                    classReader.accept(cv, EXPAND_FRAMES)
                    byte[] code = classWriter.toByteArray()
                    jarOutputStream.write(code)
                } else {
                    jarOutputStream.putNextEntry(zipEntry)
                    jarOutputStream.write(IOUtils.toByteArray(inputStream))
                }
                jarOutputStream.closeEntry()
            }
            //结束
            jarOutputStream.close()
            jarFile.close()
            def dest = outputProvider.getContentLocation(jarName + md5Name,
                    jarInput.contentTypes, jarInput.scopes, Format.JAR)
            FileUtils.copyFile(tmpFile, dest)
            tmpFile.delete()
        }
    }
}


