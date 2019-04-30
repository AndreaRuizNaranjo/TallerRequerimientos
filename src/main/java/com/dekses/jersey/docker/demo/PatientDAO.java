package com.dekses.jersey.docker.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientDAO {

    private static final Map<String, Patient> PATIENTS_MAP = new HashMap<String, Patient>();

    static {
        initPatient();
    }

    private static void initPatient() {
        Patient p1 = new Patient("1", "Sara Chamseddine", "Bogotá", "1998 02 05", "03 9002 4002", "(M9999999)", "public");
        Patient p2 = new Patient("2", "Andrea Ruiz", "Bogotá", "1996 02 05", "03 9001 4002", "(M9999992)", "private");
        Patient p3 = new Patient("3", "Jeferson Cruz", "Bogotá", "1997 02 05", "03 9003 4002", "(M9999991)", "public");

        PATIENTS_MAP.put(p1.getId(), p1);
        PATIENTS_MAP.put(p2.getId(), p2);
        PATIENTS_MAP.put(p3.getId(), p3);
    }

    public static Patient getPatient(String id) {
        return PATIENTS_MAP.get(id);
    }

    public static Patient addPatient(Patient p) {
        PATIENTS_MAP.put(p.getId(), p);
        return p;
    }

    public static Patient updatePatient(Patient p) {
        PATIENTS_MAP.put(p.getId(), p);
        return p;
    }

    public static void deletePatient(String id) {
        PATIENTS_MAP.remove(id);
    }

    public static List<Patient> getAllPatient() {
        Collection<Patient> c = PATIENTS_MAP.values();
        List<Patient> list = new ArrayList<>();
        list.addAll(c);
        return list;
    }

    List<Patient> list;

}
