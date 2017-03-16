package com.gaejexperiments.email;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MailServiceServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String strCallResult = "";
		/*resp.setContentType("text/plain");*/
		try {
			// Extract out the To, Subject and Body of the Email to be sent
			req.setCharacterEncoding("UTF-8");
			String strTo = req.getParameter("email_to");
			System.out.println(strTo);
			String strSubject = req.getParameter("email_subject");
			String strBody = req.getParameter("email_body");
			// Do validations here. Only basic ones i.e. cannot be null/empty
			// Currently only checking the To Email field
			if (strTo == null)
				throw new Exception("To field cannot be empty.");
			// Trim the stuff
			strTo = strTo.trim();
			if (strTo.length() == 0)
				throw new Exception("To field cannot beempty.");
			// Call the GAEJ Email Service
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(strTo));//mail to
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(strTo));
			msg.setSubject(strSubject);
			msg.setText(strBody);
			Transport.send(msg);
			strCallResult = "Success: " + "Email has been delivered.";
			resp.getWriter().println(strCallResult);
		} catch (Exception ex) {
			strCallResult = "Fail: " + ex.getMessage();
			resp.getWriter().println(strCallResult);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}