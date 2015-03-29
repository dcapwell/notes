package com.github.dcapwell.notes;

import sun.misc.Unsafe;

import java.nio.ByteBuffer;

import static com.github.dcapwell.notes.Bytes.SIZE_OF_INT;
import static com.github.dcapwell.notes.Bytes.SIZE_OF_LONG;

public final class Buffers {
  private Buffers() {
  }

  public static Buffer wrap(final ByteBuffer buffer) {
    return new ByteBufferBuffer(buffer);
  }

  public static Buffer wrap(final byte[] buffer) {
    return new ByteBufferBuffer(buffer);
  }

  private static final class ByteBufferBuffer implements Buffer {
    private static final Unsafe UNSAFE = UnsafeAccess.UNSAFE;
    private static final long ARRAY_BASE_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);

    private final byte[] byteArray;
    private final long addressOffset;
    private final long capacity;
    private final String strValue;

    ByteBufferBuffer(final ByteBuffer buffer) {
      strValue = buffer.clear().getClass().getSimpleName();
      if (buffer.hasArray()) {
        byteArray = buffer.array();
        addressOffset = ARRAY_BASE_OFFSET + buffer.arrayOffset();
      } else {
        byteArray = null;
        addressOffset = ((sun.nio.ch.DirectBuffer) buffer).address();
      }

      capacity = buffer.capacity();
    }

    ByteBufferBuffer(final byte[] array) {
      byteArray = array;
      addressOffset = ARRAY_BASE_OFFSET;
      strValue = "Heap";

      capacity = array.length;
    }

    @Override
    public String toString() {
      return strValue;
    }

    @Override
    public long capacity() {
      return capacity;
    }

    @Override
    public int getInt(final int index) {
      boundsCheck(index, SIZE_OF_INT);

      return UNSAFE.getInt(byteArray, addressOffset + index);
    }

    @Override
    public int getIntVolatile(final int index) {
      boundsCheck(index, SIZE_OF_INT);

      return UNSAFE.getIntVolatile(byteArray, addressOffset + index);
    }

    @Override
    public void putInt(final int index, final int value) {
      boundsCheck(index, SIZE_OF_INT);

      UNSAFE.putInt(byteArray, addressOffset + index, value);
    }

    @Override
    public void putIntVolatile(final int index, final int value) {
      boundsCheck(index, SIZE_OF_INT);

      UNSAFE.putIntVolatile(byteArray, addressOffset + index, value);
    }

    @Override
    public void putIntOrdered(final int index, final int value) {
      boundsCheck(index, SIZE_OF_INT);

      UNSAFE.putOrderedInt(byteArray, addressOffset + index, value);
    }

    @Override
    public boolean compareAndSetInt(final int index, final int expectedValue, final int updateValue) {
      boundsCheck(index, SIZE_OF_INT);

      return UNSAFE.compareAndSwapInt(byteArray, addressOffset + index, expectedValue, updateValue);
    }

    @Override
    public long getLong(final int index) {
      boundsCheck(index, SIZE_OF_LONG);

      return UNSAFE.getLong(byteArray, addressOffset + index);
    }

    @Override
    public long getLongVolatile(final int index) {
      boundsCheck(index, SIZE_OF_LONG);

      return UNSAFE.getLongVolatile(byteArray, addressOffset + index);
    }

    @Override
    public void putLong(final int index, final long value) {
      boundsCheck(index, SIZE_OF_LONG);

      UNSAFE.putLong(byteArray, addressOffset + index, value);
    }

    @Override
    public void putLongVolatile(final int index, final long value) {
      boundsCheck(index, SIZE_OF_LONG);

      UNSAFE.putLongVolatile(byteArray, addressOffset + index, value);
    }

    @Override
    public void putLongOrdered(final int index, final long value) {
      boundsCheck(index, SIZE_OF_LONG);

      UNSAFE.putOrderedLong(byteArray, addressOffset + index, value);
    }

    @Override
    public boolean compareAndSetLong(final int index, final long expectedValue, final long updateValue) {
      boundsCheck(index, SIZE_OF_LONG);

      return UNSAFE.compareAndSwapLong(byteArray, addressOffset + index, expectedValue, updateValue);
    }

    private void boundsCheck(final int index, final int length) {
      if (index < 0 || length < 0 || (index + length) > capacity) {
        throw new IndexOutOfBoundsException(String.format("index=%d, length=%d, capacity=%d", index, length, capacity));
      }
    }
  }
}
