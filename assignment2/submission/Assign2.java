import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Assign2
{
	public static void main(String[] args)
	{
		long startTime = System.nanoTime();
		
		// Create new vector.
		Vecta anagramVector = new Vecta();
		
		// Enter every word in input file into vector.
		BufferedReader myReader = null;
		String line;
		
		try{
			myReader = new BufferedReader(new FileReader(args[0]));
			
			while((line = myReader.readLine()) != null){
				anagramVector.newWord(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Sort the vector.
		anagramVector.sort();
		
		// Write output file.
		anagramVector.writeOut(args[1]);
		
		System.out.println(Long.toString(System.nanoTime() - startTime));
	}
}