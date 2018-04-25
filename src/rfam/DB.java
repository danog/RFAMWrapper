/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rfam;

import rfam.RFAMWrapper.RFAMFilter;
import rfam.RFAMWrapper.RFAMFilteredTable;
import rfam.RFAMWrapper.RFAMTable;
import rfam.RFAMWrapper.RFAMTableStats;
import rfam.RFAMWrapper.RFAMColumnStats;
import rfam.RFAMWrapper.RFAMWrapper;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rfam.RFAMWrapper.RFAMColumnFilteredStats;

/**
 *
 * @author daniil
 */
public class DB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            RFAMWrapper wrapper = new RFAMWrapper();
            
            RFAMTable table = new RFAMTable(wrapper, "family");
            RFAMFilteredTable filteredTable = new RFAMFilteredTable(table);
            
            RFAMTableStats tableStats = new RFAMTableStats(filteredTable);
            RFAMColumnStats columnStats = new RFAMColumnStats(tableStats, "description");
            
            RFAMColumnFilteredStats charStats = new RFAMColumnFilteredStats(columnStats);
            
            filteredTable.applyFilter(new RFAMFilter("author").equals("Moxon SJ"));
            filteredTable.applyFilter(new RFAMFilter("description").contains(' '));
            
            charStats.applyFilter(new RFAMFilter().contains('a'));
            
            System.out.println(String.format("Total char count: %d", columnStats.getCharCount()));
            System.out.println(String.format("Occurences of char 'a': %d", charStats.getCharCount()));
            System.out.println(String.format("Percentage of occurences of char 'a': %f", charStats.getCharPercentage()));
        } catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
