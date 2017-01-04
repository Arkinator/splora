package com.github.arkinator.splora.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SploraProject {
    private final ArrayList<SploraPackage> packagesList;
    private List<SploraClass> classList;
    private Map<String, SploraPackage> packages;
    private Map<String, SploraClass> classes;

    public SploraProject(List<SploraClass> classes) {
        this.classList = classes;
        this.packages = new HashMap<>();
        this.classes = new HashMap<>();
        for (SploraClass clazz : classList) {
            if (!packages.containsKey(clazz.getPackageName())) {
                packages.put(clazz.getPackageName(), new SploraPackage(clazz.getPackageName()));
            }
            packages.get(clazz.getPackageName()).addClass(clazz);
            this.classes.put(clazz.getClassName(), clazz);
        }
        this.packagesList = new ArrayList<>();
        packagesList.addAll(packages.values());
    }

    public List<SploraClass> getClassList() {
        return classList;
    }

    public List<SploraPackage> getPackages() {
        return packagesList;
    }

    public SploraPackage getPackage(String name) {
        return packages.get(name);
    }

    public SploraClass getClass(String name) {
        return classes.get(name);
    }

    public String exportToPlantUml() {
        StringBuilder result = new StringBuilder();
        result.append("@startuml\n");
        packagesList.stream().map(pack -> packageToString(pack)).forEach(s -> result.append(s));
        classList.stream().map(cl -> writeDependencies(cl)).forEach(s -> result.append(s));
        result.append("@enduml\n");
        return result.toString();
    }

    private String writeDependencies(SploraClass cl) {
        return cl.getDependencies().stream()
                .map(dp -> "\t\t[" + cl.getClassName() +"] -"+getArrow(cl, dp)+"> ["+dp+"]")
                .collect(Collectors.joining("\n"))+"\n";
    }

    private String getArrow(SploraClass class1, String class2Name) {
        if (!classes.containsKey(class2Name))
            return "";
        int package1Ordinal = getOrdinalForPackage(class1.getPackageName());
        int package2Ordinal = getOrdinalForPackage(getClass(class2Name).getPackageName());
        return StringUtils.repeat("-",package1Ordinal - package2Ordinal);
    }

    private String packageToString(SploraPackage pack) {
        String result = "\tpackage \""+pack.getPackageName()+"\" {\n";
        result += pack.getClasses().stream()
                .map(cl -> "\t\t[" + cl.getClassName()+"]")
                .collect(Collectors.joining("\n"));
        return result + "\n\t}\n\n";
    }

    private int getOrdinalForPackage(String packageName) {
        int ordinal = 0;
        for(SploraPackage pack : packagesList){
            if (pack.getPackageName().equals(packageName))
                return ordinal;
            ordinal++;
        }
        return -1;
    }
}
