import java.io.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Review8
{
    private static String formatter = "Departure Date and Time: %tI:%tM %Tp on %tm/%td/%ty\r\n"
        + "Arrival Date and Time: %tI:%tM %Tp on %tm/%td/%ty\r\n";
    public static void main(String[] args)
    {
        Assignment.announceAssignment("When will you get there?", 8);
        Assignment.printForInput("Input sample:\r\n");
        AtomicInteger tripNumber = new AtomicInteger(1);

        try
        {
            DSUtil.readFileContents(new File("Input Files/Prob8Input.txt"), line -> 
            {
                Assignment.printForInput(line);
                String[] w = line.split(" ");
                long days = Integer.parseInt(w[0]), hours = Integer.parseInt(w[1]), minutes = Integer.parseInt(w[2]);           
                
                Date a = new Date();
                long milliseconds_passed = (days * 86400000) + (hours * 3600000) + (minutes * 60000);
                
                Date b = new Date(a.getTime() + milliseconds_passed);
                Assignment.printForOutput(String.format("Trip %d", tripNumber.getAndIncrement()));
                Assignment.printForOutput("\t" + String.format(formatter, a, a, a, a, a, a, b, b, b, b, b, b).replace("\n", "\n\t"));
            });
        } catch (IOException e)
        {

        }
    }
}