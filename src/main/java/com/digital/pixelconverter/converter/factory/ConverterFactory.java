package com.digital.pixelconverter.converter.factory;

import com.digital.pixelconverter.converter.builder.Converter;
import com.digital.pixelconverter.converter.builder.PosterizationConverter;
import com.digital.pixelconverter.converter.model.ConverterType;

public class ConverterFactory {
  public Converter get(ConverterType type) {
    switch (type) {
    case POSTERIZATION:
      return new PosterizationConverter();
    // 他の変換が必要な場合はここでEnumと作ったConverterを紐付ける
    default:
      // MEMO: strictに判定するならエラー出す
      return new PosterizationConverter();
    }
  }
}
