package com.gmail.vladbaransky.service.model;

import javax.validation.constraints.NotEmpty;

import static com.gmail.vladbaransky.service.constant.validation.UserValidationMessages.NOT_EMPTY_MESSAGE;

public class UserDTO {
    @NotEmpty(message = NOT_EMPTY_MESSAGE)
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
