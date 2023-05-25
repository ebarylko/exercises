(ns clojure-exercises.powerset)

(def input [1 2 3])

(defn gen-next-subsets
  "Pre: takes a set and a possible subset
  Post: returns the new subsets and the modified set"
  [coll pos]
  (let [modified-set (rest coll)]
    [[modified-set (conj pos (first coll))]
     [modified-set pos]]))

(defn gen-powerset
  "Pre: takes a set of integers and a possible subset
  Post: returns all possible subsets"
  [coll pos sols]
  (if (empty? coll)
    (conj sols pos)
    (reduce (fn [sub-s [coll pos]]
              (gen-powerset coll pos sub-s))
            sols
            (gen-next-subsets coll pos) )))

(defn possible-subsets
  "Pre: Takes a set of integers
  Post: returns all possible subsets of the set"
  [coll]
  (gen-powerset coll [] #{}))

