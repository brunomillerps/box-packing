package com.mobiquityinc.domain.knapsack;

import com.mobiquityinc.domain.problem.SubSetProblem;
import com.mobiquityinc.exception.APIException;

/**
 * Specific context class for the Knapsack problem considering fixed-point arithmetic
 */
final class KnapsackSubSetFixedPointProblem {

    private static final int TWO_DIGITS_PRECISION = 100;

    private int[] weights;
    private int[] values;
    private int capacity;

    KnapsackSubSetFixedPointProblem(SubSetProblem problem) throws APIException {
        //times the capacity to comply the search table further based on integer values/weights
        this.capacity = problem.getCapacity() * TWO_DIGITS_PRECISION;

        this.weights = new int[problem.getOriginalItems().length];
        this.values = new int[problem.getOriginalItems().length];

        for (int i = 0; i < problem.getOriginalItems().length; i++) {
            this.weights[i] = (int) (problem.getOriginalItems()[i].getWeight() * TWO_DIGITS_PRECISION);
            this.values[i] = (int) (problem.getOriginalItems()[i].getValue() * TWO_DIGITS_PRECISION);
        }
    }

    int[] getValues() {
        return values;
    }

    int getValueAt(int index) {
        return values[index];
    }

    int[] getWeights() {
        return weights;
    }

    int getWeightAt(int index) {
        return weights[index];
    }

    int getCapacity() {
        return capacity;
    }
}
