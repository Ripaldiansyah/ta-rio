package ta_rio.spk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ta_rio.spk.model.SpkModel;
import ta_rio.spk.service.SpkService;
import ta_rio.util.DatabaseConnection;
import ta_rio.util.UserToken;

public class SpkDao implements SpkService {

    private Connection conn;

    public SpkDao() {
        conn = new DatabaseConnection().connect();
    }

    @Override
    public List<SpkModel> getSpkHistory() {
        PreparedStatement stat = null;
        List<SpkModel> historyList = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM spk_history WHERE User_Id = ?";

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, UserToken.userID);
            rs = stat.executeQuery();
            while (rs.next()) {
                SpkModel history = new SpkModel();
                history.setSpkId(rs.getString("spk_id"));
                history.setSpkName(rs.getString("spk_name"));
                history.setCreatedAt(rs.getString("created_at"));
                historyList.add(history);

            }
            return historyList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deleteHistory(SpkModel model) {
        PreparedStatement stat = null;
        String sqlSPK = "DELETE From spk_history WHERE spk_id=?";
        String sqlSPKDetail = "DELETE From spk_detail WHERE spk_id=?";

        try {
            stat = conn.prepareStatement(sqlSPKDetail);
            stat.setString(1, model.getSpkId());
            stat.executeUpdate();

            stat = conn.prepareStatement(sqlSPK);
            stat.setString(1, model.getSpkId());
            stat.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stat != null) {
                try {
                    stat.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Map<Object, Object>> getInfo(SpkModel model) {
        PreparedStatement stat = null;
        List<Map<Object, Object>> rankListMap = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM spk_detail WHERE spk_id = ? ";

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getSpkId());
            rs = stat.executeQuery();
            while (rs.next()) {
                String alternativeName = rs.getString("alternative_name");
                int rank = rs.getInt("alternative_rank");

                Map<Object, Object> rankMap = new LinkedHashMap<>();
                rankMap.put("alternativeName", alternativeName);
                rankMap.put("rank", rank);
                rankListMap.add(rankMap);
            }
            return rankListMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
