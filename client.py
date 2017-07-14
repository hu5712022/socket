
print("py3 client")

import _thread
import socket
import sys

client = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
 
# client.connect((socket.gethostname(),8888))
client.connect(("127.0.0.1",22223))

def recv(c):
	while True:
		msg = c.recv(1024)
		if not msg:
		  break
		print(msg.decode('utf-8'))

_thread.start_new_thread(recv,(client, ))

# accept_data = str(client.recv(1024), encoding="utf8")
#  print(accept_data)
# msg = client.recv(1024)
# print(msg.decode('utf-8'))


b = ""
a = [3]
for i in range(len(a)):
     b += chr(a[i])
client.send(chr(a[0]).encode("utf-8"))
# while True:
# 	str = input();
# 	client.send(str.encode('utf-8'))
# 	if str == '886':
# 		break

# client.close()


while True:
	data=input('>')
	if not data:
		continue
	client.sendall(data.encode('utf-8'))

	# rdata=client.recv(1024)
	# if not rdata:
	# 	break
	# print(rdata.decode('utf-8'))
	
