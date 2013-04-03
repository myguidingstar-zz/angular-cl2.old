angular-qunit
=============

AngularJS seed with QUnit + testem written in [ChlorineJS](https://github.com/chlorinejs)

You need [Chloric compiler](https://github.com/chlorinejs/chloric), NodeJS, [Bower](https://github.com/twitter/bower) installed.

Install dependencies
------------------

```
bower install
./bootstrap_font-awesome.sh
# install testem to run the tests on the fly
npm install
```
Development - Live coding
-----------------------

Have your files watched and auto-compiled:
```
npm run-script watch
```
This will watch for changes and re-compile *.cl2 files to Javascript.

Now open an other terminal, run testem
```
npm run-script test
```

View the result
--------------

Compile just the main script:
```
npm run-script compile
```
If your main script doesn't use Chlorine's core library functions (eg using `=` to
compare vectors and maps, etc), you can skip the library by:
```
npm run-script bare-compile
```
Now open `index.html` in your favourite browser and enjoy!
