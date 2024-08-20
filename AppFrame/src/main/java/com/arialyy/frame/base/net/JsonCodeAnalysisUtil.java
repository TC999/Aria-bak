package com.arialyy.frame.base.net;

import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AriaL on 2017/11/26.
 */

public class JsonCodeAnalysisUtil {

  public static boolean isSuccess(JsonObject obj) {
    JSONObject object = null;
    try {
      object = new JSONObject(obj.toString());
      return object.optBoolean("success");
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return false;
  }
}
