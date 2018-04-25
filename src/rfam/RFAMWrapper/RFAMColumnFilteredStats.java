/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rfam.RFAMWrapper;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author daniil
 */
public class RFAMColumnFilteredStats implements RFAMColumnStatsInterface {
    private RFAMColumnStats stats;
    private ArrayList<RFAMFilter> filters;

    public RFAMColumnFilteredStats(RFAMColumnStats stats) {
        this.stats = stats;
    }
    
    public void applyFilter(RFAMFilter filter) {
        filters.add(filter);
    }
    public int getCharCount() throws SQLException {
        return stats.getCharCount(filters);
    }
    public double getCharPercentage() throws SQLException {
        return stats.getCharCount()*100/stats.getCharCount(filters);
    }
}
