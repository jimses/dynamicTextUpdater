package com.adcampaign.servlet;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import com.adcampaign.bd.AdCampaignBD;
import java.io.PrintWriter;
import org.json.JSONObject;

/**
 *
 * @author Jim Connell
 */
public class AdCampaignServlet extends HttpServlet {

    final static Logger log = Logger.getLogger(AdCampaignServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String camp_id = request.getParameter("camp_id");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String sidx = "id";
        String user_name = request.getParameter("user_name");
        String password = request.getParameter("password");
        String sord = request.getParameter("sord");
        String content = request.getParameter("content");
        String version = request.getParameter("version");
        String ad_id = request.getParameter("ad_id");
        String adname = request.getParameter("adname");
        String desturl = request.getParameter("desturl");
        String contentType = request.getParameter("contentType");
        String theorder = request.getParameter("thorder");
        //String review_status = request.getParameter("review_status");
        String comments = request.getParameter("comments");
        String update_box = request.getParameter("update_box");
        String update_text_id = request.getParameter("update_text_id");
        String update_action = request.getParameter("update_action");
        String result = "";
        //String emailHost = (String) getInitParameter("mailhost");
        String emailHost = "internettest.emailtest.com";
        //String emailHost = (String) getInitParameter("emailHost");
        //String emailFrom = (String) getInitParameter("emailFrom");
        String emailTo = request.getParameter("to");
        //String emailSubject = (String) getInitParameter("emailSubject");
        String emailHTML = (String) getInitParameter("emailHTML");
        String emailTxt = (String) getInitParameter("emailTxt");
        //String attach = (String) getInitParameter("attach");
        String demo_ad_id = request.getParameter("demo_ad_id");
        String demo_camp_id = request.getParameter("demo_camp_id");
         log.info("demonstration_id-- " + demo_ad_id);

        if ("getcampaigns".equals(action)) {

            JSONObject data = AdCampaignBD.getCampaignData(page, rows, sidx, sord);
            result = data.toString();

        }  else if ("getcreative".equals(action)) {
            JSONObject data = AdCampaignBD.getCreativeData(id, page, rows, sidx, sord);
            result = data.toString();

        } else if ("getcomments".equals(action)) {
            JSONObject data = AdCampaignBD.getCommentsData(id);
            result = data.toString();

        } else if ("permit".equals(action)) {
            JSONObject data = AdCampaignBD.getPermitData(user_name, password);
            result = data.toString();

        } else if ("action4".equals(action)) {
            //do that

        } else if ("getcreativetext".equals(action)) {

            JSONObject data = AdCampaignBD.getContentTextData(id, page, rows, sidx, sord);

            result = data.toString();

        } else if ("getcreativetext_copy".equals(action)) {

            JSONObject data = AdCampaignBD.getContentTextData_copy(id, page, rows, sidx, sord);

            result = data.toString();

        } else if ("updateReviewStatus".equals(action)) {
            AdCampaignBD.updateReviewStatusData(update_text_id, update_box, update_action);

        } else if ("updateadtext".equals(action)) {

            AdCampaignBD.updateAdTextData(ad_id, comments);
            //log.info("getapproval_status" + request.getParameter("review_status"));
             log.info("getapproval_comments" + request.getParameter("review_comments"));


        } else if ("updatecreative".equals(action)) {

            JSONObject data = AdCampaignBD.updateCreative(ad_id, adname, desturl);
            result = data.toString();

        } else if ("updatefromadtext".equals(action)) {

            JSONObject data = AdCampaignBD.updateFromAdTextData(id, content, theorder, ad_id, desturl);
            result = data.toString();

        } else if ("getcreativefactors".equals(action)) {
            result = AdCampaignBD.getCreativeFactorsData(id, camp_id).toString();
            log.info("getcreativefactors" + result.length());
        }
        else if ("getadcreative".equals(action)) {
            result = AdCampaignBD.getCreativeJsonData(id, camp_id).toString();
            log.info("getadcreative" + result.length());
        }
        else if ("getadcreative_last".equals(action)) {
            result = AdCampaignBD.getCreativeJsonData_last(id, camp_id).toString();
            log.info("getadcreative_last" + result.length());
            log.info("getadcreative_last_id-- " + id);
            log.info("getadcreative_last_camp_id-- " + camp_id);
        }
        else if ("sendforreview".equals(action)) {
           //boolean success = AdCampaignBD.sendNotification("","");
           boolean success = AdCampaignBD.sendNotification(emailHost, emailTo, emailHTML, emailTxt, demo_ad_id, demo_camp_id);
           log.info("sendforreview_id-- " + ad_id);
            log.info("sendforreview_camp_id-- " + camp_id);
           if(success)
               result = "success";
           else
               result = "false";
           log.info("success=      " + result);
       }





        // Print results to screen
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        PrintWriter out = response.getWriter();
        out.println(result);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        doGet(request, response);
    }
}
