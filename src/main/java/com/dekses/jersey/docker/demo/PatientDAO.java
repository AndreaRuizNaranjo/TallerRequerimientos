package com.dekses.jersey.docker.demo;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public static Patient getPatient(String nombre) throws UnknownHostException {

        Singleton conexion = Singleton.getInstance();

        DBCollection coll = conexion.getDb().getCollection("patient");

        Gson gson = new Gson();
        DBObject doc = new BasicDBObject("name", nombre);

        DBObject obj = coll.findOne(doc);
        Patient p = gson.fromJson(obj.toString(), Patient.class);
        System.out.println("Found customer " + p);

        return p;

    }

    public static void addPatient(Patient p) {

        try {

            Singleton conexion = Singleton.getInstance();

            DBCollection coll = conexion.getDb().getCollection("patient");
            DBObject doc = new BasicDBObject("name", p.getName())
                    .append("address", p.getAddress())
                    .append("birth", p.getBirth())
                    .append("telephone", p.getTelephone())
                    .append("medicare", p.getMedicare())
                    .append("status", p.getStatus());

            coll.insert(doc);
            System.out.println("Paciente " + p.getName() + " agregado exitosamente.");

        } catch (UnknownHostException e) {
            System.err.println(e.getClass().getName() + ": "
                    + e.getMessage());
        }

    }

    public static void updatePatient(Patient p) throws UnknownHostException {

        Singleton conexion = Singleton.getInstance();

        DBCollection coll = conexion.getDb().getCollection("patient");
        DBObject document = new BasicDBObject();
        document.put("id", p.getId());

        DBObject searchQuery = new BasicDBObject().append("name", p.getName())
                .append("address", p.getAddress())
                .append("birth", p.getBirth())
                .append("telephone", p.getTelephone())
                .append("medicare", p.getMedicare())
                .append("status", p.getStatus());
        coll.update(searchQuery, document);

        System.out.println("Paciente " + p.getName() + " modificado exitosamente.");
        
    }

    public static void deletePatient(String nombre) {
        try {

            Singleton conexion = Singleton.getInstance();

            DBCollection coll = conexion.getDb().getCollection("patient");
            DBObject document = new BasicDBObject();
            document.put("name", nombre);

            coll.remove(document);
            System.out.println("Paciente con nombre: " + nombre + " eliminado exitosamente.");

        } catch (UnknownHostException e) {
            System.err.println(e.getClass().getName() + ": "
                    + e.getMessage());
        }

    }

    public static List getAllPatient() throws UnknownHostException {

        List<Patient> Patients = new ArrayList();

        try {

            Singleton conexion = Singleton.getInstance();

            DBCollection coll = conexion.getDb().getCollection("patient");
            DBCursor cursor = coll.find();
            try {
                while (cursor.hasNext()) {
                    DBObject object = cursor.next();
                    Gson gson = new Gson();
                    Patient p = gson.fromJson(object.toString(), Patient.class);
                    Patients.add(p);
                    System.out.println("Se encontraron todos los pacientes.");

                }
            } finally {
                cursor.close();
            }

        } catch (UnknownHostException e) {
            System.err.println(e.getClass().getName() + ": "
                    + e.getMessage());
        }

        return Patients;

    }

}
