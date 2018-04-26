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
    private ArrayList<RFAMFilter> filters = new ArrayList<RFAMFilter>();

    public RFAMColumnFilteredStats(RFAMColumnStats stats) {
        this.stats = stats;
    }
    
    public void applyFilter(RFAMFilter filter) {
        filters.add(filter);
    }
    public int getCharCount() throws SQLException {
        return stats.getCharCount(filters.toArray(new RFAMFilter[filters.size()]));
    }
    public double getCharPercentage() throws SQLException {
        int total = stats.getCharCount();
        int partial = this.getCharCount();
        if (total == 0) {
            return 100;
        }
        return partial*100/total;
    }
}
