package com.peach.gassales.gassalesapi.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("gassales")
@Getter @Setter
public class GassalesProperty {
    private  String originPermitida = "https://peachgassales-angular.herokuapp.com";

}
