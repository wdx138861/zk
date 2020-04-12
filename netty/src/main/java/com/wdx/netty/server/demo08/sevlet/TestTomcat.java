package com.wdx.netty.server.demo08.sevlet;

public class TestTomcat {

    public static void main(String[] args) {
        TomcatServlet tomcatServlet = new TomcatServlet("com.wdx.netty.server.demo08.webapp");
        try {
            tomcatServlet.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
