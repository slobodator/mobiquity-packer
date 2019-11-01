package com.mobiquityinc.packer;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class InputFileTest {
    @Test
    public void readLines() throws IOException {
        InputFile inputData = new InputFile("src/test/resources/input.txt");
        assertThat(inputData.testCases(), hasSize(4));
    }
}