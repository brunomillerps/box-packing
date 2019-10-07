package com.mobiquityinc.infra.converters;

import com.mobiquityinc.domain.box.Item;
import com.mobiquityinc.domain.problem.SubSetProblem;
import com.mobiquityinc.exception.APIException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Exemplo line to deal<br/>
 * 81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
 */
public class DefaultItemStringSpliter implements ItemStringSpliter {
    private final String LINE_EXAMPLE = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";

    private static final String DECIMAL = "\\d*\\.{0,1}\\d+";
    private static final String BOX_CAPACITY = "(^\\d+\\s?:\\s?)";
    private static final String CURRENCY_SYMBOL = "[$€¥]?";

    private static final String ITEM_START = "\\(";
    private static final String ITEM_END = "\\)";
    private static final String ITEM_INDEX = "\\d\\,";
    private static final String ITEM_WEIGHT = DECIMAL + "\\,";
    private static final String ITEM_VALUE = DECIMAL;

    private static final String FULL_ITEM = "(" + ITEM_START + ITEM_INDEX + ITEM_WEIGHT + CURRENCY_SYMBOL + ITEM_VALUE + ITEM_END + ")";

    @Override
    public SubSetProblem splitLine(String line) throws APIException {

        try {
            int boxCapacity = getBoxCapacity(line);
            List<Item> items = getItems(getItemsGrouped(line));

            Item[] itemsArray = new Item[items.size()];
            itemsArray = items.toArray(itemsArray);

            return new SubSetProblem(itemsArray, boxCapacity);

        } catch (Exception e) {
            throw new APIException(String.format("Line has invalid accepted patter. \n Given: %s \n Accept: %s", line, LINE_EXAMPLE), e);
        }
    }

    List<Item> getItems(List<String> itemsRaw) {

        List<Item> items = new ArrayList<>();

        for (String itemRaw : itemsRaw) {
            int index = getItemIndex(itemRaw);
            double weight = getItemWeight(itemRaw);
            double value = getItemValue(itemRaw);

            items.add(new Item(index, weight, value));
        }
        return items;

    }

    private double getItemValue(String itemRaw) {
        Pattern pattern = Pattern.compile(CURRENCY_SYMBOL + ITEM_VALUE + ITEM_END);
        Matcher matcher = pattern.matcher(itemRaw);
        matcher.find();
        String value = matcher.group();
        return Double.valueOf(value.substring(1, value.length() - 1)); //remove currency symbol and the last parentheses
    }

    private double getItemWeight(String itemRaw) {
        Pattern pattern = Pattern.compile("\\," + ITEM_WEIGHT);
        Matcher matcher = pattern.matcher(itemRaw);
        matcher.find();
        String value = matcher.group();
        return Double.valueOf(value.substring(1, value.length() - 1)); //remove comma at the begin and the end
    }

    private int getItemIndex(String itemRaw) {
        Pattern pattern = Pattern.compile(ITEM_START + ITEM_INDEX);
        Matcher matcher = pattern.matcher(itemRaw);
        matcher.find();
        String index = matcher.group();
        return Integer.valueOf(index.substring(1, index.length() - 1)); //eliminate the parentheses in the begin and the comma at the end
    }

    List<String> getItemsGrouped(String line) {
        List<String> items = new ArrayList<>();
        Pattern pattern = Pattern.compile(FULL_ITEM);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String itemGroup = matcher.group();
            items.add(itemGroup);
        }

        return items;
    }

    int getBoxCapacity(String line) throws APIException {
        Pattern pattern = Pattern.compile(BOX_CAPACITY);
        Matcher matcher = pattern.matcher(line);
        boolean isThere = matcher.find();
        if (!isThere) {
            throw new APIException("Mismatch line. Expected line to start with digits [0-9]");
        }
        String capacity = matcher.group().replaceAll("\\s", "");
        return Integer.valueOf(matcher.group().substring(0, capacity.indexOf(":")));
    }
}
