(ns clojure-labs.core
  (:use [clojure-labs.utils]))

(defn elementTailed
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
  [inputLetters targets targetsSize targetIndex]
  (let [target (first targets)
        restTargets (rest targets)]
    (if
      (< targetIndex targetsSize)
      (recur inputLetters
             (concat restTargets (elementTailed inputLetters target ()))
             targetsSize (+ targetIndex 1))
      targets
      )
    ))

(defn sequencesTailed
  [inputLetters n targets seqLength]
  (if (<= seqLength n)
    (recur
      inputLetters
      n
      (intermediate inputLetters targets (count targets) 0)
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


(println "element")
(println (elementTailed (list "a" "b" "c") "a" ()))

(println "intermediate")
(println (intermediate (list "a" "b" "c") (list "a" "b" "c") 3 0))

(println "sequences")
(println (sequences (list "a" "b" "c") 2))

(println "result")
(println (rightOrderedSequences (list "a" "b" "c" "d") 2))