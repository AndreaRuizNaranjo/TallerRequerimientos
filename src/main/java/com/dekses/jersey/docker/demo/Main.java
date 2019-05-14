package com.dekses.jersey.docker.demo;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Scanner;
import javax.ws.rs.core.MediaType;

/**
 * Main class.
 *
 */
public class Main {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
     * application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.dekses.jersey.docker.demo package
        final ResourceConfig rc = new ResourceConfig().packages("com.dekses.jersey.docker.demo");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Client client = Client.create();
        WebResource webResource = null;
        ClientResponse response = null;

        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        // System.in.read();
        // server.stop();

        webResource = client.resource("http://localhost:8080/myapp/myresource");

        try {

            int opcion = -1;

            Scanner lectura = new Scanner(System.in);
            System.out.println("¡Bienvenido al sistema de pacientes! Selecciona una opción: \n\n");
            Patient paciente = new Patient();

            do {

                System.out.println("1. Mostrar Todos.\n 2. Crear.\n 3. Buscar.\n 4.Modificar.\n 5. Borrar.\n 6. Salir.");
                opcion = lectura.nextInt();

                ObjectMapper mapper = new ObjectMapper();

                switch (opcion) {
                    
                    case 1: // Mostrar Todos

                        List Patients = PatientDAO.getAllPatient();

                        for (int i = 0; i < Patients.size(); i++) {
                            paciente = (Patient) Patients.get(i);
                            System.out.println(paciente.toString());
                        }

                        break;

                    case 2: // Crear

                        System.out.println("Ingrese el nombre");
                        String nombre = lectura.next();
                        paciente.setName(nombre);

                        System.out.println("Ingrese la direccion");
                        String direccion = lectura.next();
                        paciente.setAddress(direccion);

                        System.out.println("Ingrese la fecha de nacimiento");
                        String fechanacimiento = lectura.next();
                        paciente.setBirth(fechanacimiento);

                        System.out.println("Ingrese el numero telefonico");
                        String numerotelefono = lectura.next();
                        paciente.setTelephone(numerotelefono);

                        System.out.println("Ingrese el medicare");
                        String medicare = lectura.next();
                        paciente.setMedicare(medicare);

                        System.out.println("Ingrese el status");
                        String status = lectura.next();
                        paciente.setStatus(status);

                        String input = mapper.writeValueAsString(paciente);

                        response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, input);

                        PatientDAO.addPatient(paciente);

                        System.out.println("Listo! c: ");

                        break;

                    case 3: // Buscar.

                        System.out.println("Digite el nombre del usuario a buscar: \n");
                        String id = lectura.next();

                        Patient pacienteRetorno = PatientDAO.getPatient(id);
                        System.out.println(pacienteRetorno.toString());

                        break;

                    case 4: // Modificar.
                        
                        System.out.println("Ingrese el nombre del paciente a modificar:");
                        String idNombre = lectura.next();
                        paciente.setName(idNombre);

                        System.out.println("Ingrese la nueva direccion");
                        direccion = lectura.next();
                        paciente.setAddress(direccion);

                        System.out.println("Ingrese la nueva fecha de nacimiento");
                        fechanacimiento = lectura.next();
                        paciente.setBirth(fechanacimiento);

                        System.out.println("Ingrese el nuevo numero telefonico");
                        numerotelefono = lectura.next();
                        paciente.setTelephone(numerotelefono);

                        System.out.println("Ingrese el nuevo medicare");
                        medicare = lectura.next();
                        paciente.setMedicare(medicare);

                        System.out.println("Ingrese el nuevo status");
                        status = lectura.next();
                        paciente.setStatus(status);

                        PatientDAO.updatePatient(paciente, idNombre);
                        System.out.println("Listo! c: ");

                        break;

                    case 5: // Borrar.

                        System.out.println("Digite el nombre del usuario a eliminar: \n");
                        nombre = lectura.next();

                        PatientDAO.deletePatient(nombre);
                        System.out.println("Listo! c: ");

                        break;

                    default: // Salir.
                        lectura.close();
                        System.out.println("Adiós! :c ");

                }

            } while (opcion != 5);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
