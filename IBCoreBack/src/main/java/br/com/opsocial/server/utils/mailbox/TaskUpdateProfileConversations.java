package br.com.opsocial.server.utils.mailbox;

import java.util.ArrayList;
import java.util.List;

import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.facebook.FacebookAPI;
import br.com.opsocial.server.utils.twitter.TwitterAPI;

public class TaskUpdateProfileConversations implements Runnable {

	private Account account;

	private MaintenanceProfileRemote profileRemote;

	public TaskUpdateProfileConversations(Account account) {
		this.account = account;
	}

	@Override
	public void run() {

		profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");

		List<Character> types = new ArrayList<Character>();
		types.add(Profile.FACEBOOK_PAGE);
		types.add(Profile.TWITTER);

		List<Profile> profiles = profileRemote.getEntityByNetworkType(types, account.getIdAccount());

		for(Profile profile : profiles) {

			if(profile.getType().equals(Profile.FACEBOOK_PAGE)) {

				boolean canContinue = true;

				if(profile.getActive() == 'F') {
					canContinue = false;
				}

				if(canContinue) {
					new FacebookAPI().saveFacebookConversations(profile);
				}
			} else {
				new TwitterAPI().saveTwitterMessages(profile);
			}
		}
	}
}
