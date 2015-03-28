package com.github.dcapwell.notes;

public interface Buffer {
  long capacity();

  int getInt(int index);
  long getLong(int index);

  boolean compareAndSetInt(int index, int expectedValue, int updateValue);
  boolean compareAndSetLong(int index, long expectedValue, long updateValue);
}
