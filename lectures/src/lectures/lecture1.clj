(ns anglican.language
  (:use [anglican core emit runtime])
  (:require [oz.core :as oz]))

;; oz: (vega-lite)
(oz/start-plot-server!) 

(defquery lin-mod []
  (let [ a0 (sample (normal 0 6))
         a1 (sample (normal 0 2))
         f (fn [x] (+ (* a1 x) a0))]
    (observe (normal (f 0) 0.5) 0.6)
    (observe (normal (f 1) 0.5) 0.7)
    (observe (normal (f 2) 0.5) 1.2)
    (observe (normal (f 3) 0.5) 3.2)
    (observe (normal (f 4) 0.5) 6.8)
    (observe (normal (f 5) 0.5) 8.2)
    (observe (normal (f 6) 0.5) 8.4)
    {:a0 a0 :a1 a1}))

(def samples-coefs 
  (map :result 
       (take 10000 
             (drop 1000 (doquery :lmh lin-mod [])))))


(def scatter-plot
  {:data {:values samples-coefs}
   :encoding {:x {:field "a0" :type "quantitative"}
              :y {:field "a1" :type "quantitative"}}
   :mark "point"})

;; Render the plot
(oz/view! scatter-plot)
