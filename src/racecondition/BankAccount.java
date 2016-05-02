package racecondition;

public class BankAccount {
	private String name;
	private long balance;

	public BankAccount(String name, long openingBalance) {
		this.name = name;
		this.balance = openingBalance;
	}
	
	public void deposit(int amount)
	{
		balance += amount;
	}

	public boolean withdraw(int amount) {
		if (balance >= amount) {
			balance -= amount;
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		final BankAccount account = new BankAccount("David", 50000);

		final BankAccount withdrawal1 = new BankAccount("Thread1",0);
		final BankAccount withdrawal2 = new BankAccount("Thread2",0);
		
		Thread thread1 = new Thread() {
			public void run() {
				int amount = 10;
				while (account.withdraw(amount))
					withdrawal1.deposit(amount);
				System.out.println("total taken from " + account.name
						+ " by 1 : " + withdrawal1.balance);
				System.out.println("total taken = "+(withdrawal1.balance+withdrawal2.balance));
			}
		};

		Thread thread2 = new Thread() {
			public void run() {
				int amount = 20;
				while (account.withdraw(amount))
					withdrawal2.deposit(amount);

				System.out.println("total taken from " + account.name
						+ " by 2 : " + withdrawal2.balance);
				System.out.println("total taken = "+(withdrawal1.balance+withdrawal2.balance));
			}
		};

		thread1.start();
		thread2.start();
	}
}
