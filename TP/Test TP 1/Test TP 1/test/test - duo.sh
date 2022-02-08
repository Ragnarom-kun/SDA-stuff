#!/bin/bash

javac LembagaIntelijenSiesta.java


for i in {0..9}
do
    cat tc/tcDuo/in_0$i.txt | java LembagaIntelijenSiesta > tc/tcDuo/myOut/my_out_$i.txt ; diff -w tc/tcDuo/myOut/my_out_$i.txt tc/tcDuo/outTC/out_0$i.txt
    echo tc $i done
done


for i in {10..499}
do
    cat tc/tcDuo/in_$i.txt | java LembagaIntelijenSiesta > tc/tcDuo/myOut/my_out_$i.txt ; diff -w tc/tcDuo/myOut/my_out_$i.txt tc/tcDuo/outTC/out_$i.txt
    echo tc $i done
done

read  -n 1 -p "Done, press any key " mainmenuinput

