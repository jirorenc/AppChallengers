package com.appchallengers.appchallengers.web.servlets;

import com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl;
import com.appchallengers.appchallengers.dao.dao.UserDao;
import com.appchallengers.appchallengers.web.bussiness.BussinessLogin;

import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;




@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    UserDao userDao= new UserDaoImpl();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");


        try{
            String id=request.getParameter("id");
            String hash=request.getParameter("hash");
            // getting parameters from login page using servlet
            String user_name= request.getParameter("username");
            String password = request.getParameter("password");

            System.out.println(user_name);
            // Validation of user entering. İf it is not true this returns login page again
            // İf user accoun ino is true , it wii creat a session for user
            // Also if Account will not found Servlet will seend a 'login_message' to page
             int kontrol_active=userDao.findByEmail(user_name).getActive().ordinal();
            if(user_name!=null || password!=null ){
                BussinessLogin bl= new BussinessLogin();
                if(bl.accountControl(user_name, password)){
                    HttpSession session = request.getSession();
                    session.setAttribute("username", user_name);
                    session.setAttribute("active",kontrol_active);
                    Cookie cookie= new Cookie("user",user_name);
                    cookie.setMaxAge(60*60*24);
                    response.addCookie(cookie);

                        if(kontrol_active==1){
                            response.sendRedirect("index.jsp");
                        }else{
                            response.sendRedirect("confirmmail.jsp");
                        }


                }
                else{
                  //  request.setAttribute("login_message", "Paralo yada kullanıcı adı hatalı!<br> Lütfen kontrol ediniz...");

                  // request.getRequestDispatcher("Login.jsp").forward(request, response);
                   response.sendRedirect("Login.jsp?status=0");

                }
            }else{
                response.sendRedirect("Login.jsp");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }




    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
