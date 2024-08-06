package ta_rio.spk_result.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import ta_rio.spk_result.model.SpkResultModel;
import ta_rio.spk_result.service.SpkResultService;
import ta_rio.spk_result.view.SpkResultView;
import ta_rio.util.DatabaseConnection;
import ta_rio.util.UserToken;
import java.sql.Timestamp;

public class SpkResultDao implements SpkResultService {

    private Connection conn;
    private int SPKid;

    public SpkResultDao() {
        conn = new DatabaseConnection().connect();
    }

    @Override
    public void saveSpk(SpkResultModel model) {
        PreparedStatement stat = null;
        String sql = "INSERT INTO spk_history (user_id, spk_name, created_at) VALUES (?,?,?)";
        try {
            stat = conn.prepareStatement(sql, new String[] { "id_spk" });
            stat.setString(1, UserToken.userID);
            stat.setString(2, model.getSpkSaveName());
            stat.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stat.executeUpdate();

            ResultSet generatedKeys = stat.getGeneratedKeys();

            if (generatedKeys.next()) {
                SPKid = generatedKeys.getInt(1);
                model.setSpkId(String.valueOf(SPKid));
                detailSpk(model);
                SpkResultView.idSPK = model.getSpkId();
            }

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
    public void detailSpk(SpkResultModel model) {
        PreparedStatement stat = null;
        String sql = "INSERT INTO spk_detail (spk_id, alternative_name, alternative_rank) VALUES (?,?,?)";
        try {
            stat = conn.prepareStatement(sql);

            for (Map<Object, Object> rank : model.getRankListMap()) {

                stat.setString(1, model.getSpkId());
                stat.setString(2, rank.get("alternativeName").toString());
                stat.setString(3, rank.get("rank").toString());
                stat.executeUpdate();

            }

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

}
