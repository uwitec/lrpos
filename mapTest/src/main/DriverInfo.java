package main;

public class DriverInfo {
	int taskId;//鍙告満id
	ClientInfo clientPoint;
	int numberOfClient;//瀹㈡埛鍦板潃鏁�	
	int flag;//鏄惁绗竴娆¤闂湇鍔″櫒
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
