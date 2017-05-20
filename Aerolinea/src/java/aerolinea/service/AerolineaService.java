
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolinea.service;

import aerolinea.modelo.*;
import com.google.gson.*;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "AerolineaService", urlPatterns = {"/AerolineaService"})
public class AerolineaService extends HttpServlet{
    AerolineaModelo model;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()){
            response.setContentType("text/xml");
            RuntimeTypeAdapterFactory<Jsonable> rta = RuntimeTypeAdapterFactory.of(Jsonable.class, "_class")
                    .registerSubtype(Ciudad.class, "Ciudad")
                    .registerSubtype(Avion.class, "Avion")
                    .registerSubtype(Viaje.class, "Viaje")
                    .registerSubtype(Vuelo.class, "Vuelo");
            Gson gson = new GsonBuilder().registerTypeAdapterFactory(rta).setDateFormat("dd/MM/yyyy").create();
            String json;
            String accion = request.getParameter("action");
            System.out.println(accion);
            List<Ciudad> ciudades;
            List<Avion> aviones;
            List<Viaje> viajes;
            List<Vuelo> promos;
            Vuelo vuelos;
            switch(accion){
                case "ciudadListAll":
                    ciudades = model.getCiudades();
                    json = gson.toJson(ciudades);
                    out.write(json);
                    System.out.print(json);
                    break;
                case "vueloListPromo":
                    promos = model.getPromo();
                    json = gson.toJson(promos);
                    out.write(json);
                    break;
                case "vueloListSearch":
                    String origen = request.getParameter("origen");
                    String destino = request.getParameter("destino");
                    vuelos = model.getVuelos(origen, destino);
                    json = gson.toJson(vuelos);
                    out.write(json);
                    break;
                case "viajeListSearch":
                    String origen2 = request.getParameter("origen");
                    String destino2 = request.getParameter("destino");
                    String fecha = request.getParameter("fecha");
                    String cantAsientOcup = request.getParameter("cantAsientOcup");
                    viajes = model.getViajes(origen2, destino2, fecha, cantAsientOcup);
                    json = gson.toJson(viajes);
                    out.write(json);
                    break;                    
                case "viajeListAll":
                    viajes = model.getViajes();
                    json = gson.toJson(viajes);
                    out.write(json);
                    System.out.print(json);
                    break;
            }
        }
        catch(Exception e){ System.out.println(e); }
    }
    
   
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    @Override
    public void init() throws ServletException{
        super.init();
        model = new AerolineaModelo();
    }
}
