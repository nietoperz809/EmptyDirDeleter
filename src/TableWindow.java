import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class TableWindow extends JFrame
{
    private DefaultTableModel tableModel;
    private JTable table;

    private SyncList listFiller;

    private JButton startButton = new JButton("Start");
    private JButton stopButton = new JButton("Stop");
    private JButton renameButton = new JButton("Invoke Renamer");
    private boolean stopflag = false;


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

        this.setLayout(new BorderLayout());

        JPanel p = new JPanel();
        p.add (startButton);
        p.add (stopButton);
        p.add (renameButton);

        stopButton.addActionListener(e ->
        {
            stopflag = true;
        });

        startButton.addActionListener((ActionEvent e) ->
        {
            startButton.setEnabled(false);
            listFiller = new SyncList(tableModel, table);
            new Thread(this::runDirDeleter).start();
        });

        renameButton.addActionListener(e ->
        {
            renameButton.setEnabled(false);
            new Thread(this::runRenamer).start();
        });

        this.add (p, BorderLayout.NORTH);
        this.add (new JScrollPane(table), BorderLayout.CENTER);
        this.setTitle("Table Example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void runRenamer()
    {
        try
        {
            renamer.MainWnd.main();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        renameButton.setEnabled(true);
    }

    private void runDirDeleter ()
    {
        int pass = 0;
        for(;;)
        {
            pass++;
            System.out.println("Pass: "+pass);
            File dir = new File("F:\\pron");
            listFiller.addRow (pass, "Starting: "+dir.getPath(),"INFO", Color.CYAN);
            EmptyDirDeleter d = new EmptyDirDeleter(dir, listFiller, pass, stopButton);
            int dels = d.getDels();
            listFiller.addRow (pass, "Files deleted: "+dels,"INFO", Color.MAGENTA);
            if (dels == 0 || stopflag)
            {
                if (stopflag)
                {
                    System.out.println("Break");
                    listFiller.addRow (pass, "Break!","INFO!", Color.RED);
                }
                else
                {
                    System.out.println("Finished.");
                    listFiller.addRow (pass, "Finished","INFO!", Color.BLUE);
                }
                listFiller.stop();
                startButton.setEnabled(true);
                break;
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        UIManager.setLookAndFeel(
                "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        new TableWindow();
    }
}