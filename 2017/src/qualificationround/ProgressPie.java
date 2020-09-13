package qualificationround;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgressPie {

	static int radius = 50;	
	static int origin_x = 50;
	static int origin_y = 50;
	
	public static void main(String[] args) throws IOException {
		
		// read file
		List<String> inputs = new ArrayList<String>();
		File file = new File("2017/QR.ProgressPie/progress_pie.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       // process the line.
		    	inputs.add(line);
		    }
		}
		
		int T = Integer.parseInt( inputs.get(0) );
		for( int counter=2; counter<=2; counter++) {
			String[] input = inputs.get(counter).split(" ");
			
			float angleInDegrees = Integer.parseInt(input[0]) * 3.6f;
			int input_x = Integer.parseInt(input[1]);
			int input_y = Integer.parseInt(input[2]);
			float input_degree = 0f;
			String answer = new String("white");
			
			if (withinDistance(input_x, input_y)) {
				// get correct formula
				if ((input_x>=50 && input_y>=50) || (input_x<50 && input_y<50)) {
					//guard input_y = 50
					if (input_y == 50) {
						if ( (input_x >= 50 && angleInDegrees >= 90) || ((input_x < 50 && angleInDegrees >= 270)) )
						answer = new String("black");
					}
					input_degree = (float) Math.toDegrees(Math.atan((input_x-origin_x)/(input_y-origin_y)));
				}
				else {
					//guard input_x = 50
					if (input_x == 50 && angleInDegrees > 0) {
						answer = new String("black");
					}
					else
					{
					input_degree = (float) Math.toDegrees(Math.atan((input_y-origin_y)/(input_x-origin_x)));
					}
				}
				
				input_degree = Math.abs(input_degree);
				// debug
				System.out.println( input_degree );
					
				float pad = 0f;
				// add angle padding
				if (input_x>=50 && input_y>=50) pad = 0f;
				else if (input_x>=50 && input_y<50) pad = 90f;
				else if (input_x<50 && input_y<50) pad = 180f;
				else if (input_x<50 && input_y>=50) pad = 270f;
				
				input_degree = input_degree + pad;
				if (input_degree <= angleInDegrees)
					answer = new String("black");
			}
			
			// debug
			System.out.println("Case #" + counter + ": " + input_x + "," + input_y + "\t\t" + input_degree + "," + angleInDegrees + "\t" + answer);
			System.out.println("Case #" + counter + ": " + answer);
		}
	}

	private static boolean withinDistance(int x, int y) {
		return Math.sqrt(Math.pow( y - origin_y, 2) + Math.pow( x - origin_x, 2)) <= radius;
	}
}
