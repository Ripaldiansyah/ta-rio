package ta_rio.spk.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;
import ta_rio.UI.Icon.IconCustom;
import ta_rio.UI.Notification.NotificationCustom;
import ta_rio.UI.Table.TableCustom;
import ta_rio.UI.button.ButtonCustom;
import ta_rio.spk.controller.SpkController;
import ta_rio.spk.model.SpkModel;
import ta_rio.spk.model.SpkTableModel;
import ta_rio.spk_count.view.SPKCountView;
import ta_rio.spk_result.model.SpkResultModel;
import ta_rio.spk_result.view.SpkResultView;
import ta_rio.user.dao.UserDao;
import ta_rio.util.Report;

public class ManagementSpkView extends JPanel {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel headerTable;
    private JPanel contentPanel;
    private ButtonCustom btnDelete;
    private ButtonCustom btnAdd;
    private ButtonCustom btnInfo;
    private ButtonCustom btnPrint;
    private JScrollPane scrollPane;
    private JLabel lbTitle;
    private int indexRow;
    private SpkTableModel spkTableModel = new SpkTableModel();
    private SpkController controller = new SpkController();

    public ManagementSpkView() {
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
        setLayout(new MigLayout("insets 0, fill", "fill,center", "top,fill"));
        mainPanel = new JPanel(new MigLayout("wrap,insets 10, gap 0", "fill", "fill"));
        headerPanel = new JPanel(new MigLayout("insets 10", "left", "top,30:40"));
        headerTable = new JPanel(new MigLayout("wrap,fillx,insets 10 10 0 10", "left,grow,fill", "top,fill"));
        contentPanel = new JPanel(new MigLayout("wrap,fillx,insets 0 10 10 10", "left,grow,fill", "top,fill"));
        scrollPane = new JScrollPane(contentPanel);
    }

    private void initAdd() {
        mainPanel.add(headerPanel);
        mainPanel.add(headerTable);
        mainPanel.add(scrollPane, "grow,push,top");
        add(mainPanel);
    }

    private void initStyle() {

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
        lbTitle = new JLabel("Manajemen Keputusan");

        IconCustom iconPrint = new IconCustom("svg/print.svg", 1f, null);
        IconCustom iconAdd = new IconCustom("svg/add.svg", 1f, null);
        IconCustom iconDelete = new IconCustom("svg/delete.svg", 1f, null);
        IconCustom iconInfo = new IconCustom("svg/info.svg", 1f, null);

        if (!validasi()) {
            return;
        }
        btnPrint = new ButtonCustom(
                null,
                iconPrint.getIcon(),
                "#37393c",
                null,
                (e) -> {
                    Report report = new Report();
                    report.ReportSPKById();
                });
        btnAdd = new ButtonCustom(
                null,
                iconAdd.getIcon(),
                null,
                null,
                (e) -> {
                    changeContent(new SPKCountView());
                });
        btnInfo = new ButtonCustom(
                null,
                iconInfo.getIcon(),
                "#B1CCF3",
                null,
                (e) -> {
                    handleInfo();
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
        btnAdd.setToolTipText("Tambah Keputusan");
        btnInfo.setToolTipText("Detail Keputusan");
        btnDelete.setToolTipText("Hapus Keputusan");

        headerPanel.add(lbTitle, "pushx");
        // headerPanel.add(btnPrint);
        headerPanel.add(btnDelete);
        headerPanel.add(btnInfo);
        headerPanel.add(btnAdd);
        disableButton();

    }

    private void setContent() {
        spkTableModel.setData(controller.getData());
        TableCustom criteriaTable = new TableCustom(spkTableModel, null);
        criteriaTable.getTable().addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                indexRow = criteriaTable.getSelectedRow();
                enableButton();
            }
        });
        headerTable.add(criteriaTable.getheader(), "push,h 40!");
        contentPanel.add(criteriaTable, "grow, push");
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
        btnInfo.setEnabled(false);
    }

    private void enableButton() {
        btnDelete.setEnabled(true);
        btnInfo.setEnabled(true);
    }

    private void handleDelete() {
        SpkModel model = new SpkModel();
        model.setSpkId(spkTableModel.getSelectedIndex(indexRow).getSpkId());
        controller.deleteHistory(model);
        NotificationCustom notification = new NotificationCustom("Keputusan");
        notification.deleteNotification();
        spkTableModel.removeData(indexRow);
        disableButton();
    }

    public boolean validasi() {
        UserDao dao = new UserDao();
        boolean b = dao.getB();
        return b;
    }

    private void handleInfo() {
        SpkResultModel resultModel = new SpkResultModel();
        SpkModel model = new SpkModel();
        model.setSpkId(spkTableModel.getSelectedIndex(indexRow).getSpkId());
        List<Map<Object, Object>> rankListMap = controller.getInfo(model);
        resultModel.setRankListMap(rankListMap);
        SpkResultView resultView = new SpkResultView(resultModel);
        SpkResultView.idSPK = model.getSpkId();
        changeContent(resultView);

    }

}
