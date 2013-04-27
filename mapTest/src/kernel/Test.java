package kernel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Test {

	/**
	 * @param args
	 */
	
	private static void prtBestCircuit(int n, ATSP atsp){
		System.out.println("bestCircuitLen = " + atsp.getBestCircuitLen());
		int []tmp = atsp.getBestCircuit();
		
		System.out.print("bestCircuit:" + tmp[0]);
		for (int i = 1;i < n;i++)
			System.out.print("->" + tmp[i]);
		System.out.println("->" + tmp[0]);
	}
	
	private static void prtBestPath(int n, ATSP atsp){
		System.out.println("bestPathLen = " + atsp.getBestPathLen());
		int []tmp = atsp.getBestPath();
		
		System.out.print("bestPath:" + tmp[0]);
		for (int i = 1;i < n;i++)
			System.out.print("->" + tmp[i]);
		System.out.println("");		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ATSP rai = new ATSP_RAI();
		ATSP dp = new ATSP_DP();
		int numOfVertex = 0;
		double[][] distanceMatrix = null;
		int type = 1;
		try {
			Scanner scanner = new Scanner(new File("d:/in.txt"));
			numOfVertex = scanner.nextInt();
			distanceMatrix = new double[numOfVertex][numOfVertex];
			for (int i = 0;i < numOfVertex;i++)
				for (int j = 0 ;j < numOfVertex;j++)
					distanceMatrix[i][j] = scanner.nextDouble();
			for (int i = 0;i < numOfVertex;i++)
				distanceMatrix[i][i] = 0.0;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(numOfVertex);
//		for (int i = 0;i < numOfVertex;i++){
//			for (int j = 0;j < numOfVertex;j++)
//				System.out.print(distanceMatrix[i][j]+ " ");
//			System.out.println("");
//		}
		
//		dp.run(numOfVertex, distanceMatrix, type);
//		prtBestCircuit(numOfVertex, dp);
//		prtBestPath(numOfVertex, dp);
		rai.run(numOfVertex, distanceMatrix, type);
		prtBestCircuit(numOfVertex, rai);
		prtBestPath(numOfVertex, rai);		
	}

}
