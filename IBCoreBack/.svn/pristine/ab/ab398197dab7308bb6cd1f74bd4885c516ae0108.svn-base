package br.com.opsocial.server.actions.application;

import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneRules;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.OptimisticLockException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceTimezoneRemote;
import br.com.opsocial.ejb.entity.application.Timezone;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class ListTimezonesAction extends ServerAction{

	@CrossOrigin
	@RequestMapping(value = "/list_timezones",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doNewAction() throws Exception, OptimisticLockException {

		List<Object> l = new ArrayList<Object>();
		MaintenanceTimezoneRemote setRemote = (MaintenanceTimezoneRemote) RecoverMaintenance.recoverMaintenance("Timezone");
		List<Timezone> timezones = setRemote.listTimezones();
		l.add(timezones);

		for (Timezone itTimeZone : timezones) {

			Float addDaylightSave = new Float(0);
			if (itTimeZone.getName() != null && !itTimeZone.getName().equals("")) {
				TimeZone t = TimeZone.getTimeZone(itTimeZone.getName());
/*
				ZoneRules rules = ZoneId.of(t.getDisplayName()).getRules();

				// if it has DST, isFixed is false
				boolean isFixed = rules.isFixedOffset();

				if (!isFixed) {
					// if lists are empty, offset never varies, so there's no DST
					//List<ZoneOffsetTransitionRule> transitionRules = rules.getTransitionRules();
					//List<ZoneOffsetTransition> transitions = rules.getTransitions();

					// check a specific date (using java.time.Instant)
					if (rules.isDaylightSavings(Instant.now())) {
						addDaylightSave = ( new Float(( t.getRawOffset()/1000 ) /60) /60);
					}
					// check a specific date (using java.util.Date)
					//Date date = new Date();
					//System.out.println(rules.isDaylightSavings(date.toInstant()));
				}
*/
				Float valueF = new Float( new Float(( t.getRawOffset()/1000 ) /60) /60);
				
				if (t.inDaylightTime(new Date())) {
					valueF = valueF + (new Float( new Float(( t.getDSTSavings()/1000 ) /60) /60));
				}
				
				Object valueAux = null;
				itTimeZone.setValue(valueF);
				int refHour = 0;

				if (Math.round(valueF) > 0) {

					if ( (Math.round(valueF) - valueF) > 0) {
						refHour = Math.round(valueF) -1;
					} else {
						refHour = Math.round(valueF);
					}

				} else {

					//refHour = Math.round(valueF);

					if ( (Math.round(valueF) > valueF) ) {
						refHour = Math.round(valueF) -1;
					} else if ( (Math.round(valueF) == valueF) ) {
						refHour = Math.round(valueF);
					} else if ( (Math.round(valueF) < valueF) ) {
						refHour = Math.round(valueF) +1;
					}

				}

				if ( ((valueF - refHour) > 0) || ((valueF - refHour) < 0) ) {
					
					if (Math.round((30 * (valueF - refHour)) / 0.50) < 0) {
						valueAux = Math.round((30 * (valueF - refHour)) / 0.50) * -1;
					} else {
						valueAux = Math.round((30 * (valueF - refHour)) / 0.50);						
					}
				} else {
					valueAux = "00";
				}

				String compHour = refHour + "";
				if (refHour < 10 && refHour > -10) {
					if (refHour > 0){
						compHour = "+0" + refHour;
					} else {
						compHour = "-0" + (refHour * -1);
					}
				}

				itTimeZone.setDescription( ((t.inDaylightTime(new Date()))?"*":"") + "(GMT" + compHour + ":" + valueAux + ") - " + itTimeZone.getDescription() );
			}
		}

		return new ResponseEntity<>(l, HttpStatus.OK);		
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		
	}
}