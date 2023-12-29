public class Queue<T>
{
	private int length;
	private Node<T> head;
	
	public Queue()
	{
		this.length = 0;
		this.head = null;
	}
	
	public void enqueue(T data)
	{
		if(head == null){
			head = new Node<T>(data, null);
			length++;
			return;
		}
		
		Node current = head;
		while(current.next != null){
			current = current.next;
		}
		
		current.next = new Node<T>(data, null);
		
		length++;
	}
	
	public T dequeue()
	{
		if(isEmpty()){
			return null;
		}
		
		T data = head.data;
		
		head = head.next;
		
		length--;
		
		return data;
	}
	
	public boolean isEmpty()
	{
		if(length == 0) return true;
		
		return false;
	}
}

class Node<T>
{
	public T data = null;
	
	public Node next = null;
	
	public Node(T data, Node next)
	{
		this.data = data;
		this.next = next;
	}
}