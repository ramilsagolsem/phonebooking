package com.app.phonebooking.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductSearchDto {
    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("total_results")
        private int totalResults;

        @SerializedName("total_results_per_page")
        private int totalResultsPerPage;

        @SerializedName("total_pages")
        private int totalPages;

        @SerializedName("page")
        private int page;

        @SerializedName("type")
        private String type;

        @SerializedName("items")
        private List<Item> items;

        public int getTotalResults() {
            return totalResults;
        }

        public int getTotalResultsPerPage() {
            return totalResultsPerPage;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getPage() {
            return page;
        }

        public String getType() {
            return type;
        }

        public List<Item> getItems() {
            return items;
        }
    }

    public static class Item {
        @SerializedName("product")
        private Product product;

        @SerializedName("image")
        private Image image;

        @SerializedName("versions")
        private String versions;

        public Product getProduct() {
            return product;
        }

        public Image getImage() {
            return image;
        }

        public String getVersions() {
            return versions;
        }
    }

    public static class Product {
        @SerializedName("id")
        private String id;

        @SerializedName("brand")
        private String brand;

        @SerializedName("model")
        private String model;

        @SerializedName("version")
        private String version;

        @SerializedName("category")
        private String category;

        public String getId() {
            return id;
        }

        public String getBrand() {
            return brand;
        }

        public String getModel() {
            return model;
        }

        public String getVersion() {
            return version;
        }

        public String getCategory() {
            return category;
        }
    }

    public static class Image {
        @SerializedName("front")
        private String front;

        @SerializedName("back")
        private String back;

        public String getFront() {
            return front;
        }

        public String getBack() {
            return back;
        }
    }
}

