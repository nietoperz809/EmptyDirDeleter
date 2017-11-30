import java.io.File;

public class EmptyDirDeleter
{
    public int getDels ()
    {
        return dels;
    }

    private int dels;
    private TableWindow tab;
    private int pass;

    public EmptyDirDeleter (File dir, TableWindow tab, int pass)
    {
        this.pass = pass;
        this.tab = tab;
        dels = 0;
        run(dir);
    }

//    public static void main (String[] args)
//    {
//        int pass = 0;
//        for(;;)
//        {
//            pass++;
//            System.out.println("Pass: "+pass);
//            EmptyDirDeleter d = new EmptyDirDeleter(new File("C:\\Users\\Administrator\\Desktop"));
//            if (d.getDels() == 0)
//                break;
//        }
//    }

    public void run (File dir)
    {
        File[] files = dir.listFiles();
        tab.addRow(pass, dir.getPath(), "Begin Scan");
        if (files == null)
        {
            tab.addRow(pass, dir.getPath(), "NoAccess!");
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
            String path = dir.getPath();
            if (dir.delete())
            {
                tab.addRow(pass, path, "Deleted!");
                dels++;
            }
            else
            {
                tab.addRow(pass, path, "NO!!!");
            }
        }
        tab.addRow(pass, dir.getPath(), "End Scan");
    }
}
