(ns clojure-labs.utils)

(defn tailed
  [collection function result]
  (if (empty? collection)
    (apply list result)
    (recur (rest collection)
           function
           (conj result
                 (function (first collection))))
    )
  )

(defn myMap
  [collection function]
  (tailed collection function []))

