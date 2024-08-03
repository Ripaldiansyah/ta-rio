package ta_rio.spk_count.service;

import java.util.List;

import ta_rio.spk_count.model.SPKCountModel;

public interface SPKCountService {

    List<String> getColumnName();

    List<List<Object>> getAlternativeName();

    double getCriteriaWeight(SPKCountModel model);

    String getCriteriaType(SPKCountModel model);

}
