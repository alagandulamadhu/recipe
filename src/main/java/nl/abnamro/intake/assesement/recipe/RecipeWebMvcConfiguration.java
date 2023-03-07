package nl.abnamro.intake.assesement.recipe;

import nl.abnamro.intake.assesement.recipe.interceptor.RequestExecutionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RecipeWebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    RequestExecutionInterceptor requestProcessInterceptor() {
        return new RequestExecutionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestProcessInterceptor())
                .addPathPatterns("/api/**");
    }
}
