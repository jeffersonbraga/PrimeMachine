import { Component, OnInit } from '@angular/core';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { ScheduleService } from 'core/services/schedule/schedule.service';
import { Account } from 'modelo/account/Account';
import { ISchedulePost } from 'modelo/schedule/Schedule';
import { User } from 'modelo/user/User';

@Component({
    moduleId: module.id,
    selector: 'schedule-selector',
    templateUrl: './schedule.component.html',
    styleUrls: ['./schedule.component.css']
})

export class ScheduleComponent implements OnInit {
    result: any;
    status = 'O';
    showed = false;
    mediaPreview: string;
    typeModal: string;

    schedules: ISchedulePost;

    not_posted: any[];
    error_posting: any[];
    posted: any[];

    emptyNotPosted = false;
    emptyErrorPosting = false;
    emptyPosted = false;

    errorMessage = '';

    profilesCheckeds = [];

    showCheckAllBtn = true;

    groupType: any = 'P';

    constructor(
        private scheduleService: ScheduleService,
        private BreadcrumbsService: BreadcrumbsService,
        private profileService: ProfilesService 
    ) {

        this.profileService.resetSelectionList();
    }

    ngOnInit() {
    this.result = [];

    this.setSchedules();
    }

    changeStatus(s) {
        this.status = s;
        this.getSchedules();
        console.log(s);
    }

    getSchedules() {
        if (this.profilesCheckeds.length > 0) {
            const usr: User = new User();
            usr.account = new Account();
            usr.account.idAccount = 1;

            this.scheduleService.getSchedule(usr, this.status, this.profilesCheckeds)
            .subscribe(
                result => this.onSearchSchedule(result),
                error => console.log(error)
            );
        }
    }

    onSearchSchedule(result: any) {
        console.log(result);
        if (!result) {
            this.errorMessage = 'Ops! Something when wrong recovering schedules';
            // this.errorMessage = 'Houve um erro ao recuperar os agendamentos.';
        } else {
            this.result = result[0];
            this.mountSchedules(result[1].status);
        }
    }

    mountSchedules(status) {
        let date, splittedDate, onlyDate;
        const obj = this.result.map(
            res => {
                // converte data do backend
                date = res.scheduleDate;
                splittedDate = date.split(' ');
                onlyDate = splittedDate[0].split('/');
                onlyDate = onlyDate[0] + ' ' + onlyDate[1] + ' ' + onlyDate[2];
                splittedDate[0] = onlyDate;
                date = splittedDate.join(' ');

                return {
                    id: res.idPost,
                    type: res.profile.type,
                    channel: res.profile.screenName,
                    datetime: new Date(date),
                    postsAttachments: this.mountPhotoObject(res.postsAttachments, res.picture),
                    status: res.posted,
                    description: res.text,
                    typeMedia: res.appendType,
                    link: res.originalLink,
                    video: res.videoName,
                    userLog: res.userLog
                };
            }
        );

        this.updateSchedule(obj, status);
    }

    mountPhotoObject(album, photo) {
        const lgt: number = album.length;

        if (lgt) {
            return album;
        } else if (photo) {
            return [photo];
        } else {
            return [];
        }
    }

    updateSchedule(obj, status) {
        if (status === 'O') {
            this.not_posted = obj;
            this.emptyNotPosted = obj.length ? false : true;
        } else if (status === 'T') {
            this.posted = obj;
            this.emptyPosted = obj.length ? false : true;
        } else {
            this.error_posting = obj;
            this.emptyErrorPosting = obj.length ? false : true;
        }

        this.setSchedules();
    }

    checkSocialMedia(event) {
        this.profilesCheckeds = [];

        if(event) {
            event.map(profile => {
                this.profilesCheckeds.push(profile.idProfile);
            });
        }

        this.getSchedules();
    }

    setSchedules() {
        this.schedules = {
            not_posted: this.not_posted,
            error_posting: this.error_posting,
            posted: this.posted
        };
    }

    showMedia(event) {
        this.showed = event;
    }

    changeMedia(event) {
        this.mediaPreview = event;
    }

    changeTypeModal(event) {
        this.typeModal = event;
    }
}
