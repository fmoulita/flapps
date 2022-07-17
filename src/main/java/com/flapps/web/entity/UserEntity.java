package com.flapps.web.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "asp_tms_flapps_user")
public class UserEntity extends AbstractFlappsEntity {
	public static final long serialVersionUID = 42L;

	@Column(name = "login")
	private String login;

	@Column(name = "password")
	private String password;

	@Column(name = "password_seed")
	private String passwordSeed;

	@Column(name = "phone")
	private String phone;

	@Column(name = "birth_date")
	private Date birthDate;

	@Column(name = "active")
	private boolean active;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "email")
	private String email;

	@Column(name = "time_editing_by_duration")
	private boolean timeEditingByDuration;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "card_I_D")
	private String cardID;

	@Column(name = "user_I_D")
	private String userID;

	@Column(name = "failed_logins")
	private int failedLogins;

	@Column(name = "password_changed")
	private Date passwordChanged;

	@Column(name = "last_logged")
	private Date lastLogged;

	@Column(name = "exception_based_attendance")
	private boolean exceptionBasedAttendance;

	@Column(name = "photo_time")
	private Long photoTime;    // NULL = no photo


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordSeed() {
		return passwordSeed;
	}

	public void setPasswordSeed(String passwordSeed) {
		this.passwordSeed = passwordSeed;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isTimeEditingByDuration() {
		return timeEditingByDuration;
	}

	public void setTimeEditingByDuration(boolean timeEditingByDuration) {
		this.timeEditingByDuration = timeEditingByDuration;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getFailedLogins() {
		return failedLogins;
	}

	public void setFailedLogins(int failedLogins) {
		this.failedLogins = failedLogins;
	}

	public Date getPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(Date passwordChanged) {
		this.passwordChanged = passwordChanged;
	}

	public boolean isExceptionBasedAttendance() {
		return exceptionBasedAttendance;
	}

	public void setExceptionBasedAttendance(boolean exceptionBasedAttendance) {
		this.exceptionBasedAttendance = exceptionBasedAttendance;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

}

