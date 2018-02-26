package com.adcampaign.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.log4j.Logger;

/**
 *
 * @author Jim Connell
 */
public class AdCampaignDAO {

    final static Logger log = Logger.getLogger(AdCampaignDAO.class);

    public static Connection getConnection() throws javax.naming.NamingException, java.sql.SQLException, Exception {
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/ad_db");
        Connection connection = ds.getConnection();
        return connection;
    }

    public static int getCampaignCount(Connection connection) throws SQLException, JSONException {
        int result = -1;
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS count FROM campaign");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            result = rs.getInt("count");
        }

        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }

        return result;
    }

    public static JSONArray getCampaigns(Connection connection, String order, String ord, int start, int end) throws SQLException, JSONException {
        JSONArray results = new JSONArray();

        if (connection != null && order != null && ord != null && end > start && end > 0) {
            JSONObject result;
            JSONArray resultCell;

            PreparedStatement stmt = connection.prepareStatement("SELECT id, start as startdate, name FROM campaign  ORDER BY ? ? LIMIT ? , ?");
            stmt.setString(1, order);
            stmt.setString(2, ord);
            stmt.setInt(3, start);
            stmt.setInt(4, end);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                resultCell = new JSONArray();
                resultCell.put(rs.getString("id"));
                resultCell.put(rs.getString("name"));
                resultCell.put(rs.getString("startdate"));


                result = new JSONObject();
                result.put("cell", resultCell);
                result.put("id", rs.getString("id"));

                results.put(result);

            }

            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return results;
    }

    public static JSONArray getCreatives(Connection connection, int id, String order, String ord, int start, int end) throws SQLException, JSONException {
        JSONArray results = new JSONArray();
        if (connection != null && id > 0 && order != null && ord != null && end > start && end > 0) {
            JSONObject result;
            JSONArray resultCell;
            PreparedStatement stmt = connection.prepareStatement("SELECT a.id, a.adcampaign_id_fk, a.adname, a.size_id_fk, b.id, b.size, a.dir, a.desturl FROM creative a, size b WHERE adcampaign_id_fk=? and b.id = a.size_id_fk ORDER BY ? ? LIMIT ? , ?");
            stmt.setInt(1, id);
            stmt.setString(2, order);
            stmt.setString(3, ord);
            stmt.setInt(4, start);
            stmt.setInt(5, end);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // cell object
                resultCell = new JSONArray();
                resultCell.put(rs.getString("id"));
                resultCell.put(rs.getString("adname"));
                resultCell.put(rs.getString("size"));
                resultCell.put(rs.getString("dir"));
                resultCell.put(rs.getString("adcampaign_id_fk"));
                resultCell.put(rs.getString("desturl"));


                result = new JSONObject();
                result.put("cell", resultCell);
                result.put("id", rs.getString("id"));
                results.put(result);
            }


            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return results;

    }

    public static JSONArray getContentText(Connection connection, int id, String order, String ord, int start, int end) throws SQLException, JSONException {
        JSONArray results = new JSONArray();
        log.info("connnection****" + connection);
        log.info("order****" + order);
        log.info("start****" + start);
        log.info("end****" + end);
        log.info("ord***" + ord);
        log.info("id****" + id);
        if (connection != null && id > 0 && order != null && ord != null && end > start && end > 0) {
            JSONObject result;
            JSONArray resultCell;
            PreparedStatement stmt = connection.prepareStatement("SELECT a.desturl, a.redir, a.content as content, a.enabled, a.live, a.id as id, a.theorder as theorder, a.adcreative_id_fk as ad_id, a.version as version, a.content_type_id_fk, d.content_type_id as contentType, d.content_type_name as contentType, a.revision, a.review_status_id_fk, e.review_status_id, e.review_status as review_status, b.adname as adname, c.id, c.size as size FROM content a, creative b, size c, content_type d, review_status e WHERE a.adcreative_id_fk=? AND b.id=? AND b.size_id_fk=c.id AND d.content_type_id=a.content_type_id_fk AND e.review_status_id=a.review_status_id_fk AND (a.live=1 OR a.enabled=1) ORDER BY theorder ASC LIMIT ? , ?");
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            stmt.setInt(3, start);
            stmt.setInt(4, end);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                // cell object
                resultCell = new JSONArray();
                resultCell.put(rs.getString("id"));
                resultCell.put(rs.getString("content"));
                resultCell.put(rs.getString("theorder"));
                resultCell.put(rs.getString("ad_id"));
                resultCell.put(rs.getString("contenttype"));
                resultCell.put(rs.getString("version"));
                resultCell.put(rs.getString("revision"));
                resultCell.put(rs.getString("review_status"));
                resultCell.put(rs.getString("enabled"));
                resultCell.put(rs.getString("live"));
                resultCell.put(rs.getString("desturl"));
                resultCell.put(rs.getString("redir"));

                //pack it all together now
                result = new JSONObject();
                result.put("cell", resultCell);
                result.put("id", rs.getString("id"));

                results.put(result);
                log.info("contentresult****" + result);
            }



            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return results;
    }

    public static JSONArray getContentText_copy(Connection connection, int id, String order, String ord, int start, int end) throws SQLException, JSONException {
        JSONArray results = new JSONArray();
        log.info("connnection****" + connection);
        log.info("order****" + order);
        log.info("start****" + start);
        log.info("end****" + end);
        log.info("ord***" + ord);
        log.info("id****" + id);
        if (connection != null && id > 0 && order != null && ord != null && end > start && end > 0) {
            JSONObject result;
            JSONArray resultCell;
            PreparedStatement stmt = connection.prepareStatement("SELECT a.desturl, a.redir, a.content as content, a.enabled, a.live, a.id as id, a.theorder as theorder, a.adcreative_id_fk as ad_id, a.version as version, a.content_type_id_fk, d.content_type_id as contentType, d.content_type_name as contentType, a.revision, a.review_status_id_fk, e.review_status_id, e.review_status as review_status, b.adname as adname, c.id, c.size as size FROM content_copy a, creative b, size c, content_type d, review_status e WHERE a.adcreative_id_fk=? AND b.id=? AND b.size_id_fk=c.id AND d.content_type_id=a.content_type_id_fk AND e.review_status_id=a.review_status_id_fk AND (a.live=1 OR a.enabled=1) ORDER BY theorder ASC LIMIT ? , ?");
            //AND a.content_type_id_fk = 1
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            stmt.setInt(3, start);
            stmt.setInt(4, end);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                // cell object
                resultCell = new JSONArray();
                resultCell.put(rs.getString("id"));
                resultCell.put(rs.getString("content"));
                resultCell.put(rs.getString("theorder"));
                resultCell.put(rs.getString("ad_id"));
                resultCell.put(rs.getString("contenttype"));
                resultCell.put(rs.getString("version"));
                resultCell.put(rs.getString("revision"));
                resultCell.put(rs.getString("review_status"));
                resultCell.put(rs.getString("enabled"));
                resultCell.put(rs.getString("live"));
                resultCell.put(rs.getString("desturl"));
                resultCell.put(rs.getString("redir"));

                //pack it all together now
                result = new JSONObject();
                result.put("cell", resultCell);
                result.put("id", rs.getString("id"));

                results.put(result);
                log.info("_contentresult_copy****" + result);
            }



            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return results;
    }
    public static void updateReviewStatus(Connection conn, int update_text_id, String update_box, int update_action) throws SQLException {
        if (conn != null && update_text_id > 0 && update_box != null && update_action >0) {

            String box = update_box;
            //log.info("box****" + box);

            if (box.equals("first")){
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement("UPDATE content SET review_status_id_fk=1 where id=? and theorder=? and revision=1");
            stmt.setInt(1, update_text_id);
            stmt.setInt(2, update_action);
            stmt.executeUpdate();
            stmt = conn.prepareStatement("SELECT * from content where id=?");
            stmt.setInt(1, update_text_id);
            //log.info("select stmt****" + stmt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int parentid = rs.getInt("derivedfrom");
                stmt = conn.prepareStatement("UPDATE content SET review_status_id_fk=3 where id=?");
                stmt.setInt(1, parentid);
                stmt.executeUpdate();
                conn.commit();
                if (stmt != null) {
                    stmt.close();
                }
                }
            }
            else if (box.equals("second")){
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement("UPDATE content SET review_status_id_fk=2 where id=? and theorder=? and revision=1");
            stmt.setInt(1, update_text_id);
            stmt.setInt(2, update_action);
            stmt.executeUpdate();
            conn.commit();
            if (stmt != null) {
                    stmt.close();
                }
            /* just in case
            stmt = conn.prepareStatement("SELECT * from content where id=?");
            stmt.setInt(1, update_text_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String parentid = rs.getString("derivedfrom");
                log.info("parentid****" + parentid);
                stmt = conn.prepareStatement("Update UPDATE content SET review_status_id_fk=2 where derivedfron=?");
                }
            }
            */
            }
        }
    }
    public static void updateCreative(Connection conn, int ad_id, String adname, String desturl) throws SQLException {
        if (conn != null && ad_id > 0 && adname != null) {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement("UPDATE creative SET adname=?, desturl=?, modified=CURRENT_TIMESTAMP where id=?");
            stmt.setString(1, adname);
            stmt.setString(2, desturl);
            stmt.setInt(3, ad_id);
            stmt.executeUpdate();
            conn.commit();
            if (stmt != null) {
                    stmt.close();
                }

            }
        }

    // this is triggered by clicking the respond button on Auth 3
    public static void updateAdText(Connection conn, int ad_id, String comments) throws SQLException {
        if (conn != null && ad_id > 0) {
            conn.setAutoCommit(false);

            if (review_status == 1) {

                //previously approved creative text goes into "replaced" bin.
                PreparedStatement stmt = conn.prepareStatement("UPDATE content SET review_status_id_fk=3, revision=0 where review_status_id_fk=1 and revision=0 and adcreative_id_fk=?");
                stmt.setInt(1, ad_id);

                stmt.executeUpdate();
                conn.commit();
                //previously proposed creative has review status changed to "approved".
                stmt = conn.prepareStatement("UPDATE content SET review_status_id_fk=1, revision=0 where review_status_id_fk=0 and revision=1 and adcreative_id_fk=?");
                stmt.setInt(1, ad_id);

                stmt.executeUpdate();
                conn.commit();
                 //unmodified creative text that had been marked as revision has 'revision' status set back to zero
                stmt = conn.prepareStatement("UPDATE content SET revision=0 where review_status_id_fk=1 and revision=1 and adcreative_id_fk=?");
                stmt.setInt(1, ad_id);

                stmt.executeUpdate();
                conn.commit();
                if (stmt != null) {
                    stmt.close();
                }
            }

            else {
                // flag the previously proposed text as "rejected"
                PreparedStatement stmt = conn.prepareStatement("UPDATE content SET review_status_id_fk=2 where review_status_id_fk=0 and revision>0 and adcreative_id_fk=?");
                stmt.setInt(1, ad_id);

                stmt.executeUpdate();
                conn.commit();
                if (stmt != null) {
                    stmt.close();
                }
                */
            PreparedStatement stmt = conn.prepareStatement("UPDATE content SET live=1 where review_status_id_fk=1 and enabled=1 and adcreative_id_fk=?");
                stmt.setInt(1, ad_id);

                stmt.executeUpdate();
                stmt = conn.prepareStatement("UPDATE content SET live=0 where enabled=0 and review_status_id_fk !=1 and adcreative_id_fk=?");
                stmt.setInt(1, ad_id);

                stmt.executeUpdate();
                stmt = conn.prepareStatement("UPDATE content SET enabled=0 where adcreative_id_fk=?");
                stmt.setInt(1, ad_id);

                stmt.executeUpdate();
                conn.commit();
                if (stmt != null) {
                    stmt.close();
                }
            }

            if (conn != null) {
                conn.setAutoCommit(false);
                /* Insert comments into DB and associate with correct ad. */
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO comments (creative_id_fk,comment) values(?,?)");
                stmt.setInt(1, ad_id);
                //log.info("ad_id****" + ad_id);
                stmt.setString(2, comments);
                //log.info("review stmt****" + stmt);
                stmt.executeUpdate();
                conn.commit();
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
    //}

    public static void updateFromAdText(Connection conn, int id, String content, int theorder, int ad_id, String desturl) throws SQLException {

        if (conn != null && id > 0 && ad_id > 0) {
            conn.setAutoCommit(false);


            PreparedStatement stmt = conn.prepareStatement("SELECT MAX(version) as maxversion FROM content WHERE adcreative_id_fk=? AND theorder=?");
            stmt.setInt(1, ad_id);
            stmt.setInt(2, theorder);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maxversion = rs.getString("maxversion");
                log.info("max_version****" + maxversion);

                // review status is set to zero for "not yet approved"
                stmt = conn.prepareStatement("INSERT INTO content (derivedfrom,content,version,adcreative_id_fk,desturl,theorder,review_status_id_fk,revision,enabled, content_type_id_fk) VALUES (?, ?, (?+1), ?, ?, ?, ?, ?, ?, ?)");
                stmt.setInt(1, id);
                stmt.setString(2, content);
                stmt.setString(3, maxversion);
                stmt.setInt(4, ad_id);
                stmt.setString(5, desturl);
                stmt.setInt(6, theorder);
                stmt.setString(7, "0");
                stmt.setString(8, "1");
                stmt.setString(9, "1");
                stmt.setString(10, "1");
                stmt.executeUpdate();
                /* this is new
                stmt = conn.prepareStatement("INSERT INTO content_copy SELECT * FROM content WHERE adcreative_id_fk = ?");
                stmt.setInt(1, ad_id);
                stmt.executeUpdate();
                */
            }


            stmt = conn.prepareStatement("SELECT id as contentid, revision, enabled FROM content WHERE adcreative_id_fk =? AND enabled=0");
            stmt.setInt(1, ad_id);
            //log.info("select_ad_id_where_revision_zero****" + ad_id);
            ResultSet rs3 = stmt.executeQuery();
            while (rs3.next()) {
                int contentid = rs3.getInt("contentid");
                int revision = rs3.getInt("revision");
                int enabled = rs3.getInt("enabled");
                if (enabled != 1) {

                    //Set non-modified (approved) text records as 'enabled' temporarily until the reviewer accepts or rejects new content
                    stmt = conn.prepareStatement("UPDATE content SET enabled=1,modified=CURRENT_TIMESTAMP WHERE adcreative_id_fk=? AND review_status_id_fk=1 AND id !=?");
                    stmt.setInt(1, ad_id);
                    stmt.setInt(2, contentid);
                    stmt.executeUpdate();
                    conn.commit();

                }


                //we are grabbing the source record id from the most recent update
                stmt = conn.prepareStatement("SELECT derivedfrom, revision as revised, enabled, id as revisedID FROM content WHERE id = (SELECT max(id) FROM content) AND adcreative_id_fk =?");
                stmt.setInt(1, ad_id);
                log.info("derived_select_ad_id****" + ad_id);

                ResultSet rs2 = stmt.executeQuery();
                //log.info("derived_rs2****" + rs2);
                while (rs2.next()) {
                    int derived = rs2.getInt("derivedfrom");
                        stmt = conn.prepareStatement("SELECT MAX(id) as existing, MAX(derivedfrom) as deriving FROM content where adcreative_id_fk =? AND review_status_id_fk!=2 GROUP BY theorder HAVING COUNT(*) > 1 ORDER BY COUNT(*) DESC");
                        stmt.setInt(1, ad_id);
                        ResultSet rs4 = stmt.executeQuery();
                        while (rs4.next()) {
                        int existingid = rs4.getInt("existing");
                        int derivingid = rs4.getInt("deriving");
                  }
                        //we are setting the source record enabled status as 0 so it will not display in the 'proposed' ad creative.
                        stmt = conn.prepareStatement("UPDATE content SET enabled=0,modified=CURRENT_TIMESTAMP WHERE id=? AND adcreative_id_fk=?");
                        //tmt.setInt(1, existingid);
                        stmt.setInt(1, derivingid);
                        //stmt.setInt(2, contentid);
                        stmt.setInt(2, ad_id);
                        stmt.executeUpdate();
                        conn.commit();


                }
            }

}

            if (stmt != null) {
                stmt.close();
            }
        }

    }


    public static JSONObject getCreativeFactors(Connection connection, int id, int camp_id) throws SQLException, JSONException {

        JSONObject result = new JSONObject();
        if (connection != null && id > 0 && camp_id > 0) {
            PreparedStatement stmt = connection.prepareStatement("SELECT a.id, a.adcampaign_id_fk, a.adname, a.size_id_fk, a.dir, b.name, c.id, c.size FROM creative a, campaign b, size c WHERE a.id = ? AND b.id=? AND c.id = a.size_id_fk");
            stmt.setInt(1, id);
            stmt.setInt(2, camp_id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result.put("id", id);
                result.put("adcampaign_id_fk", rs.getString("adcampaign_id_fk"));
                result.put("adname", rs.getString("adname"));
                result.put("size", rs.getString("size"));
                result.put("dir", rs.getString("dir"));
                result.put("name", rs.getString("name"));

            }

            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return result;
    }

    public static JSONArray getCreativeJson_last(Connection connection, int id, int camp_id) throws SQLException, JSONException {
        JSONArray results = new JSONArray();
        if (connection != null && id > 0) {
            PreparedStatement stmt = connection.prepareStatement("SELECT a.content as adcontent, a.xcoord, a.ycoord, a.width, a.height, a.fontsize, a.fontcolor, a.version, b.id as adid, b.adname, d.size, b.dir, c.name FROM content a, creative b, campaign c, size d WHERE a.adcreative_id_fk = ? AND b.id = ? AND c.id = ? AND d.id = b.size_id_fk AND a.live = 1 ORDER BY a.theorder ASC, a.content_type_id_fk ASC");
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            stmt.setInt(3, camp_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // cell object
                JSONObject result = new JSONObject();
                result.put("adid", rs.getString("adid"));
                result.put("adcontent", rs.getString("adcontent"));
                result.put("xcoord", rs.getString("xcoord"));
                result.put("ycoord", rs.getString("ycoord"));
                result.put("width", rs.getString("width"));
                result.put("height", rs.getString("height"));
                result.put("fontsize", rs.getString("fontsize"));
                result.put("fontcolor", rs.getString("fontcolor"));
                result.put("adname", rs.getString("adname"));
                result.put("size", rs.getString("size"));
                result.put("dir", rs.getString("dir"));
                result.put("name", rs.getString("name"));
                results.put(result);
            }

            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return results;
    }

    public static JSONArray getCreativeJson(Connection connection, int id, int camp_id) throws SQLException, JSONException {
        JSONArray results = new JSONArray();

        if (connection != null && id > 0) {

            // this query returns the creative that is currently under revision and being proposed
            PreparedStatement stmt = connection.prepareStatement("SELECT a.theorder as theorder, a.content as adcontent, a.xcoord, a.ycoord, a.width, a.height, a.fontsize, a.fontcolor, a.version, a.revision, b.id as adid, b.adname, d.size, b.dir, c.name FROM content a, creative b, campaign c, size d WHERE a.adcreative_id_fk = ? AND b.id = ? AND c.id = ? AND a.enabled=1 AND  d.id = b.size_id_fk  ORDER BY a.theorder ASC, a.content_type_id_fk ASC");

            stmt.setInt(1, id);
            stmt.setInt(2, id);
            stmt.setInt(3, camp_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // cell object
                JSONObject result = new JSONObject();
                result.put("adid", rs.getString("adid"));
                result.put("adcontent", rs.getString("adcontent"));
                result.put("xcoord", rs.getString("xcoord"));
                result.put("ycoord", rs.getString("ycoord"));
                result.put("width", rs.getString("width"));
                result.put("height", rs.getString("height"));
                result.put("fontsize", rs.getString("fontsize"));
                result.put("fontcolor", rs.getString("fontcolor"));
                result.put("adname", rs.getString("adname"));
                result.put("size", rs.getString("size"));
                result.put("dir", rs.getString("dir"));
                result.put("name", rs.getString("name"));
                result.put("revision", rs.getString("revision"));
                results.put(result);
            }

            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return results;
    }
    public static JSONArray getComments(Connection connection, int id) throws SQLException, JSONException {
        JSONArray results = new JSONArray();
        if (connection != null && id > 0 ) {
            JSONObject result;
            JSONArray resultCell;
            PreparedStatement stmt = connection.prepareStatement("SELECT a.comment, a.created, b.id from comments a, creative b where a.creative_id_fk=? and b.id=? order by comments_id desc");
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultCell = new JSONArray();
                resultCell.put(rs.getString("comment"));
                resultCell.put(rs.getString("created"));
                result = new JSONObject();
                result.put("cell", resultCell);
                result.put("id", rs.getString("id"));
                results.put(result);
            }

            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return results;
    }

    public static JSONArray getUser(Connection connection, String user_name, String password) throws SQLException, JSONException {
        JSONArray results = new JSONArray();
        if (connection != null && user_name != null && password != null ) {
            JSONObject result;
            JSONArray resultCell;
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE user_name=? and password=?");
            stmt.setString(1, user_name);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resultCell = new JSONArray();
                resultCell.put(rs.getString("user_name"));
                resultCell.put(rs.getString("date_last_login"));
                result = new JSONObject();
                result.put("cell", resultCell);
                result.put("id", rs.getString("user_id"));
                results.put(result);
            }

            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return results;
    }

    public static JSONObject getSomeSingleSQL(Connection connection, String comm_num) throws SQLException, JSONException {
        JSONObject result = new JSONObject();
        PreparedStatement stmt = connection.prepareStatement(SQL_SOME_STATEMENT);
        stmt.setString(1, comm_num);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            result.put("key", rs.getString("minprice"));
        }

        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }

        return result;
    }
    
}
