/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author trung
 */
public class PaymentDAO {

    public long getMoneyByIdStudent(String idStudent) {
        long result = 0;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select sum(money)\n"
                    + "from Payment \n"
                    + "where idStudent = ?";
            PreparedStatement ps = connection.prepareCall(sql);
            ps.setString(1, idStudent);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getLong(1);
            }
            JDBC.closeConnection(connection);
        } catch (Exception e) {

        }
        return result;
    }

    public int insertPayment(String id, String idStudent, long money) {
        int result = 0;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Payment]\n"
                    + "           ([id]\n"
                    + "           ,[idStudent]\n"
                    + "           ,[money])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement ps = connection.prepareCall(sql);
            ps.setString(1, id);
            ps.setString(2, idStudent);
            ps.setLong(3, money);
            result = ps.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (Exception e) {

        }
        return result;
    }
}
