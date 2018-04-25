/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rfam.RFAMWrapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author daniil
 */
public interface RFAMTableInterface {
    public ResultSet select(String[] fields) throws SQLException;
    public ResultSet select(String field) throws SQLException;
}