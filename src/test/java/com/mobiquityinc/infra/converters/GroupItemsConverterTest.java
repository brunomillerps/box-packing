package com.mobiquityinc.infra.converters;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.infra.ItemsConverter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class GroupItemsConverterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void convertItemsWithNull_ShouldThrowAPIException() throws APIException {

        thrown.expect(APIException.class);
        thrown.expectMessage(is("No item has been provided."));

        ItemsConverter converter = new GroupItemsConverter();

        converter.convert(null);
    }

    @Test
    public void convertItemsWithNullSpliter_ShouldThrowAPIException() throws APIException {

        thrown.expect(APIException.class);
        thrown.expectMessage(is("A valid spliter is required"));

        GroupItemsConverter converter = new GroupItemsConverter();
        converter.setSpliter(null);

        List<String> items = new ArrayList<>();
        items.add("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
        converter.convert(items);

    }

}
