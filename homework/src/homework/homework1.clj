(ns anglican.language
  (:use [anglican core emit runtime])
  (:require [oz.core :as oz]))


;; Question 1:
;; First digits in Fibonacci numbers and Bendford's law:

(defn get-digits [n]
  (map (fn [c] (- (int c) (int \0))) (str n)))

(get-digits 123)

;; first digits of Fibonacci numbers up to m:
(defn fib-digits [m]
  (loop [i 2 r0 1 r1 1 s [1]]
    (if (= i m)
      s
      (recur (+' i 1) r1 (+' r0 r1) (conj s (first (get-digits r1)))))
    ))


(defn make-map [k x]
  (map (fn [d] (assoc {} k d)) x))

(defn fib-num-digits [m]
  (let [digs (into (sorted-map) (frequencies (fib-digits m)))]
    (vals digs)))

(def digs (make-map :x (fib-num-digits 100)))


