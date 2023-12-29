import java.io.*;
import java.util.regex.*;

public class Assign4
{
	private File query = null;
	private File matrix = null;
	private File out1 = null;
	private File out2 = null;
	private File out3 = null;
	private Vector<Vector<Element>> graph = new Vector<Vector<Element>>();
	private boolean[] visited;
	private int[] previous;
	private int[] distance;
	private int n;
	private Vector<Integer> start = null;
	private Vector<Integer> finish = null;
	
	private static final String NUMBER_REGEX = "[0-9]{1,2}";
	private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
	
	private static final String USAGE = "\n"
		+ "Invoke program as follows: java Assign4 input query output1 ouput2\n"
		+ "\n"
		+ "input -> the name of a file ending in .txt, the file should contain an\n"
		+ "  n x n matrix representing relationships between nodes in a graph. Colomns\n"
		+ "  must be separated by whitespace and rows must be separated by newline\n"
		+ "  characters.\n"
		+ "\n"
		+ "query -> the name of a file ending in .txt, the file should contain ordered\n"
		+ "  pairs in the form of two numbers on each line, separated by whitespace.\n"
		+ "  The program will attempt to find a path through the input graph that\n"
		+ "  connects each set of points, if any of the numbers in the query file\n"
		+ "  are larger than n-1, the program will not execute fully.\n"
		+ "\n"
		+ "output1 -> the name of a file ending in .txt, the program will create this\n"
		+ "  file and populate it with the solutions to the query file. These\n"
		+ "  solutions will be found using a depth first graph traversal. In the case\n"
		+ "  that there are no paths between the nodes in a query, the program will\n"
		+ "  output a solution with -1 as an intermediate node.\n"
		+ "\n"
		+ "output2 -> output2 serves the same role as output1, but instead uses a\n"
		+ "  breadth first graph traversal to find paths.\n"
		+ "\n"
		+ "output3 -> output3 serves the same role as output1 and output2, but instead\n"
		+ "  uses Dijkstra's algorithm to find the shortest weighted paths.\n"
		+ "\n"
		+ "WARNING! - any .txt files sharing names with output1 or output2 will be overwritten.\n";
	
	public Assign4(String query, String matrix, String out1, String out2, String out3)
	{
		this.query = new File(query);
		this.matrix = new File(matrix);
		this.out1 = new File(out1);
		this.out2 = new File(out2);
		this.out3 = new File(out3);
	}
	
	public void readInput()
	{
		if(!(matrix.exists())){
			System.out.println("Program terminated: input file does not exist.");
			System.out.print(USAGE);
			System.exit(0);
		}
		
		String currentLine;
		BufferedReader in = null;
		Matcher matcher = NUMBER_PATTERN.matcher("No match");
		int j = 0;
		int currentInt;
		Vector<Element> currentArr = null;
		
		try{
			in = new BufferedReader(new FileReader(matrix));
			
			while((currentLine = in.readLine()) != null){
				
				currentArr = new Vector<Element>();
				graph.append(currentArr);
				
				matcher.reset(currentLine);
				j = 0;
				
				while(matcher.find()){
					currentInt = Integer.parseInt(matcher.group());
					if(currentInt > 0){
						currentArr.append(new Element(j, currentInt));
					}
					j++;
				}
			}
			in.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}	
	}
	
