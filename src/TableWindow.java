import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;

public class TableWindow extends JFrame
{
    private DefaultTableModel tableModel;
    private JTable table;
    private int pass;

    public TableWindow ()
    {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Pass");
        tableModel.addColumn("Path");
        tableModel.addColumn("Deleted");

        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(1).setMaxWidth(1000);
        table.getColumnModel().getColumn(2).setMaxWidth(150);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        
        this.add(new JScrollPane(table));
        this.setTitle("Table Example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        runDirDeleter();
        addRow (pass, "Finished","INFO!");
    }

    public void addRow (int pass, String path, String deleted)
    {
        tableModel.addRow(new Object[]{pass, path, deleted});
        table.changeSelection(table.getRowCount() - 1, 0, false, false);
        try
        {
            Thread.sleep(0);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void runDirDeleter ()
    {
        pass = 0;
        for(;;)
        {
            pass++;
            System.out.println("Pass: "+pass);
            File dir = new File("E:\\");
            addRow (pass, "Starting: "+dir.getPath(),"INFO");
            EmptyDirDeleter d = new EmptyDirDeleter(dir, this, pass);
            int dels = d.getDels();
            addRow (pass, "Files deleted: "+dels,"INFO");
            if (dels == 0)
                break;
        }
    }

    public static void main(String[] args)
    {
        new TableWindow();
    }
}