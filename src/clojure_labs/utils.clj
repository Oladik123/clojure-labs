(ns clojure-labs.utils)

(defn- tailedMap
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
  (let [mapReduceFunction (fn [accumulator item]
                            (conj accumulator (function item)))]
    (apply list (reduce mapReduceFunction [] collection))))

