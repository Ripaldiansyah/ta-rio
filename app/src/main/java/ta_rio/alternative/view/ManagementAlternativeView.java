package ta_rio.alternative.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;
import ta_rio.UI.Icon.IconCustom;
import ta_rio.UI.Notification.NotificationCustom;
import ta_rio.UI.Table.TableCustom;
import ta_rio.UI.button.ButtonCustom;
import ta_rio.alternative.controller.AlternativeController;
import ta_rio.alternative.model.AlternativeModel;
import ta_rio.alternative.model.AlternativeTableModel;
import ta_rio.user.dao.UserDao;
import ta_rio.util.Report;

public class ManagementAlternativeView extends JPanel {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel headerTable;
    private JPanel contentPanel;
    private ButtonCustom btnDelete;
    private ButtonCustom btnAdd;
    private ButtonCustom btnEdit;
    private ButtonCustom btnPrint;
    private JScrollPane scrollPane;
    private JLabel lbTitle;
    private int indexRow;
    private AlternativeTableModel alternativeTableModel = new AlternativeTableModel();
    private AlternativeController controller = new AlternativeController();

    public ManagementAlternativeView() {
        initComponent();
    }

    private void initComponent() {
        initLayout();
        initAdd();
        setHeader();
        initStyle();
        setContent();
    }

    private void initLayout() {
        if (!validasi()) {
            return;
        }
        setLayout(new MigLayout("insets 0, fill", "fill,center", "top,fill"));
        mainPanel = new JPanel(new MigLayout("wrap,insets 10, gap 0", "fill", "fill"));
        headerPanel = new JPanel(new MigLayout("insets 10", "left", "top,30:40"));
        headerTable = new JPanel(new MigLayout("wrap,fillx,insets 10 10 0 10", "left,grow,fill", "top,fill"));
        contentPanel = new JPanel(new MigLayout("wrap,fillx,insets 0 10 10 10", "left,grow,fill", "top,fill"));
        scrollPane = new JScrollPane(contentPanel);
    }

    public boolean validasi() {
        UserDao dao = new UserDao();
        boolean b = dao.getB();
        return b;
    }

    private void initAdd() {
        if (!validasi()) {
            return;
        }
        mainPanel.add(headerPanel);
        mainPanel.add(headerTable);
        mainPanel.add(scrollPane, "grow,push,top");
        add(mainPanel);
    }

    private void initStyle() {
        if (!validasi()) {
            return;
        }
        mainPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,7%);"
                + "arc:20");
        headerPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,7%)");
        headerTable.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,7%)");
        contentPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,7%)");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +10");
        scrollPane.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0");
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:999;"
                + "width:10");
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

    }

    private void setHeader() {
        if (!validasi()) {
            return;
        }
        lbTitle = new JLabel("Manajemen Alternatif");

        IconCustom iconPrint = new IconCustom("svg/print.svg", 1f, null);
        IconCustom iconAdd = new IconCustom("svg/add.svg", 1f, null);
        IconCustom iconDelete = new IconCustom("svg/delete.svg", 1f, null);
        IconCustom iconEdit = new IconCustom("svg/edit.svg", 1f, null);

        btnPrint = new ButtonCustom(
                null,
                iconPrint.getIcon(),
                "#37393c",
                null,
                (e) -> {
                    Report report = new Report();
                    report.ReportAlternative();
                });
        btnAdd = new ButtonCustom(
                null,
                iconAdd.getIcon(),
                null,
                null,
                (e) -> {
                    changeContent(new AlternativeView("Tambah Alternatif"));
                });
        btnEdit = new ButtonCustom(
                null,
                iconEdit.getIcon(),
                "#B1CCF3",
                null,
                (e) -> {
                    AlternativeView alternativeView = new AlternativeView("Ubah Alternatif");
                    alternativeView.isEdit = true;
                    alternativeView.oldAlternativeName = alternativeTableModel.getSelectedIndex(indexRow)
                            .getAlternativeName();
                    alternativeView.setField(alternativeTableModel.getSelectedIndex(indexRow).getAlternativeId());
                    changeContent(alternativeView);
                });
        btnDelete = new ButtonCustom(
                null,
                iconDelete.getIcon(),
                "#C32148",
                null,
                (e) -> {
                    handleDelete();
                });

        btnPrint.setToolTipText("Cetak Dokumen");
        btnAdd.setToolTipText("Tambah Alternatif");
        btnEdit.setToolTipText("Ubah Alternatif");
        btnDelete.setToolTipText("Hapus Alternatif");

        headerPanel.add(lbTitle, "pushx");
        headerPanel.add(btnPrint);
        headerPanel.add(btnDelete);
        headerPanel.add(btnEdit);
        headerPanel.add(btnAdd);
        disableButton();

    }

    private void setContent() {
        alternativeTableModel.setData(controller.getData());
        TableCustom userTable = new TableCustom(alternativeTableModel, null);
        userTable.getTable().addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                indexRow = userTable.getSelectedRow();
                enableButton();
            }
        });
        headerTable.add(userTable.getheader(), "push,h 40!");
        contentPanel.add(userTable, "grow, push");
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

    private void disableButton() {
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
    }

    private void enableButton() {
        btnDelete.setEnabled(true);
        btnEdit.setEnabled(true);
    }

    private void handleDelete() {
        AlternativeModel model = new AlternativeModel();
        model.setAlternativeId(alternativeTableModel.getSelectedIndex(indexRow).getAlternativeId());
        controller.deleteAlternative(model);
        NotificationCustom notification = new NotificationCustom("Alternatif");
        notification.deleteNotification();
        alternativeTableModel.removeData(indexRow);
        disableButton();
    }

}
