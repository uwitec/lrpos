package googlemap;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import main.Line;
import main.MyPoint;
import main.Route;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BestRoute {
	int n;
	private double INF=999999999.0;
    String point_start;
    String point_end;
    String point_middle ;
	Line aLine;
	double all_distance []=new double [10000];
	int ptr=0;
	Route aRoute=new Route();
	private MyPoint mypoint;
	ArrayList<MyPoint> pointList;
	public BestRoute(){}
	//通过http获取两点间的驾车信息

	private String searchRouteOfTwoPoint(String point_start,String point_end){
		String strUrl="http://maps.googleapis.com/maps/api/directions/json?origin="+point_start+"&destination="+point_end+"&sensor=true";
		StringBuffer document = new StringBuffer();
		String s;
	
		URL urlmy = null;
		try{
			urlmy = new URL(strUrl);
			HttpURLConnection conn= (HttpURLConnection) urlmy.openConnection();
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			BufferedReader read=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8") );

			while((s=read.readLine())!=null)
			{
				document.append(s+"\r\n");
				
			}
		}catch(MalformedURLException e) {
			e.printStackTrace(); 
		}catch(IOException e){
		    e.printStackTrace(); 
		}
		String strJson = document.toString();//返回值
		return strJson;
	}
	private String searchRoutePoint(String PointStart,String PointEnd,String PointMiddle){
		String strUrl="http://maps.googleapis.com/maps/api/directions/json?origin="+PointStart+"&destination="+PointEnd+"&waypoints="+PointMiddle+"&sensor=true";
		StringBuffer document = new StringBuffer();
		String s;
	
		URL urlmy = null;
		try{
			urlmy = new URL(strUrl);
			HttpURLConnection conn= (HttpURLConnection) urlmy.openConnection();
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			BufferedReader read=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8") );

			while((s=read.readLine())!=null)
			{
				document.append(s+"\r\n");
				
			}
		}catch(MalformedURLException e) {
			e.printStackTrace(); 
		}catch(IOException e){
		    e.printStackTrace(); 
		}
		String strJson = document.toString();//返回值
		return strJson;
	}
	//通过返回的json数据得到两点间的距离
	private String testsearchRoutePoint(String PointStart,String PointEnd,String PointMiddle){
		String strUrl="http://maps.googleapis.com/maps/api/directions/json?origin="+PointStart+"&destination="+PointEnd+"&waypoints=optimize:true"+PointMiddle+"&sensor=false";
		System.out.println(strUrl);
		StringBuffer document = new StringBuffer();
		String s;
	
		URL urlmy = null;
		try{
			urlmy = new URL(strUrl);
			HttpURLConnection conn= (HttpURLConnection) urlmy.openConnection();
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			BufferedReader read=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8") );

			while((s=read.readLine())!=null)
			{
				document.append(s+"\r\n");
				
			}
		}catch(MalformedURLException e) {
			e.printStackTrace(); 
		}catch(IOException e){
		    e.printStackTrace(); 
		}
		String strJson = document.toString();//返回值
		return strJson;
	}
	private double getDistFromJson(String strJson){
		double distance=INF;
		JSONObject json;
		try{
			
			json=new JSONObject(strJson);
			if(json.getString("status").equals("NOT_FOUND")){//无法直接搜索到两点间的驾车信息
			
				distance=INF;
				
			}
			else{

				JSONObject results=json.getJSONArray("routes").getJSONObject(0);
				for(int tem=0;tem<results.getJSONArray("legs").length();tem++)
				{
					
			    JSONObject result=results.getJSONArray("legs").getJSONObject(tem);
				distance=result.getJSONObject("distance").getDouble("value");
				all_distance[ptr]= distance;
				System.out.println(all_distance[ptr]);
				ptr++;
				}
				
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		return distance;
	}
	private void getPathOfTwoPointFromJson(String strJson){

		JSONObject json;
		try{
			json=new JSONObject(strJson);
			if(json.getString("status").equals("NOT_FOUND")){//无法直接搜索到两点间的驾车信息
				
			}

			else{
				if(json.getString("status").equals("OVER_QUERY_LIMIT"))
				{
					getPathOfTwoPointFromJson(searchRouteOfTwoPoint(point_start,point_end));
			    }
				else{
				JSONObject results=json.getJSONArray("routes").getJSONObject(0);
				JSONObject result=results.getJSONArray("legs").getJSONObject(0);
				JSONArray  key=result.getJSONArray("steps");
			      int i=key.length();			      
			      pointList=new ArrayList<MyPoint>();
			      for(int tem=0;tem<i;tem++)
			      {
			    	JSONObject position=key.getJSONObject(tem);
			    	mypoint=new MyPoint();
			        mypoint.setpoint(position.getJSONObject("start_location").getDouble("lat"),position.getJSONObject("start_location").getDouble("lng"));
			        pointList.add(mypoint);
			        if(tem==i-1)
			    	{
			    		mypoint=new MyPoint();
			    		mypoint.setpoint(position.getJSONObject("end_location").getDouble("lat"),position.getJSONObject("end_location").getDouble("lng"));
			    		 pointList.add(mypoint);
			    	}
			    	
			      }
			      for(int tem=0;tem<pointList.size()-1;tem++)
			      {
			    	  aLine=new Line();
			    	  aLine.setLine(pointList.get(tem), pointList.get(tem+1));
			    	  aRoute.setRouteByTaskId(aLine);
			      }
			      
			     
			   }
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
	private void getPathPointFromJson(String strJson){

		JSONObject json;
		try{
			json=new JSONObject(strJson);
			if(json.getString("status").equals("NOT_FOUND")){//无法直接搜索到两点间的驾车信息
				
			}

			else{
				if(json.getString("status").equals("OVER_QUERY_LIMIT"))
				{
					getPathPointFromJson(searchRoutePoint(point_start,point_end,point_middle));
			    }
				else{
				JSONObject results=json.getJSONArray("routes").getJSONObject(0);
				JSONArray  result=results.getJSONArray("legs");
				for(int q=0;q<result.length();q++){
					
				JSONArray  key=result.getJSONObject(q).getJSONArray("steps");
			      int i=key.length();
			      aLine=new Line();
			      pointList=new ArrayList<MyPoint>();
			      for(int tem=0;tem<i;tem++)
			      {
			    	JSONObject position=key.getJSONObject(tem);
			    	mypoint=new MyPoint();
			        mypoint.setpoint(position.getJSONObject("start_location").getDouble("lat"),position.getJSONObject("start_location").getDouble("lng"));
			        pointList.add(mypoint);
		
			    	if(tem==i-1)
			    	{
			    		mypoint=new MyPoint();
			    		mypoint.setpoint(position.getJSONObject("end_location").getDouble("lat"),position.getJSONObject("end_location").getDouble("lng"));
			    	pointList.add(mypoint);
			    	}
			    	
			      }
			      for(int tem=0;tem<pointList.size()-1;tem++)
			      {
			    	  aLine=new Line();
			    	  aLine.setLine(pointList.get(tem), pointList.get(tem+1));
			    	  aRoute.setRouteByTaskId(aLine);
			      }
			      }
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
	private void Best_tour(MyPoint [] ds)
	{
      int i=  ds.length;
      String x;
      String y;
      int temp=0;
      int times=1;
      ptr=0;

   for(;temp<i;temp++){
	    if(times==11)
	       {
	       getPathPointFromJson(searchRoutePoint(point_start,point_end,point_middle));	
	       getDistFromJson(searchRoutePoint(point_start,point_end,point_middle));
		   times=1;
		   temp--;
		   point_start="";
		   point_end="";
		   point_middle="";
	       }
    		  if(times==1){
    			  x=Double.toString(ds[temp].getpointX());
    			  y=Double.toString(ds[temp].getpointY());
    			  point_start=x+","+y;
    			  times++;
    		  }
    		  else if (times==2)
    		  {
    			  x=Double.toString(ds[temp].getpointX());
    			  y=Double.toString(ds[temp].getpointY());
    			  point_end=x+","+y;
    			  times++;  
    		  }
    		  else if (times==3)
    		  {
    			  point_middle=point_end;
    			  x=Double.toString(ds[temp].getpointX());
    			  y=Double.toString(ds[temp].getpointY());
    			  point_end=x+","+y;
    			  times++;    
    		  }
    		  else{
    			  point_middle=point_middle+"|"+point_end;
    			  x=Double.toString(ds[temp].getpointX());
    			  y=Double.toString(ds[temp].getpointY());
    			  point_end=x+","+y;
    			  times++;   
    		  }
      }
     if (times==3)
    {
    	
    	getPathOfTwoPointFromJson(searchRouteOfTwoPoint(point_start,point_end));
    	getDistFromJson(searchRouteOfTwoPoint(point_start,point_end));
    	
    }
    else
    {
    	
    	getPathPointFromJson(searchRoutePoint(point_start,point_end,point_middle));
    	 getDistFromJson(searchRoutePoint(point_start,point_end,point_middle));
    }
   
   }
public Route print_point(List< MyPoint> a) throws JSONException   //得到最短路径的点
, InterruptedException
	{
	MyPoint [] aMyPoints=new MyPoint[a.size()];
	for(int i=0;i<a.size();i++)
	{
		aMyPoints[i]=a.get(i);
		
	}
	
	    Distance dis=new Distance(aMyPoints);
	    MyPoint all[]=new MyPoint [a.size()+1];
	    MyPoint m[]=new MyPoint [a.size()];
	    m=dis.getBestTour();
	    for(int i=0;i<a.size()+1;i++)
	    {
	    	if(i==a.size())
	    	{
	    		all[a.size()]=all[0];
	    	}
	    	else {
	    		all[i]=m[i];
			}
	    	
	    	
	    }
	    Best_tour(all);
	    for(int j=0;j<aRoute.getRoute().size();j++)
	    {
	    	double myx=aRoute.getRoute().get(j).getPointOne().getpointX();
	        double myy=aRoute.getRoute().get(j).getPointOne().getpointY();
	        System.out.print("x："+myx+"  "+"y: "+myy+"\n");	
	        if(j==aRoute.getRoute().size()-1)
	        {
		    	 myx=aRoute.getRoute().get(j).getPointTwo().getpointX();
		         myy=aRoute.getRoute().get(j).getPointTwo().getpointY();
		        System.out.print("x："+myx+"  "+"y: "+myy+"\n");	
	        	
	        	
	        }
	    }
	    double min_dis=0;
	    for(int tem=0;tem<ptr;tem++)
	    {
	    	min_dis=min_dis+all_distance[tem]; 	
	    }
	    System.out.print("距离："+min_dis+"\n");	
		return aRoute;
}

public void tests(List< MyPoint> a) throws JSONException
, InterruptedException
{
	MyPoint [] aMyPoints=new MyPoint[a.size()+1];
	for(int i=0;i<a.size();i++)
	{
		aMyPoints[i]=a.get(i);	
	}
	aMyPoints[a.size()]=aMyPoints[0];
	   
	  //  Best_tour(aMyPoints);
	String x,y;
	   point_start="";
	   point_end="";
	   point_middle="";
	for(int j=0;j<aMyPoints.length;j++)
	{
		
		if(j==0)
		{	
	    
	    x=Double.toString(aMyPoints[0].getpointX());
		y=Double.toString(aMyPoints[0].getpointY());
		point_start=x+","+y;
		}
		else if(j==1)
		{	
		    x=Double.toString(aMyPoints[1].getpointX());
			y=Double.toString(aMyPoints[1].getpointY());
			point_end=x+","+y;	
		}
		else
	    {
	    point_middle=point_middle+"|"+point_end;
	    x=Double.toString(aMyPoints[j].getpointX());
		y=Double.toString(aMyPoints[j].getpointY());
		point_end=x+","+y;
	    }
	 
	}
    ptr=0;
	getDistFromJson(testsearchRoutePoint(point_start, point_end, point_middle));
	/*
	    for(int j=0;j<aRoute.getRoute().size();j++)
	    {
	    	double myx=aRoute.getRoute().get(j).getPointOne().getpointX();
	        double myy=aRoute.getRoute().get(j).getPointOne().getpointY();
	        System.out.print("x："+myx+"  "+"y: "+myy+"\n");		
	        if(j==aRoute.getRoute().size()-1)
	        {
		    	 myx=aRoute.getRoute().get(j).getPointTwo().getpointX();
		         myy=aRoute.getRoute().get(j).getPointTwo().getpointY();
		        System.out.print("x："+myx+"  "+"y: "+myy+"\n");	
	        	
	        	
	        }
	    }
	    */
	    double min_dis=0;
	    System.out.println(ptr);	
	    for(int tem=0;tem<ptr;tem++)
	    {
	    	min_dis=min_dis+all_distance[tem]; 	
	    	  System.out.println(all_distance[tem]);
	    }
	    
	    System.out.print("距离："+min_dis+"\n");	
}
}