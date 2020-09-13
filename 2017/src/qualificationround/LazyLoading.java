package qualificationround;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LazyLoading {

	public static void main(String[] args) throws FileNotFoundException {

		System.out.println(new File("2017/QR.LazyLoading/lazy_loading.txt").getAbsolutePath());
		Scanner scan = new Scanner(new File("2017/QR.LazyLoading/lazy_loading.txt"));

		int days = scan.nextInt();
		
		for(int d=1; d<=days; d++) {
			int n = scan.nextInt();
			
			List<Integer> inputs = new ArrayList<Integer>(); 
			for(int num=0; num<n; num++) {
				inputs.add(scan.nextInt());
			}
			
			int bags = 0;
			List<Integer> toBeOptimized = new ArrayList<Integer>(); 
			for(int weight : inputs) {
				if (weight>=50) {
					bags++;
					//debug
					//System.out.println("New bag! " + weight );
				}
				else {
					toBeOptimized.add(weight);
				}
			}
			
			Collections.sort(toBeOptimized);
			int frontCounter = 0;
			int rearCounter = toBeOptimized.size() - 1;
			boolean ended = false;
			while(rearCounter>frontCounter && !ended) {
				// start from largest ie. end
				int currentValue = toBeOptimized.get(rearCounter);
				float diff = 50 - currentValue;
				//debug
				//System.out.println(diff / currentValue);
				int required = (int) Math.ceil( diff / currentValue );
				
				//debug
				//System.out.println(frontCounter + ", " + required + ", " + rearCounter);
				
				if ((frontCounter+required)<=rearCounter) {
					// has stocks
					bags++;
					//debug
					//System.out.println("New bag! " + currentValue );
					
					frontCounter+=required;
				}
				else {
					// check has any bag
					if (bags>0) {
						ended = true;
					}
					else {
						bags++;
						//debug
						//System.out.println("New bag! " + currentValue );
					}
				}
				
				rearCounter--;
			}
			
			// output
			System.out.println("Case #" + d + ": " + bags);
			for(int weight : inputs){
				System.out.print(weight + ", ");
			}
			System.out.println();
		}
	}

}
