package ta_rio.user.controller;

import ta_rio.UI.PasswordField.PasswordFieldCustom;
import ta_rio.UI.TextField.TextFieldCustom;
import ta_rio.home.view.HomeView;
import ta_rio.user.dao.UserDao;
import ta_rio.user.model.UserModel;
import ta_rio.util.UserToken;

import java.util.List;

import javax.swing.JComboBox;

public class UserController {
    private UserDao dao;
    UserModel model = new UserModel();

    public UserController() {
        this.dao = new UserDao();
    }

    public List<UserModel> getData() {
        return dao.getUser();
    }

    public void deleteUser(UserModel model) {
        dao.deleteUser(model);
    }

    public String[] tableHeader() {
        return model.getColumnHeader();
    }

    public void createUser(UserModel model) {
        dao.createUser(model);
    }

    public void updateUser(UserModel model) {
        dao.updateUser(model);
    }

    public UserModel getInput(
            TextFieldCustom txtFullname,
            TextFieldCustom txtUsername,
            PasswordFieldCustom txtPassword,
            PasswordFieldCustom txtPasswordConfirm,
            TextFieldCustom txtAddress,
            JComboBox<String> comboRole,
            String userId) {
        UserModel model = new UserModel();

        model.setFullname(txtFullname.getText());
        model.setUsername(txtUsername.getText());
        model.setPassword(txtPassword.getText());
        model.setPasswordConfirm(txtPasswordConfirm.getText());
        model.setAddress(txtAddress.getText());
        model.setRole(comboRole.getSelectedItem().toString());
        model.setUserID(userId);
        return model;

    }

    public boolean passwordValidation(UserModel model) {
        return !model.getPassword().equals(model.getPasswordConfirm());
    }

    public boolean usernameValidation(UserModel model) {
        return dao.usernameIsAvailable(model);
    }

    public void refreshToken(UserModel userModel) {
        UserToken.userID = userModel.getUserID();
        UserToken.fullname = userModel.getFullname();
        UserToken.username = userModel.getUsername();
        UserToken.role = userModel.getRole();
        UserToken.address = userModel.getAddress();
        HomeView.home.refreshProfile();

    }

    public boolean blankValidation(UserModel model) {
        boolean usernameIsBlank = model.getFullname().isEmpty();
        boolean fullnameIsBlank = model.getFullname().isEmpty();
        boolean passwordIsBlank = model.getPassword().isEmpty();
        boolean passwordConfirmIsBlank = model.getPasswordConfirm().isEmpty();
        boolean addressIsBlank = model.getAddress().isEmpty();

        return usernameIsBlank || fullnameIsBlank || passwordIsBlank || passwordConfirmIsBlank || addressIsBlank;
    }
}
