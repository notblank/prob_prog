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

;; sampling a linear function:
(defquery lin-fun []
  (let [F (fn [] 
            (let [a0 (sample (normal 0 6))
                  a1 (sample (normal 0 2))]
                  f (fn [x] (+ (* a1 x) a0))))
            f (F)]
    (observe (normal (f 0) 0.5) 0.6)
    (observe (normal (f 1) 0.5) 0.7)
    (observe (normal (f 2) 0.5) 1.2)
    (observe (normal (f 3) 0.5) 3.2)
    (observe (normal (f 4) 0.5) 6.8)
    (observe (normal (f 5) 0.5) 8.2)
    (observe (normal (f 6) 0.5) 8.4)
    f))

(def lazy-sample-funs 
  (map :result 
       (take 500 
             (take-nth 20 (drop 1000 (doquery :lmh lin-fun []))))))


;; Honseok Homework 1:
(defn uncps [f]
  (let [continuation (fn [x y] x)
        state nil]
    (fn [x]
      (trampoline (f continuation state x)))))

(def sample-funs
  (map uncps lazy-sample-funs))

;; make a map from a key value an a list
(defn make-map [k x] 
  (map (fn [d] (assoc {} k d)) x))

(defn xy [f]
  (map merge 
       (make-map :x (range -2 10 0.5)) 
       (make-map :y (map f (range -2 10 0.5)))))

(def y=fx (map xy sample-funs))

;; add f number:
(map merge (first y=fx) (make-map :function (repeat 24 0)))

(defn play-data [& names]
  (for [n names
        i (range 2)]
    {:time i :item n :quantity (+ (Math/pow (* i (count n)) 0.8) (rand-int (count n)))}))

(play-data "maa" "rrr")

(def line-plot
  {:data {:values ff}
   :encoding {:x {:field "x" :type "quantitative"}
              :y {:field "y" :type "quantitative"}}
   :mark "line"})

(def viz
  [:div
   [:vega-lite scatter-plot]
   [:vega-lite line-plot]])

;; Render the plot
(oz/view! viz)

