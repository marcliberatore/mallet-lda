# mallet-lda

This project is a minimal Clojure wrapper over the LDA topic modeling
implementation from [MALLET], the MAchine Learning for LanguagE
Toolkit.

[MALLET]:http://mallet.cs.umass.edu/

## Installation

mallet-lda is alpha-quality software. There is no artifact at the moment;
use a
[checkout dependency](https://github.com/technomancy/leiningen/blob/stable/doc/TUTORIAL.md#checkout-dependencies)
if you want to try it out.

Feedback and pull requests welcome!

## Usage

```clojure
(ns example
  (:require [marcliberatore.mallet-lda :refer [make-instance-list lda]]))

...

(let [data [[1 ["a" "little" "lamb"]]  
            [2 ["row" "your" "boat"]]
            ...]
      instance-list (make-instance-list data)]
  (lda instance-list))
  
...

```

## TODO

Write an idiomatic wrapper over the return value of `(lda)`.

## License

Copyright © 2013 Marc Liberatore

Distributed under the Eclipse Public License, the same as Clojure.
