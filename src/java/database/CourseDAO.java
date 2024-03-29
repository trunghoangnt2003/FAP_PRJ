/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author trung
 */
public class CourseDAO {

    public Course selectById(int id) {
        Course course = null;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select *\n"
                    + "from [Course]\n"
                    + "Where idCourse = ?";
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idCourse = resultSet.getInt(1);
                String codeCourse = resultSet.getString(2);
                String nameCourse = resultSet.getString(3);
                int number = resultSet.getInt(4);
                course = new Course(idCourse, codeCourse, nameCourse, number);
            }
            JDBC.closeConnection(connection);
        } catch (Exception e) {
        }
        return course;
    }

    public int insertCourse(int cou, int ses, int pri) {
        int result = 0;
        String sql = "INSERT INTO [dbo].[RE_Course]\n"
                + "           ([idCourse]\n"
                + "           ,[idSemester]\n"
                + "           ,[idPrice])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareCall(sql);
            ps.setInt(1, cou);
            ps.setInt(2, ses);

            ps.setInt(3, pri);
            result = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return result;
    }
}
