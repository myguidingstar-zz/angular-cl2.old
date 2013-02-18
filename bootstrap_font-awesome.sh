#!/bin/bash
cat components/bootstrap/less/bootstrap.less | sed 's/@import "sprites.less"/@import "font-awesome.less"/' > components/bootstrap/less/bootstrap_font-awesome.less
cp components/font-awesome/less/font-awesome.less components/bootstrap/less/ -f
cp components/font-awesome/font components/bootstrap/ -Rf
mkdir -p components/bootstrap/css
recess --compress components/font-awesome/less/font-awesome.less > components/bootstrap/css/bootstrap-awesome.min.css
recess --compress components/bootstrap/less/responsive.less > components/bootstrap/css/responsive.min.css
