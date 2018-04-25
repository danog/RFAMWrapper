/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rfam.RFAMWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author daniil
 */
public class RFAMFilteredTable implements RFAMTableInterface {
    private RFAMTable table;
    private ArrayList<RFAMFilter> filters;
    
    public RFAMFilteredTable(RFAMTable table) {
        this.table = table;
    }
    
    public void applyFilter(RFAMFilter filter) {
        filters.add(filter);
    }
    public ResultSet select(String field) throws SQLException {
        return select(new String[] { field });
    }
    public ResultSet select(String[] fields) throws SQLException {
        String whereClause = "";
        for (RFAMFilter filter: filters) {
            whereClause += filter;
        }
        PreparedStatement statement = table.select(fields, whereClause);
        Integer index = 0;
        String parameter;
        for (RFAMFilter filter: filters) {
            while ((parameter = filter.getNext()) != null) {
                statement.setString(index++, parameter);
            }
        }
        return statement.executeQuery();
    }
 }
