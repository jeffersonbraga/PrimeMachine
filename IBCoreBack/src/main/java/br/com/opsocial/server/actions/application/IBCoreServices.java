package br.com.opsocial.server.actions.application;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceGenericRemote;
import br.com.opsocial.ejb.entity.cronograma.Cronograma;
import br.com.opsocial.ejb.entity.pessoa.Pessoa;
import br.com.opsocial.ejb.entity.procedimento.Procedimento;
import br.com.opsocial.ejb.entity.questionario.Questionario;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class IBCoreServices {

	@CrossOrigin
	@RequestMapping(value = "/savePessoa",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> savePessoa(@Valid @RequestBody Pessoa pessoa) {
		MaintenanceGenericRemote remote = RecoverMaintenance.getRemoteGenericMaintenance("Generic");

		try {
			remote.save(pessoa);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@CrossOrigin
	@RequestMapping(value = "/saveAgendar",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveAgendar(@Valid @RequestBody Pessoa pessoa) {
		return null;
	}

	@CrossOrigin
	@RequestMapping(value = "/saveProcedimento",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveProcedimento(@Valid @RequestBody Procedimento procedimento) {
		return null;
	}

	@CrossOrigin
	@RequestMapping(value = "/saveQuestionario",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveQuestionario(@Valid @RequestBody Questionario questionario) {
		return null;
	}

	@CrossOrigin
	@RequestMapping(value = "/saveCronograma",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveCronograma(@Valid @RequestBody Cronograma cronograma) {
		return null;
	}

	@CrossOrigin
	@RequestMapping(value = "/saveGenerico",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveGenerico(@Valid @RequestBody Object pessoa) {
		MaintenanceGenericRemote remote = RecoverMaintenance.getRemoteGenericMaintenance("Generic");

		try {
			remote.save(pessoa);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
