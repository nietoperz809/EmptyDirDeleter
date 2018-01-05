import java.awt.Color;

import java.io.File;

public class EmptyDirDeleter
{
    public int getDels ()
    {
        return dels;
    }

    private int dels;
    private SyncList tab;
    private int pass;

    public EmptyDirDeleter (File dir, SyncList tab, int pass)
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
        tab.addRow(pass, dir.getPath(), "Begin Scan", Color.ORANGE);
        if (files == null)
        {
            tab.addRow(pass, dir.getPath(), "NoAccess!", Color.RED);
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
                tab.addRow(pass, path, "Deleted!", Color.PINK);
                dels++;
            }
            else
            {
                tab.addRow(pass, path, "NO!!!", Color.RED);
            }
        }
        tab.addRow(pass, dir.getPath(), "End Scan", Color.GREEN);
    }
}
