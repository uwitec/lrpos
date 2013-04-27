
public class ATSP_DP {
	private static final int N = 20;			            //地点个数上限
	private static final int S = (1 << N);		            //状态数
	private static final double INF = 99999999.0;             //最大长度
	
	private int n;								            //地点个数
	private double[][] d = new double[N][N];			    //各地点最短距离矩阵
	private double bestCircuitLen;                          //最优哈密尔顿回路距离 
	private int[] bestCircuit = new int[N];                 //最优哈密尔顿回路方案 
	private double bestPathLen;                             //最优哈密尔顿路径距离
	private int[] bestPath = new int[N];                    //最优哈密尔顿路径方案
	private double[][] f = new double[N][S];                //状态方程f[i][s]代表从地点i经过状态s中的各个地点后回到起点(编号0)的最短路径 
	private int[][] path = new int[N][S];                   //记录最佳路径 
	/*
	记忆化搜索 
	*/
	private double dfs(int cur, int status){
	       if (f[cur][status] >= 0.0)
	          return f[cur][status];//是否已经计算过
	       //计算status二进制表示法中有多少个1（即还有多少已经访问的城市个数） 
	       int cnt = 0;
	       int tmp = status;
	       while (tmp != 0){
	             cnt++;
	             tmp = tmp & (tmp - 1);
	       }
	       
	       if (cnt <= 1){
	          f[cur][status] = d[cur][0];
	          path[cur][status] = 0;
	       }else{
	           double mind = INF;
	           int mink = 0;
	           for (int i =  1;i < n;i++)
	               if (((1 << i) & status) != 0){
	                      double td = d[cur][i] + dfs(i, ~(1 << cur) & status);
	                      if (mind > td){
	                         mind = td;
	                         mink = i;
	                      } 
	               }
	           f[cur][status] = mind;
	           path[cur][status] = mink;
	       }
	           
	       return f[cur][status];
	}
	/* 
	动态规划 
	n：地点个数，包括起始位置
	d[0..n-1][0..n-1]：各地点最短距离矩阵，第一个为起始位置 
	*/
	public void DP(int _n, double[][] _d){
		n = _n;
		 for (int i = 0;i < n;i++)
			 for (int j = 0;j < n;j++)
				 d[i][j] = _d[i][j];
	     //初始化f[][] 
	     for (int i = 0;i < n;i++)
	         for (int j = 0;j < (1<<n);j++)
	             f[i][j] = -1.0;
	     f[0][1] = 0.0;        //从地点0出发不经过其他地点的最短路径是0 
	     path[0][1] = 0;
	     
	     //获得最优哈密尔顿回路距离(枚举n-1种情况，更新最优哈密尔顿回路距离 )
	     bestCircuitLen = INF;
	     int bestSc = 0;
	     for (int i = 1;i < n;i++){
	         double di = d[0][i] + dfs(i, (1<<n) - 1);
	         if (bestCircuitLen >  di){
	            bestCircuitLen = di;
	            bestSc = i;
	         }
	     }
	     //获得最优哈密尔顿回路方案 
	     bestCircuit[0] = 0;
	     bestCircuit[1] = bestSc;
	     int cur = bestSc;
	     int status = (1<<n) - 1;
	     int i = 1;
	     while (i < n){
	           int next = path[cur][status];
	           if (next != cur){
	              i++;
	              bestCircuit[i] = next;
	           }
	           status = ~(1 << cur) & status;
	           cur = next;
	     }
	     //获得最优哈密尔顿路径距离（该路径以n - 1起点，0为终点） 
	     bestPathLen = dfs(n - 1, (1<<n) - 1);
	     //获得最优哈密尔顿路径方案 
	     bestPath[0] = n - 1;
	     cur = n - 1;
	     status = (1<<n) - 1;
	     i = 0;
	     while (i < n - 1){
	           int next = path[cur][status];
	           if (next != cur){
	              i++;
	              bestPath[i] = next;
	           }
	           status = ~(1 << cur) & status;
	           cur = next;
	     }
	     bestPath[n - 1] = 0;
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
}
