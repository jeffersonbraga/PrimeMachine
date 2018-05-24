package br.com.opsocial.client.entity.application;

import java.util.Date;

import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.generic.Persistent;

public class UsersDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final Character ADMINISTRATOR = 'A';
	public static final Character ANALYST = 'N';
	
	private Long idUser;
	private String name;
	private String password;
	private String email;
	private char active = 'T';
	private Date cadastreDate;
	private Character type;
    private String avatar;
    private Account account;
	private String phone;
	private Character showInstagramAuthWindow;
	private Character showLettersToClientsWindow;
	
    public UsersDTO() {
	}
    
    public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getActive() {
		return active;
	}

	public void setActive(char active) {
		this.active = active;
	}	
	
	public Date getCadastreDate() {
		return cadastreDate;
	}

	public void setCadastreDate(Date cadastreDate) {
		this.cadastreDate = cadastreDate;
	}
	
	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}

	@Override
	public Boolean isActive() {		
		return active == 'T';
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Character getShowInstagramAuthWindow() {
		return showInstagramAuthWindow;
	}

	public void setShowInstagramAuthWindow(Character showInstagramAuthWindow) {
		this.showInstagramAuthWindow = showInstagramAuthWindow;
	}

	public Character getShowLettersToClientsWindow() {
		return showLettersToClientsWindow;
	}

	public void setShowLettersToClientsWindow(Character showLettersToClientsWindow) {
		this.showLettersToClientsWindow = showLettersToClientsWindow;
	}

	@Override
	public Long getId() {		
		return idUser;
	}

	@Override
	public void setId(Long id) {
		this.idUser = id;		
	}
}
