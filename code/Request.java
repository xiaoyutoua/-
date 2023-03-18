import java.io.*;
import java.util.*;

//设计一个统一的由前台发送给后台的类型 当中陈述来意 携带数据~
public class Request implements Serializable{
	private int askNo;//连接来意  1001=注册   1002=登录
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



