import java.io.*;

public class Assign5
{
	private StringHash table;
	private File in;
	private File out;
	private boolean useQuad;
	private int chain = 0;
	private int reads;
	private int records;
	private int collisions;
	
	private static final String USAGE = "\n"
		+ "Invoke program as follows: java Assign5 input output -q\n"
		+ "\n"
		+ "input -> the name of a file ending in .txt, the file should contain a\n"
		+ "  list of strings, separated by newline characters. The file should not\n"
		+ "  contain any repeated elements.\n"
		+ "\n"
		+ "output -> the name of a file ending in .txt, this file will be created or\n"
		+ "  overwritten when the program is run. This file will include statistics\n"
		+ "  regarding the performance of the program.\n"
		+ "\n"
		+ "-q -> this is an optional flag. If it is included, the program will use\n"
		+ "  quadratic probing instead of linear probing when resolving collisions.\n"
		+ "\n"
		+ "WARNING! - any .txt files sharing a name with output will be overwritten.\n";
	
	// Constructor
	public Assign5(String in, String out, boolean useQuad)
	{
		this.in = new File(in);
		this.out = new File(out);
		this.useQuad = useQuad;
		
		textToTable(useQuad);
	}
	
	
	// Methods //
	
	// Read input from file and store in a new StringHash onject
	public void textToTable(boolean useQuad)
	{
		if(!(in.exists())){
			System.out.println("Program terminated: input file does not exist.");
			System.out.print(USAGE);
			System.exit(0);
		}
		
		// Type of probing is specified when creating table
		if(useQuad){
			table = new StringHash(1);
		}
		else{
			table = new StringHash(0);
		}
		
		String currentLine;
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(in));
			
			while((currentLine = reader.readLine()) != null){
				
				table.insert(currentLine);
			}
			reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	// Read input from file and search the table for each String. Print
	// statistics to ouput file
	public void printStats()
	{
		if(!(in.exists())){
			System.out.println("Program terminated: input file does not exist.");
			System.out.print(USAGE);
			System.exit(0);
		}
		
		String currentLine;
		BufferedReader reader = null;
		records = 0;
		reads = 0;
		collisions = 0;
		
		// Start by reading input while keeping track of stats
		try{
			reader = new BufferedReader(new FileReader(in));
			
			while((currentLine = reader.readLine()) != null){
				
				if(table.search(currentLine) < 0){
					System.out.println("Program terminated: string not found");
					System.exit(0);
				}
				
				if(table.getLastChain() > chain){
					chain = table.getLastChain();
				}
				
				if(table.getLastChain() > 1){
					collisions += 1;
				}
				
				reads += table.getLastChain();
				records += 1;
			}
			reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		// Calculate efficiency stat
		float efficiency = (float)1.0 - (float)collisions / (float)records;
		efficiency = efficiency * 100;
		
		// Print statistic to output file.
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(out));

			sb.append("(Input file: ");
			sb.append(in);
			sb.append(")\n");
			
			if(useQuad){
				sb.append("\nStats for hashing using quadratic probing:\n");
			}
			else{
				sb.append("\nStats for hashing using linear probing:\n");
			}
			
			sb.append("\n - Mean reads per record: ");
			sb.append(String.format("%.3f",(float)reads / (float)records));
			sb.append('\n');
			
			sb.append("\n - Final load factor: ");
			sb.append(String.format("%.2f", table.getLoad()));
			sb.append('\n');
			
			sb.append("\n - Hashing efficiency: ");
			sb.append(String.format("%.2f", efficiency));
			sb.append("%\n");
			
			sb.append("\n - Longest chain: ");
			sb.append(chain);
			sb.append('\n');
			
			writer.write(sb.toString(), 0, sb.length());
			writer.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	// main //
	
	public static void main(String[] args)
	{
		if(args.length < 2 || args.length > 3){
			System.out.println("Incorrect usage of command line arguments");
			System.out.print(USAGE);
			System.exit(0);
		}
		
		if(args.length == 3 && !(args[2].equals("-q"))){
			System.out.println("Incorrect usage of command line arguments");
			System.out.print(USAGE);
			System.exit(0);
		}
			
		Assign5 myObj = null;
			
		if(args.length == 2){
			myObj = new Assign5(args[0], args[1], false);
		}
		else{
			myObj = new Assign5(args[0], args[1], true);
		}
			
		myObj.printStats();
	}
}