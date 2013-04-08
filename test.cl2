(include! "./main.cl2")

(ng-test
 myApp

 (controller-test
  myCtrl
  (:tabular
   (addTwo 1) {:result 3})
  )

 (service-test
  myService
  (:tabular (addThree 1) 4)
  )

 (filter-test
  myFilter
  (:tabular
   [1] 6)
  )

 (filter-test
  yourFilter
  (:tabular
   [2] 8))
 (directive-test
  MyDirective
  (def
    element
    (($compile (hiccup
                [:div {:my-directive "foo"}])) this.$scope))
  (def!$ foo 1)
  (. this.$scope $apply)
  (equal "5" (.. element text))
  (delete this.$scope.foo)
  ))
