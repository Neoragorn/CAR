/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import static Persistence.PersistenceConnection.conn;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import models.Comment;
import models.Friend;
import models.Statut;
import models.User;

/**
 *
 * @author sofian
 */
@Path("Home")
public class Home {

    private User user;
    private String SearchResult = "";

    @Context
    private UriInfo context;

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    public Home() {
        this.user = Persistence.PersistenceConnection.getInstance().getUser();
    }

    public void insertNewCommentaire(String commentaire, String idStatut) {
        try {
            String req = "INSERT INTO Comment (auteur, comment, publication, idStatut) values (?, ?, ?, ?) ";
            PreparedStatement pss = conn.prepareStatement(req);
            Date aujourdhui = new Date();
            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
            pss.setString(1, this.user.getPseudo());
            pss.setString(2, commentaire);
            pss.setString(3, shortDateFormat.format(aujourdhui));
            pss.setInt(4, Integer.parseInt(idStatut));
            pss.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertNewStatut(String statut) {
        try {
            String req = "INSERT INTO Statut (auteur, statut, publication) values (?, ?, ?) ";
            PreparedStatement pss = conn.prepareStatement(req);
            Date aujourdhui = new Date();
            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
            pss.setString(1, this.user.getPseudo());
            pss.setString(2, statut);
            pss.setString(3, shortDateFormat.format(aujourdhui));
            pss.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<User> getUserBySearch(String pseudo) throws SQLException, NoSuchAlgorithmException {
        try {
            if (pseudo.isEmpty()) {
                return null;
            }
            String req = "SELECT idUser, pseudo, mail FROM User WHERE pseudo like ?";
            ArrayList<User> userList = new ArrayList();
            PreparedStatement pss = conn.prepareStatement(req);

            pss.setString(1, pseudo + '%');
            ResultSet rs = pss.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
                userList.add(u);
            }
            return userList;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
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

    public ArrayList<Statut> getAllStatut() throws SQLException {
        ArrayList<Statut> statutList = new ArrayList();

        String req = "Select idStatut, auteur, statut, publication "
                + "FROM Statut "
                + "ORDER BY publication DESC";
        PreparedStatement pss = conn.prepareStatement(req);
        ResultSet rs = pss.executeQuery();
        while (rs.next()) {
            Statut statut = new Statut(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4));
            ArrayList<Comment> listCom = getCommentByStatut(String.valueOf(rs.getInt(1)));
            statut.setCommentList(listCom);
            statutList.add(statut);
        }
        return statutList;
    }

    public ArrayList<Comment> getCommentByStatut(String idStatut) throws SQLException {
        ArrayList<Comment> commentList = new ArrayList();

        String req = "Select idComment, c.auteur, c.comment, c.publication "
                + "FROM Comment c "
                + "JOIN Statut st on st.idStatut = c.idStatut "
                + "WHERE c.idStatut = ? "
                + "ORDER BY publication";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, Integer.parseInt(idStatut));
        ResultSet rs = pss.executeQuery();
        while (rs.next()) {
            Comment comment = new Comment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4));
            commentList.add(comment);
        }
        return commentList;
    }

    @Produces("text/html")
    public String searchEngine() {
        return "<form action=\"Home/searchEngineResult\" method=\"POST\">"
                + "<label for=\"pseudo\">Pseudo :</label><input name=\"pseudo\" type=\"text\" id=\"pseudo\" /><br />\n"
                + "<input type=\"submit\" value=\"Searching User\" \n /> </form>";
    }

    @Produces("text/html")
    public String displayAddStatut() {
        return "<form action=\"Home/addStatut\" method=\"POST\">"
                + "<label for=\"Statut\">Statut :</label><input name=\"statut\" type=\"text\" id=\"statut\" /><br />\n"
                + "<input type=\"submit\" value=\"Add Statut\" \n /> </form>";
    }

    @Produces("text/html")
    public String displayStatut() throws SQLException {
        ArrayList<Statut> listStatut = getAllStatut();
        String str = "";
        for (Statut st : listStatut) {
            str += "<form action=\"Home/addComment\" method=\"POST\"> <table border=\"1\"><tr><td>" + this.user.getPseudo() + "   " + st.getPublication() + "</td></tr>";
            str += "<tr><td><b>Statut</b></td></tr><tr><td>" + st.getStatut() + " </td></tr><tr><td><b>Commentaires</b></td></tr>";
            for (Comment com : st.getCommentList()) {                
                str += "<tr><td>" + com.getAuteur() + " : " + com.getCommentaire() + "</td></tr>";
            }
            str += "<tr><td><input type=\"text\" name=\"idStatut\" value=\"" + st.getIdStatut()
                    + "\" hidden /> <label for=\"Commentaire\">Commentaire :</label><input name=\"commentaire\""
                    + "type=\"text\" id=\"commentaire\" required />"
                    + "<input type=\"submit\" name=\"Comment\" value=\"Comment\" \n /></td></tr>"
                    + "</table> </form> <br/>";
        }
        return str;
    }

    @GET
    @Produces("text/html")
    public String displayHome() {
        try {
            String str = "Pseudo : " + this.user.getPseudo() + " Mail : " + this.user.getMail() + "<br><br>";
            str += displayFriend() + "<br>";
            str += displayUsersNotFriend();
            str += searchEngine();
            str += SearchResult;
            str += displayAddStatut();
            str += displayStatut();
            this.SearchResult = "";
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
                        + " name=\"UserInfo\" value=\"" + fr.getPseudo() + "\"></td></tr>";
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
        String friendInfo = "<form action=\"Home/removeFriend\" method=\"POST\"><table border=\"1\"><tr><td colspan=\"4\">Friend List</td><td> Remove Friend </td></tr>";
        ArrayList<Friend> listFriend = getFriends(this.user);
        for (Friend fr : listFriend) {
            if (!fr.isConnected()) {
                friendInfo += "<tr><td>" + fr.getPseudo() + "</td><td>" + fr.getMail() + "</td>" + "<td>Deconnecte</td><td>Derniere connection : " + fr.getLastConnection() + "</td>" + "<td> <input type=\"submit\""
                        + " name=\"frInfo\" value=\"" + fr.getPseudo() + "\"></td></tr>";
            } else {
                friendInfo += "<tr><td>" + fr.getPseudo() + "</td><td>" + fr.getMail() + "</td>" + "<td>Connecte</td><td></td> <td> <input type=\"submit\""
                        + " name=\"frInfo\" value=\"" + fr.getPseudo() + "\"></td></tr>";
            }
        }
        friendInfo += "</table></form>";
        return friendInfo;
    }

    public static void updateFriendAsso(User user, Friend friend) throws SQLException {
        String req = "INSERT INTO Friend (idFriend, idUser) values (?, ?) ";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, friend.getIdFriend());
        pss.setInt(2, user.getIdUser());
        pss.executeUpdate();
    }

    public static void removeFriendAsso(User user, Friend friend) throws SQLException {
        String req = "DELETE FROM Friend WHERE idUser = ? AND idFriend = ? ";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, user.getIdUser());
        pss.setInt(2, friend.getIdFriend());
        pss.executeUpdate();
    }

