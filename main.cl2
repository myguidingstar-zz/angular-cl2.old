(include! "./angular.cl2")

(defmodule myApp []
  (:filter (myFilter [] [s] (+ s 5))
           (yourFilter [] [s] (+ s 6)))
  (:controller
   (myCtrl
    [$scope]
    (defn$ addTwo [n] {:result (+ n 2)})))
  (:service
   (myService
    []
    (defn! addThree [n] (+ n 3))))
  )
