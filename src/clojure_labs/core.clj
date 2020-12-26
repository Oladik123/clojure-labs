(ns clojure-labs.core (:use [clojure-labs.constant]
                            [clojure-labs.variable]
                            [clojure-labs.utils]
                            [clojure-labs.operations]))

(declare dnf)

(def rollup-conj-rules
  ; a ^ b ^ ...  -->  a ^ (b ^ (...))
  (list
    [#(and (conj? %) (> (count (args %)) 2))
     #(dnf (conj (dnf (first (args %))) (apply conj (map dnf (rest (args %))))))])
  )

(def rollup-disj-rules
  ; a V b V ...  -->  a V (b V (...))
  (list
    [#(and (disj? %) (> (count (args %)) 2))
     #(dnf (disj (dnf (first (args %))) (apply disj (map dnf (rest (args %))))))]
    )
  )

(def absorption-rules
  (list
    ; a V a  -->  a
    ; a ^ a  -->  a
    [#(and (or (disj? %) (conj? %)) (variable-equals? (first (args %)) (second (args %))))
     #(dnf (first (args %)))]

    ; true V ...  -->  true
    [#(and (disj? %) (constant? (first (args %))) (= true (constant-value (first (args %)))))
     (fn [_] (constant true))]

    ; false V ...  -->  ...
    [#(and (disj? %) (constant? (first (args %))) (= false (constant-value (first (args %)))))
     #(dnf (second (args %)))]

    ; false ^ ...  -->  false
    [#(and (conj? %) (constant? (first (args %))) (= false (constant-value (first (args %)))))
     (fn [_] (constant false))]

    ; true ^ ...  -->  ...
    [#(and (conj? %) (constant? (first (args %))) (= true (constant-value (first (args %)))))
     #(dnf (second (args %)))]
    )
  )

(def demorgan-rules
  (list
    ; !(a ^ b ^ ...)  -->  !a V !b V ...
    [#(and (neg? %) (conj? (de-neg %)))`
        #(dnf (apply disj
                     (map (fn [expr] (dnf (neg expr)))
                          (args (de-neg %)))))]
    ; !(a V b V ...)  -->  !a ^ !b ^ ...
    [#(and (neg? %) (disj? (de-neg %)))
     #(dnf (apply conj
                  (map (fn [expr] (dnf (neg expr)))
                       (args (de-neg %)))))]
    ; !!(a)  -->  a
    [#(and (neg? %) (neg? (de-neg %)))
     #(dnf (de-neg (de-neg %)))]
    )
  )

(def distribution-rules
  (list
    ; a ^ (b V c)  -->  (a ^ b) V (a ^ c)
    [#(and (conj? %) (disj? (second (args %))))
     #((let [conj-arg (dnf (first (args %)))
             disj-lhs (dnf (first (second (args %))))
             disj-rhs (dnf (second (second (args %))))]
         (dnf (disj (conj disj-lhs conj-arg) (conj disj-rhs conj-arg)))))]

    ; (a V b) ^ c  -->  (a ^ c) V (b ^ c)
    [#(and (conj? %) (disj? (first (args %))))
     #((let [conj-arg (dnf (second (args %)))
             disj-lhs (dnf (first (first (args %))))
             disj-rhs (dnf (second (first (args %))))]
         (dnf (disj (conj disj-lhs conj-arg) (conj disj-rhs conj-arg)))))]))

(def const-rules
  (list
    [#(or (variable? %) (constant? %))
     identity]))

(def recur-rules
  (list
    [(fn [ignored] true)
     #(cons (operation %) (map dnf (args %)))]))

(def dnf-rules
  (concat
    rollup-conj-rules

    rollup-disj-rules

    absorption-rules

    demorgan-rules

    distribution-rules

    const-rules

    recur-rules
    ))

(defn dnf [expr]
  ((some (fn [rule]
           (if ((first rule) expr)
             (second rule)
             false))
         dnf-rules)
   expr))

