import java.math.BigInteger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Review2 {
    public static BigInteger LucasAt(int n) {
        if (n == 0) {
            return BigInteger.valueOf(2);
        } else if (n == 1) {
            return BigInteger.ONE;
        }
        return LucasAt(n - 1).add(LucasAt(n - 2));
    }

    static class LucasThread extends Thread {
        private BigInteger a;
        private BigInteger b;
        public BigInteger result;

        public LucasThread(BigInteger a, BigInteger b) {
            super();
            this.a = a;
            this.b = b;
        }

        public void Clear() {
            a = null;
            b = null;
            result = null;
        }

        @Override
        public void run() {
            result = a.add(b);
        }
    }

    public static BigInteger LucasMultithread(int n) {
        if (n == 0) {
            return BigInteger.valueOf(2);
        } else if (n == 1) {
            return BigInteger.ONE;
        }
        LucasThread thread = new LucasThread(LucasMultithread(n - 1), LucasMultithread(n - 2));
        thread.start();

        try {
            do {
                Thread.sleep(0);
            } while (thread.result == null);
        } catch (InterruptedException e) {}
        BigInteger res = thread.result;
        thread.Clear();

        return res;
    }

    public static BigInteger DebugLucasAt(int n) {
        if (n == 1) {
            return BigInteger.valueOf(2);
        } else if (n == 2) {
            return BigInteger.ONE;
        }
        Assignment.out.println("Input -> n=" + n + "\r");
        BigInteger a = DebugLucasAt(n - 1);
        Assignment.out.println("\t Output -> n-1=" + a);
        BigInteger b = DebugLucasAt(n - 2);
        Assignment.out.println("\t Output -> n-2=" + b);
        Assignment.out.println();
        return a.add(b);
    }

    public static BigInteger LucasIterative(int n) {
        BigInteger first = BigInteger.valueOf(2);
        BigInteger second = BigInteger.ONE;
        BigInteger third;
  
        if (n <= 0) return first;
        
        for (int i = 2; i <= n; i++)  
        { 
            third = first.add(second);
            first = second;
            second = third;
        }

        return second;
    }

    public static void main(String[] args) {
        Assignment.announceAssignment("Did He Just Steal An Idea?!", 2);

        ArrayList<String> solutions = new ArrayList<String>();

        try {
            Assignment.announceInput();
            DSUtil.readFileContents(new File("Input Files/Prob2Input.txt"), e -> {
                Assignment.printForInput("\t" + e);
                solutions.add(
                    LucasIterative(
                        Integer.parseInt(e)
                    ).toString()
                );
            });
            Assignment.announceOutput();
            solutions.forEach(Assignment::printForOutput);
            Assignment.out.println("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}