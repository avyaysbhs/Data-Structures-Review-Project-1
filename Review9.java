import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Review9
{
    public static void spiral(int[][] m)
    {
        int y = 1, x = 0;
        int er = m.length - 2, ec = m.length - 2;

        while (y <= er && x <= ec)
        {
            for (int i = x; i <= ec; i++)
            {
                m[y][i] = 1;
            }

            x++; y++;

            for (int i = y; i <= er; i++)
            {
                m[i][ec] = 1;
            }

            y++; ec--;

            for (int i = ec; i >= x; i--)
            {
                m[er][i] = 1;
            }

            er--; ec--;

            for (int i = er; i >= y; i--)
            {
                m[i][x] = 1;
            }

            er--; x++;
        }

        if (m.length % 4 == 2)
        {
            m[m.length/2][m.length/2 - 1] = 1;
        }

    }

    public static String[][] spiral_format(int[][] m)
    {
        String[][] out = new String[m.length][m[0].length];

        for (int y=0;y<m.length;y++)
        {
            for (int x=0;x<m[y].length;x++)
            {
                out[y][x] = m[y][x] == 1 ? "-" : "*";
            }
        }

        return out;
    }

    public static void print_mat(Object[][] m, PrintStream stream)
    {
        stream.print("{\r\n");
        for (int y=0;y<m.length;y++)
        {
            stream.print("\t[");
            for (int x=0;x<m[y].length;x++)
            {
                stream.print(m[y][x]);
                if (x < m[y].length - 1)
                    stream.print(", ");
            }
            stream.print("]");
            if (y < m.length - 1)
                stream.print(",\r\n");
        }
        stream.println("\r\n}");
    }

    public static void print_matrix(String[][] m, PrintStream stream)
    {
        for (int y=0;y<m.length;y++)
        {
            for (int x=0;x<m[y].length;x++)
            {
                stream.print(m[y][x] + " ");
            }
            stream.println("");
        }
        stream.println("\r\n");
    }

    public static void main(String[] args)
    {
        Assignment.announceAssignment("Spiraling", 9);
        Assignment.announceInput();

        try 
        {
            DSUtil.readFileContents(new File("Input Files/Prob9Input.txt"), e ->
            {
                int size = Integer.parseInt(e);
                int[][] matrix = new int[size][size];
                spiral(matrix);

                Assignment.printForInput("\t" + e);

                print_matrix(spiral_format(matrix), Assignment.out);
            });
        } catch (IOException e)
        {
            
        }
    }
}