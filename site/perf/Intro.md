# Perf Intro

Once `perf` is installed, using it is can take a bit, since it has a lot you can do with it.

One way of using it is to collect stack traces of a application.  To do this, you use the `record` command with a few arguments.

```bash
sudo perf record -F 99 -a -g -- curl -sL -o /dev/null http://google.com 2>&1
sudo perf script | stackcollapse-perf.pl | flamegraph.pl > perf.svg
```

A lot is going on here, so lets break it down.  `perf record` states that `perf` needs to monitor the command being run (`curl -sL -o /dev/null http://google.com 2>&1`). But what gets monitored is based off the other flags, so lets look at those flags

```man
-a, --all-cpus
   System-wide collection from all CPUs.
...

-g
   Enables call-graph (stack chain/backtrace) recording.
...

-F, --freq=
   Profile at this frequency.
```

So, the main flag here is `-g` which tells `perf` to look at the stack, so we know what functions call what.  `-a` tells `perf` to monitor all the CPUs, and this is important, since `perf` lets you control which CPUs are monitored (also which sockets). And finally the `-F` option tells `perf` how often it should collect stack traces (so in our case, 99 Hertz).
