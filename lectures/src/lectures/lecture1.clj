(ns anglican.language
  (:use [anglican core emit runtime])
  (:require [oz.core :as oz]))

;; oz: (vega-lite)
(oz/start-plot-server!) 

(defquery lin-mod []
  (let [ a0 (sample (normal 0 6))
         a1 (sample (normal 0 2))
         f (fn [x] (+ (* a1 x) a0))]
    (observe (normal (f 0) 0.6))
    (observe (normal (f 1) 0.7))
    (observe (normal (f 2) 1.2))
    (observe (normal (f 3) 3.2))
    (observe (normal (f 4) 6.8))
    (observe (normal (f 5) 8.2))
    (observe (normal (f 6) 8.4))
    [a0 a1]))


(doquery :importance lin-mod [])


