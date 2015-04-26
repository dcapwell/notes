package com.github.dcapwell.notes;

public final class Bytes {
  private Bytes() {
  }

  /**
   * Size of a byte in bytes
   */
  public static final int SIZE_OF_BYTE = 1;
  /**
   * Size of a boolean in bytes
   */
  public static final int SIZE_OF_BOOLEAN = 1;

  /**
   * Size of a char in bytes
   */
  public static final int SIZE_OF_CHAR = 2;
  /**
   * Size of a short in bytes
   */
  public static final int SIZE_OF_SHORT = 2;

  /**
   * Size of an int in bytes
   */
  public static final int SIZE_OF_INT = 4;
  /**
   * Size of a a float in bytes
   */
  public static final int SIZE_OF_FLOAT = 4;

  /**
   * Size of a long in bytes
   */
  public static final int SIZE_OF_LONG = 8;
  /**
   * Size of a double in bytes
   */
  public static final int SIZE_OF_DOUBLE = 8;

  /**
   * Align a value to the next multiple up of alignment.
   * If the value equals an alignment multiple then it is returned unchanged.
   * <p/>
   * This method executes without branching.
   *
   * @param value     to be aligned up.
   * @param alignment to be used.
   * @return the value aligned to the next boundary.
   */
  public static int align(final int value, final int alignment) {
    return (value + (alignment - 1)) & ~(alignment - 1);
  }

  public static boolean isPowerOfTwo(final int value) {
    return value > 0 && ((value & (~value + 1)) == value);
  }
}
