package com.gts.backgts.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private record UploadMapping(String urlPattern, String folderName) {}

    private static final List<UploadMapping> UPLOAD_MAPPINGS = List.of(
            new UploadMapping("/uploads/conducteurs/**", "uploads/conducteurs"),
            new UploadMapping("/uploads/engins/**", "uploads/engins"),
            new UploadMapping("/uploads/clients/**", "uploads/clients")
    );

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        for (UploadMapping mapping : UPLOAD_MAPPINGS) {
            Path folderPath = Path.of(mapping.folderName()).toAbsolutePath().normalize();

            registry.addResourceHandler(mapping.urlPattern())
                    .addResourceLocations(folderPath.toUri().toString());
        }
    }
}
