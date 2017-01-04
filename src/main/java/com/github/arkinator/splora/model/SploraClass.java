package com.github.arkinator.splora.model;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class SploraClass {
    private String packageName;
    private String className;
    private List<String> dependencies;

    public static SploraClass buildFromPath(Path path) {
        String[] mvnStructure = path.toString().split("src/main/java/");
        if (mvnStructure.length != 2)
            return null;
        SploraClass result = new SploraClass();
        extractPackageAndClassName(result, mvnStructure[1]);
        extractDependencies(result, path);
        return result;
    }

    private static void extractDependencies(SploraClass result, Path path) {
        try {
            result.dependencies = new ArrayList<>();
            String content = FileUtils.readFileToString(path.toFile(), "UTF-8");
            boolean firstPart = true;
            for (String part : content.split("@Autowired")){
                if (firstPart){
                    firstPart = false;
                    continue;
                }
                result.dependencies.add(extractBeanNameFromPart(part));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file '"+path.toString()+"'", e);
        }
    }

    public static String extractBeanNameFromPart(String part) {
        for (String candidate : part.trim().replace("\t"," ").split(" ")){
            if (!asList("public", "private", "protected").contains(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private static void extractPackageAndClassName(SploraClass result, String packageFolder) {
        String[] folders = packageFolder.split("/");
        result.packageName = Arrays.stream(folders)
                .limit(folders.length - 1)
                .collect(Collectors.joining("."));
        result.className = folders[folders.length - 1].split(".java")[0];
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public List<String> getDependencies() {
        return dependencies;
    }
}
