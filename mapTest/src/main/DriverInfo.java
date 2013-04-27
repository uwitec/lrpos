package main;

public class DriverInfo {
	int taskId;//司机id
	ClientInfo clientPoint;
	int numberOfClient;//客户地址数
	int flag;//是否第一次访问服务器
	public DriverInfo() {
		// TODO Auto-generated constructor stub
		flag=0;
	}
	public void setTaskId(int taskid){
		taskId=taskid;
	}
	public void setClientPoint(ClientInfo client){
		clientPoint=client;
	}
	public void setNumOfClient(int n){
		numberOfClient=n;
	}
	public int getTaskId(){
		return taskId;
	}
	public int getNumberOfClient(){
		return numberOfClient;
	}
	public ClientInfo getClientInfo(){
		return clientPoint;
	}
}
