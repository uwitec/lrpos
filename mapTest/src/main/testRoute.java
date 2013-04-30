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
		Service service=new Service(1);
		service.run();
		service.getBestRouteFromService(0,2);
	}

}
