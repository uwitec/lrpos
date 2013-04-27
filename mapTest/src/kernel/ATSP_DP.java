package kernel;


public class ATSP_DP extends ATSP{
	private static final int N = 20;                        //�ص��������
	private static final int S = (1 << N);		            //״̬��
	private double[][] f;                                   //״̬����f[i][s]����ӵص�i����״̬s�еĸ����ص��ص����(���0)�����·�� 
	private int[][] path;                                   //��¼���·�� 
	
	@Override
	protected void kernel() {
		// TODO Auto-generated method stub
		DP();
	}
	
	/*
	���仯���� 
	*/
	private double dfs(int cur, int status){
	       if (f[cur][status] >= 0.0)
	          return f[cur][status];//�Ƿ��Ѿ������
	       //����status�����Ʊ�ʾ�����ж��ٸ�1�������ж����Ѿ����ʵĳ��и����� 
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
	��̬�滮 
	n���ص������������ʼλ��
	d[0..n-1][0..n-1]�����ص���̾�����󣬵�һ��Ϊ��ʼλ�� 
	*/
	private void DP(){
		 f = new double[n][1<<n];
		 path = new int[n][1<<n];		
	     //��ʼ��f[][] 
	     for (int i = 0;i < n;i++)
	         for (int j = 0;j < (1<<n);j++)
	             f[i][j] = -1.0;
	     f[0][1] = 0.0;        //�ӵص�0���������������ص�����·����0 
	     path[0][1] = 0;
	     
	     //������Ź��ܶ��ٻ�·����(ö��n-1��������������Ź��ܶ��ٻ�·���� )
	     bestCircuitLen = INF;
	     int bestSc = 0;
	     for (int i = 1;i < n;i++){
	         double di = d[0][i] + dfs(i, (1<<n) - 1);
	         if (bestCircuitLen >  di){
	            bestCircuitLen = di;
	            bestSc = i;
	         }
	     }
	     //������Ź��ܶ��ٻ�·���� 
	     bestCircuit[0] = 0;
	     bestCircuit[1] = bestSc;
	     int cur = bestSc;
	     int status = (1<<n) - 1;
	     int i = 1;
	     while (i < n - 1){
	           int next = path[cur][status];
	           if (next != cur){
	              i++;
	              bestCircuit[i] = next;
	           }
	           status = ~(1 << cur) & status;
	           cur = next;
	     }
	     //������Ź��ܶ���·�����루��·����n - 1��㣬0Ϊ�յ㣩 
	     bestPathLen = dfs(n - 1, (1<<n) - 1);
	     //������Ź��ܶ���·������ 
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

}
