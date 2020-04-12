package com.wdx.netty.server.demo08.sevlet;

public class DefaultServlet extends Servlet {
    @Override
    public void doGet(NettyRequest request, NettyResponse response) throws Exception {
        String servletName = request.getUri().split("/")[1];
        response.write("404--- no this servletName：" + servletName);
    }

    @Override
    public void doPost(NettyRequest request, NettyResponse response) throws Exception {
        doGet(request, response);
    }
}
