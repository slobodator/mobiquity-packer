package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

import java.util.stream.Collectors;

public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        try {
            return new InputFile(filePath)
                    .testCases().stream()
                    .map(TestCase::solution)
                    .map(Solution::text)
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            throw new APIException("Can't pack", e);
        }
    }
}
