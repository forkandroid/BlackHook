package com.blackHook.plugin;

import org.objectweb.asm.commons.AdviceAdapter;

class AllMethodVisitor extends AdviceAdapter {
    private final String methodName;
    private final String className;
    private BlackHook blackHook;

    protected AllMethodVisitor(BlackHook blackHook, org.objectweb.asm.MethodVisitor methodVisitor, int access, String name, String descriptor, String className) {
        super(ASM6, methodVisitor, access, name, descriptor);
        this.blackHook = blackHook;
        this.methodName = name;
        this.className = name;
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String methodName, String descriptor, boolean isInterface) {
        super.visitMethodInsn(opcode, owner, methodName, descriptor, isInterface);
        if (blackHook != null && blackHook.hookMethodList != null && blackHook.hookMethodList.size() > 0) {
            for (int i = 0; i < blackHook.hookMethodList.size(); i++) {
                HookMethod hookMethod = blackHook.hookMethodList.get(i);
                if (owner.equals(hookMethod.className) && methodName.equals(hookMethod.methodName) && descriptor.equals(hookMethod.descriptor)) {
                    hookMethod.createBytecode.call(mv);
                    break;
                }
            }
        }

    }
}
