package com.mobiquityinc.domain.knapsack;

import com.mobiquityinc.domain.box.BoxPackingProblem;
import com.mobiquityinc.domain.box.Item;
import com.mobiquityinc.domain.problem.SubSetProblem;
import com.mobiquityinc.exception.APIException;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>A Dynamic Programming Algorithm (DP) based solution for 0-1
 * Brute force solution to the Knapsack Problem</h3>
 * <p>This solution converts values and weights from decimals to integers, <br/>
 * though the tradeoff for this solution ley on increasing the cache size of subsets in the algorithm
 * <p>
 * <br/>
 * <br/>
 * Given n items, each having a positive fixed point arithmetic weight and a positive value,
 * and given a knapsack (box) with a maximum weight capacity, find a subset
 * of the items of maximum total value whose total weight does not
 * exceed the knapsack capacity.
 * <br/>
 * <br/>
 * <p><strong>Inputs:</strong></p>
 * <ul>
 * <li>A SubSet represented by {@link SubSetProblem}, converted to a {@link KnapsackSubSetFixedPointProblem} containing: </li>
 * <ol>
 * <li>Values (stored in array of integer as v) </li>
 * <li>Weights (stored in array of integer as w) </li>
 * <li>The Knapsack capacity (W) </li>
 * </ol>
 * </ul>
 *
 * <p><strong>NOTE:</strong> The array "v" and array "w" are assumed to store all relevant values starting at index 1.</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Knapsack_problem"> https://en.wikipedia.org/wiki/Knapsack_problem</a>
 */
public class Knapsack implements BoxPackingProblem {

    @Override
    public List<Item> solve(final SubSetProblem curProblem) throws APIException {

        KnapsackSubSetFixedPointProblem problem = new KnapsackSubSetFixedPointProblem(curProblem);

        //max
        int NB_ITEMS = curProblem.getOriginalItems().length;
        // use a matrix to store the max value at each n-th item
        int[][] matrix = new int[NB_ITEMS + 1][problem.getCapacity() + 1];

        initializeFirstLineToZero(problem, matrix);

        buildTableDecision(problem, matrix);

        return getResult(curProblem, problem, matrix);
    }

    /**
     * Build table matrix[][] in top down manner to solve the problem
     *
     * @param problem The context of the problem: Items and box capacity
     * @param matrix  The decision table to persist the algorithm computation
     */
    private void buildTableDecision(KnapsackSubSetFixedPointProblem problem, int[][] matrix) {
        int NB_ITEMS = problem.getValues().length;
        // walk through all items
        for (int i = 1; i <= NB_ITEMS; i++) {
            //iterate over the pack capacity
            for (int j = 0; j <= problem.getCapacity(); j++) {
                if (problem.getWeightAt(i - 1) > j) {
                    matrix[i][j] = matrix[i - 1][j];
                } else {
                    // check next subset problem: which item has greater value and smaller weight?
                    matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i - 1][j - problem.getWeightAt(i - 1)] + problem.getValueAt(i - 1));
                }
            }
        }
    }

    /**
     * First line is initialized to 0
     *
     * @param problem The Knapsack context problem
     * @param matrix  The decision table to persist the algorithm computation
     */
    private void initializeFirstLineToZero(KnapsackSubSetFixedPointProblem problem, int[][] matrix) {
        for (int i = 0; i <= problem.getCapacity(); i++) {
            matrix[0][i] = 0;
        }
    }

    /**
     * Next step is to find the items to include in the bag to maximize the value
     *
     * @param curProblem Original Subset problem
     * @param problem    The Knapsack context problem
     * @param matrix     The decision table to persist the algorithm computation
     * @return An array of {@link Item} as final solution containing items included in the box.
     */
    private List<Item> getResult(SubSetProblem curProblem, KnapsackSubSetFixedPointProblem problem, int[][] matrix) {

        int NB_ITEMS = problem.getValues().length;
        int greatestValue = matrix[NB_ITEMS][problem.getCapacity()];
        int maxCapacity = problem.getCapacity();

        List<Item> itemsSolution = new ArrayList<>();

        //Finding the items to include in the box
        for (int i = NB_ITEMS; i > 0 && greatestValue > 0; i--) {
            if (matrix[i - 1][maxCapacity] != greatestValue) { // will take the item or not
                //take the original item at:
                itemsSolution.add(curProblem.getItemAt(i - 1));
                greatestValue -= problem.getValueAt(i - 1);
                maxCapacity -= problem.getWeightAt(i - 1);
            }
        }

        return itemsSolution;
    }
}
