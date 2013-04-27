package main;
import googlemap.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

public class Route {
	ArrayList<Line> lineList=new ArrayList<Line>();//一条路由许多线组成
	ArrayList<DriverInfo> driverInfos=new ArrayList<>();//司机列表
	DriverInfo curuentDriverInfo=new DriverInfo();//当前访问的司机
	int numberOfLine;//路的数量
	Route(){
		numberOfLine=0;
	}
	public void init(){
		ClientInfo clientInfo=new ClientInfo();
		clientInfo.setClientPointByTxt("clientPoint.txt");
		DriverInfo driverInfo=new DriverInfo();
		driverInfo.setClientPoint(clientInfo);
		driverInfo.setNumOfClient(clientInfo.getNumberOfClient());
		driverInfos.add(driverInfo);
	}
	public void updateBestTour(){//更新路径，每访问一次，客户点-1
		
	}
	public ArrayList<Line> getRouteByTaskId(int taskId) throws JSONException, InterruptedException{//客户端提交taskid获取最佳路线
		MyPoint[] points=null;
		for(int i=0;i<driverInfos.size();i++){
			if(driverInfos.get(i).getTaskId()==taskId){
				curuentDriverInfo=driverInfos.get(i);
				points=driverInfos.get(i).getClientInfo().getClientPoint();//司机对应的送货点
				break;
			}
		}
		if(points==null){
			System.out.print("error\n");
			return null;
		}
		updateBestTour();//更新路径
		//计算最佳路径
		BestRoute bestRoute=new BestRoute();
		List<MyPoint> list=new ArrayList<MyPoint>();
		for(int i=0;i<points.length;i++){
			list.add(points[i]);
			System.out.println(points[i].getpointX());
		}
		bestRoute.print_point(list);
		return lineList;
	}
	public void setNumberOfLine(int n){
		numberOfLine=n;
	}
	public int getNumberOfLine(){
		return numberOfLine; 
	}
}
