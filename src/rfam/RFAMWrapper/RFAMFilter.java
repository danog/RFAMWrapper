/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rfam.RFAMWrapper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author daniil
 */
public class RFAMFilter {
    private String column;
    private ArrayList<String> contains = new ArrayList<String>();
    private ArrayList<String> equals = new ArrayList<String>();
    private Integer index = -1;
    
    public RFAMFilter(String column) {
        this.column = column;
    }
    public RFAMFilter() {
    }
    
    public RFAMFilter contains(char filter) {
        return contains(String.valueOf(filter));
    }
    public RFAMFilter contains(String filter) {
        contains.add(filter);
        return this;
    }

    public RFAMFilter equals(char filter) {
        return equals(String.valueOf(filter));
    }
    public RFAMFilter equals(String filter) {
        equals.add(filter);
        return this;
    }
    
    public String getColumn() {
        return column;
    }
    public ArrayList<String> getMatchFilters() {
        return contains;
    }
    public ArrayList<String> getEqualFilters() {
        return equals;
    }
    
    @Override
    public String toString() {
        String result[] = new String[contains.size() + (equals.isEmpty() ? 0 : 1)];
        if (!equals.isEmpty()) {
            String in[] = new String[equals.size()];
            Arrays.fill(in, "?");
            result[0] = column+" IN ("+String.join(", ", in)+")";
        }
        Arrays.fill(result, equals.isEmpty() ? 0 : 1, result.length, column+" LIKE ?");
        return String.join(" AND ", result);
    }
    public void beforeFirst() {
        index = -1;
    }
    public boolean next() {
        if (++index == equals.size() + contains.size()) {
            return false;
        }
        return true;
    }
    public String getParameter() {
        if (index < equals.size()) {
            return equals.get(index);
        }
        return '%'+contains.get(index - equals.size())+'%';
    }
    
    
    public int count(String value) {
        if (contains.isEmpty() && equals.isEmpty()) {
            return value.length();
        }
        
        if (equals.size() > 0) {
            boolean match = false;
            for (String equal: equals) {
                match = match || value.equals(equal);
            }

            if (!match) {
                return 0;
            }
        }
        
        int count = 0;
        for (String contain: contains) {
            count += value.split(contain).length-1;
        }
        
        return count;
    }
}
