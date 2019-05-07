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
        System.in.read();
        server.stop();

        try {

            int opcion = -1;

            Scanner lectura = new Scanner(System.in);
            System.out.println("Bienvenido al sistema de pacientes");

            do {
                System.out.println("1. Mostrar Todos \n 2. Crear. \n 3. Buscar. \n 4.Modificar \n 5. Borrar \n 6. Salir");
                opcion = lectura.nextInt();
                switch (opcion) {
                    case 1: // Mostrar Todos

                        break;

                    case 2: // Crear
                        webResource = client.resource("http://localhost:8090/myApp/myresource");

                        ObjectMapper mapper = new ObjectMapper();
                        Patient paciente = new Patient();

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
                        //Luego se utilizara Jackson
                        //String input = "{\"empNo\":\"E11\",\"empName\":\"" + nombre + "\",\"position\":\"Salesman\"}";

                        response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, input);

                        if (response.getStatus() != 200) {
                            System.out.println(response.toString());
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatus());
                        }

                        String output = response.getEntity(String.class);
                        System.out.println(output);
                        break;
                    case 3:
                        

                        break;
                        
                      case 4:
                        

                        break;   
                        
                    case 5:
                        

                        break;
                    default:
                        System.out.println("Opcion invalida");

                }

            } while (opcion != 6);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
