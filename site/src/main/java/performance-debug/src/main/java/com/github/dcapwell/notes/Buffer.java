package com.github.dcapwell.notes;

import java.nio.ByteBuffer;

public interface Buffer {
  /**
   * How much space is in the buffer.
   */
  int capacity();

  /**
   * Get the int value from the index. This method is not thread-safe.
   *
   * @param index offset from base address
   * @return int representation of the value at index
   */
  int getInt(int index);

  /**
   * Same as {@link #getInt(int)} but a volatile read.
   */
  int getIntVolatile(int index);

  void putInt(int index, int value);

  void putIntVolatile(int index, int value);

  /**
   * Same as {@link #putIntVolatile(int, int)} but ordered and lazy.
   */
  void putIntOrdered(int index, int value);

  boolean compareAndSetInt(int index, int expectedValue, int value);

  long getLong(int index);
  long getLongVolatile(int index);

  void putLong(int index, long value);

  void putLongVolatile(int index, long value);

  /**
   * Same as {@link #putLongVolatile(int, long)} but ordered and lazy.
   */
  void putLongOrdered(int index, long value);

  boolean compareAndSetLong(int index, long expectedValue, long value);

  void putBytes(int index, ByteBuffer buffer, int srcIndex, int length);
  void putBytes(int index, Buffer buffer, int srcIndex, int length);

  void boundsCheck(final int index, final int length);

  /**
   * The underline byte array for this buffer.  If the buffer is off-heap, then this will return null.
   */
  byte[] byteArray();

  /**
   * Offset within the {@link #byteArray()}.  If this buffer is off-heap, then this is the memory location of the data.
   */
  long addressOffset();

  void setMemory(int index, int length, byte value);

  byte[] get(int offset, int length);
}
