(ns clojure-labs.core
  (:use [clojure-labs.utils]))

(defn integrale
  [function step]
  (fn [argument]
    (let []
      (* step
         (+ (/ (+ (function 0) (function argument)) 2)
            (apply + (map (fn [x] (function x)) (range 0 argument step)))
            )
         )
      )
    )
  )

(defn area
  [function step argument]
  (* step
     (/ (+ (function argument) (function (+ argument step)))
        2)))


(defn seq-generator
  ([function step accumulator n]
   (list (+ accumulator (area function step n))
         (+ n step))))

(def memoized-sum-seq
  (memoize
    (fn [function step]
      (iterate (partial apply seq-generator function step) (list 0 0))))
  )

(defn integrate
  [function step argument]
  (first (nth (memoized-sum-seq function step) (/ argument step))))

(defn integral
  [function step]
  (partial integrate function step))


(defn function
  [x]
  x
  )


(println "naive " (time ((integrale function 0.1) 10)))
(println "with warmup " (time ((integral function 0.1) 10)))
(println "same as previous " (time ((integral function 0.1) 10)))
(println "same as previous " (time ((integral function 0.1) 10)))
(println "plus 2 " (time ((integral function 0.1) 12)))
(println "plus 9 " (time ((integral function 0.1) 21)))
(println "plus 1 " (time ((integral function 0.1) 22)))
(println "same as previous " (time ((integral function 0.1) 22)))


