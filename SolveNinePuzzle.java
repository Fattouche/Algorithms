import java.util.*;

//This class takes a 3x3 sliding board with 0 being the empty space and finds the shortest
//path to the goal state with BFS.

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
			List<String> options = new ArrayList<>();
			while(!curr.string.equals(start)){
				options.add(curr.string);
				curr=curr.parent;
			}
			for(int i=options.size()-1;i>=0;i--){
				System.out.println(options.remove(i));
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
		int[][] start = {{4,1,3},{0,2,6},{7,5,8}};
		int[][] end = {{1,2,3},{4,5,6},{7,8,0}};
		System.out.println(BFS(end,start));
	}
}
