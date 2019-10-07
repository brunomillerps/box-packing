package com.mobiquityinc.infra.file;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.infra.BoxItemsReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to read the input <i>item</i> as filePath
 */
public class FileBoxItemsReader implements BoxItemsReader {
    @Override
    public List<String> read(String item) throws APIException {
        if (item == null || item.trim().equals("")) {
            throw new APIException("File Path cannot be null");
        }

        File file = new File(item);

        if (!file.exists() || !file.isFile()) {
            throw new APIException("Cannot read file because it does not exists or is not a final file");
        }

        InputStream in;

        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new APIException("Cannot read file because it does not exists or is not a file");
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

        } catch (IOException e) {
            throw new APIException("Cannot read file", e);
        }

        return lines;
    }
}
