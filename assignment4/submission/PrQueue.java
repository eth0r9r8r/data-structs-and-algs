public class PrQueue
{
	private Vector<Element> queue = null;
	
	public PrQueue()
	{
		queue = new Vector<Element>();
	}
	
	public int size()
	{
		return queue.length();
	}

	private int getParent(int index)
	{
		return (index - 1) / 2;
	}
	
	private int getLChild(int index)
	{
		return 2 * index + 1;
	}
	
	private int getRChild(int index)
	{
		return 2 * index + 2;
	}
	
	private void swap(int first, int second)
	{
		Element temp = queue.getAt(first);
		
		queue.setAt(first, queue.getAt(second));
		
		queue.setAt(second, temp);
	}
	
	private void bubble(int childIndex)
	{
		if(childIndex == 0) return;
		
		int parentIndex = getParent(childIndex);
		Element parentData = queue.getAt(parentIndex);
		Element childData = queue.getAt(childIndex);
		
		if(childData.cost < parentData.cost){
			swap(parentIndex, childIndex);
			bubble(parentIndex);
		}
	}
	
	private void sink(int parentIndex)
	{
		int leftChildIndex = getLChild(parentIndex);
		int rightChildIndex = getRChild(parentIndex);
		
		if(leftChildIndex >= queue.length()) return;
		if(rightChildIndex >= queue.length()) return;
		
		Element leftChildData = queue.getAt(leftChildIndex);
		Element rightChildData = queue.getAt(rightChildIndex);
		Element parentData = queue.getAt(parentIndex);
		
		if(leftChildData.cost > rightChildData.cost){
			if(rightChildData.cost < parentData.cost){
				swap(rightChildIndex, parentIndex);
				sink(rightChildIndex);
			}
		}
		
		else if(leftChildData.cost < parentData.cost){
			swap(leftChildIndex, parentIndex);
			sink(leftChildIndex);
		}
	}
	
	public void insert(Element data)
	{
		int newIndex = queue.length();
		
		queue.append(data);
		
		bubble(newIndex);
	}
	
	public Element poll() throws IndexOutOfBoundsException
	{
		if(queue.length() <= 0){
			throw new IndexOutOfBoundsException("Attempted removing from empty queue");
		}
		
		Element topPriority = queue.getAt(0);
		
		queue.setAt(0, queue.getAt(queue.length() - 1));
		queue.trim();
		
		sink(0);
		
		return topPriority;
	}
}