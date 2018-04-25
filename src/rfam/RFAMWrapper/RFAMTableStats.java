/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rfam.RFAMWrapper;

import java.sql.SQLException;

/**
 *
 * @author daniil
 */
public class RFAMTableStats {
    private RFAMTableInterface table;
    public RFAMTableStats(RFAMTableInterface table) {
        this.table = table;
    }
    public RFAMTableInterface getTable() {
        return table;
    }
    public RFAMColumnStats getColumn(String column) throws SQLException {
        return new RFAMColumnStats(this, column);
    }
}
