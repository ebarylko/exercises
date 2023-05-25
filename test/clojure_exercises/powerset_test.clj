(ns clojure-exercises.powerset-test
  (:require [clojure-exercises.powerset :as sut]
            [clojure.test :as t]))

(def input #{ 1 2 3})

(t/deftest gen-next-subsets-test
  (t/is (= [
            [[2 3] [1]]
            [[2 3] []]]
           (sut/gen-next-subsets [1 2 3] []))))

(t/deftest gen-powerset-test
  (t/is (= #{[] [1] [1 2] [2 3] [1 2 3] [2] [3] [1 3]}
           (sut/gen-powerset [1 2 3] [] #{}))))

(t/deftest possible-subsets-test
  (t/is (= #{[] [1] [2] [1 2]}
           (sut/possible-subsets [1 2])))
  (t/is (= #{[1] [2] [3] [4] [1 2] [1 3] [1 4] [2 3] [2 4] [3 4] [1 2 3] [2 3 4] [1 3 4] [1 2 4] [1 2 3 4] []}
         (sut/possible-subsets [1 2 3 4]))))
