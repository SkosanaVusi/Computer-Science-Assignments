import re

filenames = ["PartTwo-Experiment/subsetsOutput/AVL500.txt", "PartTwo-Experiment/subsetsOutput/AVL1000.txt", "PartTwo-Experiment/subsetsOutput/AVL1500.txt", "PartTwo-Experiment/subsetsOutput/AVL2000.txt", "PartTwo-Experiment/subsetsOutput/AVL2500.txt", "PartTwo-Experiment/subsetsOutput/AVL3000.txt", "PartTwo-Experiment/subsetsOutput/AVL3500.txt", "PartTwo-Experiment/subsetsOutput/AVL4000.txt", "PartTwo-Experiment/subsetsOutput/AVL4500.txt", "PartTwo-Experiment/subsetsOutput/AVL5000.txt"]
listnumbers = []
insertlists = []

for name in filenames:
	fileList = (open(name, "r")).readlines()
	for index in range(len(fileList)):
		listnumbers.append(int(re.sub("\D", "", fileList[index][-8:])))
		insertlists.append(int(re.sub("\D", "", fileList[index][:-8])))

	print("TreeSize="+str(len(listnumbers))+": MaxFind="+str(max(listnumbers))+", MaxInsert="+str(max(insertlists))+", MinFind="+str(min(listnumbers))+", MinInsert="+str(min(insertlists))+", FindSum="+str(sum(listnumbers))+", InsertSum="+str(sum(insertlists))+", FindAvarage="+str(round(sum(listnumbers)/len(listnumbers),2))+", InsertAvarage="+str(round(sum(insertlists)/len(insertlists),2)))
	
	listnumbers=[]
	insertlists=[]


