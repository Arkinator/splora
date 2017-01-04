package com.github.arkinator.splora.model;

import java.util.ArrayList;
import java.util.List;

public class SploraPackage {
    private final String packageName;
    private final List<SploraClass> classes;

    public SploraPackage(String packageName) {
        this.packageName = packageName;
        this.classes = new ArrayList<>();
    }

    public String getPackageName() {
        return packageName;
    }

    public List<SploraClass> getClasses() {
        return classes;
    }

    public void addClass(SploraClass clazz) {
        classes.add(clazz);
    }
}
