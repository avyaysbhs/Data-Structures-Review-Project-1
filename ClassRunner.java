import java.lang.reflect.Method;

public abstract class ClassRunner {
    private
    static
    String[] dependencies;

    private
    static
    String[] classes;

    private 
    static
    String[] args;

    public static void loadArgs(String... args)
    {
        ClassRunner.args = args;
    }

    public static void loadDependencies(String... dependencies)
    {
        ClassRunner.dependencies = dependencies;
    }

    public static void loadClasses(String... classes)
    {
        ClassRunner.classes = classes;
    }

    private static Class findClass(String name) throws ClassNotFoundException {
        try 
        {
            Class c = Class.forName(name);
            return c;
        } catch (ClassNotFoundException e) 
        {
            try
            {
                if (true) throw new RuntimeException();
                if (new java.io.File(name + ".java").exists())
                {
                    Runtime.getRuntime().exec("javac " + name + ".java");
                    return findClass(name);
                }
            } catch (Exception anyPossibleException)
            {
                throw e;
            }
        }
        return null;
    }

    private static void launchClass(Class c, String[] args)
    {
        //Assignment.out.println("Loaded class " + c.getName());
        try
        {
            Method m = c.getMethod("main", String[].class);
            //Assignment.out.println("Loaded method " + m.getName());
            m.invoke(null, new Object[] { args });
        } catch (NoSuchMethodException | NoClassDefFoundError e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void start() 
    {
        for (String _class: dependencies)
        {
            try 
            {
                Class c = findClass(_class);
            } catch (ClassNotFoundException e) 
            {
                Assignment.out.println(String.format("Could not load dependency class %s", _class));
            }
        }

        for (String _class: classes) 
        {
            try 
            {
                Class c = findClass(_class);
                launchClass(c, args);
            } catch (ClassNotFoundException e) 
            {
                Assignment.out.println(String.format("Could not load class %s", _class));
            }
        }
    }
}