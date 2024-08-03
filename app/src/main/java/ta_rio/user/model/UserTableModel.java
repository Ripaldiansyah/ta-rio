package ta_rio.user.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ta_rio.user.controller.UserController;
import ta_rio.user.service.UserTableService;

public class UserTableModel extends AbstractTableModel implements UserTableService {
    UserController controller = new UserController();
    List<UserModel> user = new ArrayList<>();

    @Override
    public int getRowCount() {
        return user.size();
    }

    @Override
    public int getColumnCount() {
        return controller.tableHeader().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return user.get(rowIndex).getFullname();
            case 1:
                return user.get(rowIndex).getUsername();
            case 2:
                return user.get(rowIndex).getRole();
            case 3:
                return user.get(rowIndex).getAddress();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return controller.tableHeader()[column];
    }

    @Override
    public void setData(List<UserModel> user) {
        clear();
        this.user.addAll(user);
        fireTableDataChanged();
    }

    @Override
    public void removeData(int index) {
        user.remove(index);
        fireTableRowsDeleted(index, index);
    }

    @Override
    public void clear() {
        user.clear();
        fireTableDataChanged();
    }

    @Override
    public UserModel getSelectedIndex(int index) {
        return user.get(index);
    }
}
