package com.group0565.engine.assets;

import org.json.JSONObject;

public class JsonFile extends Asset {
    private JSONObject jsonObject;

    public JsonFile(String name, String path) {
        super(name, path);
    }


    public JSONObject getJsonObject() {
        return jsonObject;
    }

    protected void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
