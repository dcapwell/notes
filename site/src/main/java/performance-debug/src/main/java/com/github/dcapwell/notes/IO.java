package com.github.dcapwell.notes;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public final class IO {
  private static final String TEMP_FILE_CLEANUP_KEY = "notes.tests.temp.cleanup";
  private static final String DEFAULT_TEMP_FILE_CLEANUP = "true";
  private static final boolean TEMP_FILE_CLEANUP =
      Boolean.parseBoolean(System.getProperty(TEMP_FILE_CLEANUP_KEY, DEFAULT_TEMP_FILE_CLEANUP));

  private IO() {
  }

  public static FileChannel createTempFile() {
    try {
      final File dir = File.createTempFile("test", "data");
      final RandomAccessFile raf = new RandomAccessFile(dir, "rw");
      if(TEMP_FILE_CLEANUP) {
        dir.deleteOnExit();
      }
      return raf.getChannel();
    } catch (IOException e) {
      throw Throwables.throwRuntime(e);
    }
  }
}
