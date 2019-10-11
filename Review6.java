import java.io.IOException;

public class Review6 {
    static class Cube {
        int Top = 1;
        int North = 2;
        int West = 3;
        int East = 4;
        int South = 5;
        int Bottom = 6;

        public void rotate(String direction) {
            if (direction.equals("N")) {
                int tmp = Bottom;
                Bottom = North;
                North = Top;
                Top = South;
                South = tmp;
            } else if (direction.equals("S")) {
                int tmp = Top;
                Top = North;
                North = Bottom;
                Bottom = South;
                South = tmp;
            } else if (direction.equals("E")) {
                int tmp = East;
                East = Top;
                Top = West;
                West = Bottom;
                Bottom = tmp;
            } else if (direction.equals("W")) {
                int tmp = West;
                West = Top;
                Top = East;
                East = Bottom;
                Bottom = tmp;
            }
        }

        public int roll(String series) {
            for (int i = 0; i < series.length(); i++) {
                rotate(series.substring(i, i + 1));
            }
            return Top;
        }

        public int getTopSurface()
        {
            return Top;
        }

        public void refresh() {
            Top = 1;
            North = 2;
            West = 3;
            East = 4;
            South = 5;
            Bottom = 6;
        }
    }

    public static void main(String[] args) {
        Assignment.announceAssignment("Rollin' Thunder", 6);
        Assignment.announceInput();

        Cube c = new Cube();

        Assignment.announceOutput();

        try {
            DSUtil.readFileContents("Input Files/Prob6Input.txt", line -> {
                Assignment.printForInput(line);
                c.refresh();
                Assignment.out.println(c.roll(line));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}