package main

import (
	"fmt"
	"libs"
	"net"
)

func main() {
	c, err := net.Dial("tcp", "127.0.0.1:22223")
	libs.CheckErr(err)
	defer c.Close()

	//c.Write([]byte("hahaha"))
	go send(c)

	buf := make([]byte, 128)
	for {
		len, err := c.Read(buf)
		if err == nil {
			fmt.Println(string(buf[0:len]))
		}
	}
}

func send(c net.Conn) {
	buf := make([]byte, 1)
	buf[0] = 2 // 1 java 2 go 3 python
	c.Write(buf)
	for {
		var msg string
		fmt.Println("go请输入:")
		fmt.Scanln(&msg)
		c.Write([]byte(msg))
		if msg == "886" {
			break
		}
	}
}
