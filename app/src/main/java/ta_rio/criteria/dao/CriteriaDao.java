package ta_rio.criteria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ta_rio.criteria.model.CriteriaModel;
import ta_rio.criteria.service.CriteriaService;
import ta_rio.util.DatabaseConnection;

public class CriteriaDao implements CriteriaService {

    private Connection conn;

    public CriteriaDao() {
        conn = new DatabaseConnection().connect();
    }

    @Override
    public boolean criteriaIsAvailable(CriteriaModel model) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) AS count FROM criteria WHERE criteria_name = ?;";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getCriteriaName());
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
    public void createCriteria(CriteriaModel model) {
        PreparedStatement stat = null;
        String sql = "INSERT INTO criteria (criteria_name,criteria_type,criteria_weight) VALUES (?,?,?)";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getCriteriaName());
            stat.setString(2, model.getCriteriaType());
            stat.setDouble(3, model.getCriteriaWeight());
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
    public void updateCriteria(CriteriaModel model) {
        PreparedStatement stat = null;
        String sql = "UPDATE criteria SET criteria_name= ?,criteria_type = ? ,criteria_weight=? WHERE criteria_id =?";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getCriteriaName());
            stat.setString(2, model.getCriteriaType());
            stat.setDouble(3, model.getCriteriaWeight());
            stat.setString(4, model.getCriteriaId());

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
    public void deleteCriteria(CriteriaModel model) {
        PreparedStatement stat = null;
        String sql = "DELETE From criteria where criteria_id=?";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getCriteriaId());
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
    public List<CriteriaModel> getCriteria() {
        PreparedStatement stat = null;
        List<CriteriaModel> criterias = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM criteria ";

        try {
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();
            while (rs.next()) {
                CriteriaModel criteria = new CriteriaModel();
                criteria.setCriteriaId(rs.getString("criteria_id"));
                criteria.setCriteriaName(rs.getString("criteria_name"));
                criteria.setCriteriaWeight(rs.getDouble("criteria_weight"));
                criteria.setCriteriaType(rs.getString("criteria_type"));

                criterias.add(criteria);

            }
            return criterias;
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
