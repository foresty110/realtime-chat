package org.example.realtimechat.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignRequest {
    private String email;
    private String password;
    private String name;
}
