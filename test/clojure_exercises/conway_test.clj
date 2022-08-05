(ns clojure-exercises.conway-test
  (:require [clojure-exercises.conway :as sut]
            [clojure.test :as t :refer [deftest is]]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]))

(defspec sort-is-idempotent 
  (prop/for-all [v (gen/vector gen/small-integer)]
    (= (sort v) (sort (sort v)))))

(def blinkerh-gen
  "Generates 3 horizontal contiguous cells"
  (gen/fmap
   (fn [[x y]]
     #{[x (dec y)] [x y] [x (inc y)]})
   (gen/tuple gen/small-integer gen/small-integer)))

(def even-pos-num-gen
  (gen/such-that
   pos?
   (gen/fmap (partial * 2) gen/nat)))


(def toad-gen
  (gen/fmap
   (fn [[x y]]
     #{[x y] [x (inc y)] [x (+ 2 y)] [(inc x) (dec y)] [(inc x) y] [(inc x) (inc y)]})
   (gen/tuple gen/small-integer gen/small-integer)))


(def oscillating-gen
  (gen/one-of [toad-gen blinkerh-gen]))

(defspec oscillating-evolves-to-self-after-even-iterations
  (prop/for-all [pattern oscillating-gen
                 iterations even-pos-num-gen]
                (= pattern (sut/next-generations pattern iterations))))

(def blinker #{[1 2] [2 2] [3 2]})

(deftest blinker-pattern
  (is (= blinker (sut/next-generation (sut/next-generation blinker)))))

(def toad #{[1 2] [1 3] [1 4] [2 3] [2 2] [2 1]})

(deftest toad-pattern
  (is (= toad (sut/next-generations toad 100))))

(def glider #{[0 1] [1 2] [2 0] [2 1] [2 2]})

(def glider-4 #{[1 2] [2 3] [3 1] [3 2] [3 3]})

(deftest glider-pattern
  (is (= glider-4 (sut/next-generations glider 4))))
