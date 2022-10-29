package com.lgm.demo.controller;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class UtilForEndpointProtection {
    public static int statusCodeForGetRequest(String path, MockMvc mvc, String role) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get(path)
                .with(SecurityMockMvcRequestPostProcessors
                        .user("user")
                        .roles(role));
        MvcResult result = mvc.perform(request).andReturn();
        return result.getResponse().getStatus();
    }
}
