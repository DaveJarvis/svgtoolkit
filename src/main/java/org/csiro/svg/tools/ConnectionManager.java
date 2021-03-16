package org.csiro.svg.tools;

import java.net.*;
import java.awt.*;
import java.io.*;

public class ConnectionManager {
  private static String httpAuthorization = null;

  public ConnectionManager() {
  }

  public static ConnectionManager getInstance() {
    return new ConnectionManager();
  }

  public synchronized static void setHttpBasicAuthorization(String username, String password) {
    String login = username + ":" + password;
    String encodedLogin = new String(Base64Filter.instance().encode(login.getBytes()));

    httpAuthorization = "Basic " + encodedLogin;
  }

  public byte[] retrieveData(URL url) {
    try {
      URLConnection connection = url.openConnection();

      if (httpAuthorization != null && connection instanceof HttpURLConnection) {
        // Set HTTP Basic Authorization
        System.err.println("HTTP Basic Authorization set for connection to "+url);
        connection.setRequestProperty("authorization", httpAuthorization);
      }

      BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      BufferedOutputStream bos = new BufferedOutputStream(baos);

      int b;
      while ((b = bis.read()) != -1) {
        bos.write(b);
      }
      bos.flush();
      bos.close();
      return baos.toByteArray();
    } catch (IOException ioe) {
      System.err.println("Connection to "+url+" failed: "+ ioe.getMessage());
      return null;
    }
  }

}