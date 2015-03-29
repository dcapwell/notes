package com.github.dcapwell.notes;

public final class Tests {
  private Tests() {}

  public static final boolean isMac = System.getProperty("os.name").equalsIgnoreCase("mac");
  public static final boolean isUnix = System.getProperty("os.name").equalsIgnoreCase("nix");

  public static Object[] args(final Object... args) {
    return args;
  }
}
