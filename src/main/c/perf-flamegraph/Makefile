CFLAGS=-Wall -g -std=c99 -pthread
LOCKS=counter_race locking_counter cas_counter
ADDERS=linear_adder from_both_sides_adder linkedlist_adder
PROGS=$(LOCKS) $(ADDERS)
OPTS=

all: build

clean:
	@rm $(PROGS) 2>/dev/null || true
	@rm *.svg 2>/dev/null || true

build: $(PROGS)

perf: build
	for cmd in $(PROGS); do \
		sudo perf record -F 99 -a -g -- ./$$cmd; \
		sudo perf script | stackcollapse-perf.pl | flamegraph.pl > $${cmd}.svg; \
	done;

mem-adders: build
	for cmd in $(ADDERS); do \
		sudo perf stat $(OPTS) -e $$(perf list | grep -E "loads|misses" | grep -v OR | awk '{print $$1}' | tr '[:space:]' ',' | sed 's/\(.*\),/\1/') ./$$cmd > /dev/null; \
	done;
