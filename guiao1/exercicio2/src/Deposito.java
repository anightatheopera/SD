public class Deposito implements Runnable{
    final int i;
    final Bank b;
    public Deposito(int n, Bank b){
        this.b = b;
        this.i = n;
    }

    public void run(){
        for(int a = 0; a < i; a++) b.deposit(100);
    }
}
