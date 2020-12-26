(ns clojure-labs.operations
  (:use [clojure-labs.constant]))

(defn- constants-first [expressions]
  "partial sort make constants come first"
  (let [constants (filter constant? expressions)
        other-expressions (remove constant? expressions)]
    (concat constants other-expressions)))

(defn args [expr]
  (rest expr))

(defn operation [expr]
  (first expr))

(defn conj [expr & rest]
  (let [constants-first (constants-first (cons expr rest))]
    (cons ::conj constants-first)
    ))

(defn conj? [expr]
  (= ::conj (first expr)))

(defn disj [expr & rest]
  (let [constants-first (constants-first (cons expr rest))]
    (cons ::disj constants-first)
    ))

(defn disj? [expr]
  (= ::disj (first expr)))

(defn neg [expr]
  (cons ::neg (list expr)))

(defn neg? [expr]
  (= ::neg (first expr)))

(defn de-neg [expr]
  {:pre [(neg? expr)]}
  (first (args expr)))

(defn impl [lhs rhs]
  (disj (neg lhs) rhs))

