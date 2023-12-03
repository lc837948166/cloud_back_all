package com.xw.cloud.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description  
 * @Author  Hunter
 * @Date 2023-12-02 
 */


@TableName("T_Node")
public class NodeInfo implements Serializable {

	private static final long serialVersionUID =  8153717320003653421L;

	@TableId(type = IdType.AUTO,value = "ID")
	private Long id;

	@TableField(value = "nodeName")
	private String nodeName;

	@TableField(value = "nodeIp")
	private String nodeIp;

	@TableField(value = "nodeStatus")
	private String nodeStatus;

	@TableField(value = "nodeLocation")
	private String nodeLocation;

	@TableField(value = "nodeType")
	private String nodeType;

	@TableField(value = "nodeConnectivity")
	private Long nodeConnectivity;

	public NodeInfo() {
	}

	public NodeInfo( String nodeName, String nodeIp, String nodeStatus, String nodeLocation, String nodeType, Long nodeConnectivity) {

		this.nodeName = nodeName;
		this.nodeIp = nodeIp;
		this.nodeStatus = nodeStatus;
		this.nodeLocation = nodeLocation;
		this.nodeType = nodeType;
		this.nodeConnectivity = nodeConnectivity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeIp() {
		return nodeIp;
	}

	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}

	public String getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	public String getNodeLocation() {
		return nodeLocation;
	}

	public void setNodeLocation(String nodeLocation) {
		this.nodeLocation = nodeLocation;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public Long getNodeConnectivity() {
		return nodeConnectivity;
	}

	public void setNodeConnectivity(Long nodeConnectivity) {
		this.nodeConnectivity = nodeConnectivity;
	}

	@Override
	public String toString() {
		return "NodeInfo{" +
				"id=" + id +
				", nodeName='" + nodeName + '\'' +
				", nodeIp='" + nodeIp + '\'' +
				", nodeStatus='" + nodeStatus + '\'' +
				", nodeLocation='" + nodeLocation + '\'' +
				", nodeType='" + nodeType + '\'' +
				", nodeConnectivity=" + nodeConnectivity +
				'}';
	}
}
