import { RssFeedMessage } from './RssFeedMessage';

export class RssFeed {
    
	idRssfeed : number;

    url : string;

    title : string;
	
    link : string;
	
    description : string;
	
    language : string;
	
    copyright : string;
	
    pubDate : string;
	
	rssFeedMessage : RssFeedMessage[];
}