package main;
import googlemap.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;




public class test {

	/**
	 * @param args
	 * @throws JSONException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws JSONException, InterruptedException {
		// TODO Auto-generated method stub
		BestRoute test=new BestRoute();
		 List<MyPoint> list=new ArrayList<MyPoint>();
		 
		double pt[][]=new double[15][2];
		pt[0][0]=26.074508;//����
		pt[0][1]=119.29649399999994;
		pt[1][0]=24.479834;//����
		pt[1][1]=118.089425;
		pt[2][0]=31.230393;//�Ϻ�
		pt[2][1]=121.473704;
		pt[3][0]=39.90403;//����
		pt[3][1]=116.40752599999996;
		pt[4][0]=23.132191;//�㶫
		pt[4][1]=113.26653099999999;
		pt[5][0]=30.267447;
		pt[5][1]=120.15279199999998;
		pt[6][0]=39.084158;
		pt[6][1]=117.20098299999995;
		pt[7][0]=30.545861;
		pt[7][1]=114.34192099999996;
		pt[8][0]=23.132191;
		pt[8][1]=113.26653099999999;
		pt[9][0]=37.873532;
		pt[9][1]=112.56239800000003;
		pt[10][0]=34.265472;
		pt[10][1]=108.95423900000003;
		pt[11][0]=28.112444;
		pt[11][1]=112.98380999999995;
		pt[12][0]=28.674425;
		pt[12][1]=115.90913699999999;
		pt[13][0]=30.651652;
		pt[13][1]=104.07593099999997;
		pt[14][0]=22.815478;
		pt[14][1]=108.32754599999998;
        for(int i=0;i<15;i++)
        {
        	MyPoint a=new MyPoint();
   		 	a.setpoint(pt[i][0],pt[i][1]);
   		 	list.add(a); 
        }
		test.print_point(list);
	}

}
