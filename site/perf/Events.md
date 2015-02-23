# Perf Events

`perf` has a lot of information about how programs behave.  Since its built for the kernel, there are a lot of details on how hardware, software, and kernel all interact with each other.  When you use `perf` you can select what events you care about for monitoring; the `perf list` command gives you events you can listen to.

```bash
$ perf list
List of pre-defined events (to be used in -e):
  cpu-cycles OR cycles                               [Hardware event]
  instructions                                       [Hardware event]
  cache-references                                   [Hardware event]
  cache-misses                                       [Hardware event]
  branch-instructions OR branches                    [Hardware event]
  branch-misses                                      [Hardware event]
  bus-cycles                                         [Hardware event]
  ref-cycles                                         [Hardware event]

  cpu-clock                                          [Software event]
  task-clock                                         [Software event]
  page-faults OR faults                              [Software event]
  context-switches OR cs                             [Software event]
  cpu-migrations OR migrations                       [Software event]
  minor-faults                                       [Software event]
  major-faults                                       [Software event]
  alignment-faults                                   [Software event]
  emulation-faults                                   [Software event]
  dummy                                              [Software event]

  L1-dcache-loads                                    [Hardware cache event]
  L1-dcache-load-misses                              [Hardware cache event]
  L1-dcache-stores                                   [Hardware cache event]
  L1-dcache-store-misses                             [Hardware cache event]
  L1-dcache-prefetch-misses                          [Hardware cache event]
  L1-icache-load-misses                              [Hardware cache event]
  LLC-loads                                          [Hardware cache event]
  LLC-stores                                         [Hardware cache event]
  LLC-prefetches                                     [Hardware cache event]
  dTLB-loads                                         [Hardware cache event]
  dTLB-load-misses                                   [Hardware cache event]
  dTLB-stores                                        [Hardware cache event]
  dTLB-store-misses                                  [Hardware cache event]
  iTLB-loads                                         [Hardware cache event]
  iTLB-load-misses                                   [Hardware cache event]
  branch-loads                                       [Hardware cache event]
  branch-load-misses                                 [Hardware cache event]

  branch-instructions OR cpu/branch-instructions/    [Kernel PMU event]
  branch-misses OR cpu/branch-misses/                [Kernel PMU event]
  bus-cycles OR cpu/bus-cycles/                      [Kernel PMU event]
  cache-misses OR cpu/cache-misses/                  [Kernel PMU event]
  cache-references OR cpu/cache-references/          [Kernel PMU event]
  cpu-cycles OR cpu/cpu-cycles/                      [Kernel PMU event]
  cycles-ct OR cpu/cycles-ct/                        [Kernel PMU event]
  cycles-t OR cpu/cycles-t/                          [Kernel PMU event]
  el-abort OR cpu/el-abort/                          [Kernel PMU event]
  el-capacity OR cpu/el-capacity/                    [Kernel PMU event]
  el-commit OR cpu/el-commit/                        [Kernel PMU event]
  el-conflict OR cpu/el-conflict/                    [Kernel PMU event]
  el-start OR cpu/el-start/                          [Kernel PMU event]
  instructions OR cpu/instructions/                  [Kernel PMU event]
  mem-loads OR cpu/mem-loads/                        [Kernel PMU event]
  mem-stores OR cpu/mem-stores/                      [Kernel PMU event]
  tx-abort OR cpu/tx-abort/                          [Kernel PMU event]
  tx-capacity OR cpu/tx-capacity/                    [Kernel PMU event]
  tx-commit OR cpu/tx-commit/                        [Kernel PMU event]
  tx-conflict OR cpu/tx-conflict/                    [Kernel PMU event]
  tx-start OR cpu/tx-start/                          [Kernel PMU event]

  rNNN                                               [Raw hardware event descriptor
  cpu/t1=v1[,t2=v2,t3 ...]/modifier                  [Raw hardware event descriptor
   (see 'man perf-list' on how to encode it)

  mem:<addr>[:access]                                [Hardware breakpoint]
```

For example, lets look at caches and branching.

# Adders

These examples sum up a lists of data.  The implementations are what differ as a means to show how caching and branching are effected.

The following events are being monitored

```bash
$ perf list | grep -E "loads|misses" | grep -v OR | awk '{print $1}'
cache-misses                  # was not found in any cpu cache
branch-misses
L1-dcache-loads               # L1 data cache (d stands for data)
L1-dcache-load-misses
L1-dcache-store-misses
L1-dcache-prefetch-misses
L1-icache-load-misses         # L1 instruction cache (i stands for instruction)
LLC-loads                     # "Last Level Cache", if not found here, then "cache-misses" will inc
dTLB-loads                    # "translation lookaside buffer", cache for virtual to physical address lookup (d stands for data)
dTLB-load-misses
dTLB-store-misses
iTLB-loads                    # "translation lookaside buffer", cache for virtual to physical address lookup(i stands for instruction)
iTLB-load-misses
branch-loads
branch-load-misses
```

## Linear

Take a array and walks it in order. The code can be found [here](../src/main/c/perf-flamegraph/linear_adder.c)


