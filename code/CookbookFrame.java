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
	JLabel lab1,lab2,lab3,title,intro,theme,pic;	//themeΪչʾ�ı�ǩ��picΪ�洢ͼƬ��ǩ
	JButton add,delete,reset,select1,select2,select3,Reset;
	JTextField id,name,price;
	java.awt.List list;
	OrderDao4MySQL orderDao = new OrderDao4MySQL();
	Map<String,Integer> nameAndPrice = new HashMap<>();

	public CookbookFrame()throws Exception{
		list=new java.awt.List();
		list.setFont(new Font("����",1,18));

		//Ϊlist��Ӽ���
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
			title.setText(a[1]+":  "+a[2]+"Ԫ");
			introduction.setText(c);
		});


		frame = new JFrame("���׹���ϵͳ");


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
		Font f2 = new Font("����",Font.BOLD,30);
		title = new JLabel("��բз �� 100 Ԫ");
		title.setFont(f2);
		title.setForeground(Color.red);
		introduction = new JTextArea("��һ������ˢ�Ӱ��Ϻ�зϴ�ɾ����ڶ��������������ȵ������տ������κ��Ĵ�բз�����£����������У��Ӹ���15���ӡ������������з���ʱ�䣬������������֭��׼��һ��С���룬���кõĴС�������ĩ���ټ�����������ȡ�������з�ס����ͣ��ÿ��ӽ��衣");
		introduction.setLineWrap(true);
		introduction.setEditable(false);
		introduction.setFont(new Font("����",1,18));
		intro = new JLabel("��������ϸ��������");
		introduce = new JTextArea("",3,40);
		ImageIcon bg=new ImageIcon("background.png");
		pic = new JLabel(bg);
		pic.setSize(bg.getIconWidth(),bg.getIconHeight());

		cc.setOpaque(false);

		Reset = new JButton("���");
		Reset.addActionListener((ae) ->{
			try{showlist();}catch(Exception e){e.printStackTrace();}
		});

		add = new JButton("����");
		add.addActionListener((ae) ->{
			String dishname = name.getText();
			String dishprice = price.getText();
			String intro2 = introduce.getText();
			if(dishname.trim().length() == 0 && dishprice.trim().length() != 0){
				JOptionPane.showMessageDialog(frame,"���������~");
				return;
			}else if(dishname.trim().length() != 0 && dishprice.trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"������۸�~");
				return;
			}else if(dishname.trim().length() == 0 && dishprice.trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"����������ͼ۸�~");
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
					JOptionPane.showMessageDialog(frame,"��ӳɹ�~");
					showlist();
				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"�������Ѿ����˴˲�Ʒ~");
				}

			}catch(Exception e){
				e.printStackTrace();
			}

		});



		//���ݲ���ɾ��
		delete = new JButton("ɾ��");
		delete.addActionListener((ae) ->{
			String dishname = name.getText();
			int dishid = queryFoodIdByName(dishname);

			if(dishname.trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"������Ҫɾ���Ĳ�Ʒ����~");
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
					JOptionPane.showMessageDialog(frame,"ɾ���ɹ�~");
					showlist();
				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"������û�д˲�Ʒ~");
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		});



		//����id�޸�
		reset = new JButton("�޸�");
		reset.addActionListener((ae) -> {
			String dishname = name.getText();
			String dishprice = price.getText();
			String intro2 = introduce.getText();
			int dishid = Integer.parseInt(id.getText());
			if((dishid+"").trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"������Ҫ�޸ĵĲ�Ʒid~");
				return;
			}
			else if(dishname.trim().length()==0 || dishprice.trim().length()==0 ||intro2.trim().length()==0){
				JOptionPane.showMessageDialog(frame,"����ȫ����Ҫ�޸ĵĲ�Ʒ��Ϣ~");
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
					JOptionPane.showMessageDialog(frame,"�޸ĳɹ�~");
					String sql = "update foods set food ='"+dishname+"',price='"+dishprice+"',disc='"+intro2+"' where id='"+dishid+"'";
					try(Connection conn = CF.get();
						Statement stmt = conn.createStatement();){
						int count = stmt.executeUpdate(sql);

					}catch(Exception e){
						e.printStackTrace();
					}
					showlist();
				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"û�д�idŶ~");
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		});

		//����id��ѯ
		select1 = new JButton("��ѯ4id");
		select1.addActionListener((ae) -> {
			int dishid = Integer.parseInt(id.getText());
			System.out.println(dishid);
			if((dishid+"").trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"������Ҫ��ѯ�Ĳ�Ʒid~");
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
					JOptionPane.showMessageDialog(frame,"��ѯ�ɹ�~");
					//��ѯ�ɹ����޸�title��introduction����
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
					JOptionPane.showMessageDialog(frame,"û���ҵ�idΪ"+dishid+"�Ĳ�Ʒ");
					}
			}catch(Exception e){
				e.printStackTrace();
			}

		});



		//���ݲ�����ѯ
		select2 = new JButton("��ѯ4����");
		select2.addActionListener((ae) -> {
			String dishname = name.getText();
			if(dishname.trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"������Ҫ��ѯ�Ĳ�Ʒ����~");
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
					JOptionPane.showMessageDialog(frame,"��ѯ�ɹ�~");
					//��ѯ�ɹ����޸�title��introduction����
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
					JOptionPane.showMessageDialog(frame,"û���ҵ�����Ϊ"+dishname+"�Ĳ�Ʒ~");
				}
			}catch(Exception e){
				e.printStackTrace();
			}

		});



		//���ݼ۸��ѯ
		select3 = new JButton("��ѯ4�۸�");
		select3.addActionListener((ae) -> {
			String dishprice = price.getText();
			System.out.println(dishprice);
			if(dishprice.trim().length() == 0){
				JOptionPane.showMessageDialog(frame,"������Ҫ��ѯ�Ĳ�Ʒ�۸�~");
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
					JOptionPane.showMessageDialog(frame,"��ѯ�ɹ�~");
					//��ѯ�ɹ����޸�title��introduction����
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
					JOptionPane.showMessageDialog(frame,"û���ҵ��۸�Ϊ"+dishprice+"�Ĳ�Ʒ~");
					}
			}catch(Exception e){
				e.printStackTrace();
			}

		});



		lab1 = new JLabel("ID");
		lab2 = new JLabel("����");
		lab3 = new JLabel("�۸�");

		id = new JTextField(12);
		name = new JTextField(12);
		price = new JTextField(12);

		addAll(sn,lab1,id,lab2,name,lab3,price);
		addAll(ss,add,delete,reset,select1,select2,select3,Reset);
		sc.add(intro);
		sc.add(introduce);
		cn.add(title);
		cc.add(introduction);

		Font f = new Font("����",Font.BOLD,16);
		theme = new JLabel(" ID    ����    �۸�");
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


	//���ݲ�������id�ķ���
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

	//��ʾȫ�����׵ķ���1
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
	//��ʾȫ�����׵ķ���2
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

