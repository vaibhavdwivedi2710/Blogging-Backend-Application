package com.blogging_app.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerFactoryUtil {

    // A static method to get the logger instance for a given class
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
