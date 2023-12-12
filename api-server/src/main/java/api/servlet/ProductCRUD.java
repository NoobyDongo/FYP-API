/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.servlet;

import api.bean.ProductBean;
import api.db.ProductDB;
import api.util.SQLAdvancePair;
import api.util.SQLPair;
import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author Ison Ho
 */
@WebServlet(name = "ProductCRUD", urlPatterns = {"/product"})
public class ProductCRUD extends HttpServlet {

    private ProductDB db;
    private final Gson gson = new Gson();

    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        try {
            db = new ProductDB(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");
        String lang = request.getParameter("lang");
        boolean full = "full".equals(request.getParameter("mode"));

        ProductBean p = db.queryById(lang, full, id);
        String employeeJsonString = this.gson.toJson(p);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");

        ServletOutputStream out = response.getOutputStream();
        out.write(employeeJsonString.getBytes("UTF-8"));
        out.flush();
        
        System.out.println(full ? employeeJsonString : request.getParameter("mode"));
    }

    @Override
    protected void doPut(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String jsonBody = new BufferedReader(new InputStreamReader(request.getInputStream())).lines().collect(
                Collectors.joining("\n"));
        if (jsonBody == null || jsonBody.trim().length() == 0) {
            // return error that jsonBody is empty
        }
        SQLAdvancePair[] s = new Gson().fromJson(jsonBody, SQLAdvancePair[].class); 
        String lang = request.getParameter("lang");
        boolean full = "full".equals(request.getParameter("mode"));
        

        ArrayList<ProductBean> p = db.queryByColumns(lang, full, s);
        String ps = this.gson.toJson(p);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");

        ServletOutputStream out = response.getOutputStream();
        out.write(ps.getBytes("UTF-8"));
        out.flush();
    }
    
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        /*
        String name = request.getParameter("name");
        String desc = request.getParameter("desc");
        double price = Double.parseDouble(request.getParameter("price"));
         */
        String jsonBody = new BufferedReader(new InputStreamReader(request.getInputStream())).lines().collect(
                Collectors.joining("\n"));
        if (jsonBody == null || jsonBody.trim().length() == 0) {
            // return error that jsonBody is empty
        }

        ProductBean[] data = new Gson().fromJson(jsonBody, ProductBean[].class);
        ArrayList<ProductBean> beans = new ArrayList();
        for(var d : data){
            String id;
            if(d.id == null)
                id = db.create(d).id;
            else
                id = db.update(d).id;
            
            //for development purpose
            ProductBean bean = db.queryById(null, true, id);
            beans.add(bean);
        }
        String jsonString = this.gson.toJson(beans);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");

        ServletOutputStream out = response.getOutputStream();
        out.write(jsonString.getBytes("UTF-8"));
        out.flush();
    }
    
    @Override
    protected void doDelete(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        
    }
}
/*
public class HandleCustomer extends HttpServlet {

    private CustomerDB db;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        //
        dbUser = dbUser == null? "root" : dbUser;
        dbPassword = dbPassword == null? "" : dbPassword;
        dbUrl = dbUrl == null? "jdbc:mysql://localhost:3306/ITP4511_DB" : dbUrl;
        
        db = new CustomerDB(dbUrl, dbUser, dbPassword);
        db.createCustTable();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected String fromUrl(HttpServletRequest request, HttpServletResponse response, String key)
    throws ServletException, IOException{
        
        String value = request.getParameter(key);
        
        if(value == null || value.trim().length() <= 0){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/error?error=Empty+Input+Value:+" + key);
            rd.forward(request, response);    
            return null;
        }
          
        return value;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        RequestDispatcher rd;
        if ("list".equalsIgnoreCase(action)) {
            // call the query db to get retrieve for all customer 
            ArrayList<CustomerBean> customers = db.queryCust();
            // set the result into the attribute	 
            request.setAttribute("customers", customers);
            // redirect the result to the listCustomers.jsp
            rd = getServletContext().getRequestDispatcher("/listCustomers.jsp");
            rd.forward(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            // get parameter, id, from the request
            String id = fromUrl(request, response, "id");
            if (id != null) {
                // call delete record method in the database
                db.delRecord(id);
                ArrayList<CustomerBean> customers = db.queryCust();
                request.setAttribute("customers", customers);
                // redirect the result to list action 
                if(customers.size() <= 0){
                    response.sendRedirect("welcome.jsp");
                }
                else{
                    rd = getServletContext().getRequestDispatcher("/listCustomers.jsp");
                    rd.forward(request, response);
                }
            }
        } else if ("getEditCustomer".equalsIgnoreCase(action)) {

            // obtain the parameter id;
            String id = fromUrl(request, response, "id");
            if (id != null) {
                // call  query db to get retrieve for a customer with the given id
                CustomerBean customer = db.queryCustByID(id);
                // set the customer as attribute in request scope
                request.setAttribute("c", customer);
            // forward the result to the editCustomer.jsp
                rd = getServletContext().getRequestDispatcher("/editCustomer.jsp");
                rd.forward(request, response);            
         }
        } else if ("search".equalsIgnoreCase(action)) { 

          // obtain the parameter name;
            String name = fromUrl(request, response, "name");

          if (name != null) {
            ArrayList<CustomerBean> customers;
            // call queryByName from db 
            // to retrieve for customers with the given name
            customers=db.queryCustByName(name);
           // set the result into the attribute in request
                request.setAttribute("customers", customers);
           
            // forward the result to the listCustoemrs.jsp
                rd = getServletContext().getRequestDispatcher("/listCustomers.jsp");
                rd.forward(request, response);            
         }
}


        else {
            PrintWriter out = response.getWriter();
            out.println("No such action!!!");
        }
    }
}
 */
