package com.soft1851.pojo.bo;

import com.soft1851.pojo.AdminUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qj
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewAdminBO {
    public AdminUser getAdminBO;
    private String username;
    private String adminName;
    private String password;

    public AdminUser getGetAdminBO() {
        return getAdminBO;
    }

    public void setGetAdminBO(AdminUser getAdminBO) {
        this.getAdminBO = getAdminBO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getImg64() {
        return img64;
    }

    public void setImg64(String img64) {
        this.img64 = img64;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    private String confirmPassword;
    private String img64;
    private String faceId;

}
