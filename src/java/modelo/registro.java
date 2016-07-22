/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JOptionPane;

public class registro {
    //Variables tabla taxi
    private int numero_movil;
    private String placa;
    private int modelo;
    private int codigo_marca;
    private int id_conductor;
    private int id_socio;
    
            
    static private String classfor="oracle.jdbc.OracleDriver";
    static private String url="jdbc:oracle:thin:@localhost:1521:XE";
    static private String usuario="taxis";
    static private String clave="1234";
    
    static private Connection con=null;
    static private PreparedStatement pr=null;
    static private ResultSet rs=null;
    
    
    
    public void insertar(int numero_movil,String placa, int modelo,int codigo_marca, int id_conductor, int id_socio){
        String sql="Insert into taxis values(?,?,?,?,?,?)";
    try
    {
        Class.forName(classfor);
        con=DriverManager.getConnection(url, usuario, clave);
                
        pr=con.prepareStatement(sql);
        pr.setInt(1, numero_movil);
        pr.setString(2, placa);
        pr.setInt(3, modelo);
        pr.setInt(4, codigo_marca);
        pr.setInt(5, id_conductor);
        pr.setInt(6, id_socio);
        
        pr.executeUpdate();
    }
    catch(Exception ev)
    {}
    }
    
    
    public void actualizar(int numero_movil,String placa, int modelo,int codigo_marca, int id_conductor, int id_socio){
        String sql="UPDATE taxis SET placa='"+ placa +"',modelo="+ modelo +",codigo_marca="+ codigo_marca +","
                + "id_conductor="+ id_conductor +",id_socio="+ id_socio +" WHERE numero_movil="+ numero_movil;
        try
        {
            Class.forName(classfor);
            con = DriverManager.getConnection(url, usuario,clave);
            pr = con.prepareStatement(sql);
            pr.executeUpdate();
            pr.close();
        }
        catch(Exception e)
        {
            System.out.println("Los datos no se han actualizado correctamente");           
        }
    }
    
    
   public Vector<registro> buscar(){
        Vector<registro> vecPro=new Vector<registro>();
        String sql="SELECT * FROM taxis";
        try{
            Class.forName(classfor);
            con=DriverManager.getConnection(url, usuario,clave);
            pr=con.prepareStatement(sql);
            rs=pr.executeQuery();
            while(rs.next()){
                registro pro=new registro();
                pro.setNumero_movil(rs.getInt("numero_movil"));
                pro.setPlaca(rs.getString("placa"));
                pro.setModelo(rs.getInt("modelo"));
                pro.setCodigo_marca(rs.getInt("codigo_marca"));
                pro.setId_conductor(rs.getInt("id_conductor"));
                pro.setId_socio(rs.getInt("id_socio"));
                vecPro.add(pro);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                rs.close();
                pr.close();
                con.close();
            }catch(Exception ex){

            }
        }
        return vecPro;
    }
   
   
    public static LinkedList<consulta1> getConsulta1() {
        LinkedList<consulta1> con1 = new LinkedList<consulta1>();
        try {
            Class.forName(classfor);
            con = DriverManager.getConnection(url, usuario, clave);

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select id_so, nombre_so, count(*) as Cantidad from taxis\n" +
"                                           join socios on id_socio = id_so group by id_so, nombre_so");
            while (rs.next()) {
                consulta1 contacto = new consulta1();
                contacto.setId_so(rs.getInt("id_so"));
                contacto.setNombre(rs.getString("nombre_so"));
                contacto.setCantidad(rs.getInt("cantidad"));

                con1.add(contacto);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con1;
    }
    
    public static LinkedList<consulta2> getConsulta2() {
        LinkedList<consulta2> con2 = new LinkedList<consulta2>();
        try {
            Class.forName(classfor);
            con = DriverManager.getConnection(url, usuario, clave);
            String sql1 = "CREATE VIEW v_cantMar AS (SELECT nombre_mar,count(*) AS cantidad FROM taxis JOIN marcas ON codigo_marca = codigo_mar GROUP BY nombre_mar)";
            String sql2 = "SELECT nombre_mar as Nombre_marca From v_cantMar WHERE cantidad = (SELECT MAX(cantidad) FROM v_cantMar)";
            Statement st = con.createStatement();
            //pr=con.prepareStatement(sql1);
            //pr.executeUpdate(sql1);
            ResultSet rs = st.executeQuery(sql2) ;
            while (rs.next()) {
                consulta2 contacto = new consulta2();
                contacto.setMarca(rs.getString("nombre_marca"));               
                con2.add(contacto);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con2;
    }
    
   public void eliminar(int numero_movil){
       String sql = "DELETE FROM taxis WHERE numero_movil="+numero_movil;
       String sql2 = "DELETE FROM taxis_a_cargo WHERE numero_movil_taxi="+ numero_movil;
       String sql3 = "DELETE FROM servicios WHERE numero_movil_taxi="+ numero_movil;
        try{
            Class.forName(classfor);
            con=DriverManager.getConnection(url, usuario,clave);
            pr=con.prepareStatement(sql2);
            pr.executeUpdate(sql2);
            pr=con.prepareStatement(sql3);
            pr.executeUpdate(sql3);
            pr=con.prepareStatement(sql);
            pr.executeUpdate(sql);
            pr.close();
        }catch(Exception ev){}
   }
   
    
    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    

    public int getNumero_movil() {
        return numero_movil;
    }

    public void setNumero_movil(int numero_movil) {
        this.numero_movil = numero_movil;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public int getCodigo_marca() {
        return codigo_marca;
    }

    public void setCodigo_marca(int codigo_marca) {
        this.codigo_marca = codigo_marca;
    }

    public int getId_conductor() {
        return id_conductor;
    }

    public void setId_conductor(int id_conductor) {
        this.id_conductor = id_conductor;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }
    
    
      
}

