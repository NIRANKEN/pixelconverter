package com.digital.pixelconverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.digital.pixelconverter.csv.reader.CsvToTableBuilder;
import com.google.common.collect.Table;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 50 })
    public void testExecStdinMode(int value) {
        System.setOut(new PrintStream(outputStreamCaptor));
        String userInput = String.format("%d", value) + System.lineSeparator();
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);
        App.main(new String[] {});
        assertEquals("画素値(0~255)を入力してください: " + System.lineSeparator() + "ポスタリゼーションによる画素値の変換結果: 0",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void testExecCSVMode() {
        App.main(new String[] { "-csv", "src/test/resources/test.csv", "-output", "src/test/resources/result.csv" });
        Table<Integer, Integer, Integer> resultTable = CsvToTableBuilder.build("src/test/resources/result.csv");
        Table<Integer, Integer, Integer> expectedTable = CsvToTableBuilder.build("src/test/resources/expected.csv");
        assertEquals(expectedTable, resultTable);
    }
}
