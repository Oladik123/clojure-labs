(ns clojure-labs.variable)

(defn variable [name]
  {:pre [(keyword? name)]}
  (list ::var name))

(defn variable? [expr]
  (= (first expr) ::var))

(defn variable-name [v]
  {:pre [(keyword? name)]}
  (second v))

(defn variable-equals? [v1 v2]
  (and
    (variable? v1)
    (variable? v2)
    (= (variable-name v1)
       (variable-name v2))))