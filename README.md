# mallet-lda

This project is a minimal Clojure wrapper over the LDA topic modeling
implementation from [MALLET], the MAchine Learning for LanguagE
Toolkit.

[MALLET]:http://mallet.cs.umass.edu/

## Installation

The latest stable release is 0.1.0.

Add this `:dependency` to your Leiningen `project.clj`:

```clojure
["marcliberatore.mallet-lda" "0.1.0"]
```

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
