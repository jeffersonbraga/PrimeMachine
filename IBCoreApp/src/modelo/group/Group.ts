import { GroupsProfiles } from 'modelo/group/GroupsProfiles';
import { Account } from './../account/Account';

export class Group {

	idGroup : number;
	name : string;
	account : Account;
	groupsObject : any;
	groupType : any;
}