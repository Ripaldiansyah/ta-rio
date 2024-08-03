package ta_rio.criteria.model;

public class CriteriaModel {
    String criteriaId;
    String criteriaName;
    double criteriaWeight;
    String criteriaType;

    private final String[] columnHeader = {

            "Nama Kriteria",
            "Bobot Kriteria",
            "Jenis Kriteria"
    };

    public String getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(String criteriaId) {
        this.criteriaId = criteriaId;
    }

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public double getCriteriaWeight() {
        return criteriaWeight;
    }

    public void setCriteriaWeight(double criteriaWeight) {
        this.criteriaWeight = criteriaWeight;
    }

    public String getCriteriaType() {
        return criteriaType;
    }

    public void setCriteriaType(String criteriaType) {
        this.criteriaType = criteriaType;
    }

    public String[] getColumnHeader() {
        return columnHeader;
    }

}
