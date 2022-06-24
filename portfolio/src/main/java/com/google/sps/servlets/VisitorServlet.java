package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.jsoup.Jsoup;
//import org.jsoup.safety.Whitelist;

@WebServlet("/visitor-handler")
public class VisitorServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Sanitize user input to remove HTML tags and JavaScript.
    //String title = Jsoup.clean(request.getParameter("title"), Whitelist.none());
    String name = request.getParameter("name-input");
    String emailRecipient = request.getParameter("email-input");
    String emailSubject = request.getParameter("email-subject");
    String emailContent = request.getParameter("email-message");
    

    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Visitor");
    FullEntity visitorEntity =
        Entity.newBuilder(keyFactory.newKey())
            .set("name", name)
            .set("email", emailRecipient)
            .set("subject", emailSubject)
            .set("content", emailContent)
            .build();
    datastore.put(visitorEntity);

    response.sendRedirect("/contact.html");
  }
}