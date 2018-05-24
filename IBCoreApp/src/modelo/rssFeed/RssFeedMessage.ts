import { RssFeed } from './RssFeed';

export class RssFeedMessage {
    
	idRssfeedMsg : number;
	
	rssFeed : RssFeed;

    messageTitle : string;
	
    messageDescription : string;
	
    messageContent : string;
	
    messageLink : string;
	
    messageAuthor : string;
	
    messageGuid : string;
	
    messagePubDate : string;

    messageImgURL : string;

    groupName : string;
}