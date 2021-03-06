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
  (let [a0 (sample (normal 0 6))
        a1 (sample (normal 0 2))
        f (fn [x] (+ (* a1 x) a0))]
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
       (take 100
             (take-nth 20 (drop 1000 (doquery :lmh lin-fun []))))))

;; observations:
(def obs-data
  (map merge 
       (make-map :x [0 1 2 3 4 5 6])
       (make-map :y [0.6 0.7 1.2 3.2 6.8 8.2 8.4])))

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

(def xrange (range -2 10 0.5))

(defn xy [f]
  (map merge 
       (make-map :x xrange) 
       (make-map :y (map f xrange))))

(def y=fx (map xy sample-funs))

;; add f number:
(def y=fx-plot
  (flatten
    (for [i (range (count y=fx))]
      (map merge (nth y=fx i) (make-map :f (repeat (count xrange) i))))))


(def line-plot
  {:layer
   [
    {:data {:values y=fx-plot}
     :encoding {:x {:field "x" :type "quantitative"}
                :y {:field "y" :type "quantitative"}
                :color {:field "f" 
                        :type "nominal"
                        :legend false}}
     :mark "line"},
    {:data {:values obs-data}
     :encoding {:x {:field "x" :type "quantitative"}
                :y {:field "y" :type "quantitative"}}
     :mark "point"}
    ]})


;; Change point:
;; defm: defn in anglican:
(defm add-changepoints [F l u]
  (if (sample (flip 0.5)) 
    (F)
    (if (sample (flip 0.5))
       (let [cp1 (sample 
                   (uniform-continuous l u))
             f1 (F)
             f2 (F)]
         (fn [x] (if (< x cp1) (f1 x) (f2 x))))
       (let [cp1 (sample
                   (uniform-continuous l u))
             cp2 (sample 
                   (uniform-continuous cp1 u))
             f1 (F)
             f2 (F)
             f3 (F)]
         (fn [x] (if (< x cp1) 
                   (f1 x)
                   (if (< x cp2) 
                     (f2 x)
                     (f3 x)))))))) 

(defquery pw-lin-fun []
  (let [F (fn []
            (let [a0 (sample (normal 0 6))
                  a1 (sample (normal 0 2))]
              (fn [x] (+ (* a1 x) a0))))
            f (add-changepoints F 0 6)]
    (observe (normal (f 0) 0.5) 0.6)
    (observe (normal (f 1) 0.5) 0.7)
    (observe (normal (f 2) 0.5) 1.2)
    (observe (normal (f 3) 0.5) 3.2)
    (observe (normal (f 4) 0.5) 6.8)
    (observe (normal (f 5) 0.5) 8.2)
    (observe (normal (f 6) 0.5) 8.4)
    f))

(def lazy-sample-pwl-funs 
  (map :result 
       (take 100
             (take-nth 200 (drop 2000 (doquery :lmh pw-lin-fun []))))))

(def sample-pwl-funs
  (map uncps lazy-sample-pwl-funs))

(def y=fpwlx (map xy sample-pwl-funs))

;; add f number:
(def y=fpwlx-plot
  (flatten
    (for [i (range (count y=fpwlx))]
      (map merge (nth y=fpwlx i) (make-map :f (repeat (count xrange) i))))))

(def pwl-plot
  {:layer
   [
    {:data {:values y=fpwlx-plot}
     :encoding {:x {:field "x" :type "quantitative"}
                :y {:field "y" :type "quantitative"}
                :color {:field "f" 
                        :type "nominal"
                        :legend false}}
     :mark "line"},
    {:data {:values obs-data}
     :encoding {:x {:field "x" :type "quantitative"}
                :y {:field "y" :type "quantitative"}}
     :mark "point"}
    ]})

;; Render the plot
(def viz
  [:div
   [:vega-lite scatter-plot]
   [:vega-lite line-plot]
   [:vega-lite pwl-plot]])

(oz/view! viz)

