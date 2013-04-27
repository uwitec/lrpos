package googlemap;
import main.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class BestRoute {
	int n;
	private double INF=999999999.0;
    String point1;
    String point2;
    double sum=0;
	private List< MyPoint> list=new ArrayList< MyPoint>();
	private MyPoint mypoint;
	public BestRoute(){}
	//Íš¹ýhttp»ñÈ¡ÁœµãŒäµÄŒÝ³µÐÅÏ¢

	private String searchRouteOfTwoPoint(String point_start,String point_end){
		String strUrl="http://maps.googleapis.com/maps/api/directions/json?origin="+point_start+"&destination="+point_end+"&sensor=true";
		StringBuffer document = new StringBuffer();
		String s;
	
		URL urlmy = null;
		try{
			urlmy = new URL(strUrl);
			HttpURLConnection conn= (HttpURLConnection) urlmy.openConnection();
		//	conn.setFollowRedirects(true);
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
		String strJson = document.toString();//·µ»ØÖµ
		return strJson;
	}
		private void getPathOfTwoPointFromJson(String strJson){
			
		JSONObject json;
		try{
			json=new JSONObject(strJson);
			if(json.getString("status").equals("NOT_FOUND")){//ÎÞ·šÖ±œÓËÑË÷µœÁœµãŒäµÄŒÝ³µÐÅÏ¢		
			}
			else{
				if(json.getString("status").equals("OVER_QUERY_LIMIT"))
				{
					getPathOfTwoPointFromJson(searchRouteOfTwoPoint(point1,point2));
			    }
				else{
				JSONObject results=json.getJSONArray("routes").getJSONObject(0);
				JSONObject result=results.getJSONArray("legs").getJSONObject(0);
				JSONArray  key=result.getJSONArray("steps");
			      int i=key.length();
			      for(int tem=0;tem<i;tem++)
			      {
			    	JSONObject position=key.getJSONObject(tem);
			    	mypoint=new MyPoint();
			        mypoint.setpoint(position.getJSONObject("start_location").getDouble("lat"),position.getJSONObject("start_location").getDouble("lng"));
			    	list.add(mypoint);
			    	sum+=position.getJSONObject("distance").getDouble("value");
			    	if(tem==i-1)
			    	{
			    		mypoint=new MyPoint();
			    		mypoint.setpoint(position.getJSONObject("end_location").getDouble("lat"),position.getJSONObject("end_location").getDouble("lng"));
			    		list.add(mypoint);		
			    	}
			      }
			     
			   }
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
	private void Best_tour(MyPoint[] ds)
	{
      int i=  ds.length;
      String x;
      String y;
      for(int temp=0;temp<i-1;temp++){
    	  x=Double.toString(ds[temp].getpointX());
    	  y=Double.toString(ds[temp].getpointY());
    	  point1=x+","+y;
    	  x=Double.toString(ds[temp+1].getpointX());
    	  y=Double.toString(ds[temp+1].getpointY());
    	  point2=x+","+y;
    	  getPathOfTwoPointFromJson(searchRouteOfTwoPoint(point1,point2));
		   	if(temp==i-2)
		   	{
		   		x=Double.toString(ds[i-1].getpointX());
		        y=Double.toString(ds[i-1].getpointY());
		      	 point1=x+","+y;
		      	 x=Double.toString(ds[0].getpointX());
		       	 y=Double.toString(ds[0].getpointY());
		       	 point2=x+","+y;
		      	getPathOfTwoPointFromJson(searchRouteOfTwoPoint(point1,point2));   		
		   	}
      }
     
	}
public List< MyPoint> print_point(List< MyPoint> a) throws JSONException   //µÃµœ×î¶ÌÂ·Ÿ¶µÄµã
, InterruptedException
	{
	MyPoint[] ds=new MyPoint[a.size()];
	//double[][] ds=new double[a.size()][2];
	for(int i=0;i<a.size();i++)
	{
		mypoint=a.get(i);
		ds[i]=mypoint;
	}
	    Distance dis=new Distance(ds);
	    Best_tour(dis.getBestTour());
	    System.out.println("*****"+sum+"****");
	   // Best_tour(ds);
		list.size();
		MyPoint mypoint=new MyPoint();
		double place[][]=new double[list.size()][2];
		for(int i=0;i<list.size();i++)
		{
			mypoint=list.get(i);
			place[i][0]=mypoint.getpointX();
			place[i][1]=mypoint.getpointY();
			//System.out.print("x:"+mypoint.getpointX()+"  "+"y: "+mypoint.getpointY()+"\n");
		}
	return list;
}
}
