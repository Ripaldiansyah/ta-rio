package ta_rio.spk_result.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

public class SpkResultTableModel extends AbstractTableModel {
    List<Map<Object, Object>> rankListMap = new ArrayList<>();

    @Override
    public int getRowCount() {
        return rankListMap.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Map<Object, Object> rowData = rankListMap.get(rowIndex);
        Object[] keys = rowData.keySet().toArray();
        Object key = keys[columnIndex];
        return rowData.get(key);
    }

    @Override
    public String getColumnName(int column) {
        String[] header = {
                "Nama Alternatif",
                "Peringkat"
        };
        return header[column];
    }

    public void setData(List<Map<Object, Object>> rank) {
        clear();
        rankListMap = rank;
        fireTableDataChanged();
    }

    public void clear() {
        rankListMap.clear();
        fireTableDataChanged();
    }
}
