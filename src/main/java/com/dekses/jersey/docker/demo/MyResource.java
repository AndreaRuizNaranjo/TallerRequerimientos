package com.dekses.jersey.docker.demo;

import java.net.UnknownHostException;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Patient> getPatient_JSON() throws UnknownHostException {
        List<Patient> listOfPatients = PatientDAO.getAllPatient();
        return listOfPatients;
    }
 
    // URI:
    // /contextPath/servletPath/employees/{empNo}
    @GET
    @Path("/{nombre}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Patient getPatient(@PathParam("nombre") String nombre) throws UnknownHostException {
        return PatientDAO.getPatient(nombre);
    }
 
    // URI:
    // /contextPath/servletPath/employees
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void addPatient(Patient p) {
        PatientDAO.addPatient(p);
    }
 
    @PUT
    @Path("/{idNombre}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void updatePatient(Patient p, @PathParam("idNombre") String idNombre) throws UnknownHostException {
        PatientDAO.updatePatient(p, idNombre);
    }
 
    @DELETE
    @Path("/{nombre}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void deletePatient(@PathParam("nombre") String nombre) {
        PatientDAO.deletePatient(nombre);
    }    
    
}
