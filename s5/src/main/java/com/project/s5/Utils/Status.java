package com.project.s5.Utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    String status;
    String details;
    Object data;

    public static Status ok(String details, Object data) {
        return Status.builder().details("ok").details(details).data(data).build();
    }

    public static Status error(String details, Object data) {
        return Status.builder().details("error").details(details).data(data).build();
    }
}
