(ns anglican.language
  (:use [anglican core emit runtime])
  (:require [oz.core :as oz]))

(load-file "./src/lectures/bounce.clj")

(require '[bounce :refer [create-world
                          show-world-simulation
                          simulate-world
                          display-static-world
                          balls-in-box]] 
         :reload)


;; No bumpers:
(def bumper-location1 (list))

(bounce/show-world-simulation bumper-location1)

;; Adding 2 bumpers:
(def bumper-location2 (list (list -3 6) (list 7 4)))

(bounce/show-world-simulation bumper-location2)

;; One distribution of bumpers that might work:
(def bumper-location-example
  (list (list -3 6) (list 0.8 5) (list 7 4) (list 12 3)))

(bounce/show-world-simulation bumper-location-example)


;; Number of balls that fall into the box out of 20
;; for the example world
;; and the example bumpers.
(def example-world
  (bounce/create-world bumper-location-example))

(def example-world-final-state 
  (bounce/simulate-world example-world))

(bounce/balls-in-box example-world-final-state)



;; place 8 bumpers uniformly at random:
(with-primitive-procedures ;; import functions into defquery
  [bounce/create-world bounce/simulate-world bounce/balls-in-box]
  (defquery physics0 []
    (let [n-bumpers 8
          f (fn [] (list 
                     (sample (uniform-continuous -5 14))
                     (sample (uniform-continuous 0 10))))
          bs (repeatedly n-bumpers f)
          w0 (bounce/create-world bs)
          w1 (bounce/simulate-world w0)
          num-balls (bounce/balls-in-box w1)]
      num-balls)))



(take 2000 (doquery :lmh physics0 []))

(def samples0 
  (map :result 
       (take-nth 10 (take 2000 (drop 1000 (doquery :importance physics0 []))))
       )) 

;; Error:
;; anglican.language=> (take 2000 (doquery :lmh physics0 []))
;; Execution error (ArityException) at anglican.language/eval18561$physics0$var18578 (form-in
;; it8518844555513192040.clj:3).
;; Wrong number of args (3) passed to: bounce/create-world

(def best-sample0 
  (reduce (fn [acc x] (if (> (first x) (first acc)) x acc)) 
          samples0))