	public void readQuery()
	{
		if(!(query.exists())){
			System.out.println("Program terminated: query file does not exist.");
			System.out.print(USAGE);
			System.exit(0);
		}
		
		String currentLine;
		BufferedReader in = null;
		Matcher matcher = NUMBER_PATTERN.matcher("No match");
		Integer currentInt = null;
		start = new Vector<Integer>();
		finish = new Vector<Integer>();
		
		try{
			in = new BufferedReader(new FileReader(query));
			
			while((currentLine = in.readLine()) != null){
			
				matcher.reset(currentLine);
				
				if(matcher.find()){
					currentInt = Integer.valueOf(matcher.group());
					start.append(currentInt);
				}
				else{
					System.out.println("Program terminated: query file format incorrect.");
					System.out.print(USAGE);
					System.exit(0);
				}
				
				if(matcher.find()){
					currentInt = Integer.valueOf(matcher.group());
					finish.append(currentInt);
				}
				else{
					System.out.println("Program terminated: query file format incorrect.");
					System.out.print(USAGE);
					System.exit(0);
				}
				
				if(matcher.find()){
					System.out.println("Program terminated: query file format incorrect.");
					System.out.print(USAGE);
					System.exit(0);
				}	
			}
			in.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void searchReset()
	{
		n = graph.length();
		visited = new boolean[n];
		previous = new int[n];
		distance = new int[n];
		
		for(int i = 0; i < n; i++){
			visited[i] = false;
			previous[i] = -1;
			distance[i] = Integer.MAX_VALUE;
		}
	}
	
	private void depthFirst(int node)
	{
		if(visited[node]) return;
		
		visited[node] = true;
		Vector<Element> neighbors = graph.getAt(node);
		int next;
		
		for(int i = 0; i < neighbors.length(); i++){
			next = neighbors.getAt(i).index;
			previous[next] = node;
			depthFirst(next);
		}
	}
	
	public void depthFirst()
	{
		Vector<Integer> path = null;
		Integer current = null;
		int i, j;
		StringBuilder sb = new StringBuilder();
		
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(out1));

			for(i = 0; i < start.length(); i++){
				path = new Vector<Integer>();
				path.append(finish.getAt(i));
				current = finish.getAt(i);
				searchReset();
				depthFirst(start.getAt(i));
				
				while(true){
					current = Integer.valueOf(previous[current.intValue()]);
					path.append(current);
					
					if(current.intValue() == -1){
						path = new Vector<Integer>();
						path.append(finish.getAt(i));
						path.append(Integer.valueOf(-1));
						path.append(start.getAt(i));
						break;
					}
					
					if(current.equals(start.getAt(i))){
						break;
					}
				}
				
				for(j = path.length() - 1; j >= 0; j--){
					if(j == 0){
						sb.append(path.getAt(j).toString());
						sb.append("\n");
					}
					else{
						sb.append(path.getAt(j).toString());
						sb.append(", ");
					}
				}
			}
			out.write(sb.toString(), 0, sb.length());
			out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void breadthFirst(int node)
	{
		Integer nodeWrap = Integer.valueOf(node);
		Queue<Integer> q = new Queue<Integer>();
		q.enqueue(nodeWrap);
		Integer current = null;
		Vector<Element> neighbors = null;
		int i;
		int next;
		
		visited[node] = true;
		
		while(!(q.isEmpty())){
			current = q.dequeue();
			
			neighbors = graph.getAt(current.intValue());
			
			for(i = 0; i < neighbors.length(); i++){
				next = neighbors.getAt(i).index;
				
				if(!(visited[next])){
					q.enqueue(Integer.valueOf(next));
					visited[next] = true;
					previous[next] = current.intValue();
				}
			}
		}		
	}
	
	public void breadthFirst()
	{
		Vector<Integer> path = null;
		Integer current = null;
		int i, j;
		StringBuilder sb = new StringBuilder();
		
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(out2));

			for(i = 0; i < start.length(); i++){
				path = new Vector<Integer>();
				path.append(finish.getAt(i));
				current = finish.getAt(i);
				searchReset();
				breadthFirst(start.getAt(i));
				
				while(true){
					current = Integer.valueOf(previous[current.intValue()]);
					path.append(current);
					
					if(current.intValue() == -1){
						path = new Vector<Integer>();
						path.append(finish.getAt(i));
						path.append(Integer.valueOf(-1));
						path.append(start.getAt(i));
						break;
					}
					
					if(current.equals(start.getAt(i))){
						break;
					}
				}
				
				for(j = path.length() - 1; j >= 0; j--){
					if(j == 0){
						sb.append(path.getAt(j).toString());
						sb.append("\n");
					}
					else{
						sb.append(path.getAt(j).toString());
						sb.append(", ");
					}
				}
			}
			out.write(sb.toString(), 0, sb.length());
			out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void weighted(int node)
	{
		distance[node] = 0;
		
		PrQueue heap = new PrQueue();
		heap.insert(new Element(node, 0));
		Element current = null;
		Vector<Element> currentList = null;
		Element edge = null;
		int newDist;
		int i;
		
		while(heap.size() != 0){
			current = heap.poll();
			visited[current.index] = true;
			currentList = graph.getAt(current.index);
			
			for(i = 0; i < currentList.length(); i++){
				edge = currentList.getAt(i);
				
				if(visited[edge.index]) continue;
				
				newDist = distance[current.index] + edge.cost;
				if(newDist < distance[edge.index]){
					previous[edge.index] = current.index;
					distance[edge.index] = newDist;
					heap.insert(new Element(edge.index, newDist));
				}
			}
		}			
	}
	
	public void weighted()
	{
		Vector<Integer> path = null;
		Integer current = null;
		int i, j;
		StringBuilder sb = new StringBuilder();
		
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(out3));

			for(i = 0; i < start.length(); i++){
				path = new Vector<Integer>();
				current = finish.getAt(i);
				searchReset();
				weighted(start.getAt(i));
				
				if(distance[current.intValue()] == Integer.MAX_VALUE){
					path = new Vector<Integer>();
					path.append(finish.getAt(i));
					path.append(Integer.valueOf(-1));
					path.append(start.getAt(i));
				}
				else{
					for(int node = current.intValue(); node != -1; node = previous[node]){
						path.append(Integer.valueOf(node));
					}
				}
				
				for(j = path.length() - 1; j >= 0; j--){
					if(j == 0){
						sb.append(path.getAt(j).toString());
						sb.append("\n");
					}
					else{
						sb.append(path.getAt(j).toString());
						sb.append(", ");
					}
				}
			}
			out.write(sb.toString(), 0, sb.length());
			out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		if(args.length < 5 || args.length > 5){
			System.out.println("Incorrect usage of command line arguments");
			System.out.print(USAGE);
			System.exit(0);
		}
		
		Assign4 graphObj = new Assign4(args[1], args[0], args[2], args[3], args[4]);
		graphObj.readInput();
		graphObj.readQuery();

		graphObj.depthFirst();
		graphObj.breadthFirst();
		graphObj.weighted();
	}
}