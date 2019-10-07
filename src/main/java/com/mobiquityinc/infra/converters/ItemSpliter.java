package com.mobiquityinc.infra.converters;

import com.mobiquityinc.domain.problem.SubSetProblem;
import com.mobiquityinc.exception.APIException;

interface ItemStringSpliter {

    SubSetProblem splitLine(String line) throws APIException;
}
