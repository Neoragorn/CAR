/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import static Persistence.PersistenceConnection.conn;
import WebServices.Home;
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
import models.Comment;
import models.Friend;
import models.Statut;
import models.User;

/**
 *
 * @author sofian
 */
public class AccessBdd {

    public static void insertNewCommentaire(String commentaire, String idStatut, User user) {
        try {
            String req = "INSERT INTO Comment (auteur, comment, publication, idStatut) values (?, ?, ?, ?) ";
            PreparedStatement pss = conn.prepareStatement(req);
            Date aujourdhui = new Date();
            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
            pss.setString(1, user.getPseudo());
            pss.setString(2, commentaire);
            pss.setString(3, shortDateFormat.format(aujourdhui));
            pss.setInt(4, Integer.parseInt(idStatut));
            pss.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertNewStatut(String statut, User user) {
        try {
            String req = "INSERT INTO Statut (auteur, statut, publication) values (?, ?, ?) ";
            PreparedStatement pss = conn.prepareStatement(req);
            Date aujourdhui = new Date();
            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
            pss.setString(1, user.getPseudo());
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

    public static Friend getFriendByPseudo(String pseudo) throws SQLException, NoSuchAlgorithmException {
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

    public static ArrayList<Friend> getFriends(User user, HttpServletRequest request) throws SQLException {
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

    public static ArrayList<User> getAllUser(User userOrigin) throws SQLException {
        ArrayList<User> userList = new ArrayList();

        String req = "Select idUser, pseudo, mail "
                + "FROM User "
                + "WHERE idUser not in "
                + "(SELECT u.idUser "
                + "FROM Friend f "
                + "JOIN User u ON f.idFriend = u.idUser "
                + "WHERE f.idUser = ?) AND idUser != ?";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, userOrigin.getIdUser());
        pss.setInt(2, userOrigin.getIdUser());
        ResultSet rs = pss.executeQuery();
        while (rs.next()) {
            User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
            userList.add(user);
        }
        return userList;
    }

    public static ArrayList<Comment> getCommentByStatut(String idStatut) throws SQLException {
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

    public static ArrayList<Statut> getAllStatut() throws SQLException {
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
}