```bash
 Performance counter stats for './linear_adder':

        40,878,727 cache-misses                                                 [26.99%]
        14,306,047 branch-misses                                                [27.38%]
    16,189,466,181 L1-dcache-loads                                              [27.17%]
       473,557,465 L1-dcache-load-misses     #    2.93% of all L1-dcache hits   [26.63%]
       264,300,663 L1-dcache-store-misses                                       [26.10%]
                 0 L1-dcache-prefetch-misses                                    [25.75%]
        84,983,515 L1-icache-load-misses                                        [26.26%]
        63,150,229 LLC-loads                                                    [26.67%]
    16,000,418,087 dTLB-loads                                                   [27.48%]
        12,953,496 dTLB-load-misses          #    0.08% of all dTLB cache hits  [27.85%]
         3,499,865 dTLB-store-misses                                            [27.22%]
         6,033,521 iTLB-loads                                                   [26.67%]
           137,301 iTLB-load-misses          #    2.28% of all iTLB cache hits  [25.70%]
     5,358,000,833 branch-loads                                                 [26.10%]
        14,514,551 branch-load-misses                                           [26.46%]

      72.204267148 seconds time elapsed
```

## From Both Sides

Takes a array and adds `i` and `size - i` at the same time. The code can be found [here](../src/main/c/perf-flamegraph/from_both_sides_adder.c)

```bash
 Performance counter stats for './from_both_sides_adder':

        46,651,186 cache-misses                                                 [26.58%]
        27,268,639 branch-misses                                                [26.02%]
    18,303,085,556 L1-dcache-loads                                              [26.59%]
       558,199,829 L1-dcache-load-misses     #    3.05% of all L1-dcache hits   [26.74%]
       288,333,367 L1-dcache-store-misses                                       [26.88%]
                 0 L1-dcache-prefetch-misses                                    [27.19%]
        41,591,357 L1-icache-load-misses                                        [27.01%]
        63,350,046 LLC-loads                                                    [26.72%]
    18,398,342,880 dTLB-loads                                                   [26.94%]
        14,120,976 dTLB-load-misses          #    0.08% of all dTLB cache hits  [26.76%]
         3,414,592 dTLB-store-misses                                            [26.70%]
         3,693,511 iTLB-loads                                                   [26.59%]
            78,424 iTLB-load-misses          #    2.12% of all iTLB cache hits  [26.61%]
     7,026,888,081 branch-loads                                                 [26.56%]
        26,626,588 branch-load-misses                                           [26.52%]

      39.921707780 seconds time elapsed
```

## Linked List

Takes a linked list and walks it in order (400m elements vs 1b like the rest). The code can be found [here](../src/main/c/perf-flamegraph/linkedlist_adder.c)

```bash
 Performance counter stats for './linkedlist_adder':

        55,014,404 cache-misses                                                 [26.36%]
        16,715,817 branch-misses                                                [26.31%]
    26,418,238,556 L1-dcache-loads                                              [26.58%]
       615,325,868 L1-dcache-load-misses     #    2.33% of all L1-dcache hits   [26.66%]
       276,659,932 L1-dcache-store-misses                                       [26.78%]
                 0 L1-dcache-prefetch-misses                                    [26.73%]
       111,946,630 L1-icache-load-misses                                        [26.85%]
       120,545,583 LLC-loads                                                    [26.83%]
    25,985,243,026 dTLB-loads                                                   [26.87%]
        15,918,952 dTLB-load-misses          #    0.06% of all dTLB cache hits  [26.78%]
        11,610,328 dTLB-store-misses                                            [26.85%]
         9,819,113 iTLB-loads                                                   [26.78%]
           289,067 iTLB-load-misses          #    2.94% of all iTLB cache hits  [26.74%]
    19,283,351,923 branch-loads                                                 [26.64%]
        16,742,437 branch-load-misses                                           [26.52%]

      93.792359274 seconds time elapsed
```

## Linked List without assert

Same as above, but the assert is commented out

```bash
 Performance counter stats for './linkedlist_adder':

        52,220,633 cache-misses                                                 [26.65%]
        15,847,915 branch-misses                                                [26.27%]
    25,761,210,404 L1-dcache-loads                                              [26.38%]
       579,263,767 L1-dcache-load-misses     #    2.25% of all L1-dcache hits   [26.42%]
       270,798,986 L1-dcache-store-misses                                       [26.46%]
                 0 L1-dcache-prefetch-misses                                    [26.69%]
       108,347,338 L1-icache-load-misses                                        [26.55%]
       106,339,684 LLC-loads                                                    [26.41%]
    25,909,887,869 dTLB-loads                                                   [26.77%]
        13,402,369 dTLB-load-misses          #    0.05% of all dTLB cache hits  [27.16%]
        10,280,813 dTLB-store-misses                                            [27.11%]
         7,592,594 iTLB-loads                                                   [27.14%]
           233,201 iTLB-load-misses          #    3.07% of all iTLB cache hits  [26.89%]
    18,801,929,066 branch-loads                                                 [26.49%]
        16,188,020 branch-load-misses                                           [26.89%]

      91.687703629 seconds time elapsed
```

## Summary

Results shouldn't be too shocking, `from_both_sides_adder` has to load less instructions since its doing more in its loop, same for branching since it has to do half the branching since it does two elements at the same time.

Whats interesting is why the linked list was slower, both the amount of data requests (two field lookups vs 1 array lookup) and the number of instruction misses seem to be the major issue. One thing I don't get is why `branch-loads` is so much higher.  The only difference I see is while vs for, but that shouldn't cause this.
