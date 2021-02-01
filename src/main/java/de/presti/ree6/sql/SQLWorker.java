package de.presti.ree6.sql;

import de.presti.ree6.bot.BotInfo;
import de.presti.ree6.main.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Webhook;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

public class SQLWorker {

    public void setLogWebhook(String gid, String cid, String token) throws SQLException {
        if(hasLogSetuped(gid)) {

            String[] d = getLogwebhook(gid);

            BotInfo.botInstance.getGuildById(gid).retrieveWebhooks().queue(webhooks -> {
                for(Webhook wb : webhooks) {
                    if(wb.getId().equalsIgnoreCase(d[0]) && wb.getToken().equalsIgnoreCase(d[1])) {
                        wb.delete().queue();
                    }
                }
            });

            Main.insance.sqlConnector.query("DELETE FROM LogWebhooks WHERE GID='" + gid + "'");
            Main.insance.sqlConnector.query("INSERT INTO LogWebhooks (GID, CID, TOKEN) VALUES ('" + gid + "', '" + cid + "', '" + token + "');");
        } else {
            Main.insance.sqlConnector.query("INSERT INTO LogWebhooks (GID, CID, TOKEN) VALUES ('" + gid + "', '" + cid + "', '" + token + "');");
        }
    }

    public boolean hasLogSetuped(String gid) {
        try {
            PreparedStatement st;
            ResultSet rs = null;

            try {
                st = Main.sqlConnector.con.prepareStatement("SELECT * FROM LogWebhooks WHERE GID='" + gid + "'");
                rs = st.executeQuery("SELECT * FROM LogWebhooks WHERE GID='" + gid + "'");
            } catch (Exception ex) {
            }

            if (rs.next()) {
                return true;
            }

        } catch (Exception ex) {
        }
        return false;
    }

    public String[] getLogwebhook(String gid) {
        if(hasLogSetuped(gid)) {
            try {
                PreparedStatement st;
                ResultSet rs = null;

                try {
                    st = Main.sqlConnector.con.prepareStatement("SELECT * FROM LogWebhooks WHERE GID='" + gid + "'");
                    rs = st.executeQuery("SELECT * FROM LogWebhooks WHERE GID='" + gid + "'");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (rs.next()) {
                    return new String[]{rs.getString("CID"), rs.getString("TOKEN")};
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return new String[]{ "Error", "Not setuped!"};
    }

    public void setWelcomeWebhook(String gid, String cid, String token) throws SQLException {
        if (hasWeclomeSetuped(gid)) {

            String[] d = getWelcomewebhook(gid);

            BotInfo.botInstance.getGuildById(gid).retrieveWebhooks().queue(webhooks -> {
                for(Webhook wb : webhooks) {
                    if(wb.getId().equalsIgnoreCase(d[0]) && wb.getToken().equalsIgnoreCase(d[1])) {
                        wb.delete().queue();
                    }
                }
            });
            Main.insance.sqlConnector.query("DELETE FROM WelcomeWebhooks WHERE GID='" + gid + "'");
            Main.insance.sqlConnector.query("INSERT INTO WelcomeWebhooks (GID, CID, TOKEN) VALUES ('" + gid + "', '" + cid + "', '" + token + "');");
        } else {
            Main.insance.sqlConnector.query("INSERT INTO WelcomeWebhooks (GID, CID, TOKEN) VALUES ('" + gid + "', '" + cid + "', '" + token + "');");
        }
    }

    public boolean hasWeclomeSetuped(String gid) {
        try {
            PreparedStatement st;
            ResultSet rs = null;

            try {
                st = Main.sqlConnector.con.prepareStatement("SELECT * FROM WelcomeWebhooks WHERE GID='" + gid + "'");
                rs = st.executeQuery("SELECT * FROM WelcomeWebhooks WHERE GID='" + gid + "'");
            } catch (Exception ex) {
            }

            if (rs.next()) {
                return true;
            }

        } catch (Exception ex) {
        }
        return false;
    }

    public String[] getWelcomewebhook(String gid) {
        if(hasWeclomeSetuped(gid)) {
            try {
                PreparedStatement st;
                ResultSet rs = null;

                try {
                    st = Main.sqlConnector.con.prepareStatement("SELECT * FROM WelcomeWebhooks WHERE GID='" + gid + "'");
                    rs = st.executeQuery("SELECT * FROM WelcomeWebhooks WHERE GID='" + gid + "'");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (rs.next()) {
                    return new String[]{rs.getString("CID"), rs.getString("TOKEN")};
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return new String[]{ "Error", "Not setuped!"};
    }

    public void setMuteRole(String gid, String rid) throws SQLException {
        if(hasMuteSetuped(gid)) {
            Main.insance.sqlConnector.query("UPDATE MuteRoles SET RID='" + rid + "' WHERE GID='" + gid + "'");
        } else {
            Main.insance.sqlConnector.query("INSERT INTO MuteRoles (GID, RID) VALUES ('" + gid + "', '" + rid + "');");
        }
    }

    public boolean hasMuteSetuped(String gid) {
        try {
            PreparedStatement st;
            ResultSet rs = null;

            try {
                st = Main.sqlConnector.con.prepareStatement("SELECT * FROM MuteRoles WHERE GID='" + gid + "'");
                rs = st.executeQuery("SELECT * FROM MuteRoles WHERE GID='" + gid + "'");
            } catch (Exception ex) {
            }

            if (rs.next()) {
                return true;
            }

        } catch (Exception ex) {
        }
        return false;
    }

    public String getMuteRoleID(String gid) {
        if(hasMuteSetuped(gid)) {
            try {
                PreparedStatement st;
                ResultSet rs = null;

                try {
                    st = Main.sqlConnector.con.prepareStatement("SELECT * FROM MuteRoles WHERE GID='" + gid + "'");
                    rs = st.executeQuery("SELECT * FROM MuteRoles WHERE GID='" + gid + "'");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (rs.next()) {
                    return rs.getString("RID");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return "Error";
    }
}