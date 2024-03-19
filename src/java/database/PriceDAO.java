/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Price;

/**
 *
 * @author trung
 */
public class PriceDAO {

    public Price selectById(int id) {
        Price price = null;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT *\n"
                    + "from [dbo].[Price]\n"
                    + "where id = ?";
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                price = new Price(id, name, resultSet.getInt(2));
            }
            
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
        }
        return price;
    }
}
