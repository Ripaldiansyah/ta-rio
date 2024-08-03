package ta_rio.spk_count.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ta_rio.spk_count.controller.SPKCountController;
import ta_rio.spk_count.service.SPKCountTableService;

public class SPKCountTableModel extends AbstractTableModel implements SPKCountTableService {
    SPKCountController controller = new SPKCountController();
    List<List<Object>> spkCountList = new ArrayList<>();

    @Override
    public int getRowCount() {
        return spkCountList.size();
    }

    @Override
    public int getColumnCount() {
        return controller.tableHeader().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (spkCountList.size() > rowIndex && spkCountList.get(rowIndex).size() > columnIndex) {
            return spkCountList.get(rowIndex).get(columnIndex);
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return controller.tableHeader()[column];
    }

    @Override
    public void removeData(int index) {
        spkCountList.remove(index);
        fireTableRowsDeleted(index, index);
    }

    @Override
    public void clear() {
        spkCountList.clear();
        fireTableDataChanged();
    }

    @Override
    public void setData(List<List<Object>> criteria) {
        clear();
        this.spkCountList.addAll(criteria);
        fireTableDataChanged();
    }

    @Override
    public Object getSelectedIndex(int index) {
        return spkCountList.get(index);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return false;
        }
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (spkCountList.size() <= rowIndex) {
            spkCountList.add(new ArrayList<>());
        }

        List<Object> row = spkCountList.get(rowIndex);
        if (row.size() <= columnIndex) {
            for (int i = row.size(); i <= columnIndex; i++) {
                row.add(null);
            }
        }
        row.set(columnIndex, aValue);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

}
