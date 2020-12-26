(ns clojure-labs.utils (:use [clojure-labs.constant]
                             [clojure-labs.variable]
                             [clojure-labs.operations]))
(defn stringify [expr]
  (cond
    (variable? expr) (name (first (args expr)))
    (constant? expr) (str (first (args expr)))
    (disj? expr) (str "(" (reduce #(str %1 " V " (stringify %2)) (stringify (first (args expr))) (rest (args expr))) ")")
    (conj? expr) (str "(" (reduce #(str %1 " ^ " (stringify %2)) (stringify (first (args expr))) (rest (args expr))) ")")
    (neg? expr) (str "!" (stringify (de-neg expr)))
    :else ( "?" )))

(defn log-expression [expr]
  (do
    (println (stringify expr))
    expr
    )
  )