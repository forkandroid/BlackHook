package com.blackHook.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import static groovyjarjarasm.asm.Opcodes.ASM6;


public class AllClassVisitor extends ClassVisitor {
    private String className;
    private BlackHook blackHook;

    public AllClassVisitor(ClassVisitor classVisitor, BlackHook blackHook) {
        super(ASM6, classVisitor);
        this.blackHook = blackHook;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        return new AllMethodVisitor(blackHook, mv, access, name, descriptor, className);
    }
}
