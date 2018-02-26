package com.adcampaign.util;

/**
 *
 * @author JCONNELL
 */
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.commons.lang.StringUtils;

public class Utils {

    public final static int PAGE_START = 0;
    public final static int PAGE_END = 1;
    public final static int PAGE_TOTAL = 2;

    public static int[] getPageOffsets(int currentPage, int totalItems, int itemsPerPage) {
        int[] result = {0, 0, 0};
        int start = 0;
        int end = 0;
        int totalPages = 0;

        if ((itemsPerPage > 0) && (totalItems > 0) && (currentPage > 0)) {

            //total amount of pages
            if (totalItems % itemsPerPage == 0) {
                totalPages = totalItems / itemsPerPage;
            } else {
                totalPages = (totalItems / itemsPerPage) + 1;
            }

            //calculating the row numbers needed for the query
            //start = ((currentPage - 1) * itemsPerPage) + 1;
            start = itemsPerPage * currentPage - itemsPerPage;
            end = currentPage * itemsPerPage;

            // for array out of bounds
            if (end > totalItems) {
                end = totalItems;
            }
        }

        result[PAGE_START] = start;
        result[PAGE_END] = end;
        result[PAGE_TOTAL] = totalPages;
        return result;
    }
    /*
    public static void sendEmail(String host, String from, String to, String subject, String body_txt, String body_html) throws MessagingException, Exception {
        sendEmail(host, from, to, subject, body_txt, body_html, null);
    }
    */
    public static void sendEmail(String emailHost, String emailFrom, String emailTo, String emailSubject, String emailTxt, String emailHTML) throws MessagingException, Exception {
        Session mailSession;
        mailSession = null;
        Properties mailProps = new Properties();

        if (StringUtils.isEmpty(emailHost) || StringUtils.isEmpty(emailFrom) || StringUtils.isEmpty(emailTo) || StringUtils.isEmpty(emailSubject) || StringUtils.isEmpty(emailTxt) && StringUtils.isEmpty(emailHTML)) {
            throw new Exception("Invalid or missing parameter. Email was not sent");
        }

        mailProps.put("mail.smtp.host", emailHost);
        mailSession = Session.getDefaultInstance(mailProps, null);
        mailSession.setDebug(false);
        MimeMessage msg;
        InternetAddress address[];

        msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(emailFrom));
        address = (new InternetAddress[]{new InternetAddress()});
        address = InternetAddress.parse(emailTo);
        msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
        msg.setSubject(emailSubject);

        if (StringUtils.isEmpty(emailHTML) && !StringUtils.isEmpty(emailTxt)) {
            msg.setText(emailTxt);
        } else {
            MimeMultipart multi = new MimeMultipart("alternative");
            MimeBodyPart part_text = new MimeBodyPart();
            MimeBodyPart part_html = new MimeBodyPart();
            MimeBodyPart attachment = new MimeBodyPart();
            if (StringUtils.isNotEmpty(emailTxt)) {
                part_text.setText(emailTxt);
                part_text.setContent(emailTxt, "text/plain");
                multi.addBodyPart(part_text);
            }
            if (StringUtils.isNotEmpty(emailHTML)) {
                part_html.setText(emailHTML);
                part_html.setContent(emailHTML, "text/html");
                multi.addBodyPart(part_html);
            }
            /*
            if (StringUtils.isNotEmpty(attachmentFile)) {
                javax.activation.DataSource source = new FileDataSource(attachmentFile);
                attachment.setDataHandler(new DataHandler(source));
                attachment.setFileName(attachmentFile);
                multi.addBodyPart(attachment);
            }
             * */

            msg.setContent(multi);
        }
        Transport.send(msg);
    }
}
