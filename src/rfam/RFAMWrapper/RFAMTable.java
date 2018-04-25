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
public class RFAMTable implements RFAMTableInterface, RFAMWrapperInterface {
    private RFAMWrapper wrapper;
    private String table;
    public RFAMTable(RFAMWrapper wrapper, String table) {
        this.wrapper = wrapper;
        this.table = table;
    }
    
    public RFAMFilteredTable getFilteredTable() {
        return new RFAMFilteredTable(this);
    }
    
    protected PreparedStatement select(String[] fields, String whereClause) throws SQLException {
        return wrapper.select(fields, table, whereClause);
    }
    public ResultSet select(String[] fields) throws SQLException {
        return wrapper.select(fields, table);
    }    
    public ResultSet select(String field) throws SQLException {
        return wrapper.select(field, table);
    }

}
