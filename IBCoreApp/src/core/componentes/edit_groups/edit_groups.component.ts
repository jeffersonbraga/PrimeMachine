import { ChangeDetectorRef, Component, Injector, Input, OnDestroy, OnInit } from '@angular/core';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { ControlService } from 'core/services/control/control.service';
import { GroupsService } from 'core/services/groups/groups.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { Group } from 'modelo/group/Group';
import { GroupsProfiles } from 'modelo/group/GroupsProfiles';
import { Profile } from 'modelo/profile/Profile';

declare const $: any;
@Component({
  selector: 'edit-groups',
  templateUrl: './edit_groups.component.html'
})
export class Edit_groupsComponent implements OnInit, OnDestroy {

    group: Group;
    accountProfiles: Profile[];
    currentModal: GenericModalComponent;
    checkedProfiles: Profile[] = [];
    checkedProfilesAux = [];
    groupsProfiles: GroupsProfiles[];
    loading: boolean;
    oldCheckedList: Profile[];

    @Input()
    isClassVisible = false;

    constructor (
        private groupsService: GroupsService,
        private profilesService: ProfilesService,
        private controlApp: ControlService,
        private injector: Injector,
        private ref: ChangeDetectorRef
    ) {
        this.group = new Group();
        this.accountProfiles = [];
        this.currentModal = this.injector.get('currentModal');
        this.loading = true;
        this.oldCheckedList = [];
    }

    ngOnInit() {
        this.group = this.groupsService.currentGroup;

        // Backup dos checked profiles
        for (let i = 0; i < this.profilesService.checkedProfiles.length; i++) {
            this.checkedProfilesAux[i] = this.profilesService.checkedProfiles[i];
        }

        // Zera checked profiles para não marca-los no manage channels e reatribui valores no onDestroy
        this.profilesService.checkedProfiles.splice(0, this.profilesService.checkedProfiles.length);

        // Salva profiles que estavam adicionados no grupo para no fim
        // adicionalos no grupo default caso eles sejam removidos do grupo que estavam
        if (this.group.groupsObject !== undefined && this.group.groupsObject != null) {
            if (this.group.groupsObject.length > 0) {
                for (let i = 0; i < this.group.groupsObject.length; i++) {
                    this.oldCheckedList.push(this.group.groupsObject[i].profile);
                }
            }
        }

        this.profilesService.getProfiles()
            .subscribe(
            result => this.onGetProfiles(result),
            error => console.log(error));
    }

    onGetProfiles(result: Profile[]) {
        if (!result) {
            console.log('Houve um erro ao recuperar canais. ');
        } else {
            this.accountProfiles = result;
            this.extractProfilesFromGroupsProfiles(this.group);
        }
    }

    extractProfilesFromGroupsProfiles(group: Group) {
        const aux: any = [];
        this.groupsProfiles = this.group.groupsObject;

        if (this.groupsProfiles != null) {
            for (let i = 0; i < this.groupsProfiles.length; i++) {

                this.checkedProfiles.push(this.groupsProfiles[i].profile as Profile);

                for (let k = 0; k < this.accountProfiles.length; k++) {
                    if ((this.groupsProfiles[i] as GroupsProfiles).profile.idProfile === this.accountProfiles[k].idProfile) {
                        aux.push(this.accountProfiles[k]);
                    }
                }
            }

            this.accountProfiles = $(this.accountProfiles).not(aux).get();
        }

        this.loading = false;
    }

    addRemoveObjectChecked(event) {
        this.profilesService.lockCheck = true;

        if (event.checked) {
            this.checkedProfiles.push(event.profile);
        } else {
            this.checkedProfiles.splice(this.checkedProfiles.indexOf(event.profile), 1);
        }
    }

    saveGroup() {
        this.groupsService.updateGroup(this.checkedProfiles, this.group)
            .subscribe(
            result => this.onUpdateGroup(result),
            error => console.log(error));
    }

    onUpdateGroup(result: Group) {
        if (!result) {
            console.log('Houve um erro ao salvar grupo. ');
        } else {
            this.profilesService.lockCheck = false;
            // Adicona canais, que não estão mais no grupo, no grupo default
            this.manageUncheckedList(this.oldCheckedList, result.groupsObject);
            // Remove profiles do grupo default
            this.removeFromDefault(result.groupsObject);
            // Atualiza grupos com perfis adicionados
            this.groupsService.currentGroups[this.groupsService.currentIndex] = result;
            // Volta para modal grups managament
            this.manageGroups();
        }
    }

    manageGroups() {
        // Controle do estado dos canais
        this.groupsService.editingGroupId = this.group.idGroup;

        if (this.groupsService.editingGroupIds) {
            if (this.groupsService.editingGroupIds.indexOf(this.group.idGroup) === -1) {
                this.groupsService.editingGroupIds.push(this.group.idGroup);
                localStorage.setItem('editingGroupIds', JSON.stringify(this.groupsService.editingGroupIds));
            }
        }

        this.currentModal.setDynamicModalContent('Groups_managementComponent');
    }

    manageUncheckedList(oldCheckedList: Profile[], newCheckedList: any) {
        for (let i = 0; i < oldCheckedList.length; i++) {
            this.profilesService.addProfilesToSouce(oldCheckedList[i]);
        }
    }

    removeFromDefault(groups: any) {
        const tam = groups.length;

        for (let i = 0; i < tam; i++) {
            this.profilesService.removeProfileFromSource(groups[i].profile);
        }
    }

    ngOnDestroy() {
      this.profilesService.lockCheck = true;

      // Volta backup dos checked profiles
      for (let i = 0; i < this.checkedProfilesAux.length; i++) {
          this.profilesService.checkedProfiles[i] = this.checkedProfilesAux[i];
      }
    }
}
