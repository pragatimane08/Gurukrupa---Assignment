//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class UserRowMapper implements RowMapper<User> {
//    @Override
//    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//        User user = new User();
//        user.setId(rs.getInt("id")); // Use getInt since id is of type int
//        user.setEmail(rs.getString("email")); // Corrected field
//        user.setPassword(rs.getString("password")); // Corrected field
//        user.setPhoneNo(rs.getString("phone_no")); // Corrected field, adjust as per your DB
//        user.setAddress(rs.getString("addr")); // Corrected field
//        user.setRole(User.Role.valueOf(rs.getString("role"))); // Adjust based on your Role enum
//        return user;
//    }
//}
