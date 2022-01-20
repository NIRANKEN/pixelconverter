package com.digital.pixelconverter.converter.builder;

public class PosterizationConverter extends Converter {

  @Override
  protected Integer convert(Integer value) {
    // ※階段の数が4つの場合
    if (0 <= value && value < 64) {
      return Integer.valueOf(0);
    } else if (64 <= value && value < 128) {
      return Integer.valueOf(64);
    } else if (128 <= value && value < 192) {
      return Integer.valueOf(128);
    } else if (192 <= value && value < 256) {
      return Integer.valueOf(255);
    }

    throw new RuntimeException(String.format("想定されていない画素値(value)の入力です。{value: %s}", value));
  }
}
