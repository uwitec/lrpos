package kernel;


public class ATSP_RAI extends ATSP{
	private static final int T = 10;		               //time of iteration
	private static final int N = 500;                      //max vertex
	private int[] selected;                                //vertices on the tour
	private int ios;                                       //index of selected[]
	private double curLen;                                 //length of current length
	private int[] rest;                                    //vertices not on the tour
	private int ior;                                       //index of rest[]
	private double tBestLen;                               //best length(temp)
	private int[] tBestPath;                               //best length(temp)

	@Override
	protected void kernel() {
		// TODO Auto-generated method stub
		int st, tc, ed;
		switch (type){
		case 0://最优哈密尔顿回路
			RAI();
			bestCircuitLen = tBestLen;
			//把起点移动到第一个位置
			st = 0;
			for (int i = 0;i < n;i++)
				if (tBestPath[i] == 0){
					st = i;
					break;
				}
			tc = 0;
			for (int i = st;i < n;i++)
				bestCircuit[tc++] = tBestPath[i];
			for (int i = 0;i < st;i++)
				bestCircuit[tc++] = tBestPath[i];
			break;
		case 1://最优哈密尔顿路径
			for (int i = 1;i < n - 1;i++)
				d[i][0] = d[n - 1][i];
			n--;
			RAI();
			bestPathLen = tBestLen;
			//注意下面的n已经是减过1的了
			ed = 0;
			for (int i = 0;i < n - 1;i++)
				if (tBestPath[i] == 0){
					ed = i;
					break;
				}
			tc = 0;
			bestPath[tc++] = n; //起点
			for (int i = ed + 1;i < n;i++)
				bestPath[tc++] = tBestPath[i];
			for (int i = 0;i < ed + 1;i++)
				bestPath[tc++] = tBestPath[i];
			break;
		}
	}
	
	/*
	 * 核心算法RAI
	 */
	private void RAI(){
		selected = new int[n];
		rest = new int[n];
		tBestPath = new int[n];
	
		// 1. Start with a tour consisting of a given vertex and self-loop.
		curLen = 0.0;
		ios = 0;
		selected[ios++] = 0; // 0 is start
		ior = 0;
		for (int i = 1; i < n; i++)
			rest[ior++] = i;
		// 2. Randomly choose a vertex not on the tour.
		// 3. Insert this vertex between neighboring vertices on the tour in the cheapest possible way.
		// If the tour is still incomplete, go to step 2.
		for (int i = 0; i < n - 1; i++) {
			int rd = (int) Math.round(Math.random() * (ior - 1));  //rand() % ior;
			int cur = getRest(rd);
			double minVal = curLen + d[selected[ios - 1]][cur]
					+ d[cur][selected[0]] - d[selected[ios - 1]][selected[0]];
			int minPos = ios;
			for (int j = 0; j < ios - 1; j++)
				if (minVal > curLen + d[selected[j]][cur]
						+ d[cur][selected[j + 1]]
						- d[selected[j]][selected[j + 1]]) {
					minVal = curLen + d[selected[j]][cur]
							+ d[cur][selected[j + 1]]
							- d[selected[j]][selected[j + 1]];
					minPos = j + 1;
				}
			insertSelected(minPos, cur);
		}
		// 4. Keep this tour solution, say S.
		tBestLen = curLen;
		for (int i = 0; i < n; i++)
			tBestPath[i] = selected[i];

		// 5. Repeat n2-times steps 6 through 10.
		for (int tim = 0; tim < n * n; tim++) {
			// 6. Randomly choose i and j (i, j belong N = {1,...,n} , 1 <= i <= j <= n).
			int i = (int) Math.round(Math.random() * (n - 1));//rand() % n;
			int j = (int) Math.round(Math.random() * (n - 1));//rand() % n;
			if (i > j) {
				int tmp = i;
				i = j;
				j = tmp;
			}
			// 7. From the circuit with all vertices remove a path beginning with vertex i through vertex j, and connect vertex i - 1 with vertex j + 1.
			removeSelectedToRest(i, j);
			// 8. Randomly choose a vertex from the removed path.
			while (ior > 0) {
				int rd = (int) Math.round(Math.random() * (ior - 1));// rand() % ior;
				int cur = getRest(rd);
				// 9. Insert this vertex between two neighboring vertices on the tour in the cheapest possible way. If the tour is still incomplete go to step 8.
				int minPos = ios;
				if (ios != 0) { // in case of all remove
					double minVal = curLen + d[selected[ios - 1]][cur]
							+ d[cur][selected[0]]
							- d[selected[ios - 1]][selected[0]];
					for (int k = 0; k < ios - 1; k++)
						if (minVal > curLen + d[selected[k]][cur]
								+ d[cur][selected[k + 1]]
								- d[selected[k]][selected[k + 1]]) {
							minVal = curLen + d[selected[k]][cur]
									+ d[cur][selected[k + 1]]
									- d[selected[k]][selected[k + 1]];
							minPos = k + 1;
						}
				}
				insertSelected(minPos, cur);
			}
			// 10. Compare current solution with the solution S. Keep the better one.
			if (curLen < tBestLen) {
				tBestLen = curLen;
				for (int k = 0; k < n; k++)
					tBestPath[k] = selected[k];
			} else {
				curLen = tBestLen;
				for (int k = 0; k < n; k++)
					selected[k] = tBestPath[k];
			}
			rotate();
		}
	}

	/*
	 * return rest[idx],  delete idx'th element from rest[]
	 */
	int getRest(int idx){
	    int ret = rest[idx];
	    for (int i = idx;i < ior - 1;i++)
	        rest[i] = rest[i + 1];
	    ior--;
	    return ret;
	}

	/*
	 * insert val to selected[]'idx position
	 */
	void insertSelected(int idx, int val){ 
	     if (ios != 0){
	         curLen += d[selected[idx - 1]][val];
	         curLen += d[val][selected[idx % ios]];
	         curLen -= d[selected[idx - 1]][selected[idx % ios]];
	         for (int i = ios;i > idx;i--)
	             selected[i] = selected[i - 1];
	     }
	     selected[idx] = val;
	     ios++;
	}

	/*
	 * remove selected[l..r] to rest[]
	 */
	void removeSelectedToRest(int l, int r){
	     for (int i = l;i <= r;i++)
	         rest[ior++] = selected[i];
	     if (l == 0 && r == ios - 1){  //all remove
	        curLen = 0.0;
	        ios = 0;
	        return;
	     }
	     curLen -= d[selected[(l + ios - 1) % ios]][selected[l]];
	     for (int i = l;i < r;i++)
	         curLen -= d[selected[i]][selected[i + 1]];
	     curLen -= d[selected[r]][selected[(r + 1) % ios]];
	     curLen += d[selected[(l + ios - 1) % ios]][selected[(r + 1) % ios]];
	     int i = l;
	     int j = r + 1;
	     while (j < ios){
	           selected[i] = selected[j];
	           i++;
	           j++;
	     }
	     ios = i;
	}

	/*
	 * selected[] right shift once
	 */
	void rotate(){
	     int tmp = selected[0];
	     for (int i = 1;i < n;i++)
	         selected[i - 1] = selected[i];
	     selected[n - 1] = tmp;
	}
}
