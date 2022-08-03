(ns clojure-exercises.conway)

(defn generate-neighbors
  "Pre: takes a position on the map
  Post: returns the positions of the possible neighbors"
  [[row column :as pos]]
(for [x (range (dec row) (+ 2 row))
                y (range (dec column) (+ 2 column))
                :when (not= pos [x y])]
            [x y]))


;; (defn apply-rules*
;;   "Pre: takes a board and a position
;;   Post: if the position is alive after applying the rules, returns a board with that pos, else none"
;;   [old-board new-board pos]
;;   (let [neighbors ((frequencies (mapcat generate-neighbors old-board)) pos)
;;         alive? (old-board pos)]
;;     (cond-> new-board
;;       (or (= neighbors 3)
;;           (and alive? (= 2 neighbors))) (conj pos))))

;; (defn next-generation
;;   "Pre: takes a board 
;;   Post: returns the board after applying the rules to every position"
;;   [board]
;;   (reduce (partial apply-rules board) #{} (mapcat generate-neighbors board)))


(defn apply-rules
  "Pre:takes, a map of key-values of position and the amount of neighbors, a new board, and a position
  Post: if the position is alive after applying the rules, returns new board with that pos, else new-board"
  [old-board new-board [pos neighbors]]
(cond-> new-board
      (or (= neighbors 3)
          (and (old-board pos) (= 2 neighbors))) (conj pos)))


(defn next-generation
  "Pre: takes a board 
  Post: returns the board after applying the rules to every position"
  [board]
  (reduce
  (partial apply-rules board)
   #{}
   (frequencies (mapcat generate-neighbors board))))


(defn next-generations
  "Pre: takes a board and a number N
  Post: returns the board after N generations"
  [board n]
  (nth (iterate next-generation board) n))

