import java.net.*;
import java.util.concurrent.*;
//���þ��� �� ֻ����Ӵ����˽���~
public class EtoakServer{
	public static void main(String[] args)throws Exception{
		ExecutorService es = Executors.newFixedThreadPool(5);
		ServerSocket server = new ServerSocket(6666);
		boolean isRunning = true;
		while(isRunning){
			Socket skt = server.accept();
			System.out.println("���þ���������� - ת������Ա~");
			//�ٻ��ڶ�����ɫ...�ٻ�����Ա �ź�ͻ�
			EtoakThread et = new EtoakThread(skt);
			es.submit(et);
		}
		server.close();
		es.shutdown();
	}
}