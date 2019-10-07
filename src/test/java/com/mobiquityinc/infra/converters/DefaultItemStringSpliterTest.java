package com.mobiquityinc.infra.converters;

import com.mobiquityinc.domain.box.Item;
import com.mobiquityinc.domain.problem.SubSetProblem;
import com.mobiquityinc.exception.APIException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DefaultItemStringSpliterTest {

    private final String content = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
    private DefaultItemStringSpliter spliter = new DefaultItemStringSpliter();


    @Test
    public void testSplit_Success() throws APIException {
        SubSetProblem subSetProblem = spliter.splitLine(content);

        Assert.assertNotNull(subSetProblem);
        Assert.assertEquals(6, subSetProblem.getOriginalItems().length);
        Assert.assertEquals(81, subSetProblem.getCapacity());
        Assert.assertEquals(new Item(1, 53.38, 45), subSetProblem.getOriginalItems()[0]);

    }


    @Test
    public void testBoxCapacity_ShouldReturn81() throws APIException {

        int capacity = spliter.getBoxCapacity(content);

        Assert.assertEquals(81, capacity);

    }

    @Test
    public void testBoxSplitedItems_ShouldReturnRawItems() {

        List<String> itemsGrouped = spliter.getItemsGrouped(content);
        System.out.println(itemsGrouped);

        Assert.assertEquals(6, itemsGrouped.size());
        Assert.assertEquals("(1,53.38,€45)", itemsGrouped.get(0));
    }

    @Test
    public void testBoxSplitedItems_ShouldReturnListOfObjects() {

        List<Item> itemsGrouped = spliter.getItems(spliter.getItemsGrouped(content));

        Assert.assertEquals(6, itemsGrouped.size());
        Assert.assertEquals(new Item(1, 53.38, 45), itemsGrouped.get(0));
    }


}
