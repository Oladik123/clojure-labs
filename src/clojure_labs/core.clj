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

(println "element")
(println (element (list "a" "b" "c") "a" ()))

(defn extended-by-letter-word
  "returns function that append provided letter to captured word and returns result"
  [word]
  (fn [letter]
    (cons (first letter) word))
  )

(defn collection-of-extended-by-letters-word
  "returns function that append word to every element to collection of letters and returns new collection"
  [letters]
  (fn [word]
    (map (extended-by-letter-word word) letters))
  )

(defn filtered-collection
  [predicate collection-supplier]
  (fn [& args]
    (filter predicate (apply collection-supplier args)))
  )


(defn mapped-collection
  [function collection-supplier]
  (fn [& args]
    (map function (apply collection-supplier args)))
  )

(println "new intermediate")
(println
  (apply
    concat
    (mapped-collection
      (filtered-collection
        (fn [word] (not= (first word) (first (rest word))))
        (collection-of-extended-by-letters-word (list "a" "b" "c"))
        )
      (list "a" "b" "c")
      )
    )
  )

(defn mapped-collection )
;;(println (filtered-words (map (partial word-started-with-letter "a") (list "a" "b" "c"))))


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
(println (rightOrderedSequences (list "a" "b" "c") 2))