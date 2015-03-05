# Moving Around

By default, bash uses `emacs` commands for moving around.  Since I am a `vim` person, this means I need to keep track of how to move around...

## Getting around within a line

* `Ctrl + a` - Jump to beginning of the line
* `Ctrol + e` - Jump to the end of the line
* `Meta + f` - Move forward a 'word'
* `Meta + b` - Move backward a 'word'

## Copy/Paste

* `Ctrl + k` - Cuts from cursor to end of line
* `Ctrl + y` - Pastes at cursor

## History

As your history builds up, you can use the following to repeat steps in the history

* `!n` - Run a command (n is a number from history) from history
* `!!` - Run the last command
* `!string` - Run the last command that starts with the string

If you don't want to run the command, but wish to run a subset (maybe you did `strace -- echo "hello"`), then you can do that by saying what you want with the `:` notation

* `!!:$` - For the last command, the last argument
  * `!$` - Shorthand for the above
* `!!:n` - For the last command, the nth argument

For a example

```bash
[/tmp]  $ mkdir foo
[/tmp]  $ cd !$
cd foo
[/tmp/foo]  $
```

### History / Editor

* `Ctrl + x  Ctrl + e` - When you scroll back in your history, you can add it to a editor, and when exiting it will run the command. Most used command on this page.
