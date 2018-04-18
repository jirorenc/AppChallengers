package com.appchallengers.appchallengers.endpoint.service;

import com.appchallengers.appchallengers.dao.dao.ApplicationDao;
import com.appchallengers.appchallengers.dao.daoimpl.ApplicationDaoImpl;
import com.appchallengers.appchallengers.endpoint.error_handling.CommonExceptionHandler;
import com.appchallengers.appchallengers.model.response.GetTrendsResponse;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/application")
public class ApplicationService {

    ApplicationDao applicationDao = new ApplicationDaoImpl();

    @GET
    @Path("/get_country_list")
    @Produces("application/json")
    public Response getCountrList() {
        return Response.status(200).entity(new Gson().toJson(applicationDao.getCountryList())).build();
    }

    @GET
    @Path("/get_trends_list")
    @Produces("application/json")
    public Response getTrendsList() {
        List<GetTrendsResponse> getTrendsResponseList = applicationDao.getTrendsList();
        return Response.status(200).entity(new Gson().toJson(getTrendsResponseList)).build();
    }
}
