import java.util.*;
//���� �� �������ҵ��~
public class EtoakManager{

	UserDao userDao = new UserDao4MySQL();
	OrderDao orderDao = new OrderDao4MySQL();

	public Response work(Request req){
		Response res = new Response();
		System.out.println("���׼������~");

		int askNo = req.getAskNo();
		if(askNo == 1001){//����ע��
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			int result = register(username,password);
			res.setResult(result);
		}else if(askNo == 1002){//�����¼
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			int result = login(username,password);
			res.setResult(result);
		}else if (askNo==1003){	//��������
			String food = req.getParameter("dishname");
			String price = req.getParameter("dishprice");
			String disc = req.getIntro();
			int result = increase_1(food,price,disc);
			res.setResult(result);
		}else if(askNo==1004){	//����ɾ��
			int id = req.getId();
			int result = delete_1(id);
			res.setResult(result);
		}else if(askNo==1005){	//�����޸�
			int id = req.getId();
			String food = req.getParameter("dishname");
			String price = req.getParameter("dishprice");
			String disc = req.getIntro();
			int result = modify_1(id,food,price,disc);
			res.setResult(result);
		}else if(askNo==1006){	//����id��ѯ
			int id = req.getId();
			int result = query_1(id);
			res.setResult(result);
		}else if(askNo==1007){	//���������ѯ
			String food = req.getParameter("dishname");
			int result = query_2(food);
			res.setResult(result);
		}else if(askNo==1008){	//����۸��ѯ
			String price= req.getParameter("dishprice");
			int result = query_3(price);
			res.setResult(result);
		}
		return res;
	}

	//ע��ķ���
	private int register(String username,String password){
		System.out.println("== ע���ר�������Ѿ�ִ���� ==");

		if(userDao.exists(username)){
			return 1;//
		}else{
			if(userDao.addUser(username,password)){
				return 0;
			}else{
				return 2;
			}
		}

		/*
			���ȼ��������Ѿ�ע����û�����Ϣ
			��������Ҫע����û����Ƿ��Ѿ���ռ��
				����Ѿ���ռ�� �Ͳ�������ע����
			���û�б�ռ�� �Ǿͽ��µ��û��������� ��¼��Map����
			Ȼ��Map�洢���ļ���~
			Map<String,String> userMap = EtoakFAO.load();//��ȡ�û�����
			if(userMap.containsKey(username))
				return 1; //�û����Ѿ���ռ�� ����ע����~��
			userMap.put(username,password);
			EtoakFAO.save(userMap);//���洦����ɵ��û�����
			return 0; //����0����ɹ�~
		*/

	}


	//��¼�ķ���
	private int login(String username,String password){
		System.out.println("== ��¼��ר��������ʼִ���� ==");

		if(!userDao.exists(username)){
			return 1;//�û���������
		}
		else{
			if(userDao.checkUser(username,password)){
				return 0;
			}else{
				return 2;//�������
			}
		}
	}

		/*
			������Ҫ��������ע���û�����Ϣ
			����¼������û���������
				��������� ��ֱ�ӷ����û��������ڵĴ������1
			�õ��û�ע���ʱ�������
			�жϺ���ε�¼��ʱ����д���Ƿ�һ��
				һ�·���0      0������ȷ
				��һ�·���2    2�����������
			Map<String,String> userMap = EtoakFAO.load();//�����û�����
			if(!userMap.containsKey(username))
				return 1;
			String realPassword = userMap.get(username);
			return realPassword.equals(password) ? 0 : 2 ;
		*/


	//���Ӳ��׵ķ���
	public int increase_1(String food,String price,String disc)
	{
		System.out.println("== ���Ӳ��׵ķ�����ʼִ���� ==");
		if(orderDao.queryFood_2(food)) return 1;	//�����Ѿ��д˲�Ʒ
		else
		{
			if(orderDao.increaseFood(food,price,disc)){
				return 0;	//���Ӳ��׳ɹ�
			}
			else return 2;	//���Ӳ���ʧ��
		}
	}
	//ɾ�����׵ķ���
	public int delete_1(int id){
		System.out.println("== ���Ӳ��׵ķ�����ʼִ���� ==");
		if(orderDao.deleteFood(id)) return 0;	//�������ӳɹ�
		else return 1;	//����ɾ��ʧ��
	}

	//�޸Ĳ��׵ķ���
	public int modify_1(int id,String food,String price,String disc){
		System.out.println("== ���Ӳ��׵ķ�����ʼִ���� ==");
		if(orderDao.modifyFood(id,food,price,disc)) return 0;	//�����޸ĳɹ�
		else return 1;	//�����޸�ʧ��
	}

	//����id��ѯ���׵ķ���
	public int query_1(int id){
		System.out.println("== ���Ӳ��׵ķ�����ʼִ���� ==");
		if(orderDao.queryFood_1(id)) return 0;	//���ײ�ѯ�ɹ�
		else return 1;	//���ײ�ѯʧ��
	}
	//���ݲ�����ѯ���׵ķ���
		public int query_2(String food){
			System.out.println("== ���Ӳ��׵ķ�����ʼִ���� ==");
			if(orderDao.queryFood_2(food)) return 0;	//�������ӳɹ�
			else return 1;	//���ײ�ѯʧ��
	}
	//���ݼ۸��ѯ���׵ķ���
		public int query_3(String price){
			System.out.println("== ���Ӳ��׵ķ�����ʼִ���� ==");
			if(orderDao.queryFood_3(price)) return 0;	//�������ӳɹ�
			else return 1;	//���ײ�ѯʧ��
	}

}