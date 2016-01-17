class MyThread implements Runnable {

	private Thread t;

	MyThread() {
		t = new Thread(this, "Demo Thread");
		System.out.println("Child Thread: "+ t);
		t.start();			
	}

	MyThread(String name) {
		t = new Thread(this, name);
		System.out.println("Child Thread: "+ t);
		t.start();
	}

	public Thread getThread() { return t;}

	public void run() {
		
		try {
			for(int i=10;i>=0;i--){
				System.out.println("Child "+ t.getName() + "count down "+ i);
				Thread.sleep(1000);
			}
		} catch(Exception e) {
			System.out.println("child thread interrupted and Exception raised: "+ e);	
		}
	}
}

public class ThreadRunnableDemo {

	public static void main(String a[]) {
		MyThread th1 = new MyThread("harsh_Thread1");
		MyThread th2 = new MyThread("harsh_Thread2");
		MyThread th3 = new MyThread("harsh_Thread3");

		System.out.println("Child thread "+ th1.getThread().getName() + "is alive: "+ th1.getThread().isAlive());
		System.out.println("Child thread "+ th2.getThread().getName() + "is alive: "+ th2.getThread().isAlive());
		System.out.println("Child thread "+ th3.getThread().getName() + "is alive: "+ th3.getThread().isAlive());

		try{
			th1.getThread().join();
			th2.getThread().join();
			th3.getThread().join();
		} catch(Exception e) {
			System.out.println("Main thread interrupted during joining child threads: "+ e);	
		}

		try{		
			for(int i=10;i>=0;i--){
				System.out.println("Main Count down: "+i);
				Thread.sleep(1000);
			}
		} catch(Exception e) {
			System.out.println("Main thread interrupted and Exception raised: "+ e);	
		}

		System.out.println("Child thread "+ th1.getThread().getName() + "is alive: "+ th1.getThread().isAlive());
		System.out.println("Child thread "+ th2.getThread().getName() + "is alive: "+ th2.getThread().isAlive());
		System.out.println("Child thread "+ th3.getThread().getName() + "is alive: "+ th3.getThread().isAlive());
		System.out.println("Main thread finished execution. ");
	}
} 


