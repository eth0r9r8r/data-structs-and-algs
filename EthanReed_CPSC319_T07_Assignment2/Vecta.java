import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Vecta
{
	private LinkedList[] arr;
	private int length = 0;
	
	public void newWord(String word)
	{
		if(length == 0){
			append(new LinkedList(word));
			return;
		}
	
		for(int i = 0; i < length; i++){
			if(isAnagram(word, arr[i])){
				arr[i].addToEnd(word);
				return;
			}
		}
		
		append(new LinkedList(word));
	}
	
	public void append(LinkedList list)
	{
		LinkedList[] copy = new LinkedList[length + 1];
		
		if(length > 0){
			for(int i = 0; i < length; i++){
				copy[i] = arr[i];
			}
		}
		
		copy[length] = list;
		arr = copy;
		length++;
	}
	
	public static boolean isAnagram(String word, LinkedList list)
	{
		String listWord = list.getHead().getData();
		
		if(listWord.length() != word.length())
			return false;
		
		char[] charArr1 = word.toCharArray();
		char[] charArr2 = listWord.toCharArray();
		
		charSort(charArr1);
		charSort(charArr2);
		
		for(int i = 0; i < charArr1.length; i++){
			if(charArr1[i] != charArr2[i])
				return false;
		}
		
		return true;
	}
	
	public static void charSort(char[] unSorted)
	{
		int i, j;
		char temp;
		for(i = 1; i < unSorted.length; i++){
			temp = unSorted[i];
			for(j = i; j > 0 && temp < unSorted[j - 1]; j--)
				unSorted[j] = unSorted[j - 1];
			unSorted[j] = temp;
		}
			
	}
	
	public void sort()
	{
		for(int i = 0; i < length; i++)
			arr[i].linkedSort();
		
		quicksort();
	}
	
	public void quicksort()
	{
		if(length < 2)
			return;
		
		int max = 0;
		String maxStr, currentStr;
		
		for(int i = 1; i < length; i++){
			currentStr = arr[i].getHead().getData();
			maxStr = arr[max].getHead().getData();
			
			if(currentStr.compareTo(maxStr) > 0)
				max = i;
		}
		
		swap(length - 1, max);
		
		quicksort(0, length - 1);
	}
	
	public void quicksort(int first, int last)
	{
		int lower = first + 1;
		int upper = last;
		
		swap(first, (first + last)/2);
		
		String bound = arr[first].getHead().getData();
		
		while(lower <= upper){
			while(arr[lower].getHead().getData().compareTo(bound) < 0)
				lower++;
			
			while(arr[upper].getHead().getData().compareTo(bound) > 0)
				upper--;
			
			if(lower < upper){
				swap(lower, upper);
				lower++;
				upper--;
			}
			else
				lower++;
		}
		
		swap(upper, first);
		
		if(first < upper - 1)
			quicksort(first, upper - 1);
		
		if((upper + 1) < last)
			quicksort(upper + 1, last);
	}
	
	public void swap(int indexOne, int indexTwo)
	{
		LinkedList temp = arr[indexOne];
		arr[indexOne] = arr[indexTwo];
		arr[indexTwo] = temp;
	}
	
	public void writeOut(String outFile)
	{
		Node current;
		
		try{
			BufferedWriter myWriter = new BufferedWriter(new FileWriter(outFile));
			for(int i = 0; i < length; i++){
				current = arr[i].getHead();
				while(current != null){
					myWriter.write(current.getData());
					if(current.getNext() != null)
						myWriter.write(" ");
					current = current.getNext();
				}
				
				myWriter.write("\n");
			}
			myWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}