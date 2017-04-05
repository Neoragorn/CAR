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

    @GET
    @Produces("text/html")
    public String inscriptionForm() {
        return "<form action=\"Inscription/bdd\" method=\"POST\"> <label for=\"pseudo\">Pseudo :</label><input name=\"pseudo\" type=\"text\" id=\"pseudo\" /><br />\n"
                + "<label for=\"Nom\">Nom :</label><input name=\"Nom\" type=\"text\" id=\"Nom\" /><br />\n"
                + "<label for=\"Prenom\">Prenom :</label><input name=\"Prenom\" type=\"text\" id=\"Prenom\" /><br />\n"
                + "<label for=\"Mail\">Mail :</label><input name=\"Mail\" type=\"email\" id=\"Mail\" /><br />\n"
                + "<label for=\"password\">Mot de Passe :</label><input type=\"password\" name=\"password\" id=\"password\" />\n <br/>"
                + "<input type=\"submit\" value=\"Inscription\" \n /> </form>";
    }

    @POST
    @Path("/bdd")
    @Produces("text/html")
    public String inscription(@FormParam("pseudo") String pseudo, @FormParam("Nom") String Nom, @FormParam("Prenom") String Prenom,
            @FormParam("password") String Password, @FormParam("Mail") String mail) {
        try {
            System.out.println(pseudo + Nom + Prenom + Password + mail);
            User user = new User();
            user.setPrenom(Prenom);
            user.setNom(Nom);
            user.setPseudo(pseudo);
            user.setMail(mail);
            user.setPwd(Password);
            String req = "INSERT INTO User VALUES (?, ?, ?, ?)";
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
            pss.executeUpdate();
            return "Inscription completed !";
        } catch (NoSuchAlgorithmException | SQLException ex) {
            System.out.println(ex);
            return "Error, user already existing";
        }
    }

}
