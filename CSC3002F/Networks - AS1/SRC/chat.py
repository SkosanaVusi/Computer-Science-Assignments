"""
Author(s): MSMJOS003, GNYOSC001, SKSVPX001
Date: March 2022
Assignment: 1
"""
import random
import time
import sys
import socket
import json
import threading


"""
Description:
	Client class used to create a client object
"""
class Client:
	"""
	Description:
		Constructor initialises instance variables
	Parameters:
		string: username
		int: port
	"""
	def __init__(self, username,port):
		self.username = username
		self.port = port
		self.client_sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
		self.client_sock.bind((socket.gethostname(), port))
		self.isConnected = False
		self.isChatOpen = False
		self.isChatOpenGroup = False
		self.groupAccess = False
		self.logout = False
		self.usersReturned = False
		self.users = []
		self.data = []
		self.groupdata = []
	"""
	Description:
		setter method, sets the boolean that checks if the request to get users has been returned
	Parameters:
		bool: boolV
	"""
	def setUsersReturned(self,boolV):
		self.usersReturned = boolV
	"""
	Description:
		setter method, sets the boolean that checks if the user is on the 1 on 1 chat box 
	Parameters:
		bool: boolV
	"""
	def setIsChatOpen(self, boolV):
		self.isChatOpen = boolV
	"""
	Description:
		setter method, sets the boolean that checks if the user is on the group chat, chat box
	Parameters:
		bool: boolV
	"""
	def setIsChatOpenGroup(self, boolV):
		self.isChatOpenGroup = boolV
	"""
	Description:
		connectToServer -  requests connection to the server
	"""
	def connectToServer(self):
		data = {'key':'CONNECT','isConnected':self.isConnected,'username':self.username, 'isStach':False}
		jsonString = json.dumps(data)
		self.client_sock.sendto(jsonString.encode('utf-8'),(socket.gethostname(), 8000))
	"""
	Description:
		sendTo - send a message to a specific user (called on a 1 on 1 chat)
	Parameters:
		string: msg
		string: sendto
	"""
	def sendTo(self,msg,sendto):
		data = {'key':'SENT_TO','msg':msg,'sendto':sendto,'sentfrom':self.username}
		jsonString = json.dumps(data)
		self.client_sock.sendto(jsonString.encode('utf-8'),(socket.gethostname(), 8000))
	"""
	Description:
		requestToJoinChat -  sends a request to the server to Join the MyChat group chat
	"""
	def requestToJoinChat(self):
		data = {'key':'REQUEST_JOIN_GROUP','username':self.username}
		jsonString = json.dumps(data)
		self.client_sock.sendto(jsonString.encode('utf-8'),(socket.gethostname(), 8000))
	"""
	Description:
		sendToGroup - sends a message to everyone on the group chat
	Parameters:
		string: msg
	"""
	def sendToGroup(self,msg):
		data = {'key':'SENT_TO_GROUP','msg':msg,'sentfrom':self.username}
		jsonString = json.dumps(data)
		self.client_sock.sendto(jsonString.encode('utf-8'),(socket.gethostname(), 8000))
	"""
	Description:
		requestLogOut - sends a request to the server to log out
	"""
	def requestLogOut(self):
		data = {'key':'LOGOUT','username':self.username}
		jsonString = json.dumps(data)
		self.client_sock.sendto(jsonString.encode('utf-8'),(socket.gethostname(), 8000))
	"""
	Description:
		getUsers - sends a request to get a list of users that are available in the system
	"""
	def getUsers(self):
		data = {'key':'GET_USERS','username':self.username}
		jsonString = json.dumps(data)
		self.client_sock.sendto(jsonString.encode('utf-8'),(socket.gethostname(), 8000))
	"""
	Description:
		getData - sends a request to get all the message history of usernameToGet 
	Parameters:
		string: usernameToGet
	"""
	def getData(self,usernameToGet):
		data = {'key':'GET_DATA','find':usernameToGet,'username':self.username}
		jsonString = json.dumps(data)
		self.client_sock.sendto(jsonString.encode('utf-8'),(socket.gethostname(), 8000))
	"""
	Description:
		getGroupData - sends a request to get all the message history on the group chat 
	"""
	def getGroupData(self):
		data = {'key':'GET_GROUP_DATA','username':self.username}
		jsonString = json.dumps(data)
		self.client_sock.sendto(jsonString.encode('utf-8'),(socket.gethostname(), 8000))
	"""
	Description:
		rec - Responsible for listening for requests from the server, it uses 'key'(s) to separate the type of commands to execute
	"""	
	def rec(self):
		while not self.logout:
			data, addr = self.client_sock.recvfrom(100024)
			dataString = str(data.decode())
			data = json.loads(dataString)

			if data["key"] == "CONNECT":
				self.isConnected = data['isConnected']
				self.users = data['users']

				if data["isStach"] == True:
					data["key"] = "CONFIRM_MSG_2"
					data["sendto"] = data['StashUser']
					data["sentfrom"] = self.username
					jsonString = json.dumps(data)
					self.client_sock.sendto(jsonString.encode('utf-8'),(socket.gethostname(), 8000))

			if data["key"] == "CONFIRM_MSG":
				if data["sent"] == True:
					print("--> [message sent - "+data['time']+"]")
				else:
					print("--> [message failed to send - "+data['time']+"]")
					print("--> [user '"+str(data['sendto'])+"' doesn't exist]")
					print("--> [type 'B' to return to menu]")
			if data["key"] == 'SENT_FROM':
				if self.isChatOpen == True:
					print("\t\t\t\t\t\t>> "+str(data["sentfrom"])+": "+str(data["msg"]))
				data["key"] = "CONFIRM_MSG_2"
				data["sendto"] = data["sentfrom"]
				data["sentfrom"] = self.username
				jsonString = json.dumps(data)
				self.client_sock.sendto(jsonString.encode('utf-8'),(socket.gethostname(), 8000))
				
			if data["key"] == "CONFIRM_MSG_2":
				if data["sentfrom"] != self.username:
					print("--> [Message recieved - "+data['time']+"]")
			if data["key"] == "REQUEST_JOIN_GROUP":
				self.groupAccess = data['joined']
				if data['partofgroup'] == False:
					print("--> [Access granted]")
			if data["key"] == "SENT_TO_GROUP":
				if self.isChatOpenGroup == True:
					if data["sentfrom"] !=  self.username:
						print("\t\t\t\t\t\t>> "+str(data["sentfrom"])+": "+str(data["msg"]))
			if data["key"] == "LOGOUT":
				self.logout = data['Loggedout']
				print("###################[You're now offline]###################")
			if data["key"] == "GET_USERS":
				self.users = data["users"]
				self.usersReturned = True
			if data["key"] == "GET_DATA":
				self.usersReturned = True
				self.data = data["data"]
			if data["key"] == "GET_GROUP_DATA":
				self.groupdata = data["group_data"]
				self.usersReturned = True
