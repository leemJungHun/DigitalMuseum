package com.example.digitalmuseum.network.vo;

public class DetailsVO {
    String code;
    String title;
    String productedAt;
    int amount;
    String size;
    String material;
    String note;
    String origin;
    String production;
    String source;
    String storage;
    String storeNum;
    String content;
    String imgUrl;

    public DetailsVO(String code, String title, String productedAt, int amount, String size, String material, String note, String origin, String production, String source, String storage, String storeNum, String content, String imgUrl) {
        this.code = code;
        this.title = title;
        this.productedAt = productedAt;
        this.amount = amount;
        this.size = size;
        this.material = material;
        this.note = note;
        this.origin = origin;
        this.production = production;
        this.source = source;
        this.storage = storage;
        this.storeNum = storeNum;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductedAt() {
        return productedAt;
    }

    public void setProductedAt(String productedAt) {
        this.productedAt = productedAt;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
