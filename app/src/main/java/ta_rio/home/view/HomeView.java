package ta_rio.home.view;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

import java.awt.Image;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatPopupMenu;

import ta_rio.App;
import ta_rio.UI.Icon.IconCustom;
import ta_rio.UI.button.ButtonCustom;
import ta_rio.alternative.view.ManagementAlternativeView;
import ta_rio.criteria.view.ManagementCriteriaView;
import ta_rio.dashboard.view.DashboardView;
import ta_rio.login.view.LoginView;
import ta_rio.spk.view.ManagementSpkView;
import ta_rio.user.model.UserModel;
import ta_rio.user.view.ManagementUserView;
import ta_rio.user.view.UserView;
import ta_rio.util.UserToken;

public class HomeView extends JPanel {
    static JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel separatorPanel;
    static JPanel contentPanel;
    private FlatPopupMenu profileMenu = new FlatPopupMenu();
    private ButtonCustom btnProfile;

    public static HomeView home;
    private JPanel profilePanel;

    public HomeView() {
        initComponent();
        home = this;
    }

    private void initComponent() {
        // testUser();
        initLayout();
        setProfile();
        setHeader();
        setSeparator();
        setContent();
        initAdd();
        initStyle();
    }

    private void initLayout() {
        setLayout(new MigLayout("insets 0, fill", "fill,center", "top,fill"));
        mainPanel = new JPanel(new MigLayout("wrap,insets 0, gap 0", "fill", "fill"));
        headerPanel = new JPanel(new MigLayout("insets 0 70 0 70,center"));
        separatorPanel = new JPanel(new MigLayout("fill, insets 0", "center"));
        contentPanel = new JPanel(new MigLayout("wrap, fill, insets 10 80 10 70", "center,fill", "center,fill"));
        profilePanel = new JPanel(new MigLayout("fillx, wrap", "fill, 200, left"));
    }

    private void initStyle() {
        mainPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#000;"
                + "arc:15");
        headerPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,7%)");
        separatorPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#2f7ab1");
        contentPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#ebeef5");
        profilePanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,7%);"
                + "arc:15");

    }

    private void initAdd() {
        mainPanel.add(headerPanel);
        mainPanel.add(separatorPanel, "pushx, h 20!");
        mainPanel.add(contentPanel, " push");
        add(mainPanel);
    }

    private void setHeader() {
        ImageIcon originalIcon = new ImageIcon(this.getClass().getResource("/Images/logo.png"));
        Image originalImage = originalIcon.getImage();
        int newWidth = 100;
        int newHeight = 70;
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        ImageIcon logo = new ImageIcon(resizedImage);

        String[] title = {
                "Beranda",
                "Pengguna",
                "Alternatif",
                "Kriteria",
                "SPK",
                // "Laporan",
        };

        ActionListener[] actions = {
                e -> changeContent(new DashboardView()),
                e -> changeContent(new ManagementUserView()),
                e -> changeContent(new ManagementAlternativeView()),
                e -> changeContent(new ManagementCriteriaView()),
                e -> changeContent(new ManagementSpkView()),
                // e -> System.out.println(title[5]),

        };

        ButtonCustom[] btnMenuArray = new ButtonCustom[title.length];
        btnProfile = new ButtonCustom(
                "Profil",
                null,
                null,
                null,
                (e) -> {
                    profileMenu.show(btnProfile, 50, btnProfile.getHeight());
                });

        headerPanel.add(new JLabel(logo), "pushx");
        if (UserToken.role.equalsIgnoreCase("admin")) {
            for (int i = 0; i < title.length; i++) {
                btnMenuArray[i] = new ButtonCustom(title[i], null, "#fff", "#37393c", actions[i]);
                headerPanel.add(btnMenuArray[i].getButton(), "grow, h 25!,gapx 0");
            }
        }
        headerPanel.add(btnProfile, " right, h 30!, w 100!, push");

    }

    private void setSeparator() {
        JLabel title = new JLabel(
                "Sistem Pendukung Keputusan pembelian bahan Teh Terbaik Menggunakan metode Topsis  Berbasis Java Desktop pada Teh Rahaju");
        title.putClientProperty(FlatClientProperties.STYLE, ""
                + "font : 10;"
                + "foreground:#fff");
        separatorPanel.add(title);
    }

    private void setContent() {
        if (UserToken.role.equalsIgnoreCase("Admin")) {
            changeContent(new DashboardView());
        } else {
            changeContent(new ManagementSpkView());
        }

    }

    private void refreshUI() {
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void refreshProfile() {
        profilePanel.removeAll();
        setProfile();
        profilePanel.revalidate();
        profilePanel.repaint();
    }

    private void setProfile() {
        String fullname = UserToken.fullname;
        String role = UserToken.role;
        JLabel lbName = new JLabel(fullname);
        JLabel lbRole = new JLabel(role);
        IconCustom iconSetting = new IconCustom("svg/setting.svg", 0.5f, null);
        IconCustom iconLogout = new IconCustom("svg/logout.svg", 0.5f, null);
        ButtonCustom btnSetting = new ButtonCustom(

                "Pengaturan",
                iconSetting.getIcon(),
                "#2f7ab1",
                "#fff",
                (e) -> {
                    handleSetting();
                });
        ButtonCustom btnLogout = new ButtonCustom(

                "Keluar",
                iconLogout.getIcon(),
                "#C32148",
                "#fff",
                (e) -> {
                    App.content.loginRegisterPanel(new LoginView());
                });

        profilePanel.add(lbName);
        profilePanel.add(lbRole);
        profilePanel.add(btnSetting, "h 30!, split 2, pushx");
        profilePanel.add(btnLogout, "h 30!");
        profileMenu.add(profilePanel);

    }

    public void changeContent(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel);
        refreshUI();
    }

    private void handleSetting() {
        UserView userView = new UserView("Pengaturan Akun");
        userView.isSetting = true;
        userView.oldUsername = UserToken.username;
        userView.disableUpdate();
        UserModel userModel = new UserModel();
        userModel.setUserID(UserToken.userID);
        userModel.setUsername(UserToken.username);
        userModel.setFullname(UserToken.fullname);
        userModel.setRole(UserToken.role);
        userModel.setAddress(UserToken.address);
        userView.setField(userModel);
        changeContent(userView);
    }

}
