import java.util.Arrays;

/**
  *@author Thando Tsela
  */

public class FlashcardPriorityQueue implements PriorityQueue<Flashcard> {
    private Flashcard[] heap; // Circular array of queue entries and one unused location
    private int lastIndex;     // Index of front entry
    //private int backIndex;      // Index of back entry
    private static final int DEFAULT_CAPACITY = 5; // Small capacity for testing

    /**
    * Creates an empty priority queue with the default capacity.
    */
    public FlashcardPriorityQueue() {
        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        Flashcard[] tempHeap = (Flashcard[])new Flashcard[DEFAULT_CAPACITY];
        heap = tempHeap;
        lastIndex = 0;
        heap[0] = null;
    } // end constructor

    /** Adds the given item to the queue. */
    public void add(Flashcard item) {
        int newIndex = lastIndex + 1;
        heap[newIndex] = item;
        lastIndex++;
        ensureCapacity();
        reheap(1);
    }

    /** Removes the first item according to compareTo from the queue, and returns it.
     * Throws a NoSuchElementException if the queue is empty.
     */
    public Flashcard poll() {
        Flashcard root = null;

        if (!isEmpty())
        {
           reheap(1);
           root = heap[1];            // Return value
           heap[1] = heap[lastIndex]; // Form a semiheap
           lastIndex--;               // Decrease size
        } // end if

        return root;
     } // end removeMax

    /** Returns the first item according to compareTo in the queue, without removing it.
     * Throws a NoSuchElementException if the queue is empty.
     */
    public Flashcard peek() {
        Flashcard root = null;

		if (!isEmpty())
            reheap(1);
			root = heap[1];

		return root;
	} // end peek

    /** Returns true if the queue is empty. */
    public boolean isEmpty() {
        return lastIndex < 1;
    } // end isEmpty

    /** Removes all items from the queue. */
    public void clear() {
        while (lastIndex > -1)
		{
			heap[lastIndex] = null;
			lastIndex--;
		} // end while

		lastIndex = 0;
	} // end clear

    private void reheap(int rootIndex)
    {
       boolean done = false;
       Flashcard orphan = heap[rootIndex];
       int leftChildIndex = (3 * rootIndex) - 1;

       while (!done && (leftChildIndex <= lastIndex) )
       {
          int smallerChildIndex = leftChildIndex; // Assume smaller
          int middleChildIndex = leftChildIndex + 1;
          int rightChildIndex = leftChildIndex + 2;

          if ( (middleChildIndex <= lastIndex) &&
              heap[middleChildIndex].compareTo(heap[smallerChildIndex]) < 0)
          {
             smallerChildIndex = middleChildIndex;
          } // end if

          if ( (rightChildIndex <= lastIndex) &&
              heap[rightChildIndex].compareTo(heap[smallerChildIndex]) < 0)
          {
             smallerChildIndex = rightChildIndex;
          } // end if

          if (orphan.compareTo(heap[smallerChildIndex]) > 0)
          {
             heap[rootIndex] = heap[smallerChildIndex];
             rootIndex = smallerChildIndex;
             leftChildIndex = (3 * rootIndex) - 1;
          }
          else
             done = true;
       } // end while

       heap[rootIndex] = orphan;
    } // end reheap

    // Triples the capacity of the array heap if it is full.
	private void ensureCapacity() {
      int numberOfEntries = lastIndex;
      int capacity = heap.length - 1;
      if (numberOfEntries >= capacity)
      {
         int newCapacity = 3 * capacity;
         heap = Arrays.copyOf(heap, newCapacity + 1);
      } // end if
    } // end ensureCapacity

    public static void main(String[] args) {
        FlashcardPriorityQueue test = new FlashcardPriorityQueue();
        Flashcard card1 = new Flashcard("2016-11-09T04:03", "Welcome to our city!!!", "Ciao ciao...");
        Flashcard card2 = new Flashcard("2021-03-02T14:15", "Sawubona.", "Hamba Kahle.");
        Flashcard card3 = new Flashcard("2015-11-04T05:25", "Buon giorno", "Ci vediamo.");
        Flashcard card4 = new Flashcard("2016-10-09T04:03", "It's a beautiful day.", "Goodnight.");
        Flashcard card5 = new Flashcard("2012-11-04T05:25", "What's good!", "Peace.");

        //FlashcardPriorityQueue test = new FlashcardPriorityQueue(card1, card3, card2, card4, card5);

        test.add(card1);
        test.add(card2);
        System.out.println(test.peek()); //should print card1
        test.add(card3);
        test.add(card4);

        System.out.println(test.peek()); //should print card3

        test.poll();
        System.out.println(test.peek()); //should print card4

        test.add(card5);

        System.out.println(test.peek()); //should print card5


        test.clear();
        if (test.isEmpty()) {
            System.out.println("'test' has been cleared.");
        }
        else {
            System.out.println("'clear' method does not work.");
        }


    }

} // end FlashcardPriorityQueue
