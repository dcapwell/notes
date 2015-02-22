# Perf Install

Each kernel has its own `perf` (its part of the kernel source).  So in order to install it, you need to know what kernel you are using (seems that rhel doesnt care, just say install `perf`).

## Ubuntu

For Ubuntu, you need to know what version of the kernel you are using and install `linux-tools` for that version.  We can get this from `uname` so the install is simple.

```bash
sudo apt-get install -y linux-tools-$(uname -f)
```

## RHEL

Seems that rhel doesn't require you to know your kernel, and this is most likely because rhel doesn't want you to change your kernel.  The point of rhel is that the os and packages are managed and tested together, so you go through Red Hat for this.  This means that `perf` is very simple to install on rhel.

```bash
sudo yum install -y perf
```
