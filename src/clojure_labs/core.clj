(ns clojure-labs.core
  (:use [clojure-labs.utils]))

(defn integrale
  [function iterations]
  (fn [argument]
    (let [step (/ argument iterations)]
      (* step
         (+ (/ (+ (function 0) (function argument)) 2)
            (apply + (map (fn [x] (function x)) (range 0 argument step)))
            )
         )
      )
    )
  )





(defn integrate
  [function iterations argument]
  ())

(defn integral
  [function iterations]
  (partial integrate function iterations))


(defn function
  [x]
  (* (- x (/ x 2)) (- x (/ x 2)))
  )

(println ((integral function 10000) 100))