package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.Assert;
import org.junit.Test;

public class PackerTest {

    @Test
    public void testSuccess_WithoutCriterias() throws APIException {

        String result = Packer.pack("src/test/resource/boxes-segments");
        Assert.assertEquals("\n" +
                "1\n" +
                "-\n" +
                "4\n" +
                "\n" +
                "\n" +
                "2\n" +
                "-\n" +
                "7,2\n" +
                "\n" +
                "\n" +
                "2\n" +
                "-\n" +
                "9,6\n\n", result);

    }
}
