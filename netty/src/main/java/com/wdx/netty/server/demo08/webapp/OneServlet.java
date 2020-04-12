package com.wdx.netty.server.demo08.webapp;

import com.wdx.netty.server.demo08.sevlet.NettyRequest;
import com.wdx.netty.server.demo08.sevlet.NettyResponse;
import com.wdx.netty.server.demo08.sevlet.Servlet;
import java.util.List;
import java.util.Map;

public class OneServlet extends Servlet {
    @Override
    public void doGet(NettyRequest request, NettyResponse response) throws Exception {
        String uri = request.getUri();
        String path = request.getPath();
        String method = request.getMethod();
        Map<String, List<String>> parameterMap = request.getParameterMap();
        response.write("uri=" + uri
                + "\n path = " + path
                + "\n method = " + method
                + "\n parameterMap = " + parameterMap.toString());
    }

    @Override
    public void doPost(NettyRequest request, NettyResponse response) throws Exception {
        doGet(request, response);
    }
}
