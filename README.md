angular-qunit
=============

AngularJS seed with QUnit + testem written in [ChlorineJS](https://github.com/chlorinejs)

You need [Chloric compiler](https://github.com/chlorinejs/chloric), NodeJS, [Bower](https://github.com/twitter/bower) and [Testem](https://github.com/airportyh/testem) installed.

Install dependencies
------------------
```
bower install
./bootstrap_font-awesome.sh
```
Compile
-------
```
chloric -d test.cl2
```
This will watch for changes and re-compile *.cl2 files to Javascript.

Test - Live coding
------------------
Open an other terminal, run testem
```
testem
```
