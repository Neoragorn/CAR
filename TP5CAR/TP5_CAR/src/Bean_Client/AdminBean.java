package Bean_Client;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Models.User;
import Persistence.AdminBdd;

public class AdminBean extends UserBean{
	
	public AdminBean() {
		
	}

	 public static AdminBean inst;

	    static public AdminBean getInstance() {
	        if (inst == null) {
	            inst = new AdminBean();
	        }
	        return inst;
	    }
	    
	    public static void deleteUser(User user) throws SQLException {
	        AdminBdd.deleteUserAndLinks(user);
	    }
	    
	    public static void ModifyUser(User user, String newPseudo, String newMail, String newPassword) throws SQLException, NoSuchAlgorithmException {
	       AdminBdd.modifyUser(user, newPseudo, newMail, newPassword);
	    }

		public static void CreateUser(String pseudo, String password, String mail) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
			AdminBdd.createUser(pseudo, password, mail);
		}
}
