package indi.zhuhai.pojo;

public class Item {
    private Integer id;

    private String name;

    private String introduce;

    private Integer price;

    private Integer startPrice;

    private String effectHandle;

    private Integer effectNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Integer startPrice) {
        this.startPrice = startPrice;
    }

    public String getEffectHandle() {
        return effectHandle;
    }

    public void setEffectHandle(String effectHandle) {
        this.effectHandle = effectHandle;
    }

    public Integer getEffectNumber() {
        return effectNumber;
    }

    public void setEffectNumber(Integer effectNumber) {
        this.effectNumber = effectNumber;
    }
}