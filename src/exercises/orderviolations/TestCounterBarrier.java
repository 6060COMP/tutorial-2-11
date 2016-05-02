package exercises.orderviolations;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestCounterBarrier extends Thread {

  public static void main(String[] args) {
	CyclicBarrier barrier = new CyclicBarrier(3);
	
	TestCounterBarrier two = new TestCounterBarrier(2, barrier);
	TestCounterBarrier fiver = new TestCounterBarrier(5, barrier);
	TestCounterBarrier tenner = new TestCounterBarrier(10, barrier);

    two.start(); // produce the output for 2
    fiver.start(); // produce the output for 5
    tenner.start(); // produce the output for 10
  }

  private int increment;
  private CyclicBarrier barrier;
  private static int totalCounts = 0;

  public TestCounterBarrier(int increment, CyclicBarrier barrier) {
    this.increment = increment;
    this.barrier = barrier;
  }

  @Override
  public void run() {
    go();
  }
  
  public static int getTotalCounts() {
	  return totalCounts;
  }

  // This method produces the output for the class
  // It executes a basic for loop 5 times, doing some calculations each time,
  // and printing the output
  public void go() {
    {
      int multiplier = increment;
      for (int i = 0; i < 5; i++) {
    	// Why the hell does this println statement make the following two statements run without pre-emption?
    	// I'm guessing 'println' is synchronized which causes the other two threads to block.
    	// And when the println finishes, there is always enough time to increment totalCounts before either are ready.
        System.out
            .println("(" + increment + ":" + (i + 1) + ") - " + multiplier
                + " * " + increment + " = " + (multiplier * increment));
        multiplier += increment;
        totalCounts = totalCounts + 1;
        
        try {
        	// TODO: Handle interrupt/brokenbarrier
			barrier.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
    }
  }

}
