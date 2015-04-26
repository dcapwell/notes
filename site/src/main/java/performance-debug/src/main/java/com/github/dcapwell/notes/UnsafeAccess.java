package com.github.dcapwell.notes;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

public final class UnsafeAccess {
  public static final Unsafe UNSAFE;

  static {
    Unsafe unsafe = null;
    try {
      final PrivilegedExceptionAction<Unsafe> action =
          () ->
          {
            final Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);

            return (Unsafe) f.get(null);
          };

      unsafe = AccessController.doPrivileged(action);
    } catch (final Exception ex) {
      Throwables.throwRuntime(ex);
    }

    UNSAFE = unsafe;
  }
}
