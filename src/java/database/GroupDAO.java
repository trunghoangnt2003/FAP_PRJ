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
import model.Course;
import model.Group;
import model.Semester;

/**
 *
 * @author trung
 */
public class GroupDAO {

    public Group selectIdGroup(int id) {
        Group group = null;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT *"
                    + "  FROM [dbo].[Group]\n"
                    + "  WHERE idGroup = ?";
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idGroup = resultSet.getInt(1);
                String codeGroup = resultSet.getString(2);
                int idCourse = resultSet.getInt(3);
                int idSemester = resultSet.getInt(4);
                CourseDAO courseDAO = new CourseDAO();
                SemesterDAO semesterDAO = new SemesterDAO();
                group = new Group(idGroup, codeGroup, courseDAO.selectById(idCourse),semesterDAO.selectById(idSemester));

            }
            JDBC.closeConnection(connection);
        } catch (Exception e) {
        }
        return group;
    }

    public ArrayList<Group> selectAllByIdStudentAndIdSemester(String idStudent,int idSemester) {
        ArrayList<Group> groups = new ArrayList<Group>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select g.idGroup,g.codeGroup,g.idCourse,g.[idSemester]\n"
                    + "from [Group] g join inGroup i on g.idGroup=i.idGroup\n"
                    + "where i.idStudent = ? AND idSemester=?";
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, idStudent);
            preparedStatement.setInt(2, idSemester);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idGroup = resultSet.getInt(1);
                String codeGroup = resultSet.getString(2);
                int idCourse = resultSet.getInt(3);
                
                CourseDAO courseDAO = new CourseDAO();
                SemesterDAO semesterDAO = new SemesterDAO();
                Group group = new Group(idGroup, codeGroup, courseDAO.selectById(idCourse),semesterDAO.selectById(idSemester));
                groups.add(group);
            }
            JDBC.closeConnection(connection);
        } catch (Exception e) {
        }
        return groups;
    }
    public ArrayList<Group> listCourseRE(String idStudent) {
        ArrayList<Group> list = new ArrayList<>();
        String sql = "with t as (\n"
                + "	select [idCourse],[idSemester]\n"
                + "	from [dbo].[RE_Course]\n"
                + ")\n"
                + "\n"
                + "select c.idCourse,g.idSemester,s.idStudent,sum(s.[value]*p.[percent]) as total,sum(p.[percent]) as [percent]\n"
                + "from Course c join Point p on c.idCourse=p.idCourse\n"
                + "join [Group] g on c.idCourse=g.idCourse\n"
                + "join Score s on s.idPoint=p.idPoint\n"
                + "where s.idStudent=? and c.idCourse not in (\n"
                + "		select [idCourse]\n"
                + "		from t\n"
                + "		where [idCourse] = c.idCourse and [idSemester] = g.idSemester\n"
                + "	)\n"
                + "group by c.idCourse,g.idSemester,s.idStudent\n"
                + "having sum(s.[value]*p.[percent])<5 and sum(p.[percent]) >0.9999\n";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareCall(sql);
            ps.setString(1, idStudent);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                CourseDAO courseDAO = new CourseDAO();
                Course course = courseDAO.selectById(rs.getInt(1));
                Group group = new Group();
                group.setCourse(course);
                SemesterDAO semesterDAO = new SemesterDAO();
                Semester semester = semesterDAO.selectById(rs.getInt(2));
                group.setSemester(semester);
                list.add(group);
            }
            
        } catch (SQLException e) {
        }
        return list;
    }
}
