package ta_rio.spk.model;

public class SpkModel {
    String spkId;
    String createdAt;
    String userId;
    String SpkName;

    private final String[] columnHeader = {

            "Nama SPK",
            "Tanggal Perhitungan"

    };

    public String getSpkName() {
        return SpkName;
    }

    public void setSpkName(String spkName) {
        SpkName = spkName;
    }

    public String getSpkId() {
        return spkId;
    }

    public void setSpkId(String spkId) {
        this.spkId = spkId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String[] getColumnHeader() {
        return columnHeader;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
