(include! "../lib/angular.cl2")

(defapp myApp [])

(defdirective myApp myDirective
  []
  ;; can be a directive-definition object or a link function
  (fn [scope elm attrs]
    (.
     scope
     ($watch
      (. attrs -myDirective)
      (fn [value] (. elm (text (+ value 4))))))))

(defmodule myApp
  (:filter (myFilter [] [s] (+ s 5))
           (yourFilter [] [s] (+ s 6)))
  (:controller
   (myCtrl
    [$scope myService]
    (def$ someNumber 12)
    (defn$ addTwo [n] {:result (+ n 2)})
    (defn$ serviceAdd [n]
      (myService.addThree n))))
  (:service
   (myService
    []
    (defn! addThree [n] (+ n 3))))
  )
