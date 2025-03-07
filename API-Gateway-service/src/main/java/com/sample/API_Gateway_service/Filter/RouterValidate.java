package com.sample.API_Gateway_service.Filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouterValidate {

    public final static List<String> openApi=List.of(
            "/auth/register",
            "/auth/login",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecure =
            request->openApi
                    .stream()
                    .noneMatch(uri->request.getURI().getPath().contains(uri));
}
