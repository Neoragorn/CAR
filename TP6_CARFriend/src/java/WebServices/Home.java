/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import static Persistence.PersistenceConnection.conn;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import models.Friend;
import models.User;

/**
 *
 * @author sofian
 */
@Path("Home")
public class Home {

    private User user;

    @Context
    private UriInfo context;

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    public Home() {
        this.user = Persistence.PersistenceConnection.getInstance().getUser();
    }

    public Friend getFriendByPseudo(String pseudo) throws SQLException, NoSuchAlgorithmException {
        try {
            String req = "SELECT idUser, pseudo, mail, LastConnection FROM User WHERE pseudo = ?";
            PreparedStatement pss = conn.prepareStatement(req);
            pss.setString(1, pseudo);
            ResultSet rs = pss.executeQuery();
            rs.next();
            Friend fr = new Friend(rs.getInt(1), rs.getString(2), rs.getString(3));
            fr.setLastConnection(rs.getString(4));
            return fr;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Friend> getFriends(User user) throws SQLException {
        ArrayList<Friend> friends = new ArrayList();
        String req = "SELECT u.idUser, u.pseudo, u.mail, u.LastConnection "
                + "FROM Friend f "
                + "JOIN User u ON f.idFriend = u.idUser "
                + "WHERE f.idUser = ?;";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, user.getIdUser());
        ResultSet rs = pss.executeQuery();
        Cookie[] cookies = request.getCookies();
        while (rs.next()) {
            Friend friend = new Friend();
            friend.setIdFriend(rs.getInt(1));
            friend.setPseudo(rs.getString(2));
            friend.setMail(rs.getString(3));
            friend.setLastConnection(rs.getString(4));
            for (Cookie co : cookies) {
                System.out.println(co.getName());
                if (co.getName().equals(friend.getPseudo())) {
                    friend.setConnected(true);
                }
            }
            friends.add(friend);
        }
        return friends;
    }

    public ArrayList<User> getAllUser() throws SQLException {
        ArrayList<User> userList = new ArrayList();

        String req = "Select idUser, pseudo, mail "
                + "FROM User "
                + "WHERE idUser not in "
                + "(SELECT u.idUser "
                + "FROM Friend f "
                + "JOIN User u ON f.idFriend = u.idUser "
                + "WHERE f.idUser = ?) AND idUser != ?";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, user.getIdUser());
        pss.setInt(2, user.getIdUser());
        ResultSet rs = pss.executeQuery();
        while (rs.next()) {
            User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
            userList.add(user);
        }
        return userList;
    }

    @GET
    @Produces("text/html")
    public String displayHome() {
        try {
            String str = "Pseudo : " + this.user.getPseudo() + " Mail : " + this.user.getMail() + "<br><br>";
            str += displayFriend() + "<br>";
            str += displayUsersNotFriend();
            return str;
        } catch (Exception ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            return "Error on display";
        }
    }

    @Produces("text/html")
    public String displayUsersNotFriend() {
        try {
            ArrayList<User> listUser = getAllUser();
            String userInfo = "<form action=\"Home/addFriend\" method=\"POST\"><table border=\"1\"><tr><td colspan=\"3\">User List</td></tr>";
            for (User fr : listUser) {
                userInfo += "<tr><td>" + fr.getPseudo() + "</td><td>"
                        + fr.getMail() + "</td><td> <input type=\"submit\""
                        + " name=\"frInfo\" value=\"" + fr.getPseudo() + "\"></td></tr>";
            }
            userInfo += "</table></form>";
            return userInfo;
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            return "Error on getallUser";
        }
    }

    @Produces("text/html")
    public String displayFriend() throws SQLException {
        String friendInfo = "<table border=\"1\"><tr><td colspan=\"4\">Friend List</td></tr>";
        ArrayList<Friend> listFriend = getFriends(this.user);
        for (Friend fr : listFriend) {
            if (!fr.isConnected()) {
                friendInfo += "<tr><td>" + fr.getPseudo() + "</td><td>" + fr.getMail() + "</td>" + "<td>Deconnecte</td><td>Derniere connection : " + fr.getLastConnection() + "</td>" + "</tr>";
            } else {
                friendInfo += "<tr><td>" + fr.getPseudo() + "</td><td>" + fr.getMail() + "</td>" + "<td>Connecte</td><td></td</tr>";
            }
        }
        friendInfo += "</table>";
        return friendInfo;
    }

     public static void updateFriendAsso(User user, Friend friend) throws SQLException {
        String req = "INSERT INTO Friend (idFriend, idUser) values (?, ?) ";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, friend.getIdFriend());
        pss.setInt(2, user.getIdUser());
        pss.executeUpdate();
    }
     
    @POST
    @Produces("text/html")
    @Path("/addFriend")
    public String addFriend(@FormParam("frInfo") String friend) throws SQLException, NoSuchAlgorithmException {
       System.out.println("ENTRERED ADDFIRNED");
        Friend fr = getFriendByPseudo(friend);
        updateFriendAsso(user, fr);
        return displayHome();
    }
}
