package com.mobiquityinc.domain.problem;

import com.mobiquityinc.domain.box.Item;
import com.mobiquityinc.exception.APIException;

import java.util.Arrays;

/**
 * A simple container class for the incoming solutions
 */
public class SubSetProblem {

    private Item[] originalItems;
    private int capacity;

    public SubSetProblem(Item[] originalItems, int capacity) throws APIException {
        if (originalItems == null || originalItems.length == 0) {
            throw new APIException("No item has been provided");
        }

        this.capacity = capacity;
        this.originalItems = originalItems;
    }

    public Item[] getOriginalItems() {
        return originalItems;
    }


    public Item getItemAt(int index) {
        return originalItems[index];
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "SubSetProblem{" +
                "originalItems=" + Arrays.toString(originalItems) +
                ", capacity=" + capacity +
                '}';
    }
}
