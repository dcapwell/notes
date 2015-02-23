#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

#define NUM_THREADS     20
#define NUM_INCREMENTS  1000000
#define EXPECTED        NUM_THREADS * NUM_INCREMENTS

int counter;

void *task(void *tid)
{
  for(int i = 0; i < NUM_INCREMENTS; i++) {
    __sync_add_and_fetch(&counter, 1);
  }

  pthread_exit(NULL);
}

int main(int argc, char *argv[])
{
  pthread_t threads[NUM_THREADS];
  int thread_args[NUM_THREADS];
  int rc, i;
  for(i = 0; i < NUM_THREADS; ++i) {
    thread_args[i] = i;
    rc = pthread_create(&threads[i], NULL, task, (void *) &thread_args[i]);
    assert(0 == rc);
  }

  for(i = 0; i < NUM_THREADS; ++i) {
    rc = pthread_join(threads[i], NULL);
    printf("In main: thread %d is complete\n", i);
    assert(0 == rc);
  }
  printf("Final value of counter is: %d\n", counter);
  printf("Expected value is: %d\nOff by: %d\n", EXPECTED, EXPECTED - counter);
  pthread_exit(NULL);
}
