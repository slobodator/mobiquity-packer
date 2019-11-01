package com.mobiquityinc.packer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

class InputFile {
    private final String fileName;

    InputFile(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns testCases which are read from the file
     */
    List<TestCase> testCases() throws IOException {
        return Files.readAllLines(Paths.get(fileName))
                .stream()
                .map(TestCase::parse)
                .collect(Collectors.toList());
    }
}
