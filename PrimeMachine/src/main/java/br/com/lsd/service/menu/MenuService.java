package br.com.lsd.service.menu;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lsd.bo.menu.MenuBO;
import br.com.primum.modelo.menu.Menu;
import br.com.primum.modelo.modulo.Modulo;
import br.com.primum.modelo.modulo_indicadores.Modulo_indicadores;

@RestController
@RequestMapping("api")
public class MenuService {

	public MenuService() {

	}

	@Inject
	private MenuBO menuBO;

	public String getMenu(String modulo) throws Exception {

		return this.getMenuBO().getMenu(modulo);
	}

	public MenuBO getMenuBO() {
		
		if (this.menuBO == null) {
			this.setMenuBO(new MenuBO());			
		}

		return menuBO;
	}

	public void setMenuBO(MenuBO menuBO) {
		this.menuBO = menuBO;
	}

    @CrossOrigin
	@RequestMapping(value = "/MenuService/listaModulos",
        method = RequestMethod.GET)
    public ResponseEntity<List<Modulo>> listaModulos(Integer cd_usuario, Integer cd_unidade){
		return new ResponseEntity<>(this.getMenuBO().listaModulos(cd_usuario, cd_unidade), HttpStatus.OK);
	}

	public List<Modulo_indicadores> listaModulosIndicadores( Integer cd_usuario, Integer cd_unidade ){
		return this.getMenuBO().listaModulosIndicadores( cd_usuario, cd_unidade );
	}

	public List<Menu> listaProgramasMenu(String str){
		return this.getMenuBO().listaProgramasMenu( str );
	}
}