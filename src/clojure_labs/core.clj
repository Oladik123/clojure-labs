(ns clojure-labs.core
  (:use [clojure-labs.utils]))


(defn integral
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


(defn function
  [x]
  (* (- x (/ x 2)) (- x (/ x 2)))
  )

(println ((integral function 1000) 100000))