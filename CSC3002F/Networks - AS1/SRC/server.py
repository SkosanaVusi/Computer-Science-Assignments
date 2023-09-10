"""
Author(s): MSMJOS003, GNYOSC001, SKSVPX001
Date: March 2022
Assignment: 1
"""
import socket
import json
import sys
import os
import threading
from datetime import datetime

"""
Description:
	Server class is used to create the a server object which will be responsible for connecting multiple client over a network
"""
class Server:
	"""
	Description:
		Constructor used to initialise instance variable(s)
	"""
	def __init__(self):
		self.server_sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
		self.server_sock.bind((socket.gethostname(), 8000))
		self.database = []
	"""
	Description:
		readTextfile - reads data from textfile to the database variable
	"""
	def readTextfile(self):
		f = open('database.txt', 'r')
		Lines = f.readlines()
		if len(Lines) != 0:
			for x in Lines:
				self.database.append(json.loads(x))
		f.close()
	"""
	Description:
		writeToTextfile - writes every element in database to the textfile called "database.txt"
	"""
	def writeToTextfile(self):
		f = open('database.txt', 'w')
		f.truncate(0)

		for x in self.database:
			f.write(json.dumps(x)+"\n")
		f.close()
	"""
	Description:
		rec - Responsible for listening for requests from the Client users, it uses 'key'(s) to separate the type of commands to execute
	"""	
	def rec(self):
		print("--> [MyChat server is now online]")
		while True:
			data, addr = self.server_sock.recvfrom(100024)
			dataString = str(data.decode())
			data = json.loads(dataString)

			if data['key'] == 'CONNECT':
				#change online status
				count = 0
				data['users'] = []
				if len(self.database) == 0:
					pass
				else:
					for x in self.database:
						if x['username'] == data['username']:		
							x['isOnline'] = True
							x['IP'] = addr[0]
							x['port'] = int(addr[1])
							count = count +1

							for y in x['chats']:
								if len(y['stash']) == 0:
									data['isStach'] = False
									pass
								else:
									for z in y['stash']:
										y["messages"].append(z)
									y["stash"] = []
									data['isStach'] = True
									data['StashUser'] = y['username']
								
						data['users'].append({'username':x['username'],'isOnline': x['isOnline']})
				
				if count == 0:
					self.database.append({"username":data["username"],"isOnline":True,"IP":addr[0],"port":int(addr[1]),"joinedGroupChat":False,"groupChats":[],"chats":[]})
					data['users'].append({'username':data["username"],'isOnline': True})

				data['newAccount'] = True
				data['isConnected'] = True


				jsonString = json.dumps(data)
				self.server_sock.sendto(jsonString.encode() , (socket.gethostname(),int(addr[1])))



				self.writeToTextfile()


			if data['key'] == 'SENT_TO':

				count = 0
				if len(self.database) != 0:
					for x in self.database:
						if x['username'] == data['sendto']:
							data['key'] = 'CONFIRM_MSG'
							data['sent'] = True
							count = count + 1
							break;
				if count == 0:
					data['key'] = 'CONFIRM_MSG'
					data['sent'] = False
				time = datetime.now() .strftime("%H:%M:%S")
				data['time'] = time
				jsonString = json.dumps(data)
				self.server_sock.sendto(jsonString.encode() , (socket.gethostname(),int(addr[1])))

				if data['sent'] != False:
					for x in self.database:
						if x['username'] == data['sendto']:
							if len(x['chats'])==0:
								x['chats'].append({'username': data['sentfrom'],'messages':[{'username':data['sentfrom'],'msg':data['msg'],'time':time}], 'stash':[]})
							else:
								for y in x['chats']:
									if y['username'] == data['sentfrom']:
										if x['isOnline'] == False:
											y['stash'].append({'username':data['sentfrom'],'msg':data['msg'],'time':time})
										else:
											y['messages'].append({'username':data['sentfrom'],'msg':data['msg'],'time':time})
											y['stash'] = []

						if x['username'] == data['sentfrom']:	
							if len(x['chats'])==0:
								x['chats'].append({'username': data['sendto'],'messages':[{'username':data['sentfrom'],'msg':data['msg'],'time':time}],'stash':[]})
							else:
								for y in x['chats']:
									if y['username'] == data['sendto']:
										if x['isOnline'] == False:
											y['stash'].append({'username':data['sentfrom'],'msg':data['msg'],'time':time})
										else:
											y['messages'].append({'username':data['sentfrom'],'msg':data['msg'],'time':time})
											y['stash'] = []

					self.writeToTextfile()
				
				if count > 0 :
					for x in self.database:
						if x['username'] == data['sendto']:
							data['key'] = 'SENT_FROM'
							jsonString = json.dumps(data)
							self.server_sock.sendto(jsonString.encode() , (x['IP'],x['port']))
							break;

				#data = {'key':'SENT_TO','msg':msg,'sendto':sendto,'sentfrom':self.username}

			if data['key'] == 'CONFIRM_MSG_2':
				time = datetime.now() .strftime("%H:%M:%S")
				for x in self.database:
					if x['username'] == data['sendto']:
						data['key'] = 'CONFIRM_MSG_2'
						data['time'] = time
						jsonString = json.dumps(data)
						self.server_sock.sendto(jsonString.encode() , (x['IP'],x['port']))
						break;
			if data['key'] == 'REQUEST_JOIN_GROUP':
				for x in self.database:
					if x['username'] == data['username']:
						data['partofgroup'] = x['joinedGroupChat']
						x['joinedGroupChat'] = True
						break;
				data['joined'] = True
				jsonString = json.dumps(data)
				self.server_sock.sendto(jsonString.encode() , (socket.gethostname(),int(addr[1])))			


			if data['key'] == 'SENT_TO_GROUP':
				time = datetime.now() .strftime("%H:%M:%S")
				count = 0

				data['key'] = 'CONFIRM_MSG'
				data['sent'] = True
				data['time'] = time

				jsonString = json.dumps(data)
				self.server_sock.sendto(jsonString.encode() , (socket.gethostname(),int(addr[1])))

				data['key'] = 'SENT_TO_GROUP'
				if len(self.database) != 0:
					for x in self.database:
						if x['joinedGroupChat'] == True:
							x['groupChats'].append({'username':data['sentfrom'],'msg':data['msg'],'time':time})
							jsonString = json.dumps(data)
							self.server_sock.sendto(jsonString.encode() , (x["IP"],x["port"]))
				self.writeToTextfile()
			if data['key'] == 'LOGOUT':
				for x in self.database:
					if x['username'] == data['username']:
						x['isOnline'] = False
						x['lastSeen'] = datetime.now() .strftime("%H:%M:%S")
				data['Loggedout'] = True	
				jsonString = json.dumps(data)
				self.server_sock.sendto(jsonString.encode() , (socket.gethostname(),int(addr[1])))	
			if data['key'] == 'GET_USERS':
				data['users'] = []
				for x in self.database:
					if data['username'] != x['username']:
						data['users'].append({'username':x['username'], 'isOnline':x['isOnline']})

				jsonString = json.dumps(data)
				self.server_sock.sendto(jsonString.encode() , (socket.gethostname(),int(addr[1])))	
			if data['key'] == 'GET_DATA':
				data['data'] = []
				for x in self.database:
					if x['username'] == data['username']:
						for y in x['chats']:
							if y['username'] == data['find']:
								data['data'] = y['messages']
				jsonString = json.dumps(data)
				self.server_sock.sendto(jsonString.encode() , (socket.gethostname(),int(addr[1])))	
			if data['key'] == 'GET_GROUP_DATA':
				data['group_data'] = []
				for x in self.database:
					if x['username'] == data['username']:
						if len(x['groupChats']) == 0:
							pass
						else:
							for y in x['groupChats']:
								data['group_data'].append(y)
				jsonString = json.dumps(data)
				self.server_sock.sendto(jsonString.encode() , (socket.gethostname(),int(addr[1])))	
"""
Description:
	main - resposible for running the server by creating an instance of the server class and using the rec method to start a thread that will be listenig to requests from clients 
"""	
def main():
	serverObj = Server()
	serverObj.readTextfile()
	serverOnline = threading.Thread(target = serverObj.rec)
	serverOnline.start()

	print("--> [type 'E' and press ENTER to turn server off]")
	ms = input()
	while ms !="E":
		pass
	print("--> [server is now offline]")
	serverOnline.close()
if __name__ == "__main__":
	main()
