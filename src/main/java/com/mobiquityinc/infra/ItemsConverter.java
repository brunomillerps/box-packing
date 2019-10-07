package com.mobiquityinc.infra;

import com.mobiquityinc.domain.problem.SubSetProblem;
import com.mobiquityinc.exception.APIException;

import java.util.List;

public interface ItemsConverter {

    List<SubSetProblem> convert(List<String> itemsAsString) throws APIException;
}
