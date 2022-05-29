/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import Entity.Person;
import Entity.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ash
 */
public class UserRepo {
        DBconnection dbc;
    
    public UserRepo(){
        dbc = new DBconnection();
    }
    
    public User getUser(String username,String password){
        User user = null;
        
        dbc.openConnection();
        try {
            dbc.result = dbc.st.executeQuery("select * from auth where username='"+username+"' and password='"+password+"'");
            while(dbc.result.next()){
                user = new User();
                user.setUserId(dbc.result.getString("username"));
                user.setPassword(dbc.result.getString("password"));
                user.setRole(dbc.result.getString("role"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        dbc.closeConnection();
        return user;
    }
    
    public User getUser(String username){
        User user = null;
        
        dbc.openConnection();
        try {
            dbc.result = dbc.st.executeQuery("select * from auth where username='"+username+"'");
            while(dbc.result.next()){
                user = new User();
                user.setUserId(dbc.result.getString("username"));
                user.setPassword(dbc.result.getString("password"));
                user.setRole(dbc.result.getString("role"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        dbc.closeConnection();
        return user;
    }
    
    public String getRole(String username,String password){
        User user = getUser(username, password);
        return user.getRole();
    }
    
    public void updatePassword(String username,String password){
        User user = getUser(username);
        if(user != null){
            dbc.openConnection();
            try {
                dbc.st.executeUpdate("replace into auth values('"+username+"','"+password+"','"+user.getRole()+"')");
            } catch (SQLException ex) {
                Logger.getLogger(UserRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dbc.closeConnection();
    }
    
    public ArrayList<Integer> getOrders(User user){
        ArrayList<Integer> ids = new ArrayList<>();
        dbc.openConnection();
        try {
            dbc.result = dbc.st.executeQuery("select * from orders where username='"+user.getUserId()+"'");
            while(dbc.result.next()){
                ids.add(dbc.result.getInt("orderId"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        dbc.closeConnection();
        return ids;
    }
    
    public Person getPersonDetails(String username){
        Person person = null;
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
    
    public Person getPersonDetails(User user){
        return getPersonDetails(user.getUserId());
    }
}
