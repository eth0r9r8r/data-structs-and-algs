public class StringHash
{
	private String[] arr;
	private int length;
	private int capacity;
	private final float LOAD_FACTOR = (float)0.9;
	private boolean useQuad;
	private int lastChain = 0;
	
	// Constructor
	public StringHash(int useQuad)
	{
		arr = null;
		length = 0;
		capacity = 0;
		
		if(useQuad == 0){
			this.useQuad = false;
		}
		
		else{
			this.useQuad = true;
		}
	}
	
	// Public methods: //
	
	// Insert new string
	public void insert(String newStr) throws IllegalArgumentException
	{
		if(arr == null){
			arr = new String[1];
		}
		
		float load = getLoad();
		
		if(load >= LOAD_FACTOR){
			resize(nextCap());
		}
		
		int index = hash(newStr);
		int quadCount = 1;
		
		while(arr[index] != null){
			if(arr[index].equals(newStr)){
				throw new IllegalArgumentException("attempted to insert a duplicate");
			}
			
			if(useQuad){
				index = quadProbe(index, quadCount);
				quadCount++;
			}
			else{
				index = linearProbe(index);
			}
		}
		
		arr[index] = newStr;
		length++;
	}
	
	// Look for provided string in table, if the string is found,
	// return index, else return -1
	public int search(String query)
	{
		int index = hash(query);
		int readCount = 1;
		lastChain = 0;
		
		while(arr[index] != null){
			lastChain += 1;
			
			if(arr[index].equals(query)){
				return index;
			}
			
			if(useQuad){
				index = quadProbe(index, lastChain);
			}
			else{
				index = linearProbe(index);
			}
		}
		lastChain = 0;
		return -1;
	}
	
	// Helper methods: //
	
	// In case of collision, find new index using linear probing
	private int linearProbe(int index)
	{
		index += 1;
		
		return index % arr.length;
	}
	
	// In case of collision, find new index using quadratic probing
	private int quadProbe(int index, int count)
	{
		index += count*count;
		
		return index % arr.length;
	}
	
	// Replaces arr with a new array of size newCapacity,
	// all values are remaped to new table
	private void resize(int newCapacity)
	{
		String[] oldArr = arr;
		
		arr = new String[newCapacity];
		
		for(int i = 0; i < oldArr.length; i++){
			
			if(oldArr[i] == null) continue;
			
			insert(oldArr[i]);
		}
	}
	
	// hash function
	private int hash(String newStr)
	{
		int length = newStr.length();
		int tag = 0;
		int uniVal;
		int power;
		
		for(int i = 0; i < 10 && i < length; i++){
			uniVal = newStr.codePointAt(i);
			power = (int)Math.pow(3, i);
			tag += uniVal * power;
		}
		
		return tag % arr.length;
	}
	
	// Find next prime that is >= capacity * 2
	private int nextCap()
	{
		int nextCap = arr.length * 2;
		boolean notPrime;
		int i;
		
		while(true){
			i = 2;
			notPrime = false;
			
			while(i <= nextCap/2){
				if(nextCap % i == 0){
					notPrime = true;
					break;
				}
				
				i++;
			}
			
			if(!(notPrime)){
				return nextCap;
			}
			
			nextCap++;
		}
	}
	
	// Getters: //
	
	public int getLength(){return length;}
	public int getCapacity(){return arr.length;}
	public float getLoad(){return ((float)length / (float)arr.length);}
	public int getLastChain(){return lastChain;}
}