(ns clojure-exercises.powerset-test
  (:require [clojure-exercises.powerset :as sut]
            [clojure.test :as t]))


(t/deftest possible-subsets-test
  (t/is (= [[] [1] [1 2] [2 3] [1 2 3] [2] [3] [1 3]]
           (sut/possible-subsets [1 2 3]))))
