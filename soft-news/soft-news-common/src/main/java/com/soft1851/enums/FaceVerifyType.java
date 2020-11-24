package com.soft1851.enums;

/**
 * @Desc: 浜鸿劯姣斿绫诲瀷 鏋氫妇
 */
public enum FaceVerifyType {
    BASE64(1, "鍥剧墖Base64瀵规瘮"),
    IMAGE_URL(0, "URL鍥剧墖鍦板潃瀵规瘮");

    public final Integer type;
    public final String value;

    FaceVerifyType(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}