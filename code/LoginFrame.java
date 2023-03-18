import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
public class LoginFrame{
	JFrame frame;
	JPanel top,center,bottom;
	JLabel lab1,lab2,pic;
	JTextField username,password;
	JButton register,submit,reset;
	JButton[] bts;
	public LoginFrame(){
		frame = new JFrame("菜谱管理系统");
		top = new JPanel(new FlowLayout());
		center = new JPanel(new BorderLayout());
		bottom = new JPanel(new GridLayout(4,9));
		lab1 = new JLabel("用户名：");
		lab2 = new JLabel("密码：");
		pic = new JLabel(new ImageIcon("theme.png"));
		username = new JTextField(12);
		password = new JPasswordField(12);
		password.setEditable(false);//禁止手动编辑

		register = new JButton("注册用户");
		register.addActionListener((ae) -> {
			String name = username.getText();//获得用户名框框里面的内容
			String pwd = password.getText();//获得密码框框当中的内容
			if(name.trim().length() == 0 || pwd.trim().length() == 0){//校验其是否合法
				JOptionPane.showMessageDialog(frame,"手呢？！");
				return;
			}
			System.out.println("== 注册请求 准备连接服务器 ==");//准备连接服务器
			try{
				Request req = new Request();
				req.setAskNo(1001);
				req.setParameter("username",name);
				req.setParameter("password",pwd);

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
					JOptionPane.showMessageDialog(frame,"注册成功");
				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"用户名已经被占用~");
				}


			}catch(Exception e){
				e.printStackTrace();
			}


		});

		submit = new JButton("提交登录");
		submit.addActionListener((ae) -> {
			String name = username.getText();//获得用户名框框里面的内容
			String pwd = password.getText();//获得密码框框当中的内容
			if(name.trim().length() == 0 || pwd.trim().length() == 0){//校验其是否合法
				JOptionPane.showMessageDialog(frame,"手呢？！");
				return;
			}
			System.out.println("== 登录请求 准备连接服务器 ==");//准备连接服务器

			try{
				Request req = new Request();
				req.setAskNo(1002);
				req.setParameter("username",name);
				req.setParameter("password",pwd);

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
					JOptionPane.showMessageDialog(frame,"登录成功");
					frame.setVisible(false);
					new CookbookFrame().showlist();//弹出显示商品的界面
					//RecipeFrame.username = name; //获取登录成功的用户名
				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"用户名不存在");
				}else if(result == 2){
					JOptionPane.showMessageDialog(frame,"密码错误");
				}


			}catch(Exception e){
				e.printStackTrace();
			}
		});
		reset = new JButton("全部重置");

		reset.addActionListener((ae) -> {
			username.setText("");
			password.setText("");
		});

		bts = new JButton[36];

		KeyboardListener kl = new KeyboardListener();
		for(int i = 0;i<bts.length;i++){
			bts[i] = new JButton(i<10 ? i+"" : (char)(i+87)+"");
			bts[i].setBackground(new Color((int)(Math.random()*36)+220,(int)(Math.random()*36)+220,(int)(Math.random()*36)+220));
			bts[i].addActionListener(kl);
			bottom.add(bts[i]);
		}

		center.add(pic);//BorderLayout的center区域

		addAll(top,lab1,username,lab2,password,register,submit,reset);

		frame.add(top,"North");
		frame.add(center);
		frame.add(bottom,"South");

		frame.setSize(1000,680);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);

	}
	public static void addAll(Container con,Component ... cs){
		for(Component c : cs){
			con.add(c);
		}
	}

	public static void main(String[] args){
		new LoginFrame();
	}

	//成员内部类 为了更轻松的访问当前外部类的那些组件
	class KeyboardListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae){
			//Object obj = ae.getSource();//得到事件源对象

			//得到动作指令 默认的动作指令就是组件上的字！
			String cmd = ae.getActionCommand();
			String old = password.getText();//得到密码框当中原有内容
			password.setText(old + cmd);
		}
	}
}