package com.app.phonebooking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDetailsDto {
    private int status;
    private String message;
    private Data data;

    @lombok.Data
    @NoArgsConstructor
    public static class Data {
        private String type;
        private String language;
        private List<Item> items;

        @lombok.Data
        @NoArgsConstructor
        public static class Item {
            private Inside inside;

            @lombok.Data
            @NoArgsConstructor
            public static class Inside {
                private Cellular cellular;

                @lombok.Data
                @NoArgsConstructor
                public static class Cellular {
                    private String generation;
                    // getters and setters
                }

                // getters and setters
            }

            // getters and setters
        }

        // getters and setters
    }

    // getters and setters
}

