(include! "../lib/angular.cl2")

(defapp myApp [])

;; don't have to specify app name as compiler remember the last app name
;; defined in `defapp`
(defdirective myDirective
  []
  ;; can be a directive-definition object or a link function
  (fn [scope elm attrs]
    (.
     scope
     ($watch
      (. attrs -myDirective)
      (fn [value] (. elm (text (+ value 4))))))))

(defcontroller myCtrl
  [$scope myService]
  (def$ someNumber 12)
  (defn$ addTwo [n] {:result (+ n 2)})
  (defn$ serviceAdd [n]
    (myService.addThree n)))

;; example of specifying app name
(defservice myApp myService
 []
 (defn! addThree [n] (+ n 3)))

(defmodule myApp
  (:filter (myFilter [] [s] (+ s 5))))

(deffilter yourFilter []
  [s]
  (+ s 6))
