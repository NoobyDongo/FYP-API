/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.db;

import api.bean.ProductBean;
import api.util.SQLPair;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ison Ho
 */
public class ProductDB extends AbstractDB<ProductBean>{

    public ProductDB(String url, String uname, String pwd) throws SQLException {
        super(url, uname, pwd);
    }

    @Override
    protected ProductBean _create(ProductBean bean) {
        String sql = "insert into product values(default, ?, ?, ?)";
        try {
            String name = createText(bean._name).id;
            String desc = createText(bean._desc).id;
            
            helper.executeUpdate(sql, name, desc, bean.price + "");
            bean.id = helper.getGeneratedKeys() + "";
            helper.closePreparedStatement();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return bean;
    }

    @Override
    protected ProductBean _update(ProductBean bean) {
        String sql = "update product set `price` = ? where id = ?";
        try {
            if (bean._name.id == null || bean._desc.id == null) {
                throw new SQLException();
            }
            updateText(bean._name);
            updateText(bean._desc);

            helper.executeUpdate(sql, bean.price + "", bean.id);
            helper.closePreparedStatement();

            if (helper.rowCount < 1) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return bean;
    }
    
    @Override
    protected ArrayList<ProductBean> _query(String language, boolean allInfo, SQLPair... param){
        ArrayList<ProductBean> list = new ArrayList();
        String sql = helper.fillSqlCommand("select * from `product`", param);

        try {
            helper.executeQuery(sql, param);
            while (helper.rs.next()) {
                ProductBean bean = new ProductBean();
                bean.fill(helper.rs);
                String name = helper.rs.getString("name");
                String desc = helper.rs.getString("desc");

                if (language != null) {
                    bean._desc = getText(allInfo, desc, language);
                    bean._name = getText(allInfo, name, language);
                } else {
                    bean._desc = getText(allInfo, desc);
                    bean._name = getText(allInfo, name);
                }
                list.add(bean);
            }
            helper.closePreparedStatement();

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
}
