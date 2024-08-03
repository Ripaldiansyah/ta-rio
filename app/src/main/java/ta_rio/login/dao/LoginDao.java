package ta_rio.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ta_rio.login.model.LoginModel;
import ta_rio.login.service.LoginService;
import ta_rio.util.DatabaseConnection;
import ta_rio.util.UserToken;

public class LoginDao implements LoginService {
    private Connection conn;

    public LoginDao() {
        conn = new DatabaseConnection().connect();
    }

    @Override
    public boolean isRegistered(LoginModel model) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE username = ? AND Password = MD5(?)";

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getUsername());
            stat.setString(2, model.getPassword());
            rs = stat.executeQuery();
            if (!rs.next()) {
                return false;
            } else {
                String id = rs.getString("user_id");
                String role = rs.getString("role");
                String fullname = rs.getString("fullname");
                String username = rs.getString("username");
                String address = rs.getString("address");

                UserToken.userID = id;
                UserToken.fullname = fullname;
                UserToken.username = username;
                UserToken.role = role;
                UserToken.address = address;
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
        return true;
    }

}
