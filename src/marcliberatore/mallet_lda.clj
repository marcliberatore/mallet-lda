;; ## Overview
;;
;; This namespace provides functions that thinly wrap the MALLET
;; toolkit's LDA topic modeling. Notably, it does not currently wrap
;; any of the tokenizing or other "pipe" based features of MALLET.
;;
;; Topic models are built on sets of documents, and MALLET requires
;; that documents be given an arbitrary id. A (document, id) pair is
;; called an instance, and the topic modeling algorithms run on
;; instance lists. To create a topic model, you must first make an
;; instance list.

(ns marcliberatore.mallet-lda
  (:import [cc.mallet.types Alphabet FeatureSequence Instance InstanceList])
  (:import cc.mallet.topics.ParallelTopicModel))

;; ## Instance and InstanceList Wrappers 
;;
;; All instances in an InstanceList must share a common alphabet,
;; hence the passing around of the Alphabet object in the functions
;; below.

(defn- make-feature-sequence
  "Instantiate a FeatureSequence with a collection of tokens,
  using the provided Alphabet to handle symbols."
  ([tokens alphabet]
     (let [feature-sequence (FeatureSequence. alphabet)]
       (doseq [token tokens]
         (.add feature-sequence token))
       feature-sequence)))

(defn- make-instance
  "Instantiate an Instance with an id and token collection,
  using the provided Alphabet to handle symbols."
  ([id tokens alphabet]
     (let [feature-sequence (make-feature-sequence tokens alphabet)]
       (Instance. feature-sequence nil id nil))))

(defn make-instance-list
  "Make an InstanceList using a collection of documents. A document is
  a pairing of a document id and the collection of tokens for that
  document. Document ids must be unique, and tokens must be strings."
  ([documents]
     (let [alphabet (Alphabet.)
           instance-list (InstanceList. alphabet nil)]
       (doseq [[id tokens] documents]
         (.add instance-list (make-instance id tokens alphabet)))
       instance-list)))

;; ## Topic Model Wrapper
;;
;; The invocation sequence to create a topic model will
;; look something like:
;;
;;     (let [data [[1 ["a" "little" "lamb"]]
;;                 [2 ["row" "your" "boat"]]]
;;           instance-list (make-instance-list data)]
;;       (lda instance-list))
;;
;; though you probably will load your data from elsewhere.

(defn lda
  "Return a topic model (ParallelTopicModel) on the given
  instance-list, using the optional parameters if specified. The
  default parameters will run fairly quickly, but will not return
  high-quality topics."
  ([instance-list &
    {:keys [num-topics num-iter optimize-interval optimize-burn-in
            num-threads random-seed]
     :or {num-topics 10
          num-iter 100
          optimize-interval 10
          optimize-burn-in 20
          num-threads (.availableProcessors (Runtime/getRuntime))
          random-seed -1 }}]
     (doto (ParallelTopicModel. num-topics)    
       (.addInstances instance-list)
       (.setNumIterations num-iter)
       (.setOptimizeInterval optimize-interval)
       (.setBurninPeriod optimize-burn-in)
       (.setNumThreads num-threads)
       (.setRandomSeed random-seed)
       .estimate)))
