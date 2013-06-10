(ns picross.model
  (:require [noir.session :as session]))

(def empty-board [["-" "-" "-" "-" "-" "-" "-" "-" "-" "-"]
                  ["-" "-" "-" "-" "-" "-" "-" "-" "-" "-"]
                  ["-" "-" "-" "-" "-" "-" "-" "-" "-" "-"]
                  ["-" "-" "-" "-" "-" "-" "-" "-" "-" "-"]
                  ["-" "-" "-" "-" "-" "-" "-" "-" "-" "-"]
                  ["-" "-" "-" "-" "-" "-" "-" "-" "-" "-"]
                  ["-" "-" "-" "-" "-" "-" "-" "-" "-" "-"]
                  ["-" "-" "-" "-" "-" "-" "-" "-" "-" "-"]
                  ["-" "-" "-" "-" "-" "-" "-" "-" "-" "-"]
                  ["-" "-" "-" "-" "-" "-" "-" "-" "-" "-"]
                  ])

(def solution-board [["fill" "fill" "-" "fill" "fill" "fill" "-" "-" "-" "-"]
                     ["fill" "-" "fill" "-" "-" "-" "fill" "-" "-" "-"]
                     ["-" "fill" "fill" "-" "-" "fill" "fill" "fill" "-" "-"]
                     ["fill" "fill" "-" "-" "-" "-" "-" "-" "fill" "-"]
                     ["fill" "fill" "-" "-" "-" "-" "fill" "fill" "fill" "-"]
                     ["fill" "fill" "fill" "-" "-" "fill" "fill" "fill" "fill" "-"]
                     ["-" "fill" "fill" "fill" "-" "fill" "fill" "-" "fill" "-"]
                     ["-" "-" "fill" "fill" "fill" "fill" "-" "-" "-" "fill"]
                     ["-" "-" "-" "fill" "fill" "fill" "fill" "-" "fill" "fill"]
                     ["-" "-" "-" "-" "-" "-" "fill" "fill" "fill" "-"]
                     ])

(def solution-board-numbers-horizontal [
                                 [\- 2 3]
                                 [1 1 1]
                                 [\- 2 3]
                                 [\- 2 1]
                                 [\- 2 3]
                                 [\- 3 4]
                                 [3 2 1]
                                 [\- 4 1]
                                 [\- 4 2]
                                 [\- \- 3]
                                 ])

(def solution-board-numbers-vertical [
                                 [\- \- \- \- \- 1 2 1 \- \-]
                                 [2 1 2 1 1 1 3 2 4 \-]
                                 [3 5 3 3 2 4 2 1 2 2]
                                 ])


(defn get-solution-hor [] solution-board-numbers-horizontal)

(defn get-solution-ver [] solution-board-numbers-vertical)

(def init-state {:board empty-board :solution solution-board :action (str "fill")})

(defn reset-game! []
  (session/put! :game-state init-state))


(defn get-board []
  (:board (session/get :game-state)))

(defn get-solution []
  (:solution (session/get :game-state)))

(defn get-board-cell 
  ([row col]
    (get-board-cell (get-board) row col))
  ([board row col]
    (get-in board [row col])))

(defn get-action []
  (:action (session/get :game-state)))

(defn new-action! 
  [action old-state]
  (if (= 1 1)
    {:board (:board old-state)
     :solution (:solution old-state)
     :action action} old-state))

(defn put-action! [action]
  [action]
  (session/swap! (fn [session-map]
                   (assoc session-map :game-state 
                          (new-action! action (:game-state session-map))))))

(defn winner?
  ([] (winner? (get-board) (get-solution)))
  ([cur-board] (winner? cur-board (get-solution)))
  ([cur-board solution-board]
    (if (= 
          (clojure.string/replace cur-board #"block" "-")
          (clojure.string/replace solution-board #"" "")
          ) true false)))

(defn new-state! [row col old-state]
  (if (not (winner? (:board old-state)))
    {:board (assoc-in (:board old-state) [row col] (:action old-state))
     :solution (:solution old-state)
     :action (:action old-state)}
    old-state))

(defn play! 
  [row col]
  (session/swap! (fn [session-map]
                   (assoc session-map :game-state 
                          (new-state! row col (:game-state session-map))))))