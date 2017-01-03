package com.github.arkinator.splora.importer;

import com.github.arkinator.splora.model.SploraClass;
import com.github.arkinator.splora.model.SploraProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SploraImporter {
    private final String baseDirectory;

    private SploraImporter(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public static SploraProject importFromDirectory(String mavenBaseDirectory) {
        return new SploraImporter(mavenBaseDirectory).execute();
    }

    private SploraProject execute() {
        return new SploraProject(readAllClassesFromDirectory());
    }

    private List<SploraClass> readAllClassesFromDirectory() {
        try (Stream<Path> stream = Files.walk(Paths.get(baseDirectory))) {
            return stream
                    .filter(path -> path.getFileName().toString().endsWith(".java"))
                    .map(path -> buildSploraClass(path))
                    .filter(cl -> cl != null)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new SploraBuilderException(e);
        }
    }

    private SploraClass buildSploraClass(Path path) {
        return new SploraClass(path);
    }

    private class SploraBuilderException extends RuntimeException {
        public SploraBuilderException(Exception e) {
            super(e);
        }
    }
}
