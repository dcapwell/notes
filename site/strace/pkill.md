# pkill

As stated, if you want to see how a program works, `strace` will show you all the sycalls done by it.  For programs that are very system related, you can basically see most of the logic by just monitoring calls.

Lets see how pkill works!

```bash
 $ strace -- pkill foo 2>&1 | tail -n+48 | head -n 39
 openat(AT_FDCWD, "/proc", O_RDONLY|O_NONBLOCK|O_DIRECTORY|O_CLOEXEC) = 3
 mmap(NULL, 135168, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANONYMOUS, -1, 0) = 0x7fbcf951e000
 mmap(NULL, 135168, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANONYMOUS, -1, 0) = 0x7fbcf94fd000
 open("/usr/lib/x86_64-linux-gnu/gconv/gconv-modules.cache", O_RDONLY) = 4
 fstat(4, {st_mode=S_IFREG|0644, st_size=26258, ...}) = 0
 mmap(NULL, 26258, PROT_READ, MAP_SHARED, 4, 0) = 0x7fbcf9559000
 close(4)                                = 0
 getdents(3, /* 313 entries */, 32768)   = 8400
 stat("/proc/1", {st_mode=S_IFDIR|0555, st_size=0, ...}) = 0
 open("/proc/1/status", O_RDONLY)        = 4
 read(4, "Name:\tinit\nState:\tS (sleeping)\nT"..., 1024) = 770
 close(4)                                = 0
 open("/proc/1/cmdline", O_RDONLY)       = 4
 read(4, "/sbin/init", 2047)             = 10
 close(4)                                = 0
 stat("/proc/2", {st_mode=S_IFDIR|0555, st_size=0, ...}) = 0
 open("/proc/2/status", O_RDONLY)        = 4
 read(4, "Name:\tkthreadd\nState:\tS (sleepin"..., 1024) = 539
 close(4)                                = 0
 open("/proc/2/cmdline", O_RDONLY)       = 4
 read(4, "", 2047)                       = 0
 close(4)                                = 0
 stat("/proc/3", {st_mode=S_IFDIR|0555, st_size=0, ...}) = 0
 open("/proc/3/status", O_RDONLY)        = 4
 read(4, "Name:\tksoftirqd/0\nState:\tS (slee"..., 1024) = 541
 close(4)                                = 0
 open("/proc/3/cmdline", O_RDONLY)       = 4
 read(4, "", 2047)                       = 0
 close(4)                                = 0
 stat("/proc/5", {st_mode=S_IFDIR|0555, st_size=0, ...}) = 0
 open("/proc/5/status", O_RDONLY)        = 4
 read(4, "Name:\tkworker/0:0H\nState:\tS (sle"..., 1024) = 538
 close(4)                                = 0
 open("/proc/5/cmdline", O_RDONLY)       = 4
 read(4, "", 2047)                       = 0
 close(4)                                = 0
 stat("/proc/7", {st_mode=S_IFDIR|0555, st_size=0, ...}) = 0
 open("/proc/7/status", O_RDONLY)        = 4
 read(4, "Name:\trcu_sched\nState:\tS (sleepi"..., 1024) = 545
```

This should give a good understanding of how `pkill` works; it walks the `/proc` directory finding running processes, it looks at the status (for name) and command run to see if it matches the name given.  Since I have no process called `foo`, the `kill` syscall is never called, but you would see it there if `pkill` finds a match.
