package exercises.needsatomic;

/**
 * ThreadedVenueCounter - a simple class for keeping track of all the people in
 * a venue
 * 
 * @author Dr. David Lamb
 */
public class ThreadedVenueCounter {

	public static void main(String[] args) {
		final ThreadedVenueCounter venue = new ThreadedVenueCounter();

		// 10 entry gates are opened, and 100 people enter via each gate
		Thread[] threads = new Thread[10];
		for (int cnt = 0; cnt < threads.length; cnt++) {
			threads[cnt] = new Thread() {
				public void run() {
					for (int i = 0; i < 1000; i++)
						venue.admit();
				}
			};
		}

		// Fire drill - everyone out!!
		// 10 exit gates are opened, and 100 people leave through each gate
		Thread[] leavingThreads = new Thread[10];
		for (int cnt = 0; cnt < leavingThreads.length; cnt++) {
			leavingThreads[cnt] = new Thread() {
				public void run() {
					for (int i = 0; i < 1000; i++)
						venue.exit();
				}
			};
		}

		// start the entry gates code
		for (Thread thread : threads)
			thread.start();

		// wait for 10 secs
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}

		// start the exit gates code
		for (Thread thread : leavingThreads)
			thread.start();

		// wait for 2 more secs, then report how many people the system still
		// thinks are in the venue
		Thread reporter = new Thread() {
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {// ignore

				}
				System.out
						.println("Total remaining after everyone's gone home = "
								+ venue.getTotal());
			}
		};
		reporter.start();

	}

	/**
	 * The current number of people in the venue
	 */
	private int total;

	/**
	 * Admits a person to the venue, incrementing the total
	 */
	public void admit() {
		total = total + 1;
	}

	/**
	 * Called when someone leaves the venue, decrementing the total
	 */
	public void exit() {
		total--;
	}

	/**
	 * Returns the current number of people in the venue at any given instant
	 * 
	 * @return the number of people
	 */
	public int getTotal() {
		return total;
	}

}
