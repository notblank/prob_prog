;; Data structures

;;Numbers, Strings...

;;Maps:
{:first-name "Fidor"
 :last-name "Drunchwichz"}

(get  
  {:first-name "Fidor" :last-name "Drunchwichz"}
  :first-name)

;get can return default values

(get  
  {:a 1 :b 0}
  :c "first")

; look up values in nested maps:
(get-in 
  {:a 1 :b {:c 2}} [:b :c])

; treat the map as a funtion
({:a 1 :b {:c 2}} :b)

;; Keywords
; usually used as keys in a map

;; Vectors
[3 2 1]

(get [3 2 1] 0)

(conj [3 2 1] 0)


;; Lists
; like vectors but no order

'(1 2 3)

; no get for lists:
(nth '(1 2 3) 0)

;; Sets
; collections of unique values

(contains? #{:a 1} :a)

(get #{:a 1} 1)

;; Functions:

; + function applied to (1, 2)
(+ 1 2)

(inc 4.5)

; apply a function to each element:
(map inc [1 2 3 4]) 

; to define function use defn [args] 
; airity: can use different numbers of args
(defn multi-arity
  ([a b c]
   (reduce + [a b c]))
  ([a b]
   (+ a b)))

(multi-arity 1 2)
(multi-arity 1 2 7)

; use fn for anonymous functions


