package pl.coderslab.UserDao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.utils.DbUtil;

import java.sql.*;

public class UserDao {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)"; // the query creating the user


    private static final String READ_USER_QUERY =
            "SELECT * FROM users where id = ?"; // the query loading all data for user ID

    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ? where id = ?"; // the query update user data

    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?"; // the query delete user

    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users"; // the query finds the user

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLIntegrityConstraintViolationException sqli) {
            System.out.println(sqli.getMessage());
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        }

    public User read(int userId) {

        try (Connection conn = DbUtil.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                User user = new User();

                user.setId(resultSet.getInt("id"));

                user.setUserName(resultSet.getString("username"));

                user.setEmail(resultSet.getString("email"));

                user.setPassword(resultSet.getString("password"));

                return user;

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }

}
