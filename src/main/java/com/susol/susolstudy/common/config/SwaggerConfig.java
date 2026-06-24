package com.susol.susolstudy.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "SuSol Study API", version = "v1"),
        security = @SecurityRequirement(name = "session")
)
@SecurityScheme(
        name = "session",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.COOKIE,
        paramName = "JSESSIONID",
        description = "브라우저에서 로그인 후 개발자도구 → Application → Cookies → JSESSIONID 값을 입력하세요"
)
public class SwaggerConfig {
}
