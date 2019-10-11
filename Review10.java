import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

class Measure
{
    public final static String[] display_notes = {
        "G#", "G", "F#", "F", "E", "D#", "D", "C#", "C", "B", "A#", "A", 
        "G#", "G", "F#", "F", "E", "D#", "D", "C#", "C", "B", "A#", "A", 
        "G#", "G", "F#", "F", "E"
    };

    public final static String[] notes = {
        "G#3", "G3", "F#3", "F3", "E3", "D#3", "D3", "C#3", "C3", "B3", "A#3", "A3", 
        "G#2", "G2", "F#2", "F2", "E2", "D#2", "D2", "C#2", "C2", "B2", "A#2", "A2", 
        "G#1", "G1", "F#1", "F1", "E1"
    };

    public static String[][] match =
    {
        { "E1", "A2", "D2", "G2", "B3", "E3" },
        { "F1", "A#2", "D#2", "G#2", "C3", "F3" },
        { "F#1", "B2", "E2", "A3", "C#3", "F#3" },
        { "G1", "C2", "F2", "A#3", "D3", "G3" },
        { "G#1", "C#2", "F#2", "B3", "D#3", "G#3"}
    };

    public DSUtil.BetterList<
            DSUtil.BetterList<String>
        > components = new DSUtil.BetterList<>();

    private HashMap<String, Boolean> cache = null;

    public HashMap<String, Boolean> matchComponents()
    {
        if (cache != null) return cache;
        
        HashMap<String, Boolean> out = new HashMap<>();

        for (int y = 0; y < components.size(); y++)
        {
            for (int x = 0; x < components.get(y).size(); x++)
            {
                String key = components.get(y).get(x);
                if (key.equals("*") || key.equals("o"))
                {
                    out.put(match[y][x], true);
                }
                else
                {
                    out.put(match[y][x], false);
                }
            }
        }

        cache = out;

        return out;
    }
    public Measure() { }
}

public class Review10
{

    public static void main(String[] args)
    {
        Assignment.announceAssignment("Guitar Hero", 10);
        Assignment.announceInput();
        
        DSUtil.BetterList<String> lines = new DSUtil.BetterList<>();
        DSUtil.BetterList<Measure> measures = new DSUtil.BetterList<>();

        try
        {
            Assignment.printForInput("\t" + DSUtil.readFileContents("Input Files/Prob10Input.txt", lines::add).replaceAll("\n", "\n\t"));
            lines.forEach(e ->
            {
                String[] words = e.split(",");
                
                if (measures.size() == 0)
                {
                    for (int i=0;i<words.length;i++)
                    {
                        Measure measure = new Measure();
                        measures.add(measure);
                    }
                }

                for (int i=0;i<words.length;i++)
                {
                    Measure m = measures.get(i);
                    String[] word = words[i].split("");
                    m.components.add(new DSUtil.BetterList<String>(word));
                }
            });

            DSUtil.BetterList<String> tr = new DSUtil.BetterList<>();
            AtomicInteger atom = new AtomicInteger(1);

            measures.forEach(e ->
            {
                tr.add(String.valueOf(atom.getAndIncrement()));
            });

            Assignment.printForOutput("Measure\t\t" + tr.reduce((a, b) -> {
                return a + "\t" + b;
            }));

            Assignment.announceOutput();

            for (int i=0;i<Measure.notes.length;i++)
            {
                tr.recycle();
                for (Measure m: measures) {
                    if (m.matchComponents().get(Measure.notes[i]))
                    {
                        tr.add("O");
                    } else
                    {
                        tr.add(" ");
                    }
                };
                
                Assignment.printForOutput(Measure.display_notes[i] + " \t\t\t" + tr.reduce((a, b) -> {
                    return a + "\t" + b;
                }));
            }
        } catch (IOException e)
        {

        }
    }
}