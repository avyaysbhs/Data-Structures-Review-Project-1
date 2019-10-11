import java.io.IOException;

public class Review4
{
    static class Code
    {
        public DSUtil.BetterList<String> code = new DSUtil.BetterList<>();
        public boolean Done = false;

        public void SetCode(String s)
        {
            code = new DSUtil.BetterList<String>(s.split(""));
        }

        public boolean CodeAlreadySet()
        {
            return code.size() == 4;
        }

        public String GetCode()
        {
            return code.reduce((a, b) -> {
                return a + b;
            });
        }

        public String Match(String g)
        {
            int cccp = 0, ccip = 0;
            DSUtil.BetterList<String> code = this.code.cloneList();
            DSUtil.BetterList<String> guess = new DSUtil.BetterList<>(g.split(""));

            while (code.size() > 0)
            {
                for (int i=0;i<code.size();i++)
                {
                    if (guess.contains(code.get(i)))
                    {
                        if (guess.get(i).equals(code.get(i)))
                        {
                            cccp++;

                            guess.remove(i);
                            code.remove(i);

                            break;
                        } else
                        {
                            ccip++;

                            guess.remove(
                                guess.indexOf(
                                    code.remove(i)
                                )
                            );
                            
                            break;
                        }
                    } else
                    {
                        code.remove(i);
                        break;
                    }
                }
            }

            Done = cccp == 4;
            
            String out = 
                "Code: " + GetCode() + "\r\n"
              + "Guess: " + g + "\r\n"
              + "Color Correct - Correctly Placed: " + cccp + "\r\n"
              + "Color Correct - Incorrectly Placed: " + ccip + "\r\n";

            return out;
        }
    }
    public static void main(String[] args)
    {
        Assignment.announceAssignment("Code Breaker", 4);
        Assignment.announceInput();

        Code og = new Code();

        DSUtil.BetterList<String> output = new DSUtil.BetterList<>();
        
        try {
            DSUtil.readFileContents("Input Files/Prob4Input.txt", e -> {
                Assignment.printForInput("\t" + e);
                if (og.Done)
                    return;
                if (!og.CodeAlreadySet())
                {
                    og.SetCode(e);
                } else
                {
                    output.add(og.Match(e));
                }
            });
        } catch (IOException e) {}

        Assignment.announceOutput();
        
        for (String s: output)
        {
            Assignment.printForOutput(s);
        }

        Assignment.out.println();
    }
}