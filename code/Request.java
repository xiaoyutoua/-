import java.io.*;
import java.util.*;

//���һ��ͳһ����ǰ̨���͸���̨������ ���г������� Я������~
public class Request implements Serializable{
	private int askNo;//��������  1001=ע��   1002=��¼
	private int id;
	public void setAskNo(int askNo){
		this.askNo = askNo;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getAskNo(){
		return askNo;
	}
	public int getId(){
		return this.id;
	}
	private Map<String,String> params = new HashMap<>();
	public void setParameter(String k,String v){
		params.put(k,v);
	}
	public String getParameter(String k){
		return params.get(k);
	}

	private String introduction;
	public void setIntro(String introduction){
		this.introduction = introduction;
	}
	public String getIntro(){
		return this.introduction;
	}
}



