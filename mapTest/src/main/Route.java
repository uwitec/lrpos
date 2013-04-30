package main;
import googlemap.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

public class Route {
	ArrayList<Line> lineList=new ArrayList<Line>();//涓�潯璺敱璁稿绾跨粍鎴�
	ClientInfo clientInfo=new ClientInfo();
	public void setClientInfo(ClientInfo client){
		clientInfo=client;
	}
	public ClientInfo getClientInfo(){
		return clientInfo;
	}
	public ArrayList<Line> getRoute(){
		return lineList;
	}
}
