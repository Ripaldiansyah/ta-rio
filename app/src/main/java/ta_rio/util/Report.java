package ta_rio.util;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Report {
    public Report() {
        conn = new DatabaseConnection().connect();
    }

    public void ReportAlternative() {

        try {
            InputStream reportStream = getClass().getResourceAsStream("/jasper/ReportAlternative.jasper");
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("id"));

            JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, conn);
            JasperViewer.viewReport(print, false);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage());
        }
    }

    public void ReportCriteria() {

        try {
            InputStream reportStream = getClass().getResourceAsStream("/jasper/ReportCriteria.jasper");
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("id"));

            JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, conn);
            JasperViewer.viewReport(print, false);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage());
        }
    }

    public void ReportUser() {

        try {
            InputStream reportStream = getClass().getResourceAsStream("/jasper/ReportUser.jasper");
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("id"));
            JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, conn);
            JasperViewer.viewReport(print, false);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage());
        }
    }

    public void ReportSPKById() {

        try {
            InputStream reportStream = getClass().getResourceAsStream("/jasper/ReportSPKAllFilterId.jasper");
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("id"));
            parameters.put("user_id", UserToken.userID);

            JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, conn);
            JasperViewer.viewReport(print, false);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage());
        }
    }

    public void ReportSPKResult(String id) {

        try {
            InputStream reportStream = getClass().getResourceAsStream("/jasper/ReportSPKResult.jasper");
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("id_spk", id);
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("id"));

            JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, conn);
            JasperViewer.viewReport(print, false);

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage());
        }
    }

    private Connection conn;
    public static List<Map<Object, Object>> rankListMap = new ArrayList<>();
}
