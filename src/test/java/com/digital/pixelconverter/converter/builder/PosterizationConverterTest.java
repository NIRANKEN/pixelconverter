package com.digital.pixelconverter.converter.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PosterizationConverterTest {

  private PosterizationConverter sut;

  @BeforeEach
  public void setUp() {
    sut = new PosterizationConverter();
  }

  @ParameterizedTest
  @ValueSource(ints = { 0, 1, 50 })
  public void testConvert_return0(int inputValue) {
    Integer expected = Integer.valueOf(0);
    assertEquals(expected, sut.convert(inputValue));
  }

  @ParameterizedTest
  @ValueSource(ints = { 51, 101 })
  public void testConvert_return64(int inputValue) {
    Integer expected = Integer.valueOf(64);
    assertEquals(expected, sut.convert(inputValue));
  }

  @ParameterizedTest
  @ValueSource(ints = { 102, 152 })
  public void testConvert_return128(int inputValue) {
    Integer expected = Integer.valueOf(128);
    assertEquals(expected, sut.convert(inputValue));
  }

  @ParameterizedTest
  @ValueSource(ints = { 153, 203 })
  public void testConvert_return192(int inputValue) {
    Integer expected = Integer.valueOf(192);
    assertEquals(expected, sut.convert(inputValue));
  }

  @ParameterizedTest
  @ValueSource(ints = { 204, 255 })
  public void testConvert_return255(int inputValue) {
    Integer expected = Integer.valueOf(255);
    assertEquals(expected, sut.convert(inputValue));
  }

  @ParameterizedTest
  @ValueSource(ints = { -1, 256 })
  public void testConvert_throwError(int inputValue) {
    try {
      sut.convert(inputValue);
      fail();
    } catch (RuntimeException re) {
      assertEquals(String.format("想定されていない画素値(value)の入力です。{value: %s}", inputValue), re.getMessage());
    }
  }
}
