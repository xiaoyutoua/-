import java.net.*;
import java.io.*;
//服务员 ： 接收客户的Request(点菜)  交给厨子做处理   返回客户Response(上菜)
public class EtoakThread extends Thread{
	Socket skt;
	public EtoakThread(Socket skt){
		this.skt = skt;
	}
	@Override
	public void run(){
		try{
			//接
			InputStream is = skt.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			Object obj = ois.readObject();
			Request req = (Request)obj;
			System.out.println("服务员完成客户点菜~");
			//化
			EtoakManager em = new EtoakManager();
			Response res = em.work(req);
			//发
			OutputStream os = skt.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(res);
			System.out.println("服务员完成客户上菜~");

			oos.close();
			ois.close();
			skt.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}