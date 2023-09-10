#!/bin/sh

cat babynames.txt | grep -v '#' | cut -f 2 -d ' ' | sort | uniq > firstnames.txt
cat babynames.txt | grep -v '#' | cut -f 6 -d ' ' | sort | uniq >> firstnames.txt
cat babynames.txt | grep -v '#' | cut -f 10 -d ' ' | sort | uniq > lastnames.txt
perl generate.pl > oklist.txt


