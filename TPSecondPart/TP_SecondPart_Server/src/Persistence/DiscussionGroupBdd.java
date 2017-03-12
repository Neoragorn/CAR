/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Models.DiscussionGroup;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author sofian
 */
public class DiscussionGroupBdd {

    private static final Connection conn = PersistenceConnection.getInstance().getConn();

    public static boolean checkIfModerator(User user, DiscussionGroup discussion) throws SQLException {
        String req = "Select idCreator FROM DiscussionGroup Where idGroup = ?";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, discussion.getIdDiscussion());
        pss.executeQuery();
        ResultSet rs = pss.executeQuery();
        rs.next();
        if (user.getIdUser() == rs.getInt(1)) {
            return true;
        }

        return false;
    }

    public static void deleteUserFromDiscussionGroup(User user, DiscussionGroup discussion) throws SQLException {
        String req = "DELETE FROM AssoGroupUser where idUser = ? AND idGroup = ?";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, user.getIdUser());
        pss.setInt(2, discussion.getIdDiscussion());
        pss.executeUpdate();
    }

    public static void createDiscussionGroupBdd(int idCreator, String title, String description) throws SQLException {
        try {
            String req = "INSERT INTO DiscussionGroup VALUES (?, ?, ?, ?)";
            PreparedStatement pss = conn.prepareStatement(req);
            pss.setInt(1, 0);
            pss.setInt(2, idCreator);
            pss.setString(3, title);
            pss.setString(4, description);
            pss.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void insertAssoGroupUser(User user, DiscussionGroup discu) throws SQLException {
        try {
            String req = "INSERT INTO AssoGroupUser (idUser, idGroup) VALUES (?, ?)";
            PreparedStatement pss = conn.prepareStatement(req);
            pss.setInt(1, user.getIdUser());
            pss.setInt(2, discu.getIdDiscussion());
            pss.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static ArrayList<DiscussionGroup> getNotJoinedDiscussionGroupBdd(User user) throws SQLException {
        try {
            ArrayList<DiscussionGroup> discussionGroups = new ArrayList();
            System.out.println("entered not joined bdd");
            String req = "SELECT idGroup, idCreator, title, description FROM DiscussionGroup  discu "
                    + "WHERE idGroup NOT IN "
                    + "(SELECT discu2.idGroup FROM DiscussionGroup discu2 "
                    + "JOIN AssoGroupUser asso on asso.idGroup = discu2.idGroup WHERE asso.idUser = ? )";
            PreparedStatement pss = conn.prepareStatement(req);
            pss.setInt(1, user.getIdUser());
            pss.executeQuery();
            ResultSet rs = pss.executeQuery();
            while (rs.next()) {
                DiscussionGroup discussion = new DiscussionGroup();
                discussion.setIdDiscussion(rs.getInt(1));
                discussion.setIdCreator(rs.getInt(2));
                discussion.setTitle(rs.getString(3));
                discussion.setDescription(rs.getString(4));
                discussionGroups.add(discussion);
            }
            return discussionGroups;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static ArrayList<DiscussionGroup> getJoinedDiscussionGroupBdd(User user) throws SQLException {
        try {
            ArrayList<DiscussionGroup> discussionGroups = new ArrayList();

            String req = "SELECT discu.idGroup, discu.idCreator, title, description FROM DiscussionGroup discu "
                    + "JOIN AssoGroupUser asso on asso.idGroup = discu.idGroup "
                    + "WHERE asso.idUser = ?";
            PreparedStatement pss = conn.prepareStatement(req);
            pss.setInt(1, user.getIdUser());
            pss.executeQuery();
            ResultSet rs = pss.executeQuery();
            while (rs.next()) {
                DiscussionGroup discussion = new DiscussionGroup();
                discussion.setIdDiscussion(rs.getInt(1));
                discussion.setIdCreator(rs.getInt(2));
                discussion.setTitle(rs.getString(3));
                discussion.setDescription(rs.getString(4));
                discussionGroups.add(discussion);
            }
            return discussionGroups;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static ArrayList<User> getDiscussionGroupUserById(int id) throws SQLException {
        try {
            ArrayList<User> users = new ArrayList();

            String req = "SELECT u.idUser, u.pseudo, u.mail FROM User u "
                    + "join AssoGroupUser asso on asso.idUser = u.idUser "
                    + "join DiscussionGroup disc on disc.idGroup = asso.idGroup "
                    + "WHERE disc.idGroup = ?";
            PreparedStatement pss = conn.prepareStatement(req);
            pss.setInt(1, id);
            pss.executeQuery();
            ResultSet rs = pss.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
