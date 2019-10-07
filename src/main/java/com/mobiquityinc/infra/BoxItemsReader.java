package com.mobiquityinc.infra;

import com.mobiquityinc.exception.APIException;

import java.util.List;

public interface BoxItemsReader {

    List<String> read(String item) throws APIException;
}
