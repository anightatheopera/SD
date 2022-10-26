public class Worker implements Runnable {

    Barrier b;

    public Worker(Barrier b) {
        this.b = b;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName().toUpperCase());
        }
        try {
            b.nAwait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Worker + " + Thread.currentThread().getName().toUpperCase() + " Released!");
    }
}