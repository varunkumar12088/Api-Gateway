package com.learning.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRoute {

    private String id;
    private String path;
    private String uri;
    private boolean enabled;

}
