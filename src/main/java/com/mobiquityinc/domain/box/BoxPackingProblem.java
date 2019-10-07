package com.mobiquityinc.domain.box;

import com.mobiquityinc.domain.problem.SubSetProblem;
import com.mobiquityinc.exception.APIException;

import java.util.List;

/**
 * <p>An abstraction for the problem resolution.</p>
 */
public interface BoxPackingProblem {

    List<Item> solve(final SubSetProblem problem) throws APIException;
}
