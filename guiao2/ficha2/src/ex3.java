import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {

    private static final Lock lock = new ReentrantLock();
    private static class Account {
        Lock a_lock = new ReentrantLock();
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() {
            //a_lock.lock();
            //try {
            return balance;
            //} finally {
            //  a_lock.unlock();
            //}
        }
        boolean deposit(int value) {
            //a_lock.lock();
            //try {
            balance += value;
            return true;
            //} finally {
            //  a_lock.unlock();
            //}
        }
        boolean withdraw(int value) {
            if (value > balance)
                return false;
            //a_lock.lock();
            //try {
            balance -= value;
            return true;
            //} finally {
            //  a_lock.unlock();
            //}
        }
    }

    // Bank slots and vector of accounts
    private int slots;
    private Account[] av;

    public Bank(int n)
    {
        slots=n;
        av=new Account[slots];
        for (int i=0; i<slots; i++) av[i]=new Account(0);
    }

    // Account balance
    public int balance(int id) {
        if (id < 0 || id >= slots)
            return 0;
        //lock.lock();
        //try {
        av[id].a_lock.lock();
        try {
            return av[id].balance;
        } finally {
            av[id].a_lock.unlock();
        }
        //} finally {
        //  lock.unlock();
        //}
    }

    // Deposit
    boolean deposit(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        //lock.lock();
        //try {
        Account ac = av[id];
        ac.a_lock.lock();
        try {
            return ac.deposit(value);
        } finally {
            ac.a_lock.unlock();
        }
        //} finally {
        //  lock.unlock();
        //}
    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        //lock.lock();
        //try {
        Account ac = av[id];
        ac.a_lock.lock();
        try {
            return ac.withdraw(value);
        } finally {
            ac.a_lock.unlock();
        }
        //} finally {
        //  lock.unlock();
        //}
    }

    public boolean transfer(int from, int to, int value) {
        if (from < 0 || from >= slots || to < 0 || to >= slots) {
            return false;
        }
        //lock.lock();
        //try {
        if (from < to) {
            av[from].a_lock.lock();
            av[to].a_lock.lock();
        } else {
            av[to].a_lock.lock();
            av[from].a_lock.lock();
        }
        try {
            boolean b = av[from].withdraw(value);
            if (!b) return false;
            return deposit(to, value);
        } finally {
            av[from].a_lock.unlock();
            av[to].a_lock.unlock();
        }
        //} finally {
        //  lock.unlock();
        //}
    }
    public int totalBalance() {
        //lock.lock();
        //try {
        int sum = 0;
        for (int i = 0; i < slots; i++)
            av[i].a_lock.lock();
        for (int i = 0; i < slots; i++)
            sum += av[i].balance();
        for (int i = 0; i < slots; i++)
            av[i].a_lock.unlock();
        return sum;
        //} finally {
        //  lock.unlock();
        //}
    }

}