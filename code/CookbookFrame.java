import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.event.*;
import java.sql.*;

public class CookbookFrame{
	JFrame frame;
	JPanel center,south,east,cc,cn,sn,sc,ss,all_1;

	JTextArea introduction,introduce;
	JLabel lab1,lab2,lab3,title,intro,theme,pic;	//theme为展示的标签，pic为存储图片标签
	JButton add,delete,reset,select1,select2,select3,Reset;
	JTextField id,name,price;
	java.awt.List list;
	OrderDao4MySQL orderDao = new OrderDao4MySQL();
	Map<String,Integer> nameAndPrice = new HashMap<>();

	public CookbookFrame()throws Exception{
		list=new java.awt.List();
		list.setFont(new Font("宋体",1,18));

		//为list添加监听
		list.addActionListener(ae ->{
			String []a = new String[3];
			a = list.getSelectedItems();
			String b = a[0];
			a = b.split("  ");
			System.out.println(a[1]);
			String c = "";

			String sql = "select * from foods";
			try(Connection conn = CF.get();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
					while(rs.next()){
						if(rs.getInt("id") == Integer.parseInt(a[0])){
							c = rs.getString("disc");
						}
					}

			}catch(Exception e){e.printStackTrace();}

			System.out.println(a[1]+" "+a[2]+" "+c);
			title.setText(a[1]+":  "+a[2]+"元");
			introduction.setText(c);
		});


		frame = new JFrame("菜谱管理系统");


		center = new JPanel(new BorderLayout());
		east = new JPanel(new BorderLayout());
		south = new JPanel(new BorderLayout());
		cc = new JPanel(new BorderLayout());
		//cc=(JPanel)frame.getContentPane();
		cn = new JPanel(new FlowLayout());
		sn = new JPanel(new FlowLayout());
		sc = new JPanel(new FlowLayout());
		ss = new JPanel(new FlowLayout());

		//====================================
		Font f2 = new Font("宋体",Font.BOLD,30);
		title = new JLabel("大闸蟹 ： 100 元");
		title.setFont(f2);
		title.setForeground(Color.red);
		introduction = new JTextArea("第一步：用刷子把上海蟹洗干净。第二步：上蒸器，等到蒸笼烧开，阳澄湖的大闸蟹背朝下，放入蒸笼中，加盖蒸15分钟。第三步：蒸螃蟹这段时间，首先制作材料汁：准备一个小茶碗，加切好的葱、姜、蒜末，再加适量的生提取、特制螃蟹醋、香油，用筷子搅拌。");
		introduction.setLineWrap(true);
		introduction.setEditable(false);
		introduction.setFont(new Font("宋体",1,18));
		intro = new JLabel("请输入详细制作过程");
		introduce = new JTextArea("",3,40);
		ImageIcon bg=new ImageIcon("background.png");
		pic = new JLabel(bg);
		pic.setSize(bg.getIconWidth(),bg.getIconHeight());

		cc.setOpaque(false);

		Reset = new JButton("浏览");
		Reset.addActionListener((ae) ->{
			try{showlist();}catch(Exception e){e.printStackTrace();}
		});

		add = new JButton("增加");
		add.addActionListener((ae) ->{
			String dishname = name.getText();
			String dishprice = price.getText();
			String intro2 = introduce.getText();
			if(dishname.trim().length() == 0 && dishprice.trim().length() != 0){
				JOptionPane.showMessageDialog(frame,"请输入菜名~");
				return;
			}else if(dishname.trim().length() != 0 && dishprice.trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"请输入价格~");
				return;
			}else if(dishname.trim().length() == 0 && dishprice.trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"请输入菜名和价格~");
				return;
			}

			try{
				Request req = new Request();
				req.setAskNo(1003);
				req.setParameter("dishname",dishname);
				req.setParameter("dishprice",dishprice);
				req.setIntro(intro2);

				Socket skt = new Socket("127.0.0.1",6666);
				OutputStream os = skt.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(req);

				InputStream is = skt.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Object obj = ois.readObject();
				Response res = (Response)obj;

				ois.close();
				oos.close();
				skt.close();

				int result = res.getResult();
				if(result == 0){
					JOptionPane.showMessageDialog(frame,"添加成功~");
					showlist();
				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"菜谱中已经有了此菜品~");
				}

			}catch(Exception e){
				e.printStackTrace();
			}

		});



		//根据菜名删除
		delete = new JButton("删除");
		delete.addActionListener((ae) ->{
			String dishname = name.getText();
			int dishid = queryFoodIdByName(dishname);

			if(dishname.trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"请输入要删除的菜品名字~");
				return;
			}

			try{
				Request req = new Request();
				req.setAskNo(1004);
				req.setId(dishid);

				Socket skt = new Socket("127.0.0.1",6666);
				OutputStream os = skt.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(req);

				InputStream is = skt.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Object obj = ois.readObject();
				Response res = (Response)obj;

				ois.close();
				oos.close();
				skt.close();

				int result = res.getResult();
				if(result == 0){
					JOptionPane.showMessageDialog(frame,"删除成功~");
					showlist();
				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"菜谱中没有此菜品~");
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		});



		//根据id修改
		reset = new JButton("修改");
		reset.addActionListener((ae) -> {
			String dishname = name.getText();
			String dishprice = price.getText();
			String intro2 = introduce.getText();
			int dishid = Integer.parseInt(id.getText());
			if((dishid+"").trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"请输入要修改的菜品id~");
				return;
			}
			else if(dishname.trim().length()==0 || dishprice.trim().length()==0 ||intro2.trim().length()==0){
				JOptionPane.showMessageDialog(frame,"请完全输入要修改的菜品信息~");
				return;
			}

			try{
				Request req = new Request();
				req.setAskNo(1005);
				req.setParameter("dishname",dishname);
				req.setParameter("dishprice",dishprice);
				req.setId(dishid);
				req.setIntro(intro2);

				Socket skt = new Socket("127.0.0.1",6666);
				OutputStream os = skt.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(req);

				InputStream is = skt.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Object obj = ois.readObject();
				Response res = (Response)obj;

				ois.close();
				oos.close();
				skt.close();

				int result = res.getResult();
				if(result == 0){
					JOptionPane.showMessageDialog(frame,"修改成功~");
					String sql = "update foods set food ='"+dishname+"',price='"+dishprice+"',disc='"+intro2+"' where id='"+dishid+"'";
					try(Connection conn = CF.get();
						Statement stmt = conn.createStatement();){
						int count = stmt.executeUpdate(sql);

					}catch(Exception e){
						e.printStackTrace();
					}
					showlist();
				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"没有此id哦~");
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		});

		//根据id查询
		select1 = new JButton("查询4id");
		select1.addActionListener((ae) -> {
			int dishid = Integer.parseInt(id.getText());
			System.out.println(dishid);
			if((dishid+"").trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"请输入要查询的菜品id~");
				return;
			}
			try{
				Request req = new Request();
				req.setAskNo(1006);
				req.setId(dishid);

				Socket skt = new Socket("127.0.0.1",6666);
				OutputStream os = skt.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(req);

				InputStream is = skt.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Object obj = ois.readObject();
				Response res = (Response)obj;

				ois.close();
				oos.close();
				skt.close();

				int result = res.getResult();
				if(result == 0){
					JOptionPane.showMessageDialog(frame,"查询成功~");
					//查询成功，修改title和introduction内容
					String sql = "select * from foods";
					try(Connection conn = CF.get();
						Statement stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery(sql)){
							list.removeAll();
						while(rs.next()){
							if(rs.getInt("id") == Integer.parseInt(id.getText())){
								String l = String.valueOf(rs.getInt("id"))+"  "+rs.getString("food")+"  "+rs.getString("price");
								list.add(l);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"没有找到id为"+dishid+"的菜品");
					}
			}catch(Exception e){
				e.printStackTrace();
			}

		});



		//根据菜名查询
		select2 = new JButton("查询4菜名");
		select2.addActionListener((ae) -> {
			String dishname = name.getText();
			if(dishname.trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"请输入要查询的菜品名称~");
				return;
			}
			try{
				Request req = new Request();
				req.setAskNo(1007);
				req.setParameter("dishname",dishname);

				Socket skt = new Socket("127.0.0.1",6666);
				OutputStream os = skt.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(req);

				InputStream is = skt.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Object obj = ois.readObject();
				Response res = (Response)obj;

				ois.close();
				oos.close();
				skt.close();

				int result = res.getResult();
				if(result == 0){
					JOptionPane.showMessageDialog(frame,"查询成功~");
					//查询成功，修改title和introduction内容
					String sql = "select * from foods ";
					try(Connection conn = CF.get();
						Statement stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery(sql)){
							list.removeAll();
							while(rs.next()){
								if(dishname.equals(rs.getString("food"))){
									String l = String.valueOf(rs.getInt("id"))+"  "+rs.getString("food")+"  "+rs.getString("price");
									list.add(l);
								}

							}

					}catch(Exception e){
						e.printStackTrace();
					}

				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"没有找到菜名为"+dishname+"的菜品~");
				}
			}catch(Exception e){
				e.printStackTrace();
			}

		});



		//根据价格查询
		select3 = new JButton("查询4价格");
		select3.addActionListener((ae) -> {
			String dishprice = price.getText();
			System.out.println(dishprice);
			if(dishprice.trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"请输入要查询的菜品价格~");
				return;
			}
			try{
				Request req = new Request();
				req.setAskNo(1008);
				req.setParameter("dishprice",dishprice);

				Socket skt = new Socket("127.0.0.1",6666);
				OutputStream os = skt.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(req);

				InputStream is = skt.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Object obj = ois.readObject();
				Response res = (Response)obj;

				ois.close();
				oos.close();
				skt.close();

				int result = res.getResult();
				if(result == 0){
					JOptionPane.showMessageDialog(frame,"查询成功~");
					//查询成功，修改title和introduction内容
					String sql = "select * from foods";

					try(Connection conn = CF.get();
						Statement stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery(sql)){
							list.removeAll();
						while(rs.next()){
							if(rs.getString("price").equals(price.getText())){
								String l = String.valueOf(rs.getInt("id"))+"  "+rs.getString("food")+"  "+rs.getString("price");
								list.add(l);
							}

						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"没有找到价格为"+dishprice+"的菜品~");
					}
			}catch(Exception e){
				e.printStackTrace();
			}

		});



		lab1 = new JLabel("ID");
		lab2 = new JLabel("菜名");
		lab3 = new JLabel("价格");

		id = new JTextField(12);
		name = new JTextField(12);
		price = new JTextField(12);

		addAll(sn,lab1,id,lab2,name,lab3,price);
		addAll(ss,add,delete,reset,select1,select2,select3,Reset);
		sc.add(intro);
		sc.add(introduce);
		cn.add(title);
		cc.add(introduction);

		Font f = new Font("宋体",Font.BOLD,16);
		theme = new JLabel(" ID    菜名    价格");
		theme.setFont(f);
		theme.setForeground(Color.red);
		east.add(theme,"North");
		east.add(list);

		south.add(sn,"North");
		south.add(sc);
		south.add(ss,"South");
		center.add(cn,"North");
		center.add(cc);

		frame.add(center);
		frame.add(south,"South");
		frame.add(east,"East");

		//all_1.add(south,"South");
		//all_1.add(center);
		//frame.add(all_1);
		//frame.add(east,"East");

		frame.setSize(1000,680);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);

	}

	public static void addAll(Container con,Component ... cs){
		for(Component c : cs){
			con.add(c);
		}
	}


	//根据菜名查找id的方法
	public int queryFoodIdByName(String name){
			String sql = "select id from foods where food ='"
			+name+"'";
			try(Connection conn = CF.get();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){

				if(rs.next()) return rs.getInt("id");
				return 0;

			}catch(Exception e){
				e.printStackTrace();
				return 0;
			}
	}

	//显示全部菜谱的方法1
	public void showlist_2()throws Exception{
		list.removeAll();
		String list1 = orderDao.allFood();
		System.out.println(list1);
		String[] list2 = list1.split(" ");
		String list3 = Arrays.toString(list2);
		for(int i=0;i<list2.length;i++)
		{
			list.add(list2[i]);
			System.out.println(list2[i]+"123456");
		}
		System.out.println(list);

	}
	//显示全部菜谱的方法2
	public void showlist()throws Exception{
		String sql = "select * from foods";
		list.removeAll();
		try(Connection conn = CF.get();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql)){
			String l;
			while(rs.next()){
				l = String.valueOf(rs.getInt("id"))+"  "+rs.getString("food")+"  "+rs.getString("price");
//				System.out.println(l);
				list.add(l);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public void showlistfood()throws Exception{
		String dishname = name.getText();
		String sql = "select * from foods";
		try(Connection conn = CF.get();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql)){
			String l;
			while(rs.next()){
				if(dishname.equals(rs.getString("food"))){
					l = String.valueOf(rs.getInt(1))+" "+rs.getString(2)+" "+rs.getString(3);
					list.add(l);
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args)throws Exception{
			new CookbookFrame();
	}
}

