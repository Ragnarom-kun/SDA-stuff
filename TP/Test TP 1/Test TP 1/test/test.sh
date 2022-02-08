#!/bin/bash

javac LembagaIntelijenSiesta.java

for i in {10..499}
do
    cat tc/in_$i.txt | java LembagaIntelijenSiesta > tc/custom_out_$i.txt ; diff tc/custom_out_$i.txt tc/out_$i.txt
    echo tc/custom_out_$i.txt done
done

read  -n 1 -p "Done, press any key " mainmenuinput

