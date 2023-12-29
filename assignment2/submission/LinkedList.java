public class LinkedList
{
	private Node head;
	private int length;
	
	public LinkedList(String headData)
	{
		this.head = new Node(headData);
		this.length = 1;
	}
	
	public LinkedList()
	{
		this.head = null;
		this.length = 0;
	}
	
	public void addToFront(String newData)
	{
		Node newNode = new Node(newData, head);
		head = newNode;
		length++;
	}
	
	public void addToEnd(String newData)
	{
		Node oldLast = head;
		
		while(oldLast.getNext() != null)
			oldLast = oldLast.getNext();
		
		oldLast.setNext(new Node(newData, null));
		length++;
	}
	
	public void insert(String newData, int index)
	{
		if(index < 0) return;
		
		if(index >= length){
			addToEnd(newData);
			return;
		}
		
		if(index == 0){
			addToFront(newData);
			return;
		}
		
		Node previous = head;
		
		for(int i = 1; i < index; i++)
			previous = previous.getNext();
		
		Node newNode = new Node(newData, previous.getNext());
		previous.setNext(newNode);
		length++;
	}
	
	public void remove(Node toRemove)
	{
		Node previous = head;
		
		while(previous.getNext() != toRemove && previous.getNext() != null)
			previous = previous.getNext();
		
		previous.setNext(toRemove.getNext());
		toRemove.setNext(null);
		
		length--;
	}
	
	public void linkedSort()
	{
		if(length < 2) return;
		
		Node compare;
		Node next;
		Node current = head.getNext();
		int i;
		
		while(current != null){
			i = 0;
			compare = head;
			while(current.getData().compareTo(compare.getData()) > 0 && compare != current){
				compare = compare.getNext();
				i++;
			}
			if(compare != current){
				insert(current.getData(), i);
				next = current.getNext();
				remove(current);
				current = next;
			}
			else
				current = current.getNext();
		}
	}
	
	public Node getHead(){ return this.head; }
}

class Node
{
	private String data;
	private Node next;
	
	public Node(String newData)
	{
		this.data = newData;
		this.next = null;
	}
	
	public Node(String newData, Node newNext)
	{
		this.data = newData;
		this.next = newNext;
	}
	
	public String getData(){ return this.data; }
	public Node getNext(){ return this.next; }
	
	public void setData(String newData){ this.data = newData; }
	public void setNext(Node newNext){ this.next = newNext; }
}