package com.wdx.netty.server.demo08.sevlet;

import java.util.List;
import java.util.Map;

public interface NettyRequest {

    String getUri();

    String getPath();

    String getMethod();

    Map<String, List<String>> getParameterMap();

    List<String> getParameterList(String name);

    String getParameter(String name);


}
