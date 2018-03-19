package com.appchallengers.appchallengers.endpoint.service;

import com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl;
import com.appchallengers.appchallengers.dao.dao.UserDao;
import com.appchallengers.appchallengers.endpoint.error_handling.CommonExceptionHandler;
import com.appchallengers.appchallengers.model.entity.Users;
import com.appchallengers.appchallengers.model.request.LoginRequestModel;
import com.appchallengers.appchallengers.model.request.SignUpRequestModel;
import com.appchallengers.appchallengers.model.response.UserPreferenceData;
import com.appchallengers.appchallengers.util.EmailUtil;
import com.appchallengers.appchallengers.util.Util;
import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

import javax.mail.MessagingException;
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
            @FormDataParam("country") String country) throws CommonExceptionHandler {
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance(Locale.US).getTime().getTime());
        String uploadedFileLocation = null;
        try {
            uploadedFileLocation = "c://Users/MHMTNASIF/Desktop/uploaded/" + Util.hashMD5(currentTimestamp.toString() + email) + fileDetail.getFileName();
        } catch (NoSuchAlgorithmException e) {
            throw new CommonExceptionHandler("290");
        }
        Long emailCheckStatus = mUserDao.checkEmail(email);
        if (emailCheckStatus == 0) {
            if (!Util.writeToFile(uploadedInputStream, uploadedFileLocation)) {
                uploadedFileLocation = null;
            }
            Users createdUser = null;
            try {
                createdUser = mUserDao.saveUser(new Users(
                        fullName, email, Util.hashMD5(password),
                        Util.hashMD5(currentTimestamp.toString() + password),
                        country, uploadedFileLocation, Users.Active.NOT_CONFİRMED,
                        currentTimestamp, currentTimestamp
                ));
            } catch (NoSuchAlgorithmException e) {
                throw new CommonExceptionHandler("290");
            }
            UserPreferenceData userPreferenceData = null;
            try {
                userPreferenceData = new UserPreferenceData(
                        Util.createToken(createdUser.getEmail(), createdUser.getFullName(), createdUser.getId()),
                        createdUser.getFullName(), createdUser.getProfilePicture(),
                        createdUser.getEmail(), createdUser.getActive().ordinal()
                );
            } catch (UnsupportedEncodingException e) {
                throw new CommonExceptionHandler("290");
            }
            String url = "https://www.appchallengers.com./confirm.jsp?id=" + createdUser.getId() + "&" + "hash=" + createdUser.getPasswordSalt();
            try {
                EmailUtil.sendEmail(createdUser.getEmail(), "Confirm Email", url);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } finally {
                return Response.status(200).entity(new Gson().toJson(userPreferenceData)).build();
            }
        } else if (emailCheckStatus == 1) {
            throw new CommonExceptionHandler("250");
        } else {
            throw new CommonExceptionHandler("290");
        }
    }

    @POST
    @Path("/create/without_image")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createAccountWtihImage(SignUpRequestModel signUpRequestModel) throws CommonExceptionHandler {
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance(Locale.US).getTime().getTime());
        Long emailCheckStatus = mUserDao.checkEmail(signUpRequestModel.getEmail());
        if (emailCheckStatus == 0) {
            Users createdUser = null;
            try {
                createdUser = mUserDao.saveUser(new Users(
                        signUpRequestModel.getFullName(), signUpRequestModel.getEmail(),
                        Util.hashMD5(signUpRequestModel.getPassword()),
                        Util.hashMD5(currentTimestamp.toString() + signUpRequestModel.getPassword()),
                        signUpRequestModel.getCountry(), Users.Active.NOT_CONFİRMED,
                        currentTimestamp, currentTimestamp
                ));
            } catch (NoSuchAlgorithmException e) {
                throw new CommonExceptionHandler("290");
            }
            UserPreferenceData userPreferenceData = null;
            try {
                userPreferenceData = new UserPreferenceData(
                        Util.createToken(createdUser.getEmail(), createdUser.getFullName(), createdUser.getId()),
                        createdUser.getFullName(), createdUser.getProfilePicture(),
                        createdUser.getEmail(), createdUser.getActive().ordinal()
                );
            } catch (UnsupportedEncodingException e) {
                throw new CommonExceptionHandler("290");
            }
            String url = "https://www.appchallengers.com./confirm.jsp?id=" + createdUser.getId() + "&" + "hash=" + createdUser.getPasswordSalt();
            try {
                EmailUtil.sendEmail(createdUser.getEmail(), "Confirm Email", url);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } finally {
                return Response.status(200).entity(new Gson().toJson(userPreferenceData)).build();
            }
        } else if (emailCheckStatus == 1) {
            throw new CommonExceptionHandler("250");
        } else {
            throw new CommonExceptionHandler("290");
        }
    }


    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequestModel loginRequestModel) throws CommonExceptionHandler {
        Long status = null;
        try {
            status = (Long) mUserDao.login(loginRequestModel.getEmail(), Util.hashMD5(loginRequestModel.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            throw new CommonExceptionHandler("290");
        }
        if (status == 0) {
            throw new CommonExceptionHandler("251");
        } else if (status == 1) {
            Users user = mUserDao.findByEmail(loginRequestModel.getEmail());
            UserPreferenceData userPreferenceData = null;
            try {
                userPreferenceData = new UserPreferenceData(
                        Util.createToken(user.getEmail(), user.getFullName(), user.getId()),
                        user.getFullName(), user.getProfilePicture(),
                        user.getEmail(), user.getActive().ordinal()
                );
            } catch (UnsupportedEncodingException e) {
                throw new CommonExceptionHandler("290");
            }
            return Response.status(200).entity(new Gson().toJson(userPreferenceData)).build();
        } else {
            throw new CommonExceptionHandler("290");
        }
    }

    @GET
    @Path("/send_email")
    @Produces("application/json")
    public Response resendConfirmEmail(@HeaderParam("token") String token) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            Users user = null;
            try {
                user=mUserDao.findUserById(Util.getIdFromToken(token));
            } catch (UnsupportedEncodingException e) {
                throw new CommonExceptionHandler("289");
            } catch (MalformedJwtException exception) {
                throw new CommonExceptionHandler("289");
            } catch (SignatureException exception) {
                throw new CommonExceptionHandler("289");
            }
            String url = "https://www.appchallengers.com./confirm.jsp?id=" + user.getId() + "&" + "hash=" + user.getPasswordSalt();
            try {
                EmailUtil.sendEmail(user.getEmail(), "Confirm Email", url);
            } catch (MessagingException e) {
                throw new CommonExceptionHandler("254");
            } catch (UnsupportedEncodingException e) {
                throw new CommonExceptionHandler("254");
            }
            return Response.status(200).entity(new Gson().toJson(new UserPreferenceData())).build();
        }
    }

    @GET
    @Path("/check_confirm_email")
    @Produces("application/json")
    public Response checkConfirmEmail(@HeaderParam("token") String token) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            Users user = null;
            try {
                user=mUserDao.findUserById(Util.getIdFromToken(token));
            } catch (UnsupportedEncodingException e) {
                throw new CommonExceptionHandler("289");
            } catch (MalformedJwtException exception) {
                throw new CommonExceptionHandler("289");
            } catch (SignatureException exception) {
                throw new CommonExceptionHandler("289");
            }
            if (user.getActive() == Users.Active.NOT_CONFİRMED) {
                throw new CommonExceptionHandler("253");
            } else {
                return Response.status(200).entity(new Gson().toJson(new UserPreferenceData())).build();
            }
        }
    }
}



