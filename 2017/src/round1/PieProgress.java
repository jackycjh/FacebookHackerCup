package round1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PieProgress {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("2017/R1.PieProgress/pie_progress.txt"));
		
		int T = scan.nextInt();
		
		for(int Tth=1; Tth <= T; Tth++) {
			int N = scan.nextInt();
			int M = scan.nextInt();
			
			int inputs[][] = new int[N][M];
			
			for(int Nth=0; Nth<N; Nth++) {
				for(int Mth=0; Mth<M; Mth++) {
					inputs[Nth][Mth] = scan.nextInt();
				}
			}
			
			//debug
			/*
			System.out.println("Before actual cost");
			for(int Nth=0; Nth<N; Nth++) {
				for(int Mth=0; Mth<M; Mth++) {
					System.out.print( inputs[Nth][Mth] + " ");
				}
				System.out.println();
			}*/
			
			List<ArrayList> localSorts = new ArrayList<ArrayList>();
			for(int Nth=0; Nth<N; Nth++) {
				ArrayList<POTD> innerLocalSorts = new ArrayList<POTD>();
				// add entries
				for(int Mth=0; Mth<M; Mth++) {
					innerLocalSorts.add(new POTD(Nth, Mth, inputs[Nth][Mth]));
				}
				// sort inner
				Collections.sort( innerLocalSorts );
				
				int c = 0;
				// normalize actual costs
				for(int Mth=0; Mth<M; Mth++) {
					POTD pie = innerLocalSorts.get(Mth);
					pie.accumulatedCost = (int) (pie.cost + Math.pow((Mth + 1), 2));
					innerLocalSorts.set( Mth, pie );
					if( Mth >0 ) {
						// add previous unnormalized costs
						//innerLocalSorts.set( Mth, innerLocalSorts.get(Mth) + c);
						pie.accumulatedCost = pie.accumulatedCost + c;
						innerLocalSorts.set( Mth, pie );
					}
					c += pie.cost ;
				}
				
				localSorts.add(innerLocalSorts);
			}
			
			//debug
			/*
			System.out.println("After actual cost");
			for(int Nth=0; Nth<N; Nth++) {
				for(int Mth=0; Mth<M; Mth++) {
					POTD pie = (POTD) localSorts.get(Nth).get(Mth);
					System.out.print( pie.accumulatedCost + " ");
				}
				System.out.println();
			}*/
			
			// Sort everything
			List<POTD> mainSort = new ArrayList<POTD>();
			for(int Nth=0; Nth<N; Nth++) {
				for(int Mth=0; Mth<M; Mth++) {
					mainSort.add((POTD) localSorts.get(Nth).get(Mth));
				}
			}
			Collections.sort(mainSort);
			
			//debug
			/*
			System.out.println("Main sort:");
			for( POTD p : mainSort) {
				System.out.print(p.accumulatedCost + " ");
			}*/
			
			// select smallest first
			for(int Nth=0; Nth<N; Nth++) {
				POTD pie = mainSort.get(Nth);
				pie.markAsPreferred();
				mainSort.set(Nth, pie);
			}
			
			List<Integer> selection = new ArrayList<Integer>();
//			System.out.print("Selection size: " + selection.size());
//			System.out.print("N size: " + N);
			for(int x=0; x<N; x++) {
				selection.add(0);
			}
//			System.out.print("Selection size: " + selection.size());
			for(int x=0; x<mainSort.size(); x++) {
				POTD pie = mainSort.get(x);
				if( pie.isPreferred )
				{
					selection.set(pie.i, selection.get(pie.i) + 1);
				}
			}
			
			//debug
			/*
			System.out.println("Selection:");
			for(int x=0; x<selection.size(); x++) {
				System.out.print(selection.get(x) + " ");
			}*/
			
			// arrange inventory
			boolean everydayPie = false;
			int cycle = 1;
			while( !everydayPie ) {
				//debug
				/*
				System.out.print("\nCycle #" + cycle + ": ");
				for(int x=0; x<selection.size(); x++) {
					System.out.print(selection.get(x) + " ");
				}*/
				cycle++;
				
				everydayPie = true;
				int pieLeft = 0;
				for(int x=0; x<N; x++) {
					pieLeft += selection.get(x);
					
					if(pieLeft < 1) {
						everydayPie = false;
						
						// buy at least one pie for the day
						selection.set(x, 1);
						pieLeft += selection.get(x);
						
						// add 1 (smallest) from any other day
						boolean found = false;
						for(int y=mainSort.size()-1; y>=0; y--) {
							POTD pie = mainSort.get(y);
							if( pie.isPreferred ) {
								pie.isPreferred = false;
								mainSort.set(y, pie);
								if( y+1 < mainSort.size()) {
									pie = mainSort.get( y+1 );
									pie.isPreferred = true;
									mainSort.set(y+1, pie);
								}
							}
						}
						
						// reset selection
						for(int y=0; y<N; y++) {
							selection.set(y, 0);
						}
						for(int y=0; y<mainSort.size(); y++) {
							POTD pie = mainSort.get(y);
							if( pie.isPreferred )
							{
								selection.set(pie.i, selection.get(pie.i) + 1);
							}
						}
					}
					
					// eat one
					pieLeft--;
				}
			}//end while
			
			// calculate costs!
			int totalCost = 0;
			for(int s=0; s<selection.size(); s++) {
				int pies = selection.get(s);
				if( pies > 0 ) {
					POTD pie = (POTD) localSorts.get(s).get(pies-1);
					totalCost += pie.accumulatedCost;
				}
			}
			System.out.println("Case #" + Tth + ": " + totalCost);
		}
	}

	private static class POTD implements Comparable<POTD>{
		int i;
		int j;
		int cost;
		int accumulatedCost = 0;
		boolean isPreferred = false;
		public POTD( int i, int j, int cost) {
			this.i = i;
			this.j = j;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(POTD other) {
			int value = this.accumulatedCost - other.accumulatedCost;
			if( value == 0 ) {
				value = this.cost - other.cost;
			}
			
			return value;
		}
		
		public int getAccumulatedCost() { return this.accumulatedCost; }
		
		public void markAsPreferred() { this.isPreferred = true; }
	}
}
