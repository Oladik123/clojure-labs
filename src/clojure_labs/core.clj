(ns clojure-labs.core (:use [clojure-labs.utils]))

(defn element
  [inputLetters target letterIndex]
  (if (>= letterIndex (count inputLetters))
    ()
    (if (not= (first target) (first (nth inputLetters letterIndex)))
      (concat
        (list (cons (first (nth inputLetters letterIndex)) target))
        (element inputLetters target (+ letterIndex 1))
        )
      (element inputLetters target (+ letterIndex 1))
      )
    )
  )

(defn intermediate
  [inputLetters targets targetsSize targetIndex]
  (let [target  (first targets)
        restTargets (rest targets)]
    ;(println inputLetters targets targetsSize targetIndex)
    (if
      (< targetIndex targetsSize)
      (intermediate inputLetters
                    (concat restTargets (element inputLetters target 0))
                    targetsSize (+ targetIndex 1))
      targets
      )
    ))

(defn sequencesTailed
  [inputLetters n targets seqLength]
  (if (<= seqLength n)
    (sequencesTailed
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
  (map (sequences letters n) reverse))


(println "element")
(println (element (list "a" "b" "c") "a" 0))

(println "intermediate")
(println (intermediate (list "a" "b" "c") (list "a" "b" "c") 3 0))

(println "sequences")
(println (sequences (list "a" "b" "c") 2) )

(println (rightOrderedSequences (list "a" "b" "c") 2))