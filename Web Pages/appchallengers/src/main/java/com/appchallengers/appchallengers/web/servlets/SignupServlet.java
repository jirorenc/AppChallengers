package com.appchallengers.appchallengers.web.servlets;

import com.appchallengers.appchallengers.dao.dao.UserDao;
import com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl;
import com.appchallengers.appchallengers.model.entity.Users;
import com.appchallengers.appchallengers.util.Util;
import com.appchallengers.appchallengers.web.bussiness.BussinessLogin;

import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
;


@WebServlet(name = "SignupServlet", urlPatterns = {"/SignupServlet"})
public class SignupServlet extends HttpServlet {
 Users users= new Users();
 UserDao userDao=new UserDaoImpl();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        BussinessLogin bl= new BussinessLogin();
        try{

            String fullname=request.getParameter("fullname");
            String email=request.getParameter("email");
            String password= request.getParameter("password");
            String country=request.getParameter("country");
            Timestamp currentTimestamp=new Timestamp(Calendar.getInstance(Locale.US).getTime().getTime());
            if(!(fullname==null || email==null || password==null || country==null)){
                Long status =userDao.checkEmail(email);
                if (status==0){
                    users.setFullName(fullname);
                    users.setEmail(email);
                    users.setPasswordHash(Util.hashMD5(password));
                    users.setCountry(country);
                    users.setPasswordSalt(Util.hashMD5(currentTimestamp.toString()+password));
                    users.setActive(Users.Active.NOT_CONFİRMED);
                    users.setCreateDate(currentTimestamp);
                    users.setUpdateDate(currentTimestamp);
                    if(bl.signupUser(users)){
                        HttpSession session = request.getSession();
                        session.setAttribute("username", email);
                        session.setAttribute("active",Users.Active.NOT_CONFİRMED.ordinal());
                        Cookie cookie= new Cookie("user",users.getEmail());
                        cookie.setMaxAge(60*60*24);
                        response.addCookie(cookie);
                        response.sendRedirect("confirmmail.jsp");

                    }else{
                        response.sendRedirect("errorpage.jsp");
                    }
                }else if(status==1){
                    //  bu mail adresine ait bir hesap zaten bulunmaktadır.
                    request.setAttribute("signup_message", "Bu mail adresine ait bir hesap zaten bulunmaktadır.");
                    request.getRequestDispatcher("signup.jsp").forward(request, response);
                }
               // System.out.println(user.getFullname()+user.getEmail()+user.getPassword()+user.getCountry());
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
