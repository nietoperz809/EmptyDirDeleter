import java.io.File;

public class EmptyDirDeleter
{
    public int getDels ()
    {
        return dels;
    }

    private int dels;

    private EmptyDirDeleter (File dir)
    {
        dels = 0;
        run(dir);
    }

    public static void main (String[] args)
    {
        int pass = 0;
        for(;;)
        {
            pass++;
            System.out.println("Pass: "+pass);
            EmptyDirDeleter d = new EmptyDirDeleter(new File("E:/"));
            if (d.getDels() == 0)
                break;
        }
    }

    public void run (File dir)
    {
        File[] files = dir.listFiles();
        if (files == null)
        {
            System.out.println("FAIL: "+dir.getPath());
            return;
        }
        for (File file : files)
        {
            if (file.isDirectory())
            {
                run (file);
            }
        }
        if (files.length == 0)  // empty
        {
            System.out.println("Empty: "+dir.getPath());
            if (dir.delete())
            {
                System.out.println("deleted!");
                dels++;
            }
        }
    }
}
