package br.com.primum.bo.escalonamento_formulario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.sun.org.apache.bcel.internal.generic.NEW;

import br.com.primum.bo.crud.CrudGenericBO;
//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.escala_tempo.Escala_tempo;
import br.com.primum.modelo.escalonamento_formulario.Escalonamento_formulario;
import br.com.primum.modelo.escalonamento_usuario.Escalonamento_usuario;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.ocorrencia_periodicidade.Ocorrencia_periodicidade;
import br.com.primum.modelo.periodicidade.Periodicidade;
import br.com.primum.modelo.programa_qualidade.Programa_qualidade;
import br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario;
import br.com.primum.modelo.revisao_programa_qualidade.Revisao_programa_qualidade;
import br.com.primum.modelo.usuario.Usuario;


public class Escalonamento_formularioBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<?> montarPainelProximoDia(Integer qtdeDias, Integer cd_unidade ){
		this.setDao(new GenericDAO());
		List<?> lista = this.getDao().getSession().createCriteria(Usuario.class).addOrder(Order.asc("cd_usuario")).list();
		List<Object> listaRetorno = new ArrayList<>();

		
		
		for (Usuario usuario : (List<Usuario>) lista) {
			
			List<Escalonamento_usuario> listaAtividades = this.buscarCronogramaAtividadesProximoDia(usuario.getCd_usuario(), 0, qtdeDias, cd_unidade);
			List<Escalonamento_usuario> listaAtividadesMonit = this.buscarCronogramaAtividadesProximoDia(usuario.getCd_usuario(), 1, qtdeDias, cd_unidade);
			List<Escalonamento_usuario> listaAtividadesDocumental = this.buscarCronogramaAtividadesProximoDia(usuario.getCd_usuario(), 2, qtdeDias, cd_unidade);
			List<Object> listaAux = new ArrayList<>();
			
			if (listaAtividades != null) {
				if (listaAtividadesMonit != null)
					listaAtividades.addAll(listaAtividadesMonit);
				
				if (listaAtividadesDocumental != null)
					listaAtividades.addAll(listaAtividadesDocumental);
			}

			listaAux.add(usuario);
			listaAux.add(listaAtividades);

			listaRetorno.add(listaAux);
		}
		return listaRetorno;
	}
	
	
	public List<Escalonamento_usuario> buscarCronogramaAtividadesProximoDia(Integer cd_usuario, Integer tp_formulario, Integer qtdeDias, Integer cd_unidade) {
		
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		GregorianCalendar dataAtual = (GregorianCalendar) GregorianCalendar.getInstance();
		dataAtual.set(Calendar.DATE, dataAtual.get(Calendar.DATE) + qtdeDias);
		Integer diaSemana = dataAtual.get(GregorianCalendar.DAY_OF_WEEK);

		//System.out.println( new SimpleDateFormat("dd/MM/yyyy").format(dataAtual.getTime()) );
		this.setDao(new GenericDAO());
		
		Criterion a 	= Restrictions.isNull("dt_termino_vigencia");
		Criterion b 	= Restrictions.ge("dt_termino_vigencia", dataAtual.getTime());
		Criterion c 	= Restrictions.or(a, b);
		
		List<?> lista = this.getDao().getSession().createCriteria(Escalonamento_usuario.class)
																	.add(Restrictions.eq("cd_usuario", cd_usuario))
																	.add(Restrictions.like("ds_dias_repeticao", "%" + diaSemana.toString() + "%"))
																	.add(c)
																	.addOrder(Order.asc("hr_execucao"))
																	.list();
		
		
		List<Escalonamento_usuario> listaAux = new ArrayList<>();

		try {
			for (Escalonamento_usuario esc : (List<Escalonamento_usuario>) lista) {
				Escalonamento_formulario escalonamento_formulario = (Escalonamento_formulario) this.getDao().getSession().createCriteria(Escalonamento_formulario.class).add(Restrictions.eq("cd_escalonamento_formulario", esc.getCd_escalonamento_formulario())).add(Restrictions.eq("cd_unidade", cd_unidade)).uniqueResult();
				if(escalonamento_formulario!=null){
					Formulario formulario = (Formulario) this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", escalonamento_formulario.getCd_formulario())).add(Restrictions.eq("tp_formulario", tp_formulario)).uniqueResult();
	
					if ( (formulario != null) && (formulario.getCd_formulario() != null) ) {
						
						GregorianCalendar dataEscalonado = new GregorianCalendar();
						GregorianCalendar dataPrevista = new GregorianCalendar();
	
						GregorianCalendar dataInicialDia = (GregorianCalendar) GregorianCalendar.getInstance();
						dataInicialDia.set(Calendar.DATE, dataInicialDia.get(Calendar.DATE)+qtdeDias);
						dataInicialDia.set(Calendar.HOUR_OF_DAY,0);     
						dataInicialDia.set(Calendar.MINUTE,0);     
						dataInicialDia.set(Calendar.SECOND,0);     
						
						GregorianCalendar dataFinalDia = (GregorianCalendar) GregorianCalendar.getInstance();
						dataFinalDia.set(Calendar.DATE, dataFinalDia.get(Calendar.DATE)+qtdeDias);
						dataFinalDia.set(Calendar.HOUR_OF_DAY,23);     
						dataFinalDia.set(Calendar.MINUTE,59);     
						dataFinalDia.set(Calendar.SECOND,59); 
						
						dataEscalonado.setTime(esc.getHr_execucao());
						dataPrevista.setTime(new Date());
						dataPrevista.set(dataPrevista.HOUR, dataEscalonado.get(dataEscalonado.HOUR));
						dataPrevista.set(dataPrevista.MINUTE, dataEscalonado.get(dataEscalonado.MINUTE));
	
						//SITUA��O 0 --> PROGRAMADA
						Integer st_escalonamento_usuario = 0;
						Boolean vigenciaPermitida=false;
						if( escalonamento_formulario.getDt_inicio_vigencia() == null ) { vigenciaPermitida=true;}
						if(escalonamento_formulario.getDt_inicio_vigencia() != null ){
	
							//come�a hoje
							if(escalonamento_formulario.getDt_inicio_vigencia().after(dataInicialDia.getTime()) && escalonamento_formulario.getDt_inicio_vigencia().before(dataFinalDia.getTime())){
								//termina hoje
								if(escalonamento_formulario.getDt_termino_vigencia().before(dataFinalDia.getTime())){
									vigenciaPermitida=true;
								}
								//termina mais alem
								if(escalonamento_formulario.getDt_termino_vigencia().after(dataFinalDia.getTime())){
									vigenciaPermitida=true;
								}
							}
							
							//j� come�ou
							if(escalonamento_formulario.getDt_inicio_vigencia().before(dataInicialDia.getTime()) ){
								if(escalonamento_formulario.getDt_termino_vigencia().after(dataInicialDia.getTime())){
									vigenciaPermitida=true;
								}
								
							}
						}
	
						if(vigenciaPermitida){
							if(qtdeDias<=0){
								Execucao_formulario execucao = null;
								
								try {
									execucao = (Execucao_formulario) this.getDao().getSession().createCriteria(Execucao_formulario.class)
											.add(Restrictions.eq("cd_formulario", formulario.getCd_formulario()))
											.add(Restrictions.eq("cd_usuario", cd_usuario))
											.add(Restrictions.eq("cd_escalonamento_usuario", esc.getCd_escalonamento_usuario()))
											.add(Restrictions.ge("dt_execucao_formulario", dataInicialDia.getTime()))
											.add(Restrictions.le("nr_hora_termino", dataFinalDia.getTime())).uniqueResult();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									//LogPrimum.mensagem("2 OU MAIS EXECU��ES PARA UMA TAREFA NA MESMA DATA. CHECAR ESCALONAMENTO_BO");
								}
	
								if(execucao!=null && execucao.getCd_execucao_formulario()!=null){
									st_escalonamento_usuario = this.verificaSituacaoAtividade(execucao, esc, formulario);
	
									if (execucao.getDt_execucao_formulario() != null) {
										esc.setHr_execucao_efetiva( execucao.getDt_execucao_formulario().toString().split(" ")[1] );
									}
	
									if (execucao.getNr_hora_termino() != null) {
										esc.setHr_termino_efetivo( execucao.getNr_hora_termino().toString().split(" ")[1] );
									}
									
									esc.setExecucao_formulario( execucao );
									
								}else{
									if(qtdeDias==0){
										if( ( dataAtual.get(Calendar.HOUR_OF_DAY) == (dataEscalonado.get(Calendar.HOUR_OF_DAY) ) )){
											if(  ( dataAtual.get(Calendar.MINUTE) > dataEscalonado.get(Calendar.MINUTE) ) ){
											//ATRASADA
												st_escalonamento_usuario = 3;
											}
										}else if( ( dataAtual.get(Calendar.HOUR_OF_DAY) > (dataEscalonado.get(Calendar.HOUR_OF_DAY) ) ) ){
											st_escalonamento_usuario = 3;
										}
									}else{
										st_escalonamento_usuario = 3;
									}
								}
							}
							
							
							pesquisa.buscarObjetoCompleto(formulario);
							escalonamento_formulario.setFormulario(formulario);
							esc.setEscalonamento_formulario(escalonamento_formulario);						
							esc.setSt_escalonamento_usuario(st_escalonamento_usuario);
	
							Revisao_programa_qualidade revisao = (Revisao_programa_qualidade) this.getDao().getSession().createCriteria(Revisao_programa_qualidade.class).add(Restrictions.eq("cd_revisao_programa_qualidade", formulario.getCd_revisao_programa_qualidade())).uniqueResult();
							if (revisao.getSt_revisao_programa_qualidade().equals(2)) {
								listaAux.add(esc);
							}
						}
					}
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return (List<Escalonamento_usuario>) listaAux;
	}
	
	//FUN��O DO JEFF
	@SuppressWarnings({ "unchecked", "static-access" })
	public List<Escalonamento_usuario> buscarCronogramaAtividades(Integer cd_usuario, Integer tp_formulario, Integer cd_unidade) {

		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();

		GregorianCalendar dataAtual = (GregorianCalendar) GregorianCalendar.getInstance();
		//dataAtual.setTime(new Date());
		Integer diaSemana = dataAtual.get(GregorianCalendar.DAY_OF_WEEK);

		Criterion a 	= Restrictions.isNull("dt_termino_vigencia");
		Criterion b 	= Restrictions.ge("dt_termino_vigencia", dataAtual.getTime());
		Criterion critOr 	= Restrictions.or(a, b);
		
		this.setDao(new GenericDAO());
		List<?> lista = this.getDao().getSession().createCriteria(Escalonamento_usuario.class)
																	.add(Restrictions.eq("cd_usuario", cd_usuario))
																	.add(Restrictions.like("ds_dias_repeticao", "%" + diaSemana.toString() + "%"))
																	.add(critOr)
																	.addOrder(Order.asc("hr_execucao"))
																	.list();

		List<Escalonamento_usuario> listaAux = new ArrayList<>();

		try {
			for (Escalonamento_usuario esc : (List<Escalonamento_usuario>) lista) {

				Escalonamento_formulario escalonamento_formulario = (Escalonamento_formulario) this.getDao().getSession().createCriteria(Escalonamento_formulario.class).add(Restrictions.eq("cd_escalonamento_formulario", esc.getCd_escalonamento_formulario())).add(Restrictions.eq("cd_unidade", cd_unidade)).uniqueResult();
				if(escalonamento_formulario!=null){
					Formulario formulario = (Formulario) this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", escalonamento_formulario.getCd_formulario())).add(Restrictions.eq("tp_formulario", tp_formulario)).uniqueResult();
					Periodicidade periodicidade = null;
	
					//System.out.println("escalonamento_formulario: " + escalonamento_formulario.getCd_escalonamento_formulario());
					//System.out.println("escalonamento_usuario: " + esc.getCd_escalonamento_usuario());
					if ( (formulario != null) && (formulario.getCd_formulario() != null) ) {
	
						if (formulario != null) {
							periodicidade = (Periodicidade) this.getDao().getSession().createCriteria(Periodicidade.class).add(Restrictions.eq("cd_periodicidade", formulario.getCd_periodicidade())).uniqueResult();
						}
						/******************/
	
						GregorianCalendar dataEscalonado = new GregorianCalendar();
						GregorianCalendar dataPrevista = new GregorianCalendar();
	
						GregorianCalendar dataInicialDia = (GregorianCalendar) GregorianCalendar.getInstance();
						dataInicialDia.set(Calendar.HOUR_OF_DAY,0);     
						dataInicialDia.set(Calendar.MINUTE,0);     
						dataInicialDia.set(Calendar.SECOND,0);
	
						GregorianCalendar dataFinalDia = (GregorianCalendar) GregorianCalendar.getInstance();
						dataFinalDia.set(Calendar.HOUR_OF_DAY,23);     
						dataFinalDia.set(Calendar.MINUTE,59);     
						dataFinalDia.set(Calendar.SECOND,59);  
	
						/*System.out.println(""+ dataInicialDia.get(Calendar.YEAR) +"-"+ dataInicialDia.get(Calendar.MONTH)
									+"-"+ dataInicialDia.get(Calendar.DAY_OF_MONTH) 
									+"  "+ dataInicialDia.get(Calendar.HOUR_OF_DAY)
									+":"+ dataInicialDia.get(Calendar.MINUTE)
									+":"+ dataInicialDia.get(Calendar.SECOND));
						*/
						dataEscalonado.setTime(esc.getHr_execucao());
						dataPrevista.setTime(new Date());
						dataPrevista.set(dataPrevista.HOUR, dataEscalonado.get(dataEscalonado.HOUR));
						dataPrevista.set(dataPrevista.MINUTE, dataEscalonado.get(dataEscalonado.MINUTE));
	
						//SITUA��O 0 --> PROGRAMADA
						Integer st_escalonamento_usuario = 0;
	
						//System.out.println("cd_formulario" + formulario.getCd_formulario());
						//System.out.println("cd_usuario" + cd_usuario);
						//System.out.println("cd_escalonamento_usuario" + esc.getCd_escalonamento_usuario());
						//System.out.println("dt_execucao_formulario" + dataInicialDia.getTime());
	
						Boolean vigenciaPermitida=false;
						if( escalonamento_formulario.getDt_inicio_vigencia() == null ) { vigenciaPermitida=true;}
						if(escalonamento_formulario.getDt_inicio_vigencia() != null ){
	
							//come�a hoje
							if(escalonamento_formulario.getDt_inicio_vigencia().after(dataInicialDia.getTime()) && escalonamento_formulario.getDt_inicio_vigencia().before(dataFinalDia.getTime())){
								//termina hoje
								if(escalonamento_formulario.getDt_termino_vigencia().before(dataFinalDia.getTime())){
									vigenciaPermitida=true;
								}
								//termina mais alem
								if(escalonamento_formulario.getDt_termino_vigencia().after(dataFinalDia.getTime())){
									vigenciaPermitida=true;
								}
							}
	
							//j� come�ou
							if(escalonamento_formulario.getDt_inicio_vigencia().before(dataInicialDia.getTime()) ){
								if(escalonamento_formulario.getDt_termino_vigencia().after(dataInicialDia.getTime())){
									vigenciaPermitida=true;
								}
							}
						}
	
						if(vigenciaPermitida){
							Execucao_formulario execucao = null;
							boolean addEscalonamento_ciclo_tempo = false;
	
							try {
	
								if (periodicidade.getTp_ciclo_tempo() == null) {
									execucao = (Execucao_formulario) this.getDao().getSession().createCriteria(Execucao_formulario.class)
											.add(Restrictions.eq("cd_formulario", formulario.getCd_formulario()))
											.add(Restrictions.eq("cd_usuario", cd_usuario))
											.add(Restrictions.eq("cd_escalonamento_usuario", esc.getCd_escalonamento_usuario()))
											.add(Restrictions.ge("dt_execucao_formulario", dataInicialDia.getTime())).uniqueResult();
								} else {
	
									execucao = (Execucao_formulario) this.getDao().getSession().createCriteria(Execucao_formulario.class)
											.add(Restrictions.eq("cd_formulario", formulario.getCd_formulario()))
											.add(Restrictions.eq("cd_usuario", cd_usuario))
											.add(Restrictions.eq("cd_escalonamento_usuario", esc.getCd_escalonamento_usuario()))
											.addOrder(Order.desc("dt_execucao_formulario"))
											.setMaxResults(1)
											.uniqueResult();
	
									if ( (execucao != null) && (execucao.getCd_execucao_formulario()!=null) ) {
	
										Escala_tempo escala_tempo = (Escala_tempo) this.getDao().getSession().createCriteria(Escala_tempo.class).add(Restrictions.eq("cd_escala_tempo", periodicidade.getCd_escala_tempo())).uniqueResult();
										GregorianCalendar dataPrevistaCiclo_tempo = new GregorianCalendar();
										dataPrevistaCiclo_tempo.add(dataPrevistaCiclo_tempo.MINUTE, -(periodicidade.getVl_tempo_periodicidade() * escala_tempo.getVl_equivalencia_minuto()));
	
										GregorianCalendar dataExecucao = new GregorianCalendar();
										dataExecucao.setTime(execucao.getDt_execucao_formulario());
	
										if (execucao.getDt_execucao_formulario().compareTo(dataPrevistaCiclo_tempo.getTime()) < 1) {
											//if ( (dataExecucao.get(dataExecucao.DAY_OF_YEAR) < dataPrevistaCiclo_tempo.get(dataExecucao.DAY_OF_YEAR)) || (dataExecucao.get(dataExecucao.YEAR) < dataPrevistaCiclo_tempo.get(dataExecucao.YEAR)) ) {
											if (this.validarData(dataExecucao, dataPrevistaCiclo_tempo)) {
												execucao = null;
											}
	
											addEscalonamento_ciclo_tempo = true;
										} else if ( (dataPrevistaCiclo_tempo.get(dataPrevistaCiclo_tempo.DAY_OF_YEAR) <= dataAtual.get(dataAtual.DAY_OF_YEAR)) && 
												(dataPrevistaCiclo_tempo.get(dataPrevistaCiclo_tempo.DAY_OF_YEAR) >= dataExecucao.get(dataAtual.DAY_OF_YEAR)) ) {
	
											addEscalonamento_ciclo_tempo = true;
										} else if ( (execucao.getDt_execucao_formulario().compareTo(dataPrevistaCiclo_tempo.getTime()) > -1) && (this.validarData(dataExecucao, dataPrevistaCiclo_tempo))) {
											addEscalonamento_ciclo_tempo = true;
										}
	
										esc.setExecucao_formulario( execucao );
									} else {
										addEscalonamento_ciclo_tempo = true;
									}
								}
							} catch (Exception e) {
								//LogPrimum.mensagem("2 OU MAIS EXECU��ES PARA UMA TAREFA NA MESMA DATA. CHECAR ESCALONAMENTO_BO");
							}
	
							if(execucao!=null && execucao.getCd_execucao_formulario()!=null){
								st_escalonamento_usuario = this.verificaSituacaoAtividade(execucao, esc, formulario);
	
								if (execucao.getDt_execucao_formulario() != null) {
									esc.setHr_execucao_efetiva( execucao.getDt_execucao_formulario().toString() );
								}

								if (execucao.getNr_hora_termino() == null) {
									if(execucao.getListaRespostas_execucao_formulario()!=null){
										st_escalonamento_usuario = 1;
									}
								} else {
									esc.setHr_termino_efetivo( execucao.getNr_hora_termino().toString()  );
								}

								esc.setExecucao_formulario(execucao);

							}else{

								Calendar c = Calendar.getInstance(TimeZone.getDefault()); // omit timezone for default tz  
						        c.setTime(new Date()); // your date; omit this line for current date  
						        int offset = c.get(Calendar.DST_OFFSET); 

						        if(offset == 0){
						        	//System.out.println("N�O ESTA EM HORARIO DE VER�O");
						        }else{
						        	//System.out.println("EST� EM HORARIO DE VER�O");
						        }

								if( ( dataAtual.get(Calendar.HOUR_OF_DAY) == (dataEscalonado.get(Calendar.HOUR_OF_DAY) ) )){
									if(  ( dataAtual.get(Calendar.MINUTE) > dataEscalonado.get(Calendar.MINUTE) ) ){
									//ATRASADA
										st_escalonamento_usuario = 3;
									}
								}else if( ( dataAtual.get(Calendar.HOUR_OF_DAY) > (dataEscalonado.get(Calendar.HOUR_OF_DAY) ) ) ){
									st_escalonamento_usuario = 3;
								}
							}

							if ( (periodicidade.getTp_ciclo_tempo() == null) || ((periodicidade.getTp_ciclo_tempo() == 1) && (addEscalonamento_ciclo_tempo) ) ) {
								pesquisa.buscarObjetoCompleto(formulario);
								escalonamento_formulario.setFormulario(formulario);
								esc.setEscalonamento_formulario(escalonamento_formulario);						
								esc.setSt_escalonamento_usuario(st_escalonamento_usuario);
	
								Revisao_programa_qualidade revisao = (Revisao_programa_qualidade) this.getDao().getSession().createCriteria(Revisao_programa_qualidade.class).add(Restrictions.eq("cd_revisao_programa_qualidade", formulario.getCd_revisao_programa_qualidade())).uniqueResult();
								if (revisao.getSt_revisao_programa_qualidade().equals(2)) {
									listaAux.add(esc);							
								}
							}
						}
					} else {
						// TODO
					}
				}
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (List<Escalonamento_usuario>) listaAux;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public List<Execucao_formulario> buscarCronogramaAtividadesPorSituacao( Integer cd_usuario, Date data, Integer cd_unidade ) {

		GregorianCalendar dataInicialDia = (GregorianCalendar) GregorianCalendar.getInstance();
		dataInicialDia.setTime( data );
		dataInicialDia.set(Calendar.HOUR_OF_DAY,0);     
		dataInicialDia.set(Calendar.MINUTE,0);     
		dataInicialDia.set(Calendar.SECOND,0);

		GregorianCalendar dataFinalDia = (GregorianCalendar) GregorianCalendar.getInstance();
		dataFinalDia.setTime( data );
		dataFinalDia.set(Calendar.HOUR_OF_DAY,23);     
		dataFinalDia.set(Calendar.MINUTE,59);     
		dataFinalDia.set(Calendar.SECOND,59);

		/*dataInicialDia.set(Calendar.YEAR,data.getYear()+1900);     
		dataInicialDia.set(Calendar.MONTH,data.getMonth());     
		dataInicialDia.set(Calendar.DATE,data.getDate());
		*/

		//System.out.println( new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataInicialDia.getTime()) );
		//System.out.println( new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataFinalDia.getTime()) );

		List<?> lista = this.getDao().getSession().createCriteria(Escalonamento_usuario.class)
				.add(Restrictions.eq("cd_usuario", cd_usuario))
				.add(Restrictions.isNull("ds_dias_repeticao"))
				.list();

		List<Execucao_formulario> listExecucaoTotal = new ArrayList<>();

		try {
			for (Escalonamento_usuario esc : (List<Escalonamento_usuario>) lista) {

				Escalonamento_formulario escalonamento_formulario = (Escalonamento_formulario) this.getDao().getSession().createCriteria(Escalonamento_formulario.class).add(Restrictions.eq("cd_escalonamento_formulario", esc.getCd_escalonamento_formulario())).add(Restrictions.eq("cd_unidade", cd_unidade)).uniqueResult();
				if( escalonamento_formulario != null ){
					Formulario formulario = (Formulario) this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", escalonamento_formulario.getCd_formulario())).uniqueResult();
	
					if ( (formulario != null) && (formulario.getCd_formulario() != null) ) {
	
						List<Execucao_formulario> listExecucao_formularios = (List<Execucao_formulario>) this.getDao().getSession().createCriteria(Execucao_formulario.class)
								.add(Restrictions.eq("cd_usuario", cd_usuario))
								.add(Restrictions.eq("cd_escalonamento_usuario", esc.getCd_escalonamento_usuario()))
								.add(Restrictions.eq("cd_formulario", formulario.getCd_formulario()))
								.add(Restrictions.ge("dt_execucao_formulario", dataInicialDia.getTime()))
								.add(Restrictions.le("nr_hora_termino", dataFinalDia.getTime()))
								.add(Restrictions.isNotNull("nr_hora_termino"))
								.list();
	
						if( listExecucao_formularios !=null  ){
							if(listExecucao_formularios.size()>0){
								Revisao_programa_qualidade revisao = (Revisao_programa_qualidade) this.getDao().getSession().createCriteria(Revisao_programa_qualidade.class).add(Restrictions.eq("cd_revisao_programa_qualidade", formulario.getCd_revisao_programa_qualidade())).uniqueResult();
								if (revisao.getSt_revisao_programa_qualidade().equals(2)) {
									PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
									for(Execucao_formulario e: listExecucao_formularios){
										pesquisa.buscarObjetoCompleto( e );
									}
									listExecucaoTotal.addAll( listExecucao_formularios );							
								}
							}
						}
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}

		return listExecucaoTotal;
	}

	public Integer verificaSituacaoAtividade(Execucao_formulario execucao, Escalonamento_usuario esc, Formulario form) {

		GregorianCalendar dataAtual = new GregorianCalendar();
		dataAtual.setTime(new Date());
		Integer situacao=0;

		GregorianCalendar horarioInicioColaborador = new GregorianCalendar();
		horarioInicioColaborador.setTime( execucao.getDt_execucao_formulario() );

		GregorianCalendar horarioInicioAtividade = new GregorianCalendar();
		horarioInicioAtividade.setTime(esc.getHr_execucao());

		if( (execucao.getDt_execucao_formulario().compareTo(dataAtual.getTime()) < 0  && execucao.getNr_hora_termino()==null) && ( (dataAtual.get(dataAtual.DAY_OF_YEAR) == (horarioInicioColaborador.get(horarioInicioColaborador.DAY_OF_YEAR)))) ){
			//INICIADA
			situacao=1;
			esc.setExecucao_formulario(execucao);
		//}else if(execucao.getDt_execucao_formulario().compareTo(dataAtual.getTime()) < 0 && execucao.getNr_hora_termino()!=null){
		}else if(execucao.getDt_execucao_formulario().compareTo(dataAtual.getTime()) < 0 && execucao.getNr_hora_termino()!=null){
			/*HORA INICIADA E TERMINADA DA ATIVIDADE PELO COLABORADOR*/
			GregorianCalendar horarioTerminoColaborador = new GregorianCalendar();
			horarioTerminoColaborador.setTime( execucao.getNr_hora_termino() );
			/**********************/

			/*HORA REAL DE INICIO E TERMINO DA ATIVIDADE */
			GregorianCalendar horarioTerminoAtividade = new GregorianCalendar();
			horarioTerminoAtividade.setTime(esc.getHr_execucao());
			if(form.getNr_tempoestimado()!=null){
				horarioTerminoAtividade.add(Calendar.MINUTE, form.getNr_tempoestimado());
			}
			/**********************/
			
			/*
			//System.out.println(""+ horarioTerminoColaborador.get(Calendar.HOUR_OF_DAY));
			//System.out.println(""+ horarioTerminoColaborador.get(Calendar.MINUTE));

			//System.out.println("************");

			//System.out.println(""+ dataAtual.get(Calendar.HOUR_OF_DAY));
			//System.out.println(""+ dataAtual.get(Calendar.MINUTE));

			//System.out.println("************");

			//System.out.println(""+ horarioInicioAtividade.get(Calendar.HOUR_OF_DAY));
			//System.out.println(""+ horarioInicioAtividade.get(Calendar.MINUTE));
			*/
			
			
			Integer horaInicioAtividade = horarioInicioAtividade.get(Calendar.HOUR_OF_DAY) * 60 * 60;
			horaInicioAtividade = horaInicioAtividade + horarioInicioAtividade.get(Calendar.MINUTE) * 60;
			Integer horaTerminoAtividade = horarioTerminoAtividade.get(Calendar.HOUR_OF_DAY) * 60 * 60;
			horaTerminoAtividade = horaTerminoAtividade + horarioTerminoAtividade.get(Calendar.MINUTE) * 60;
			
			
			Integer horaInicioEfetivoAtividade = horarioInicioColaborador.get(Calendar.HOUR_OF_DAY) * 60 * 60;
			horaInicioEfetivoAtividade = horaInicioEfetivoAtividade + horarioInicioColaborador.get(Calendar.MINUTE) * 60;
			Integer horaTerminoEfetivoAtividade = horarioTerminoColaborador.get(Calendar.HOUR_OF_DAY) * 60 * 60;
			horaTerminoEfetivoAtividade = horaTerminoEfetivoAtividade + horarioTerminoColaborador.get(Calendar.MINUTE) * 60;
			
			

			if( ( horarioTerminoColaborador.get(Calendar.HOUR_OF_DAY) == horarioInicioAtividade.get(Calendar.HOUR_OF_DAY))) {
				if(  ( horarioTerminoColaborador.get(Calendar.MINUTE) >= horarioInicioAtividade.get(Calendar.MINUTE) ) ){

					//15 - periodo de tolerancia
					if( ( horarioTerminoColaborador.get(Calendar.MINUTE) - horarioInicioAtividade.get(Calendar.MINUTE) ) >= 15 ){
						//CONCLU�DA ATRASADA
						situacao=4;
					}else{
						situacao=2;
					}

				}else if( ( horarioTerminoColaborador.get(Calendar.MINUTE) == horarioInicioAtividade.get(Calendar.MINUTE) )  ){
					situacao=2;
				}else{
					situacao=5;
				}
			}else if( (( horarioTerminoColaborador.get(Calendar.HOUR_OF_DAY) > horarioInicioAtividade.get(Calendar.HOUR_OF_DAY))) && ( horarioTerminoColaborador.get(Calendar.DAY_OF_YEAR) == new GregorianCalendar().get(Calendar.DAY_OF_YEAR)) ){
				//SE PASSOU A HORA MAS NAO PASSOU DE 15 MINUTOS (EXEMPLO: ATIVIDADE AS 14:55 EXECUTADA 15:02)

				if ( (horarioTerminoColaborador.get(Calendar.HOUR_OF_DAY) - horarioInicioAtividade.get(Calendar.HOUR_OF_DAY)) > 1 ) {
					situacao=4;
				} else if( ((60 - horarioTerminoColaborador.get(Calendar.MINUTE) ) + horarioInicioAtividade.get(Calendar.MINUTE)) < 15 ){ 
					situacao=2;
				}else{
					situacao=4;
				}
			}else if( ( horarioTerminoColaborador.get(Calendar.DAY_OF_YEAR) < new GregorianCalendar().get(Calendar.DAY_OF_YEAR)) ){
				situacao=0;				
			}else{
				situacao=5;
			}
		}else if(execucao.getDt_execucao_formulario().compareTo(dataAtual.getTime()) < 0 && execucao.getNr_hora_termino() == null){

			if( horarioInicioColaborador.get(GregorianCalendar.DAY_OF_YEAR) < new GregorianCalendar().get(GregorianCalendar.DAY_OF_YEAR) ) {

				if ( horarioInicioAtividade.get(GregorianCalendar.HOUR_OF_DAY) <= new GregorianCalendar().get(GregorianCalendar.HOUR_OF_DAY) ) {
					if ( horarioInicioAtividade.get(GregorianCalendar.MINUTE) > new GregorianCalendar().get(GregorianCalendar.MINUTE) ) {
						situacao = 3;
					} else {
						situacao = 0;
					}
				} else {
					situacao = 3;
				}
			}else if(horarioInicioColaborador.get(GregorianCalendar.DAY_OF_YEAR) == new GregorianCalendar().get(GregorianCalendar.DAY_OF_YEAR)){
				situacao=0;
			}
		}else {
			situacao=0;
		}

		return situacao;
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	public boolean validarEscalonamentoUsuario(Escalonamento_formulario escalonamento_formulario, Escalonamento_usuario escalonamento_usuario) {

		boolean retorno = true;
		if(escalonamento_formulario!=null && escalonamento_usuario!=null){
			
			this.setDao(new GenericDAO());
			Criteria c = this.getDao().getSession().createCriteria(Escalonamento_usuario.class);
	
			String[] s = null;
	
			if (!escalonamento_usuario.getDs_dias_repeticao().equals("")) {
				s = escalonamento_usuario.getDs_dias_repeticao().split("|");
			} else {
				c.add(Restrictions.like("ds_dias_repeticao", escalonamento_usuario.getDs_dias_repeticao()));
			}
	
			if(s!=null){
	
				if (s.length > 1) {
	
					Criterion a 	= null;
					Criterion b 	= null;
					Criterion aux 	= null;
					Criterion aux2 	= null;
					for (int i = 0; i < s.length; i++) {
						String d = s[i];
	
						if (!d.equals("|") && !d.equals("")) {
	
							d = "%" + s[i] + "%";
							if (a == null) {
								a = Restrictions.like("ds_dias_repeticao", d);
							} else if (b == null) {
								b = Restrictions.like("ds_dias_repeticao", d);
							} else if ( (aux == null) && (aux2 == null) ) {
	
								aux 	= Restrictions.or(a, b);
								a 		= Restrictions.like("ds_dias_repeticao", d);
								aux2 	= Restrictions.or(a, aux);
								a 		= null;
								b 		= null;
								aux 	= null;
							}
	
							if( (aux2 != null) && (a != null) && (b == null) ) {
								b = aux2;
								aux2 = null;
							}
						}
					}
	
					if ( (a != null) && (b == null)) {
						c.add(a);
					} else if ( (a != null) && (b != null) && (aux == null) ) {
						aux = Restrictions.or(a, b);
						c.add(aux);
					} else if ( aux2 != null ) {
						c.add(aux2);
					}
	
				} else if (s.length > 0) {
	
					c.add(Restrictions.like("ds_dias_repeticao", s[0]));
				}
	
				c.add(Restrictions.eq("cd_usuario", escalonamento_usuario.getCd_usuario()));
	
				GregorianCalendar horarioPrevisaoInicio 	= new GregorianCalendar();
				GregorianCalendar horarioPrevisaoTermino	= new GregorianCalendar();
				GregorianCalendar horarioEscalonar			= new GregorianCalendar();
				GregorianCalendar horarioComparar			= new GregorianCalendar();
				
				horarioEscalonar.setTime(escalonamento_usuario.getHr_execucao());
				//System.out.println(horarioEscalonar.get(horarioEscalonar.HOUR_OF_DAY));
				horarioPrevisaoInicio.setTime(new java.util.Date(0));
				horarioPrevisaoTermino.setTime(new java.util.Date(86400));
				
				horarioPrevisaoInicio.set(	horarioPrevisaoInicio.DAY_OF_MONTH, 	horarioEscalonar.get(horarioEscalonar.DAY_OF_MONTH));
				horarioPrevisaoInicio.set(	horarioPrevisaoInicio.MONTH, 			horarioEscalonar.get(horarioEscalonar.MONTH));
				horarioPrevisaoInicio.set(	horarioPrevisaoInicio.YEAR, 			horarioEscalonar.get(horarioEscalonar.YEAR));	
				horarioPrevisaoInicio.set(	horarioPrevisaoInicio.HOUR_OF_DAY, 		horarioEscalonar.get(horarioEscalonar.HOUR_OF_DAY));
				horarioPrevisaoInicio.set(	horarioPrevisaoInicio.MINUTE, 			horarioEscalonar.get(horarioEscalonar.MINUTE));
	
				Integer tempoAcrescimo = 0;
				if (escalonamento_formulario.getFormulario().getNr_tempoestimado() != null) {
					tempoAcrescimo = escalonamento_formulario.getFormulario().getNr_tempoestimado();
				}
	
				horarioPrevisaoTermino.set(	horarioPrevisaoTermino.DAY_OF_MONTH, 	horarioEscalonar.get(horarioEscalonar.DAY_OF_MONTH));
				horarioPrevisaoTermino.set(	horarioPrevisaoTermino.MONTH, 			horarioEscalonar.get(horarioEscalonar.MONTH));
				horarioPrevisaoTermino.set(	horarioPrevisaoTermino.YEAR, 			horarioEscalonar.get(horarioEscalonar.YEAR));	
				horarioPrevisaoTermino.set(	horarioPrevisaoTermino.HOUR_OF_DAY, 	horarioEscalonar.get(horarioEscalonar.HOUR_OF_DAY));
				horarioPrevisaoTermino.set(	horarioPrevisaoTermino.MINUTE, 			(horarioEscalonar.get(horarioEscalonar.MINUTE) + tempoAcrescimo));
	
				//c.add(Restrictions.between("hr_execucao", horarioPrevisaoInicio.getTime(), horarioPrevisaoTermino.getTime()));
	
				List<Escalonamento_usuario> listaEscalonamento_usuarios = c.list();
				
				//System.out.println(listaEscalonamento_usuarios.size());
	
				if ( (listaEscalonamento_usuarios != null) && (listaEscalonamento_usuarios.size() > 0) ) {
										
					for(Escalonamento_usuario esc : listaEscalonamento_usuarios ){
						horarioComparar.setTime(esc.getHr_execucao());
						horarioComparar.set(	horarioComparar.MINUTE, 			(horarioComparar.get(horarioComparar.MINUTE) + tempoAcrescimo));
						
						if( horarioPrevisaoInicio.getTime().after( esc.getHr_execucao() ) && 
								horarioPrevisaoInicio.getTime().before( horarioComparar.getTime() )	){
							retorno = false;
							break;
						}
						
						GregorianCalendar horarioCompararAntes			= new GregorianCalendar();
						horarioCompararAntes.setTime(horarioPrevisaoInicio.getTime());
						horarioCompararAntes.set(	horarioCompararAntes.MINUTE, 			(horarioCompararAntes.get(horarioCompararAntes.MINUTE) + tempoAcrescimo));
						
						if( horarioCompararAntes.getTime().after( esc.getHr_execucao() ) && 
								horarioCompararAntes.getTime().before( horarioComparar.getTime() )	){
							retorno = false;
							break;
						}
					}
					
				}

				if(escalonamento_formulario.getListaEscalonamento_usuario().size() > 0){
					for(Escalonamento_usuario esc : escalonamento_formulario.getListaEscalonamento_usuario() ){
						horarioComparar.setTime(esc.getHr_execucao());
						horarioComparar.set(	horarioComparar.MINUTE, 			(horarioComparar.get(horarioComparar.MINUTE) + tempoAcrescimo));

						if( horarioPrevisaoInicio.getTime().after( esc.getHr_execucao() ) && 
								horarioPrevisaoInicio.getTime().before( horarioComparar.getTime() )	){
							retorno = false;
							break;
						}
						
						GregorianCalendar horarioCompararAntes			= new GregorianCalendar();
						horarioCompararAntes.setTime(horarioPrevisaoInicio.getTime());
						horarioCompararAntes.set(	horarioCompararAntes.MINUTE, 			(horarioCompararAntes.get(horarioCompararAntes.MINUTE) + tempoAcrescimo));
						
						if( horarioCompararAntes.getTime().after( esc.getHr_execucao() ) && 
								horarioCompararAntes.getTime().before( horarioComparar.getTime() )	){
							retorno = false;
							break;
						}
					}
				}
				
				if(retorno){
					c.add(Restrictions.between("hr_execucao", horarioPrevisaoInicio.getTime(), horarioPrevisaoTermino.getTime()));
					listaEscalonamento_usuarios = c.list();
					if ( (listaEscalonamento_usuarios != null) && (listaEscalonamento_usuarios.size() > 0) ) {
						retorno = false;
					}
				}
				
			}
		}else{
			retorno = false;
		}
		return retorno;
	}

	@SuppressWarnings("unchecked")
	public List<?> montarPainelAcompanhamento( Integer cd_unidade ) {

		this.setDao(new GenericDAO());
		List<?> lista = this.getDao().getSession().createCriteria(Usuario.class).addOrder(Order.asc("cd_usuario")).list();
		List<Object> listaRetorno = new ArrayList<>();

		for (Usuario usuario : (List<Usuario>) lista) {

			List<Escalonamento_usuario> listaAtividades = this.buscarCronogramaAtividades(usuario.getCd_usuario(), 0, cd_unidade);
			List<Escalonamento_usuario> listaAtividadesMonit = this.buscarCronogramaAtividades(usuario.getCd_usuario(), 1, cd_unidade);
			List<Escalonamento_usuario> listaAtividadesDocumental = this.buscarCronogramaAtividades(usuario.getCd_usuario(), 2, cd_unidade);
			
			List<Object> listaAux = new ArrayList<>();
			
			if (listaAtividades != null) {
				if (listaAtividadesMonit != null)
					listaAtividades.addAll(listaAtividadesMonit);
				
				if (listaAtividadesDocumental != null)
					listaAtividades.addAll(listaAtividadesDocumental);
			}

			listaAux.add(usuario);
			listaAux.add(listaAtividades);

			listaRetorno.add(listaAux);
		}

		return listaRetorno;
	}

	@SuppressWarnings("unchecked")
	public List<List<?>> getExecucoesPorSituacao(Date data, Integer cd_unidade) {

		this.setDao(new GenericDAO());
		List<Usuario> lista = (List<Usuario>)this.getDao().getSession().createCriteria(Usuario.class).addOrder(Order.asc("cd_usuario")).list();
		List<Execucao_formulario> listaExecucoesTotal = new ArrayList<>();
		List<Usuario> listaUsuarios = new ArrayList<>();
		List<List<?>> listaTotal = new ArrayList<>();
		List<List<?>> listaAux = new ArrayList<>();
		
		for (Usuario usuario : lista) {

			List<Execucao_formulario> listaExecucoes = (List<Execucao_formulario>)this.buscarCronogramaAtividadesPorSituacao( usuario.getCd_usuario() , data, cd_unidade );
			if(listaExecucoes!=null){
				if(listaExecucoes.size()>0){
					
					listaUsuarios = new ArrayList<>();
					listaExecucoesTotal = new ArrayList<>();
					listaAux = new ArrayList<>();
					
					listaUsuarios.add( usuario );
					listaExecucoesTotal.addAll( listaExecucoes );
					listaAux.add( listaUsuarios );
					listaAux.add( listaExecucoesTotal );
					listaTotal.add( listaAux );
				}
			}
		}

		return listaTotal;
	}
	
	@SuppressWarnings("unchecked")
	public List<Escalonamento_usuario> listarFormularioEscalonadosPorSituacao(Integer cd_usuario, Integer cd_unidade) {
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Formulario> listaFormulariosSituacao = new ArrayList<>();
		List<Escalonamento_usuario> listaFormulariosUsuario = new ArrayList<>();
		List<Escalonamento_formulario> listaEscalonamentosSituacao = new ArrayList<>();
		
		List<Ocorrencia_periodicidade> listaPeriodicidadeSituacao = (List<Ocorrencia_periodicidade>) this.getDao().getSession().createCriteria(Ocorrencia_periodicidade.class)
				.addOrder(Order.desc("cd_ocorrencia_periodicidade"))
				.list();
		List<Ocorrencia_periodicidade> listaPeriodicidadeSituacaoAux = new ArrayList<>(); 
		for(Ocorrencia_periodicidade p: listaPeriodicidadeSituacao){
			if(new Date().before(p.getDt_expirar()) && new Date().after(p.getDt_ocorrencia_periodicidade())){
				listaPeriodicidadeSituacaoAux.add(p);
			}
		}

		listaPeriodicidadeSituacao = new ArrayList<>();
		listaPeriodicidadeSituacao.addAll(listaPeriodicidadeSituacaoAux);

		for(Ocorrencia_periodicidade p: listaPeriodicidadeSituacao){
			List<Formulario> listaFormulariosSituacaoAux = (List<Formulario>) this.getDao().getSession().createCriteria(Formulario.class)
					.add(Restrictions.eq("cd_periodicidade", p.getCd_periodicidade()))
					.addOrder(Order.desc("cd_formulario"))				
					.list();

			listaFormulariosSituacao.addAll(listaFormulariosSituacaoAux);
		}

		for(Formulario f: listaFormulariosSituacao){
				List<Escalonamento_formulario> listaEscAux = (List<Escalonamento_formulario>) this.getDao().getSession().createCriteria(Escalonamento_formulario.class).add(Restrictions.eq("cd_formulario", f.getCd_formulario())).list();
				listaEscalonamentosSituacao.addAll(listaEscAux);
		}

		for(Escalonamento_formulario esc:listaEscalonamentosSituacao){
			pesquisa.buscarObjetoCompleto(esc);
		}

		for(Escalonamento_formulario esc:listaEscalonamentosSituacao){
			if( esc.getCd_unidade().equals(cd_unidade) ){
				for(Escalonamento_usuario esc_usuario: esc.getListaEscalonamento_usuario()){
					if(esc_usuario.getCd_usuario().equals(cd_usuario) ){
						esc_usuario.setEscalonamento_formulario(esc);
						listaFormulariosUsuario.add(esc_usuario);
					}
				}
			}
		}
		return listaFormulariosUsuario;
	}
	
	private void getSQLEscalonamento_validar_periodo(Periodicidade periodicidade) {

		String intervalo = "";
		
		if (this.getDao() == null) {
			this.setDao(new GenericDAO());
		}
		
		Escala_tempo escala_tempo = (Escala_tempo) this.getDao().getSession().createCriteria(Escala_tempo.class).add(Restrictions.eq("cd_escala_tempo", periodicidade.getCd_escala_tempo())).uniqueResult();
		
		intervalo = (periodicidade.getVl_tempo_periodicidade() * escala_tempo.getVl_equivalencia_minuto()) + " minutes" ;
		String sql = "select " +
				"escalonamento_usuario.* " +
				"from escalonamento_usuario " +
				"inner join  escalonamento_formulario on " +
				"escalonamento_formulario.cd_escalonamento_formulario = escalonamento_usuario.cd_escalonamento_formulario " +
				"inner join formulario on " +
				"formulario.cd_formulario = escalonamento_formulario.cd_formulario  " +
				"inner join periodicidade on " +
				"periodicidade.cd_periodicidade = formulario.cd_periodicidade and " +
				"periodicidade.tp_ciclo_tempo = 1  " +
				"inner join execucao_formulario on " +
				"execucao_formulario.cd_escalonamento_usuario = escalonamento_usuario.cd_escalonamento_usuario and " +
				"(  " +
				"( select CASE     WHEN " +
				"(SELECT EXTRACT(DOY FROM execucao_formulario.dt_execucao_formulario)) = " +
				"(SELECT EXTRACT(DOY FROM now())) THEN true     ELSE false END as \"Igual\" ) is true  or ( " +
				"select CASE     WHEN (SELECT EXTRACT(DOY FROM         " +
				"execucao_formulario.dt_execucao_formulario + interval '" + intervalo + "'         )) " +
				"<= (SELECT EXTRACT(DOY FROM now())) THEN true     ELSE false END as \"Igual\" ) is true  )  and " +
				"execucao_formulario.cd_execucao_formulario = 	" +
				"(select " +
				"execucao_formulario_aux.cd_execucao_formulario from " +
				"execucao_formulario execucao_formulario_aux " +
				"where execucao_formulario_aux.cd_escalonamento_usuario = escalonamento_usuario.cd_escalonamento_usuario " +
				"order by " +
				"execucao_formulario_aux.dt_execucao_formulario desc " +
				"limit 1)";
	}

	@SuppressWarnings("unchecked")
	public List<Escalonamento_usuario> getEscalonamentoUsuarioPorUsuario(Integer cd_usuario){
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();

		List<Escalonamento_usuario> listaEscUsuFinal = new ArrayList<>(); 
		List<Escalonamento_usuario> listaEscUsu = ( List<Escalonamento_usuario> ) this.getDao().getSession().createCriteria(Escalonamento_usuario.class).
				add(Restrictions.eq("cd_usuario", cd_usuario))
				.addOrder(Order.asc("hr_execucao")).list();

		for(Escalonamento_usuario esc: listaEscUsu ){
			Escalonamento_formulario escForm = ( Escalonamento_formulario ) this.getDao().getSession().createCriteria(Escalonamento_formulario.class)
					.add(Restrictions.eq("cd_escalonamento_formulario", esc.getCd_escalonamento_formulario())).uniqueResult();
			//System.out.println( new SimpleDateFormat("dd/MM/yyyy").format(escForm.getDt_inicio_vigencia()) + " <> "+ new SimpleDateFormat("dd/MM/yyyy").format(new Date())  +" <> "+ new SimpleDateFormat("dd/MM/yyyy").format(escForm.getDt_termino_vigencia()));
			if( escForm.getDt_inicio_vigencia().before(new Date()) && 
					escForm.getDt_termino_vigencia().after(new Date()) && esc.getHr_execucao() != null){
				pesquisa.buscarObjetoCompleto( escForm );
				esc.setEscalonamento_formulario( escForm );
				listaEscUsuFinal.add( esc );

			}
		}

		return listaEscUsuFinal;
	}

	@SuppressWarnings("unchecked")
	public Boolean setEscalonamentoFormularioInativo( List<Escalonamento_usuario> listaEscUsu ){

		this.setDao(new GenericDAO());
		CrudGenericBO crud = new CrudGenericBO();
		for( Escalonamento_usuario escUsu : listaEscUsu ){

			Calendar calendar = Calendar.getInstance();    
		    calendar.set(Calendar.HOUR_OF_DAY, 0);  
			calendar.set(Calendar.MINUTE, 0);  
			calendar.set(Calendar.SECOND, 0);  
			calendar.set(Calendar.MILLISECOND, 0);  

			if( escUsu.getDt_termino_vigencia() == null ){
				escUsu.setDt_termino_vigencia( calendar.getTime() );
			}

			try {
				crud.atualizar( escUsu );
			} catch (Exception e) {
				return false;
			}
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	public Boolean getEscalonamentoFormulario(List<Integer> lista, Date dt_inicio, Date dt_termino, Integer cd_usuario_de, Integer cd_usuario_para){

		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		String strSQL = "SELECT * from escalonamento_formulario where cd_escalonamento_formulario in (";
		for(Integer i = 0 ; i < lista.size() ; i++ ){
			strSQL += lista.get(i);
			if(i < (lista.size() - 1 )){
				strSQL += ", ";
			}
		}

		strSQL += ")";
		CrudGenericBO crud = new CrudGenericBO();
		List<Escalonamento_formulario> listaEscalonamentos = (List<Escalonamento_formulario>) this.getDao().getSession().createSQLQuery( strSQL ).setResultTransformer(Transformers.aliasToBean(Escalonamento_formulario.class)).list();
		for( Escalonamento_formulario escForm : listaEscalonamentos ){
			pesquisa.buscarObjetoCompleto( escForm );

			Escalonamento_formulario escFormAux = new Escalonamento_formulario(); 

			escFormAux.setCd_formulario(escForm.getCd_formulario());
			escFormAux.setCd_sub_formulario(escForm.getCd_sub_formulario());

			//escForm.setCd_escalonamento_formulario(null);
			escFormAux.setDt_inicio_vigencia( dt_inicio );
			escFormAux.setDt_termino_vigencia( dt_termino );

			List<Escalonamento_usuario> listaAux = new ArrayList<Escalonamento_usuario>();
			for( Escalonamento_usuario escUsu : escForm.getListaEscalonamento_usuario() ){
				if( escUsu.getCd_usuario().equals( cd_usuario_de ) ){
					Escalonamento_usuario escAux = new Escalonamento_usuario();

					escAux.setCd_escalonamento_usuario(null);
					escAux.setCd_escalonamento_formulario(null);

					escAux.setHr_execucao(escUsu.getHr_execucao());
					escAux.setDs_dias_repeticao(escUsu.getDs_dias_repeticao());

					escAux.setCd_usuario(cd_usuario_para);
					listaAux.add(escAux);
				}
			}

			escFormAux.setListaEscalonamento_usuario(listaAux);

			try {
				crud.salvarObjetoCompleto( escFormAux );
			} catch (Exception e) {
				return false;
			}
		}

		//System.out.println( listaEscalonamentos );
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Escalonamento_formulario> getEscalonamentoFormularioPorUsuario(Integer cd_usuario){

		this.setDao(new GenericDAO());

		String strSQL = "SELECT * from escalonamento_formulario where cd_escalonamento_formulario in" +
		"( SELECT cd_escalonamento_formulario" +
		" FROM escalonamento_usuario where cd_usuario = "+ cd_usuario.toString() +")" + 
		" AND (now() > dt_inicio_vigencia) " +
		" AND (now() < dt_TERMINO_vigencia)";

		List<Escalonamento_formulario> listaEscalonamentos = (List<Escalonamento_formulario>) this.getDao().getSession().createSQLQuery( strSQL ).setResultTransformer(Transformers.aliasToBean(Escalonamento_formulario.class)).list();

		return listaEscalonamentos;
	}

	private boolean validarData(GregorianCalendar dataExecucao,GregorianCalendar dataPrevistaCiclo_tempo) {

		boolean retorno = false;

		if (dataExecucao.get(dataExecucao.YEAR) == dataPrevistaCiclo_tempo.get(dataExecucao.YEAR)) {

			if (dataExecucao.get(dataExecucao.DAY_OF_YEAR) > dataPrevistaCiclo_tempo.get(dataExecucao.DAY_OF_YEAR)) {
				retorno = false;
			} else {
				retorno = true;
			}
		} else if (dataExecucao.get(dataExecucao.YEAR) < dataPrevistaCiclo_tempo.get(dataExecucao.YEAR)) {

			if (dataExecucao.get(dataExecucao.DAY_OF_YEAR) > dataPrevistaCiclo_tempo.get(dataExecucao.DAY_OF_YEAR)) {
				retorno = true;
			} else {
				retorno = false;
			}
		} else if (dataExecucao.get(dataExecucao.YEAR) > dataPrevistaCiclo_tempo.get(dataExecucao.YEAR)) {

			if (dataExecucao.get(dataExecucao.DAY_OF_YEAR) < dataPrevistaCiclo_tempo.get(dataExecucao.DAY_OF_YEAR)) {
				retorno = true;
			} else {
				retorno = false;
			}
		}

		return retorno;
	}

	@SuppressWarnings("unchecked")
	public List<Formulario> buscarAtividadesCheckList(Integer cd_usuario) {

		this.setDao(new GenericDAO());
		List<?> lista = this.getDao().getSession().createCriteria(Formulario.class).list();
		return ((List<Formulario>) lista);
	}

	public List<Execucao_formulario> buscarAtividadesIniciadas(Integer cd_usuario) {

		this.setDao(new GenericDAO());
		List<?> lista = this.getDao().getSession().createCriteria(Execucao_formulario.class)
						.add(Restrictions.isNull("nr_hora_termino"))
						.list();

		PesquisaPadraoBO pesq = new PesquisaPadraoBO();
		pesq.setDao(this.getDao());
		for (Execucao_formulario exec : (List<Execucao_formulario>) lista) {
			pesq.buscarObjetoCompleto(exec);
		}

		return ((List<Execucao_formulario>) lista);
	}
	
	public List<Formulario> buscarTodosChecklist() {

		this.setDao(new GenericDAO());
		List<?> lista = this.getDao().getSession().createCriteria(Formulario.class).list();
		
		PesquisaPadraoBO pesq = new PesquisaPadraoBO();
		pesq.setDao(this.getDao());		
		for (Formulario frm : (List<Formulario>) lista) {
			pesq.buscarObjetoCompleto(frm);
		}
		return ((List<Formulario>) lista);
	}

	//FUN��O DO JEFF
	@SuppressWarnings({ "unchecked", "static-access" })
	public List<Object> buscarCronogramaAtividadesMobile(Integer cd_usuario, Integer tp_formulario) {

		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();

		GregorianCalendar dataAtual = (GregorianCalendar) GregorianCalendar.getInstance();
		//dataAtual.setTime(new Date());
		Integer diaSemana = dataAtual.get(GregorianCalendar.DAY_OF_WEEK);

		Criterion a 	= Restrictions.isNull("dt_termino_vigencia");
		Criterion b 	= Restrictions.ge("dt_termino_vigencia", dataAtual.getTime());
		Criterion critOr 	= Restrictions.or(a, b);
		
		this.setDao(new GenericDAO());
		List<?> lista = this.getDao().getSession().createCriteria(Escalonamento_usuario.class)
																	.add(Restrictions.eq("cd_usuario", cd_usuario))
																	.add(Restrictions.like("ds_dias_repeticao", "%" + diaSemana.toString() + "%"))
																	.add(critOr)
																	.addOrder(Order.asc("hr_execucao"))
																	.list();

		List<Escalonamento_usuario> listaAux = new ArrayList<>();

		try {
			for (Escalonamento_usuario esc : (List<Escalonamento_usuario>) lista) {

				Escalonamento_formulario escalonamento_formulario = (Escalonamento_formulario) this.getDao().getSession().createCriteria(Escalonamento_formulario.class).add(Restrictions.eq("cd_escalonamento_formulario", esc.getCd_escalonamento_formulario())).uniqueResult();
				Formulario formulario = (Formulario) this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", escalonamento_formulario.getCd_formulario())).uniqueResult();
				Periodicidade periodicidade = null;

				System.out.println("escalonamento_formulario: " + escalonamento_formulario.getCd_escalonamento_formulario());
				System.out.println("escalonamento_usuario: " + esc.getCd_escalonamento_usuario());
				if ( (formulario != null) && (formulario.getCd_formulario() != null) ) {

					if (formulario != null) {
						periodicidade = (Periodicidade) this.getDao().getSession().createCriteria(Periodicidade.class).add(Restrictions.eq("cd_periodicidade", formulario.getCd_periodicidade())).uniqueResult();
					}
					/******************/

					GregorianCalendar dataEscalonado = new GregorianCalendar();
					GregorianCalendar dataPrevista = new GregorianCalendar();

					GregorianCalendar dataInicialDia = (GregorianCalendar) GregorianCalendar.getInstance();
					dataInicialDia.set(Calendar.HOUR_OF_DAY,0);     
					dataInicialDia.set(Calendar.MINUTE,0);     
					dataInicialDia.set(Calendar.SECOND,0);

					GregorianCalendar dataFinalDia = (GregorianCalendar) GregorianCalendar.getInstance();
					dataFinalDia.set(Calendar.HOUR_OF_DAY,23);     
					dataFinalDia.set(Calendar.MINUTE,59);     
					dataFinalDia.set(Calendar.SECOND,59);  

					System.out.println(""+ dataInicialDia.get(Calendar.YEAR) +"-"+ dataInicialDia.get(Calendar.MONTH)
								+"-"+ dataInicialDia.get(Calendar.DAY_OF_MONTH) 
								+"  "+ dataInicialDia.get(Calendar.HOUR_OF_DAY)
								+":"+ dataInicialDia.get(Calendar.MINUTE)
								+":"+ dataInicialDia.get(Calendar.SECOND));

					dataEscalonado.setTime(esc.getHr_execucao());
					dataPrevista.setTime(new Date());
					dataPrevista.set(dataPrevista.HOUR, dataEscalonado.get(dataEscalonado.HOUR));
					dataPrevista.set(dataPrevista.MINUTE, dataEscalonado.get(dataEscalonado.MINUTE));

					//SITUA��O 0 --> PROGRAMADA
					Integer st_escalonamento_usuario = 0;

					System.out.println("cd_formulario" + formulario.getCd_formulario());
					System.out.println("cd_usuario" + cd_usuario);
					System.out.println("cd_escalonamento_usuario" + esc.getCd_escalonamento_usuario());
					System.out.println("dt_execucao_formulario" + dataInicialDia.getTime());

					Boolean vigenciaPermitida=false;
					if( escalonamento_formulario.getDt_inicio_vigencia() == null ) { vigenciaPermitida=true;}
					if(escalonamento_formulario.getDt_inicio_vigencia() != null ){

						//come�a hoje
						if(escalonamento_formulario.getDt_inicio_vigencia().after(dataInicialDia.getTime()) && escalonamento_formulario.getDt_inicio_vigencia().before(dataFinalDia.getTime())){
							//termina hoje
							if(escalonamento_formulario.getDt_termino_vigencia().before(dataFinalDia.getTime())){
								vigenciaPermitida=true;
							}
							//termina mais alem
							if(escalonamento_formulario.getDt_termino_vigencia().after(dataFinalDia.getTime())){
								vigenciaPermitida=true;
							}
						}

						//j� come�ou
						if(escalonamento_formulario.getDt_inicio_vigencia().before(dataInicialDia.getTime()) ){
							if(escalonamento_formulario.getDt_termino_vigencia().after(dataInicialDia.getTime())){
								vigenciaPermitida=true;
							}
						}
					}

					if(vigenciaPermitida){
						Execucao_formulario execucao = null;
						boolean addEscalonamento_ciclo_tempo = false;

						try {

							if (periodicidade.getTp_ciclo_tempo() == null) {
								execucao = (Execucao_formulario) this.getDao().getSession().createCriteria(Execucao_formulario.class)
										.add(Restrictions.eq("cd_formulario", formulario.getCd_formulario()))
										.add(Restrictions.eq("cd_usuario", cd_usuario))
										.add(Restrictions.eq("cd_escalonamento_usuario", esc.getCd_escalonamento_usuario()))
										.add(Restrictions.ge("dt_execucao_formulario", dataInicialDia.getTime())).uniqueResult();
							} else {

								execucao = (Execucao_formulario) this.getDao().getSession().createCriteria(Execucao_formulario.class)
										.add(Restrictions.eq("cd_formulario", formulario.getCd_formulario()))
										.add(Restrictions.eq("cd_usuario", cd_usuario))
										.add(Restrictions.eq("cd_escalonamento_usuario", esc.getCd_escalonamento_usuario()))
										.addOrder(Order.desc("dt_execucao_formulario"))
										.setMaxResults(1)
										.uniqueResult();

								if ( (execucao != null) && (execucao.getCd_execucao_formulario()!=null) ) {

									Escala_tempo escala_tempo = (Escala_tempo) this.getDao().getSession().createCriteria(Escala_tempo.class).add(Restrictions.eq("cd_escala_tempo", periodicidade.getCd_escala_tempo())).uniqueResult();
									GregorianCalendar dataPrevistaCiclo_tempo = new GregorianCalendar();
									dataPrevistaCiclo_tempo.add(dataPrevistaCiclo_tempo.MINUTE, -(periodicidade.getVl_tempo_periodicidade() * escala_tempo.getVl_equivalencia_minuto()));

									GregorianCalendar dataExecucao = new GregorianCalendar();
									dataExecucao.setTime(execucao.getDt_execucao_formulario());

									if (execucao.getDt_execucao_formulario().compareTo(dataPrevistaCiclo_tempo.getTime()) < 1) {
										if(dataExecucao.get(dataExecucao.DAY_OF_YEAR) < dataPrevistaCiclo_tempo.get(dataPrevistaCiclo_tempo.DAY_OF_YEAR)){
											execucao = null;
										}

										addEscalonamento_ciclo_tempo = true;
									} else if ( (dataPrevistaCiclo_tempo.get(dataPrevistaCiclo_tempo.DAY_OF_YEAR) <= dataAtual.get(dataAtual.DAY_OF_YEAR)) && 
											(dataPrevistaCiclo_tempo.get(dataPrevistaCiclo_tempo.DAY_OF_YEAR) >= dataExecucao.get(dataAtual.DAY_OF_YEAR)) ) {

										addEscalonamento_ciclo_tempo = true;
									}

									esc.setExecucao_formulario( execucao );

								} else {
									addEscalonamento_ciclo_tempo = true;
								}
							}
						} catch (Exception e) {
							//LogPrimum.mensagem("2 OU MAIS EXECU��ES PARA UMA TAREFA NA MESMA DATA. CHECAR ESCALONAMENTO_BO");
						}

						if(execucao!=null && execucao.getCd_execucao_formulario()!=null){
							st_escalonamento_usuario = this.verificaSituacaoAtividade(execucao, esc, formulario);

							if (execucao.getDt_execucao_formulario() != null) {
								esc.setHr_execucao_efetiva( execucao.getDt_execucao_formulario().toString().split(" ")[1] );
							}

							if (execucao.getNr_hora_termino() != null) {
								esc.setHr_termino_efetivo( execucao.getNr_hora_termino().toString().split(" ")[1] );
							}

							System.out.println("[0] " + execucao.getDt_execucao_formulario().toString().split(" ")[0]);
							System.out.println("[1] " + execucao.getDt_execucao_formulario().toString().split(" ")[1]);

						}else{

							Calendar c = Calendar.getInstance(TimeZone.getDefault()); // omit timezone for default tz  
					        c.setTime(new Date()); // your date; omit this line for current date  
					        int offset = c.get(Calendar.DST_OFFSET); 

					        if(offset == 0){
					        	System.out.println("N�O ESTA EM HORARIO DE VER�O");
					        }else{
					        	System.out.println("EST� EM HORARIO DE VER�O");
					        }

							if( ( dataAtual.get(Calendar.HOUR_OF_DAY) == (dataEscalonado.get(Calendar.HOUR_OF_DAY) ) )){
								if(  ( dataAtual.get(Calendar.MINUTE) > dataEscalonado.get(Calendar.MINUTE) ) ){
								//ATRASADA
									st_escalonamento_usuario = 3;
								}
							}else if( ( dataAtual.get(Calendar.HOUR_OF_DAY) > (dataEscalonado.get(Calendar.HOUR_OF_DAY) ) ) ){
								st_escalonamento_usuario = 3;
							}
						}

						if ( (periodicidade.getTp_ciclo_tempo() == null) || ((periodicidade.getTp_ciclo_tempo() == 1) && (addEscalonamento_ciclo_tempo) ) ) {
							pesquisa.buscarObjetoCompleto(formulario);
							escalonamento_formulario.setFormulario(formulario);
							esc.setEscalonamento_formulario(escalonamento_formulario);						
							esc.setSt_escalonamento_usuario(st_escalonamento_usuario);

							Revisao_programa_qualidade revisao = (Revisao_programa_qualidade) this.getDao().getSession().createCriteria(Revisao_programa_qualidade.class).add(Restrictions.eq("cd_revisao_programa_qualidade", formulario.getCd_revisao_programa_qualidade())).uniqueResult();
							if (revisao.getSt_revisao_programa_qualidade().equals(2)) {
								listaAux.add(esc);							
							}
						}
					}
				} else {
					// TODO
				}
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pesquisa.setDao(new GenericDAO());
		List<?> listaRevisoes  = pesquisa.getDao().getSession().createCriteria(Revisao_programa_qualidade.class).add(Restrictions.eq("st_revisao_programa_qualidade", Revisao_programa_qualidade.REVISAO_APROVADA)).list();
		List<Object> listaFormulario  		= new ArrayList<Object>();
		List<Object> listaProcessosListar  	= new ArrayList<Object>();
		listaProcessosListar.add(listaAux);

		for (Revisao_programa_qualidade rev : (List<Revisao_programa_qualidade>) listaRevisoes) {

			Programa_qualidade prg = (Programa_qualidade) pesquisa.getDao().getSession().createCriteria(Programa_qualidade.class).add(Restrictions.eq("cd_programa_qualidade", rev.getCd_programa_qualidade())).uniqueResult();

			listaProcessosListar.add(prg);
			List<?> listaProcessos = pesquisa.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_revisao_programa_qualidade", rev.getCd_revisao_programa_qualidade())).add(Restrictions.eq("st_formulario", Formulario.APROVADO)).addOrder(Order.asc("nm_formulario")).list();

			if ( (listaAux != null) && (listaAux.size() > 0) ) {
				
				for (Object oAuxFRM : listaProcessos) {
					pesquisa.buscarObjetoCompleto(oAuxFRM);
				}

				listaProcessosListar.add(listaProcessos);
			}
		}

		return listaProcessosListar;
	}
}