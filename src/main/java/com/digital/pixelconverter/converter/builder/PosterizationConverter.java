package com.digital.pixelconverter.converter.builder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PosterizationConverter extends Converter {

  // MEMO: 画素上限値255 + 1
  private static final int OVER_PIXEL_VALUE = 256;

  // MEMO: テキストp266-267の階段の数が5つの場合
  private static final int SEP_PIXEL_VALUE = 5;

  @Override
  protected Integer convert(Integer value) {
    if (value < 0 || 255 < value) {
      log.error("想定されていない画素値(value)の入力です。{value: {}}", value);
      throw new RuntimeException(String.format("想定されていない画素値(value)の入力です。{value: %s}", value));
    }
    int outputUnitLength = OVER_PIXEL_VALUE / (SEP_PIXEL_VALUE - 1);
    int outputUnitLengthNum = calcOutputUnitLengthNum(value);
    log.trace("出力長: {}, 出力長の数: {}", outputUnitLength, outputUnitLengthNum);
    log.trace("変換結果: {}", outputUnitLength * outputUnitLengthNum);
    return outputUnitLength * outputUnitLengthNum == 256 ? Integer.valueOf(255)
        : outputUnitLength * outputUnitLengthNum;
  }

  private int calcOutputUnitLengthNum(int value) {
    int stepWidth = OVER_PIXEL_VALUE / (SEP_PIXEL_VALUE);
    int outputLengthUnitNum = (value / stepWidth);
    return outputLengthUnitNum == SEP_PIXEL_VALUE ? SEP_PIXEL_VALUE - 1 : outputLengthUnitNum;
  }
}
