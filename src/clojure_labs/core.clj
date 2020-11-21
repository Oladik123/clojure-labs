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
     (/ (+ (function argument) (function (- argument step)))
        2)
     )
  )

(def memoized-part-sum
  (memoize
    (fn [function step argument]
      (print ".")
      (+ (area function step argument)
         (if (> argument 0)
           (memoized-part-sum function step (- argument step))
           0)
         )
      )
    )
  )

(defn integrate
  [function step argument]
  (memoized-part-sum function step argument))

(defn integral
  [function step]
  (partial integrate function step))


(defn function
  [x]
  x
  )



(println ((integrale function 1) 10))
(time ((integral function 1) 10))
(time ((integral function 1) 10))
(time ((integral function 1) 11))
(time ((integral function 1) 12))
(time ((integral function 1) 21))
(time ((integral function 1) 22))
(time ((integral function 1) 22))
(time ((integral function 1) 5))


