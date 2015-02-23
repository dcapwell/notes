CFLAGS=-Wall -g -std=c99 -pthread

build: counter_race locking_counter cas_counter

perf: build
	for cmd in cas_counter  counter_race  locking_counter; do \
		sudo perf record -F 99 -a -g -- ./$$cmd; \
		sudo perf script | stackcollapse-perf.pl | flamegraph.pl > $${cmd}.svg; \
	done;
