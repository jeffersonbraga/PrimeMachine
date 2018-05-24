package br.com.primum.scheduler.teste;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Timer;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.service.scheduler.SchedulerService;

public class SchedulerImpl{
	
	public SchedulerImpl() {

		//Maneira utilizando timer.
		//LogPrimum.mensagem("Inicio teste.");
		Timer timer = new Timer();
		//timer.schedule(new LogPrimum(), 5000);
		//LogPrimum.mensagem("Fim teste.");		
	}

	 /*public static void main( String[] args ) throws Exception {
		//Maneira utilizando Quartz e cronexpression    	
    	//specify your sceduler task details
    	JobDetail job = new JobDetail();
    	job.setName("primum");
    	job.setJobClass(SchedulerService.class);

    	//configure the scheduler time
    	try {
			CronTrigger trigger = new CronTrigger();    	
			trigger.setName("trigger_primum");
			trigger.setCronExpression("0/5 * * * * ?");

			//schedule it
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	 }*/

	 public static void main( String[] args ) throws Exception {
		/*
		JobDetail job = new JobDetail();
	    job.setName("primum");
	    job.setJobClass(SchedulerService.class);

		//configure the scheduler time
		SimpleTrigger trigger = new SimpleTrigger();
		trigger.setName("trigger_primum");
		trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		trigger.setRepeatInterval(3000);

		//schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);*/
	}
	 
	 
	 public void agendarTarefa(String jobName, Integer tempo) throws Exception{
		 /*JobDetail job = new JobDetail();
		 job.setName(jobName);
		 job.setJobClass(SchedulerService.class);
	
		 //configure the scheduler time
		 SimpleTrigger trigger = new SimpleTrigger();
		 trigger.setName("trigger_primum");
		 trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		 trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		 trigger.setRepeatInterval(3000);
				//schedule it
		 Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		 scheduler.start();
	 	 scheduler.scheduleJob(job, trigger);*/
	 }
}