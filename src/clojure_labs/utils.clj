(ns clojure-labs.utils)

(defn- tailedMap
  [collection function result]
  (if (empty? collection)
    result
    (recur (rest collection)
           function
           (conj result
                 (function (first collection))))
    )
  )



(defn myMap
  [collection function]
  (let [mapReduceFunction
        (fn [accumulator item]
          (conj accumulator (function item)))]
    (apply list (reduce mapReduceFunction [] collection))))


(defn myFilter
  [collection predicate]
  (let [filterReduceFunction
        (fn [accumulator item]
          (if (predicate item)
            (conj accumulator item)
            accumulator
            ))]
    (apply list (reduce filterReduceFunction [] collection))
    )
  )