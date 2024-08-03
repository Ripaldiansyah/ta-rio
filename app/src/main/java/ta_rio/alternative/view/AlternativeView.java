package ta_rio.alternative.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;
import ta_rio.UI.Notification.NotificationCustom;
import ta_rio.UI.TextField.TextFieldCustom;
import ta_rio.UI.button.ButtonCustom;
import ta_rio.alternative.controller.AlternativeController;
import ta_rio.alternative.model.AlternativeModel;

public class AlternativeView extends JPanel {
        private JPanel mainPanel;
        private JPanel contentPanel;
        private JLabel lbTitle;
        private TextFieldCustom txtAlternativeName;
        private ButtonCustom btnCancel;
        private ButtonCustom btnSave;
        private String title;
        public boolean isEdit = false;
        private AlternativeController controller = new AlternativeController();
        private AlternativeModel model = new AlternativeModel();
        private NotificationCustom notification = new NotificationCustom("Alternatif");
        public String oldAlternativeName;

        public AlternativeView(String title) {
                this.title = title;
                initComponent();
        }

        private void initComponent() {
                initLayout();
                initAdd();
                setContent();
                initStyle();

        }

        private void initLayout() {
                setLayout(new MigLayout("insets 0, fill", "fill,center", "top,fill"));
                mainPanel = new JPanel(new MigLayout("wrap,insets 10, gap 0, fill", "fill", "fill"));
                contentPanel = new JPanel(new MigLayout("wrap,fillx,insets 0 10 10 10", "left,grow,fill", "top,fill"));
        }

        private void initAdd() {
                mainPanel.add(contentPanel);
                add(mainPanel);
        }

        private void initStyle() {
                mainPanel.putClientProperty(FlatClientProperties.STYLE, ""
                                + "background:lighten(@background,7%);"
                                + "arc:20");
                contentPanel.putClientProperty(FlatClientProperties.STYLE, ""
                                + "background:lighten(@background,7%)");
                lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                                + "font:bold +5");
        }

        private void setContent() {
                lbTitle = new JLabel(title);
                txtAlternativeName = new TextFieldCustom(
                                "Masukan Nama Alternatif",
                                null,
                                false);
                btnCancel = new ButtonCustom(
                                "Batal",
                                null,
                                "#37393c",
                                "#fff",
                                (e) -> {
                                        changeContent(new ManagementAlternativeView());
                                });
                btnSave = new ButtonCustom(
                                "Simpan",
                                null,
                                "#2f7ab1",
                                "#fff",
                                (e) -> {
                                        getInput();
                                        if (isEdit) {
                                                handleUpdate();
                                        } else {
                                                handleInsert();
                                        }
                                });

                contentPanel.add(lbTitle);
                contentPanel.add(new JSeparator(), "gapy 5");
                contentPanel.add(new JLabel("Nama Alternatif"), "gapy 5");
                contentPanel.add(txtAlternativeName);
                contentPanel.add(new JSeparator(), "gapy 5");
                contentPanel.add(btnCancel, "pushx, split 2, w 100!, h 50!");
                contentPanel.add(btnSave, "w 100!,h 50!");

        }

        private void changeContent(JPanel panel) {
                removeAll();
                setLayout(new MigLayout("insets 0, fill", "fill,center", "top"));
                putClientProperty(FlatClientProperties.STYLE, ""
                                + "background:lighten(@background,7%);"
                                + "arc:20");
                add(panel);
                refreshUI();
        }

        private void refreshUI() {
                repaint();
                revalidate();
        }

        private void getInput() {
                model = controller.getInput(txtAlternativeName, model.getAlternativeId());
        }

        private void handleInsert() {
                if (controller.blankValidation(model)) {
                        notification.fieldBlankNotification();
                        return;
                }

                if (controller.alternativeValidation(model)) {
                        notification.IsnAvailableNotification();
                        return;
                }
                controller.createAlternative(model);
                notification.successCreateNotification();
                changeContent(new ManagementAlternativeView());
        }

        private void handleUpdate() {
                if (controller.blankValidation(model)) {
                        notification.fieldBlankNotification();
                        return;
                }

                if (!oldAlternativeName.equalsIgnoreCase(txtAlternativeName.getText())
                                && controller.alternativeValidation(model)) {
                        notification.IsnAvailableNotification();
                        return;
                }

                controller.updateAlternative(model);
                notification.successUpdateNotification();
                changeContent(new ManagementAlternativeView());
        }

        public void setField(String id) {
                txtAlternativeName.setText(oldAlternativeName);
                model.setAlternativeId(id);
        }

}
