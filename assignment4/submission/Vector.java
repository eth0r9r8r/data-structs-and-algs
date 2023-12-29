public class Vector<T>
{
	private T[] arr;
	private int length;
	private int capacity;
	
	public Vector(int length)
	{
		this.length = length;
		this.capacity = length * 2;
		this.arr = (T[])new Object[length * 2];
	}
	
	public Vector()
	{
		this.length = 0;
		this.capacity = 1;
		this.arr = (T[])new Object[1];
	}
	
	public void append(T data)
	{
		if(length == capacity) resize();
		
		arr[length] = data;
		
		length++;
	}
	
	public void trim()
	{
		if(length <= 0){
			throw new IndexOutOfBoundsException("Tried to trim empty Vector");
		}
		
		length--;
	}
	
	public T getAt(int index) throws IndexOutOfBoundsException
	{
		if(index >= length || index < 0){
			throw new IndexOutOfBoundsException("Invalid index");
		}
		
		return arr[index];
	}
	
	public void setAt(int index, T data) throws IndexOutOfBoundsException
	{
		if(index >= length || index < 0){
			throw new IndexOutOfBoundsException("Invalid index");
		}
		
		arr[index] = data;
	}
	
	public int length()
	{
		return length;
	}
	
	private void resize()
	{
		capacity = capacity + 10;
		T[] tempArr = (T[])new Object[capacity];
		
		for(int i = 0; i < length; i++){
			tempArr[i] = arr[i];
		}
		
		arr = tempArr;
	}
}