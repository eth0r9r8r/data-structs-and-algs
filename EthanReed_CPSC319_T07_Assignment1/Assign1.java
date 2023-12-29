/**
*Class used for experimentation with varios array sorting algorithms, 
*Such as quicksort, insertion sort, merge sort, and selection sort.
*@author Ethan Reed <a href="mailto:ethan.reed@ucalgary.ca">
*ethan.reed@ucalgary.ca</a>
*@version 1.5
*@since 1.0
*/

import java.util.Random;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Assign1
{
	private int[] arr;
	private int arrSize;
	private String outFileName;
	
	public Assign1(int size, String order, String fileName) 
	{
		arrSize = size;
		outFileName = fileName;
		Random r = new Random();
		arr = r.ints(1, arrSize).limit(size).toArray();
		
		if(order.equals("descending")){
			Arrays.sort(arr);
			this.reverse();
		}
		
		else if(order.equals("ascending")){
			Arrays.sort(arr);
		}
		
		else if(!(order.equals("random"))){
			throw new IllegalArgumentException
				("Arg 1 (order) must be 'ascending', 'descending', or 'random'.");
		}
	}
	
	public void reverse()
	{
		int temp;
		for(int i = 0; i < arrSize/2; i++){
			temp = arr[arrSize - 1 - i];
			arr[arrSize - 1 - i] = arr[i];
			arr[i] = temp;
		}
	}
	
	// Quicksort (quicksort method provided in lecture notes from January 28th).
	public void quickSort()
	{
		if(arrSize < 2)
			return;
		
		int max = 0;
		
		for(int i = 1; i < arrSize; i++){
			if(arr[i] > arr[max])
				max = i;
		}
		
		swap(arrSize - 1, max);
		
		quickSort(0, arrSize - 1);
	}
	
	public void quickSort(int first, int last)
	{
		int lower = first + 1;
		int upper = last;
		
		swap(first, (first + last)/2);
		
		int bound = arr[first];
		
		while(lower <= upper){
			while(arr[lower] < bound)
				lower++;
			
			while(arr[upper] > bound)
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
			quickSort(first, upper - 1);
		
		if((upper + 1) < last)
			quickSort(upper + 1, last);
	}
	
	public void swap(int first, int last)
	{
		int temp = arr[first];
		arr[first] = arr[last];
		arr[last] = temp;
	}
	
	// Selection sort (selectSort method provided in lecture notes from January 24th).
	public void selectSort()
	{
		for(int i = 0; i < arrSize - 1; i++){
			int min = i;
			for(int j = i + 1; j < arrSize; j++)
				if(arr[j] < arr[min])
					min = j;
				
			swap(min, i);
		}
	}
	
	// Merge sort (mergeSort method provided in lecture notes from January 26th).
	public void mergeSort(int first, int last)
	{
		// Recursively cut into sub arrays before merging.
		if(first < last){
			int mid = (first + last) / 2;
			mergeSort(first, mid);
			mergeSort(mid + 1, last);
			merge(first, mid, mid + 1, last);
		}
	}
	
	// merge method borrowed from geeksforgeeks.org
	public void merge(int left, int midLeft, int midRight, int right)
	{
		// Doing "midRight - left" instead of "midLeft - left + 1" to effectively
		// add the one. Same concept used to find rightLength.
		int leftLength = midRight - left;
		int rightLength = right - midLeft;
		
		int i, j, k;
		
		// Create temp arrays:
		int[] L = new int[leftLength];
		int[] R = new int[rightLength];
		
		for(i = 0; i < leftLength; i++)
			L[i] = arr[left + i];
		
		for(j = 0; j < rightLength; j++)
			R[j] = arr[midRight + j];
		
		// Merge arrays:
		i = 0;
		j = 0;
		
		// iterate over both arrays while sorting and copying to arr.
		k = left;
		while(i < leftLength && j < rightLength){
			if(L[i] < R[j]){
				arr[k] = L[i];
				i++;
			}
			else{
				arr[k] = R[j];
				j++;
			}
			k++;
		}
		
		// copy any remaining values in L and R into arr.  
		while(i < leftLength){
			arr[k] = L[i];
			i++;
			k++;
		}
		while(j < rightLength){
			arr[k] = R[j];
			j++;
			k++;
		}
	}
	
	// Insertion sort (insertSort method provided in lecture notes from January 26th).
	public void insertSort()
	{
		int i, j, temp;
		for(i = 1; i < arrSize; i++){
			temp = arr[i];
			for(j = i; j > 0 && temp < arr[j - 1]; j--)
				arr[j] = arr[j - 1];
			arr[j] = temp;
		}
	}
	
	// Creating and writing to txt file.
	public void createFile()
	{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
			for(int val : arr)
				writer.write(Integer.toString(val) + "\n");
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		// Saving args to local variables and converting to lowercase for later.
		String order = args[0].toLowerCase();
		int size = Integer.parseInt(args[1]);
		String algo = args[2].toLowerCase();
		String fileName = args[3].toLowerCase();
		
		// Comfirming that size is at least one 
		if(size < 1){
			throw new IllegalArgumentException("Arg 2 (size) must be a positive integer.");
		}
		
		// Creating new Assign1 object with properties dictated by command line args
		Assign1 arrObj = new Assign1(size, order, fileName);
		long startTime;
		long elapsedTime;
		
		// Selecting sorting algorithm based on command line args
		// then timing sort methods.
		if(algo.equals("selection")){
			startTime = System.nanoTime();
			arrObj.selectSort();
			elapsedTime = System.nanoTime() - startTime;
		}
		
		else if(algo.equals("insertion")){
			startTime = System.nanoTime();
			arrObj.insertSort();
			elapsedTime = System.nanoTime() - startTime;
		}
		
		else if(algo.equals("merge")){
			startTime = System.nanoTime();
			arrObj.mergeSort(0, arrObj.arrSize - 1);
			elapsedTime = System.nanoTime() - startTime;
		}
		
		else if(algo.equals("quick")){
			startTime = System.nanoTime();
			arrObj.quickSort();
			elapsedTime = System.nanoTime() - startTime;
		}
		
		// If algo does not match any of the options, exception thrown.
		else{
			throw new IllegalArgumentException
				("Arg 3 (algorithm) must be 'selection', 'insertion', 'merge', or 'quick'.");
		}
		
		// Print sorting time.
		System.out.print("Array Sorted.\nSort time in nanoseconds: ");
		System.out.print(Long.toString(elapsedTime)+"\n");
		
		// Save array to text file.
		arrObj.createFile();
	}
}