package ta_rio.spk_result.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;
import ta_rio.UI.Icon.IconCustom;
import ta_rio.UI.Table.TableCustom;
import ta_rio.UI.button.ButtonCustom;
import ta_rio.spk.view.ManagementSpkView;
import ta_rio.spk_result.controller.SpkResultController;
import ta_rio.spk_result.model.SpkResultModel;
import ta_rio.spk_result.model.SpkResultTableModel;
import ta_rio.user.dao.UserDao;
import ta_rio.util.Report;

public class SpkResultView extends JPanel {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel headerTable;
    private JPanel contentPanel;
    private ButtonCustom btnPrint;
    private ButtonCustom btnBack;
    private JScrollPane scrollPane;
    private JLabel lbTitle;
    private SpkResultTableModel resultTableModel = new SpkResultTableModel();
    private SpkResultController controller = new SpkResultController();
    public static String idSPK;
    private SpkResultModel model = new SpkResultModel();

    public SpkResultView(SpkResultModel model) {
        this.model = model;
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
        if (validasi()) {
            return;
        }
        lbTitle = new JLabel("Hasil Keputusan");

        IconCustom iconPrint = new IconCustom("svg/print.svg", 1f, null);
        btnPrint = new ButtonCustom(
                null,
                iconPrint.getIcon(),
                "#37393c",
                null,
                (e) -> {
                    Report report = new Report();
                    report.ReportSPKResult(idSPK);
                });
        btnBack = new ButtonCustom(
                "Kembali",
                null,
                "#f2f2f2",
                "#000",
                (e) -> {
                    changeContent(new ManagementSpkView());
                });

        btnPrint.setToolTipText("Cetak Dokumen");

        headerPanel.add(lbTitle, "pushx");
        headerPanel.add(btnBack, "h 40!, w 100!");
        headerPanel.add(btnPrint);

    }

    public void changeContent(JPanel panel) {
        removeAll();
        setLayout(new MigLayout("insets 0, fill", "fill,center", "top"));
        add(panel);
        refreshUI();
    }

    public void refreshUI() {
        repaint();
        revalidate();
    }

    public boolean validasi() {
        UserDao dao = new UserDao();
        boolean b = dao.getB();
        return b;
    }

    private void setContent() {
        resultTableModel.setData(controller.getData(model));
        TableCustom resultTable = new TableCustom(resultTableModel, null);
        headerTable.add(resultTable.getheader(), "push,h 40!");
        contentPanel.add(resultTable, "grow, push");
    }

}
