(ns clojure-labs.core
  (:use [clojure-labs.utils]))

(defn extended-by-letter-word
  [word]
  (fn [letter]
    (cons (first letter) word))
  )

(defn collection-of-extended-by-letters-word
  [letters]
  (fn [word]
    (map (extended-by-letter-word word) letters))
  )

(defn filtered-collection
  [predicate collection-supplier]
  (fn [& args]
    (filter predicate (apply collection-supplier args)))
  )

(defn words-set
  [letters length]
  (map
    reverse
    (loop [current-length 1
           words letters]
      (if (< current-length length)
        (recur (inc current-length)
               (apply
                 concat
                 (map
                   (filtered-collection
                     (fn [word] (not= (first word) (first (rest word))))
                     (collection-of-extended-by-letters-word letters)
                     )
                   words
                   )
                 ))
        words
        )
      )
    )
  )

(println "result")
(println (words-set (list "a" "b" "c") 3))
