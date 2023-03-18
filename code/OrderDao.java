public interface OrderDao{
	//增加菜谱的方法
	boolean increaseFood(String food,String price,String disc);

	//删除菜谱的方法
	boolean deleteFood(int id);

	//修改菜谱的方法
	boolean modifyFood(int id,String food,String price,String disc);

	//查询菜谱的方法
	boolean queryFood_1(int id);
	boolean queryFood_2(String food);
	boolean queryFood_3(String price);
}