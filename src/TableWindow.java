import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class TableWindow extends JFrame
{
    private DefaultTableModel tableModel;
    private JTable table;
    private int pass;

    private SyncList sl;

    private TableWindow ()
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

        table.setDefaultRenderer(Object.class, new ColoredTableCellRenderer());

        sl = new SyncList(tableModel, table);

        this.add(new JScrollPane(table));
        this.setTitle("Table Example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        runDirDeleter();
        sl.addRow (pass, "Finished","INFO!", Color.BLUE);
    }

    private void runDirDeleter ()
    {
        pass = 0;
        for(;;)
        {
            pass++;
            System.out.println("Pass: "+pass);
            File dir = new File("F:\\pron");
            sl.addRow (pass, "Starting: "+dir.getPath(),"INFO", Color.CYAN);
            EmptyDirDeleter d = new EmptyDirDeleter(dir, sl, pass);
            int dels = d.getDels();
            sl.addRow (pass, "Files deleted: "+dels,"INFO", Color.MAGENTA);
            if (dels == 0)
            {
                System.out.println("Finished.");
                sl.stop();
                break;
            }
        }
    }

    public static void main(String[] args)
    {
        new TableWindow();
    }
}