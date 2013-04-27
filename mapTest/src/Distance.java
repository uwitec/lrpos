import java.math.*;
import java.awt.geom.QuadCurve2D.Double;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;   
import org.jdom.Element;   
import org.jdom.JDOMException;   
import org.jdom.input.SAXBuilder;   
import org.jdom.output.XMLOutputter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
//计算城市间的距离
public class Distance extends Thread{
	int n;
	int count;
	//private Point[] pt;
	private double[][] pt;//保存地点坐标
	private double[][] dist; //任意两点间的距离
	private double INF=999999999.0;//两点不可达距离
	Distance(double[][] p){
		pt=p;
		n=p.length;
		dist=new double[n][n];
		count=0;
	}
	private JSONObject getJson(String strUrl) throws JSONException{
		String strJson=null;
		StringBuffer document = new StringBuffer();
		JSONObject json = null;
		try{
			URL url = new URL(strUrl);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
		    while ((line = reader.readLine()) != null){
		    	document.append(line + "\n");
		    }
		    reader.close();
		    strJson= document.toString();//返回值
		    json=new JSONObject(strJson);
		}catch(MalformedURLException e) {
			e.printStackTrace(); 
		}catch(IOException e){
		    e.printStackTrace(); 
		}
		//System.out.print(strJson);
		return json;
	}

	private void getDistFromGoogle(double[][] orig,double[][] dest,int o1,int o2,int d1,int d2) throws JSONException{//起点为o1-o2 //终点为d1-d2
		String strUrl="http://maps.googleapis.com/maps/api/distancematrix/json?";
		strUrl+="origins=";
		for(int i=o1;i<o2;i++){//起点
			strUrl+=orig[i][0]+","+orig[i][1];
			if(i!=o2-o1-1)
				strUrl+="|";
		}
		strUrl+="&destinations=";
		for(int i=d1;i<d2;i++){//终点
			strUrl+=dest[i][0]+","+dest[i][1];
			if(i!=d2-d1-1)
				strUrl+="|";
		}
		strUrl+="&mode=driving&language=chinese&sensor=false";
		System.out.print(strUrl+"\n");
		//获取距离
		JSONObject json=getJson(strUrl);
		System.out.print(json.getString("status"));
		if(!json.getString("status").equalsIgnoreCase("OK")){
			System.out.print("error\n");
		}
		if(json.getString("status").equalsIgnoreCase("OK")){
			System.out.print("..\n");
			JSONArray rows=json.getJSONArray("rows");
			for(int i=0;i<rows.length();i++){
				JSONArray elements=rows.getJSONObject(i).getJSONArray("elements");
				for(int j=0;j<elements.length();j++){
					JSONObject element=elements.getJSONObject(j);
					if(element.getString("status").equalsIgnoreCase("OK")){
						//System.out.print(j+i);
						JSONObject distance=element.getJSONObject("distance");
						dist[i+o1][j+d1]=distance.getDouble("value");
					}
					else
						dist[i+o1][j+d1]=INF;
				}
			}
		}
	}
	//计算任意两点间的距离，返回距离矩阵
	public void getdist() throws JSONException, InterruptedException{//一次计算个数 起点*终点《100
		List<Thread> threadls=new ArrayList<Thread>();
		List<GetDistanceThread> distancels=new ArrayList<GetDistanceThread>();
		if(n>10){
			int m=0;
			int mm=0;
			for(int i=0;i<n;i+=m){
				if(i+5<n)
					m=5;
				else
					m=n-i;
				for(int j=0;j<n;j+=mm){
					if(j+5<n)
						mm=5;
					else
						mm=n-j;
					//开启线程获取距离
					GetDistanceThread distanceThread=new GetDistanceThread(pt, pt, i, i+m, j, j+mm, n);
					Thread thread=new Thread(distanceThread);
					thread.start();
					//thread.join();
					threadls.add(thread);
					distancels.add(distanceThread);

					//getDistFromGoogle(pt,pt,i,i+m,j,j+mm);
				}
			}
			Iterator<Thread> it;
			Iterator<GetDistanceThread> it2;
			for(it = threadls.iterator(),it2=distancels.iterator();it.hasNext();)
            {
                 it.next().join();
                 double[][] d=it2.next().getDist();
                 for(int i=0;i<n;i++){
                	 for(int j=0;j<n;j++){
                		 if(dist[i][j]==0&&dist[i][j]!=d[i][j]){
                			 dist[i][j]=d[i][j];	
                		 }
                	 }
                 }   
            }
		}
		else{//n<=10
			getDistFromGoogle(pt,pt,0,n,0,n);
		}
		//return dist;
	}
	public double[][] getBestTour() throws JSONException, InterruptedException{
		int[] bestTour=new int[n];
		getdist();
		
		System.out.print("distance:\n");
		for(int i=0;i<dist.length;i++){
			for(int j=0;j<dist.length;j++){
				System.out.print(dist[i][j]+"  ");
			}
			System.out.print("\n");
		}
		ATSP_DP atspdp=new ATSP_DP();
		atspdp.DP(n, dist);
		bestTour=atspdp.getBestCircuit();
		System.out.print("bestTour:\n");
		for(int i=0;i<n;i++)
			System.out.print(bestTour[i]+"  ");
		System.out.print("\n");
		double[][] point=new double[n][n];
		System.out.print("bestTour Point:\n");
		for(int i=0;i<n;i++){
			point[i][0]=pt[bestTour[i]][0];
			point[i][1]=pt[bestTour[i]][1];
			if(i!=0&&i%3==0)
				System.out.print("\n");
		System.out.print(point[i][0]+","+point[i][1]+"  ");
		}
		System.out.print("\n");
		return point;
	}
}
