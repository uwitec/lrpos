package kernel;

public abstract class ATSP {
	protected static final double INF = 99999999.0;           //��󳤶�
	
	protected int n;								          //�ص����
	protected int type;                                       //���ͣ������Ź��ܶ��ٻ�·�����Ź��ܶ���·��
	protected double[][] d;			                          //���ص���̾������
	protected double bestCircuitLen;                          //���Ź��ܶ��ٻ�·���� 
	protected int[] bestCircuit;                              //���Ź��ܶ��ٻ�·���� 
	protected double bestPathLen;                             //���Ź��ܶ���·������
	protected int[] bestPath;                                 //���Ź��ܶ���·������
	
	/*
	 * ���к����㷨
	 */
	public void run(int numOfVertex, double[][] distanceMatrix, int type){
		this.n = numOfVertex;
		this.d = new double[this.n][this.n];
		for (int i = 0;i < this.n;i++)
			for (int j = 0;j < this.n;j++)
				this.d[i][j] = distanceMatrix[i][j];
		this.type = type;
		bestCircuit = new int[this.n];
		bestPath = new int[this.n];
		kernel();
	}
	
	/*
	 * ��ȡ���Ź��ܶ��ٻ�·���� 
	 */
	public double getBestCircuitLen(){
		return bestCircuitLen;
	}
	
	/*
	 * ��ȡ���Ź��ܶ��ٻ�·���� 
	 */
	public int[] getBestCircuit(){
		return bestCircuit;
	}
	
	/*
	 * ��ȡ���Ź��ܶ���·������
	 */
	public double getBestPathLen(){
		return bestPathLen;
	}
	
	/*
	 * ��ȡ���Ź��ܶ���·������
	 */
	public int[] getBestPath(){
		return bestPath;
	}
	
	/*
	 * �����㷨
	 */
	protected abstract void kernel();
}
