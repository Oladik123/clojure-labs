(ns clojure-labs.core-test
  (:require [clojure.test :refer :all]
            [clojure-labs.core :refer :all]
            [clojure-labs.operations :refer :all]
            [clojure-labs.constant :refer :all]
            [clojure-labs.variable :refer :all]
            [clojure-labs.utils :refer :all]))

(deftest basic-operations-test
  (testing "Basic operations"
    (is (constant? (constant true)))

    (is (variable? (variable :a)))
    ))

(deftest api-test
  (testing "API"
    (is (conj? (de-neg (neg (conj (variable :a) (variable :b))))))

    (is (= (args (neg (variable :a))) (list (variable :a))))

    (is (not (conj? (variable :a))))
    ))


(deftest a-test
  (testing "DNF"
    (is (= (dnf (constant true)) (constant true)))

    (is (= (dnf (neg (neg (variable :a)))) (variable :a)))

    (is (= (dnf (disj (constant true) (variable :a))) (constant true)))
    (is (= (dnf (disj (constant false) (variable :a))) (variable :a)))
    (is (= (dnf (conj (constant true) (variable :a))) (variable :a)))
    (is (= (dnf (conj (constant false) (variable :a))) (constant false)))

    (is (= (dnf (neg
                  (disj (variable :a) (variable :b)))
                )
           (conj
             (neg (variable :a))
             (neg (variable :b))
             )
           ))

    ; (a V b) -> c  -->  (!a ^ !b) V c
    (is (= (dnf (log-expression (impl (disj (variable :a) (variable :b)) (variable :c))))
           (log-expression (disj
                             (conj (neg (variable :a)) (neg (variable :b)))
                             (variable :c)))
           ))


    ;(a V b) -> (c V d -> x)  -->  (!a ^ !b) V (c) V (!d) V (x)
    (is (= (dnf (log-expression
                  (impl
                    (disj (variable :a) (variable :b))
                    (disj
                      (variable :c)
                      (impl (variable :d) (variable :x)))
                    )))
           (log-expression (dnf
                             (disj
                               (conj (neg (variable :a)) (neg (variable :b)))
                               (variable :c)
                               (neg (variable :d))
                               (variable :x))
                             ))))))


;(log-expression (dnf (impl (variable :a) (variable :b))))
;(log-expression (dnf (disj (impl (variable :a) (variable :b)) (conj (variable :c) (variable :d)))))
;(log-expression (dnf (disj (impl (variable :a) (variable :b)) (conj (variable :c) (variable :d)))))

(log-expression (dnf (conj (impl (variable :a) (variable :b)) (conj (variable :c) (variable :d)))))
(log-expression (dnf (disj (impl (variable :a) (variable :b)) (conj (variable :c) (variable :d)))))