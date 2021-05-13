package com.blackHook.plugin;

import com.android.build.api.transform.QualifiedContent;
import java.util.List;
import java.util.Set;
import groovy.lang.Closure;
import static com.android.build.gradle.internal.pipeline.TransformManager.CONTENT_CLASS;
import static com.android.build.gradle.internal.pipeline.TransformManager.SCOPE_FULL_PROJECT;

public class BlackHook {

    Closure methodHooker;

    List<HookMethod> hookMethodList;

    Set<QualifiedContent.ContentType> inputTypes = CONTENT_CLASS;

    Set<? super QualifiedContent.Scope> scopes = SCOPE_FULL_PROJECT;

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

    public Set<QualifiedContent.ContentType> getInputTypes() {
        return inputTypes;
    }

    public void setInputTypes(Set<QualifiedContent.ContentType> inputTypes) {
        this.inputTypes = inputTypes;
    }

    public Set<? super QualifiedContent.Scope> getScopes() {
        return scopes;
    }

    public void setScopes(Set<? super QualifiedContent.Scope> scopes) {
        this.scopes = scopes;
    }

    public boolean getIsIncremental() {
        return isIncremental;
    }

    public void setIsIncremental(boolean incremental) {
        isIncremental = incremental;
    }
}
