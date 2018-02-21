package com.appchallengers.webservice;

import com.appchallengers.webservice.dao.ApplicationDao;
import com.appchallengers.webservice.dao.Jpa.ApplicationDaoImpl;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/application")
public class ApplicationService {

    ApplicationDao applicationDao = new ApplicationDaoImpl();

    @GET
    @Path("/get_country_list")
    @Produces("application/json")
    public Response getCountrList() {
        return Response.status(200).entity(new Gson().toJson(applicationDao.getCountryList())).build();
    }
}
