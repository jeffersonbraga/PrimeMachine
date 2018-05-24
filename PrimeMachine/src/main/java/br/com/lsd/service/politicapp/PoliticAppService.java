package br.com.lsd.service.politicapp;

import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lsd.bo.pesquisa.PesquisaBO;
import br.com.lsd.modelo.PoliticAppDTO;
import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.candidato.Candidato;
import br.com.primum.modelo.container_principal.Container_principal;
import br.com.primum.modelo.evento_campanha.Evento_campanha;
import br.com.primum.reflection.Reflection;

@RestController
@RequestMapping("mobile")
public class PoliticAppService {

    @CrossOrigin
    @RequestMapping(value = "/politicapp",
            method = RequestMethod.POST)
    public void cargaInicial(HttpServletResponse response) throws URISyntaxException {

    	try {

    		PoliticAppDTO lEnvio = new PoliticAppDTO();

			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
			oos.writeObject(lEnvio);
			oos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}