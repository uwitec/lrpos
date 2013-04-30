package googlemap;
import main.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GetDistanceThread extends Thread{
	MyPoint[] orig;
	MyPoint[] dest;
	int o1;
	int o2;
	int d1;
	int d2;
	double[][] dist;
	int n;
	double INF=999999999.0;//���㲻�ɴ����
	GetDistanceThread(MyPoint[] _orig,MyPoint[] _dest,int _o1,int _o2,int _d1,int _d2,int _n) {
		// TODO Auto-generated constructor stub
		orig=_orig;
		dest=_dest;
		o1=_o1;
		o2=_o2;
		d1=_d1;
		d2=_d2;
		n=_n;
		dist=new double[n][n];
	}
	private JSONObject getJson(String strUrl) throws JSONException{
		String strJson=null;
		StringBuffer document = new StringBuffer();
		JSONObject json = null;
		try{
			URL url = new URL(strUrl);
			URLConnection conn = url.openConnection();
			if(conn==null)
				return null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
		    while ((line = reader.readLine()) != null){
		    	document.append(line + "\n");
		    }
		    reader.close();
		    strJson= document.toString();//����ֵ
		    json=new JSONObject(strJson);
		}catch(MalformedURLException e) {
			e.printStackTrace(); 
		}catch(IOException e){
		    e.printStackTrace(); 
		}
		//System.out.print(strJson);
		return json;
	}
	public void run(){
		String strUrl="http://maps.googleapis.com/maps/api/distancematrix/json?";
		strUrl+="origins=";
		for(int i=o1;i<o2;i++){//���
			strUrl+=orig[i].getpointX()+","+orig[i].getpointY();
			if(i!=o2-o1-1)
				strUrl+="|";
		}
		strUrl+="&destinations=";
		for(int i=d1;i<d2;i++){//�յ�
			strUrl+=dest[i].getpointX()+","+dest[i].getpointY();
			if(i!=d2-d1-1)
				strUrl+="|";
		}
		strUrl+="&mode=driving&language=chinese&sensor=false";
		System.out.print(strUrl+"\n");
		//��ȡ����
		JSONObject json;
		int threadCount=0;
		while(true){
			if(threadCount>=1000)
				break;
			threadCount++;
			try {
				json = getJson(strUrl);
				if(json==null)
					break;
				System.out.print(json.getString("status")+"\n");
				if(json.getString("status").equalsIgnoreCase("OK")){
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
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public double[][] getDist(){
		return dist;
	}

}
