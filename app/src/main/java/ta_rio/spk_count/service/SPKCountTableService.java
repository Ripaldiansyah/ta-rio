package ta_rio.spk_count.service;

import java.util.List;

import ta_rio.spk_count.model.SPKCountModel;

public interface SPKCountTableService {
    void setData(List<List<Object>> criteria);

    void removeData(int index);

    void clear();

    Object getSelectedIndex(int index);
}
