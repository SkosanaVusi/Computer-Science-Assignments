import os, random
oklistFile = '../data/oklist.txt'

aset = (open(oklistFile, 'r')).readlines() # read files content, and save in list
FileOne = open(oklistFile, "w") # empty the file

os.system("javac AccessAVLApp.java") # compile program 
a = []
for x in range(500,5500, 500):
    for i in range(0, x):
        randomnumberforline = random.randint(0, 4999)
        os.system("echo " + aset[randomnumberforline][:-1]+ " >> " + str("../data/oklist") + ".txt")
        a.append(aset[randomnumberforline])
        if i== x-1:
            for m in range(x):
                os.system("java AccessAVLApp " + a[m][:9]+ " >> AVL" + str(x) + ".txt")
            a = []
            FileOne.truncate(0)

for details in aset:
	FileOne.write(details)
FileOne.close()
    


