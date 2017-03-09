/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Models.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author casier
 */
public class MessageBdd {

    private static final Connection conn = PersistenceConnection.getInstance().getConn();

    static public void insertMessage(Message msg) throws SQLException {
        String req = "INSERT INTO PrivateMessage (idAuteur, idDestinataire, message, date) VALUES (?, ?, ?, ?)";

        PreparedStatement pss = conn.prepareStatement(req);
        pss.setInt(1, msg.getAuteur().getIdUser());
        pss.setInt(2, msg.getDestinataire().getIdFriend());
        pss.setString(3, msg.getMessage());
        pss.setDate(4, msg.getDate());
        pss.executeUpdate();
    }
}
