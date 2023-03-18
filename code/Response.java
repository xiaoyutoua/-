import java.io.*;

//设计一个统一的由后台返回给前台的类型 当中描述处理结果~
public class Response implements Serializable{
	private int result = -1;
	public void setResult(int result){
		this.result = result;
	}
	public int getResult(){
		return result;
	}
}