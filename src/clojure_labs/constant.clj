(ns clojure-labs.constant)

(defn constant [val]
  {:pre [(boolean? val)]}
  (list ::const val))

(defn constant? [expr]
  (= (first expr) ::const))

(defn constant-value [expr]
  {:pre [(constant? expr)]}
  (second expr))