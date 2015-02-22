# Perf FlameGraphs

[Flame Graphs](http://www.brendangregg.com/flamegraphs.html) are a great way to visualize stack traces for applications.  They let you see where most of your time is being spent, and just look damn cool! The integration with `perf` is also very simple, the following is all it takes to go from `perf` to a flame graph.

```bash
sudo perf record -F 99 -a -g -- curl -sL -o /dev/null http://google.com 2>&1
sudo perf script | stackcollapse-perf.pl | flamegraph.pl > perf.svg
```

Just record, then convert from `perf` to flamegraph format (`stackcollapse-perf.pl`), then create the svg (`flamegraph.pl > perf.svg`).  Once you do this, you will get the awesome image below!

![Curl flame graph](imgs/perf.svg)
