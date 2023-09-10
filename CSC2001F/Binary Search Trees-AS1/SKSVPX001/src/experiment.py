import os, random
oklistFile = '../data/oklist.txt'

os.system("javac AccessArrayApp.java")
os.system("javac AccessBSTApp.java")

aset = (open(oklistFile, 'r')).readlines()
FileOne = open(oklistFile, "w")
a = []
for x in range(500,1000, 500):
    for i in range(0, x):
        randomnumberforline = random.randint(0, x-1)
        os.system("echo " + aset[randomnumberforline][:-1]+ " >> " + str("../data/oklist") + ".txt")
        a.append(aset[randomnumberforline])
        if i== x-1:
            for m in range(0, len(a)):
                os.system("java AccessArrayApp " + a[m][:9]+ " >> AAA" + str(x) + ".txt")
                os.system("java AccessBSTApp " + a[m][:9]+ " >> ABA" + str(x) + ".txt")
            a = []
            FileOne.truncate(0)
      
FileOne.close()
    
