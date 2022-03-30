#!/bin/bash

while mvn -Didea.ignore.disabled.plugins=true -Didea.home.path=/tmp clean test -T 20; do :; done
