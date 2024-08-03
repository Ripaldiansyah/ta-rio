package ta_rio.spk_result.model;

import java.util.List;
import java.util.Map;

public class SpkResultModel {

    String spkId;
    String createdAt;
    String userId;
    List<Map<Object, Object>> rankListMap;

    private final String[] columnHeader = {

            "Keputusan Terbaik",
            "Tanggal Perhitungan"

    };

    public List<Map<Object, Object>> getRankListMap() {
        return rankListMap;
    }

    public void setRankListMap(List<Map<Object, Object>> rankListMap) {
        this.rankListMap = rankListMap;
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