package com.github.dcapwell.notes;

import java.nio.charset.Charset;

public final class Tests {
  private Tests() {}

  public static final boolean isMac = System.getProperty("os.name").equalsIgnoreCase("mac");
  public static final boolean isUnix = System.getProperty("os.name").equalsIgnoreCase("nix");

  public static Object[] args(final Object... args) {
    return args;
  }

  public static Buffer buffer(final String data) {
    return buffer(data.getBytes(Charsets.UTF_8));
  }

  private static Buffer buffer(final byte[] bytes) {
    return Buffers.wrap(bytes);
  }
}
