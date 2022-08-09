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

(defn next-generation
  "Pre: takes a board 
  Post: returns the board after applying the rules to every position"
  [board]
  (->> board
       (mapcat generate-neighbors) 
       frequencies 
       (reduce 
        (partial apply-rules board) 
        #{})))

(defn next-generations
  "Pre: takes a board and a number N
  Post: returns the board after N generations"
  [board n]
  (nth (iterate next-generation board) n))
