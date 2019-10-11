import java.io.IOException;
import java.util.*;

public class Review3
{
    
    public static int[] getFactors(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=1;i<n;i++)
        {
            if (n % i == 0)
            {
                list.add(i);
            }
        }
        return DSUtil.ListIntegerToArrayPrimitive(list);
    }

    public static boolean IsAmicable(int a, int b)
    {

        DSUtil.ReductionOperation<Integer> Sum = (x, y) -> {
            return new Integer(x.intValue() + y.intValue());
        };

        List<Integer> aFactors = DSUtil.ArrayPrimitiveToListInteger(getFactors(a));
        aFactors.removeIf(e -> {
            return e == a;
        });

        List<Integer> bFactors = DSUtil.ArrayPrimitiveToListInteger(getFactors(b));
        bFactors.removeIf(e -> {
            return e == b;
        });

        int sumA = new DSUtil.BetterList<Integer>(aFactors).reduce(Sum).intValue();
        int sumB = new DSUtil.BetterList<Integer>(bFactors).reduce(Sum).intValue();

        return sumA == b && sumB == a;
    }

    public static String getAmicableInfo(int a, int b)
    {
        String out = String.format("The numbers %d and %d are%samicable.\r\n", a, b, IsAmicable(a, b) ? " " : " not ");
        int[] fa = getFactors(a);
        int[] fb = getFactors(b);

        out += "\tFactors of " + a + " are: ";
        for (int ia=0;ia<fa.length;ia++)
        {
            if (ia == fa.length - 1)
                out += "and ";
            out += fa[ia];
            if (ia < fa.length - 1)
                out += ", ";
            else out += ".\r\n";
        }

        out += "\tFactors of " + b + " are: ";
        for (int ib=0;ib<fb.length;ib++)
        {
            if (ib == fb.length - 1)
                out += "and ";
            out += fb[ib];
            if (ib < fb.length - 1)
                out += ", ";
            else out += ".\r\n";
        }

        return out;
    }

    public static void main(String[] args)
    {
        Assignment.announceAssignment("Can't My Numbers Just Get Along", 3);
        Assignment.announceInput();

        ArrayList<String> output = new ArrayList<>();

        try {
            DSUtil.readFileContents("Input Files/Prob3Input.txt", e -> {
                String[] _words = e.split(" ");
                int a = Integer.parseInt(_words[0]);
                int b = Integer.parseInt(_words[1]);
                Assignment.printForInput("\t" + e);
                output.add(getAmicableInfo(a, b));
            });
        } catch (IOException e) {}

        Assignment.announceOutput();

        for (String s: output)
        {
            Assignment.printForOutput(s.replace("\r\n", "\r\n\t"));
        }

        Assignment.out.println();
    }
}