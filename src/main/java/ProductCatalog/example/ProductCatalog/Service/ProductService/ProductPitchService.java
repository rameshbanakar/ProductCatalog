package ProductCatalog.example.ProductCatalog.Service.ProductService;

import ProductCatalog.example.ProductCatalog.DTO.PitchReponseDto;
import ProductCatalog.example.ProductCatalog.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.List;


@Service
public class ProductPitchService {

    private final static String API = "https://api.groq.com/openai/v1/chat/completions";
    private final static String MODEL = "openai/gpt-oss-20b";
    private final static String REASONING_EFFORT = "low";
    private final static Integer MAX_TOKEN = 300;
    private final static String SYSTEM_PROMPT = "You are a marketing copywriter for ShopNest, an online store. You write short push notifications that make people want to open the app.";

    private final String api_key=System.getenv("groq_api_key");

    @Autowired
    private RestTemplate restTemplate;

    public String generatePitch(Product product){
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(api_key);
            headers.setContentType(MediaType.APPLICATION_JSON);


            Map<String, Object> body = Map.of(
                    "model",  MODEL,
                    "reasoning_effort", REASONING_EFFORT,
                    "max_completion_tokens" , MAX_TOKEN,
                    "messages", List.of(
                            Map.of("role", "system",
                                    "content", SYSTEM_PROMPT),
                            Map.of("role", "user",
                                    "content", userPromptFormatter(product)
                            )
                    )
            );
            long startTime = System.nanoTime();
            ResponseEntity<PitchReponseDto> responseEntity=restTemplate.postForEntity(API,new HttpEntity<>(body, headers),PitchReponseDto.class);
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;
            System.out.print("3rd party API latency:"+durationMs );
            if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
                return responseEntity.getBody().getChoices().get(0).getMessage().getContent();
            }
            return "dummy pitch";

        } catch (HttpClientErrorException e){
                System.out.println("Error while calling Groq API : " + e.getMessage());
                return "dummy pitch";
        }

    }

    private String userPromptFormatter(Product product) {
        return """
                Write ONE push notification for this product.
                Rules:
                -Maximum 25 words.
                - Mention the price.
                - At most one emoji.
                - No hashtags.
                - Reply with only the notification text.
                
                Product: %s
                Category: %s
                Price: %s
                Details: %s
                """.formatted(product.getName(), product.getCategory().getName(), product.getPrice(), product.getDescription());
    }

}
