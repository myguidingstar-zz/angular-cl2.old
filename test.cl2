(include! "./main.cl2")

(ng-test
 myApp

 (:controller
  myCtrl
  (:tabular
   (addTwo 1) {:result 3})
  )

 (:service
  myService
  (:tabular (addThree 1) 4)
  )

 (:filter
  myFilter
  (:tabular
   [1] 6)
  )

 (:filter
  yourFilter
  (:tabular
   [2] 8))
 (:directive
  MyDirective
  (def
    element
    (($compile (hiccup
                [:div {:my-directive "foo"}])) (. this -$scope)))
  (def!$ foo 1)
  (.. this -$scope $apply)
  (equal "5" (.. element text))
  (delete (.. this -$scope -foo))
  )
 (:directive
  MyDirective
  (:tabular
   [:div {:my-directive "foo"}]
   {:foo 2}
   "6" text
   "6" (text))
  ))
