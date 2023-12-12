/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.db;

import api.bean.AbstractBean;
import api.bean.TextBean;
import api.bean.TranslatedTextBean;
import api.util.SQLPair;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Ison Ho
 * @param <T>
 */
public abstract class AbstractDB<T extends AbstractBean> {

    protected interface Action<O, I> {

        O run(I input) throws SQLException;
    }

    protected class Helper {

        public Connection cnnct;
        public Statement st;
        public PreparedStatement pt;
        public ResultSet rs;
        public int rowCount = -1;

        public void closePreparedStatement() {
            try {
                if (pt != null) {
                    pt.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        public Helper newPreparedStatement(String placeholder) throws SQLException {
            closePreparedStatement();
            pt = cnnct.prepareStatement(placeholder, Statement.RETURN_GENERATED_KEYS);
            return this;
        }

        public Helper newPreparedStatement(String placeholder, String... s) throws SQLException {
            newPreparedStatement(placeholder);
            fillPreparedStatement(s);
            return this;
        }

        public Helper newPreparedStatement(String placeholder, SQLPair... s) throws SQLException {
            newPreparedStatement(placeholder);
            fillPreparedStatement(s);
            return this;
        }

        public void fillPreparedStatement(String... s) throws SQLException {
            for (int i = 0, c = 1; i < pt.getParameterMetaData().getParameterCount() && i < s.length; i++) {
                pt.setString(c++, s[i]);
            }
        }

        public void fillPreparedStatement(SQLPair... s) throws SQLException {
            for (int i = 0, c = 1; i < pt.getParameterMetaData().getParameterCount() && i < s.length; i++) {
                pt.setString(c++, s[i].toString());
            }
        }

        public Helper() throws SQLException {
            cnnct = getConnection();  // the connection 
            st = cnnct.createStatement();  // create statement
        }

        public void execute(String sql) throws SQLException {
            System.out.println(st);
            st.execute(sql);
        }

        public void executeUpdate() throws SQLException {
            System.out.println(pt);
            rowCount = pt.executeUpdate();
        }

        public void executeQuery() throws SQLException {
            System.out.println(pt);
            rs = pt.executeQuery();
        }

        public void executeQuery(String placeholder, String... s) throws SQLException {
            newPreparedStatement(placeholder, s);
            executeQuery();
        }

        public void executeQuery(String placeholder, SQLPair... s) throws SQLException {
            newPreparedStatement(placeholder, s);
            executeQuery();
        }

        public void executeUpdate(String placeholder, String... s) throws SQLException {
            newPreparedStatement(placeholder, s);
            executeUpdate();
        }

        public void executeUpdate(String placeholder, SQLPair... s) throws SQLException {
            newPreparedStatement(placeholder, s);
            executeUpdate();
        }

        public String getGeneratedKeys() throws SQLException {
            ResultSet generatedKeys = pt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1) + "";
            } else {
                return null;
            }
        }

        public String fillSqlCommand(String sql, SQLPair... param) {
            if (param.length > 0) {
                sql += " where " + param[0].toCondition(true);
                for (int i = 1; i < param.length; i++) {
                    sql += param[i].toCondition();
                }
            }
            return sql;
        }
    }

    protected final String uname, pwd, url;
    protected final Helper helper;
    private final Helper textHelper;

    public AbstractDB(String url, String uname, String pwd) throws SQLException {
        this.pwd = pwd;
        this.url = url;
        this.uname = uname;
        helper = new Helper();
        textHelper = new Helper();
    }

    private Connection getConnection() throws SQLException {
        try {
            //System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return DriverManager.getConnection(url, uname, pwd);
    }

    private TextBean _getText(String sql, boolean fullInfo, String... param) throws SQLException {
        TextBean bean = new TextBean();
        textHelper.executeQuery(sql, param);
        if (textHelper.rs.next()) {
            bean = new TextBean(textHelper.rs.getString("id"), textHelper.rs.getString("desc"));
        }

        if (fullInfo) {
            String newSql = "select `languageid`, `desc` from text_language where `textid` = ?";
            textHelper.executeQuery(newSql, param[0]);
            while (textHelper.rs.next()) {
                bean.translation.add(new TranslatedTextBean(textHelper.rs.getString("languageid"), textHelper.rs.getString("desc")));
            }
        }
        textHelper.closePreparedStatement();
        return bean;
    }

    public final TextBean getText(boolean allInfo, String id) throws SQLException {
        String sql = "select * from `text` where `id` = ?";
        return _getText(sql, allInfo, id);
    }

    public final TextBean getText(boolean allInfo, String id, String languageId) throws SQLException {
        String sql = "select `id`, `text_language`.`desc` from `text` inner join `text_language` on `id` = `textid` where `id` = ? and `languageid` = ?";
        return _getText(sql, allInfo, id, languageId);
    }

    private TextBean _setText(String sql, String subSql, TextBean bean, ProductDB.Action<String[], String[]> action, String... param) throws SQLException {
        textHelper.executeUpdate(sql, param);
        if (textHelper.rowCount < 1) {
            throw new SQLException();
        }

        String id = textHelper.getGeneratedKeys();
        if (id != null) {
            bean.id = id;
        }

        textHelper.newPreparedStatement(subSql);
        for (var e : bean.translation) {
            textHelper.fillPreparedStatement(action.run(new String[]{bean.id, e.languageId, e.desc}));
            textHelper.executeUpdate();
        }
        textHelper.closePreparedStatement();
        return bean;
    }

    public final TextBean updateText(TextBean bean) throws SQLException {
        String sql = "update `text` set `desc` = ? where id = ?";
        String subSql = "update text_language set `desc` = ? where textid = ? and languageid = ?";
        ProductDB.Action a = (ProductDB.Action<String[], String[]>) ((s) -> {
            return new String[]{s[2], s[0], s[1]
            };
        });
        return _setText(sql, subSql, bean, a, bean.desc, bean.id);
    }

    public final TextBean createText(TextBean bean) throws SQLException {
        String sql = "insert into text values(default, ?)";
        String subSql = "insert into text_language values(?, ?, ?)";
        ProductDB.Action a = (ProductDB.Action<String[], String[]>) ((s) -> {
            return new String[]{s[0], s[1], s[2]
            };
        });
        return _setText(sql, subSql, bean, a, bean.desc, bean.id);

    }

    protected abstract T _create(T bean) throws SQLException;

    protected abstract T _update(T bean) throws SQLException;

    public final T create(T bean) {
        try {
            return _create(bean);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public final T update(T bean) {
        try {
            T b = _update(bean);
            if (helper.rowCount < 1) {
                throw new SQLException();
            }
            return b;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    protected abstract ArrayList<T> _query(String language, boolean allInfo, SQLPair... pairs);

    public final T queryById(String language, boolean allInfo, String id) {
        return _query(language, allInfo, new SQLPair("id", id)).get(0);
    }

    public final ArrayList<T> queryByColumns(String language, boolean allInfo, SQLPair... pairs) {
        return _query(language, allInfo, pairs);
    }
}
