package com.digital.pixelconverter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.digital.pixelconverter.converter.builder.Converter;
import com.digital.pixelconverter.converter.factory.ConverterFactory;
import com.digital.pixelconverter.converter.model.ConverterType;
import com.digital.pixelconverter.csv.reader.CsvToTableBuilder;
import com.google.common.collect.Table;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    private static final String CSV_OPTION = "csv";
    private static final String OUTPUT_OPTION = "output";

    public static void main(String[] args) {
        try {
            Options options = new Options();
            options.addOption(CSV_OPTION, true, "use csv file for input");
            options.addOption(OUTPUT_OPTION, true, "write output file");
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(CSV_OPTION)) {
                // CSVファイルの画素値変換を実行
                String outputFilePath = "./result.csv";
                if (cmd.hasOption(OUTPUT_OPTION)) {
                    outputFilePath = cmd.getOptionValue(OUTPUT_OPTION);
                }
                execCSVMode(cmd.getOptionValue(CSV_OPTION), outputFilePath);
            } else {
                // 標準入力の画素値変換を実行
                exec();
            }
        } catch (ParseException pe) {
            log.error("実行引数が不正です", pe);
        }
    }

    private static void exec() {
        try (Scanner scan = new Scanner(System.in)) {
            Integer value = Integer.valueOf(scan.nextLine());
            if (value < 0 || 255 < value) {
                log.error("0~255の数値で入力してください");
                exec();
            }
            ConverterFactory factory = new ConverterFactory();
            Converter converter = factory.get(ConverterType.POSTERIZATION);
            Integer result = converter.getResult(value);

            System.out.println(String.format("ポスタリゼーションによる画素値の変換結果: %d", result));
        } catch (NumberFormatException nfe) {
            log.error("数値で入力してください", nfe);
            exec();
        }
    }

    private static void execCSVMode(String csvPath, String outputFilePath) {
        Table<Integer, Integer, Integer> inputValues = CsvToTableBuilder.build(csvPath);
        log.debug(inputValues.toString());
        ConverterFactory factory = new ConverterFactory();
        Converter converter = factory.get(ConverterType.POSTERIZATION);
        Table<Integer, Integer, Integer> result = converter.getResult(inputValues);

        try (CSVPrinter csvPrinter = CSVFormat.DEFAULT.print(new File(outputFilePath), Charset.forName("UTF-8"))) {
            result.rowKeySet().stream().sorted().forEach(x -> {
                try {
                    Map<Integer, Integer> yToValue = result.rowMap().get(x);
                    csvPrinter.printRecord(
                            yToValue.keySet().stream().sorted().map(y -> yToValue.get(y)).collect(Collectors.toList()));
                } catch (IOException e) {
                    log.error("csvPrinter.printRecordのエラー", e);
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            log.error("CSVPrinter生成エラー", e);
            throw new RuntimeException(e);
        }
    }

}
