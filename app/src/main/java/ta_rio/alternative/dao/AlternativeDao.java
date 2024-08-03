package ta_rio.alternative.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ta_rio.alternative.model.AlternativeModel;
import ta_rio.alternative.service.AlternativeService;
import ta_rio.user.model.UserModel;
import ta_rio.util.DatabaseConnection;

public class AlternativeDao implements AlternativeService {

    private Connection conn;

    public AlternativeDao() {
        conn = new DatabaseConnection().connect();
    }

    @Override
    public boolean alternativeIsAvailable(AlternativeModel model) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) AS count FROM alternative WHERE alternative_name = ?;";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getAlternativeName());
            rs = stat.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        return false;
    }

    @Override
    public void createAlternative(AlternativeModel model) {
        PreparedStatement stat = null;
        String sql = "INSERT INTO alternative (alternative_name) VALUES (?)";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getAlternativeName());
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
    public void updateAlternative(AlternativeModel model) {
        PreparedStatement stat = null;
        String sql = "UPDATE alternative SET alternative_name= ? WHERE alternative_id =?";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getAlternativeName());
            stat.setString(2, model.getAlternativeId());
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
    public void deleteAlternative(AlternativeModel model) {
        PreparedStatement stat = null;
        String sql = "DELETE From alternative where alternative_id=?";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getAlternativeId());
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
    public List<AlternativeModel> getAlternative() {
        PreparedStatement stat = null;
        List<AlternativeModel> alternatives = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM alternative ";

        try {
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();
            while (rs.next()) {
                AlternativeModel alternative = new AlternativeModel();
                alternative.setAlternativeId(rs.getString("alternative_id"));
                alternative.setAlternativeName(rs.getString("alternative_name"));

                alternatives.add(alternative);

            }
            return alternatives;
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
