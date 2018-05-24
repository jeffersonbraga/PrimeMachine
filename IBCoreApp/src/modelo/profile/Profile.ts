import { Account } from './../account/Account';
import { InstagramDevice } from './../instagramDevice/InstagramDevice';

export class Profile {
	
	public static   TWITTER : string = 'T';
	public static   FACEBOOK: string = 'F';
	public static   GOOGLE: string = 'G';
	public static   YOUTUBE: string = 'Y'; 
	public static   FACEBOOK_PAGE: string = 'P';
	public static   INSTAGRAM: string = 'I';
	public static   RECLAMEAQUI: string = 'R';
	public static   NEWS: string = 'N';
	public static   BLOGS: string = 'B';
	public static   FACEBOOK_GROUP: string = 'A';

	public static   FANS_FACEBOOK_TO_REPORT: number = 30;

	public static   REPORT_UNAVAILABLE: string = 'U';
	public static   REPORT_GENERATING: string = 'G';
	public static   REPORT_FINALIZED: string = 'F';
	public static   REPORT_REFRESH_TOKEN: string = 'R';
	
	public static   GROUP_STATUS_OPEN: string = "OPEN";
	public static   GROUP_STATUS_CLOSED: string = "CLOSED";
	public static   GROUP_STATUS_SECRET: string = "SECRET";
	
	public static   ACTIVE: string = 'T';
	public static   INACTIVE: string = 'F';

	idProfile : number;

	networkId : string;
	
	avatar : string;
	
	screenName : string;
	
	type : string = "";
	
	token : string;
	
	tokenSecret : string;
	
	active : string = 'T';
	
	refreshToken : string;
	
	tokenDate: number;
	
	tokenExpiresIn: number;
	
	followers: number;
	
	follows: number;
	
	insertionDate: number;
	
	account : Account;
	
	parentProfile : Profile;
	
	instagramDevice : InstagramDevice;
	
    childrenProfiles : Profile[];
    
    profileInformation : string;
    
    turn : string = 'T';
    
    idMonitoringForGroups : number;
    
    instagramPassword : string;
   
    instagramGUID : string;
    
    instagramDeviceId : string;

    instagramPhoneId : string;

	isSchedulingAuthenticated : string;
	
	isTokenValid: string;

	instagrambusinessid : string;

	instagramCookies : string;

	instagramLastLoginTime: number;

	authenticationProfileCount: number;
}