(ns clojure-labs.core
  (:use [clojure-labs.utils]))

(defn split
  [part-size coll]
  (take-while #(not (empty? %))
              (lazy-seq
                (cons
                  (take part-size coll)
                  (split part-size (drop part-size coll))))))

(defn heavy-even? [x] (do (Thread/sleep 10) true))

(defn lazy-parallel-filter [sublist-size thread-count predicate coll]
  (->> (split (* sublist-size thread-count) coll)
       (map (fn [list] (->> (split sublist-size list)
                            (map #(future (do (println "running") (doall (filter predicate %)))))
                            ;(map #(future (doall (filter predicate %))))
                            (doall)
                            (map deref)
                            (doall))))
       (apply concat)
       (apply concat)))

(def configured-lazy-filter (partial lazy-parallel-filter 100 4))

(time (doall (take 1000 (configured-lazy-filter heavy-even? (iterate inc -10)))))
(time (doall (take 1000 (filter heavy-even? (iterate inc 0)))))