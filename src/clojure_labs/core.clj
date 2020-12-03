(ns clojure-labs.core
  (:use [clojure-labs.utils]))

(defn integrale
  [function step]
  (fn [argument]
    (let []
      (* step
         (+ (/ (+ (function 0) (function argument)) 2)
            (apply + (map (fn [x] (function x)) (range 0 argument step))))))))

(defn area
  [function step steps-from-zero]
  (* step
     (/ (+ (function (* steps-from-zero step)) (function (* (inc steps-from-zero) step)))
        2)))

(defn seq-generator
  ([function step accumulator steps-from-zero]
   (print ".")
   (list (+ accumulator (area function step steps-from-zero))
         (inc steps-from-zero))))     

(def memoized-sum-seq
  (memoize
    (fn [function step]
      (iterate (partial apply seq-generator function step) (list 0 0)))))


(defn integrate
  [function step-size argument]
  (let [steps-to-zero (/ argument step-size)]
    (first (nth (memoized-sum-seq function step-size) steps-to-zero))))

(defn integral
  [function step]
  (partial integrate function step))

(defn function
  [x]
  x)

(def stp 1/3)

(println "naive " (time ((integrale function stp) 10)))
(println "with warmup " 
         (time ((integral function stp) 10)))
(println "same as previous " 
         (time ((integral function stp) 10)))
(println "same as previous " 
         (time ((integral function stp) 10)))
(println "plus 2 "
         (time ((integral function stp) 12)))

(println "minus 2 "
         (time ((integral function stp) 8)))
(println "plus 9 "
         (time ((integral function stp) 21)))
(println "plus 1 "
         (time ((integral function stp) 22)))
(println "same as previous "
         (time ((integral function stp) 22)))


