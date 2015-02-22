# Strace Filtering

`strace` can produce a lot of information, so if you know what you are looking for, you can ask it to just emit that information.  Lets say you want to know what are all the files opened by `curl`, you can use the `open` syscall for that.

```bash
$ strace -e trace=open -- curl -sL -o /dev/null http://google.com
open("/etc/ld.so.cache", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libcurl.so.4", O_RDONLY|O_CLOEXEC) = 3
open("/lib/x86_64-linux-gnu/libz.so.1", O_RDONLY|O_CLOEXEC) = 3
open("/lib/x86_64-linux-gnu/libpthread.so.0", O_RDONLY|O_CLOEXEC) = 3
open("/lib/x86_64-linux-gnu/libc.so.6", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libidn.so.11", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/librtmp.so.0", O_RDONLY|O_CLOEXEC) = 3
open("/lib/x86_64-linux-gnu/libssl.so.1.0.0", O_RDONLY|O_CLOEXEC) = 3
open("/lib/x86_64-linux-gnu/libcrypto.so.1.0.0", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libgssapi_krb5.so.2", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/liblber-2.4.so.2", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libldap_r-2.4.so.2", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libgnutls.so.26", O_RDONLY|O_CLOEXEC) = 3
open("/lib/x86_64-linux-gnu/libgcrypt.so.11", O_RDONLY|O_CLOEXEC) = 3
open("/lib/x86_64-linux-gnu/libdl.so.2", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libkrb5.so.3", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libk5crypto.so.3", O_RDONLY|O_CLOEXEC) = 3
open("/lib/x86_64-linux-gnu/libcom_err.so.2", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libkrb5support.so.0", O_RDONLY|O_CLOEXEC) = 3
open("/lib/x86_64-linux-gnu/libresolv.so.2", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libsasl2.so.2", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libgssapi.so.3", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libtasn1.so.6", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libp11-kit.so.0", O_RDONLY|O_CLOEXEC) = 3
open("/lib/x86_64-linux-gnu/libgpg-error.so.0", O_RDONLY|O_CLOEXEC) = 3
open("/lib/x86_64-linux-gnu/libkeyutils.so.1", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libheimntlm.so.0", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libkrb5.so.26", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libasn1.so.8", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libhcrypto.so.4", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libroken.so.18", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libffi.so.6", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libwind.so.0", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libheimbase.so.1", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libhx509.so.5", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/x86_64-linux-gnu/libsqlite3.so.0", O_RDONLY|O_CLOEXEC) = 3
open("/lib/x86_64-linux-gnu/libcrypt.so.1", O_RDONLY|O_CLOEXEC) = 3
open("/usr/lib/locale/locale-archive", O_RDONLY|O_CLOEXEC) = 3
open("/home/dcapwell/.curlrc", O_RDONLY) = -1 ENOENT (No such file or directory)
open("/dev/null", O_WRONLY|O_CREAT|O_TRUNC, 0666) = 5
+++ exited with 0 +++
```

Here we see that c will load all the modules curl needs, but this also shows what the curl configuration file is!

```
open("/home/dcapwell/.curlrc", O_RDONLY) = -1 ENOENT (No such file or directory)
```

Curl tried to open a `$HOME/.curlrc` file!  This is a great way to see what configuration files a new cli uses (also the `stat` syscall).
