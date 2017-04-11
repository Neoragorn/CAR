/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import static Persistence.PersistenceConnection.conn;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import models.User;

/**
 *
 * @author casier
 */
@Path("Inscription")
public class Inscription {

    @Context
    private UriInfo context;

    public Inscription() {
    }

    public static User getUser(String pseudo){
        try {
            User user = new User();
            String req = "SELECT * FROM User WHERE pseudo = ? ";
            PreparedStatement pss = conn.prepareStatement(req);
            pss.setString(1, pseudo);
            ResultSet rs = pss.executeQuery();
            rs.next();
            user.setIdUser(rs.getInt(1));
            user.setPseudo(rs.getString(2));
            user.setMail(rs.getString(4));
            return user;
        } catch (SQLException e) {
                System.out.println("Erreur dans le login et/ou le mot de passe" + e);
                return null;
        }
    }
    
    @GET
    @Produces("text/html")
    public String inscriptionForm() {
        return "<form action=\"Inscription/bdd\" method=\"POST\"> <label for=\"pseudo\">Pseudo :</label><input name=\"pseudo\" type=\"text\" id=\"pseudo\" required /><br />\n"
                + "<label for=\"Nom\">Nom :</label><input name=\"Nom\" type=\"text\" id=\"Nom\" required /><br />\n"
                + "<label for=\"Prenom\">Prenom :</label><input name=\"Prenom\" type=\"text\" id=\"Prenom\" required /><br />\n"
                + "<label for=\"Mail\">Mail :</label><input name=\"Mail\" type=\"email\" id=\"Mail\" required /><br />\n"
                + "<label for=\"password\">Mot de Passe :</label><input type=\"password\" name=\"password\" id=\"password\" required />\n <br/>"
                + "<input type=\"submit\" value=\"Inscription\" \n /> </form>";
    }

    @Produces("text/html")
    public String inscriptionFormRedone() {
        return "<form action=\"../Inscription/bdd\" method=\"POST\"> <label for=\"pseudo\">Pseudo :</label><input name=\"pseudo\" type=\"text\" id=\"pseudo\" required /><br />\n"
                + "<label for=\"Nom\">Nom :</label><input name=\"Nom\" type=\"text\" id=\"Nom\" required /><br />\n"
                + "<label for=\"Prenom\">Prenom :</label><input name=\"Prenom\" type=\"text\" id=\"Prenom\" required /><br />\n"
                + "<label for=\"Mail\">Mail :</label><input name=\"Mail\" type=\"email\" id=\"Mail\" required /><br />\n"
                + "<label for=\"password\">Mot de Passe :</label><input type=\"password\" name=\"password\" id=\"password\" required />\n <br/>"
                + "<input type=\"submit\" value=\"Inscription\" \n /> </form>";
    }
    
    @POST
    @Path("/bdd")
    @Produces("text/html")
    public String inscription(@FormParam("pseudo") String pseudo, @FormParam("Nom") String Nom, @FormParam("Prenom") String Prenom,
            @FormParam("password") String Password, @FormParam("Mail") String mail) {
        try {
            User user = getUser(pseudo);
            if (user != null)
            {
                return  "FIrst condition Error, user already existing" + inscriptionFormRedone();
            }
            user =  new User();
            user.setPrenom(Prenom);
            user.setNom(Nom);
            user.setPseudo(pseudo);
            user.setMail(mail);
            user.setPwd(Password);
            String req = "INSERT INTO User VALUES (?, ?, ?, ?, ?)";
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] result = mDigest.digest(user.getPwd().getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

            user.setPwd(sb.toString());
            PreparedStatement pss = conn.prepareStatement(req);
            pss.setInt(1, user.getIdUser());
            pss.setString(2, user.getPseudo());
            pss.setString(3, user.getPwd());
            pss.setString(4, user.getMail());
            pss.setDate(5, null);
            pss.executeUpdate();
            return "Inscription completed !" + new Connection().connectionFormAfterInscription(); 
        } catch (NoSuchAlgorithmException | SQLException ex) {
            System.out.println(ex);
            return " exception Error, user already existing" + inscriptionFormRedone();
        }
    }

}
