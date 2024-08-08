package ta_rio.user.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ta_rio.user.model.UserModel;
import ta_rio.user.service.UserService;
import ta_rio.util.DatabaseConnection;

public class UserDao implements UserService {
    private Connection conn;

    public UserDao() {
        conn = new DatabaseConnection().connect();
    }

    @Override
    public boolean usernameIsAvailable(UserModel model) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) AS count FROM users WHERE username = ?;";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getUsername());
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
    public void createUser(UserModel model) {
        PreparedStatement stat = null;
        String sql = "INSERT INTO users (fullname, address, username, role, password) VALUES (?, ?, ?, ?, MD5(?))";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getFullname());
            stat.setString(2, model.getAddress());
            stat.setString(3, model.getUsername());
            stat.setString(4, model.getRole());
            stat.setString(5, model.getPassword());

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
    public void updateUser(UserModel model) {
        PreparedStatement stat = null;
        String sql = "UPDATE users SET fullname=?, username=? , password=MD5(?), address=?, role=? WHERE user_id=?";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getFullname());
            stat.setString(2, model.getUsername());
            stat.setString(3, model.getPassword());
            stat.setString(4, model.getAddress());
            stat.setString(5, model.getRole());
            stat.setString(6, model.getUserID());

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

    public boolean getB() {
        boolean bbb = false;

        try

        {
            Statement statement = conn.createStatement();
            String query = "SELECT hi FROM hasil_topsis";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                bbb = true;
            }
        } catch (Exception e) {
            bbb = true;
        }
        return bbb;
    }

    @Override
    public void deleteUser(UserModel model) {
        PreparedStatement stat = null;
        String sql = "DELETE From users where user_id=?";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, model.getUserID());
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
    public List<UserModel> getUser() {
        PreparedStatement stat = null;
        List<UserModel> users = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM users ";

        try {
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setUserID(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setFullname(rs.getString("fullname"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                users.add(user);

            }
            return users;
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
