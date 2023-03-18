import java.net.*;
import java.io.*;
//����Ա �� ���տͻ���Request(���)  ��������������   ���ؿͻ�Response(�ϲ�)
public class EtoakThread extends Thread{
	Socket skt;
	public EtoakThread(Socket skt){
		this.skt = skt;
	}
	@Override
	public void run(){
		try{
			//��
			InputStream is = skt.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			Object obj = ois.readObject();
			Request req = (Request)obj;
			System.out.println("����Ա��ɿͻ����~");
			//��
			EtoakManager em = new EtoakManager();
			Response res = em.work(req);
			//��
			OutputStream os = skt.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(res);
			System.out.println("����Ա��ɿͻ��ϲ�~");

			oos.close();
			ois.close();
			skt.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}