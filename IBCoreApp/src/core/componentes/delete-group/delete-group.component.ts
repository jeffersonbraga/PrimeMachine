import { Component, Injector, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { ControlService } from 'core/services/control/control.service';
import { GroupsService } from 'core/services/groups/groups.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { Group } from 'modelo/group/Group';

@Component({
  selector: 'delete-group',
  templateUrl: './delete-group.component.html',
  styleUrls: ['./delete-group.component.css']
})
export class DeleteGroupComponent implements OnInit {

  group: Group;
  groups : Group[] = [];

  currentModal : GenericModalComponent;
  returnComponent: string;

	errorMessage: string = '';

  constructor(
    private groupService: GroupsService, 
		private controlApp: ControlService,
    private injector: Injector, 
    private profileService: ProfilesService,
    private route: ActivatedRoute
    ) {
      this.currentModal = this.injector.get("currentModal");
	}

  ngOnInit() {
    this.group = this.groupService.currentGroupToDelete;
    this.returnComponent = this.groupService.returnComponent;
		this.groups = this.groupService.currentGroups;
  }

  cancel() {
    this.currentModal.setDynamicModalContent(this.returnComponent);
  }

  delete() {
    this.groupService.deleteGroup(this.group).subscribe(
			result => this.onDeleteGroup(result),
			error => console.log(error)
		);
  }

	onDeleteGroup(result: Group[]) {
		if (!result) {
			this.errorMessage = 'Ops! Something went wrong when deleting the group.';
			// this.errorMessage = 'Houve um erro ao deletar grupo.';
		} else {
      //Remove grupo da lista de estados dos grupos
      if (this.groupService.editingGroupIds != null) {
        if (this.groupService.editingGroupIds.indexOf(this.group.idGroup) != -1) {
          this.groupService.editingGroupIds.splice(this.groupService.editingGroupIds.indexOf(this.group.idGroup), 1);
          localStorage.setItem('editingGroupIds', JSON.stringify(this.groupService.editingGroupIds));
        }
      }
      //Atualiza o current Grupos
      this.groupService.currentGroups = result;
      //Adiciona canais que estava no grupo no grupo default
      if(location.pathname.split('/')[2] !== 'rssfeed') {
        if(this.groupService.currentGroupToDelete.groupsObject != null) {
          if(this.groupService.currentGroupToDelete.groupsObject.length > 0) {
            this.addToDefault(this.groupService.currentGroupToDelete.groupsObject);
          }
        }
      } else {
        this.groupService.rssGroupsSource.next(result);

        if(this.group.groupsObject) {
          for(let i = 0; i < this.group.groupsObject.length; i++) {
            this.groupService.removeRssFeedFromSource(this.group.groupsObject[i].rssFeed);
          }
        }
      }
      //Atualiza grupo
			// this.group = new Group();
      //Retorna para modal groups management
      this.currentModal.setDynamicModalContent(this.returnComponent);
		}
	}

	addToDefault(groups: any){
		let tam = groups.length;

		for(let i = 0; i < tam; i++){
			this.profileService.addProfilesToSouce(groups[i].profile);
		}
	}
}
