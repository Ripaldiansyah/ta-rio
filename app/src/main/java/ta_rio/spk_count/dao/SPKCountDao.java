package ta_rio.spk_count.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ta_rio.spk_count.model.SPKCountModel;
import ta_rio.spk_count.service.SPKCountService;
import ta_rio.util.DatabaseConnection;

public class SPKCountDao implements SPKCountService {
    private Connection conn;

    public SPKCountDao() {
        conn = new DatabaseConnection().connect();
    }

    @Override
    public List<String> getColumnName() {
        PreparedStatement stat = null;
        List<String> columnName = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT criteria_name FROM criteria ";
        String name;

        try {
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();
            while (rs.next()) {
                name = rs.getString("criteria_name");
                columnName.add(name);

            }
            return columnName;
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
    public List<List<Object>> getAlternativeName() {
        PreparedStatement stat = null;
        List<List<Object>> columnName = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT alternative_name FROM alternative ";

        try {
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();

            while (rs.next()) {
                List<Object> alternativeNameList = new ArrayList<>();
                Object alternativeName;
                alternativeName = rs.getString("alternative_name");
                alternativeNameList.add(alternativeName);
                columnName.add(alternativeNameList);
            }

            return columnName;
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
    public double getCriteriaWeight(SPKCountModel model) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "SELECT criteria_weight FROM criteria WHERE criteria_name = ? ";

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getCriteriaName());
            rs = stat.executeQuery();

            if (rs.next()) {
                return rs.getDouble("criteria_weight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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
        return 0;
    }

    @Override
    public String getCriteriaType(SPKCountModel model) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "SELECT criteria_type FROM criteria WHERE criteria_name = ? ";

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getCriteriaName());
            rs = stat.executeQuery();

            if (rs.next()) {
                return rs.getString("criteria_type");
            }
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
        return null;
    }

}
