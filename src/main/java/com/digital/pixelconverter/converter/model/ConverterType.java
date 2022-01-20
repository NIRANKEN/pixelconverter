package com.digital.pixelconverter.converter.model;

public enum ConverterType {
  POSTERIZATION;

  public static ConverterType getType(String converterTypeArg) {
    for (ConverterType type : ConverterType.values()) {
      if (type.name().equals(converterTypeArg)) {
        return type;
      }
    }
    throw new RuntimeException(String.format("不正なconverterTypeです: %s", converterTypeArg));
  }
}
