# Strace a Running Process

When a process is already running and you want to see what its doing, you can just tell `strace` the pid.  

For this example, lets attach to a chrome process

```bash
$ strace -p $(ps -ef | grep chrome | head -n2 | tail -n1 | awk '{print $2}')
strace: attach: ptrace(PTRACE_ATTACH, ...): Operation not permitted
Could not attach to process.  If your uid matches the uid of the target
process, check the setting of /proc/sys/kernel/yama/ptrace_scope, or try
again as the root user.  For more details, see /etc/sysctl.d/10-ptrace.conf
```

Chrome is running as the same user, so not sure why, lets follow the message

```bash
$ cat /proc/sys/kernel/yama/ptrace_scope
1
```

Googling this, I found [Ubuntu introduced a patch to disallow ptracing of non-child processes by non-root users](http://askubuntu.com/questions/41629/after-upgrade-gdb-wont-attach-to-process). Could set this to 0, but lets leave this as they are for now.

One more time, but with power!

```
sudo strace -p $(ps -ef | grep chrome | head -n2 | tail -n1 | awk '{print $2}')
Process 10233 attached
waitid(P_PID, 10234, ^CProcess 10233 detached
 <detached ...>
```

Ok, so nothing cool is going on with that process, but you should get the idea; launch a new process or monitor a existing one to see what its doing.
