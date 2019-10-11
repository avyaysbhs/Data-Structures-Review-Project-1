import java.io.IOException;
import java.util.HashMap;

public class Review7 {

    public static double round(float in, int places)
    {
        double n = (double) in;
        return Math.floor(n * Math.pow(10, places))/Math.pow(10, places);
    }
    public static void main(String[] args) {
        Assignment.announceAssignment("Making Money", 7);
        Assignment.announceInput();

        class FeeComparer
        {
            public float cr1;
            public float cr2;
            public float dollars;
            public float fee1;
            public float fee2;

            public boolean started = false;
            
            public void output()
            {
                float b1 = (dollars - fee1) * cr1;
                float b2 = (dollars - fee2) * cr2;

                Assignment.printForInput(
                    "Dollars: " + (int) round(dollars, 0) + "\n"
                    + "CR1: " + cr1 + "\n"
                    + "Fee: $" + (int) round(fee1, 0) + "\n"
                    + "CR2: " + cr2 + "\n"
                    + "Fee: $" + (int) round(fee2, 0) + "\n"
                );

                Assignment.printForOutput("Booth 1: " + round(b1, 2) + " euros");
                Assignment.printForOutput("Booth 2: " + round(b2, 2) + " euros");
                
                float difference = b2 - b1;
                String out = "Booth %d is the best; difference is %f\n";
                Assignment.printForOutput(String.format(out, 
                    b2 > b1 ? 2 : 1,
                    round(difference > 0 ? difference : -difference, 2)
                ));
            }

            public void clear()
            {
                cr1 = 0;
                cr2 = 0;
                fee1 = 0;
                fee2 = 0;
                dollars = 0;
            }

            public void setDollars(String[] dollars)
            {
                if (dollars.length < 1) return;
                if (started)
                    output();
                    clear();
                started = true;
                this.dollars = Float.parseFloat(dollars[0]);
            }

            public void setCR1(String[] cr1)
            {
                this.cr1 = Float.parseFloat(cr1[0]);
            }

            public void setCR2(String[] cr2)
            {
                this.cr2 = Float.parseFloat(cr2[0]);
            }

            public void setFee1(String[] fee1)
            {
                this.fee1 = Float.parseFloat(fee1[0]);
            }

            public void setFee2(String[] fee2)
            {
                this.fee2 = Float.parseFloat(fee2[0]);
            }
        }

        HashMap<String, DSUtil.ArgCommand<String>> commands = new HashMap<>();
        FeeComparer thing = new FeeComparer();

        commands.put("Dollars", thing::setDollars);
        commands.put("CR1", thing::setCR1);
        commands.put("CR2", thing::setCR2);
        commands.put("Fee1", thing::setFee1);
        commands.put("Fee2", thing::setFee2);

        class AtomicString
        {
            private String val;

            public AtomicString(String in) {set(in);}
            public AtomicString() {set("");}

            public void set(String value)
            {
                val = value;
            }

            public String get() { return val; }
        }

        AtomicString command = new AtomicString();
        
        try {
            DSUtil.readFileContents("Input Files/Prob7Input.txt", line -> {
                if (!line.contains(":")) return;

                int colon = line.indexOf(":");
                String field = line.substring(0, colon);

                if (field.equals("Fee"))
                {
                    if (command.get().equals("CR1"))
                        field = "Fee1";
                    else if (command.get().equals("CR2"))
                        field = "Fee2";
                }

                String data = line.substring(colon + 2);

                command.set(field);
                DSUtil.ArgCommand<String> cmd = commands.get(command.get());
                if (cmd != null)
                    cmd.call(data);
            });
            thing.output();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}