(defn equal
  "An other implement of qunit's equal that use Chlorine's = to compare values"
  [actual expected message]
  (.. QUnit (push (= expected actual) actual expected message))
  )
(defmacro defmodule
  [module-name module-deps & section-dclrs]
  (let [final-body
        (apply
         concat
         (for [section section-dclrs]
           (if (keyword? (first section))
             (let [[section-type & section-exprs] section]
               (for [section-expr section-exprs]
                 (cond
                  (= :route section-type)
                  `(config ~section-expr)
                  (= :filter section-type)
                  (let [[filter-name filter-deps & filter-body]
                        section-expr]
                    `(filter ~(name filter-name)
                             (fn-di ~filter-deps
                                    (fn ~@filter-body))))
                  (= :directive section-type)
                  (let [[d-name d-deps link-args & body]
                        section-expr]
                    `(directive
                      ~(name d-name)
                      (fn-di ~d-deps
                             {:link (fn ~link-args
                                      ~@body)})))
                  (contains? #{:controller
                               :service} section-type)
                  (let [[di-name & body]
                        section-expr]
                    `(~(symbol (name section-type)) ~(name di-name)
                      (fn-di ~@body))))))
             section)))]
    `(..
      angular
      (module ~(name module-name) ~(mapv name module-deps))
      ~@final-body)))

(defmacro defroute
  [& routes]
  `(fn-di [$routeProvider]
          ~(concat '(.. $routeProvider)
                   (for [[route route-config] (partition 2 routes)]
                     (let [head (if (keyword? route)
                                  `(otherwise)
                                  `(when ~route))
                           tail (cond
                                 (vector? route-config)
                                 {:controller (first route-config)
                                  :templateUrl (second route-config)}

                                 (string? route-config)
                                 {:redirectTo route-config}

                                 :default
                                 route-config)]
                       (concat head [tail]))))))

(defmacro fn-di
  "Like `fn` but will automatically generate dependency injection vectors.
  Doesn't support multi-arity."
  [& body]
  (let [[docstring v] (if (string? (first body))
                        [(first body) (second body)]
                        [nil (first body)])]
    (if (= v [])
      `(fn ~@body)
      (vec (concat (map name v) [`(fn ~@body)])))))

(defmacro defn-di
  "Like `defn` but will automatically generate dependency injection vectors.
  Doesn't support multi-arity."
  [di-name & body]
  (let [[docstring v] (if (string? (first body))
                        [(first body) (second body)]
                        [nil (first body)])]
    `(def ~di-name
       (if (= v [])
         `(fn ~@body)
         ~(vec (concat (map name v) [`(fn ~@body)]))))))

(defmacro ng-test
  [module-name & body]
  (let [final-body
        (for [expr body]
          (let [[test-type test-name & test-body] expr
                test-init
                (fn [test-type]
                  (cond
                   (= 'controller-test test-type)
                   (list
                    `(def $controller
                       (.. injector (get "$controller")))
                    `($controller ~(name test-name)
                                  {:$scope this.$scope})
                    )
                   (= 'service-test test-type)
                   (list
                    `(def ~test-name (.. injector (get ~(name test-name)))))
                   (= 'filter-test test-type)
                   (list
                    `(def $filter (.. injector (get "$filter"))))
                   (= 'directive-test test-type)
                   (list
                    `(def $compile (.. injector (get "$compile"))))
                   :default
                   nil))
                test-tabular
                (fn [test-type test-name test-body]
                  (for [expr test-body]
                    (if (= :tabular (first expr))
                      (apply concat
                             (for [[test-case expect-val]
                                   (partition 2 (rest expr))]
                               (cond
                                (= 'controller-test test-type)
                                `(equal (.. this.$scope ~test-case)
                                        ~expect-val)
                                (= 'service-test test-type)
                                `(equal (.. ~test-name ~test-case)
                                        ~expect-val)
                                (= 'filter-test test-type)
                                `(equal (($filter ~(name test-name))
                                         ~@test-case)
                                        ~expect-val)
                                :default
                                `(equal ~test-case ~expect-val))))
                      expr)))]
            `(deftest ~test-name
               ~@(test-init test-type)
               ~@(test-tabular test-type test-name test-body))
            ))]
    `(do
       (def injector (.. angular (injector ["ng" ~(name module-name)])))
       (module "tests"
               {:setup
                (fn []
                  (set! this.$scope
                        (.. injector (get "$rootScope") $new)))})
       ~@final-body)))

(defmacro def!
  "Shortcut for `(def this.var-name ...)`"
  [var-name val]
  `(set! ~(symbol (str "this." (name var-name)))
         ~val))

(defmacro defn!
  "Shortcut for `(defn this.fname ...)`"
  [fname & body]
  `(set! ~(symbol (str "this." (name fname)))
         (fn ~@body)))

(defmacro def$
  "Shortcut for `(def $scope.var-name ...)`"
  [var-name val]
  `(set! ~(symbol (str "$scope." (name var-name)))
         ~val))

(defmacro defn$
  "Shortcut for `(defn $scope.fname ...)`"
  [fname & body]
  `(set! ~(symbol (str "$scope." (name fname)))
         (fn ~@body)))