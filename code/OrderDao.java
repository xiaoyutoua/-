public interface OrderDao{
	//���Ӳ��׵ķ���
	boolean increaseFood(String food,String price,String disc);

	//ɾ�����׵ķ���
	boolean deleteFood(int id);

	//�޸Ĳ��׵ķ���
	boolean modifyFood(int id,String food,String price,String disc);

	//��ѯ���׵ķ���
	boolean queryFood_1(int id);
	boolean queryFood_2(String food);
	boolean queryFood_3(String price);
}