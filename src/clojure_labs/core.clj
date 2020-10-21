(ns clojure-labs.core
  (:use [clojure-labs.utils]))

(defn element
  [inputLetters target accumulator]
  (if (empty? inputLetters)
    accumulator
    (let [letter (first (first inputLetters))]
      (if (= (first target) letter)
        (recur (rest inputLetters) target accumulator)
        (recur (rest inputLetters) target (concat accumulator (list (cons letter target))))
        )
      )
    )
  )

(defn intermediate
  [inputLetters targets accumulator]
  (if
    (empty? targets)
    accumulator
    (recur inputLetters (rest targets) (concat accumulator (element inputLetters (first targets) ())))
    )
  )

(defn sequencesTailed
  [inputLetters n targets seqLength]
  (if (<= seqLength n)
    (recur
      inputLetters
      n
      (intermediate inputLetters targets ())
      (+ seqLength 1))
    targets
    )
  )

(defn sequences
  [inputLetters n]
  (sequencesTailed inputLetters n inputLetters 2))

(defn rightOrderedSequences
  [letters n]
  (myMap (sequences letters n) reverse))

(println "intermediate")
(println (intermediate (list "a" "b" "c") (list "a" "b" "c") ()))

(println "sequences")
(println (sequences (list "a" "b" "c") 2))

(println "result")
(println (rightOrderedSequences (list "a" "b" "c" "d") 2))