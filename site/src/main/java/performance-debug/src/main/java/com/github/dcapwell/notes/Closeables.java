package com.github.dcapwell.notes;

import java.io.Closeable;

public final class Closeables {
  private Closeables() {}

  public static void closeQuietly(final Closeable closeable) {
    try {
      closeable.close();
    } catch (Exception e) {
      // ignore...
    }
  }

  public static <T extends Closeable> T closeOnTerm(final T closeable) {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        closeQuietly(closeable);
      }
    });
    return closeable;
  }
}
