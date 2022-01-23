package com.digital.pixelconverter.csv.reader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.digital.pixelconverter.converter.model.CSVModel;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import org.apache.commons.csv.CSVFormat;

public class CsvToTableBuilder {
  public static Table<Integer, Integer, Integer> build(String csvPath) {
    try {
      AtomicInteger rowNum = new AtomicInteger();
      Reader reader = new FileReader(csvPath);
      return CSVFormat.DEFAULT.parse(reader).stream().flatMap(record -> {
        Integer x = Integer.valueOf(rowNum.getAndIncrement());
        return IntStream.range(0, record.size()).boxed()
            .map(y -> new CSVModel(x, y, Integer.valueOf(record.get(y).replaceAll("\\s", ""))))
            .collect(Collectors.toList()).stream();
      }).collect(
          ImmutableTable.toImmutableTable(model -> model.getX(), model -> model.getY(), model -> model.getValue()));
    } catch (FileNotFoundException fe) {
      throw new RuntimeException(fe);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
