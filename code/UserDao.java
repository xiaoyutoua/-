public interface UserDao{

	boolean exists(String username);//У���û����Ƿ��Ѿ���ռ��

	boolean checkUser(String username,String password);//����¼��

	boolean addUser(String username,String password);//ע�� ����û���

}