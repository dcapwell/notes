package com.github.dcapwell.notes;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public final class IO {
  private IO() {
  }

  public static FileChannel createTempFile() {
    try {
      final File dir = File.createTempFile("test", "data");
      final RandomAccessFile raf = new RandomAccessFile(dir, "rw");
      return raf.getChannel();
    } catch (IOException e) {
      throw Throwables.throwRuntime(e);
    }
  }
}
