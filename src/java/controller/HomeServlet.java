/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import database.PaymentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import model.User;

/**
 *
 * @author trung
 */
public class HomeServlet extends AuthenticationServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        String url = "";
        if(user.getEmail().contains("fpt")){
            url = "StudentView/HomeOfStudent.jsp";
        }else {
            url = "LecturersView/Home.jsp";
        }
        req.getRequestDispatcher(url).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        String url = "";
        if(user.getEmail().contains("fpt")){
            url = "StudentView/HomeOfStudent.jsp";
            PaymentDAO paymentDAO = new PaymentDAO();
            long money = paymentDAO.getMoneyByIdStudent(user.getId());
            Locale locale = new Locale("vi","VN");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            req.setAttribute("money", numberFormat.format(money));
        }else {
            url = "LecturersView/Home.jsp";
        }
        req.getRequestDispatcher(url).forward(req, resp);
        
    }
    
    
    
    
}
