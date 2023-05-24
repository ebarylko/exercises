(ns backtracking_practice.shortest_path_test
  (:require [backtracking_practice.shortest_path :as sut]
            [clojure.test :as t]))


(sut/reached-end? 1 3)

(t/deftest shortest-path-test
  (t/is (= [[0 1]] (sut/shortest-path [0 0] [0 1]))))
