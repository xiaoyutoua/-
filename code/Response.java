import java.io.*;

//���һ��ͳһ���ɺ�̨���ظ�ǰ̨������ ��������������~
public class Response implements Serializable{
	private int result = -1;
	public void setResult(int result){
		this.result = result;
	}
	public int getResult(){
		return result;
	}
}