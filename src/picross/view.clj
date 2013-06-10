(ns picross.view
  (:use hiccup.form
        [hiccup.def :only [defhtml]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [html5 include-css]])
  (:require [picross.model :as model]))

(defhtml layout [& content]
  (html5
   [:head
    [:title "Picross-Clojure"]
    (include-css "/css/picross.css")]
   [:body [:div#wrapper content]]))
  
(defn cell-html [rownum colnum cell] 
  [:td
   [:input {:name (str "b" rownum colnum)
            :class (str cell)
            :value (str "")
            :type (str "submit")}]])

(defn row-html [rownum row]
  [:tr (map-indexed (fn [colnum cell]
                      (cell-html rownum colnum cell))
                    row)])
  
(defn board-html [board]
  (form-to [:post "/"]
           [:table {:class (str "board")}
            (map-indexed (fn [rownum row]
                           (row-html rownum row)) 
                         board)]))

(defn solution-cell [rownum colnum cell]
  [:td
   (if (= (str cell) "-")  
   [:input {
            :class (str "solution-empty")
            :value (str cell)
            :type (str "submit")}]
	  [:input {
	        :class (str "solution")
	        :value (str cell)
	        :type (str "submit")}]
   )])

(defn solution-row-html [rownum row]
  [:tr (map-indexed (fn [colnum cell]
                      (solution-cell rownum colnum cell))
                    row)])

(defn board-vertical-solution-html [solution-vertical]
    [:table {:class (str "ver")}
     (map-indexed (fn [rownum row]
                    (solution-row-html rownum row))
                  solution-vertical)])

(defn board-horizontal-solution-html [solution-horizontal]
    [:table {:class (str "hor")}
     (map-indexed (fn [rownum row]
                    (solution-row-html rownum row))
                  solution-horizontal)])

(defn action-select-fill-html []
	(form-to [:post "/"]
          (hidden-field "fill" "fill-button")
          (submit-button {:class (if (= "fill" (model/get-action)) (str "action-fill-button-selected")(str "action-block-button-unselected"))} 
                         "fill")))

(defn action-select-clear-html []
	(form-to [:post "/"]
          (hidden-field "clear" "clear-button")
          (submit-button {:class (if (= "-" (model/get-action)) (str "action-clear-button-selected")(str "action-block-button-unselected"))} 
                         "clear")))
            
(defn action-select-block-html []
  (form-to [:post "/"]
           (hidden-field "block" "block-button")
          (submit-button {:class (if (= "block" (model/get-action)) (str "action-block-button-selected") (str "action-block-button-unselected"))} 
                         "block")))

(defn board-action-html []
  [:p {:class "changeactiontext"} "change action"]
  [:table {:class (str "actionbuttons")}
   [:tr
    [:td  (action-select-fill-html)]
    [:td (if (= (model/get-action) "clear") {:class (str "selected")})(action-select-clear-html)]
    [:td (if (= (model/get-action) "block") {:class (str "selected")})(action-select-block-html)]
    ]])

(defn play-screen []
  (layout
    [:img {:src "/images/picross-logo.png"
           :class (str "logo")}]
    [:div
     (board-vertical-solution-html (model/get-solution-ver))
     (board-horizontal-solution-html (model/get-solution-hor))
     (board-html (model/get-board))
     (board-action-html)]))

(defn winner-screen [winner]
  (layout
    [:img {:src "/images/picross-logo.png"
           :class (str "logo")}]
    [:div 
     [:p "You finished the puzzle!"]
     (link-to {:class (str "reset-link")} "/" "Play again")
     (board-vertical-solution-html (model/get-solution-ver))
     (board-horizontal-solution-html (model/get-solution-hor))
     (board-html (model/get-board))
     ]))

(defn draw-screen []
  (layout
    [:div
     [:p "It's a draw!"]
     (board-html (model/get-board))
     (link-to "/" "Reset")]))
  
