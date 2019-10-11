import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Review5
{
    static class Car
    {
        public String Model;
        public float MPG;
        public int Cylinders;
        public float Displacement;
        public float Horsepower;
        public float Weight;
        public float Acceleration;
        public int Year;
        public String Origin;

        public HashMap<String, Boolean> nulls = new HashMap<>();

        public String toString()
        {
            return Model;
        }
    }
    public static void main(String[] args)
    {
        Assignment.announceAssignment("Cars – Reorganized", 5);
        DSUtil.BetterList<DSUtil.BetterList<String>> table = new DSUtil.BetterList<>();

        Assignment.announceInput();

        try {
            DSUtil.readFileContents("Input Files/Prob5Input.txt", e -> {
                Assignment.printForInput("\t" + e);
                table.add(new DSUtil.BetterList<>(e.split("\t")));
            });
        } catch (IOException e) {}

        Assignment.announceOutput();

        DSUtil.BetterList<Car> all_cars = new DSUtil.BetterList<>();

        for (int r = 1; r < table.size(); r++) 
        {
            DSUtil.BetterList<String> row = table.get(r);
            Car car = new Car();

            for (int column = 0; column < row.size(); column++)
            {
                String category = table.get(0).get(column);
                String item = row.get(column);

                if (!item.equals("NA"))
                {
                    car.nulls.put(category, false);
                    try
                    {
                        Field field = Car.class.getField(category);
                        if (field.getType().getName() == "int")
                        {
                            field.set(car, Integer.parseInt(item));
                        } else if (field.getType().getName() == "float")
                        {
                            field.set(car, Float.parseFloat(item));
                        } else
                        {
                            field.set(car, item);
                        }
                    } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e)
                    {
                        Assignment.printForOutput(category);
                        e.printStackTrace();
                    }
                } else
                {
                    car.nulls.put(category, true);
                }
            }

            all_cars.add(car);
        }

        AtomicInteger rotations = new AtomicInteger(0);

        DSUtil.SingleArgCommand<String> sortList = (String param) ->
        {   
            Field f;

            try {
                f = Car.class.getField(param);
            } catch (Exception e) {
                return;
            }

            DSUtil.ComparisonOperation<Car> c1 = (Car a, Car b) ->
            {
                try 
                {
                    if (f.getType().getName() == "int")
                    {
                        int fb = f.getInt(b);
                        int fa = f.getInt(a);
                        return fb < fa;
                    } else if (f.getType().getName() == "float")
                    {
                        float fb = f.getFloat(b);
                        float fa = f.getFloat(a);
                        return fb < fa;
                    }
                } catch (Exception e)
                {
                    return false;
                }

                return false;
            };

            DSUtil.BetterList<Car> l1 = (all_cars.cloneList().filter((Car e) -> 
            {
                return !e.nulls.get(param).booleanValue();
            }).sort(c1));

            DSUtil.BetterList<Car> l2 = l1 = (all_cars.cloneList().filter((Car e) -> 
            {
                return !e.nulls.get(param).booleanValue();
            }).sort(c1));

            l1 = l1.reverse().sector(0, 3).sort(c1);
            l2 = l2.sector(0, 3).sort(c1);

            Assignment.printForOutput(String.format("%-40s%s\n", "Highest " + param, "Lowest " + param));

            for (int i=0;i<3;i++)
            {
                try {
                    Assignment.printForOutput(String.format("\t%-40s%s", l1.get(i).Model, l2.get(i).Model));
                } catch (Exception e) {}
            }

            Assignment.printForOutput("");
        };

        try
        {   
            Field[] fields = Car.class.getFields();

            for (Field f: fields)
            {
                String type = f.getType().getName();
                if (type == "int" || type == "float")
                    sortList.call(f.getName());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}