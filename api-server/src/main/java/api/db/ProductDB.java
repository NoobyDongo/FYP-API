/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.db;

import api.bean.ProductBean;
import api.bean.TextBean;
import api.util.SQLPair;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ison Ho
 */
public class ProductDB extends AbstractDB<ProductBean> {

    public ProductDB(String url, String uname, String pwd) throws SQLException {
        super(url, uname, pwd);
    }

    @Override
    protected ProductBean _create(ProductBean bean) {
        String sql = "insert into product values(default, ?, ?, ?,?,?,?)";
        try {
            String name = bean.name;
            String desc = bean.desc;
            String producttype = Double.toString(bean.producttype);
            String origin = Double.toString(bean.origin);
            // String name = createText(bean._name).id;
            // String desc = createText(bean._desc).id;

            // String producttypeid = createText(bean._producttypeid).id;
            // String originid = createText(bean._originid).id;
            String price = Double.toString(bean.price);
            helper.executeUpdate(sql, name, desc, price, "", producttype, origin + "");
            bean.id = helper.getGeneratedKeys() + "";
            helper.closePreparedStatement();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return bean;
    }

    @Override
    protected ProductBean _update(ProductBean bean) {
        String sql = "update product set `price` = ? , `name` = ? , `desc` = ? ,`producttypeid` = ? , `originid` = ?  where id = ?";
        try {

            helper.executeUpdate(sql, bean.price + "", bean.name, bean.desc, bean.producttype + "", bean.origin + "",
                    bean.id);
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
    protected ArrayList<ProductBean> _query(boolean allInfo, SQLPair... param) {
        ArrayList<ProductBean> list = new ArrayList();
        String sql = helper.fillSqlCommand("select * from `product`", param);

        try {
            helper.executeQuery(sql, param);
            while (helper.rs.next()) {
                ProductBean bean = new ProductBean();
                bean.fill(helper.rs);
                // String name = helper.rs.getString("name");
                // String desc = helper.rs.getString("desc");
                String producttypeid = helper.rs.getString("producttypeid");//
                String originid = helper.rs.getString("originid");//

                // bean._desc = getText(allInfo, desc);
                // bean._name = getText(allInfo, name);
                bean._producttypeid = getPText(allInfo, producttypeid);//
                bean._originid = getOText(allInfo, originid);//

                list.add(bean);
            }
            helper.closePreparedStatement();

        } catch (

        SQLException e) {
            System.out.println(e);
        }

        return list;
    }

}
