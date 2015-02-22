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
