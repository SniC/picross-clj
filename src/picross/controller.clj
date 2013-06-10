(ns picross.controller
  (:use compojure.core)
  (:require [compojure.core :as compojure]
            [picross.view :as view]
            [picross.model :as model]))

(defn start-page []
  (model/reset-game!)
  (view/play-screen))

(defn action-page [button-pressed]
  (let [button-id (name (first (keys button-pressed)))
        rownr (Integer/parseInt (str (second button-id)))
        colnr (Integer/parseInt (str (nth button-id 2)))]
    (model/play! rownr colnr)
    (if-let [winner (model/winner?)]
      (view/winner-screen winner)
        (view/play-screen))))

(defn fill-action []
  (model/put-action! "fill")
  (view/play-screen))

(defn clear-action []
  (model/put-action! "-")
  (view/play-screen))

(defn block-action []
  (model/put-action! "block")
  (view/play-screen))

(defn method-decision [button-pressed]
  (cond
    (= (first (vals button-pressed)) "fill-button") (fill-action)
    (= (first (vals button-pressed)) "clear-button") (clear-action)
    (= (first (vals button-pressed)) "block-button") (block-action)
    :else (action-page button-pressed)))

(defroutes picross-routes
  (GET "/" [] (start-page))
  (POST "/" {button-pressed :params} (method-decision button-pressed)))
