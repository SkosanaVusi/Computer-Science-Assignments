import os, subprocess

subprocess.call("javac AccessAVLApp.java".split())

print("This is a program to check if a student is on a pre-approved list for access to campus during the lockdown.\n")
while True:
	print("Please choose an option and press enter:")
	print("1. Check with a student Identity.")
	print("2. Display all students in the pre-approved on the system.")
	print("3. Close the program.\n")
	option = input("Enter your option"+" ("+'\033[6m'+"1 or 2 or 3"+'\033[0m'+"): ")

	if option.strip(" ") == "1":
		studentID = (input("Enter student number: ")).upper()
		print("")
		command = "java AccessAVLApp " + studentID
		output = subprocess.check_output(command.split())

		if "Access denied!" in output.decode():
			print('\x1b[0;30;41m' + 'Access Denied!' + '\x1b[0m')
			break
		elif "Access denied!" not in output.decode():
			print('\x1b[6;30;42m' + 'Access Granted!' + '\x1b[0m')
			break
	
	elif option.strip(" ") == "2":
		allnames = subprocess.check_output("java AccessAVLApp".split())
		print('\033[33m'+(allnames).decode()+'\033[33m')
		break
	elif option.strip(" ") == "3":
		print('\033[91m'+"Program closed"+'\033[0m')
		break
	else:
		print("You have entered an incorrect option. Please try again\n")


