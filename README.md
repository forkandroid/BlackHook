 # blackHook

   简介\
         BlackHook 是一个编译时插桩插件，基于ASM+Tranfrom实现，理论上可以hook任意一个java方法或者kotlin方法，只要代码对应的字节码可以在编译阶段被Tranfrom扫描到，
         就可以使用ASM在代码对应的字节码处插入特定字节码，从而hook该方法
   优点\
         1. 用DSL(领域特定语言)使用该插件，使用简单，配置灵活，而且插入的字节码可以使用ASM Bytecode Viewer Support Kotlin 插件自动生成，上手难度低\
         2. 理论上可以hook任意一个java方法，只要代码对应的字节码可以在编译阶段被Tranfrom扫描到\
         3. 基于ASM+Tranfrom实现，在编译阶段直接修改字节码，效率高，兼容性问题少

   使用\
         1. 在工程根目录下的gradle添加如下代码

 ```

         buildscript {
             dependencies {
               classpath 'com.csp.blackHook:buildSrc:1.0.0'
             }
         }

 ```



         2. 在app module下的build.gradle添加如下代码，hookMethodList是需要hook的方法的集合，每次hook一个新的方法的时候，新建一个HookMethod对象，\
         传入hook的类的路径，方法名，方法签名，插入字节码的闭包即可，如下代码所示，hook startService()方法和startForegroundService()方法



 ```
         apply plugin: 'com.csp.blackHook.killer'

         List<HookMethod> getHookMethods() {
           List<HookMethod> hookMethodList = new ArrayList<>()
           hookMethodList.add(new HookMethod("android/content/Context", "startService", "(Landroid/content/Intent;)Landroid/content/ComponentName", { MethodVisitor mv ->
             //下面的代码使用 ASM Bytecode Viewer Support Kotlin 插件自动生成
             mv.visitTypeInsn(Opcodes.NEW, "com/quwan/tt/common/performance/ServiceHook")
             mv.visitInsn(Opcodes.DUP)
             mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/quwan/tt/common/performance/ServiceHook", "<init>", "()V", false)
             mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/quwan/tt/common/performance/ServiceHook", "printStartServiceStackTrace", "()V", false)
            }))
           hookMethodList.add(new HookMethod("android/content/Context", "startForegroundService", "(Landroid/content/Intent;)Landroid/content/ComponentName;", { MethodVisitor mv ->
             //下面的代码使用 ASM Bytecode Viewer Support Kotlin 插件自动生成
             mv.visitTypeInsn(Opcodes.NEW, "com/quwan/tt/common/performance/ServiceHook")
             mv.visitInsn(Opcodes.DUP)
             mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/quwan/tt/common/performance/ServiceHook", "<init>", "()V", false)
             mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/quwan/tt/common/performance/ServiceHook", "printStartServiceStackTrace", "()V", false)
            }))
             return hookMethodList
         }

         //用DSL的方式使用插件
         blackHook {
             //表示要处理的数据类型是什么，CLASSES 表示要处理编译后的字节码(可能是 jar 包也可能是目录)，RESOURCES 表示要处理的是标准的 java 资源
             inputTypes CONTENT_CLASS
             //表示Transform 的作用域，这里设置的SCOPE_FULL_PROJECT代表作用域是全工程
             scopes SCOPE_FULL_PROJECT
             //表示是否支持增量编译，false不支持
             isIncremental false
             //是否打印扫描到的方法的信息，包含类名，方法名，方法签名
             isNeedLog false
             //表示需要被hook的方法
             hookMethodList = getHookMethods()
         }
 ```