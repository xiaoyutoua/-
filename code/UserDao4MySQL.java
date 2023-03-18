import java.sql.*;
public class UserDao4MySQL implements UserDao{
	public static void main(String[] args){
		UserDao dao = new UserDao4MySQL();
		System.out.println(dao.checkUser("1","123"));
	}
	@Override
	public boolean exists(String username){//DQL
		String sql = "select id from user where username='"+username+"'";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){
			return rs.next();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean checkUser(String username,String password){//DQL
		String sql = "select id from user where username='"+username+"' and password='"+password+"'";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){
			return rs.next();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean addUser(String username,String password){//DML
		String sql = "insert into user(username,password) values('"+username+"','"+password+"')";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement()){
			int count = stmt.executeUpdate(sql);
			return count != 0;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}