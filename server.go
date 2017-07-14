package main

import (
	"fmt"
	. "libs"
	"net"
	//"os"
	//	"time"
)

func main() {
	l, err := net.Listen("tcp", "127.0.0.1:12345")
	CheckErr(err)
	defer l.Close()
	fmt.Println("开启服务器")
	for {
		c, err := l.Accept()
		CheckErr(err)
		go recv(c)
	}

	//time.Sleep(time.Second * 60)

}

func recv(c net.Conn) {
	fmt.Println(c.RemoteAddr().String())
	c.Write([]byte("fuck"))

	buf := make([]byte, 10)
	for {
		len, err := c.Read(buf)
		if err == nil {
			fmt.Println(string(buf[0:len]))
		}
	}
}
