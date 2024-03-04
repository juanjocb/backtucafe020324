package com.backtucafe.model.request;

import lombok.*;

@Setter
@Getter
public class RegisterRequest {
    String name;
    String email;
    String password;

}
