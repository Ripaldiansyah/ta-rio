package ta_rio.spk_count.controller;

import java.util.List;

import ta_rio.spk_count.dao.SPKCountDao;
import ta_rio.spk_count.model.SPKCountModel;

public class SPKCountController {
    private SPKCountDao dao;
    SPKCountModel model = new SPKCountModel();

    public SPKCountController() {
        this.dao = new SPKCountDao();
    }

    public String[] tableHeader() {
        List<String> columnName = dao.getColumnName();
        String[] criteriaName = new String[columnName.size() + 1];
        criteriaName[0] = "Nama Alternatif";
        for (int i = 0; i < columnName.size(); i++) {
            criteriaName[i + 1] = columnName.get(i).toString();
        }
        return criteriaName;
    }

    public List<List<Object>> getData() {
        return dao.getAlternativeName();
    }

    public double getCriteriaWeight(SPKCountModel model) {
        return dao.getCriteriaWeight(model);
    }

    public String getCriteriaType(SPKCountModel model) {
        return dao.getCriteriaType(model);
    }
}
