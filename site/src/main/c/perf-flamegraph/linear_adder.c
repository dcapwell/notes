#include <stdio.h>

#define NUM_ELEMENTS  1000000000

unsigned long add(unsigned int esc, unsigned long *es)
{
  unsigned long sum = 0;
  for(unsigned int i = 0; i < esc; ++i) {
    sum = sum + es[i];
  }
  return sum;
}

int main(int argc, char *argv[])
{
  unsigned long elements[NUM_ELEMENTS];
  // prep the data
  for(unsigned int i = 0; i < NUM_ELEMENTS; ++i) {
    elements[i] = i << 1;
  }

  unsigned long sum = add(NUM_ELEMENTS, elements);
  printf("Sum of all elements was: %lu\n", sum);
}
