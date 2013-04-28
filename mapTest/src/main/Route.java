package main;
import googlemap.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

public class Route {
	ArrayList<Line> lineList=new ArrayList<Line>();//涓�潯璺敱璁稿绾跨粍鎴�	
	ArrayList<DriverInfo> driverInfos=new ArrayList<>();//鍙告満鍒楄〃
	DriverInfo curuentDriverInfo=new DriverInfo();//褰撳墠璁块棶鐨勫徃鏈�	
	int numberOfLine;//璺殑鏁伴噺
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
	public void updateBestTour(){//鏇存柊璺緞锛屾瘡璁块棶涓�锛屽鎴风偣-1
		
	}
	public ArrayList<Line> getRouteByTaskId(int taskId) throws JSONException, InterruptedException{//瀹㈡埛绔彁浜askid鑾峰彇鏈�匠璺嚎
		MyPoint[] points=null;
		for(int i=0;i<driverInfos.size();i++){
			if(driverInfos.get(i).getTaskId()==taskId){
				curuentDriverInfo=driverInfos.get(i);
				points=driverInfos.get(i).getClientInfo().getClientPoint();//鍙告満瀵瑰簲鐨勯�璐х偣
				break;
			}
		}
		if(points==null){
			System.out.print("error\n");
			return null;
		}
		updateBestTour();//鏇存柊璺緞
		//璁＄畻鏈�匠璺緞
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
