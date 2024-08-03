package ta_rio.criteria.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ta_rio.criteria.controller.CriteriaController;
import ta_rio.criteria.service.CriteriaTableService;

public class CriteriaTableModel extends AbstractTableModel implements CriteriaTableService {
    CriteriaController controller = new CriteriaController();
    List<CriteriaModel> criteriaList = new ArrayList<>();

    @Override
    public int getRowCount() {
        return criteriaList.size();
    }

    @Override
    public int getColumnCount() {
        return controller.tableHeader().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return criteriaList.get(rowIndex).getCriteriaName();
            case 1:
                return criteriaList.get(rowIndex).getCriteriaWeight();
            case 2:
                return criteriaList.get(rowIndex).getCriteriaType();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return controller.tableHeader()[column];
    }

    @Override
    public void removeData(int index) {
        criteriaList.remove(index);
        fireTableRowsDeleted(index, index);
    }

    @Override
    public void clear() {
        criteriaList.clear();
        fireTableDataChanged();
    }

    @Override
    public CriteriaModel getSelectedIndex(int index) {
        return criteriaList.get(index);
    }

    @Override
    public void setData(List<CriteriaModel> criteria) {
        clear();
        this.criteriaList.addAll(criteria);
        fireTableDataChanged();
    }
}
