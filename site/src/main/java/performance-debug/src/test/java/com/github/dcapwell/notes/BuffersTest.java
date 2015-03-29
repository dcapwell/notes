package com.github.dcapwell.notes;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.github.dcapwell.notes.Tests.args;
import static com.github.dcapwell.notes.Tests.isMac;
import static com.github.dcapwell.notes.Tests.isUnix;

public final class BuffersTest {

  @Test(dataProvider = "smallBuffers")
  public void intBuffers(final Buffer buffer) {
    int prev = 0;
    int next;
    for (int i = 0; i < 32; i++) {
      next = 1 << i;
      Assert.assertTrue(buffer.compareAndSetInt(0, prev, next));
      Assert.assertEquals(buffer.getInt(0), next);
      prev = next;
    }
  }

  @Test(dataProvider = "smallBuffers")
  public void intBuffersVolatile(final Buffer buffer) {
    int next;
    for (int i = 0; i < 32; i++) {
      next = 1 << i;
      buffer.putIntVolatile(0, next);
      Assert.assertEquals(buffer.getIntVolatile(0), next);
    }
  }

  @Test(dataProvider = "smallBuffers")
  public void intBuffersOrdered(final Buffer buffer) {
    int next;
    for (int i = 0; i < 32; i++) {
      next = 1 << i;
      buffer.putIntOrdered(0, next);
      Assert.assertEquals(buffer.getIntVolatile(0), next);
    }
  }

  @Test(dataProvider = "smallBuffers")
  public void longBuffers(final Buffer buffer) {
    long prev = 0;
    long next;
    for (int i = 0; i < 64; i++) {
      next = 1 << i;
      Assert.assertTrue(buffer.compareAndSetLong(0, prev, next));
      Assert.assertEquals(buffer.getLong(0), next);
      prev = next;
    }
  }

  @Test(dataProvider = "smallBuffers")
  public void longBuffersVolatile(final Buffer buffer) {
    long next;
    for (int i = 0; i < 64; i++) {
      next = 1 << i;
      buffer.putLongVolatile(0, next);
      Assert.assertEquals(buffer.getLongVolatile(0), next);
    }
  }

  @Test(dataProvider = "smallBuffers")
  public void longBuffersOrdered(final Buffer buffer) {
    long next;
    for (int i = 0; i < 64; i++) {
      next = 1 << i;
      buffer.putLongOrdered(0, next);
      Assert.assertEquals(buffer.getLongVolatile(0), next);
    }
  }

  @DataProvider
  public static Iterator<Object[]> smallBuffers() throws IOException {
    final List<Object[]> tests = new ArrayList<>(4);

    tests.add(args(Buffers.wrap(new byte[Bytes.SIZE_OF_LONG])));

    tests.add(args(Buffers.wrap(ByteBuffer.allocate(Bytes.SIZE_OF_LONG))));
    tests.add(args(Buffers.wrap(ByteBuffer.allocateDirect(Bytes.SIZE_OF_LONG))));

    // on mac, this seems to cause segfaults when you call volatile methods
    if(isUnix) {
      final FileChannel fc = Closeables.closeOnTerm(IO.createTempFile());
      tests.add(args(Buffers.wrap(fc.map(FileChannel.MapMode.READ_WRITE, 0, Bytes.SIZE_OF_LONG))));
    }

    return tests.iterator();
  }
}
