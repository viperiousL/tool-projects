package com.libing.libingdemo.enums;

import java.util.HashMap;
import java.util.Map;

public enum PayStrategyEnum {

    WX((short) 0, "PayStrategyWx"),
    ZFB((short) 1, "PayStrategyZfb"),
    CASH((short) 2, "PayStrategyCash"),
    ;
    private Short  value;
    private String desc;

    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private PayStrategyEnum(Short value, String desc) {
        this.value = value;
        this.desc = desc;

    }

    public static Map<Short,String> enumToMap(){
        Map<Short, String> map = new HashMap<>();
        PayStrategyEnum[] enums = PayStrategyEnum.values();
        for (PayStrategyEnum PayStrategyEnum: enums) {
            map.put(PayStrategyEnum.getValue(),PayStrategyEnum.getDesc());
        }
        return map;
    }
}
