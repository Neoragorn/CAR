/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import static Persistence.PersistenceConnection.conn;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import models.User;

/**
 *
 * @author sofian
 */
@Path("Connection_check")
public class Connection_check {

    private User user;

    @Context
    private UriInfo context;

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    public Connection_check() {
    }

    public static void updateConnection(User user) throws SQLException {
        String req = "UPDATE User SET LastConnection = ? WHERE idUser = ?";
        Date aujourdhui = new Date();
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setString(1, shortDateFormat.format(aujourdhui));
        pss.setInt(2, user.getIdUser());
        pss.executeUpdate();
    }

    @GET
    public Response connecting(@QueryParam("pseudo") String pseudo, @QueryParam("password") String pwd) throws NoSuchAlgorithmException, SQLException {

        if (checkConnecting(pseudo, pwd)) {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] result = mDigest.digest(pwd.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            response.addCookie(new javax.servlet.http.Cookie(pseudo, sb.toString()));
            updateConnection(user);
            Persistence.PersistenceConnection.getInstance().setUser(user);
            return Response.seeOther(URI.create("/TP6_CARFriend/WebResource/Home")).build();
        }
        return Response.seeOther(URI.create("/TP6_CARFriend/WebResource/Connection")).build();
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
            this.user = user;
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Connection_check.class
                    .getName()).log(Level.SEVERE, null, ex);

            return false;
        }
    }
}
