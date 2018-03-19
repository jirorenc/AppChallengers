package com.appchallengers.webservice;

import com.appchallengers.webservice.dao.Jpa.UserDaoImpl;
import com.appchallengers.webservice.dao.UserDao;
import com.appchallengers.webservice.model.Users;
import com.appchallengers.webservice.model.request.LoginRequestModel;
import com.appchallengers.webservice.model.request.SignUpRequestModel;
import com.appchallengers.webservice.model.response.UserSignUpAndLoginResponseModel;
import com.appchallengers.webservice.util.EmailUtil;
import com.appchallengers.webservice.util.Util;
import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Locale;


@Path("/users/account")
public class UserAccountService {

    private UserDao mUserDao = new UserDaoImpl();

    @GET
    @Path("/example")
    @Produces("text/plain")
    public String createAccount() {
        return "hello word";
    }

    @POST
    @Path("/create/with_image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    public Response createAccountWtihImage(
            @FormDataParam("image") InputStream uploadedInputStream,
            @FormDataParam("image") FormDataContentDisposition fileDetail,
            @FormDataParam("fullName") String fullName,
            @FormDataParam("email") String email,
            @FormDataParam("password") String password,
            @FormDataParam("country") String country) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance(Locale.US).getTime().getTime());
        String uploadedFileLocation = "c://Users/MHMTNASIF/Desktop/uploaded/" + Util.hashMD5(currentTimestamp.toString() + email) + fileDetail.getFileName();
        Long emailCheckStatus = mUserDao.checkEmail(email);
        if (emailCheckStatus == 0) {
            if (uploadedInputStream != null) {
                if (!Util.writeToFile(uploadedInputStream, uploadedFileLocation)) {
                    uploadedFileLocation = null;
                }
            } else {
                uploadedFileLocation = null;
            }
            mUserDao.saveUser(new Users(
                    fullName, email, Util.hashMD5(password),
                    Util.hashMD5(currentTimestamp.toString() + password),
                    country, uploadedFileLocation, Users.Active.NOT_CONFİRMED,
                    currentTimestamp, currentTimestamp
            ));
            Users user = mUserDao.findByEmail(email);
            UserSignUpAndLoginResponseModel userSignUpAndLoginResponseModel = new UserSignUpAndLoginResponseModel(
                    200,Util.createToken(user.getEmail(), user.getFullName(), user.getId()),
                    user.getFullName(),user.getProfilePicture(),
                    user.getEmail(), user.getActive().ordinal()
            );
            String url = "https://www.appchallengers.com./confirm.jsp?id=" + user.getId() + "&" + "hash=" + user.getPasswordSalt();
            EmailUtil.sendEmail(user.getEmail(), "Confirm Email", url);
            return Response.status(200).entity(new Gson().toJson(userSignUpAndLoginResponseModel)).build();
        } else if (emailCheckStatus == 1) {
            return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(250))).build();
        } else {
            return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(290))).build();

        }
    }

    @POST
    @Path("/create/without_image")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createAccountWtihImage(SignUpRequestModel signUpRequestModel) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance(Locale.US).getTime().getTime());
        Long emailCheckStatus = mUserDao.checkEmail(signUpRequestModel.getEmail());
        if (emailCheckStatus == 0) {
            mUserDao.saveUser(new Users(
                    signUpRequestModel.getFullName(), signUpRequestModel.getEmail(),
                    Util.hashMD5(signUpRequestModel.getPassword()),
                    Util.hashMD5(currentTimestamp.toString() + signUpRequestModel.getPassword()),
                    signUpRequestModel.getCountry(), Users.Active.NOT_CONFİRMED,
                    currentTimestamp, currentTimestamp
            ));
            Users user = mUserDao.findByEmail(signUpRequestModel.getEmail());
            UserSignUpAndLoginResponseModel userSignUpAndLoginResponseModel = new UserSignUpAndLoginResponseModel(
                    200,Util.createToken(user.getEmail(), user.getFullName(), user.getId()),
                    user.getFullName(),user.getProfilePicture(),
                    user.getEmail(), user.getActive().ordinal()
            );
            String url = "https://www.appchallengers.com./confirm.jsp?id=" + user.getId() + "&" + "hash=" + user.getPasswordSalt();
            EmailUtil.sendEmail(user.getEmail(), "Confirm Email", url);
            return Response.status(200).entity(new Gson().toJson(userSignUpAndLoginResponseModel)).build();
        } else if (emailCheckStatus == 1) {
            return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(250))).build();
        } else {
            return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(290))).build();

        }
    }

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequestModel loginRequestModel) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Long status = (Long) mUserDao.login(loginRequestModel.getEmail(), Util.hashMD5(loginRequestModel.getPassword()));
        if (status == 0) {
            return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(251))).build();
        } else if (status == 1) {
            Users user = mUserDao.findByEmail(loginRequestModel.getEmail());
            UserSignUpAndLoginResponseModel userSignUpAndLoginResponseModel = new UserSignUpAndLoginResponseModel(
                    200,Util.createToken(user.getEmail(), user.getFullName(), user.getId()),
                    user.getFullName(),user.getProfilePicture(),
                    user.getEmail(), user.getActive().ordinal()
            );
            return Response.status(200).entity(new Gson().toJson(userSignUpAndLoginResponseModel)).build();
        } else {
            return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(290))).build();
        }
    }

    @GET
    @Path("/send_email")
    @Produces("application/json")
    public Response resendConfirmEmail(@HeaderParam("token") String token) {
        if (token == null) {
            return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(289))).build();
        } else {
            Users user = null;
            try {
                user = mUserDao.findByEmail(Util.getEmailFromToken(token));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(290))).build();
            }catch (RuntimeException e){
                return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(290))).build();
            }
            String url = "https://www.appchallengers.com./confirm.jsp?id=" + user.getId() + "&" + "hash=" + user.getPasswordSalt();
            EmailUtil.sendEmail(user.getEmail(), "Confirm Email", url);
            if (EmailUtil.sendEmail(user.getEmail(), "Confirm Email", url)) {
                return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(252))).build();
            } else {
                return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(290))).build();
            }
        }
    }

    @GET
    @Path("/check_confirm_email")
    @Produces("application/json")
    public Response checkConfirmEmail(@HeaderParam("token") String token) {
        if (token == null) {
            return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(289))).build();
        }else{
            Users user = null;
            try {
                user = mUserDao.findByEmail(Util.getEmailFromToken(token));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(290))).build();
            }catch (RuntimeException e){
                return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(290))).build();
            }
            if (user.getActive() == Users.Active.NOT_CONFİRMED) {
                return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(253))).build();
            } else {
                return Response.status(200).entity(new Gson().toJson(new UserSignUpAndLoginResponseModel(200))).build();
            }
        }
    }
}



