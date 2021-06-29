(ns anglican.language
  (:use [anglican core emit runtime])
  (:require [oz.core :as oz]))


;; oz: (vega-lite)
(oz/start-plot-server!) 

;; Question 1:
;; First digits in Fibonacci numbers and Bendford's law.

(defn get-digits [n]
  (map (fn [c] (- (int c) (int \0))) (str n)))

;; first digits of Fibonacci numbers up to m:
(defn fib-digits [m]
  (loop [i 2 r0 1 r1 1 s [1]]
    (if (= i m)
      s
      (recur (+' i 1) r1 (+' r0 r1) (conj s (first (get-digits r1)))))
    ))

;; make a map from list:
(defn make-map [k x]
  (map (fn [d] (assoc {} k d)) x))

;; Benford's law pdf:
(defn benf [x] 
  (Math/log10 (/ (+ x 1) x)))

(defn fib-num-digits [m]
  (let [digs (into (sorted-map) (frequencies (fib-digits m)))]
    (vals digs)))


(def fib-nums-digs (fib-num-digits 10000))
(def num-digs
  (make-map :y (map float (for [n fib-nums-digs] (/ n 10000)))))
(def digs (make-map :x (range 1 (+ 1 (count fib-nums-digs)))))
(def benford (make-map :b (map benf (range 1 (+ 1 (count fib-nums-digs))))))

(def digs-plot (map conj digs num-digs))
(def benford-plot (map conj digs benford))

(def histogram
  {:layer
   [
    {:data {:values digs-plot}
     :encoding {:x {:field "x" :type "quantitative"}
                :y {:field "y" :type "quantitative"}}
     :mark "bar"},
    {:data {:values benford-plot}
     :encoding {:x {:field "x" :type "quantitative"}
                :y {:field "b" :type "quantitative"}}
     :mark {:type "line" :color "#85C5A6"}}
  ]})

(oz/view! histogram)

;; Question 2:
;; Islanders tell the truth 1/3 of the time. 
;; After one islander made a statement you ask another if it was true.
;; He answers yes. What is the prob that it was really true?

(defquery island []
  (let [s (sample (categorical {:truth (/ 1 3) :lie (/ 2 3)}))]
    (if (= s :lie)
      (observe (categorical {:wastrue (/ 2 3) :waslie (/ 1 3)}) :wastrue)
      (observe (categorical {:wastrue (/ 1 3) :waslie (/ 2 3)}) :wastrue))
    s))

(frequencies (map :result (take 10000 (doquery :lmh island []))))



