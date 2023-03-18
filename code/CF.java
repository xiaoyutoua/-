import java.sql.*;
//ConnectionFactory => 连接工厂 为了获得连接而调用它~
public class CF{
	//静态初始化块 类第一次被加载的时候执行 加载这个类的时候 加载数据库驱动~
	static{
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动~
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static Connection get(){//这个方法用于提供一个Connection给调用者
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