package com.blackHook.plugin;

import java.io.Serializable;

import groovy.lang.Closure;

public class HookMethod implements Serializable {
    String className;
    String methodName;
    String descriptor;
    Closure<Void> createBytecode;

    public HookMethod() {
    }

    public HookMethod(String className, String methodName, String descriptor, Closure<Void> createBytecode) {
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

    public Closure<Void> getCreateBytecode() {
        return createBytecode;
    }

    public void setCreateBytecode(Closure<Void> createBytecode) {
        this.createBytecode = createBytecode;
    }
}