"""
Description:
	main - reposible for creating the user interface for the client.
"""	
def main():
	print("###################[Welcome to MyChat]###################")
	username = input("Enter username: ")
	port = random.randint(5000,6000) 

	clientObj = Client(username,port);

	try:
		recieve = threading.Thread(target = clientObj.rec)
		recieve.start()

		print("--> [connecting to server]*", end = "")

		count = 0;
		while not clientObj.isConnected:
			clientObj.connectToServer()
			count = count+1
			if count >75:
				print("[couldn't connect to server]")
				exit()
			print("*",end = "")
			time.sleep(0.2)
			sys.stdout.flush()

		clientObj.getUsers()
		while not clientObj.usersReturned:
			pass
		print("[connected to server]")
		print("###################[you: "+username+"]###################")
		print("--> [available group chats]")
		print("    1. MyChat\n")
		num = 1
			
		if len(clientObj.users) != 0:
			print("--> [available users]")
			for x in clientObj.users:
				if x['isOnline'] == True:
					print("    "+str(num)+ ". " +str(x['username']+" (online)"))
				else:
					print("    "+str(num)+ ". " +str(x['username']+" (offline)"))
				num = num + 1

		getInput =  input("\n--> [type 'JOIN' + 'group chat name' and press ENTER to join the group chat, 'CHAT' + 'username' and press ENTER to chat to a user, 'R' to refresh and 'E' to exit]\n    ").split(" ")
			
		if len(getInput) == 1:
			command = getInput[0]
		else:
			command = getInput[0]
			chatTo = getInput[1]

		count = 0
		count1 = 0
		while command != "E":
			if len(getInput) == 1:
				if command == "JOIN":
					command = "R"
				if command == "CHAT":
					command = "R"
				if command == "R":
					clientObj.setIsChatOpen(False)
					clientObj.setIsChatOpenGroup(False)
					clientObj.setUsersReturned(False)
					clientObj.getUsers()
					while not clientObj.usersReturned:
						pass
					print("###################[you: "+username+"]###################")
					print("--> [available group chats]")
					print("    1. MyChat\n")
					num = 1
						
					if len(clientObj.users) != 0:
						print("--> [available users]")
						for x in clientObj.users:
							if x['isOnline'] == True:
								print("    "+str(num)+ ". " +str(x['username']+" (online)"))
							else:
								print("    "+str(num)+ ". " +str(x['username']+" (offline)"))
							num = num + 1

						getInput =  input("\n--> [type 'JOIN' + 'group chat name' and press ENTER to join the group chat, 'CHAT' + 'username' and press ENTER to chat to a user, 'R' to refresh and 'E' to exit]\n    ").split(" ")
						
						if len(getInput) == 1:
							command = getInput[0]
						else:
							command = getInput[0]
							chatTo = getInput[1]
						if command == "E":
							break;
				if command == "B":
					count1 = 0
					count = 0
					clientObj.setIsChatOpen(False)
					clientObj.setIsChatOpenGroup(False)
					clientObj.setUsersReturned(False)
					clientObj.getUsers()
					while not clientObj.usersReturned:
						pass
					print("###################[you: "+username+"]###################")
					print("--> [available group chats]")
					print("    1. MyChat\n")
					num = 1
						
					if len(clientObj.users) != 0:
						print("--> [available users]")
						for x in clientObj.users:
							if x['isOnline'] == True:
								print("    "+str(num)+ ". " +str(x['username']+" (online)"))
							else:
								print("    "+str(num)+ ". " +str(x['username']+" (offline)"))
							num = num + 1

					getInput =  input("\n--> [type 'JOIN' + 'group chat name' and press ENTER to join the group chat, 'CHAT' + 'username' and press ENTER to chat to a user, 'R' to refresh and 'E' to exit]\n    ").split(" ")
						
					if len(getInput) == 1:
						command = getInput[0]
					else:
						command = getInput[0]
						chatTo = getInput[1]
					if command == "E":
						break;
			else:
				if command == "JOIN" and chatTo == "MyChat":
					if count == 0:
						print("###################[GROUP CHAT]###################")

					clientObj.requestToJoinChat()
					while not clientObj.groupAccess:
						pass

					clientObj.getGroupData()
					clientObj.setUsersReturned(False)
					while not clientObj.usersReturned:
						pass

					clientObj.setIsChatOpen(False)
					clientObj.setIsChatOpenGroup(True)
					if count == 0:
						print("--> [you joined 'MyChat' group]")
						if len(clientObj.groupdata) ==0:
							pass
						else:
							for x in clientObj.groupdata:
								if x['username'] != username:
									print("\t\t\t\t\t\t>> "+str(x["username"])+" - "+str(x['time'])+": "+str(x["msg"]))
								else:
									print("    >> you - "+str(x['time'])+": "+str(x['msg']))
						print("--> [type message and press ENTER or type 'B' to return to menu]")
						count = count + 1
						msg = input()
						if msg == "B":
							command = "B"
							getInput = []
							getInput.append(command)
						else:
							clientObj.sendToGroup(msg)
					else: 
						msg = input()
						if msg == "B":
							command = "B"
							getInput = []
							getInput.append(command)
						else:
							clientObj.sendToGroup(msg)
				if command == "JOIN" and chatTo == "":
					command = "R"
					getInput = []
					getInput.append(command)

				if command == "CHAT" and chatTo != "":
					clientObj.getData(chatTo)
					clientObj.setUsersReturned(False)
					while not clientObj.usersReturned:
						pass
					clientObj.setUsersReturned(False)
					clientObj.setIsChatOpen(True)
					clientObj.setIsChatOpenGroup(False)
						
					if count1 == 0:
						print("###################[you opened a chat with "+str(chatTo)+"]###################")
						print("--> [type message and press ENTER or type 'B' to return to menu]")
						if len(clientObj.data)!=0:
							for x in clientObj.data:
								if x['username'] == username:
									print("    >> you - "+str(x['time'])+": "+str(x['msg']))
								else:
									print("\t\t\t\t\t\t>> "+str(x["username"])+" - "+str(x['time'])+": "+str(x["msg"]))
							msg = input()
							if msg == "B":
								command = "B"
								getInput = []
								getInput.append(command)
							else:
								clientObj.sendTo(msg,chatTo)
						count1 = count1 + 1
					else:
						msg = input()
						if msg == "B":
							command = "B"
							getInput = []
							getInput.append(command)
						else:
							clientObj.sendTo(msg,chatTo)
				if command == "CHAT"and chatTo == "":
					command = "R"
					getInput = []
					getInput.append(command)

				if command == "CHAT"and chatTo == username:
					print("--> [you can't open a chat with yourself]")
					command = "R"
					getInput = []
					getInput.append(command)
				if command == "CHAT"and chatTo == "MyChat":
					command = "R"
					getInput = []
					getInput.append(command)

			if command != "B" and command != "R" and command != "CHAT" and command != "JOIN" :
				command = "R"
				getInput = []
				getInput.append(command)

		clientObj.requestLogOut()
		exit()
	except KeyboardInterrupt:
		clientObj.requestLogOut()
		exit()
	

if __name__ == "__main__":
	main()
