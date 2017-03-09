package Persistence;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Models.User;

public class AdminBdd extends UserBdd {
	
    private static final Connection conn = PersistenceConnection.getInstance().getConn();

    public static void modifyUser(User user, String newPseudo, String newMail, String newPassword) throws SQLException, NoSuchAlgorithmException {
        String req = " UPDATE User SET pseudo = ?, mail = ?, password = ? WHERE idUser = ? ";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setString(1, newPseudo);
        pss.setString(2, newMail);
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(newPassword.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        pss.setString(3, sb.toString());
        pss.setInt(4, user.getIdUser());
        pss.executeUpdate();
        System.out.println("user updated");
    }
    
    public static void deleteUserAndLinks(User user) throws SQLException {
        String req = "DELETE FROM User WHERE idUser = ? ";
        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, user.getIdUser());
        pss.executeUpdate();
        String req2 = "DELETE FROM Friend WHERE idFriend = ? ";
        PreparedStatement pss2 = conn.prepareStatement(req2);
        pss2.setInt(1, user.getIdUser());
        pss2.executeUpdate();

    }
    
    public static void createUser(String pseudo, String password, String mail) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String req = "INSERT INTO User (pseudo, password, mail) VALUES ( ?, ?, ?)";
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        PreparedStatement pss = conn.prepareStatement(req);
        pss.setString(1, pseudo);
        pss.setString(2, sb.toString());
        pss.setString(3, mail);
        pss.executeUpdate();
    }
    

}
