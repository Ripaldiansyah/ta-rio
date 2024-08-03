package ta_rio.spk.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ta_rio.criteria.controller.CriteriaController;
import ta_rio.criteria.service.CriteriaTableService;
import ta_rio.spk.controller.SpkController;

public class SpkTableModel extends AbstractTableModel {
    SpkController controller = new SpkController();
    List<SpkModel> spkList = new ArrayList<>();

    @Override
    public int getRowCount() {
        return spkList.size();
    }

    @Override
    public int getColumnCount() {
        return controller.tableHeader().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return spkList.get(rowIndex).getSpkId();
            case 1:
                return spkList.get(rowIndex).getCreatedAt();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return controller.tableHeader()[column];
    }

    public void removeData(int index) {
        spkList.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public void clear() {
        spkList.clear();
        fireTableDataChanged();
    }

    public SpkModel getSelectedIndex(int index) {
        return spkList.get(index);
    }

    public void setData(List<SpkModel> spk) {
        clear();
        this.spkList.addAll(spk);
        fireTableDataChanged();
    }
}
