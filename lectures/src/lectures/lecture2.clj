(ns anglican.language
  (:use [anglican core emit runtime])
  (:require [oz.core :as oz]))

;; Tail recursion
;; cs 3110 Cornell

(defn f [n]
  (if (= n 0)
    0
    (+ n (f (- n 1)))))

(println (f 10))

;; sum of squares:

(defn sq [n] (* n n))

(defn g [n]
  (if (= n 0)
    0
    (+ (sq n) (g (- n 1)))))

; stackoverflow: functions to be executed are stored in the stack.
; executed functions are poped from the stack
; it is better to have a function execution at the end.
; in g after evaluating g[n-1] we need to add it to sq[n]

(println (g 10000))

;; after recursion is done no more operations left:
(defn dec-rec [n r]
  (if (= n 0)
    r
    (dec-rec (- n 1) (+ n r))))

(println (dec-rec 3 0))

;; use recur and loop to tell the compiler that fn is tail-rec.

(defn dec-rec-comp [N]
  (loop [n N r 0] ; like let n = N, r = 0
    (if (= n 0)
      r
      (recur (- n 1) (+ n r)))
    ))

(println (dec-rec-comp 3))

(defn g-rec-comp [n]
  (loop [i n r 0]
    (if (= i 0)
      r
      (recur (- i 1) (+ (sq i) r)))))

(g-rec-comp 2) 

;; Fibonacci

(defn fib [n]
  (loop [i 2 r0 1 r1 1]
    (if (= i n)
      r1
      (recur (+ i 1) r1 (+ r0 r1)))
    ))

(fib 7)


;; rand fib:

(defquery rand-fib [n]
  (let [b (sample (flip 0.5))
        new-n (if b n (+ n 1))]
    (loop [i 2 r0 1 r1 1]
      (if (= i new-n)
        r1
        (recur (+ i 1) r1 (+ r0 r1))))
    ))

(map :result (take 100 (doquery :importance rand-fib [6])))




