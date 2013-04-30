package kernel;

public abstract class ATSP {
	protected static final double INF = 99999999.0;           //最大长度
	
	protected int n;								          //地点个数
	protected int type;                                       //类型：求最优哈密尔顿回路或最优哈密尔顿路径
	protected double[][] d;			                          //各地点最短距离矩阵
	protected double bestCircuitLen;                          //最优哈密尔顿回路距离 
	protected int[] bestCircuit;                              //最优哈密尔顿回路方案 
	protected double bestPathLen;                             //最优哈密尔顿路径距离
	protected int[] bestPath;                                 //最优哈密尔顿路径方案
	
	/*
	 * 运行核心算法
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
	 * 获取最优哈密尔顿回路距离 
	 */
	public double getBestCircuitLen(){
		return bestCircuitLen;
	}
	
	/*
	 * 获取最优哈密尔顿回路方案 
	 */
	public int[] getBestCircuit(){
		return bestCircuit;
	}
	
	/*
	 * 获取最优哈密尔顿路径距离
	 */
	public double getBestPathLen(){
		return bestPathLen;
	}
	
	/*
	 * 获取最优哈密尔顿路径方案
	 */
	public int[] getBestPath(){
		return bestPath;
	}
	
	/*
	 * 核心算法
	 */
	protected abstract void kernel();
}
