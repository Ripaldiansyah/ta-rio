package ta_rio.user.service;

import java.util.List;

import ta_rio.user.model.UserModel;

public interface UserTableService {
    void setData(List<UserModel> user);

    void removeData(int index);

    void clear();

    UserModel getSelectedIndex(int index);
}
