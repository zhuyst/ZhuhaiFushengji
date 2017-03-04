package indi.zhuhai.pojo;

public class Event_item {
    private Integer id;

    private String message;

    private String effectItemId;

    private String effectHandle;

    private Integer effectNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEffectItemId() {
        return effectItemId;
    }

    public void setEffectItemId(String effectItemId) {
        this.effectItemId = effectItemId;
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
    
    public int[] getEffectItemIdArray(){
		String[] effect_item_ID_S = this.getEffectItemId().split("\\.");
		int[] effect_item_ID = new int[effect_item_ID_S.length];
		for(int i = 0;i < effect_item_ID_S.length;i++){
			effect_item_ID[i] = Integer.valueOf(effect_item_ID_S[i]);
		}
		return effect_item_ID;
    }
}