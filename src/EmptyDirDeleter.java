import javax.swing.*;
import java.awt.*;
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
    private volatile boolean stopFlag = false;

    public EmptyDirDeleter (File dir, SyncList tab, int pass, JButton stopb)
    {
        this.pass = pass;
        this.tab = tab;
        dels = 0;
        stopb.addActionListener(e ->
        {
            stopFlag = true;
        });
        run(dir);
    }

    private void run (File dir)
    {
        if (stopFlag)
            return;
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
