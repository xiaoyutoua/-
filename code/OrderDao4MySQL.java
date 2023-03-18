import java.sql.*;
import java.awt.*;

public class OrderDao4MySQL implements OrderDao{
	public static void main(String[] args){
		OrderDao dao = new OrderDao4MySQL();
	}

	//���Ӳ��׵ķ���
	@Override
	public boolean increaseFood(String food,String price,String disc){
		String sql = "insert into foods(food,price,disc) values ('"+food+"','"+price+"','"+disc+"')";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();){
			int count = stmt.executeUpdate(sql);
			return count!=0;		//�������ӳɹ�����true,ʧ�ܷ���false

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//ɾ�����׵ķ���
	@Override
	public boolean deleteFood(int id){
		String sql = "delete from foods where id= '"+id+"'";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();){
			int count = stmt.executeUpdate(sql);
			return count!=0;		//����ɾ���ɹ�����true��ʧ�ܷ���false

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//�޸Ĳ��׵ķ���
	@Override
	public boolean modifyFood(int id,String food,String price,String disc){
		String sql = "update foods set food ='"+food+"',price='"+price+"',disc='"+disc+"' where id='"+id+"'";
		try(Connection conn = CF.get();
			Statement stmt = conn.createStatement();){
			int count = stmt.executeUpdate(sql);
			return count!=0;		//�����޸ĳɹ�����true��ʧ�ܷ���false

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//��ѯ���׵ķ���[����id]
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
	//��ѯ���׵ķ���[���ݲ���]
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
	//��ѯ���׵ķ���[���ݼ۸�]
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

	//������׵ķ���1
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

	//������׵ķ���2
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


	//��ѯ�ɹ���ʾ���
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