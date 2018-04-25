/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rfam.RFAMWrapper;
import java.sql.*;

/**
 *
 * @author daniil
 */
public class RFAMWrapper implements RFAMWrapperInterface {
    private final String driver = "com.mysql.jdbc.driver";
    private final String URL = "jdbc:mysql://mysql-rfam-public.ebi.ac.uk:4497/Rfam";
    private final String username = "rfamro";
    private final String password = "";
    
    private Connection connection;
    public RFAMWrapper() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        Class.forName(driver).newInstance();
        connection = DriverManager.getConnection(URL, username, password);
    }
    
    public RFAMTable getTable(String table) {
        return new RFAMTable(this, table);
    }
    public RFAMFilteredTable getFilteredTable(String table) {
        return new RFAMFilteredTable(new RFAMTable(this, table));
    }
    
    protected PreparedStatement select(String field, String fromTable, String whereClause) throws SQLException {
        return select(new String[] { field }, fromTable, whereClause);
    }
    protected PreparedStatement select(String[] fields, String fromTable, String whereClause) throws SQLException {
        String field = String.join(", ", fields);
        if (whereClause.length() > 0) {
            whereClause = " WHERE "+whereClause;
        }
        return connection.prepareStatement("SELECT "+field+" FROM "+fromTable+whereClause);
    }
    
    public ResultSet select(String field, String fromTable) throws SQLException {
        return select(new String[] { field }, fromTable);        
    }
    public ResultSet select(String[] fields, String fromTable) throws SQLException {
        String field = String.join(", ", fields);
        return connection.prepareStatement("SELECT "+field+" FROM "+fromTable).executeQuery();
    }
}
