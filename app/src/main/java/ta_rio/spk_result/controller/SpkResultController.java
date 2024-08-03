package ta_rio.spk_result.controller;

import java.util.List;
import java.util.Map;

import ta_rio.spk_result.dao.SpkResultDao;
import ta_rio.spk_result.model.SpkResultModel;

public class SpkResultController {

    private SpkResultDao dao;
    SpkResultModel model = new SpkResultModel();

    public SpkResultController() {
        this.dao = new SpkResultDao();
    }

    public void saveSpk(SpkResultModel model) {
        dao.saveSpk(model);
    }

    public List<Map<Object, Object>> getData(SpkResultModel model) {
        return model.getRankListMap();
    }

}
