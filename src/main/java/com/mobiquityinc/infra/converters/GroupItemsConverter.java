package com.mobiquityinc.infra.converters;

import com.mobiquityinc.domain.problem.SubSetProblem;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.infra.ItemsConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>The input represents several lines.</p>
 * <p>Each line contains the weight that the package can take (before the colon) and the list of things you <br/>
 * need to choose.
 * <p>Each thing is enclosed in parentheses where the 1 st number is a thing's index number,<br/>
 * the 2 nd is its weight and the 3 rd is its cost. E.g:</p>
 * <blockquote>81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)</blockquote>
 */
public class GroupItemsConverter implements ItemsConverter {

    private ItemStringSpliter spliter = new DefaultItemStringSpliter();

    @Override
    public List<SubSetProblem> convert(List<String> itemsAsString) throws APIException {
        if (itemsAsString == null || itemsAsString.size() == 0) {
            throw new APIException("No item has been provided.");
        }

        if (spliter == null) {
            throw new APIException("A valid spliter is required");
        }

        List<SubSetProblem> items = new ArrayList<>();
        for (String line : itemsAsString) {
            SubSetProblem subset = spliter.splitLine(line);
            items.add(subset);
        }

        return items;
    }

    public void setSpliter(ItemStringSpliter spliter) {
        this.spliter = spliter;
    }
}
