package com.digital.pixelconverter.converter.builder;

import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public abstract class Converter {

  /**
   * @param inputValues ... N×Nで配置された画素値のMap(0~255)
   * @return 変換したあとのN×Nで配置された画素値のMap(0~255)
   */
  /// 具体例:
  /// inputValues[x][y] = val はx+1行目のy+1列目にvalの画素値をもつことを表す。
  /// つまり、下記のような画素値
  /// 0, 1, 2
  /// 3, 4, 5
  /// 6, 7, 8
  /// を渡された場合、下記のようなMapが入力値になる。
  ///
  /// inputValues[0][0] = 0
  /// inputValues[0][1] = 1
  /// inputValues[0][2] = 2
  /// inputValues[1][0] = 3
  /// inputValues[1][1] = 4
  /// inputValues[1][2] = 5
  /// inputValues[2][0] = 6
  /// inputValues[2][1] = 7
  /// inputValues[2][2] = 8
  public Table<Integer, Integer, Integer> getResult(Table<Integer, Integer, Integer> inputValues) {
    Table<Integer, Integer, Integer> result = HashBasedTable.create();
    inputValues.rowKeySet().stream().forEach(x -> {
      Map<Integer, Integer> yToValue = inputValues.rowMap().get(x);
      yToValue.entrySet().forEach(entry -> {
        Integer y = entry.getKey();
        Integer value = entry.getValue();
        result.put(x, y, convert(value));
      });
    });
    return result;
  }

  abstract protected Integer convert(Integer value);
}
