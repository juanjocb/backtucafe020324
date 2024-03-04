package com.backtucafe.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBusinessRequest {

    private String name;
    private String description;
    private String address;
    private String phone;
    private List<Byte> image;
    private String password;
    private String city;
    private LocalTime start_hour;
    private LocalTime finish_hour;
}
