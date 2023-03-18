public interface UserDao{

	boolean exists(String username);//校验用户名是否已经被占用

	boolean checkUser(String username,String password);//检查登录的

	boolean addUser(String username,String password);//注册 添加用户的

}