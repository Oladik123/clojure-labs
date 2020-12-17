(ns clojure-labs.core (:use [clojure-labs.utils]))

(defn break-coll
  [part-size coll]
  (take-while #(not (empty? %))
              (lazy-seq
                (cons
                  (take part-size coll)
                  (break-coll part-size (drop part-size coll))))))

(defn parallel-filter
  [predicate part-size coll]
  (->> coll
       (break-coll part-size)
       (map #(future (doall (filter predicate %))))
       (doall)
       (map deref)
       (apply concat)))

(defn parallel-filter-by-threads
  [threads predicate coll]
  (parallel-filter predicate (/ (count coll) threads) coll))

(def naturals (take 100 (iterate inc 0)))
(def naturals2 (take 100 (iterate inc 0)))

(defn heavy-even? [x] (do (Thread/sleep 100) (even? x)))

(def configured-parallel-filter (partial parallel-filter-by-threads 4))

(time (doall (filter heavy-even? naturals)))
(time (configured-parallel-filter heavy-even? naturals2))