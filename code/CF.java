import java.sql.*;
//ConnectionFactory => ���ӹ��� Ϊ�˻�����Ӷ�������~
public class CF{
	//��̬��ʼ���� ���һ�α����ص�ʱ��ִ�� ����������ʱ�� �������ݿ�����~
	static{
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");//��������~
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static Connection get(){//������������ṩһ��Connection��������
		String url = "jdbc:mysql://127.0.0.1:3306/sleep?useSSL=false&serverTimezone=Asia/Shanghai";
		String username = "root";
		String password = "yuguolong.0920..";
		try{
			return DriverManager.getConnection(url,username,password);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}