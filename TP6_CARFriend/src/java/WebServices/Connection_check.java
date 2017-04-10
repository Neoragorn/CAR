/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import models.User;

/**
 *
 * @author sofian
 */
@Path("Connection_check")
public class Connection_check {

    @Context
    private UriInfo context;

    public Connection_check() {
    }

    @GET
    public String connecting(@QueryParam("pseudo") String pseudo, @QueryParam("password") String pwd) throws NoSuchAlgorithmException {

        if (checkConnecting(pseudo, pwd)) {
            return "Checking info : " + pseudo + "   " + pwd;
        }
        return "Error on connecting. Wrong password or login" + new Connection().connectionForm();
    }

    public boolean checkConnecting(String pseudo, String password) throws NoSuchAlgorithmException {
        String req = "SELECT * FROM User WHERE pseudo = ? AND password = ? ";
        try {
            User user = new User();
            PreparedStatement pss = Persistence.PersistenceConnection.getConn().prepareStatement(req);
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] result = mDigest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            pss.setString(1, pseudo);
            pss.setString(2, sb.toString());
            ResultSet rs = pss.executeQuery();
            rs.next();
            user.setIdUser(rs.getInt(1));
            user.setPseudo(rs.getString(2));
            user.setMail(rs.getString(4));
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Connection_check.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
