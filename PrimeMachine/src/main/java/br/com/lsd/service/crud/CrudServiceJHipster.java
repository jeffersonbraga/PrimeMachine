package br.com.lsd.service.crud;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.modelo.acao_corretiva.Acao_corretiva;
import br.com.primum.reflection.Reflection;

@RestController
@RequestMapping("api")
public class CrudServiceJHipster {
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @CrossOrigin
	@RequestMapping(value = "/crud",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> salvarObjeto(@Valid @RequestBody Object objPersistir) {
		try {
	    	
			Gson gsonDeserializer = new Gson();
	    	LinkedHashMap<String, Object> mpa = (LinkedHashMap) objPersistir;
	    	Object entidade = Class.forName((String) mpa.get("classeJava")).newInstance();
			CrudGenericBO crud = new CrudGenericBO();

	    	Reflection.montarObjetoAbstrato(entidade, (LinkedHashMap<String, Object>) mpa.get("objetoJson"));
	    	//recupera campo por campo com o método get() e imprime cada um
			crud.salvarObjetoCompleto(entidade);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<>(objPersistir, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/excluir",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> excluirObjeto(@Valid @RequestBody Object objPersistir) throws Exception {
		try {
	    	
			Gson gsonDeserializer = new Gson();
	    	LinkedHashMap<String, Object> mpa = (LinkedHashMap) objPersistir;
	    	Object entidade = Class.forName((String) mpa.get("classeJava")).newInstance();
			CrudGenericBO crud = new CrudGenericBO();

	    	Reflection.montarObjetoAbstrato(entidade, (LinkedHashMap<String, Object>) mpa.get("objetoJson"));
	    	//recupera campo por campo com o método get() e imprime cada um
			if (crud.excluirObjetoCompleto(entidade)) {
				return new ResponseEntity<>(objPersistir, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(objPersistir, HttpStatus.NOT_MODIFIED);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return new ResponseEntity<>(objPersistir, HttpStatus.NOT_MODIFIED);
		}
    }
}