

import java.util.*;
import java.io.File;

public class TripleSum {

	
	public static boolean TripleSum (int[] A){
		//A is a sorted array of length less than or equal to 226
		A=AlexSort(A);
		//This nested loop will be constant time because A.length will always be less than or equal to 226.
		for(int i = 0; i < A.length;i++){
			int start = i;
			int start1 = i + 1;
			int end = A.length-1;
			while(start1<=end){
				if(Summed(A[start],A[start1],A[end])){
					return true;
				}
				if(A[start]+A[start1]+A[end]<225){
					start1++;
				}
				if(A[start]+A[start1]+A[end]>225){
					end--;
				}
			}
		}
		
		return false;
		
	}
	
	//Checks all possible combinations of the three numbers to sum to 225
	public static boolean Summed(int a,int b,int c){
		if(a+b+c==225){
			return true;
		}
		if(a*3==225||b*3==225||c*3==225)
			return true;
		if(a*2+b==225||a*2+c==225)
			return true;
		if(b*2+a==225||b*2+c==225)
			return true;
		if(c*2+a==225||c*2+b==225)
			return true;
		return false;
	}
	
	
	public static int[] AlexSort(int[] a) {
		//creates a new array of size 226 because we only care about numbers <=225
        int arr[] = new int[226];
		
		//If the original array contains a number less than 226 we increase the index of that number in our new array. EX: if a[i] is 4 we will increase index 4;
        for (int i = 0; i < a.length; i++){
			if(a[i]<226){
				if(arr[a[i]]==0)
					arr[a[i]]++;
			}
		}
		//Counts the number of ones in our new array, each 1 represents that the index number was in our previous array.
		int counter=0;
		int indexSpot=0;
		for(int i=0;i<226;i++){
			if(arr[i]==1){
				counter++;
			}
		}
		//Creates a new array of size less than or equal to 226
		int finalarr[] = new int[counter];
		
		//Iterates through the array of ones and zeros and inputs the index number into our new array. 
		//EX: if a[4]==1 we will input 4 into our array, this operation returns a sorted array.
		
		for(int i=0;i<arr.length;i++){
			if(arr[i]==1){
				finalarr[indexSpot]=i;
				indexSpot++;
			}
		}
		//returns an array of size<=226
        return finalarr;
    }


	

	//This code was taken from the csc225 lab 2
	/* main()
	 Contains code to test the TripleSum  function.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		Vector<Integer> inputVector = new Vector<Integer>();

		int v;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputVector.add(v);

		int[] array = new int[inputVector.size()];

		for (int i = 0; i < array.length; i++)
			array[i] = inputVector.get(i);

		System.out.printf("Read %d values.\n",array.length);


		long startTime = System.currentTimeMillis();

		boolean pairExists = TripleSum (array);

		long endTime = System.currentTimeMillis();

		double totalTimeSeconds = (endTime-startTime)/1000.0;

		System.out.printf("Array %s a triple which adds to 225.\n",pairExists? "contains":"does not contain");
		System.out.printf("Total Time (seconds): %.4f\n",totalTimeSeconds);
	}

}
