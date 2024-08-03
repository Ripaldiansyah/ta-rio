package ta_rio.spk.service;

import java.util.List;
import java.util.Map;

import ta_rio.spk.model.SpkModel;
import ta_rio.spk_result.model.SpkResultModel;

public interface SpkService {

    List<SpkModel> getSpkHistory();

    void deleteHistory(SpkModel model);

    List<Map<Object, Object>> getInfo(SpkModel model);

}
