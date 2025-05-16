package com.Servindustria.WebPage.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
        private Integer id;
        private String name;
        private String last_name;
        private String telephone;
        private String birthDay;
        private String email;
        private String doc;
        private String password;
}
