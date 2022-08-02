(ns clojure-exercises.conway-test
  (:require [clojure-exercises.conway :as sut]
            [clojure.test :as t :refer [deftest is]]))

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
