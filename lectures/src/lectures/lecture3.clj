(ns anglican.language
  (:use [anglican core emit runtime])
  (:require [oz.core :as oz]))


;; Two bins. Pick an orange. Chance that I picked the blue bin?
(defquery fruits-pzl [fruit]
  (let [bin (sample (categorical {:blue (/ 5 6) :red (/ 1 6)}))]
    (if (= bin :red)
      (observe (categorical {:orange (/ 6 8) :apple (/ 2 8)}) fruit)
      (observe (categorical {:orange (/ 1 4) :apple (/ 3 4)}) fruit))
    bin))

(frequencies 
  (map :result (take 10000 (doquery :lmh fruits-pzl [:orange]))))

;; Balls exercise:

(defquery balls [] 
  (let [b1 (sample (categorical {:white (/ 1 5) :black (/ 4 5)}))
        b2 (sample (categorical {:white 1 :black 0}))
        draw (sample (flip 0.5))
        ball-left (fn [x] (if (not x) b1 b2))] ;; p(ball left)
    (if draw 
      (observe (categorical {:white (/ 1 5) :black (/ 4 5)}) :white)
      (observe (categorical {:white 1 :black 0}) :white))
    (ball-left draw))) ;; p(ball left | fist draw = white)

(frequencies 
  (map :result (take 1000 (doquery :lmh balls []))))

