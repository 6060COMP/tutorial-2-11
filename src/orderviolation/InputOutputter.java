package orderviolation;

import java.util.Random;

/**
 * Demonstrates simple, albeit contrived, Order Violation; an abstract of that
 * identified as a problem in Mozilla by Lu's 2008 "Learning from Mistakes"
 * paper. <BR>
 * If everything goes well, the system will output "I'm ready to output" and
 * quit. <BR>
 * If not, the system will apparently hang with "I've read something in" <BR>
 * Study the assumed order between the lines labelled In1 and Out1 <BR>
 * {@link #input()} and {@link #output()} each have a small random delay
 * inserted to show how timing affects this bug. <BR>
 * <BR>
 * You're likely going to need to execute the program several times to see the
 * correct vs. incorrect functionality. Depending on the execution environment,
 * you may have difficulty either a) creating the bug or b) getting the expected
 * behaviour; try reducing or increasing the maximum timing delay for either
 * method if so. All being well, you should, over about 5 - 10 executions, see
 * both correct and incorrect behaviour.
 * 
 * @author david
 *
 */
public class InputOutputter {

  public static void main(String[] args) {
    InputOutputter io = new InputOutputter();

    io.output();
  }

  // private Semaphore semaphore = new Semaphore(1);

  private boolean bufferEmpty = true;

  public void startInputThread() {
    new Thread() {
      @Override
      public void run() {
        input();
      }
    }.start();
  }

  public void output() {
    System.out.println("Waiting for buffer..");
    startInputThread(); // start input method in new thread
    System.out.println("Still Waiting for buffer..");
    try {
      Random rnd = new Random(System.currentTimeMillis());
      Thread.sleep(rnd.nextInt(5));
    } catch (InterruptedException e) {
    }

    bufferEmpty = true; // Out1: assumed to happen first

    while (bufferEmpty) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
      }
      // wait for buffer data
    }

    System.out.println("I'm ready to output!");

  }

  public void input() {
    // read in some data
    Random rnd = new Random(System.currentTimeMillis());
    System.out.println("**I'm reading in data");
    try {
      Thread.sleep(rnd.nextInt(5));
    } catch (InterruptedException e) {
    }
    bufferEmpty = false; // In1: assumed to happen after Out1
    System.out.println("**I've read something in!");
  }
}
