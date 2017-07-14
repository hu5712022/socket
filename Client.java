

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	
	public static void main(String[] args) throws IOException {
		Socket s = new Socket();
		
		s.connect(new InetSocketAddress("127.0.0.1",22223));
		OutputStream os = s.getOutputStream();
		new ClientSendThread(os).start();
		new ClientRecvThread(s.getInputStream()).start();
		
	}
}
class ClientRecvThread extends Thread{
	InputStream is;
	private byte[] bufs;
	public ClientRecvThread(InputStream i) {
		is= i;
	}
	@Override
	public void run() {
		bufs = new byte[128];
		while(true){
			try {
				int len = is.read(bufs);
				if(len!=-1){
					System.out.println(new String(bufs,0,len));
				}
				
			} catch (IOException e) {
				try {
					is.close();
				} catch (IOException e1) {
					return;
				}
				return;
				
			}
		}
	}
	
}

class ClientSendThread extends Thread{
	OutputStream os;
	private Scanner scanner = new Scanner(System.in);
	public ClientSendThread(OutputStream o) {
		os = o;
		try {
			// 1 java 2 go 3 python
			os.write(new byte[]{1});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		while (true) {
			System.out.println("请输入：");
			try {
				os.write(scanner.nextLine().getBytes());
			} catch (IOException e) {
				try {
					os.close();
				} catch (IOException e1) {
					return;
				}
				return;
			}
		}
	}
}
