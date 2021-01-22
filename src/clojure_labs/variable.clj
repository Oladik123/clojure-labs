(ns clojure-labs.variable)

(defn variable [name]
  {:pre [(keyword? name) (do (println "ctor" name) true)]}
  (list ::var name))

(defn variable? [expr]
  (= (first expr) ::var))

(defn variable-name [v]
  {:pre [(do (println "name" v) true) (variable? v)]}
  (second v))

(defn variable-equals? [v1 v2]
  (and
    (variable? v1)
    (variable? v2)
    (= (variable-name v1)
       (variable-name v2))))