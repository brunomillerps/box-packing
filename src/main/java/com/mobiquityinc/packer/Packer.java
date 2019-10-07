package com.mobiquityinc.packer;

import com.mobiquityinc.domain.box.BoxPackingProblem;
import com.mobiquityinc.domain.box.Item;
import com.mobiquityinc.domain.knapsack.Knapsack;
import com.mobiquityinc.domain.problem.SubSetProblem;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.infra.BoxItemsReader;
import com.mobiquityinc.infra.ItemsConverter;
import com.mobiquityinc.infra.converters.GroupItemsConverter;
import com.mobiquityinc.infra.file.FileBoxItemsReader;

import java.util.List;
import java.util.stream.Collectors;

public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {

        //read file
        BoxItemsReader reader = new FileBoxItemsReader();
        List<String> items = reader.read(filePath);

        //convert file to list of problems
        ItemsConverter converter = new GroupItemsConverter();
        List<SubSetProblem> problems = converter.convert(items);

        //the Knapsack solution
        BoxPackingProblem knapsack = new Knapsack();

        StringBuilder sb = new StringBuilder();

        for (SubSetProblem problem : problems) {

            List<Item> solution = knapsack.solve(problem);

            if (solution.size() == 0) continue;

            String indexes = solution.stream()
                    .map(i -> String.valueOf(i.getIndex()))
                    .collect(Collectors.joining(","));

            sb.append(System.lineSeparator());
            sb.append(solution.size());
            sb.append(System.lineSeparator());
            sb.append("-");
            sb.append(System.lineSeparator());
            sb.append(indexes);
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
