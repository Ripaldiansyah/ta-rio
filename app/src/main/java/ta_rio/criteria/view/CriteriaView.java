package ta_rio.criteria.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.text.PlainDocument;
import net.miginfocom.swing.MigLayout;
import ta_rio.UI.Notification.NotificationCustom;
import ta_rio.UI.TextField.TextFieldCustom;
import ta_rio.UI.button.ButtonCustom;
import ta_rio.alternative.view.ManagementAlternativeView;
import ta_rio.criteria.controller.CriteriaController;
import ta_rio.criteria.model.CriteriaModel;
import ta_rio.user.model.UserModel;
import ta_rio.util.DoubleFilter;

public class CriteriaView extends JPanel {
        private JPanel mainPanel;
        private JPanel contentPanel;
        private JLabel lbTitle;
        private TextFieldCustom txtCriteriaName;
        private TextFieldCustom txtCriteriaWeight;
        private ButtonCustom btnCancel;
        private ButtonCustom btnSave;
        private String title;
        private JComboBox<String> comboCriteriaType;
        CriteriaModel model = new CriteriaModel();
        CriteriaController controller = new CriteriaController();
        private NotificationCustom notification = new NotificationCustom("Kriteria");
        public String oldCriteriaName;
        public boolean isUpdate = false;

        public CriteriaView(
                        String title) {
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
                String[] type = { "Benefit", "Cost", };
                txtCriteriaName = new TextFieldCustom(
                                "Masukan Nama Kriteria",
                                null,
                                false);
                txtCriteriaWeight = new TextFieldCustom(
                                "Masukan Bobot Kriteria",
                                null,
                                false);
                PlainDocument doc = (PlainDocument) txtCriteriaWeight.getDocument();
                doc.setDocumentFilter(new DoubleFilter());

                comboCriteriaType = new JComboBox<String>(type);
                comboCriteriaType.putClientProperty(FlatClientProperties.STYLE, ""
                                + "arc:20");
                btnCancel = new ButtonCustom(
                                "Batal",
                                null,
                                "#37393c",
                                "#fff",
                                (e) -> {
                                        changeContent(new ManagementCriteriaView());
                                });
                btnSave = new ButtonCustom(
                                "Simpan",
                                null,
                                "#2f7ab1",
                                "#fff",
                                (e) -> {
                                        getInput();
                                        if (isUpdate) {
                                                handleUpdate();
                                        } else {
                                                handleInsert();
                                        }
                                });

                contentPanel.add(lbTitle);
                contentPanel.add(new JSeparator(), "gapy 5");
                contentPanel.add(new JLabel("Nama Kriteria"), "gapy 5");
                contentPanel.add(txtCriteriaName);
                contentPanel.add(new JLabel("Bobot Kriteria"), "gapy 5");
                contentPanel.add(txtCriteriaWeight);
                contentPanel.add(new JLabel("Tipe Kriteria"), "gapy 5");
                contentPanel.add(comboCriteriaType, "h 50!");
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
                model = controller.getInput(txtCriteriaName, txtCriteriaWeight, comboCriteriaType,
                                model.getCriteriaId());
        }

        private void handleInsert() {
                if (controller.blankValidation(model)) {
                        notification.fieldBlankNotification();
                        return;
                }

                if (controller.criteriaValidation(model)) {
                        notification.IsnAvailableNotification();
                        return;
                }
                controller.createCriteria(model);
                notification.successCreateNotification();
                changeContent(new ManagementCriteriaView());
        }

        private void handleUpdate() {
                if (controller.blankValidation(model)) {
                        notification.fieldBlankNotification();
                        return;
                }

                if (!oldCriteriaName.equalsIgnoreCase(txtCriteriaName.getText())
                                && controller.criteriaValidation(model)) {
                        notification.IsnAvailableNotification();
                        return;
                }

                controller.updateCriteria(model);
                notification.successUpdateNotification();
                changeContent(new ManagementCriteriaView());
        }

        public void setField(CriteriaModel criteriaModel) {
                txtCriteriaName.setText(criteriaModel.getCriteriaName());
                txtCriteriaWeight.setText(String.valueOf(criteriaModel.getCriteriaWeight()));
                comboCriteriaType.setSelectedItem(criteriaModel.getCriteriaType());
                model.setCriteriaId(criteriaModel.getCriteriaId());
        }

}
