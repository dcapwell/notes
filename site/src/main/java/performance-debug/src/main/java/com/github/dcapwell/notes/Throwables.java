package com.github.dcapwell.notes;

public final class Throwables {
  private Throwables() {}

  public static RuntimeException throwRuntime(final Throwable t) {
    if(t instanceof RuntimeException) {
      throw (RuntimeException) t;
    } else {
      throw new RuntimeException(t);
    }
  }
}
