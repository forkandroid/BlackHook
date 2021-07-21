简介
      BlackHook 是一个编译时插桩插件，基于ASM+Tranfrom实现，理论上可以hook任意一个java方法或者kotlin方法，只要代码对应的字节码可以在编译阶段被Tranfrom扫描到，就可以使用ASM在代码对应的字节码处插入特定字节码，从而hook该方法
优点
       1. 用DSL(领域特定语言)使用该插件，使用简单，配置灵活，而且插入的字节码可以使用
           ASM Bytecode Viewer Support Kotlin 插件自动生成，上手难度低
       2. 理论上可以hook任意一个java方法，只要代码对应的字节码可以在编译阶段被Tranfrom扫描到
       3. 基于ASM+Tranfrom实现，在编译阶段直接修改字节码，效率高，兼容性问题少
