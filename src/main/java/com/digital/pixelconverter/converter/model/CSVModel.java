package com.digital.pixelconverter.converter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CSVModel {
  private Integer x;
  private Integer y;
  private Integer value;
}
