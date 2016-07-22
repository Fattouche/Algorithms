import java.util.*;
import java.io.File;


public class CountInversions{
	
	//Since the inner loop only runs a max of K times(K=inversions), this algorithm will run in O(n+k) time.
	
	public static long CountInversions(int[] input){
		int i=1;
		int tracker=i;
		int count=0;
		int temp;
		while(i<input.length){
			//This inner while loop will only run a total of K times over the entire iteration through the array
			while(tracker>0){
				if(input[tracker]<input[tracker-1]){//start at the i=1 index, if the value of input[i-1] is less than the value at input[i],
					temp=input[tracker];			//we will swap the two values and increase the counter of inversions. 
					input[tracker]=input[tracker-1];
					input[tracker-1]=temp;
					count++;
					tracker--;
				}else{
					break;							//If the input[i] is greater than the input[i-1] we will quit the loop, this makes it run only k times
				}
			}
			i++;									
			tracker=i;
		}
		return count;
       
	}
	
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

		long inversions = CountInversions(array);

		long endTime = System.currentTimeMillis();

		double totalTimeSeconds = (endTime-startTime)/1000.0;

		System.out.printf("Array has "+inversions+" inversions\n");
		System.out.printf("Total Time (seconds): %.4f\n",totalTimeSeconds);
	}
}