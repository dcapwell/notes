# Notes

This is a site for me to keep my notes for random development stuff...

# Building

To build the site, build the docker image

```
docker build -t devel/grunt .
```

and in a new container, install the build dependencies

```
docker run -ti --rm -w $PWD -v $PWD:$PWD devel/grunt bash
```

```
npm install
```

Now you are ready to build the site

## Building site

```
grunt clean gitbook
```

## Publish site

To publish, you need to be able to commit to github, which may require you to mount your user in the container.  Here is how I do it.

```
docker run -ti --rm \
  -w $PWD -v $PWD:$PWD \
  -v $HOME:$HOME \
  -v /etc/passwd:/etc/passwd -v /etc/shadow:/etc/shadow -v /etc/group:/etc/group \
  --user $USER \
  devel/grunt bash
```

Once inside, you can publish

```
grunt publish
```
