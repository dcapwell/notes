package com.github.dcapwell.notes;


public final class ManyToOneRingBuffer {
  private static final int HEADER_LENGTH = Bytes.SIZE_OF_INT * 2;
  private static final int ALIGNMENT = HEADER_LENGTH;
  private static final int TRAILER_LENGTH = Bytes.SIZE_OF_LONG * 2;

  /**
   * When {@link #claimCapacity(int)} is unable to get the resources, then this is returned.
   */
  private static final int INSUFFICIENT_CAPACITY = -1;

  private static final int NORMAL_MESSAGE = 0;
  private static final int PAD_MESSAGE = 1;

  private static final int TAIL_COUNTER_OFFSET = 0;
  private static final int HEAD_COUNTER_OFFSET = TAIL_COUNTER_OFFSET + Bytes.SIZE_OF_LONG;

  private final Buffer buffer;
  private final int capacity;
  private final int mask;

  private final int writtenCounterIndex;
  private final int readCounterIndex;

  private ManyToOneRingBuffer(final Buffer buffer) {
    this.buffer = buffer;
    this.capacity = checkCapacity(buffer.capacity() - TRAILER_LENGTH);
    this.mask = capacity - 1;

    writtenCounterIndex = capacity + TAIL_COUNTER_OFFSET;
    readCounterIndex = capacity + HEAD_COUNTER_OFFSET;
  }

  private static int checkCapacity(final int capacity) {
    if (!Bytes.isPowerOfTwo(capacity)) {
      throw new IllegalArgumentException("Capacity must be a power of two, but was not: " + capacity);
    }
    return capacity;
  }

  public boolean write(final Buffer srcBuffer, final int srcIndex, final int length) {
    checkLength(length);

    final int requiredCapacity = Bytes.align(length + HEADER_LENGTH, ALIGNMENT);
    final int index = claimCapacity(requiredCapacity);
    if (index == INSUFFICIENT_CAPACITY) {
      return false;
    }

    // write body
    writeBody(index, srcBuffer, srcIndex, length);

    // write header
    msgType(index, NORMAL_MESSAGE);
    msgLength(index, length); // flags the read path that the data is readable

    return true;
  }

  /**
   * When a write wants to allocation capacity, it needs to figure out where the last write was, try
   * to increment the write size (tail), and then write the data. To make this fully non-locking, the logic is
   * based off a CAS loop around the write count. If the writer was able to increment the write count, then its
   * safe to write the data, else the write must be aborted.
   * <p/>
   * There are a few cases when a write must be aborted
   * <ul>
   *   <li>not enough capacity. This happens when the reader is far behind the writer.</li>
   *   <li>not enough capacity. when the write extends pass the max buffer length, it rolls over to the start.
   *   If the write would override the unread data, then there is not enough space.</li>
   * </ul>
   */
  private int claimCapacity(final int requiredCapacity) {
    final long read = readVolatile();
    final int readIndex = (int) read & mask;

    long written;
    int writtenIndex;
    int padding;
    do {
      written = writtenVolatile();
      final int availableCapacity = capacity - (int) (written - read);

      if (availableCapacity < requiredCapacity) {
        return INSUFFICIENT_CAPACITY;
      }

      writtenIndex = (int) written & mask;
      padding = 0;

      // check if the write goes past the buffer length, if so wrap around
      final int remainingBufferSpace = capacity - writtenIndex;
      if (requiredCapacity > remainingBufferSpace) {
        // we can't write here, so we need to loop back to the start of the buffer
        // check to see if a write would cause read index to be overridden
        if (requiredCapacity > readIndex) {
          return INSUFFICIENT_CAPACITY;
        }

        // safe to write, but need to override all data here, so pad end of buffer with empty data.
        padding = remainingBufferSpace;
      }

    } while (casWritten(written, written + requiredCapacity + padding));

    if (padding > 0) {
      // write a padding record and loop back to start of buffer
      writePaddingRecord(writtenIndex, padding - HEADER_LENGTH);
      writtenIndex = 0;
    }
    return writtenIndex;
  }

  /**
   * Add a padding record to the buffer. This tells the read path to ignore all data in this record. The read path
   * should not assume that the data is zeroed out, so should always ignore the data.
   */
  private void writePaddingRecord(final int index, final int length) {
    msgType(index, PAD_MESSAGE);
    msgLength(index, length);
  }

  private boolean casWritten(final long expected, final long value) {
    return buffer.compareAndSetLong(writtenCounterIndex, expected, value);
  }

  private long writtenVolatile() {
    return buffer.getLongVolatile(writtenCounterIndex);
  }

  private long readVolatile() {
    return buffer.getLongVolatile(readCounterIndex);
  }

  private void msgType(final int index, final int type) {
    buffer.putIntOrdered(msgTypeOffset(index), type);
  }

  private void msgLength(final int index, final int length) {
    buffer.putIntOrdered(lengthOffset(index), length);
  }

  private void writeBody(final int index, final Buffer srcBuffer, final int srcIndex, final int length) {
    buffer.putBytes(bodyOffset(index), srcBuffer, srcIndex, length);
  }

  private static int msgTypeOffset(final int index) {
    return index;
  }

  private static int bodyOffset(final int index) {
    return index + HEADER_LENGTH;
  }

  private static int lengthOffset(final int index) {
    return index + Bytes.SIZE_OF_INT;
  }

  private static void checkLength(final int length) {
    if (length < 0) {
      throw new IllegalArgumentException("Length must be a positive number: given " + length);
    }
  }
}
