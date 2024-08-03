package ta_rio.login.controller;

import ta_rio.App;
import ta_rio.UI.PasswordField.PasswordFieldCustom;
import ta_rio.UI.TextField.TextFieldCustom;
import ta_rio.home.view.HomeView;
import ta_rio.login.dao.LoginDao;
import ta_rio.login.model.LoginModel;

public class LoginController {

    private LoginDao dao;

    public LoginController() {
        this.dao = new LoginDao();
    }

    public LoginModel getInput(
            TextFieldCustom txtUsername,
            PasswordFieldCustom txtPassword) {
        LoginModel model = new LoginModel();

        model.setUsername(txtUsername.getText());
        model.setPassword(txtPassword.getText());

        return model;
    }

    public boolean isRegistered(LoginModel model) {
        return dao.isRegistered(model);
    }

    public void login() {
        App.content.changeContentPanel(new HomeView());
    }

}
