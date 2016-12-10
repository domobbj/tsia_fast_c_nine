package com.domob.hot.select.bolts.settle;


import com.domob.hot.select.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

class DatabaseProcessor {

    private Map _config;
    private static Logger _log = LoggerFactory.getLogger(DatabaseProcessor.class);
    private Connection conn;
    private Statement st;

    DatabaseProcessor(Map config) {
        _config = config;
        try {
            conn = getConnection();
        } catch (SQLException e) {
            _log.error(e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        String dbUrl = String.format(
                "jdbc:mysql://%s:%s/%s",
                _config.get("db.tsia.host").toString(),
                _config.get("db.tsia.port").toString(),
                _config.get("db.tsia.database").toString()
        );

        conn = DriverManager.getConnection(
                dbUrl,
                _config.get("db.tsia.user").toString(),
                _config.get("db.tsia.password").toString()
        );
        return conn;
    }

    /**
     * 插入数据
     */
    boolean insert(String key) {
        try {
            String sql = String.format(
                    "insert into hot_select (`key`, create_time, dt, hr) values ('%s', %s, %s, %s)",
                    key,
                    Utils.now(),
                    Utils.getDt(),
                    Utils.getHr()
            );
            st = conn.createStatement();
            int num = st.executeUpdate(sql);
            _log.info("insert into hot_select " + num + " records.");
            st.close();
            return true;
        } catch (SQLException e) {
            _log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 长链接 不太需要关闭
     */
    public void close() {
        try {
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
