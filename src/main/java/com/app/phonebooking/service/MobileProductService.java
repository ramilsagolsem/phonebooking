package com.app.phonebooking.service;

import com.app.phonebooking.dto.MobileDto;
import com.app.phonebooking.dto.ProductDetailsDto;
import com.app.phonebooking.dto.ProductSearchDto;
import com.app.phonebooking.repository.MobileRepository;
import com.app.phonebooking.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class loads the category from tech details provider and stores to database
 */
@Service
public class MobileProductService {

    @Value("${api.key}")
    private String apiKey;

    private final Map<String, String> productMap = new HashMap<>();
    private final List<String> mobileList = Arrays.asList("Samsung Galaxy S9", "Samsung Galaxy S8",
            "Motorola Nexus 6", "Oneplus 9", "iPhone 13", "iPhone 12", "iPhone 11", "iPhone X", "Nokia 3310");

    @Autowired
    private MobileService mobileService;

    @Autowired
    private MobileRepository mobileRepository;

    public String getCategory(String productName) {
        return productMap.get(productName);
    }

    public void loadProductDetails() {
        mobileService.getMobiles().subscribe(mobileDto -> {
            fetchProductDetails(mobileDto);
        });
    }

    private void fetchProductDetails(MobileDto mobileDto) {
        System.out.println("Started fetching product details: "+mobileDto.getName());
        String url = "https://api.techspecs.io/v4/product/search?query=" + mobileDto.getName();
        WebClient webClient = WebClient.create();
        webClient.post()
                .uri(url)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("Authorization", apiKey)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    return Mono.error(new RuntimeException("Client Error: " + response.statusCode()));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    return Mono.error(new RuntimeException("Server Error: " + response.statusCode()));
                })
                .bodyToMono(ProductSearchDto.class)
                .subscribe(productSearchDto -> {
                            if (productSearchDto != null && productSearchDto.getData() != null && !productSearchDto.getData().getItems().isEmpty()) {
                                String productid = productSearchDto.getData().getItems().get(0).getProduct().getId();
                                fetchTechnology(productid, mobileDto);
                            }
                        }
                        , error -> System.out.println("Error :" + error.getMessage() + ", product : "+mobileDto.getName())
                );
    }

    private void fetchTechnology(String productid, MobileDto mobileDto) {
        String url = "https://api.techspecs.io/v4/product/detail?productId=" + productid;
        WebClient webClient = WebClient.create();
        webClient.get()
                .uri(url)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("Authorization", apiKey)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    return Mono.error(new RuntimeException("Client Error: " + response.statusCode()));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    return Mono.error(new RuntimeException("Server Error: " + response.statusCode()));
                })
                .bodyToMono(ProductDetailsDto.class)
                .subscribe(productDetailsDto -> {
                            System.out.println("productDetailsDto " + productDetailsDto.getData().getItems().size());
                            if (productDetailsDto != null && productDetailsDto.getData() != null && !productDetailsDto.getData().getItems().isEmpty()) {
                                String technology = productDetailsDto.getData().getItems().get(0).getInside().getCellular().getGeneration();
                                productMap.put(mobileDto.getName(), technology.substring(0, 2));
                                System.out.println("productMap " + productMap.get(mobileDto.getName()));
                                mobileDto.setTechnology(productMap.get(mobileDto.getName()));
                                mobileRepository.save(AppUtils.mobileDtoToMobile(mobileDto))
                                        .map(AppUtils::mobileToMobileDto);
                                System.out.println("Technology updated " + productMap.get(mobileDto.getName()));
                            }
                        }
                        , error -> System.out.println("Error :" + error.getMessage() + ", product : "+mobileDto.getName())
                );
    }

}
