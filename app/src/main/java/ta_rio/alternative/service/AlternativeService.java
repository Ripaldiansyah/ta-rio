package ta_rio.alternative.service;

import java.util.List;

import ta_rio.alternative.model.AlternativeModel;

public interface AlternativeService {

    boolean alternativeIsAvailable(AlternativeModel model);

    void createAlternative(AlternativeModel model);

    void updateAlternative(AlternativeModel model);

    void deleteAlternative(AlternativeModel model);

    List<AlternativeModel> getAlternative();
}
