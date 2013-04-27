import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.FileNotFoundException;   
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.util.Iterator;
import java.util.List;   

import org.jdom.Document;   
import org.jdom.Element;   
import org.jdom.JDOMException;   
import org.jdom.input.SAXBuilder;   
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class maptest {

	/**
	 * @param args
	 * @throws JSONException 
	 */
	//解析xml文件
public static void jsonParse(String str) throws JSONException{
	//String sTotalString ="{\"message\":\"success\",\"result\":[{\"surveyid\":\"1\",\"surveyname\":\"B\"},{\"surveyid\":\"2\",\"surveyname\":\"C\"}]}";
	//str="{\"status\":\"success\",\"currentCity\":\"上海市\",\"dateTime\": \"2012/05/24/17/45/21\",\"results\":[{\"startTime\": \"2012/01/01/00/00/00\",\"endTime\": \"2012/08/30/00/00/00\",\"title\": \"大连路限行(长期)\",\"description\": \"大连路因为占路施工，导致限行。\",\"location\":{\"lng\": \"121.519729\",\"lat\": \"31.263772\"},\"type\": \"2\"}]}";
	//str="{status: \"success\",currentCity: \"上海市\",dateTime: \"2012/05/24/17/45/21\",results:[{startTime: \"2012/01/01/00/00/00\",endTime: \"2012/08/30/00/00/00\",title: \"大连路限行(长期)\",description: \"大连路因为占路施工，导致限行。\",location:{lng: 121.519729,lat: 31.263772},type: \"2\"}    ]}";
	JSONObject json=new JSONObject(str);
	String ss=json.getString("results");
	JSONObject js=json.getJSONObject("results");//new JSONObject(ss);
	
	try{		
		JSONArray results=js.getJSONArray("landMark");	
		for(int i=0;i<results.length();i++){
			JSONObject result=results.getJSONObject(i);
			System.out.print(result.getString("name")+"\n");
		}
	}
	catch(JSONException e){
		e.printStackTrace();
	}
}
public static void XmlParse() throws JDOMException, IOException {
	  SAXBuilder builder = new SAXBuilder();
	  InputStream file = new FileInputStream("D:/study/adt-bundle-windows-x86/eclipse/workspace/mapTest/test.xml");
	  Document document = builder.build(file);//获得文档对象
	  Element root = document.getRootElement();//获得根节点
	  System.out.println("status="+root.getChildText("status"));
	  List<Element> list = root.getChildren();//获取根节点的所有子节点
	  for(Element e:list) {
		  System.out.println("status="+e.getChildText("status"));
	  }

}
	    
	public static void main(String[] args) throws JDOMException, IOException, JSONException {
		// TODO Auto-generated method stub
		String currentUrl="http://api.map.baidu.com/telematics/v2/viaPath?origin=116.290993,40.004519&destination=116.38342,39.913348&output=json&ak=848663247d2b1f176aa52c206d32e149";
		StringBuffer document = new StringBuffer();
		try{
			URL url = new URL(currentUrl);
			//HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
			URLConnection conn = url.openConnection();
			//InputStream file=new FileInputStream(conn.getInputStream().toString());
			//System.out.print(conn.getInputStream().toString());
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
		    while ((line = reader.readLine()) != null)
		      document.append(line + " ");
		    	//System.out.print(line);
		    reader.close();
			//下面的设置对应HTTP请求中的消息报头
			//XmlParse(file);
		}catch(MalformedURLException e) {
			e.printStackTrace(); 
		}catch(IOException e){
		    e.printStackTrace(); 
		}
		String xml = document.toString();//返回值
		//System.out.print(xml);
		//XmlParse();
		jsonParse(xml);
	}

}
