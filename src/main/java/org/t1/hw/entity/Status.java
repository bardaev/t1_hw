package org.t1.hw.entity;

import lombok.Getter;

@Getter
public enum Status {
    IN_PROGRESS("IN_PROGRESS"), FINISHED("FINISHED");

    private final String status;

    Status(String status) {
        this.status = status;
    }

}
