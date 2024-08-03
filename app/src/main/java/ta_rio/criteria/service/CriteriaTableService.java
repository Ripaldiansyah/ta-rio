package ta_rio.criteria.service;

import java.util.List;

import ta_rio.criteria.model.CriteriaModel;

public interface CriteriaTableService {
    void setData(List<CriteriaModel> criteria);

    void removeData(int index);

    void clear();

    CriteriaModel getSelectedIndex(int index);
}
