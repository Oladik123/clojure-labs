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
  [function step steps-to-zero]
  (* step
     (/ (+ (function (* steps-to-zero step)) (function (* (dec steps-to-zero) step)))
        2)
     )
  )

(def memoized-part-sum
  (memoize
    (fn [function step-size steps-to-zero]
      (print ".")
      (+ (area function step-size steps-to-zero)
         (if (> steps-to-zero 0)
           (memoized-part-sum function step-size (dec steps-to-zero))
           0)
         )
      )
    )
  )

(defn integrate
  [function step-size argument]
  (print argument " ->> ")
  (memoized-part-sum function step-size (int (/ argument step-size))))

(defn integral
  [function step]
  (partial integrate function step))

(defn function
  [x]
  x
  )


(def stp 0.2342231234321)

(time ((integral function stp) 10))
(time ((integral function stp) 10))
(time ((integral function stp) 11))
(time ((integral function stp) 12))
(time ((integral function stp) 21))
(time ((integral function stp) 22))
(time ((integral function stp) 22))
(time ((integral function stp) 5))


