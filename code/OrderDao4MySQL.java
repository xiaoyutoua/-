import java.sql.*;
import java.awt.*;

public class OrderDao4MySQL implements OrderDao{
	public static void main(String[] args){
		OrderDao dao = new OrderDao4MySQL();
	}

	//增加菜谱的方法
	@Override
	public boolean increaseFood(String food,String price,String disc){
		String sql = "insert into foods(food,price,disc) values ('"+food+"','"+price+"','"+disc+"')";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();){
			int count = stmt.executeUpdate(sql);
			return count!=0;		//菜谱增加成功返回true,失败返回false

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//删除菜谱的方法
	@Override
	public boolean deleteFood(int id){
		String sql = "delete from foods where id= '"+id+"'";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();){
			int count = stmt.executeUpdate(sql);
			return count!=0;		//菜谱删除成功返回true，失败返回false

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//修改菜谱的方法
	@Override
	public boolean modifyFood(int id,String food,String price,String disc){
		String sql = "update foods set food ='"+food+"',price='"+price+"',disc='"+disc+"' where id='"+id+"'";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();){
			int count = stmt.executeUpdate(sql);
			return count!=0;		//菜谱修改成功返回true，失败返回false

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//查询菜谱的方法[根据id]
	@Override
	public boolean queryFood_1(int id){
		String sql = "select * from foods where id= '"+id+"'";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){

			return rs.next();

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	//查询菜谱的方法[根据菜名]
	@Override
	public boolean queryFood_2(String food){
		String sql = "select * from foods where food= '"+food+"'";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){

			return rs.next();

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	//查询菜谱的方法[根据价格]
	@Override
	public boolean queryFood_3(String price){
		String sql = "select * from foods where price= '"+price+"'";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){

			return rs.next();

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//浏览菜谱的方法1
	public String allFood(){
		String list="";
		String sql = "select id,food,price from foods";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){
			while(rs.next()){
				int id = rs.getInt("id");
				String food = rs.getString("food");
				String price = rs.getString("price");
				String ls = id+"_"+food+"_"+price;
				list = list+" "+ls;
				System.out.println(ls);
			}
			return list;

		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	//浏览菜谱的方法2
	public ResultSet getList(){
		String sql = "select id,food,price from foods";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){
			return rs;

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	//查询成功显示结果
	public ResultSet show_id(int id){
		String sql = "select food,price,disc from foods where food= '"+id+"'";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){
			return rs;

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet show_food(String food){
			String sql = "select food,price,disc from foods where food= '"+food+"'";
			try(Connection conn = CF.get();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
				return rs;

			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
	}

	public ResultSet show_price(String price){
			String sql = "select food,price,disc from foods where food= '"+price+"'";
			try(Connection conn = CF.get();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
				return rs;

			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
	}
}