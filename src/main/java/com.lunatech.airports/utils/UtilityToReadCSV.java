package com.lunatech.airports.utils;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UtilityToReadCSV {

    public static List<Map<String, String>> readCsv(String fileName) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema cvsSchema = CsvSchema.emptySchema().withHeader();
        File csvFile = new ClassPathResource(fileName).getFile();

        MappingIterator<Map<String, String>> populatedValues = csvMapper.readerFor(Map.class)
                .with(cvsSchema)
                .readValues(csvFile);
        return populatedValues.readAll();
    }



}
