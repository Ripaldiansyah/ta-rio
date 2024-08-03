package ta_rio.login.view;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;
import ta_rio.UI.Icon.IconCustom;
import ta_rio.UI.Notification.NotificationCustom;
import ta_rio.UI.PasswordField.PasswordFieldCustom;
import ta_rio.UI.TextField.TextFieldCustom;
import ta_rio.UI.button.ButtonCustom;
import ta_rio.login.controller.LoginController;
import ta_rio.login.model.LoginModel;

public class LoginView extends JPanel {

    private JPanel rightPanel;
    private JPanel leftPanel;
    private JLabel lbTitle;
    private JLabel lbDescription;
    private ButtonCustom btnLogin;
    private TextFieldCustom txtUsername;
    private PasswordFieldCustom txtPassword;
    private LoginModel model = new LoginModel();
    private LoginController controller = new LoginController();
    private NotificationCustom notification = new NotificationCustom("Login");

    public LoginView() {
        initComponent();
    }

    private void initComponent() {
        initLayout();
        initLeft();
        initRight();
        initAdd();
        initStyle();
    }

    private void initLayout() {
        setLayout(new MigLayout("fill,insets 10", "[center, fill]", "[center, fill]"));
        leftPanel = new JPanel(new MigLayout("fill, insets 100,wrap", "[fill,center, 250:280]", "[center]"));
        rightPanel = new JPanel(new MigLayout("fill, insets", "[fill,center]", "[center]"));
    }

    private void initRight() {
        Icon imgIcon = new ImageIcon(this.getClass().getResource("/Images/logo.png"));
        JLabel logo = new JLabel(imgIcon);
        rightPanel.add(logo);
    }

    private void initLeft() {
        lbTitle = new JLabel("Hai,");
        lbDescription = new JLabel("Selamat Datang");
        IconCustom iconLogin = new IconCustom("svg/login.svg", 1f, null);
        txtUsername = new TextFieldCustom(
                "Masukan nama pengguna Anda",
                null,
                true);
        txtPassword = new PasswordFieldCustom(
                "Masukan Kata andi Anda",
                null,
                true);

        btnLogin = new ButtonCustom(
                null,
                iconLogin.getIcon(),
                null,
                null,
                (e) -> {
                    model = controller.getInput(txtUsername, txtPassword);
                    if (!controller.isRegistered(model)) {
                        notification.wrongAccount();
                        return;
                    }
                    controller.login();
                });

        leftPanel.add(lbTitle);
        leftPanel.add(lbDescription);
        leftPanel.add(txtUsername, "gapy 8");
        leftPanel.add(txtPassword);
        leftPanel.add(btnLogin, "gapy 1, w 80!, h 40!, push, right");
    }

    private void initAdd() {
        add(leftPanel, "split 2, h 450!");
        add(rightPanel);
    }

    private void initStyle() {
        rightPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "background:lighten(@background,7%)");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +10");
        lbDescription.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+10;"
                + "foreground:lighten(@foreground,30%)");
    }

}
