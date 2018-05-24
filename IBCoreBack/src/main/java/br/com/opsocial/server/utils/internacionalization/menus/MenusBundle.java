package br.com.opsocial.server.utils.internacionalization.menus;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

public class MenusBundle {
	
	List<String> menusKeys;
	
	public MenusBundle() {
	}
	
	public List<String> getMenusKeys() {
		
		menusKeys = new ArrayList<String>();
		
		Enumeration<String> keys = ResourceBundle.getBundle("br.com.opsocial.server.utils.internacionalization.menus.menus").getKeys();
		
		String key = keys.nextElement();
		menusKeys.add(key + "*" + ResourceBundle.getBundle("br.com.opsocial.server.utils.internacionalization.menus.menus").getString(key));
		
		while(keys.hasMoreElements()) {
			key = keys.nextElement();
			menusKeys.add(key + "*" + ResourceBundle.getBundle("br.com.opsocial.server.utils.internacionalization.menus.menus").getString(key));
			
		}
		
		return menusKeys;
	}

	public String getMenu01 () {return ResourceBundle.getBundle("menus").getString("menu01");}
	
	
}
