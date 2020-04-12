package com.wdx.netty.server.demo08.sevlet;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.util.List;
import java.util.Map;

public class DefaultNettyRequest implements NettyRequest {

    private HttpRequest request;

    public DefaultNettyRequest(HttpRequest request) {
        this.request = request;
    }

    @Override
    public String getUri() {
        return request.getUri();
    }

    @Override
    public String getPath() {
        QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
        return decoder.path();
    }

    @Override
    public String getMethod() {
        return request.getMethod().name();
    }

    @Override
    public Map<String, List<String>> getParameterMap() {
        QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
        return decoder.parameters();
    }

    @Override
    public List<String> getParameterList(String name) {
        return this.getParameterMap().get(name);
    }

    @Override
    public String getParameter(String name) {
        List<String> parameterList = this.getParameterList(name);
        if (null == parameterList || parameterList.size() == 0) {
            return null;
        }
        return parameterList.get(0);
    }
}
