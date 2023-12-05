package com.xw.cloud.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//@Getter
//@ToString
//@Setter
//@AllArgsConstructor

/**
 * 虚拟机信息
 */
@Data
@TableName(value = "T_VM")
@ApiModel(description = "虚拟机信息")
public class VMInfo2 {

    @TableField(value = "name")
    @ApiModelProperty(value = "虚拟机名称")
    private String name;

    @TableField(value = "ip")
    @ApiModelProperty(value = "虚拟机ip")
    private String ip;

    @TableField(value = "username")
    @ApiModelProperty(value = "用户名")
    private String username;

    @TableField(value = "passwd")
    @ApiModelProperty(value = "密码")
    private String passwd;

    @TableField(value = "serverip")
    @ApiModelProperty(value = "虚拟机所在服务器ip")
    private String serverip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip;
    }


}