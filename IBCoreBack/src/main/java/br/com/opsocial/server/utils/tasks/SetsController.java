package br.com.opsocial.server.utils.tasks;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.opsocial.ejb.das.MaintenancePostRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.sets.PostMessage;

public class SetsController {

	private Timer timer;

	public SetsController() {
		timer = new Timer();
		timer.schedule(new Task(), 0, 1000 * 60);
	}

	class Task extends TimerTask {

		public void run() {

			MaintenancePostRemote postRemote = (MaintenancePostRemote) RecoverMaintenance.recoverMaintenance("Post");

			Date now = new Date();
			now.setSeconds(0);

			//Long time = now.getTime() / 1000;
			Long time = now.getTime();

			List<Post> startingPosts = postRemote.getByStartDate(time);

			for(Post post : startingPosts) {

				try {

					if(!post.getProfile().getType().equals(Profile.INSTAGRAM)) {

						if(post.getPosted().equals(Post.NOT_POSTED)) {
							new Thread(new PostMessage(post, post.getProfile())).start();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
