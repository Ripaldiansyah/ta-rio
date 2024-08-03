package ta_rio.alternative.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ta_rio.alternative.controller.AlternativeController;
import ta_rio.alternative.service.AlternativeTableService;

public class AlternativeTableModel extends AbstractTableModel implements AlternativeTableService {
    AlternativeController controller = new AlternativeController();
    List<AlternativeModel> alternatives = new ArrayList<>();

    @Override
    public int getRowCount() {
        return alternatives.size();
    }

    @Override
    public int getColumnCount() {
        return controller.tableHeader().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return alternatives.get(rowIndex).getAlternativeId();
            case 1:
                return alternatives.get(rowIndex).getAlternativeName();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return controller.tableHeader()[column];
    }

    @Override
    public void setData(List<AlternativeModel> criteria) {
        clear();
        this.alternatives.addAll(criteria);
        fireTableDataChanged();
    }

    @Override
    public void removeData(int index) {
        alternatives.remove(index);
        fireTableRowsDeleted(index, index);
    }

    @Override
    public void clear() {
        alternatives.clear();
        fireTableDataChanged();
    }

    @Override
    public AlternativeModel getSelectedIndex(int index) {
        return alternatives.get(index);
    }
}
