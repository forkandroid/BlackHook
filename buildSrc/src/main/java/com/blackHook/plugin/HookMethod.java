package com.blackHook.plugin;

import groovy.lang.Closure;

public class HookMethod {
    String className;
    String methodName;
    String descriptor;
    Closure createBytecode;

    public HookMethod(String className, String methodName, String descriptor, Closure createBytecode) {
        this.className = className;
        this.methodName = methodName;
        this.descriptor = descriptor;
        this.createBytecode = createBytecode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Closure getCreateBytecode() {
        return createBytecode;
    }

    public void setCreateBytecode(Closure createBytecode) {
        this.createBytecode = createBytecode;
    }
}
