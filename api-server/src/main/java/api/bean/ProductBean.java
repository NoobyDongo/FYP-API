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

public class ProductBean extends AbstractBean {

    public String id;
    public double price;
    public String name;
    public String desc;
    public TextBean _name;
    public TextBean _desc;
    public TextBean _producttypeid;
    public TextBean _originid;

    public int producttype;
    public int origin;

    public ProductBean() {

    }

    @Override
    public void fill(ResultSet rs) throws SQLException {
        id = rs.getString("id");
        price = rs.getDouble("price");
        name = rs.getString("name");
        desc = rs.getString("desc");
    }
}
