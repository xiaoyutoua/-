import java.net.*;
import java.util.concurrent.*;
//大堂经理 ： 只负责接待客人进门~
public class EtoakServer{
	public static void main(String[] args)throws Exception{
		ExecutorService es = Executors.newFixedThreadPool(5);
		ServerSocket server = new ServerSocket(6666);
		boolean isRunning = true;
		while(isRunning){
			Socket skt = server.accept();
			System.out.println("大堂经理任务完成 - 转交服务员~");
			//召唤第二个角色...召唤服务员 伺候客户
			EtoakThread et = new EtoakThread(skt);
			es.submit(et);
		}
		server.close();
		es.shutdown();
	}
}