    @POST
    @Produces("text/html")
    @Path("/addFriend")
    public Response addFriend(@FormParam("UserInfo") String friend) throws SQLException, NoSuchAlgorithmException {
        Friend fr = getFriendByPseudo(friend);
        updateFriendAsso(user, fr);
        return Response.seeOther(URI.create("/TP6_CARFriend/WebResource/Home")).build();
    }

    @POST
    @Produces("text/html")
    @Path("/addStatut")
    public Response addStatut(@FormParam("statut") String statut) throws SQLException, NoSuchAlgorithmException {
        insertNewStatut(statut);
        return Response.seeOther(URI.create("/TP6_CARFriend/WebResource/Home")).build();
    }

    @POST
    @Produces("text/html")
    @Path("/addComment")
    public Response addComment(@FormParam("commentaire") String commentaire, @FormParam("idStatut") String statut) throws SQLException, NoSuchAlgorithmException {
        System.out.println("Comment => " + commentaire + "  statutid => " + statut);
        insertNewCommentaire(commentaire, statut);
        return Response.seeOther(URI.create("/TP6_CARFriend/WebResource/Home")).build();
    }

    @POST
    @Produces("text/html")
    @Path("/removeFriend")
    public Response removeFriend(@FormParam("frInfo") String friend) throws SQLException, NoSuchAlgorithmException {
        Friend fr = getFriendByPseudo(friend);
        removeFriendAsso(user, fr);
        return Response.seeOther(URI.create("/TP6_CARFriend/WebResource/Home")).build();
    }

    @POST
    @Produces("text/html")
    @Path("/searchEngineResult")
    public String searchEngineResult(@FormParam("pseudo") String pseudo) {
        try {
            ArrayList<User> listUser = getUserBySearch(pseudo);
            for (User user : listUser) {
                SearchResult += user.getPseudo() + "<br>";
            }
            return SearchResult + "<form action=\"../Home\"> <input type=\"submit\" value=\"Retour à l'accueil\" \n /> </form>";
        } catch (Exception ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            return "Aucun resultat ! <form action=\"../Home\"> <input type=\"submit\" value=\"Retour à l'accueil\" \n /> </form>";
        }
    }
}
