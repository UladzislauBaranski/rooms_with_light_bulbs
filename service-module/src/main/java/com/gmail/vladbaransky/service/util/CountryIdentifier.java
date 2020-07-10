package com.gmail.vladbaransky.service.util;

import com.gmail.vladbaransky.service.constant.DatabasePathConstant;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.net.InetAddress;
import java.net.URL;

@Component
public class CountryIdentifier {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String getUsersIp() throws IOException {
        URL usersIp = new URL("http://checkip.amazonaws.com");
        BufferedReader ip = new BufferedReader(new InputStreamReader(usersIp.openStream()));
        return ip.readLine();
    }

    public String getCountryByIp() throws IOException, GeoIp2Exception {
        // A File object pointing to your GeoLite2 database
        File dbFile = new File(DatabasePathConstant.DATABASE_CITY_PATH);
        // This creates the DatabaseReader object,
        // which should be reused across lookups.
        DatabaseReader reader = new DatabaseReader.Builder(dbFile).build();
        // A IP Address
        InetAddress ipAddress = InetAddress.getByName(getUsersIp());
        // Get City info
        CityResponse response = reader.city(ipAddress);
        // Country Info
        Country country = response.getCountry();
        // Set to uppercase for comparison with Enum
        String countryName = country.getName().toUpperCase();
        logger.info("Country Name: " + countryName);
        return countryName;
    }
}

