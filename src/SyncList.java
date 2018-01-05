import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.concurrent.LinkedBlockingDeque;

class SyncObject
{
    int pass;
    String path;
    String deleted;
    Color col;

    public SyncObject (int pass, String path, String deleted, Color col)
    {
        this.pass = pass;
        this.path = path;
        this.deleted = deleted;
        this.col = col;
    }
}

public class SyncList
{
    LinkedBlockingDeque<SyncObject> syncal = new LinkedBlockingDeque<SyncObject>();

    DefaultTableModel model;
    JTable tab;
    volatile boolean stopflag = false;

    public SyncList (DefaultTableModel model, JTable tab)
    {
        this.model = model;
        this.tab = tab;

        new Thread(() ->
        {
            for (; ; )
            {
                try
                {
                    if (syncal.isEmpty())
                    {
                        tab.changeSelection(tab.getRowCount() - 1, 0, false, false);
                        if (stopflag)
                            break;
                        Thread.sleep(100);
                    }
                    else
                    {
                        SyncObject so = syncal.takeLast();
                        ColorString s1 = new ColorString(so.deleted, so.col);
                        model.addRow(new Object[]{so.pass, so.path, s1});
                    }
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void addRow (int pass, String path, String deleted, Color col)
    {
        SyncObject so = new SyncObject(pass, path, deleted, col);
        syncal.addFirst(so);
    }

    public void stop()
    {
        stopflag = true;
    }
}
