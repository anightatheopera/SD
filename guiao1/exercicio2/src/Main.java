public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        int v = 100;
        Bank b = new Bank();
        Thread[] a = new Thread[n];
        for(int i = 0; i < n; i++) a[i] = new Thread(new Deposito(v,b));
        for(int i = 0; i < n; i++) a[i].start();
        for(int i = 0; i < n; i++) a[i].join();
        System.out.println(b.balance());
    }
}