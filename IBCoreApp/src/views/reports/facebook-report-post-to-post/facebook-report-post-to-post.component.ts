import { DatePipe } from '@angular/common';
import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { MonthNamePipe } from 'core/pipes/monthName.pipe';
import { ControlService } from 'core/services/control/control.service';
import { FacebookService } from 'core/services/facebook/facebook.service';
import { NotificationService } from 'core/services/notification/notification.service';
import { IAsidePieChartDouble } from 'modelo/complements/Complements';
import { Notification } from 'modelo/notification/Notification';
import { FacebookReportPostsData } from 'modelo/reports/FacebookReportPostsData';

declare let $;

@Component({
  selector: 'facebook-report-post-to-post',
  templateUrl: './facebook-report-post-to-post.component.html',
  styleUrls: ['./facebook-report-post-to-post.component.css']
})
export class FacebookReportPostToPostComponent implements OnInit, AfterViewInit {

  @Input() dateFrom: Date;
  @Input() dateUntil: Date;
  @Input() profile;
  @Input() reportFacebookPostToPost: any;
  account;

  public postsData: FacebookReportPostsData[] = [];

  constructor(private facebookService: FacebookService,
        private controlService: ControlService,
        private datePipe: DatePipe,
        private monthNamePipe: MonthNamePipe,
        private notificationService: NotificationService) { }

  ngOnInit() {
    this.mountPostsData();
    this.account = this.controlService.account;
  }

  ngAfterViewInit(): void {
    $('[data-toggle="tooltip"]').tooltip();
  }

  getXLS() {
    this.facebookService.getPostToPostReportXLS(this.profile.idProfile, this.dateFrom.getTime(), this.dateUntil.getTime())
			.subscribe(
				result => this.downloadXLS(result),
				error => console.log(error)
			);
  }

  downloadXLS(result){
		window.open(this.controlService.context_app + 'woopsocial/get_xls?file=' + result.fileName + '&filename=' + result.formattedFileName + '&idAccount=' + result.idAccount);
  }
  
  getPDF() {
    
		let notification: Notification = {
			account: this.account,
			profile: this.profile,
			date: new Date(),
			message: this.account.name + " created a PDF report for channel " + this.profile.screenName + ".",
			// message: this.account.name + " gerou um relatório em PDF do canal " + this.profile.screenName + ".",
			color: this.notificationService.COLOR_SUCCESS,
			action: '',
			unread: true
		};

		this.notificationService.addNotifications(notification)
		.subscribe(
			result => console.log(result),
			error => console.log(error)
		);
		window.print();
	}

  mountPostsData() {

    for (var index = 0; index < this.reportFacebookPostToPost.posts.length; index++) {

      let post = this.reportFacebookPostToPost.posts[index];

      let postData: FacebookReportPostsData;

      postData = {
        likeData: this.getReaction(post.likes, post.likesPercentage, 'likes'),
        loveData: this.getReaction(post.haha, post.hahaPercentage, 'haha'),
        hahaData: this.getReaction(post.haha, post.hahaPercentage, 'haha'),
        wowData: this.getReaction(post.wow, post.wowPercentage, 'wow'),
        sorryData: this.getReaction(post.sorry, post.sorryPercentage, 'sorry'),
        angerData: this.getReaction(post.anger, post.angerPercentage, 'anger'),
        commentsData: this.comments(post),
        sharesData: this.shares(post),
        clicksData: this.clicks(post),
        videoRetentionData: post.type == '128' ? this.videoRetention(post.videoRetention) : null,
        videoViewsTypeData: post.type == '128' ? this.videoViewsType(post) : null,
        videoViewTimeByAgeGenderData: post.type == '128' ? this.videoViewTimeByAgeGender(post) : null
      }

      this.postsData.push(postData);
    }
  }

