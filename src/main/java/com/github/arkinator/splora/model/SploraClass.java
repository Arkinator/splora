package com.github.arkinator.splora.model;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SploraClass {
    private String packageName;
    private String className;

    public static SploraClass buildFromPath(Path path) {
        String[] mvnStructure = path.toString().split("src/main/java/");
        if (mvnStructure.length != 2)
            return null;
        SploraClass result = new SploraClass();
        extractPackageAndClassName(result, mvnStructure[1]);
        return result;
    }

    private static void extractPackageAndClassName(SploraClass result, String packageFolder) {
        String[] folders = packageFolder.split("/");
        result.packageName = Arrays.stream(folders)
                .limit(folders.length - 1)
                .collect(Collectors.joining("."));
        result.className = folders[folders.length - 1].split(".java")[0];
    }
}
