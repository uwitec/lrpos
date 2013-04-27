package main;
import org.json.JSONException;


public class testRoute {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws JSONException, InterruptedException {
		// TODO Auto-generated method stub
		Route route=new Route();
		route.init();
		route.getRouteByTaskId(0);
	}

}
