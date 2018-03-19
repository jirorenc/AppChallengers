package com.appchallengers.appchallengers.web.servlets;



import com.appchallengers.appchallengers.dao.Jpa.UserDaoImpl;
import com.appchallengers.appchallengers.dao.UserDao;
import com.appchallengers.appchallengers.model.Users;
import com.appchallengers.appchallengers.web.bussiness.BussinessLogin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginConfirmServlet", urlPatterns = {"/LoginConfirmServlet"})
public class LoginConfirmServlet extends HttpServlet {

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
            Users users=userDao.findByEmail(user_name);
            if(user_name!=null || password!=null ){
                BussinessLogin bl= new BussinessLogin();
                if(bl.accountControl(user_name, password)){
                    HttpSession session = request.getSession();
                    session.setAttribute("username", user_name);
                    Cookie cookie= new Cookie("user",user_name);
                    cookie.setMaxAge(60*60*24);
                    response.addCookie(cookie);
                    response.sendRedirect("confirm.jsp?id="+users.getId()+"&hash="+users.getPasswordSalt());
                }
                else{

                    response.sendRedirect("confirm.jsp?id="+users.getId()+"&hash="+users.getPasswordSalt());
                }
            }else{
                response.sendRedirect("confirm.jsp?id="+users.getId()+"&hash="+users.getPasswordSalt());
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
