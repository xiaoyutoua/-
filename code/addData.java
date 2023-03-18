import java.io.*;
import java.sql.*;
public class addData{
	//将获取到的数据导入到数据库当中
	public static void main(String[] args)throws Exception{
		File dir = new File("C:\\Users\\21431\\Desktop\\实训结束项目\\cfg");

		File[] fs = dir.listFiles((x) -> x.isFile()
		&& x.getName().toLowerCase().endsWith(".properties"));
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println(fs.length);
		//jdbc:mysql://127.0.0.1:3306/et666?useSSL=false&serverTimezone=Asia/Shanghai
		Connection conn = DriverManager
		.getConnection("jdbc:mysql://127.0.0.1:3306/sleep","root","yuguolong.0920..");
		Statement stmt = conn.createStatement();

		for(File f: fs){
			BufferedReader br = new BufferedReader(new FileReader(f));
			String str;
			while((str = br.readLine()) != null){
				String[] data = str.split("=");
				String sql =
				"insert into foods(food,price,disc) values('"
				+data[0]+"','"+data[1]+"','"+data[2]+"')";

				stmt.executeUpdate(sql);
			}
			br.close();
		}
		stmt.close();
		conn.close();
	}
}