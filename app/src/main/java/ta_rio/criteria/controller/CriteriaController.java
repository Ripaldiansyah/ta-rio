package ta_rio.criteria.controller;

import java.util.List;

import javax.swing.JComboBox;

import ta_rio.UI.TextField.TextFieldCustom;
import ta_rio.criteria.dao.CriteriaDao;
import ta_rio.criteria.model.CriteriaModel;

public class CriteriaController {
    private CriteriaDao dao;
    CriteriaModel model = new CriteriaModel();

    public CriteriaController() {
        this.dao = new CriteriaDao();
    }

    public List<CriteriaModel> getData() {
        return dao.getCriteria();
    }

    public void deletecriteria(CriteriaModel model) {
        dao.deleteCriteria(model);
    }

    public String[] tableHeader() {
        return model.getColumnHeader();
    }

    public void createCriteria(CriteriaModel model) {
        dao.createCriteria(model);
    }

    public void updateCriteria(CriteriaModel model) {
        dao.updateCriteria(model);
    }

    public CriteriaModel getInput(
            TextFieldCustom txtCriteriaName,
            TextFieldCustom txtCriteriaWeight,
            JComboBox<String> comboCriteriaType,
            String id) {
        CriteriaModel model = new CriteriaModel();

        model.setCriteriaName(txtCriteriaName.getText());
        model.setCriteriaWeight(Double.parseDouble(txtCriteriaWeight.getText()));
        model.setCriteriaType(comboCriteriaType.getSelectedItem().toString());
        model.setCriteriaId(id);

        return model;

    }

    public boolean blankValidation(CriteriaModel model) {
        boolean criteriaIsBlank = model.getCriteriaName().isEmpty();
        return criteriaIsBlank;
    }

    public boolean criteriaValidation(CriteriaModel model) {
        return dao.criteriaIsAvailable(model);
    }
}
