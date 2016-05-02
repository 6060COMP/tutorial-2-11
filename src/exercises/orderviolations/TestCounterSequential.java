package exercises.orderviolations;

/**
 * This class is a simple demonstration used to demonstrate sequential and
 * threaded execution. Each object of this class is constructed with an integer.
 * The go method of each object then prints out five positions in "a"
 * timestable, with each step incrementing by the initial value. As such, if an
 * object was created using 7, then the output would be as follows: <BR>
 * <BR>
 * (7:1) 7 * 7 = 49 <BR>
 * (7:2) 14 * 7 = 98 <BR>
 * (7:3) 21 * 7 = 147 <BR>
 * (7:4) 28 * 7 = 196 <BR>
 * (7:5) 35 * 7 = 245 <BR>
 * <BR>
 * 
 * Three variants of this class exist: <BR>
 * <BR>
 * -the sequential variant (THIS) - creates three simple times-table printers
 * for 2, 5 and 10 respectively. It then executes each printer sequentially.
 * This produces output for 2, then 5, then 10.<BR>
 * <BR>
 * -the threaded variant - creates three simple times-table printers for 2, 5
 * and 10 respectively. It then executes each printer concurrently - resulting
 * in a predictably unpredictable output order.<BR>
 * <BR>
 * -the synchronised variant (EXERCISE TASK!)- - creates three simple
 * times-table printers for 2, 5 and 10 respectively. It then executes each
 * printer concurrently with barrier synchronisation - resulting in position 1
 * for 2,5,10, then position 2 for 2,5,10, and so on and so forth.<BR>
 * <BR>
 * 
 * @author david
 */
public class TestCounterSequential {

  // This is the main method; this runs first
  public static void main(String[] args) {
    TestCounterSequential two = new TestCounterSequential(2);
    TestCounterSequential fiver = new TestCounterSequential(5);
    TestCounterSequential tenner = new TestCounterSequential(10);

    two.go(); // produce the output for 2
    fiver.go(); // produce the output for 5
    tenner.go(); // produce the output for 10
  }

  private int increment = 1;
  private static int totalCounts = 0;

  public TestCounterSequential(int increment) {
    this.increment = increment;
  }

  // This method produces the output for the class
  // It executes a basic for loop 5 times, doing some calculations each time,
  // and printing the output
  public void go() {
    {
      int multiplier = increment;
      for (int i = 0; i < 5; i++) {
        System.out
            .println("(" + increment + ":" + (i + 1) + ") - " + multiplier
                + " * " + increment + " = " + (multiplier * increment));
        multiplier += increment;
        totalCounts = totalCounts + 1;
      }
    }
    System.out.println("---" + totalCounts);

  }

}
