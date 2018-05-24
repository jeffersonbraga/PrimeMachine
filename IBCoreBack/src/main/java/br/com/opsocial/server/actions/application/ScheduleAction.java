package br.com.opsocial.server.actions.application;

public class ScheduleAction {

//	@Override
//	public void execute(JobExecutionContext context) throws JobExecutionException {
//
//		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
//		try {
//
//			Long id = dataMap.getLong("post_schedule");
//			MaintenancePostRemote postRemote 		= (MaintenancePostRemote) RecoverMaintenance.recoverMaintenance("Post");
//			Post p = postRemote.getById(id);
//			HashMap<Boolean, String> result = new  HashMap<Boolean, String>();
//
//			new PostAction().makeSingleProfilePost(p.getProfile(), p, result);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
/*	
	@Override
	@RequestMapping(value = "/schedule_action",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {

		List<Profile> profilesToPost = (List<Profile>) getParameters().get("profiles");
		Character imageType = (Character) getParameters().get("imageType");
		Sets scheduleView = (Sets) getParameters().get("schedule");
		
		Date scheduleDate = ((br.com.opsocial.client.entity.primitive.Date) getParameters().get("scheduledate")).getValue();
		
		Date now = new Date();
		
		//TODO Felipe - e se tiver em alguma timezone com hora maior?
		
		if(scheduleDate.getTime() <= now.getTime()) {
			
			getParameters().put("message", new StringUtil
					("Data e hora inicial deve ser maior que a hora atual (BRT)."));
		} else {
			
			List<Profile> profilesToUpdate = new ArrayList<Profile>();
			//profilesToUpdate.setPersistences(new ArrayList<Profile>());
			
			if(imageType != null) {
				if(imageType.equals(Persistent.FILTERED_PICTURE)) {
					String picture = ((StringUtil) getParameters().get("picture")).getValue();
					//scheduleView.getPosts().get(0).setPicture(UtilFunctions.createFilteredPicture(picture, OpSocialBackApplication.opSocialContext));
					scheduleView.getPosts().get(0).setPicture(UtilFunctions.createFilteredPicture(picture));
				}
			}
			
			setParameters(new HashMap<String, Object>());
		
			for(Profile profileDTO : profilesToPost) {
				
				MaintenanceSetRemote remote = (MaintenanceSetRemote) RecoverMaintenance.recoverMaintenance("Set");

				List<Sets> setsByProfile = remote.getByProfile(profileDTO.getIdProfile(), 
						((User) getSession().getAttribute("user")).getIdUser());
				
				Boolean canSave = true;
				
				for(Sets set : setsByProfile) {
					
					Date startDate = new Date(set.getStartDate() * 1000);
						
					if(profileDTO.getType().equals(Profile.TWITTER) &&
							(startDate.getYear() + startDate.getMonth() + startDate.getDate() ==
							scheduleDate.getYear() + scheduleDate.getMonth() + scheduleDate.getDate())) {
						
						for(Post post : set.getPosts()) {
							if(post.getText().equals(scheduleView.getPosts().get(0).getText())) {

								getParameters().put("message", new StringUtil("O twitter não aceita conteúdo idêntico no prazo de 24 horas."));
								canSave = false;
								break;
							}
						}
					}
				}
						
				if(canSave) {
				
					Sets scheduleBase = null;
					
					if(scheduleView.getIdSet() != null) {
						scheduleBase = scheduleView;
						getParameters().put("isedit", true);
					} else {
						scheduleView.setProfile(profileDTO);
						scheduleBase = scheduleView;
					}
								
					scheduleBase.setActive('T');
					scheduleBase.setEndDate(scheduleDate.getTime() / 1000);
					scheduleBase.setIsSchedule('T');
					scheduleBase.setProfile(profileDTO);
					scheduleBase.setStartDate(scheduleDate.getTime() / 1000);
					scheduleBase.setState(Sets.ON);
					scheduleBase.setStatus(Sets.SCHEDULED);
			
					scheduleBase = remote.save(scheduleBase);
					
					getParameters().put("schedule", scheduleBase);
				}
			}
		}
	}*/
}