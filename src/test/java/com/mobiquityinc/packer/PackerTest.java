package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PackerTest {
    @Test
    public void pack() throws APIException {
        assertThat(
                Packer.pack("src/test/resources/input.txt"),
                equalTo("4\n" +
                        "-\n" +
                        "2,7\n" +
                        "8,9")
        );
    }
}