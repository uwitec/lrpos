package main;
import googlemap.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;


public class testClientInfo {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws JSONException, InterruptedException {
		// TODO Auto-generated method stub
		ClientInfo clientInfo=new ClientInfo();
		clientInfo.setClientPointByTxt("clientPoint.txt");
		MyPoint[] point=clientInfo.getClientPoint();
		for(int i=0;i<point.length;i++)
			System.out.println(point[i].getpointX()+","+point[i].getpointY());
		BestRoute test=new BestRoute();
		 List<MyPoint> list=new ArrayList<MyPoint>();
		 for(int i=0;i<15;i++)
	        {
			 MyPoint a=new MyPoint();
	   		 a.setpoint(point[i].getpointX(),point[i].getpointY());
	   		 list.add(a); 
	        }
			test.print_point(list);
	}
}
