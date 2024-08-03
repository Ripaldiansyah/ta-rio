package ta_rio.spk.controller;

import java.util.List;
import java.util.Map;

import ta_rio.criteria.model.CriteriaModel;
import ta_rio.spk.dao.SpkDao;
import ta_rio.spk.model.SpkModel;

public class SpkController {
    private SpkDao dao;
    SpkModel model = new SpkModel();

    public SpkController() {
        this.dao = new SpkDao();
    }

    public void deleteHistory(SpkModel model) {
        dao.deleteHistory(model);
    }

    public String[] tableHeader() {
        return model.getColumnHeader();
    }

    public List<SpkModel> getData() {
        return dao.getSpkHistory();
    }

    public List<Map<Object, Object>> getInfo(SpkModel model) {
        return dao.getInfo(model);
    }

}
