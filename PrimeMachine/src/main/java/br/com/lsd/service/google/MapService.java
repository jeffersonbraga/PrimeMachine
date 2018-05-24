package br.com.lsd.service.google;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.OverDailyLimitException;
import com.google.maps.model.GeocodingResult;

import br.com.lsd.modelo.MapsData;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.campo.Campo;

@RestController
@RequestMapping("/lsd")
public class MapService {

    @CrossOrigin
	@RequestMapping(value= "/enderecos", method = RequestMethod.GET)
	public void getListaEnderecos() {

    	try {

    		//String sqlTabelas = "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname IN ('"+nomeSchema+"') and tablename not in ('codigosfixos','sessaodocumento','campospesquisa','databasechangelog','databasechangeloglock') and tablename not like 'jhi_%' ORDER BY schemaname, tablename ";
        	String sqlE = "select z.*,ed.idend as idend, ed.numend || ' ' || ed.endereco || ' ' || ed.bairro  || ' ' || cidade.NOME || ' ' || cidade.UF as endereco from (select y.idpess, sum(y.valor) as valor from (select x.idpess, ( coalesce(x.vlrvda,0) - coalesce(x.vlrdev,0) ) as valor from (SELECT n.idpess, CASE WHEN N.IDTIPOOPER IN ('VD', 'EF') THEN (NI.VALCONT) END AS VLRVDA ,CASE WHEN N.IDTIPOOPER IN ('DV') THEN (NI.VALCONT) END AS VLRDEV    FROM NOTAITEM NI       LEFT JOIN NOTA N ON N.ESTAB  = NI.ESTAB AND N.IDNOTA = NI.IDNOTA      LEFT JOIN USANDODE ON USANDODE.ESTAB = N.ESTAB AND USANDODE.TABELA = 'NOTACONF'      LEFT JOIN NOTACONF ON NOTACONF.ESTAB = USANDODE.USADE AND NOTACONF.IDNOTACONF = N.IDNOTACONF      LEFT JOIN FILIAL ON FILIAL.ESTAB = N.ESTAB      LEFT JOIN V_SIMPLEPESS V ON V.IDPESS = NI.IDVENDEDOR AND NI.IDVENDEDOR <> 1      LEFT JOIN ITEMESTAB ON ITEMESTAB.ESTAB = NI.ESTAB AND ITEMESTAB.IDITEM = NI.IDITEM      LEFT JOIN ITEM ON ITEM.ESTAB = ITEMESTAB.ESTABITEM AND ITEM.IDITEM =ITEMESTAB.IDITEM      LEFT JOIN FILIALCONFCAD ON FILIALCONFCAD.ESTAB = N.ESTAB    WHERE      N.ESTAB = 500 AND      N.IDTIPOOPER IN ('VD', 'DV', 'EF')      AND N.SITUACAO IN (5, 10) AND       N.EMISSAO BETWEEN '01/01/2016' AND '31/12/2016'      order by n.idpess) x) y       group by y.idpess) z inner join pessoadocend pdend on pdend.idpess = z.idpess      inner join endereco ed on ed.idend = pdend.idend and pdend.TIPOEND = 'C'       inner join cidade on cidade.CIDADE = ed.CIDADE      order by z.valor desc";
        	/*String sqlE =  "select z.*,ed.idend as idend"
    				//+ ", ed.numend || ' ' || ed.endereco || ' ' || ed.bairro  || ' ' || cidade.NOME || ' ' || cidade.UF as endereco "
    				 + ",cidade.NOME || ' ' || cidade.UF as endereco "
    				+ "from (select y.idpess, sum(y.valor) as valor from (select x.idpess, ( coalesce(x.vlrvda,0) - coalesce(x.vlrdev,0) ) as valor from (SELECT n.idpess, CASE WHEN N.IDTIPOOPER IN ('VD', 'EF') THEN (NI.VALCONT) END AS VLRVDA ,CASE WHEN N.IDTIPOOPER IN ('DV') THEN (NI.VALCONT) END AS VLRDEV    FROM NOTAITEM NI       LEFT JOIN NOTA N ON N.ESTAB  = NI.ESTAB AND N.IDNOTA = NI.IDNOTA      LEFT JOIN USANDODE ON USANDODE.ESTAB = N.ESTAB AND USANDODE.TABELA = 'NOTACONF'      LEFT JOIN NOTACONF ON NOTACONF.ESTAB = USANDODE.USADE AND NOTACONF.IDNOTACONF = N.IDNOTACONF      LEFT JOIN FILIAL ON FILIAL.ESTAB = N.ESTAB      LEFT JOIN V_SIMPLEPESS V ON V.IDPESS = NI.IDVENDEDOR AND NI.IDVENDEDOR <> 1      LEFT JOIN ITEMESTAB ON ITEMESTAB.ESTAB = NI.ESTAB AND ITEMESTAB.IDITEM = NI.IDITEM      LEFT JOIN ITEM ON ITEM.ESTAB = ITEMESTAB.ESTABITEM AND ITEM.IDITEM =ITEMESTAB.IDITEM      LEFT JOIN FILIALCONFCAD ON FILIALCONFCAD.ESTAB = N.ESTAB    WHERE      N.ESTAB = 500 AND      N.IDTIPOOPER IN ('VD', 'DV', 'EF')      AND N.SITUACAO IN (5, 10) AND       N.EMISSAO BETWEEN '01/01/2016' AND '31/12/2017'      order by n.idpess) x) y       group by y.idpess) z inner join pessoadocend pdend on pdend.idpess = z.idpess      inner join endereco ed on ed.idend = pdend.idend and pdend.TIPOEND = 'C' and ed.latitude is null       inner join cidade on cidade.CIDADE = ed.CIDADE      order by z.valor desc";
        	 */

    		GenericDAO dao = new GenericDAO();
    		dao.abrirTransacao();
    		//Object o = dao.findBySql(sqlE, new MapsData());
    		Object o = dao.getSession().createSQLQuery(sqlE).list();

			GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDNaYmm_ebMMP_yBrzML_KbQAYNU2g_uuM");

			int i 			= 0;
			String s 		= "";
			String update 	= "";
			List<MapsData> lsDados = new ArrayList<MapsData>();

    		for (Object[] item : (ArrayList<Object[]>) o) {

    			MapsData dt = new MapsData();
    			dt.setIdpess((BigDecimal) item[0]);
    			dt.setValor((BigDecimal) item[1]);
    			dt.setIdend(item[2]);
    			dt.setEndereco((String) item[3]);

    			GeocodingResult[] results = null;
				try {
					results = GeocodingApi.geocode(context, dt.getEndereco() ).await();
				} catch (OverDailyLimitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (results != null && results.length > 0) {

	    			dt.setResultado(results[0]);
					s = s + "['" + dt.getValor() + "'," + results[0].geometry.location + ", '" + dt.getEndereco() + "'],";
					update = update + "update endereco set latitude = '" + results[0].geometry.location.lat + "', longitude = '" + results[0].geometry.location.lng + "' where idend =" + dt.getIdend() + ";";
					i++;
				}

				lsDados.add(dt);
    		}

			System.out.println(s);
			//['Ponto Cliente', -22.10395100,-51.40561080, 8]
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @CrossOrigin
	@RequestMapping(value= "/endCliCompras", method = RequestMethod.GET)
	public void getDadosMapaClientes() {

		String sqlE = "select z.*,ed.idend as idend, ed.numend || ' ' || ed.endereco || ' ' || ed.bairro  || ' ' || cidade.NOME || ' ' || cidade.UF as endereco, ed.longitude as longitude, ed.latitude as latitude from (select y.idpess, sum(y.valor) as valor from (select x.idpess, ( coalesce(x.vlrvda,0) - coalesce(x.vlrdev,0) ) as valor from (SELECT n.idpess, CASE WHEN N.IDTIPOOPER IN ('VD', 'EF') THEN (NI.VALCONT) END AS VLRVDA ,CASE WHEN N.IDTIPOOPER IN ('DV') THEN (NI.VALCONT) END AS VLRDEV    FROM NOTAITEM NI       LEFT JOIN NOTA N ON N.ESTAB  = NI.ESTAB AND N.IDNOTA = NI.IDNOTA      LEFT JOIN USANDODE ON USANDODE.ESTAB = N.ESTAB AND USANDODE.TABELA = 'NOTACONF'      LEFT JOIN NOTACONF ON NOTACONF.ESTAB = USANDODE.USADE AND NOTACONF.IDNOTACONF = N.IDNOTACONF      LEFT JOIN FILIAL ON FILIAL.ESTAB = N.ESTAB      LEFT JOIN V_SIMPLEPESS V ON V.IDPESS = NI.IDVENDEDOR AND NI.IDVENDEDOR <> 1      LEFT JOIN ITEMESTAB ON ITEMESTAB.ESTAB = NI.ESTAB AND ITEMESTAB.IDITEM = NI.IDITEM      LEFT JOIN ITEM ON ITEM.ESTAB = ITEMESTAB.ESTABITEM AND ITEM.IDITEM =ITEMESTAB.IDITEM      LEFT JOIN FILIALCONFCAD ON FILIALCONFCAD.ESTAB = N.ESTAB    WHERE      N.ESTAB = 500 AND      N.IDTIPOOPER IN ('VD', 'DV', 'EF')      AND N.SITUACAO IN (5, 10) AND       N.EMISSAO BETWEEN '01/01/2016' AND '31/12/2017'      order by n.idpess) x) y       group by y.idpess) z inner join pessoadocend pdend on pdend.idpess = z.idpess      inner join endereco ed on ed.idend = pdend.idend and pdend.TIPOEND = 'C'       inner join cidade on cidade.CIDADE = ed.CIDADE      order by z.valor desc";

		GenericDAO dao = new GenericDAO();
		dao.abrirTransacao();
		//Object o = dao.findBySql(sqlE, new MapsData());
		Object o = dao.getSession().createSQLQuery(sqlE).list();

		int i 			= 0;
		String s 		= "";
		String update 	= "";
		List<MapsData> lsDados = new ArrayList<MapsData>();

		this.criarListaObjetoMapa(o, lsDados);
		for (MapsData item : lsDados) {
			s = s + "['" + item.getValor() + "', " + item.getLongitude() + ", " + item.getLatitude() + ",''],";
			update = update + "['" + item.getValor() + "', " + item.getLongitude() + ", " + item.getLatitude() + "],";
			//['191154.78',-22.13000030,-51.42182280, '680 RUA NATAL CASADEI JARDIM CAMBUY Presidente Prudente SP']
		}

		System.out.println(s);
		System.out.println(update);
	}

    @CrossOrigin
	@RequestMapping(value= "/getDadosMapaEntregas", method = RequestMethod.GET)
	public void getDadosMapaEntregas() {

		String sqlE = "SELECT DISTINCT IDEND, ENDERECO, geografico FROM ( SELECT  'Nota: ' ||NOTA.IDNOTA NUMDOC  ,VCLI.IDPESS || ' - ' || VCLI.NOME CLIENTE  ,VEND.IDEND AS IDEND  ,'Cidade ' || CIDADE.NOME ENDERECO  ,'Obs: '|| REPLACE (NOTAMSG.OBS, 'Nota Gerada do Carrinho Nro: ',' Carrinho: ')  || ' ' ||CARRINHO.NOME || ' ' || CARRINHO.TELEFONE OBS  ,ITEM.IDITEM  ,ITEM.DESCRICAO|| ' ' ||MARCA.DESCRICAO DESCRICAO  ,LOCALRETIRADA.DESCRICAO LOCALRETIRADA  ,(NOTAITEM.QUANTIDADE) QTDEENTREGAR  ,COALESCE(EMBALAGEM.UNIDADE, ITEM.UNIDADE) AS UNIDADE  ,NOTAITEM.IDVENDEDOR  ,PVEND.NOME AS NOMEVENDEDOR  ,ITEMCATEGORIA.IDDEPTO  ,NOTAMCP.OBSEXPEDICAO  ,'Volumes: '|| NOTAMCP.QTDVOLUMEEXP AS VOLUME  ,CA.COMPL  ,ITEMESTAB.LOCALIZACAO  ,GRUPOROTEIRO.IDGRUPO  ,GRUPOROTEIRO.DESCRICAO AS ROTEIRO  ,NOTAMCP.QTDVOLUMEEXP AS VOLUMES  ,NOTAEXPEDICAO.SEQROTEIRO  ,NOTA.IDNOTACONF  ,VEND.LATITUDE || ', ' || VEND.LONGITUDE AS GEOGRAFICO FROM NOTAEXPEDICAO  LEFT JOIN NOTAITEMCP NIC ON (NOTAEXPEDICAO.ESTAB = NIC.ESTAB) AND (NOTAEXPEDICAO.IDNOTA = NIC.IDNOTA) AND (NOTAEXPEDICAO.SEQITEM = NIC.SEQITEM)  LEFT JOIN NOTAMCP ON NIC.ESTAB = NOTAMCP.ESTAB AND NIC.IDNOTA = NOTAMCP.IDNOTA  LEFT JOIN NOTAITEM ON (NOTAITEM.ESTAB = NIC.ESTAB) AND (NOTAITEM.IDNOTA = NIC.IDNOTA) AND (NOTAITEM.SEQITEM = NIC.SEQITEM)  LEFT JOIN NOTA ON (NOTA.ESTAB = NOTAITEM.ESTAB) AND (NOTA.IDNOTA = NOTAITEM.IDNOTA)  LEFT JOIN EMBALAGEM ON (EMBALAGEM.IDEMBALAGEM = NOTAITEM.IDEMBALAGEM)  LEFT JOIN LOCALRETIRADA ON (LOCALRETIRADA.IDLOCALRETIRADA = NOTAEXPEDICAO.IDLOCALENTREGA)  LEFT JOIN NOTAMSG ON (NOTAMSG.ESTAB = NOTA.ESTAB) AND (NOTAMSG.IDNOTA = NOTA.IDNOTA)  LEFT JOIN CARRINHO ON (CARRINHO.ESTAB = NOTA.ESTAB) AND (CARRINHO.IDDOCTO = NOTA.IDNOTA)  LEFT JOIN CARRINHOITEM CA ON CARRINHO.ESTAB = CA.ESTAB AND CARRINHO.IDCARRINHO = CA.IDCARRINHO                              AND NOTAITEM.IDITEM = CA.IDITEM AND NOTAITEM.SEQITEM = CA.SEQITEM  LEFT JOIN ITEMESTAB ON (ITEMESTAB.ESTAB = NOTAITEM.ESTAB) AND (ITEMESTAB.IDITEM = NOTAITEM.IDITEM)  LEFT JOIN ITEM ON (ITEM.ESTAB = ITEMESTAB.ESTABITEM) AND (ITEM.IDITEM =ITEMESTAB.IDITEM)  LEFT JOIN V_PESSOADOC VCLI ON (VCLI.ESTAB = NOTA.ESTAB) AND (VCLI.IDPESS = NOTA.IDPESS)  LEFT JOIN ENDERECO VEND ON (VEND.IDEND = NOTAMCP.IDENDENTREGA) AND (VEND.LATITUDE IS NULL)   LEFT JOIN CIDADE ON (CIDADE.CIDADE = VEND.CIDADE)  LEFT JOIN V_PESSOADOC PVEND ON (PVEND.ESTAB = NOTAITEM.ESTAB) AND (PVEND.IDPESS = NOTAITEM.IDVENDEDOR) LEFT JOIN ITEMCATEGORIA ON ITEMCATEGORIA.ESTAB = ITEM.ESTAB AND ITEMCATEGORIA.IDITEM = ITEM.IDITEM  LEFT JOIN ITEMEMP ON ITEMEMP.ESTABITEM = ITEMESTAB.ESTABITEM AND ITEMEMP.IDITEM = ITEMESTAB.IDITEM AND ITEMEMP.EMPRESA = ITEMESTAB.ESTAB  LEFT JOIN MARCA ON MARCA.IDMARCA = ITEMCATEGORIA.IDMARCA  LEFT JOIN GRUPOROTEIRO ON GRUPOROTEIRO.IDGRUPO = NOTAEXPEDICAO.GRUPOROTEIRO ORDER BY NOTAEXPEDICAO.GRUPOROTEIRO ASC, NOTAEXPEDICAO.SEQROTEIRO DESC, ITEMESTAB.LOCALIZACAO DESC)";

		GenericDAO dao = new GenericDAO();
		dao.abrirTransacao();
		//Object o = dao.findBySql(sqlE, new MapsData());
		Object o = dao.getSession().createSQLQuery(sqlE).list();

		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDNaYmm_ebMMP_yBrzML_KbQAYNU2g_uuM");
		String update 	= "";

		for (Object[] item : (ArrayList<Object[]>) o) {

			GeocodingResult[] results = null;
			try {
				results = GeocodingApi.geocode(context, (String) item[1] ).await();
			} catch (OverDailyLimitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (results != null && results.length > 0) {

				update = update + "update endereco set latitude = " + results[0].geometry.location.lat + ", longitude = " + results[0].geometry.location.lng + " where idend = " + item[0] + ";";
			}
		}

		System.out.println(update);
	}

	private void criarListaObjetoMapa(Object o, List<MapsData> lsDados) {

		for (Object[] item : (ArrayList<Object[]>) o) {

			MapsData dt = new MapsData();
			dt.setIdpess((BigDecimal) item[0]);
			dt.setValor((BigDecimal) item[1]);
			dt.setIdend(item[2]);
			dt.setEndereco((String) item[3]);
			dt.setLatitude((BigDecimal) item[4]);
			dt.setLongitude((BigDecimal) item[5]);
			lsDados.add(dt);
		}
	}
}