  getIconUrl(type) {

    if (type == 'love') {
      return '/assets/img/reactions/reaction-love.svg';
    } else if (type == 'likes') {
      return '/assets/img/reactions/reaction-like.svg';
    } else if (type == 'haha') {
      return '/assets/img/reactions/reaction-haha.svg';
    } else if (type == 'wow') {
      return '/assets/img/reactions/reaction-wow.svg';
    } else if (type == 'sorry') {
      return '/assets/img/reactions/reaction-sad.svg';
    } else if (type == 'anger') {
      return '/assets/img/reactions/reaction-angry.svg';
    } else if (type == 'comment') {
      return '/assets/img/reactions/comment.svg';
    } else if (type == 'share') {
      return '/assets/img/reactions/share.svg';
    } else if (type == 'click') {
      return '/assets/img/reactions/click.svg';
    }

  }

  formatAgeGroupDisplay(initialAgeGroup, endAgeGroup) {
    if (Number(initialAgeGroup) < 65) {
      return initialAgeGroup + '-' + endAgeGroup;
    } else {
      return initialAgeGroup + '+';
    }
  }

  fromMilisecondsToMinutes(milisecondsTime) {
		return Number(((milisecondsTime / 1000) / 60));
	}

  videoViewTimeByAgeGender(post) {

    let male = post.videoViewTimeByMaleUsers.map(
      fav => {
        return {
          label: this.formatAgeGroupDisplay(fav.initialAgeGroup, fav.endAgeGroup),
          value: Math.round(this.fromMilisecondsToMinutes(fav.value))
        }
      });

    male.sort((a, b) => a.label > b.label ? 1 : 0);

    let female = post.videoViewTimeByFemaleUsers.map(
      fav => {
        return {
          label: this.formatAgeGroupDisplay(fav.initialAgeGroup, fav.endAgeGroup),
          value: Math.round(this.fromMilisecondsToMinutes(fav.value))
        }
      });

    female.sort((a, b) => a.label > b.label ? 1 : 0);

    let unidentified = post.videoViewTimeByUnidentifiedUsers.map(
      fav => {
        return {
          label: this.formatAgeGroupDisplay(fav.initialAgeGroup, fav.endAgeGroup),
          value: Math.round(this.fromMilisecondsToMinutes(fav.value))
        }
      });

    unidentified.sort((a, b) => a.label > b.label ? 1 : 0);

    return [{
      "key": "Male",
      "color": "#00d6f6",
      "values": male
    },
    {
      "key": "Female",
      "color": "#f74e7e",
      "values": female
    },
    {
      "key": "Unidentified",
      "color": "#cdcdcd",
      "values": unidentified
    }
    ]
  }

  videoViewsType(post) {

    let videoViewsTypePieDouble: IAsidePieChartDouble;

    return videoViewsTypePieDouble = {
      chart: {
        data: [
          {
            "label": "Autoplay",
            "value": post.videoAutoPlayedViews,
            "color": "#00d6f6"
          },
          {
            "label": "Clique",
            "value": post.videoClickToPlayViews,
            "color": "#f6cb00"
          }
        ],
        height: 70,
        isDonut: true,
      },
      leftContent: {
        title: 'Autoplay',
        value: post.videoAutoPlayedViews,
        smallText: '(' + post.videoAutoPlayedViewsPercentage + '%)',
        valueColor: '#00d6f6'
      },
      rightContent: {
        title: 'Clique',
        value: post.videoClickToPlayViews,
        smallText: '(' + post.videoClickToPlayViewsPercentage + '%)',
        valueColor: '#f6cb00'
      }
    }
  }

  videoRetention(videoRetention) {

    videoRetention = videoRetention.sort((a, b) => a.property - b.property);

    let videoRetentionData = videoRetention.map(
      fav => {
        return fav.value / 100
      });

    return videoRetentionData;
  }

  getReaction(value, percentage, type) {

    return {
      value: value,
      smallText: '(' + percentage + '%)',
      iconUrl: this.getIconUrl(type)
    };

  }

  comments(post) {

    return {
      value: post.comments,
      smallText: '(' + post.commentsPercentage + '%)',
      iconUrl: this.getIconUrl('comment')
    };
  }

  shares(post) {

    return {
      value: post.shares,
      smallText: '(' + post.sharesPercentage + '%)',
      iconUrl: this.getIconUrl('share')
    };
  }

  clicks(post) {

    return {
      value: post.clicks,
      smallText: '(' + post.clicksPercentage + '%)',
      iconUrl: this.getIconUrl('click')
    };
  }
}
