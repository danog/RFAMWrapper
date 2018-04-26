/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rfam.RFAMWrapper;

import java.util.ArrayList;

/**
 *
 * @author daniil
 */
public class RFAMFilter {
    private String column;
    private ArrayList<String> contains = new ArrayList<String>();
    private ArrayList<String> equals = new ArrayList<String>();
    private Integer index = 0;
    
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
        String result = "";
        String and = "";
        if (equals.size() > 0) {
            result += column+" IN (";
            for (String equal: equals) {
                result += "?, ";
            }
            result += ")";
            if (contains.size() > 0) {
                and = " AND ";
            }
        }
        for (String match: contains) {
            result += and+column+" LIKE %?%";
            and = " AND ";
        }
        return result;
    }
    public String getNext() {
        if (index < contains.size()) {
            return contains.get(index++);
        }
        Integer newindex = index - contains.size();
        if (newindex < equals.size()) {
            index = 0;
            return null;
        }
        index++;
        return equals.get(newindex);
    }
    
    
    public int count(String value) {
        if (contains.size() == 0 && equals.size() == 0) {
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
