import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ControlService } from 'core/services/control/control.service';
import { DialogService } from 'core/services/dialog/dialog.service';
import { ScheduleService } from 'core/services/schedule/schedule.service';

@Component({
    selector: 'schedule-item',
    templateUrl: './schedule-item.component.html',
    styleUrls: ['./schedule-item.component.css']
})
export class ScheduleItemComponent {
    @Input() modalMedia: boolean;
    @Input() schedules: string[];

    @Output() show: EventEmitter<boolean> = new EventEmitter<boolean>();
    @Output() media: EventEmitter<string> = new EventEmitter<string>();
    @Output() type: EventEmitter<string> = new EventEmitter<string>();

    errorMessage = '';
    result: any;
    confirmDelete = false;

    statusName: Object = {
        O: 'A Enviar',
        T: 'Enviado',
        E: 'Não enviados'
    };

    constructor(
        private scheduleService: ScheduleService,
        private router: Router,
        private DialogService: DialogService,
        private controlService: ControlService
    ) { }

    editSchedule(id) {
        this.router.navigateByUrl('/home/compose/' + id);
    }

    getNameStatus(status) {
        return this.statusName[status];
    }

    onSearchSchedule(result: any) {
        if (!result) {
            this.errorMessage = 'Ops! Something when wrong recovering schedules';
            // this.errorMessage = 'Houve um erro ao efetuar o login.';
        } else {
            this.result = result[0];
        }
    }

    confirmDeleteSchedule(id, index) {
        const promise = this.DialogService.confirm({
            titleBar: 'Delete scheduling',
            message: 'Do you really want to delete this schedule?',
            yesMessage: '<i class="fa fa-times" aria-hidden="true"></i> Delete',
            noMessage: 'Cancel'
        });

        promise.then(res => {
            if (res) {
                this.deleteSchedule(id, index);
            }
        });
    }

    deleteSchedule(id, index) {
        this.schedules.splice(index, 1);
        this.scheduleService.deleteSchedule(id)
            .subscribe(
            result => this.onSearchSchedule(result),
            error => console.log(error)
            );
    }

    openMedia(media) {
        if (media.type === 'A' || media.type === 'P' || media.type === 'S') {
            this.openPhoto(this.controlService.temp_image_path + media.photo);
        } else if (media.type === 'V') {
            this.openVideo(this.controlService.video_image_path + media.video);
        } else if (media.type === 'R') {
            this.openLink(media.link);
        }
    }

    openVideo(video) {
        this.modalMedia = true;
        this.show.emit(this.modalMedia);
        this.media.emit(video);
        this.type.emit('V');
    }

    openLink(link) {
        window.open(link);
    }

    openPhoto(photo) {
        this.modalMedia = true;
        this.show.emit(this.modalMedia);
        this.media.emit(photo);
        this.type.emit('P');
    }
}
