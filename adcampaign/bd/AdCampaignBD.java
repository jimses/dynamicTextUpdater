package com.adcampaign.bd;

import com.adcampaign.dao.AdCampaignDAO;
import com.adcampaign.util.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;



/**
 *
 * @author Jim Connell
 */
public class AdCampaignBD {

    final static Logger log = Logger.getLogger(AdCampaignBD.class);

    public static JSONObject getCampaignData(String page, String rows, String sidx, String sord) {
        JSONObject result = new JSONObject();
        Connection conn = null;

        try {

            int iPage = Integer.parseInt((page != null && page.length() > 0) ? page : "1");
            int iRows = Integer.parseInt((rows != null && rows.length() > 0) ? rows : "10");
            String iSidx = (sidx != null && sidx.length() > 0) ? sidx : "id";
            String iSord = (sord != null && sord.length() > 0) ? sord : "desc";

            conn = AdCampaignDAO.getConnection();
            int totalRows = AdCampaignDAO.getCampaignCount(conn);
            int[] pageOffsets = Utils.getPageOffsets(iPage, totalRows, iRows);

            log.info("sidx: " + iSidx + " iSord: " + iSord + " start: " + pageOffsets[Utils.PAGE_START] + " iRows: " + iRows);
            JSONArray campaigns = AdCampaignDAO.getCampaigns(conn, iSidx, iSord, pageOffsets[Utils.PAGE_START], pageOffsets[Utils.PAGE_END]);
            log.info("*******" + campaigns.length());

            result.put("page", iPage);
            result.put("total", pageOffsets[Utils.PAGE_TOTAL]);
            result.put("records", totalRows);
            result.put("rows", campaigns);


        } catch (SQLException e) {
            log.error("Problem connecting to the database. ", e);
        } catch (NumberFormatException e) {
            log.error("Problem parsing the parameters.", e);
        } catch (Exception e) {
            log.error("Unhandled exception. ", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }


        return result;

    }
    // start get ad creative data

    public static JSONObject getCreativeData(String id, String page, String rows, String sidx, String sord) {
        JSONObject result = new JSONObject();
        Connection conn = null;

        try {

            int iPage = Integer.parseInt((page != null && page.length() > 0) ? page : "1");
            int iRows = Integer.parseInt((rows != null && rows.length() > 0) ? rows : "10");
            String iSidx = (sidx != null && sidx.length() > 0) ? sidx : "id";
            String iSord = (sord != null && sord.length() > 0) ? sord : "desc";
            int iId = Integer.parseInt((id != null && id.length() > 0) ? id : "1");
            conn = AdCampaignDAO.getConnection();
            int totalRows = 0;
            int[] pageOffsets = Utils.getPageOffsets(iPage, totalRows, iRows);
            JSONArray creatives = AdCampaignDAO.getCreatives(conn, iId, iSidx, iSord, pageOffsets[Utils.PAGE_START], iRows);
            result.put("rows", creatives);
        } catch (SQLException e) {
            log.error("Problem connecting to the database. ", e);
        } catch (NumberFormatException e) {
            log.error("Problem parsing the parameters.", e);
        } catch (Exception e) {
            log.error("Unhandled exception. ", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }


        return result;

    }

    public static JSONObject getContentTextData(String id, String page, String rows, String sidx, String sord) {
        JSONObject result = new JSONObject();
        Connection conn = null;
        try {
            int iPage = Integer.parseInt((page != null && page.length() > 0) ? page : "1");
            int iRows = Integer.parseInt((rows != null && rows.length() > 0) ? rows : "10");
            String iSidx = (sidx != null && sidx.length() > 0) ? sidx : "id";
            String iSord = (sord != null && sord.length() > 0) ? sord : "desc";
            int iId = Integer.parseInt((id != null && id.length() > 0) ? id : "1");
            conn = AdCampaignDAO.getConnection();
            int totalRows = 0;
            int[] pageOffsets = Utils.getPageOffsets(iPage, totalRows, iRows);
            JSONArray contentText = AdCampaignDAO.getContentText(conn, iId, iSidx, iSord, pageOffsets[Utils.PAGE_START], iRows);
            result.put("rows", contentText);
        } catch (SQLException e) {
            log.error("Problem connecting to the database. ", e);
        } catch (NumberFormatException e) {
            log.error("Problem parsing the parameters.", e);
        } catch (Exception e) {
            log.error("Unhandled exception. ", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    public static JSONObject getContentTextData_copy(String id, String page, String rows, String sidx, String sord) {
        JSONObject result = new JSONObject();
        Connection conn = null;
        try {
            int iPage = Integer.parseInt((page != null && page.length() > 0) ? page : "1");
            int iRows = Integer.parseInt((rows != null && rows.length() > 0) ? rows : "10");
            String iSidx = (sidx != null && sidx.length() > 0) ? sidx : "id";
            String iSord = (sord != null && sord.length() > 0) ? sord : "desc";
            int iId = Integer.parseInt((id != null && id.length() > 0) ? id : "1");
            conn = AdCampaignDAO.getConnection();
            int totalRows = 0;
            int[] pageOffsets = Utils.getPageOffsets(iPage, totalRows, iRows);
            JSONArray contentText = AdCampaignDAO.getContentText_copy(conn, iId, iSidx, iSord, pageOffsets[Utils.PAGE_START], iRows);
            log.info("content_copy length*******" + contentText.length());
            result.put("rows", contentText);
        } catch (SQLException e) {
            log.error("Problem connecting to the database. ", e);
        } catch (NumberFormatException e) {
            log.error("Problem parsing the parameters.", e);
        } catch (Exception e) {
            log.error("Unhandled exception. ", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    public static JSONObject getCreativeFactorsData(String id, String camp_id) {
        JSONObject result = new JSONObject();
        Connection conn = null;
        int iId = Integer.parseInt((id != null && id.length() > 0) ? id : "1");
        int iCampId = Integer.parseInt((camp_id != null && camp_id.length() > 0) ? camp_id : "1");
        if (iId > 0 && iCampId > 0) {
            try {
                conn = AdCampaignDAO.getConnection();
                result = AdCampaignDAO.getCreativeFactors(conn, iId, iCampId);
                log.info("*******" + result.length());
            } catch (SQLException e) {
                log.error("Problem connecting to the database. ", e);
            } catch (NumberFormatException e) {
                log.error("Problem parsing the parameters.", e);
            } catch (Exception e) {
                log.error("Unhandled exception. ", e);
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                }
            }
        }
          return result;

    }

    public static JSONArray getCreativeJsonData(String id, String camp_id) {
        JSONArray result = new JSONArray();
        Connection conn = null;
        int iId = Integer.parseInt((id != null && id.length() > 0) ? id : "1");
        int iCampId = Integer.parseInt((camp_id != null && camp_id.length() > 0) ? camp_id : "1");
        if (iId > 0 && iCampId > 0) {
            try {

                conn = AdCampaignDAO.getConnection();
                result = AdCampaignDAO.getCreativeJson(conn, iId, iCampId);
            } catch (SQLException e) {
                log.error("Problem connecting to the database. ", e);
            } catch (NumberFormatException e) {
                log.error("Problem parsing the parameters.", e);
            } catch (Exception e) {
                log.error("Unhandled exception. ", e);
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                }
            }
        }


        return result;

    }

    public static JSONArray getCreativeJsonData_last(String id, String camp_id) {
        JSONArray result = new JSONArray();
        Connection conn = null;
        int iId = Integer.parseInt((id != null && id.length() > 0) ? id : "1");
        int iCampId = Integer.parseInt((camp_id != null && camp_id.length() > 0) ? camp_id : "1");
        if (iId > 0 && iCampId > 0) {
            try {

                conn = AdCampaignDAO.getConnection();
                result = AdCampaignDAO.getCreativeJson_last(conn, iId, iCampId);

            } catch (SQLException e) {
                log.error("Problem connecting to the database. ", e);
            } catch (NumberFormatException e) {
                log.error("Problem parsing the parameters.", e);
            } catch (Exception e) {
                log.error("Unhandled exception. ", e);
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                }
            }
        }


        return result;

    }

    public static void updateReviewStatusData(String update_text_id, String update_box, String update_action) {
        if (update_text_id != null & update_box != null & update_action !=null) {
            String aBox = (update_box != null && update_box.length() > 0) ? update_box : "";
            int aTextId = Integer.parseInt(update_text_id);
            int aTextOrd = Integer.parseInt(update_action);
            String error = "";
            Connection conn = null;
            try {
                conn = AdCampaignDAO.getConnection();
                AdCampaignDAO.updateReviewStatus(conn, aTextId, aBox, aTextOrd);

            } catch (SQLException e) {
                error = "Problem connecting to the database. ";
                log.error(error, e);
            } catch (NumberFormatException e) {
                error = "Problem parsing the parameters.";
                log.error(error, e);
            } catch (Exception e) {
                error = "Unhandled exception";
                log.error(error, e);
            } finally {
                try {
                  if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                }
            }
        }

    }


    public static void updateAdTextData(String ad_id, String comments) {
        if (ad_id != null && comments != null) {
            int aId = Integer.parseInt(ad_id);
            //int aReviewStatus = Integer.parseInt(review_status);
            String aReviewComments = comments;
            String error = "";
            Connection conn = null;
            try {
                conn = AdCampaignDAO.getConnection();
                AdCampaignDAO.updateAdText(conn, aId, aReviewComments);
            } catch (SQLException e) {
                error = "Problem connecting to the database. ";
                log.error(error, e);
            } catch (NumberFormatException e) {
                error = "Problem parsing the parameters.";
                log.error(error, e);
            } catch (Exception e) {
                error = "Unhandled exception";
                log.error(error, e);
            } finally {
                try {
                  if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                }
            }
        }

    }

    public static JSONObject updateFromAdTextData(String id, String content, String theorder, String ad_id, String desturl) {
        JSONObject result = new JSONObject();
        String error = "";
        if (id != null && content != null && id.length() > 0 && ad_id != null) {

            int iId = Integer.parseInt(id);
            int iOrder = Integer.parseInt(theorder);
            int iAdid = Integer.parseInt(ad_id);
            String iDestURL  = (desturl != null && desturl.length() > 0) ? desturl : "";

            Connection conn = null;
            try {

                conn = AdCampaignDAO.getConnection();
                AdCampaignDAO.updateFromAdText(conn, iId, content, iOrder, iAdid, iDestURL);
                result.put("id", id);
                result.put("content", content);
                result.put("theorder", theorder);
                result.put("ad_id", ad_id);
                result.put("desturl", desturl);
            } catch (SQLException e) {
                error = "Problem connecting to the database. ";
                log.error(error, e);
            } catch (NumberFormatException e) {
                error = "Problem parsing the parameters.";
                log.error(error, e);
            } catch (Exception e) {
                error = "Unhandled exception";
                log.error(error, e);
            } finally {
                try {
                    if (error.length() > 0) {
                        result.put(Constants.KEY_ERROR, error);
                    }

                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    public static JSONObject updateCreative(String ad_id, String adname, String desturl) {
        JSONObject result = new JSONObject();
        String error = "";
        if (ad_id != null && adname != null && ad_id.length() > 0 && ad_id != null) {
            int aId = Integer.parseInt(ad_id);
            String aName = (adname != null && adname.length() > 0) ? adname : "";
            String aDest = (desturl != null && desturl.length() > 0) ? desturl : "";
            Connection conn = null;
            try {

                conn = AdCampaignDAO.getConnection();
                AdCampaignDAO.updateCreative(conn, aId, aName, aDest);
                result.put("id", ad_id);
                result.put("adname", adname);
                result.put("desturl", desturl);
            } catch (SQLException e) {
                error = "Problem connecting to the database. ";
                log.error(error, e);
            } catch (NumberFormatException e) {
                error = "Problem parsing the parameters.";
                log.error(error, e);
            } catch (Exception e) {
                error = "Unhandled exception";
                log.error(error, e);
            } finally {
                try {
                    if (error.length() > 0) {
                        result.put(Constants.KEY_ERROR, error);
                    }

                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                }
            }
        }
        return result;
    }
    /*
    public static JSONObject setContentTextData(String content, String id, String theorder) {
    JSONObject result = new JSONObject();
     * Connection conn = null;
    Connection conn = null;

    try {


    String iContent = (content != null && content.length() > 0) ? content : "something has gone terribly wrong";
    int iName = Integer.parseInt((id != null && id.length() > 0) ? id : "1");
    int iOrder = Integer.parseInt((theorder != null && theorder.length() > 0) ? theorder : "1");


    conn = AdCampaignDAO.getConnection();



    // log.info("sidx: " + iSidx + " iSord: " + iSord + " start: " + start + " iRows: "+ iRows);
    JSONArray setText = AdCampaignDAO.setContentText(conn, iName, iContent, iOrder);
    log.info("*******"+setText.length());




    result.put("rows", setText);


    } catch (SQLException e) {
    log.error("Problem connecting to the database. ", e);
    } catch (NumberFormatException e) {
    log.error("Problem parsing the parameters.", e);
    } catch (Exception e) {
    log.error("Unhandled exception. ", e);
    } finally {
    try {
    if (conn != null) {
    conn.close();
    }
    } catch (Exception e) {
    }
    }


    return result;

    }
     */
    public static JSONObject getCommentsData(String id) {
        JSONObject result = new JSONObject();
        Connection conn = null;

        try {


            int iId = Integer.parseInt((id != null && id.length() > 0) ? id : "1");
            conn = AdCampaignDAO.getConnection();
            JSONArray comments = AdCampaignDAO.getComments(conn, iId);
            log.info("*******" + comments.length());
            result.put("rows", comments);


        } catch (SQLException e) {
            log.error("Problem connecting to the database. ", e);
        } catch (NumberFormatException e) {
            log.error("Problem parsing the parameters.", e);
        } catch (Exception e) {
            log.error("Unhandled exception. ", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }


        return result;

    }

    public static JSONObject getPermitData(String user_name, String password) {
        JSONObject result = new JSONObject();
        Connection conn = null;
        try {
            String aUser = (user_name != null && user_name.length() > 0) ? user_name : "";
            String aPass = (password != null && password.length() > 0) ? password : "";
            conn = AdCampaignDAO.getConnection();
            JSONArray comments = AdCampaignDAO.getUser(conn, aUser, aPass);

            result.put("rows", user_name);
            result.put("rows", password);
        } catch (SQLException e) {
            log.error("Problem connecting to the database. ", e);
        } catch (NumberFormatException e) {
            log.error("Problem parsing the parameters.", e);
        } catch (Exception e) {
            log.error("Unhandled exception. ", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }


        return result;

    }
    public static boolean sendNotification(String emailHost, String emailTo, String emailHTML, String emailTxt, String demo_ad_id, String demo_camp_id) {
        boolean success = false;
        String emailSubject;
        String emailFrom;
        log.info("BDemailTo=      " + emailTo);
        log.info("BDemailHost=      " + emailHost);
        log.info("demo ad_id=      " + demo_ad_id);
        log.info("demo camp_id=      " + demo_camp_id);
        String ad_id = demo_ad_id;
        String camp_id = demo_camp_id;

        if(emailTo!=null && emailTo.length()>0 && emailHost!=null && emailHost.length()>0) {
            try {
                // generate the message
                String demolink = "http://localhost:8080/AdSystem/?auth=3&ad_id=";
                String emailHTML1 = "<html><head><title>Ad System Notification</title></head><body>Ad System Notification. Please click the link below to review proposed creative. <br><br><a href=";
                String emailHTML2 = ">Click</a></body></html>";
                emailHTML = emailHTML1+demolink+demo_ad_id+"&camp_id="+demo_camp_id+emailHTML2;
                emailTxt = "Ad System Notification" + System.getProperty("line.separator");
                emailSubject = "Ad System Notification";
                emailFrom = "adsystem@thewebdevelopmentco.com";
                // send the mail
                Utils.sendEmail(emailHost, emailFrom, emailTo, emailSubject, emailTxt, emailHTML);
                success = true;

            } catch(Exception e) {

            }

        }
        return success;
      }
}


  
