package org.elasticsearch.index.analysis;

/**
 * Created by medcl on 16/8/22.
 */
public class ConfigErrorException extends RuntimeException {
    private final String mesage;

    public ConfigErrorException(String message) {
        this.mesage=message;
    }
    public String getMessage() {
        return this.mesage;
    }
}
