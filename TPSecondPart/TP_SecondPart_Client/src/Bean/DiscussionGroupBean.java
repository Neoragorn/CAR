/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Models.DiscussionGroup;
import Models.MessageDiscussion;
import Models.User;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author sofian
 */
public class DiscussionGroupBean {

    private ArrayList<DiscussionGroup> notJoinedDiscussionGroup = new ArrayList();
    private ArrayList<DiscussionGroup> joinedDiscussionGroup = new ArrayList();
    private DiscussionGroup discussion;
    private boolean moderator = false;

    public static DiscussionGroupBean inst;

    static public DiscussionGroupBean getInstance() {
        if (inst == null) {
            inst = new DiscussionGroupBean();
        }
        return inst;
    }
    
    public void checkUserModerator() throws SQLException {
        if (this.discussion.getIdCreator() == UserBean.getInstance().getUser().getIdUser()) {
            this.moderator = true;
        }
    }

/*    public void destroyUserFromDiscussion(User user) throws SQLException {
        DiscussionGroupBdd.deleteUserFromDiscussionGroup(user, discussion);
    }*/

    public ArrayList<DiscussionGroup> getNotJoinedDiscussionGroup() {
        return notJoinedDiscussionGroup;
    }

    public boolean isModerator() {
        return moderator;
    }

    public void setModerator(boolean moderator) {
        this.moderator = moderator;
    }

/*    public void insertAssoUserDiscu(User user) throws SQLException {
        DiscussionGroupBdd.insertAssoGroupUser(user, discussion);
    }

    public void addMessageToDiscussion(DiscussionGroup discussion, MessageDiscussion message, User user) throws SQLException {
        MessageDiscussionBdd.insertMessageIntoDiscussion(discussion, message, user);
    }
*/
/*    public void createDiscussion(User user, String title, String description) throws SQLException {
        DiscussionGroupBdd.createDiscussionGroupBdd(user.getIdUser(), title, description);
    }

    public void recoverNotJoinedDiscussionGroups(User user) throws SQLException {
        try {
            this.notJoinedDiscussionGroup = DiscussionGroupBdd.getNotJoinedDiscussionGroupBdd(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void recoverJoinedDiscussionGroups(User user) throws SQLException {
        try {
            this.joinedDiscussionGroup = DiscussionGroupBdd.getJoinedDiscussionGroupBdd(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    } */

    public boolean getModerator() {
        return moderator;
    }

    public DiscussionGroup getDiscussion() {
        return discussion;
    }

    public void setDiscussion(DiscussionGroup discussion) {
        this.discussion = discussion;
    }

    public ArrayList<DiscussionGroup> getJoinedDiscussionGroup() {
        return joinedDiscussionGroup;
    }

    public void setJoinedDiscussionGroup(ArrayList<DiscussionGroup> joinedDiscussionGroup) {
        this.joinedDiscussionGroup = joinedDiscussionGroup;
    }

    
}
