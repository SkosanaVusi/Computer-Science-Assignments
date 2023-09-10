import re

filenames = ["Experiment/AccessArrayApp/AAA500.txt", "Experiment/AccessArrayApp/AAA1000.txt", "Experiment/AccessArrayApp/AAA1500.txt", "Experiment/AccessArrayApp/AAA2000.txt", "Experiment/AccessArrayApp/AAA2500.txt", "Experiment/AccessArrayApp/AAA3000.txt", "Experiment/AccessArrayApp/AAA3500.txt", "Experiment/AccessArrayApp/AAA4000.txt", "Experiment/AccessArrayApp/AAA4500.txt", "Experiment/AccessArrayApp/AAA5000.txt", "Experiment/AccessBSTApp/ABA500.txt", "Experiment/AccessBSTApp/ABA1000.txt", "Experiment/AccessBSTApp/ABA1500.txt", "Experiment/AccessBSTApp/ABA2000.txt", "Experiment/AccessBSTApp/ABA2500.txt", "Experiment/AccessBSTApp/ABA3000.txt", "Experiment/AccessBSTApp/ABA3500.txt", "Experiment/AccessBSTApp/ABA4000.txt", "Experiment/AccessBSTApp/ABA4500.txt", "Experiment/AccessBSTApp/ABA5000.txt"]
listnumbers = []

for name in filenames:
	fileList = (open(name, "r")).readlines()
	for index in range(len(fileList)):
		listnumbers.append(int(re.sub("\D", "", fileList[index])))
	print(str(name)+": max="+str(max(listnumbers))+", min="+str(min(listnumbers))+", sum="+str(sum(listnumbers))+", lenght="+str(len(listnumbers))+", avarage="+str(sum(listnumbers)/len(listnumbers)))
	
	listnumbers=[]

