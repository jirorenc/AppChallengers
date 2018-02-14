package com.appchallengers.webservice;

import com.appchallengers.webservice.dao.ConfirmDao;
import com.appchallengers.webservice.dao.Jpa.ConfirmDaoImpl;
import com.appchallengers.webservice.dao.Jpa.UserDaoImpl;
import com.appchallengers.webservice.dao.UserDao;
import com.appchallengers.webservice.model.Confirm;
import com.appchallengers.webservice.model.Users;
import com.appchallengers.webservice.model.response.UserSignUpAndLoginResponseModel;
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
import java.util.Date;
import java.util.Locale;
import java.util.Random;


@Path("/users")
public class UserFeedService {

    private UserDao mUserDao = new UserDaoImpl();
    private ConfirmDao confirmDao = new ConfirmDaoImpl();

    @GET
    @Path("/createaccount")
    @Produces("text/plain")
    public String createAccount() {
        return "hello word";
    }

    @POST
    @Path("/uploadphoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    public Response uploadUserProfilePicture(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("fullName") String fullName,
            @FormDataParam("email") String email,
            @FormDataParam("password") String password,
            @FormDataParam("country") String country) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String uploadedFileLocation = null;
        if (mUserDao.checkEmail(email).size() == 0) {
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance(Locale.US).getTime().getTime());
            if (uploadedInputStream != null) {
                uploadedFileLocation = "c://Users/MHMTNASIF/Desktop/uploaded/" + Util.hashMD5(currentTimestamp.toString() + email) + fileDetail.getFileName();
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
            confirmDao.saveConfirm(new Confirm(
                    (1000000 + new Random().nextInt(8888888)) + "",
                    user
            ));
            UserSignUpAndLoginResponseModel userSignUpAndLoginResponseModel = new UserSignUpAndLoginResponseModel(
                    user.getId(), Util.createToken(user.getEmail(), user.getFullName(), user.getId()),
                    user.getEmail(), user.getActive().ordinal()
            );
            return Response.status(200).entity(new Gson().toJson(userSignUpAndLoginResponseModel)).build();
        } else {
            return Response.status(250).build();
        }
    }

}



