package ta_rio.user.service;

import ta_rio.user.model.UserModel;
import java.util.List;

public interface UserService {

    boolean usernameIsAvailable(UserModel model);

    void createUser(UserModel model);

    void updateUser(UserModel model);

    void deleteUser(UserModel model);

    List<UserModel> getUser();
}
