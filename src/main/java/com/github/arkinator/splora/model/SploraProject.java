package com.github.arkinator.splora.model;

import java.util.List;

public class SploraProject {
    private List<SploraClass> classes;

    public SploraProject(List<SploraClass> classes) {
        this.classes = classes;
    }

    public List<SploraClass> getClasses() {
        return classes;
    }
}
