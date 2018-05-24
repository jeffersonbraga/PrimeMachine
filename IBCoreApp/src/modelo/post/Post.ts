import { Timezone } from './../timezone/Timezone';
import { Profile } from './../profile/Profile';
import { PostAttachment } from './../postAttachment/PostAttachment';
export class Post {

    public serialVersionUID : number = 1;
	public POSTED 			: string = 'T';
	public NOT_POSTED 		: string = 'F';
	public ERROR_POSTING 	: string = 'E';
	public POSTING 			: string = 'P';

	public static APPEND_VIDEO 		: string = 'V';
	public static APPEND_PICTURE 	: string = 'P';
	public static APPEND_ALBUM 		: string = 'A';
	public static APPEND_RESUME 	: string = 'R';
	public static APPEND_STORY 		: string = 'S';

	public static TIMELINE_FEED 	: string = 'T';
	public static STORY_FEED 		: string = 'S';

	public static SCHEDULED 	: string = 'O';
	public static STARTED 		: string = 'S';
	public static INTERRUPTED 	: string = 'I';
	public static FINALIZED 	: string = 'F';

	public idPost : number;
	
	public text : string;
	
	public formattedPost : string;

	public appendType : string;

	public posted : string = 'F';

	public postDate : Object;

	public scheduleDate : Object;

	public picture : string;
	
	public pictureHeight : number;

	public pictureWidth : number;
	
	public title : string;

	public link : string;

	public description : string;
	
	public videoName : string;
	
	public videoSize : number;
	
	public videoExtension : string;
	
	public videoDuration : number;
	
	public countPostingAttempts : number;
	
	public originalLink : string;
	
	public set : Object;

	public postsAttachments : PostAttachment[];

	public hashtags : string;	

	public profile : Profile;

	public targetFeed : string;

	public status : string;

	public startDate : string;	

	public timezone : Timezone;
}