

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Server {
	static ArrayList<AcceptThread> list = new ArrayList<AcceptThread>();

	public static void main(String[] args) throws IOException {

		ServerSocket ss = new ServerSocket(22223);

		while (true) {
			Socket accept = ss.accept();
			AcceptThread t = new AcceptThread(accept);
			t.start();
			list.add(t);

		}
	}
}

class AcceptThread extends Thread {
	Socket accept;
	OutputStream os;
	InputStream is;
	String name;
	String ip;
	int port;

	public AcceptThread(Socket s) {
		accept = s;
		try {
			ip =accept.getInetAddress().getHostName();
			port = accept.getPort();
			os = accept.getOutputStream();
			is = accept.getInputStream();
			byte[] bf = new byte[1];
			is.read(bf);
			if(bf[0] == 1){
				name = "java 老爷爷";
			}else if(bf[0] == 2){
				name = "go 哥";
			}else if(bf[0] == 3){
				name = "python 妹";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public OutputStream getOS() {
		return os;
	}

	@Override
	public void run() {
		while (true) {
			byte[] buf = new byte[128];
			try {
				int len = is.read(buf);
				if(len==-1){
					accept.close();
					os= null;
					return;
				}
				String msg = new String(buf, 0, len);
				System.out.println(msg);
				if (msg.length() > 0) {
					for (int i = 0; i < Server.list.size(); i++) {
						if (!Server.list.get(i).equals(this)) {
							OutputStream os = Server.list.get(i).getOS();
						
							if (os != null) {
								os.write((name+"("+port+")说:").getBytes());
								os.write(buf, 0, len);
							}
						}
					}
				}

			} catch (IOException e) {
				try {
					accept.close();
					os= null;
					return;
				} catch (IOException e1) {
				}
			}
		}

	}
}
