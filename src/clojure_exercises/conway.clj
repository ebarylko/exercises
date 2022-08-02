(ns clojure-exercises.conway)


;; Any live cell with fewer than two live neighbours dies (referred to as underpopulation or exposure[1]).
;; Any live cell with more than three live neighbours dies (referred to as overpopulation or overcrowding).
;; Any live cell with two or three live neighbours lives, unchanged, to the next generation.
;; Any dead cell with exactly three live neighbours will come to life.
; row is horizontal, column is vertical

(defn make-empty-board
  "Pre: takes nothing
  Post: returns the starting board"
  []
  (vec (repeat 5 (vec (repeat 5 [])))))
(defn change-pos
  "Pre: takes a position on the board, the board, and a value
  Post: changes the position to the value"
  [ board [row column] value]
  (assoc-in board [row column] value))

(defn add-cell
  "Pre: takes a board and a position
  Post: returns the board with the position as a live cell"
  [board pos]
  (conj board pos))

(defn kill-cell
  "Pre: takes a board and a position
  Post: returns the board with the position as a dead cell"
  [board pos]
  (disj board pos))


(def starting-board #{ [1 2] [2 2] [3 2]})

(defn generate-neighbors
  "Pre: takes a position on the map
  Post: returns the positions of the possible neighbors"
  [[row column :as pos]]
(for [x (range (dec row) (+ 2 row))
                y (range (dec column) (+ 2 column))
                :when (not= pos [x y])]
            [x y]))

(generate-neighbors [2 2])
(map generate-neighbors #{[1 2] [0 0]})

(defn count-neighbours
  "Pre: takes a board and a position on the board
  Post: returns the number of neighbours from that position"
  [board pos]
  (->> pos
       generate-neighbors
       (filter board)
       count))



;; (defn count-neighbours
;;   "Pre: takes a board and a position on the board
;;   Post: returns the number of neighbours from that position"
;;   [board pos]
;;   (count (remove empty? (map
;;                            (partial apply #(nth (nth board %1 []) %2 []))
;;                            (generate-neighbors pos)))))

(defn change-board
  "Pre: takes a board and a position
  Post: returns the board after applying the rules for that position"
  [board pos]
  (let [neighbors (count-neighbours board pos)
        alive? (board pos)
        right-neighbors (> 4 neighbors 1)]
  (cond
    (and  alive? right-neighbors) board
    (and alive? (or (> neighbors 3) (< neighbors 2))) (kill-cell board pos)
    (and (not alive?) (= neighbors 3)) (add-cell board pos)
    :else board)))

(defn apply-rules
  "Pre: takes a board and a position
  Post: if the position is alive after applying the rules, returns a board with that pos, else none"
  [old-board new-board pos]
  (let [neighbors (count-neighbours old-board pos)
        alive? (old-board pos)
        right-neighbors (> 4 neighbors 1)]
    (cond-> new-board
      (and  alive? right-neighbors) (conj pos)
      (and (not alive?) (= neighbors 3)) (conj pos))))


(defn next-generation
  "Pre: takes a board 
  Post: returns the board after applying the rules to every position"
  [board]
  (reduce (partial apply-rules board) #{} (mapcat generate-neighbors board)))

(defn next-generations
  "Pre: takes a board and a number N
  Post: returns the board after N generations"
  [board n]
  (nth (iterate next-generation board) n))
