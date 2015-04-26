* File system isolation
* memory limiting
* I/O limits
* CPU quotes
* network isolation
* some security

# Install

```
apt-get install lxc
```

# First Container

```
lxc-create \
  -t ubuntu \# type of container
  -n myfirstcontainer # name of the container
```

This downloads the bare OS for the type

Now list and attach to container

```
lxc-ls --fancy
```

Nothing is running, so start it

```
lxc-start -n myfirstcontainer
```

This starts the container and lets you log into it

Whats interesting is that when you do this, you will see a lot of things running. This is because this image
is a basic ubuntu setup, so it will run things ubuntu normally would.

Now, how do you get out?

```
sudo init 0
```

If you try to `ctrl c`, or exit, it will just put you into the login prompt.  To really get out, you need to kill the container, so just power it off


This is cool and all, but what if we don't want to interact with it?

```
lxc-start -n myfirstcontainer -d
```

This runs the container as a daemon

Since this type (ubuntu) starts up ssh by default, you can just login through the IP


```
lxc-ls --fancy
```

Use the IP from there and login (this type's user/pass are ubuntu/ubuntu)

Now, how do we stop this running container?

```
lxc-stop -n myfirstcontainer
lxc-ls --fancy
```

If you don't want to stop the container, but tempory freeze the processes, you can do

```
lxc-freeze -n myfirstcontainer
```

This is like doing `ctrl z` on a process, but for everything in the namespace

If you want to stop and kill

```
lxc-stop -n myfirstcontainer -k
```

This will stop the container, and send `kill` to the container (if you remove -k, it just stops the container)

If you want to "attach" to the container

```
lxc-attach -n mycontainer
```

This will act like running a `bash` process in the same namespace

# Configuring a container

There are templates lxc provides that really defines how to setup the container.  To view the current templates

```
ls /usr/share/lxc/templates
```

Now, lets look at a running container

```
$ cd /var/lib/lxc/myfirstcontainer
$ ls
rootfs fstab config
```

rootfs is the file system the container users
fstab are all the mounts for the container
config is how lxc manages the container

to read the man page for the config

```
man 5 lxc.container.conf
```
