;; ## Overview
;;
;; This namespace provides functions to load external files into the
;; format expected by `mallet-lda/make-instance`. It also loads a
;; small collection of documents for testing.

(ns marcliberatore.mallet-lda.misc
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn- tokenize [x]
  "Returns a naive tokenization of contents of x, which should be
  coerceable to a java.io.Reader as per clojure.java.io."  
  (-> x
      slurp
      str/lower-case
      (str/replace #"[^\s\w]" "")
      (str/split #"\s+")))

(defn- make-document [x]
  "Return a document representing x, per mallet-lda/make-instance-list:
  [id vector-of-tokens]."
  [(str x) (tokenize x)])

(defn make-documents [directory]
  "Return a sequence of documents appropriate for use in
  mallet-lda/make-instance-list, by reading and tokenizing all files
  in directory."
  (for [file (.listFiles (io/file directory))]
    (make-document file)))

(defn load-sample-documents []
  "Returns sample data for use with mallet-lda/make-instance-list."
  (make-documents (io/resource "sample-data/en")))
