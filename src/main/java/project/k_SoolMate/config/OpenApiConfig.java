package project.k_SoolMate.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "K-SoolMate API 문서",
                version = "v2",
                description = "한국 전통주 플랫폼 API 명세서"
        ),
        servers = {
                @Server(url = "http://localhost:8081", description = "Local Server")
        }
)
public class OpenApiConfig {
}
