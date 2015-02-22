# Strace

`strace` is a linux utility for exploring system calls done. This is a very useful tool for debugging and exploring how programs work.  Have you ever forgotten the name of the hidden file that `curl` uses?  Want to know how `pkill` is implemented?  `strace` will let you see all the system calls (such as `open`) done by these programs so you get insight into whats going on without needing to look at the code.
