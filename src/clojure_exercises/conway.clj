(ns clojure-exercises.conway
  (:require [clojure.set :as s]))

(def neighbors
  (for [x [-1 0 1]
        y [-1 0 1]
        :when (not= [0 0] [x y])]
    [x y]))

(defn generate-neighbors
  "Pre: takes a position on the map
  Post: returns the positions of the possible neighbors"
  [[x y]]
  (for [[dx dy] neighbors]
    [(+ x dx) (+ y dy)]))

(defn apply-rules
  "Pre:takes, a map of key-values of position and the amount of neighbors, a new board, and a position
  Post: if the position is alive after applying the rules, returns new board with that pos, else new-board"
  [old-board new-board [pos neighbors]]
  (cond-> new-board
    (or (= 3 neighbors)
        (and (old-board pos)
             (= 2 neighbors))) (conj pos)))

(#{1 2 3} 2)

([1 2 3] 10)

(defn next-generation
  "Pre: takes a board 
  Post: returns the board after applying the rules to every position"
  [board]
  ; O(n) + O(8 * n) + O(8 * n) = O(17 * n)
  ;= O(k * n) = O(n) (because k is relatively small)
  (->> board
       (mapcat generate-neighbors) ; O(n)
       frequencies ; O(8 * n)
       (reduce ; O(8 * n)
        (partial apply-rules board) ; O(1)
        #{})))

(defn next-generations
  "Pre: takes a board and a number N
  Post: returns the board after N generations"
  [board n]
  (nth (iterate next-generation board) n))
