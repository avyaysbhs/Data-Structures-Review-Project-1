import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;

public class Assignment extends ClassRunner {
    public final static String[] dependencies = { "DSUtil" };
    public static final boolean UseFiles = true;

    public final static String[] classes = {
        "Review1", 
        "Review2", 
        "Review3", 
        "Review4",
        "Review5",
        "Review6",
        "Review7",
        "Review8",
        "Review9",
        "Review10"
    };

    public static PrintStream out;

    public static void resetOutput() {
        out = System.out;
    }

    public static void setOutput(String fileName) {
        try {
            File f = new File(fileName);
            if (!f.exists())
                f.createNewFile();
            out = new PrintStream(f);
        } catch (IOException e) {
            resetOutput();
        }
    }

    public static void announceAssignment(String name, int number)
    {
        System.out.println("\r\nAssignment " + number + ") " + name + "\r\n");
        if (UseFiles)
        {
            setOutput("Output Files/Prob" + number + "Output.txt");
        } else {
            out = System.out;
        }
    }

    public static void announceOutput()
    {
        if (!UseFiles)
            System.out.println("Output:\r\n");
    }

    public static void announceInput()
    {
        Assignment.printForInput("Input sample:\r\n");
    }

    public static void printForInput(String input)
    {
        System.out.println(input);
    }

    public static void printForOutput(String output)
    {
        if (UseFiles)
        {
            out.println(output);
        } else {
            out.println("\t" + output.replace("\n", "\n\t"));
        }
    }

    public static void main(String[] args)
    {
        out = System.out;
        Assignment.loadArgs(args);
        Assignment.loadDependencies(dependencies);
        Assignment.loadClasses(classes);
        out.println("\r\n");
        start();
    }
}