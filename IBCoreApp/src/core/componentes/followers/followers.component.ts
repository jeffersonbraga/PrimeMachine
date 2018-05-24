import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { FollowersService } from 'core/services/followers/followers.service';
import { Profile } from 'modelo/profile/Profile';

declare const $;

@Component({
    selector: 'followers',
    templateUrl: './followers.component.html',
    styleUrls: ['./followers.component.css']
})

export class FollowersComponent {

    @Input()
    profile: Profile;

    @Input()
    followers: any;

    @ViewChild('follower')
    follower: ElementRef;

    @Output()
    emit: EventEmitter<number> = new EventEmitter<number>();

    anyNumber = 0;

    followState = [];

    constructor(
        private FollowersService: FollowersService
    ) {	}

    toggleFollow(follow, follower) {
        if (follow) {
            this.followState[follower.userName] = 'follow';
            this.FollowersService.follow(follower)
                .subscribe();

            const options = {
                message: 'Você começou a seguir ' + follower.userName,
                style: 'bar',
                timeout: 8000,
                position: 'top'
            };

            $('body').pgNotification(options).show();
        } else {
            this.followState[follower.userName] = 'unfollow';
            this.FollowersService.unfollow(follower)
                .subscribe();

            const options = {
                message: 'Você não está mais seguindo ' + follower.userName,
                style: 'bar',
                timeout: 8000,
                position: 'top'
            };

            $('body').pgNotification(options).show();
        }
    }

    checkScroll() {
        if (this.follower.nativeElement.scrollTop + this.follower.nativeElement.offsetHeight
            > this.follower.nativeElement.scrollHeight - 20) {
                this.emit.emit(++this.anyNumber);
        }
    }
}
