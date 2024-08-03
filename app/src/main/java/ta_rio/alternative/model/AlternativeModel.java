package ta_rio.alternative.model;

public class AlternativeModel {

    String alternativeName;
    String alternativeId;

    private final String[] columnHeader = {

            "ID Alternatif",
            "Nama Alternatif"
    };

    public String[] getColumnHeader() {
        return columnHeader;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    public void setAlternativeName(String alternativeName) {
        this.alternativeName = alternativeName;
    }

    public String getAlternativeId() {
        return alternativeId;
    }

    public void setAlternativeId(String alternativeId) {
        this.alternativeId = alternativeId;
    }

}
