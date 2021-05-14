package com.blackHook.plugin;

import java.util.ArrayList;
import java.util.List;
import groovy.lang.Closure;

//version:1.0.9

public class BlackHook {

    Closure methodHooker;

    List<HookMethod> hookMethodList = new ArrayList<>();

    public static final String CONTENT_CLASS = "CONTENT_CLASS";
    public static final String CONTENT_JARS = "CONTENT_JARS";
    public static final String CONTENT_RESOURCES = "CONTENT_RESOURCES";

    public static final String SCOPE_FULL_PROJECT = "SCOPE_FULL_PROJECT";
    public static final String PROJECT_ONLY = "PROJECT_ONLY";

    String inputTypes = CONTENT_CLASS;

    String scopes = SCOPE_FULL_PROJECT;

    boolean isIncremental = false;

    public Closure getMethodHooker() {
        return methodHooker;
    }

    public void setMethodHooker(Closure methodHooker) {
        this.methodHooker = methodHooker;
    }

    public List<HookMethod> getHookMethodList() {
        return hookMethodList;
    }

    public void setHookMethodList(List<HookMethod> hookMethodList) {
        this.hookMethodList = hookMethodList;
    }

    public String getInputTypes() {
        return inputTypes;
    }

    public void setInputTypes(String inputTypes) {
        this.inputTypes = inputTypes;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public boolean getIsIncremental() {
        return isIncremental;
    }

    public void setIsIncremental(boolean incremental) {
        isIncremental = incremental;
    }
}
