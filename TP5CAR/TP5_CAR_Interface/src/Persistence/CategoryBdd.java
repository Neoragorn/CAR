/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Models.Category;
import Models.User;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author sofian
 */
public class CategoryBdd {

    private static final Connection conn = PersistenceConnection.getInstance().getConn();

    public static ArrayList<Category> getCategoryByUserId(int id) throws SQLException {
        ArrayList<Category> categories = new ArrayList();
        String req = "select cat.idCategorie, nom FROM CategorieUser cat "
                + "join AssoCategorieUser catAsso on cat.idCategorie = catAsso.idCategorie "
                + "join User user on catAsso.idUser = user.idUser "
                + "WHERE user.idUser = ? ";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, id);
        ResultSet rs = pss.executeQuery();
        while (rs.next()) {
            Category cat = new Category(rs.getInt(1), rs.getString(2));
            categories.add(cat);
        }
        return categories;
    }

    public static ArrayList<Category> getAllCategoryExceptUser(User user) throws SQLException {
        ArrayList<Category> categories = new ArrayList();
        String req = "select cat.idCategorie, nom FROM CategorieUser cat WHERE cat.idCategorie not in (select cat.idCategorie from CategorieUser cat "
                + "join AssoCategorieUser catAsso on cat.idCategorie = catAsso.idCategorie "
                + "join User user on catAsso.idUser = user.idUser where user.idUser = ?)";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, user.getIdUser());
        ResultSet rs = pss.executeQuery();
        while (rs.next()) {
            Category cat = new Category(rs.getInt(1), rs.getString(2));
            categories.add(cat);
        }
        return categories;
    }

    public static void insertAssoCategoryUser(User user, Category cat) throws SQLException {
        try {
            String req = "INSERT INTO AssoCategorieUser (idUser, idCategorie) VALUES (?, ?)";
            PreparedStatement pss = conn.prepareStatement(req);
            pss.setInt(1, user.getIdUser());
            pss.setInt(2, cat.getIdCategory());
            pss.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void deleteUserFromCategorie(User user, Category cat) throws SQLException {
        try
        {
        String req = "DELETE FROM AssoCategorieUser where idUser = ? AND idCategorie = ?";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, user.getIdUser());
        pss.setInt(2, cat.getIdCategory());
        pss.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
        public static Category getCategoryByName(String category) throws SQLException, NoSuchAlgorithmException {
        try {
            if (category.isEmpty()) {
                return null;
            }
            String req = "SELECT * FROM CategorieUser WHERE nom like ?";
            PreparedStatement pss = conn.prepareStatement(req);

            pss.setString(1, category);
            ResultSet rs = pss.executeQuery();
            rs.next();
            Category cat = new Category(rs.getInt(1), rs.getString(2));
            return cat;            
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
