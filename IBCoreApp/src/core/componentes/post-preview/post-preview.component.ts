import { Component, DoCheck, Input } from '@angular/core';
import { KeyValueDiffers } from '@angular/core';
import { Post } from 'modelo/post/Post';

declare const $;

@Component({
    selector: 'post-preview',
    templateUrl: './post-preview.component.html',
    styleUrls: ['./post-preview.component.css']
})
export class PostPreviewComponent implements DoCheck {

    @Input('post')
    public post: Post = new Post();

    @Input() mychannels: any;

    data: any;
    images: string[] = [];

    differ: any;

    constructor(private differs: KeyValueDiffers) {
        this.differ = differs.find({}).create(null);
    }

    ngDoCheck() {
        // Verifição para se ter sempre um perfil selecionado
        const changes = this.differ.diff(this.mychannels);

        if (changes) {
            setTimeout(() => {
                const activeLis = $('#panel-post-preview li.active').length;

                if (!activeLis) {
                    $('#panel-post-preview li:first-child').addClass('active');
                    $('#panel-post-preview .tab-pane:first-child').addClass('active');
                }
            }, 100);
        }
    }
}
