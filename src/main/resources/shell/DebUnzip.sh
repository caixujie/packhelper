#!/bin/bash

cd $(dirname $1)
mkdir $2
cd ./$2
dpkg -e ../$(basename $1)
