import java.util.*;
//厨子 ： 处理核心业务~
public class EtoakManager{

	UserDao userDao = new UserDao4MySQL();
	OrderDao orderDao = new OrderDao4MySQL();

	public Response work(Request req){
		Response res = new Response();
		System.out.println("大厨准备做菜~");

		int askNo = req.getAskNo();
		if(askNo == 1001){//代表注册
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			int result = register(username,password);
			res.setResult(result);
		}else if(askNo == 1002){//代表登录
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			int result = login(username,password);
			res.setResult(result);
		}else if (askNo==1003){	//代表增加
			String food = req.getParameter("dishname");
			String price = req.getParameter("dishprice");
			String disc = req.getIntro();
			int result = increase_1(food,price,disc);
			res.setResult(result);
		}else if(askNo==1004){	//代表删除
			int id = req.getId();
			int result = delete_1(id);
			res.setResult(result);
		}else if(askNo==1005){	//代表修改
			int id = req.getId();
			String food = req.getParameter("dishname");
			String price = req.getParameter("dishprice");
			String disc = req.getIntro();
			int result = modify_1(id,food,price,disc);
			res.setResult(result);
		}else if(askNo==1006){	//代表id查询
			int id = req.getId();
			int result = query_1(id);
			res.setResult(result);
		}else if(askNo==1007){	//代表菜名查询
			String food = req.getParameter("dishname");
			int result = query_2(food);
			res.setResult(result);
		}else if(askNo==1008){	//代表价格查询
			String price= req.getParameter("dishprice");
			int result = query_3(price);
			res.setResult(result);
		}
		return res;
	}

	//注册的方法
	private int register(String username,String password){
		System.out.println("== 注册的专属方法已经执行了 ==");

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
			首先加载所有已经注册的用户的信息
			检查这次想要注册的用户名是否已经被占用
				如果已经被占用 就不允许再注册了
			如果没有被占用 那就将新的用户名和密码 记录到Map当中
			然后将Map存储到文件里~
			Map<String,String> userMap = EtoakFAO.load();//读取用户数据
			if(userMap.containsKey(username))
				return 1; //用户名已经被占用 不能注册了~！
			userMap.put(username,password);
			EtoakFAO.save(userMap);//保存处理完成的用户数据
			return 0; //返回0代表成功~
		*/

	}


	//登录的方法
	private int login(String username,String password){
		System.out.println("== 登录的专属方法开始执行了 ==");

		if(!userDao.exists(username)){
			return 1;//用户名不存在
		}
		else{
			if(userDao.checkUser(username,password)){
				return 0;
			}else{
				return 2;//密码错误
			}
		}
	}

		/*
			首先需要加载所有注册用户的信息
			检查登录的这个用户名不存在
				如果不存在 则直接返回用户名不存在的错误编码1
			拿到用户注册的时候的密码
			判断和这次登录的时候填写的是否一致
				一致返回0      0代表正确
				不一致返回2    2代表密码错误
			Map<String,String> userMap = EtoakFAO.load();//加载用户数据
			if(!userMap.containsKey(username))
				return 1;
			String realPassword = userMap.get(username);
			return realPassword.equals(password) ? 0 : 2 ;
		*/


	//增加菜谱的方法
	public int increase_1(String food,String price,String disc)
	{
		System.out.println("== 增加菜谱的方法开始执行了 ==");
		if(orderDao.queryFood_2(food)) return 1;	//菜谱已经有此菜品
		else
		{
			if(orderDao.increaseFood(food,price,disc)){
				return 0;	//增加菜谱成功
			}
			else return 2;	//增加菜谱失败
		}
	}
	//删除菜谱的方法
	public int delete_1(int id){
		System.out.println("== 增加菜谱的方法开始执行了 ==");
		if(orderDao.deleteFood(id)) return 0;	//菜谱增加成功
		else return 1;	//菜谱删除失败
	}

	//修改菜谱的方法
	public int modify_1(int id,String food,String price,String disc){
		System.out.println("== 增加菜谱的方法开始执行了 ==");
		if(orderDao.modifyFood(id,food,price,disc)) return 0;	//菜谱修改成功
		else return 1;	//菜谱修改失败
	}

	//根据id查询菜谱的方法
	public int query_1(int id){
		System.out.println("== 增加菜谱的方法开始执行了 ==");
		if(orderDao.queryFood_1(id)) return 0;	//菜谱查询成功
		else return 1;	//菜谱查询失败
	}
	//根据菜名查询菜谱的方法
		public int query_2(String food){
			System.out.println("== 增加菜谱的方法开始执行了 ==");
			if(orderDao.queryFood_2(food)) return 0;	//菜谱增加成功
			else return 1;	//菜谱查询失败
	}
	//根据价格查询菜谱的方法
		public int query_3(String price){
			System.out.println("== 增加菜谱的方法开始执行了 ==");
			if(orderDao.queryFood_3(price)) return 0;	//菜谱增加成功
			else return 1;	//菜谱查询失败
	}

}