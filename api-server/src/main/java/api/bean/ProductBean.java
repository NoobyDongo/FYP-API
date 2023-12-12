/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.bean;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ison Ho
 */


public class ProductBean extends AbstractBean{
    
    public String id;
    public double price;
    
    public TextBean _name;
    public TextBean _desc;
    
    public ProductBean(){
        
    }
    
    @Override
    public void fill(ResultSet rs) throws SQLException{
        id = rs.getString("id");
        price = rs.getDouble("price");
    }
}
