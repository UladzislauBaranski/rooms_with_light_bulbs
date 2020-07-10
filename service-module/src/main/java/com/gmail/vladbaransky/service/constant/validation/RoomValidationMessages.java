package com.gmail.vladbaransky.service.constant.validation;

import static com.gmail.vladbaransky.service.constant.validation.RoomValidationRules.MAX_NAME_SIZE;
import static com.gmail.vladbaransky.service.constant.validation.RoomValidationRules.MIN_NAME_SIZE;

public interface RoomValidationMessages {

    String NOT_EMPTY_NAME_MESSAGE = "Name cannot be empty.";
    String NAME_SIZE_MESSAGE = "Name length should be from " + MIN_NAME_SIZE + " to " + MAX_NAME_SIZE + ".";
    String NAME_PATTERN_MESSAGE = "Name can contain only letters.";
}
