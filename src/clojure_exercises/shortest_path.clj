(ns backtracking_practice.shortest_path)

(def init-board [
 [ 1  1  1 ]
 [ 1  1  1 ]
 [ 0  0  1 ]])

(def rows (count init-board))
(def columns (count (nth init-board 0)))


(defn reached-end?
  "Pre: takes the current position and the destination postition
  Post: returns true if the current position matches the destination postition"
  [curr end]
  (= curr end))

(defn within-board?
  [[x y]]
  (and
   (<= 0 x (dec columns))
   (<= 0 y (dec rows))))

(defn unvisited?
  "Pre: takes a move and a collection of visited positions
  Post: returns true if the move is not in the collection. False otherwise"
  [move visited]
  (every? (partial not= move) visited))


(defn not-approaching-wall?
  [[x y] board]
  (not= 0 (nth (nth board y) x)))


(defn valid-move?
  "Pre: takes the board and a move
  Post: returns true if the move is within the board and has not been visited"
  [visited board move]
  (and
   (within-board? move)
   (unvisited? move visited)
   (not-approaching-wall? move board)))

(defn gen-valid-moves
  "Pre: takes a move, and the previously visited locations
  Post: returns the moves that are within the board and have not been visited"
  [[x y] visited board]
  (filter (partial valid-move? visited board)
          [[x (inc y)] [(inc x) y] [(dec x) y] [x (dec y)]]))


;; (defn can-move-towards-end?
;;   "Pre: takes an initital position, a final position, and the board
;;   Post: returns true if the we can move from initial position towards end position"
;;   [start end visited]
;;   (->> (gen-valid-moves start)
;;        seq))

(defn plot-path
  "Pre:
  Post: "
  [start-pos end-pos visited board]
  (println "Start:" start-pos)
  (println "Visited:" visited)
  (if (reached-end? start-pos end-pos)
    (println visited "Found a solution")
    (for [pos (gen-valid-moves start-pos visited board)]
      (plot-path pos end-pos (conj visited start-pos) board))))

(defn plot-path%
  "Pre: Takes a starting point, an end point, the visited positions, the board, and the current solutions
  Post: returns all possible paths from the starting position to end position"
  [start-pos end-pos visited board solution]
  (println "Start:" start-pos)
  (println "Visited:" visited)
  (if (reached-end? start-pos end-pos)
    visited
(for [pos (gen-valid-moves start-pos visited board)]
            (plot-path% pos end-pos (conj visited start-pos) board solution))))


(defn plot-path*
  "Pre: Takes a starting point, an end point, the visited positions, the board, and the current solutions
  Post: returns all possible paths from the starting position to end position"
  [start-pos end-pos visited board solution]
  (println "Start:" start-pos)
  (println "Visited:" visited)
  (if (reached-end? start-pos end-pos)
    (swap! solutions conj visited)
    (for [pos (gen-valid-moves start-pos visited board)]
      (plot-path* pos end-pos (conj visited start-pos) board solution))))

(defn worse-than-solution?
  "Pre: takes the current visited path and the current solution
  Post: Returns true if the current solution is better than the proposed one. False otherwise"
  [proposed-sol current-sol]
  (if (nil? current-sol)
    nil
  (>= (count proposed-sol) (count current-sol))))

(defn plot-path*
  "Pre: Takes a starting point, an end point, the visited positions, the board, and the current solutions
  Post: returns all possible paths from the starting position to end position"
  [start-pos end-pos visited board solution]
  (if (reached-end? start-pos end-pos)
    (reset! solutions visited)
    (for [pos (gen-valid-moves start-pos visited board)]
      (when-not (worse-than-solution? visited @solutions)
      (plot-path* pos end-pos (conj visited start-pos) board solution)))))

#_(defn plot-paths
  "Pre: takes the initial position, a final destination, all visited positions, and the board
  Post: returns all possible paths from start to end"
  [start end visited board]
(loop [start start end end visited visited board board sol []]
  (if (reached-end? start end)
    (swap! solutions conj visited)
    (for [pos (gen-valid-moves start visited board)]
      (recur pos end (conj visited start) board sol)))))


(defn shortest-path
  "Pre: Takes a initial position, and a final destination
  Post: Returns the shortest amounts of steps needed to reach destination"
  [start-pos end-pos board]
(plot-path start-pos end-pos [] board))

"Try making a reducible function that takes the neighbours, and passes plot-path. if the path has a route that is longer than the best path, then stop checking this path.
"


(def solutions (atom nil))
(def A (shortest-path [0 0] [2 0] init-board))
(plot-path* [0 0] [2 2] [] init-board [])
@solutions
