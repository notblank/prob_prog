(ns anglican.language
  (:use [anglican core emit runtime])
  (:require [oz.core :as oz]))

; one funtion in core:
(-main)

;1 + 2
(+ 1 2)

; funtion:
(defn train []
  (println "choo choo"))

(train)


; literal forms and operators
1
; usually literals inside operators:
(+ 1 1)

; Control flow:
(if true
  "By Zeus's hammer!"
  "By Aquaman's trident!")

(if false
  "By Zeus's hammer!"
  "By Aquaman's trident!")

;; do lets you wrap multiple forms and run each.

(if true
  (do (println "Success")
      "By Zeus's hammer!")
  "By Aquaman's trident!")

; when = if + do without else branch

; logical operators:
(= 1 1)

(or false true)

(and false true)

; names:
(def s (+ 1 1))
s 






