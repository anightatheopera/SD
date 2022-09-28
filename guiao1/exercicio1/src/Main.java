public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        int I = 100;
        Thread[] a = new Thread[n];
        for(int i = 0; i < n; i++) a[i] = new Thread(new Increment());
        for(int i = 0; i < n; i++) a[i].start();
        for(int i = 0; i < n; i++) a[i].join();

    }
}