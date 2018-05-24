package br.com.lsd.service.pesquisa;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import br.com.lsd.bo.pesquisa.PesquisaBO;
import br.com.lsd.util.HeaderUtil;
import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.ferramentas.ObjetoVirtual;
import br.com.primum.modelo.acao_corretiva.Acao_corretiva;
import br.com.primum.modelo.campo_formulario.Campo_formulario;
import br.com.primum.modelo.categoria_ishikawa.Categoria_ishikawa;
import br.com.primum.modelo.documento.Documento;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.modelo.setor.Setor;
import br.com.primum.reflection.Reflection;
import br.com.primum.modelo.periodicidade.Periodicidade;

@RestController
@RequestMapping("api")
public class PesquisaServiceJHipster {

	private PesquisaBO pesquisaBO;
	
	public PesquisaServiceJHipster() {
		
	}

	public PesquisaBO getPesquisaBO() {
		return pesquisaBO;
	}

	public void setPesquisaBO(PesquisaBO pesquisaBO) {
		this.pesquisaBO = pesquisaBO;
	}

    /**
     * GET  /users : get all users.
     * 
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     * @throws URISyntaxException if the pagination headers couldnt be generated
     */
    @CrossOrigin
    @RequestMapping(value = "/pesquisar",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object>> pesquisaGrid(String ds_caminho_classe) throws URISyntaxException {

		this.setPesquisaBO(new PesquisaBO());
		List<Object> l = null;
		try {
			l = (List<Object>) this.getPesquisaBO().getListaObjetos(ds_caminho_classe, true, Reflection.getExisteChavePrimaria(Class.forName(ds_caminho_classe)), 0);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @CrossOrigin
	@RequestMapping(value = "/pesquisaPadrao",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> pesquisarAngular2(@Valid @RequestBody Object obj) {

		List<Object> l = null;
		Object o = null;
		try {

			Gson gsonDeserializer = new Gson();
	    	LinkedHashMap<String, Object> mpa = (LinkedHashMap) obj;
	    	String ds_caminho_classe = (String) mpa.get("classeJava");

	    	Object entidade = Class.forName((String) mpa.get("classeJava")).newInstance();
	    	Reflection.montarObjetoAbstrato(entidade, (LinkedHashMap<String, Object>) mpa.get("objetoJson"));
	    	
	    	this.setPesquisaBO(new PesquisaBO());
	    	Object vl_chave = Reflection.getValorChavePrimaria(entidade);
	    	if (vl_chave != null) {
	    		o = this.getPesquisaBO().getObjeto(ds_caminho_classe, false, ((Integer) vl_chave));
	    		return new ResponseEntity<>(o, HttpStatus.OK);
	    	} else {	    	
	    		l = (List<Object>) this.getPesquisaBO().getListaObjetos(ds_caminho_classe, false, Reflection.getExisteChavePrimaria(Class.forName(ds_caminho_classe)), 0);
	    		return new ResponseEntity<>(l, HttpStatus.OK);
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
    }

    /**
     * GET  /users : get all users.
     * 
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     * @throws Exception 
     */
    @CrossOrigin
    @RequestMapping(value = "/pesquisar",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> pesquisaItemGrid(@Valid @RequestBody ObjetoVirtual obj_virtual) throws Exception {

		this.setPesquisaBO(new PesquisaBO());
		Object l = null;
		try {
			l = this.getPesquisaBO().getObjeto(obj_virtual.getClasseJava(), true, new Integer(obj_virtual.getValorFiltro()));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String nm_tabelaaUX 	= l.getClass().getSimpleName().toLowerCase().replaceAll("_", "-");
        return ResponseEntity.created(new URI("/api/" + nm_tabelaaUX + "/" + Reflection.getValorChavePrimaria(l)))
            .headers(HeaderUtil.createEntityCreationAlert(l.getClass().getSimpleName(), Reflection.getValorChavePrimaria(l).toString()))
            .body(l);
    }

	public List<?> pesquisa(String datafield, String valor) throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().pesquisar(datafield, valor);
	}

	public List<?> getListaObjetos(String ds_caminhoclasse, Boolean buscarCompleto, String campoOrdenar, Integer tipoOrdenacao) throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaObjetos(ds_caminhoclasse, buscarCompleto, campoOrdenar, tipoOrdenacao);
	}

	public List<?> getListaTabelasNotificacao() throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaTabelasNotificacao();
	}

	public List<?> getObjetoByTabelaEID(Integer cd_tabela, Integer cd_obj) throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getObjetoByTabelaEID(cd_tabela, cd_obj);
	}

	public Boolean criarBeans(String nm_tabela) throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().criarBeans(nm_tabela);
	}

	public Integer verificaNroSequencial() throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().verificaNroSequencial();
	}
	
	public Integer verificaNroSequencialParametros(String chavePrimaria, String tabela) throws Exception {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().verificaNroSequencialParametros( chavePrimaria, tabela );
	}

	public Integer mudaRevisao(Integer numRevisao, Integer revisaoAtual){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().mudaRevisao(numRevisao, revisaoAtual);
	}

	public Integer getNumDocumento(Integer tp_documento, Integer tp_processo){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getNumDocumento(tp_documento, tp_processo);
	}

	public Integer verificaExistencia(Integer tp_documento, Integer tp_processo, Integer nr_sequencial){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().verificaExistencia(tp_documento, tp_processo, nr_sequencial);
	}

	public List<Documento> getListaDocumentos(Integer cd_usuario){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaDocumentos(cd_usuario);
	}

	public List<List<?>> getListaDocumentosListaMestra(){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaDocumentosListaMestra();
	}

	public List<Helpdesk_ocorrencia> getListaHelpDesks(Integer cd_usuario, Date dt_inicial, Date dt_final, Integer cd_unidade, Integer st_hd, Boolean buscarComFiltro){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaHelpDesks(cd_usuario, dt_inicial, dt_final, cd_unidade, st_hd, buscarComFiltro);
	}

	public List<Helpdesk_ocorrencia> getListaHelpDesksAbertos(Integer cd_usuario){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaHelpDesksAbertos(cd_usuario);
	}

	public List<Helpdesk_ocorrencia> getListaHelpDesksAnalise(Integer cd_usuario){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getListaHelpDesksAnalise(cd_usuario);
	}

	public List<Categoria_ishikawa> getCategoriasIshikawa(){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getCategoriasIshikawa();
	}

	public List<Setor> pesquisaServiceSetor(Integer cd_unidade) {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().pesquisaServiceSetor(cd_unidade);		
	}

	public List<Periodicidade> getPeriodicidadePorSituacao(){
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().getPeriodicidadePorSituacao();
	}

	public Campo_formulario buscarCampoIdentificador(Formulario formulario) {
		this.setPesquisaBO(new PesquisaBO());
		return this.getPesquisaBO().buscarCampoIdentificador(formulario);
	}
}




class PaginationUtil {

    public static HttpHeaders generatePaginationHttpHeaders(Page<?> page, String baseUrl)
        throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + page.getTotalElements());
        String link = "";
        if ((page.getNumber() + 1) < page.getTotalPages()) {
            link = "<" + generateUri(baseUrl, page.getNumber() + 1, page.getSize()) + ">; rel=\"next\",";
        }
        // prev link
        if ((page.getNumber()) > 0) {
            link += "<" + generateUri(baseUrl, page.getNumber() - 1, page.getSize()) + ">; rel=\"prev\",";
        }
        // last and first link
        int lastPage = 0;
        if (page.getTotalPages() > 0) {
            lastPage = page.getTotalPages() - 1;
        }
        link += "<" + generateUri(baseUrl, lastPage, page.getSize()) + ">; rel=\"last\",";
        link += "<" + generateUri(baseUrl, 0, page.getSize()) + ">; rel=\"first\"";
        headers.add(HttpHeaders.LINK, link);
        return headers;
    }

    private static String generateUri(String baseUrl, int page, int size) throws URISyntaxException {
        return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size).toUriString();
    }


    
}