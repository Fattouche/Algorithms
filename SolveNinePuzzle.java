import java.util.*;

//This class takes a 3x3 sliding board as an argument when the program is ran. 
//0 being the empty space and finds the shortest path to the goal state with BFS.


public class  SolveNinePuzzle{
	
	//Need to make a tree class to keep track of the shortest path.
	//Once the final state is reached, we will traverse back up the tree
	//only printing the previous board state(parent) of each board.
	
	static class Tree{
		public Board root;
		public void addChild(Board parent,Board insert){
			parent.children.add(insert);
			insert.parent=parent;
		}
		public void printPath(Board child,String start){
			Board curr=child;
			List<int[][]> options = new ArrayList<>();
			while(!curr.string.equals(start)){
				options.add(curr.board);
				curr=curr.parent;
			}
			for(int j=options.size()-1;j>=0;j--){
				int[][] array = options.get(j);
				System.out.println();
				for (int i = 0; i < array.length; i++) {
					int []sub=array[i];
					for (int x = 0; x < 3; x++) {
					System.out.print(sub[x] + " ");
					}
					System.out.println();
				}
			}
		}
	}
	//The boards act as nodes for the tree, it contains a 2d array, a string which is the 2d
	//array stringified so we can check the hashSet and it also contains, 1 parent and potentially
	//multiple children
	static class Board{
		int [][]board;
		String string;
		public ArrayList<Board> children=new ArrayList<Board>();
		Board parent;
	}
	
	
	//A simple clone method so that the arrays of the new board are stored by value and not reference
	public static int[][] clone(int [][]cloner){
		int [][]cloned=new int[3][3];
		for(int i=0;i<3;i++){
			cloned[i]=cloner[i].clone();
		}
		return cloned;
	}
	
	
	/*
	*This is the main Algorithm that finds the shortest path.
	*
	*myTree - stores the paths of all the board states, the shortest can be found by traversing backwards 
	*check - a HashSet to make sure we havent visited that board state before
	*Q - The Q to preform the bFS with, makes sure that all adjacent board states are visited before progressing.
	*
	*The algorithm is O(n^2) because the 2d array inside will only ever happend a max of 9 times
	*and the while loop iterates through the entire queue of board states.
	*
	*If the board isnt at the goal state, it will shift move a token into the empty space.
	*The new board will be put in the tree, queue and hashSet.
	*/
	
	public static boolean BFS(int[][] EndState, int[][] StartState){
		Tree myTree = new Tree();
		HashSet<String> check = new HashSet<String>();
		Queue<Board> Q = new ArrayDeque<>();
		String startString=Arrays.deepToString(StartState);
		String goalString=Arrays.deepToString(EndState);
		Board start = new Board();
		start.string=startString;
		start.board = StartState;
		check.add(start.string);
		myTree.root=start;
		int temp;
		Board curr;
		Q.add(start);
		int i=0;
		int j=0;
		while(!Q.isEmpty()){
			curr=Q.remove();
			//Will only happend max of 9 times every iteration
			label:
			for(i=0;i<3;i++){
				for(j=0;j<3;j++){
					if(curr.board[i][j]==0){
						break label;
					}
				}
			}

			Board boardLeft=new Board();
			Board boardDown=new Board();
			Board boardRight=new Board();
			Board boardUp=new Board();
			
		
			boardLeft.board=clone(curr.board);
			boardDown.board=clone(curr.board);
			boardRight.board=clone(curr.board);
			boardUp.board=clone(curr.board);
			
			if(curr.string.equals(goalString)){
				myTree.printPath(curr,startString);
				return true;
			}
			if(i-1>=0){
				temp=curr.board[i-1][j];
				boardLeft.board[i-1][j]=0;
				boardLeft.board[i][j]=temp;
				boardLeft.string=Arrays.deepToString(boardLeft.board);
				boardLeft.parent=curr;
				if(!check.contains(boardLeft.string)){
					check.add(boardLeft.string);
					myTree.addChild(curr,boardLeft);
					Q.add(boardLeft);
				}
			}
			
			if(j-1>=0){
				temp=curr.board[i][j-1];
				boardDown.board[i][j-1]=0;
				boardDown.board[i][j]=temp;
				boardDown.string=Arrays.deepToString(boardDown.board);
				boardDown.parent=curr;
				if(!check.contains(boardDown.string)){
					check.add(boardDown.string);
					myTree.addChild(curr,boardDown);
					Q.add(boardDown);
				}
			}
			if(j+1<3){
				temp=curr.board[i][j+1];
				boardUp.board[i][j+1]=0;
				boardUp.board[i][j]=temp;
				boardUp.string=Arrays.deepToString(boardUp.board);
				boardUp.parent=curr;
				if(!check.contains(boardUp.string)){
					check.add(boardUp.string);
					myTree.addChild(curr,boardUp);
					Q.add(boardUp);
				}
			}
			if(i+1<3){
				temp=curr.board[i+1][j];
				boardRight.board[i+1][j]=0;
				boardRight.board[i][j]=temp;
				boardRight.string=Arrays.deepToString(boardRight.board);
				boardRight.parent=curr;
				if(!check.contains(boardRight.string)){
					check.add(boardRight.string);
					myTree.addChild(curr,boardRight);
					Q.add(boardRight);
				}
			}
			
		}
		return false;
	
	}
	
	
	public static void main(String args[]){
		int[][] end = {{1,2,3},{4,5,6},{7,8,0}};
		System.out.println("Reading input board");
		String[] Arg=new String[3];
		int [][] start=new int[3][3];
		if (args.length > 0) {
			try {
				Arg = args;
				for(int i=0;i<3;i++){
					for(int j=0;j<3;j++){
						start[i][j]=Character.getNumericValue(Arg[i].charAt(j));
					}
				}
			} catch (Exception e) {
				System.err.println("Argument must be an 3X3 array where each row is separated with a space. EX: 413 026 758 would produce \n413\n026\n758");
				System.exit(1);
			}
		}
		else{
			System.err.println("You need to input 3X3 array where each row is separated with a space. EX: 413 026 758 would produce \n413\n026\n758");
			System.exit(1);
		}
		
		System.out.println("input Board:");
		for (int i = 0; i < 3; i++) {
			int [] sub=start[i];
			for (int x = 0; x < 3; x++) {
				System.out.print(sub[x] + " ");
			}
			System.out.println();
		}
		

		System.out.println("\nAttempting to solve input board");
		long startTime = System.currentTimeMillis();
		boolean checkValid = BFS(end,start);
		long endTime = System.currentTimeMillis();
		double totalTimeSeconds = (endTime-startTime)/1000.0;
		System.out.println();
		if(checkValid)
			System.out.println("Solvable board");
		if(!checkValid)
			System.out.println("Not solvable!");
		System.out.printf("Total Time (seconds): %.4f\n",totalTimeSeconds);
		
	}
}