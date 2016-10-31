package com.casey.hbweather.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月21日 下午1:10:00    类说明  
 *
 */
public class UserBean implements Serializable {

	private String id;// 5,
	private String roleId;// 2",
	private String active;// 0,
	private String realname;// null,
	private String activeTime;// null,
	private String setting;// null,
	private String login;// false,
	private String roleName;// 普通用户",
	private String activeStatus;// null,
	private String email;// null,
	private String name;// 13307174218",
	private String psw;// "
	private ArrayList<permission> permissions;

	public class permission implements Serializable{
		private String name;// 浏览",
		private boolean allow;// true,
		private int resource;// 1"

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean getAllow() {
			return allow;
		}

		public void setAllow(boolean allow) {
			this.allow = allow;
		}

		public int getResource() {
			return resource;
		}

		public void setResource(int resource) {
			this.resource = resource;
		}

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public ArrayList<permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(ArrayList<permission> permissions) {
		this.permissions = permissions;
	}

}
