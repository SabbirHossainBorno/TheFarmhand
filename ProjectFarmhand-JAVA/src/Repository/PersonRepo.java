/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import Entity.Person;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ash
 */
public class PersonRepo {
    DBconnection dbc;

    public PersonRepo() {
        dbc = new DBconnection();
    }
    
    public Person getPersonDetails(String username){
        Person person = new Person();
        dbc.openConnection();
            try {
                dbc.result = dbc.st.executeQuery("select * from person where username='"+username+"'");
                if(dbc.result.next()){
                    person.setName(dbc.result.getString("name"));
                    person.setAddress(dbc.result.getString("address"));
                    person.setDateOfBirth(dbc.result.getString("name"));
                    person.setEmail(dbc.result.getString("email"));
                    person.setMobile(dbc.result.getString("mobile"));
                    person.setTradeLicenseNo(dbc.result.getString("tradeLicNo"));
                    person.setNid(dbc.result.getString("nid"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
            dbc.closeConnection();
        return person;
    }
    
    
}
