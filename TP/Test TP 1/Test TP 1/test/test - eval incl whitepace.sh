#!/bin/bash

javac LembagaIntelijenSiesta.java


for i in {0..9}
do
    cat tc/tcEval/in_0$i.txt | java LembagaIntelijenSiesta > tc/tcEval/myOut/my_out_$i.txt ; diff tc/tcEval/myOut/my_out_$i.txt tc/tcEval/outTC/out_0$i.txt
    echo tc $i done
done


for i in {10..699}
do
    cat tc/tcEval/in_$i.txt | java LembagaIntelijenSiesta > tc/tcEval/myOut/my_out_$i.txt ; diff tc/tcEval/myOut/my_out_$i.txt tc/tcEval/outTC/out_$i.txt
    echo tc $i done
done

read  -n 1 -p "Done, press any key " mainmenuinput

