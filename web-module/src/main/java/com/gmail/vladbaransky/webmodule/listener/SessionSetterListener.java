package com.gmail.vladbaransky.webmodule.listener;

import com.gmail.vladbaransky.service.model.UserDTO;
import com.gmail.vladbaransky.service.util.CountryIdentifier;
import com.maxmind.geoip2.exception.GeoIp2Exception;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import java.io.IOException;

import static com.gmail.vladbaransky.webmodule.constant.DefaultValue.KEY_FOR_SESSION;

public class SessionSetterListener implements javax.servlet.http.HttpSessionListener {

    private final CountryIdentifier countryIdentifier;

    public SessionSetterListener(CountryIdentifier countryIdentifier) {
        this.countryIdentifier = countryIdentifier;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        UserDTO userDTO = new UserDTO();
        try {
            String countryName = countryIdentifier.getCountryByIp();
            userDTO.setCountry(countryName);
            session.setAttribute(KEY_FOR_SESSION, userDTO);
        } catch (IOException | GeoIp2Exception e) {
            e.getMessage();
        }
    }
}
