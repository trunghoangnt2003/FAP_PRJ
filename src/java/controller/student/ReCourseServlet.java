/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.student;

import controller.AuthenticationServlet;
import database.CourseDAO;
import database.GroupDAO;
import database.PaymentDAO;
import database.PriceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import model.Course;
import model.Group;
import model.Price;
import model.User;

/**
 *
 * @author trung
 */
public class ReCourseServlet extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        String course = req.getParameter("course");
        PaymentDAO paymentDAO = new PaymentDAO();
        long money = paymentDAO.getMoneyByIdStudent(user.getId());
        if (course != null) {
            int idCourse = Integer.parseInt(course);
            int idSemester = Integer.parseInt(req.getParameter("semester"));
            CourseDAO courseDAO = new CourseDAO();
            PriceDAO priceDAO = new PriceDAO();
            Price price = priceDAO.selectById(1);
            int result = 0;
            if (money >= price.getPrice()) {
                result = courseDAO.insertCourse(idCourse, idSemester, 1);
                if (result != 0) {
                    paymentDAO.insertPayment("GD_" + idCourse + "_" + idSemester + "_" + user.getId(), user.getId(), 0 - price.getPrice());
                    req.setAttribute("error", "Giao dịch thành công !");
                }
            } else {
                req.setAttribute("error", "Không đủ số dư !");
            }
            System.out.println("Đăng kí học lại : " + result);
        }
        doGet(req, resp, user);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp,
             User user) throws ServletException, IOException {
        
        PaymentDAO paymentDAO = new PaymentDAO();
        long money = paymentDAO.getMoneyByIdStudent(user.getId());
        Locale locale = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);

        GroupDAO groupDAO = new GroupDAO();
        ArrayList<Group> list = groupDAO.listCourseRE(user.getId());
        PriceDAO priceDAO = new PriceDAO();
        Price price = priceDAO.selectById(1);
        req.setAttribute("price", numberFormat.format(price.getPrice()));
        req.setAttribute("list", list);
        req.setAttribute("sodu", numberFormat.format(money));
        req.getRequestDispatcher("../StudentView/recourse.jsp").forward(req, resp);
    }

}
