
print("server");

import socket
import sys

server = socket.socket(socket.AF_INET,socket.SOCK_STREAM)

host =socket.gethostname()
print("hostname:",host)
port = 22223

server.bind(("127.0.0.1",port))

server.listen(5)
print("开启服务器..");
while True:
	client,addr = server.accept()
	print("%s 前来报到.." % str(addr))

	msg = "哈喽，我是tcp服务器.."
	client.send(msg.encode('utf-8'))
	while True:
		msg = client.recv(1024)
		msg_u8 = msg.decode('utf-8')
		print(str(addr)+"说："+msg_u8)
		if msg_u8 == '886':
			break
	client.close()
