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
    private ArrayList<RFAMFilter> filters = new ArrayList<RFAMFilter>();
    
    public RFAMFilteredTable(RFAMTable table) {
        this.table = table;
    }
    
    public void applyFilter(RFAMFilter filter) {
        filters.add(filter);
    }
    @Override
    public ResultSet select(String field) throws SQLException {
        return select(new String[] { field });
    }
    @Override
    public ResultSet select(String[] fields) throws SQLException {
        String whereClauses[] = new String[filters.size()];
        for (int index = 0; index < filters.size(); index++) {
            whereClauses[index] = String.valueOf(filters.get(index));
        }
        PreparedStatement statement = table.select(fields, String.join(" AND ", whereClauses));
        Integer index = 1;
        for (RFAMFilter filter: filters) {
            while (filter.next()) {
                statement.setString(index++, filter.getParameter());
            }
            filter.beforeFirst();
        }
        return statement.executeQuery();
    }
 }
