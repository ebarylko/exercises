(ns clojure-exercises.powerset)

(def input [1 2 3])

(defn gen-powerset
  "Pre: takes a set of integers and a possible subset
  Post: returns all possible subsets"
  [coll pos]
  (if (empty? coll)
    pos
(conj pos (gen-powerset (rest coll) (conj pos (first coll)))
      (gen-powerset (rest coll) pos))))

(defn possible-subsets
  "Pre: Takes a set of integers
  Post: returns all possible subsets of the set"
  [coll]
  (gen-powerset coll []))

"Idea: for every element, recur with picking the element and with not picking the element
Since every choice is to include or not include, you will generate all possibilities"

"Loop: will have current set so far and remaining elements. if no remaining elements left, will add it to the solutions. if not, I will add to the stack the set with and without the first remaining element, and discard the remaining element I just used"

(gen-powerset [1] [])
(gen-powerset [1 2] [])
(count (gen-powerset [1 2 3] []))
