package util;
import java.sql.Connection;
import java.sql.SQLException;
import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtil {
    private static DBUtil dbPool;
    private static ComboPooledDataSource dataSource;

    static {
        dbPool = new DBUtil();
    }

    public DBUtil() {
        try {
            dataSource = new ComboPooledDataSource();
            dataSource.setUser("root");
            dataSource.setPassword("123456");
            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/shorttermtask?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=utf-8");
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            dataSource.setInitialPoolSize(2);
            dataSource.setMinPoolSize(1);
            dataSource.setMaxPoolSize(10);
            dataSource.setMaxStatements(50);
            dataSource.setMaxIdleTime(60);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }

    public final static DBUtil getInstance() {
        return dbPool;
    }

    public final static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("无法从数据源获取连接 ", e);
        }
    }

}