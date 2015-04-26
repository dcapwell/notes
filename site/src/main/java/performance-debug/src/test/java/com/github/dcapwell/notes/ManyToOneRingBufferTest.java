package com.github.dcapwell.notes;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public final class ManyToOneRingBufferTest {

  public void putThenRead() {
    final String expectedValue = "putThenRead";
    final ManyToOneRingBuffer buffer = new ManyToOneRingBuffer(Buffers.ofSize((1 << 15) + ManyToOneRingBuffer.TRAILER_LENGTH));
    final boolean written = buffer.write(Tests.buffer(expectedValue));
    Assert.assertTrue(written, "Unable to perform the write");

    final int count = buffer.read((record, offset, length) -> {
      final byte[] data = record.get(offset, length);
      final String value = new String(data, Charsets.UTF_8);
      Assert.assertEquals(value, expectedValue);
    }, 1);

    Assert.assertEquals(count, 1);
  }
}
