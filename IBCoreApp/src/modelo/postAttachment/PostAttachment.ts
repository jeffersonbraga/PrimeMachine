import { Post } from 'modelo/post/Post';

export class PostAttachment {

	public static PHOTO : string = 'P';

	public static VIDEO : string = 'V';
    
	public idPostAttachment : number;

	public post : Post;
	
    public fileName : string;
    
    public fileType : string; 

    public height : number;
    
    public width : number;

    public size : number;
    
    public extension : string;
    
    public duration : number;
        
    public uploadId : string;
    
    public videoThumbnail : string;

    public index : number;

    thumbnails: any;

    public version: number;
}