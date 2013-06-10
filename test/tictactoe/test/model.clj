(ns tictactoe.test.model
  (:use tictactoe.model)
  (:use clojure.test)
  (:require [tictactoe.test.testdata :as td]))

(deftest get-board-cell-test
  (let [testboard [["fill" \- \- \- \- \- \- \- \- \-]
                   [\- \- \- \- \- \- \- \- \- \-]
                   [\- \- \- \- \- \- \- \- \- \-]
                   [\- \- \- \- \- \- \- \- \- \-]
                   [\- \- \- \- \- \- \- \- \- \-]
                   [\- \- \- \- \- \- \- \- \- \-]
                   [\- \- \- \- \- \- \- \- \- \-]
                   [\- \- \- \- \- \- \- \- \- \-]
                   [\- \- \- \- \- \- \- \- \- \-]
                   [\- \- \- \- \- \- \- \- \- \-]]]
    (is (get-board-cell testboard 0 0) "pop")
    (is (get-board-cell testboard 0 1) \-)
    (is (get-board-cell testboard 1 1) \O)
    (is (get-board-cell testboard 2 2) \X)))

(deftest winner?-test
  
  )

(deftest scenario1-test
  "it should not be possible to choose a cell that is already taken"
  (binding [noir.session/*noir-session* (atom {})]
    (reset-game!)
    (play! 0 0)
    (is (= (get-board-cell 0 0) "fill"))
    (play! 0 1)
    (is (= (get-board-cell 0 1) "fill"))
    (play! 0 2)
    (is (= (get-board-cell 0 2) "fill"))
    (is (= (get-action) "fill"))
    (play! 0 0)
    (is (= (get-board-cell 0 0) "fill") "value of cell 0 0 should still be fill")
    (is (= (get-action) "fill") "player should still be fill")
    (reset-game!)))