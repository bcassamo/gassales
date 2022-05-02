package com.peach.gassales.gassalesapi.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("gassales")
@Getter @Setter
public class GassalesProperty {
    private  String originPermitida = "https://peachgassales-api.herokuapp.com";

}
