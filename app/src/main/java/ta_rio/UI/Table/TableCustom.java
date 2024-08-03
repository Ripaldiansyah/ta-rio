package ta_rio.UI.Table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

public class TableCustom extends JPanel {

    JTable table;

    public TableCustom(
            TableModel tabelModel,
            String title) {

        table = new JTable(tabelModel);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tabelModel);
        table.setRowSorter(sorter);
        table.setRowHeight(40);
        table.getTableHeader().setBackground(Color.decode("#2f7ab1"));
        table.getTableHeader().setForeground(getBackground());
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JLabel lbTitle = new JLabel(title);
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +5");
        JPanel tablePanel = new JPanel(new MigLayout("wrap,insets 0, gap 0", "[fill]", "fill"));
        tablePanel.add(lbTitle);
        tablePanel.add(table, "push, gap 0");
        putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,7%)");
        setLayout(new MigLayout("wrap,insets 0", "[grow, fill]"));
        add(tablePanel);
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public int getSelectedColumn() {
        return table.getSelectedColumn();
    }

    public Component getheader() {
        return table.getTableHeader();
    }

    public Object getValueAt(int row, int column) {
        return table.getValueAt(row, column);
    }

    public JTable getTable() {
        return table;
    }

}
