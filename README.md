Angular-cl2
==========
Angular macros for [ChlorineJS](https://github.com/chlorinejs/chlorine/wiki).

[![Build Status](https://travis-ci.org/chlorinejs/angular-cl2.png?branch=master)](https://travis-ci.org/chlorinejs/angular-cl2)

Comsume this package
------------------
You need Java, NodeJS installed.

Pull angular-cl2 to your machine:
```
npm install angular-cl2
```
Now you should have `angular.cl2` somewhere in your `node_modules` directory. Include it as normal:
```clojure
(include! "./path/to/angular.cl2")
;; define your Angular app now
(defmodule myApp ...)
```

Develope this package
-------------------

Install dependencies
------------------

```
# install testem to run the tests on the fly
npm install
# install components via Twitter's bower
cd dev
bower install
./bootstrap_font-awesome.sh
cd ..
```

Live coding
----------

Have your files watched and auto-compiled:
```
npm run-script watch
```
This will watch for changes and re-compile `*.cl2` files to Javascript.

Now open an other terminal, run testem:
```
npm run-script test
```

View the result
--------------

Compile just the `main.cl2` script:
```
npm run-script compile
```
If your `main.cl2` script doesn't use Chlorine's core library functions (eg using `=` to
compare vectors and maps, etc), you can skip the library by:
```
npm run-script bare-compile
```
Now open `dev/index.html` in your favourite browser and enjoy!
