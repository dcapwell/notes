#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

//#define NUM_ELEMENTS  1,000,000,000
// Can't handle as much 0's as the other ones could, ~8gb needed
//   /var/log/messages had the following
//   Feb 22 23:27:36 $hostname kernel: [159997.382492] [ 7867]  1000  7867  3271098  1770781    6395  1498723             0 linkedlist_adde
//   Feb 22 23:27:36 $hostname kernel: [159997.382493] Out of memory: Kill process 7867 (linkedlist_adde) score 819 or sacrifice child
//   Feb 22 23:27:36 $hostname kernel: [159997.382495] Killed process 7867 (linkedlist_adde) total-vm:13084392kB, anon-rss:7083124kB, file-rss:0kB
#define NUM_ELEMENTS  400000000
#define Nil           NULL

struct Node {
  unsigned long data;
  struct Node *next;
};

struct Node *cons(struct Node *head, unsigned long data)
{
  struct Node *next = malloc(sizeof(struct Node));
  // assert(next != NULL);
  next->data = data;
  next->next = head;
  return next;
}

unsigned long add(struct Node *head)
{
  unsigned long sum = 0;
  while(head != Nil) {
    sum = sum + head->data;
    head = head->next;
  }
  return sum;
}

int main(int argc, char *argv[])
{
  struct Node *head = Nil;
  for(unsigned int i = 0; i < NUM_ELEMENTS; ++i) {
    head = cons(head, i << 1);
  }
  unsigned long sum = add(head);
  printf("Sum of all elements was: %lu\n", sum);
}
