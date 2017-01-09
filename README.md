# convert-clj
## Code Challenge: String Conversion

Parse the given string of words, whitespace, and parentheses. Output each word on a separate line,
with hyphen indentation corresponding to the parenthesis nesting level.

Bonus problem:
Output the lines in word-sorted order.

Author: Tom Cooper
Date: 2017-01-08

This program is written in [Clojure](http://clojure.org), a modern LISP language that
is highly productive, concise, functional, and scalable.

## Prerequisites

This is a leiningen clojure project. [Install leiningen](http://leiningen.org/#install) if you don't already have it,
and then clone this project. After installing leingingen, run 

    $ lein deps
    
to pull in and connect to all the maven components you'll need.

## Building

Although you can build a complete jar file to run or embed this in
any JVM environment if you like, you can also just run it, and leiningen will
build what is necessary on the fly.

## Running the solution

Run the executable jar file. To turn off assertion checking,
change the line in the project.clj file that controls assertion checking to false.

```
:global-vars {*assert* false}
```

Then run the application using leiningen.
```
lein run
```

This demonstrates the given example. Test it on your own strings by giving it
a string argument.

```
lein run "(a,b(foo(((deep stuff)))), c)"
```

Output:

```
Original order:
a
b
- foo
---- deep
---- stuff
c

Sorted order:
a
b
c
---- deep
- foo
---- stuff
```
