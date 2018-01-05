import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

class ColoredTableCellRenderer
        implements TableCellRenderer
{

    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column
    )
    {
        JLabel label = new JLabel(""+value);
        label.setOpaque(true);
        column = table.convertColumnIndexToModel(column);
        if (column == 2)
        {
            ColorString v = (ColorString)value;
            label.setBackground(v.getColor());
        }
        return label;
    }
}
