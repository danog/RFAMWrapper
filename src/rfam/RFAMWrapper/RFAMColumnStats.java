/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rfam.RFAMWrapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author daniil
 */
public class RFAMColumnStats implements RFAMColumnStatsInterface {
    private ResultSet result;
    private String column;
    public RFAMColumnStats(RFAMTableStats stats, String column) throws SQLException {
        this.result = stats.getTable().select(column);
        this.column = column;
    }
    public int getCharCount() throws SQLException {
        return getCharCount(new RFAMFilter[] { new RFAMFilter() });
    }
    public int getCharCount(RFAMFilter[] filters) throws SQLException {
        int count = 0;
        String value;
        while (result.next()) {
            value = result.getString(column);
            for (RFAMFilter filter: filters) {
                count += filter.count(value);
            }
        }
        result.first();
        return count;
    }
}
