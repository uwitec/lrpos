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
public class Route {
	int n;
	private double INF=999999999.0;
    String point1;
    String point2;
	public static  final class point{
		private double point_x;
		private double point_y;
		public point(){}
		void setpoint(double x,double y)
		{
			point_x=x;
			point_y=y;
		}
		double getpoint_x(){
			
			return point_x;
		}
		double getpoint_y(){
			
			return point_y;
		}
	}
	private List< point> list=new ArrayList< point>();
	private point mypoint;
	Route(){}
	//通过http获取两点间的驾车信息

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
		String strJson = document.toString();//返回值
		return strJson;
	}
	//通过返回的json数据得到两点间的距离
	/*
	private double getDistOfTwoPointFromJson(String strJson){
		double distance=INF;
		JSONObject json;
		try{
			
			json=new JSONObject(strJson);
			if(json.getString("status").equals("NOT_FOUND")){//无法直接搜索到两点间的驾车信息
			
				distance=INF;
				
			}
			else{

				JSONObject results=json.getJSONArray("routes").getJSONObject(0);
				JSONObject result=results.getJSONArray("legs").getJSONObject(0);
				distance=result.getJSONObject("distance").getDouble("value");
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		return distance;
	}*/
	//通过返回的json数据得到两点间的路线信息
	private void getPathOfTwoPointFromJson(String strJson){

		JSONObject json;
		try{
			json=new JSONObject(strJson);
			if(json.getString("status").equals("NOT_FOUND")){//无法直接搜索到两点间的驾车信息
				
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
			    	mypoint=new point();
			        mypoint.setpoint(position.getJSONObject("start_location").getDouble("lat"),position.getJSONObject("start_location").getDouble("lng"));
			    	list.add(mypoint);
			    	if(tem==i-1)
			    	{
			    		mypoint=new point();
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
	private void Best_tour(double [][] ds)
	{
      int i=  ds.length;
      String x;
      String y;
      for(int temp=0;temp<i-1;temp++){
   	 x=Double.toString(ds[temp][0]);
   	 y=Double.toString(ds[temp][1]);
   	 point1=x+","+y;
   	 x=Double.toString(ds[temp+1][0]);
     y=Double.toString(ds[temp+1][1]);
   	 point2=x+","+y;
   	getPathOfTwoPointFromJson(searchRouteOfTwoPoint(point1,point2));
   	if(temp==i-2)
   	{
   		x=Double.toString(ds[i-1][0]);
        y=Double.toString(ds[i-1][1]);
      	 point1=x+","+y;
      	 x=Double.toString(ds[0][0]);
       	 y=Double.toString(ds[0][1]);
       	 point2=x+","+y;
      	getPathOfTwoPointFromJson(searchRouteOfTwoPoint(point1,point2));
   		
   	}
   		
      }
		
	}
public List< point> print_point(List< point> a) throws JSONException   //得到最短路径的点
, InterruptedException
	{
	double[][] ds=new double[a.size()][2];
	for(int i=0;i<a.size();i++)
	{
		mypoint=a.get(i);
		ds[i][0]=mypoint.getpoint_x();
		ds[i][1]=mypoint.getpoint_y();
		
	}
	    Distance dis=new Distance(ds);
	    Best_tour(dis.getBestTour());
	   // Best_tour(ds);
		list.size();
		point mypoint=new point();
		double place[][]=new double[list.size()][2];
		for(int i=0;i<list.size();i++)
		{
			mypoint=list.get(i);
			place[i][0]=mypoint.getpoint_x();
			place[i][1]=mypoint.getpoint_y();
			System.out.print("x："+mypoint.getpoint_x()+"  "+"y: "+mypoint.getpoint_y()+"\n");
		
		}

      
	return list;
}
}

