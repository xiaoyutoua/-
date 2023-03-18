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
		frame = new JFrame("���׹���ϵͳ");
		top = new JPanel(new FlowLayout());
		center = new JPanel(new BorderLayout());
		bottom = new JPanel(new GridLayout(4,9));
		lab1 = new JLabel("�û�����");
		lab2 = new JLabel("���룺");
		pic = new JLabel(new ImageIcon("theme.png"));
		username = new JTextField(12);
		password = new JPasswordField(12);
		password.setEditable(false);//��ֹ�ֶ��༭

		register = new JButton("ע���û�");
		register.addActionListener((ae) -> {
			String name = username.getText();//����û���������������
			String pwd = password.getText();//����������е�����
			if(name.trim().length() == 0 || pwd.trim().length() == 0){//У�����Ƿ�Ϸ�
				JOptionPane.showMessageDialog(frame,"���أ���");
				return;
			}
			System.out.println("== ע������ ׼�����ӷ����� ==");//׼�����ӷ�����
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
					JOptionPane.showMessageDialog(frame,"ע��ɹ�");
				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"�û����Ѿ���ռ��~");
				}


			}catch(Exception e){
				e.printStackTrace();
			}


		});

		submit = new JButton("�ύ��¼");
		submit.addActionListener((ae) -> {
			String name = username.getText();//����û���������������
			String pwd = password.getText();//����������е�����
			if(name.trim().length() == 0 || pwd.trim().length() == 0){//У�����Ƿ�Ϸ�
				JOptionPane.showMessageDialog(frame,"���أ���");
				return;
			}
			System.out.println("== ��¼���� ׼�����ӷ����� ==");//׼�����ӷ�����

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
					JOptionPane.showMessageDialog(frame,"��¼�ɹ�");
					frame.setVisible(false);
					new CookbookFrame().showlist();//������ʾ��Ʒ�Ľ���
					//RecipeFrame.username = name; //��ȡ��¼�ɹ����û���
				}else if(result == 1){
					JOptionPane.showMessageDialog(frame,"�û���������");
				}else if(result == 2){
					JOptionPane.showMessageDialog(frame,"�������");
				}


			}catch(Exception e){
				e.printStackTrace();
			}
		});
		reset = new JButton("ȫ������");

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

		center.add(pic);//BorderLayout��center����

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

	//��Ա�ڲ��� Ϊ�˸����ɵķ��ʵ�ǰ�ⲿ�����Щ���
	class KeyboardListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae){
			//Object obj = ae.getSource();//�õ��¼�Դ����

			//�õ�����ָ�� Ĭ�ϵĶ���ָ���������ϵ��֣�
			String cmd = ae.getActionCommand();
			String old = password.getText();//�õ��������ԭ������
			password.setText(old + cmd);
		}
	}
}