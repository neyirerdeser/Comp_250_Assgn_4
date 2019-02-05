package assignment4Graph;

public class Graph {
	
	boolean[][] adjacency;
	int nbNodes;
	
	public Graph (int nb){
		this.nbNodes = nb;
		this.adjacency = new boolean [nb][nb];
		for (int i = 0; i < nb; i++){
			for (int j = 0; j < nb; j++){
				this.adjacency[i][j] = false;
			}
		}
	}
	
	public void addEdge (int i, int j){
		adjacency[i][j] = true;
  	adjacency[j][i] = true;
	}
	
	public void removeEdge (int i, int j){
		adjacency[i][j] = false;
	  adjacency[j][i] = false;
	}
	
	public int nbEdges(){
		double counter = 0;
	  for(int i =0; i<adjacency.length; i++)
			for(int j=0; j<adjacency[i].length; j++)
		    if(adjacency[i][j]==true) {
		    	if(i==j) counter++;
		    		else counter += 0.5;
		    }
	  return (int)counter;
	}
	
	public boolean cycle(int start){
		return cycleLength(start)>2;
	}
	
	//HELPER METHODS FOR CYCLE (and cycles helper methods') - TOTAL OF 5
	// #1 method to find cycle length
	public int cycleLength(int start){
		int[][]visit = new int[nbNodes][nbNodes];
		// 0 - unvisited edge
		// 1 - visited edge
		// 2 - no edge
		for(int i=0; i<nbNodes; i++) {
			for(int j=0; j<nbNodes; j++) {
				if(i==j) visit[i][j] = 2; // prevents self-loops
				else if(adjacency[i][j]) visit[i][j] = 0;
				else visit[i][j] = 2;
			} 
		}
		int[]path = new int[nbNodes*2];
		for(int i=0; i<path.length; i++) path[i] = nbNodes+1;
		path = DFS(start,-1,start,path,visit);
		int length = 0;
		for(int i=0; i<path.length; i++) {
			if(path[i]==path[0] && i>0) {
				length = i;
				break;
			}
		}
		return length;
	}
	
	// #2 method to help with depth first search
	private int[] DFS(int node,int prev, int start, int[]path, int[][]visited) {
		//System.out.print(node+"\t");
		//if(node!=start) visited[node] = true;
		int tail = -1;
		for(int i=0; i<path.length; i++)
			if(path[i]==nbNodes+1) {
				tail = i;
				break;
			}
		if(prev>=0) visit(visited,node,prev);
		prev = node;
		path[tail] = node;
		int[]adj = connected(node);
		for(int i=0; i<adj.length; i++) {
			// int n = path.length();
			// if(n>1 && path.charAt(0)==path.charAt(n-1)) return path;
			int v = adj[i];
			if(visited[node][v]==0)
				DFS(v,prev,start,path,visited);
		}
		return path;
	}

	// #3 method to change the visited status of the visit matrix
	private void visit(int[][]a, int n, int m) {
		a[n][m] = 1;
		a[m][n] = 1;
	}

	// #4 method to give an array of neighboring vertices for a given vertix
	private int[] connected(int n) {
		int[]connect = new int[0];
		for(int i=0; i<adjacency.length; i++)
			if(adjacency[n][i]==true)
				connect = add(connect,i);
		return connect;
	}

	// #5 method to add a value at the end of a given array by lengthining it
	private static int[] add(int[]a, int n) {
	  int[]b = new int[a.length+1];
	  for(int i=0; i<b.length-1; i++) b[i] = a[i];
	  	b[b.length-1] = n;
	  return b;
 	}

	public int shortestPath(int start, int end){
		if(start==end) {
			if(adjacency[start][start]) return 1;
			else if(cycle(start)) return cycleLength(start);
			else return nbNodes+1;
		}
		int[]path = new int[nbNodes];
		int[]queue = new int[nbNodes];
		for(int i=0; i<path.length; i++)path[i] = nbNodes+1;
		path[start] = 0;
		int head = 0;
		int tail = 0;
		queue[tail] = start;
		tail++;
		while(head<=tail && tail<nbNodes) {
			int curr = queue[head];
			head++;
			for(int i=0; i<adjacency.length; i++)
				if(adjacency[curr][i] && path[i]==nbNodes+1) {
					queue[tail] = i;
					path[i] = path[curr]+1;
					tail++;
				}
		}
		return path[end];
	}
	
}
