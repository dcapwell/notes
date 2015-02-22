# Strace Intro

`strace` can be used to see what a running process is doing, or will run a program for you and show you what happens.  For a basic example, lets look at `echo`.

```bash
$ strace -- echo "Hello world!"
execve("/bin/echo", ["echo", "Hello world!"], [/* 56 vars */]) = 0
brk(0)                                  = 0xd37000
access("/etc/ld.so.nohwcap", F_OK)      = -1 ENOENT (No such file or directory)
mmap(NULL, 8192, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANONYMOUS, -1, 0) = 0x7fcf9c4eb000
access("/etc/ld.so.preload", R_OK)      = -1 ENOENT (No such file or directory)
open("/etc/ld.so.cache", O_RDONLY|O_CLOEXEC) = 3
fstat(3, {st_mode=S_IFREG|0644, st_size=121912, ...}) = 0
mmap(NULL, 121912, PROT_READ, MAP_PRIVATE, 3, 0) = 0x7fcf9c4cd000
close(3)                                = 0
access("/etc/ld.so.nohwcap", F_OK)      = -1 ENOENT (No such file or directory)
open("/lib/x86_64-linux-gnu/libc.so.6", O_RDONLY|O_CLOEXEC) = 3
read(3, "\177ELF\2\1\1\0\0\0\0\0\0\0\0\0\3\0>\0\1\0\0\0\320\37\2\0\0\0\0\0"..., 832) = 832
fstat(3, {st_mode=S_IFREG|0755, st_size=1845024, ...}) = 0
mmap(NULL, 3953344, PROT_READ|PROT_EXEC, MAP_PRIVATE|MAP_DENYWRITE, 3, 0) = 0x7fcf9bf05000
mprotect(0x7fcf9c0c0000, 2097152, PROT_NONE) = 0
mmap(0x7fcf9c2c0000, 24576, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_FIXED|MAP_DENYWRITE, 3, 0x1bb000) = 0x7fcf9c2c0000
mmap(0x7fcf9c2c6000, 17088, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_FIXED|MAP_ANONYMOUS, -1, 0) = 0x7fcf9c2c6000
close(3)                                = 0
mmap(NULL, 4096, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANONYMOUS, -1, 0) = 0x7fcf9c4cc000
mmap(NULL, 8192, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANONYMOUS, -1, 0) = 0x7fcf9c4ca000
arch_prctl(ARCH_SET_FS, 0x7fcf9c4ca740) = 0
mprotect(0x7fcf9c2c0000, 16384, PROT_READ) = 0
mprotect(0x606000, 4096, PROT_READ)     = 0
mprotect(0x7fcf9c4ed000, 4096, PROT_READ) = 0
munmap(0x7fcf9c4cd000, 121912)          = 0
brk(0)                                  = 0xd37000
brk(0xd58000)                           = 0xd58000
open("/usr/lib/locale/locale-archive", O_RDONLY|O_CLOEXEC) = 3
fstat(3, {st_mode=S_IFREG|0644, st_size=2919792, ...}) = 0
mmap(NULL, 2919792, PROT_READ, MAP_PRIVATE, 3, 0) = 0x7fcf9bc3c000
close(3)                                = 0
fstat(1, {st_mode=S_IFCHR|0620, st_rdev=makedev(136, 4), ...}) = 0
mmap(NULL, 4096, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANONYMOUS, -1, 0) = 0x7fcf9c4ea000
write(1, "Hello world!\n", 13Hello world!
)          = 13
close(1)                                = 0
munmap(0x7fcf9c4ea000, 4096)            = 0
close(2)                                = 0
exit_group(0)                           = ?
+++ exited with 0 +++
```

There is a lot going on here, so lets see what the different syscalls are used for.

```
open("/lib/x86_64-linux-gnu/libc.so.6", O_RDONLY|O_CLOEXEC) = 3
```

C uses dynamic linking, so at runtime all libraries need to be loaded.  This is what the different syscalls are doing around this open statement; giving the program access to the functions.


```
write(1, "Hello world!\n", 13Hello world!
```

This is the first real part of the output, everything above this can be looked at as mostly just setting up the c runtime.  This write command is printing the requested string to stdout (`1`).
