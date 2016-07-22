/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.registro;


@WebServlet(name = "ingreso", urlPatterns = {"/ingreso"})
public class ingreso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        registro re=new registro();
        
        if(request.getParameter("ingreso") != null){
            String numero_movil=request.getParameter("numero_movil").toUpperCase();
            String placa=request.getParameter("placa").toUpperCase();
            String modelo=request.getParameter("modelo").toUpperCase();
            String codigo_marca=request.getParameter("codigo_marca").toUpperCase();
            String id_conductor=request.getParameter("id_conductor").toUpperCase();
            String id_socio=request.getParameter("id_socio").toUpperCase();

            re.insertar(Integer.parseInt(numero_movil), placa, Integer.parseInt(modelo),Integer.parseInt(codigo_marca),Integer.parseInt(id_conductor), Integer.parseInt(id_socio));
         
        }else if(request.getParameter("actualizar") != null){
            
            String numero_movil=request.getParameter("numero_movil").toUpperCase();
            String placa=request.getParameter("placa").toUpperCase();
            String modelo=request.getParameter("modelo").toUpperCase();
            String codigo_marca=request.getParameter("codigo_marca").toUpperCase();
            String id_conductor=request.getParameter("id_conductor").toUpperCase();
            String id_socio=request.getParameter("id_socio").toUpperCase();

            re.actualizar(Integer.parseInt(numero_movil), placa, Integer.parseInt(modelo),Integer.parseInt(codigo_marca),Integer.parseInt(id_conductor), Integer.parseInt(id_socio));
            
        }else if(request.getParameter("borrar") != null){
            String numero_movil = request.getParameter("numero_movilb").toUpperCase();
            re.eliminar(Integer.parseInt(numero_movil));                        
        }
        response.sendRedirect("index.jsp");    
    
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

}
