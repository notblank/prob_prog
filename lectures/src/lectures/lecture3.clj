(ns anglican.language
  (:use [anglican core emit runtime])
  (:require [oz.core :as oz]))


;; balls exercise:
(defquery balls [] 
  (let [b1 (sample (categorical {:white (/ 1 5) :black (/ 4 5)}))
        b2 :white
        draw (sample (flip 0.5))]
    (if draw b1 b2)
    ))

(map :result (take 10 (doquery :importance balls [])))


(defquery balls-color [color] 
  (let [draw (sample (flip 0.5))]
    (if draw 
      (observe (categorical {:white (/ 1 5) :black (/ 4 5)}) color)
      (observe (categorical {:white 1 :black 0}) color))
    draw))

(map :result (take 20 (doquery :importance balls-color [:white])))
