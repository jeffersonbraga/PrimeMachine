import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { ControlService } from 'core/services/control/control.service';
import { GroupsService } from 'core/services/groups/groups.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { Group } from 'modelo/group/Group';
import { GroupsProfiles } from 'modelo/group/GroupsProfiles';
import { Profile } from 'modelo/profile/Profile';
import { Subscription } from 'rxjs/subscription';

declare var $;

@Component({
    selector: 'app-blank-component',
    templateUrl: './blank.component.html'
})
export class BlankComponent implements OnInit, OnDestroy {

    @Input()
    currentModal: string; // GenericModalComponent;

    headerTitleChannels = 'Blank';

    manageChannelsComponentClassName = 'BlankComponent';

    constructor(
    ) {
    }

    ngOnInit() {
    }

    ngOnDestroy(): void {
    }
}
