import { Injectable } from '@angular/core';

declare var $: any;

@Injectable()
export class BindMarksService {
    bind() {
        setTimeout(() => {
            const self = this;
            $('.mentionMark').unbind('click');
            $('.mentionMark').bind('click', function() {
                const network = $(this).data('network'),
                    type = $(this).data('type'),
                    href = $(this).data('href');

                const link = self.getLink(network, type) + href;
                window.open(link);
            });
        }, 100);
    };

    getLink(network, type): string {
        let link: string;
        if (network === 'F' || network === 'P') {
            if (type === 'h') {
                link = 'https:\/\/www.facebook.com/hashtag/';
            } else if (type === 'm') {
                link = 'https:\/\/www.facebook.com/';
            };
        } else if (network === 'T') {
            if (type === 'h') {
                link = 'https:\/\/www.twitter.com/hashtag/';
            } else if (type === 'm') {
                link = 'https:\/\/twitter.com/';
            };
        } else if (network === 'I') {
            if (type === 'h') {
                link = 'https:\/\/www.instagram.com/explore/tags/';
            } else if (type === 'm') {
                link = 'https:\/\/instagram.com/';
            };
        }
        return link;
    }
}
