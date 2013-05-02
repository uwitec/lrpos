package main;

import googlemap.BestRoute;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

public class Service {
	ArrayList<Route> routes=new ArrayList<Route>();//索引代表司机id
	int numOfDriver;
	public Service(){
		numOfDriver=0;
	}
	public Service(int _numOfDriver) {
		// TODO Auto-generated constructor stub
		numOfDriver=_numOfDriver;
	}
	public void run(){//系统初始化
		for(int i=0;i<numOfDriver;i++){
			ClientInfo clientInfo=new ClientInfo();
			clientInfo.setClientPointByTxt("clientPoint.txt");
			Route route=new Route();
			route.setClientInfo(clientInfo);
			routes.add(route);////jbh,vnv
		}
	}
	public Route getBestRouteFromService(int taskId,int location) throws JSONException, InterruptedException{
		Route route=new Route();
		MyPoint[] points=routes.get(taskId).getClientInfo().getClientPoint();
		int len=points.length;
		//取location开始的所有点
		MyPoint[] points2=new MyPoint[len-location];
		for(int i=0;i<len-location;i++)
			points2[i]=points[i+location];
		BestRoute bestRoute=new BestRoute();
		List<MyPoint> list=new ArrayList<MyPoint>();
		for(int i=0;i<len-location;i++)
	    {
	        MyPoint a=new MyPoint();
	   		a.setpoint(points2[i].getpointX(),points2[i].getpointY());
	   		list.add(a); 
	   }
		List<MyPoint> list2=bestRoute.print_point(list);
		for(int i=0;i<list2.size()-1;i++){
			Line line=new Line();
			line.setLine(list2.get(i), list2.get(i+1));
			route.lineList.add(line);
		}
		return route;
	}
}
