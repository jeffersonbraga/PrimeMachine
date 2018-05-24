import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

declare var d3;
declare var $;

@Component({
    selector: 'word-cloud',
    templateUrl: './word-cloud.component.html',
    styleUrls: ['./word-cloud.component.css']
})
export class WordCloudComponent implements OnInit {

    public static outer: any = [];

    htmlElement: HTMLElement; // Host HTMLElement
    host;

    @ViewChild('cloudContainer') cloudContainer;


    fontSize = d3.scale['sqrt']().range([10, 80]);
    fill = d3.scale.ordinal().range(['#00abf1']);

    w: number;
    h = 450;

    wordCloudSvg: any;
    wordCloudSvgVis: any;
    wordCloudLayout: any;

    constructor(private element: ElementRef) {
        this.htmlElement = this.element.nativeElement;
        this.host = d3.select(this.element.nativeElement);

        WordCloudComponent.outer['fontSize'] = this.fontSize;
        WordCloudComponent.outer['fill'] = this.fill;
    }

    ngOnInit() {
        window['generateCloud'] = this.generateCloud;
        const component = this;
        window.addEventListener('load', function () {
            this['generateCloud'](component);
        });
    }

    drawWordCloud(data, bounds) {
        const outer = WordCloudComponent.outer;
        outer.wordCloudSvg.attr('width', outer.w).attr('height', outer.h);

        const scale = bounds ? Math.min(
            outer.w / Math.abs(bounds[1].x - outer.w / 2),
            outer.w / Math.abs(bounds[0].x - outer.w / 2),
            outer.h / Math.abs(bounds[1].y - outer.h / 2),
            outer.h / Math.abs(bounds[0].y - outer.h / 2)) / 2 : 1;

        const text = outer.wordCloudSvgVis.selectAll('text')
            .data(data, function (d) {
                return d.text.toLowerCase();
            });
        text.transition()
            .duration(1000)
            .attr('transform', function (d) {
                return 'translate(' + [d.x, d.y] + ')rotate(' + d.rotate + ')';
            })
            .style('font-size', function (d) {
                return d.size + 'px';
            });

        text.enter().append('text')
            .attr('text-anchor', 'middle')
            .attr('transform', function (d) {
                return 'translate(' + [d.x, d.y] + ')rotate(' + d.rotate + ')';
            })
            .style('font-size', function (d) {
                return d.size + 'px';
            })
            .style('opacity', 1e-6)
            .transition()
            .duration(1000)
            .style('opacity', 1);
        text.style('font-family', function (d) {
            return d.font;
        })
            .style('fill', function (d) {
                return WordCloudComponent.outer.fill(d.text.toLowerCase());
            })
            .text(function (d) {
                return d.text;
            });

        outer.wordCloudSvgVis.transition().attr('transform', 'translate(' + [outer.w >> 1, outer.h >> 1] + ')scale(' + scale + ')');
    }

    generateCloud(component) {
        component.w = component.cloudContainer.nativeElement.offsetWidth;

        WordCloudComponent.outer['w'] = component.w;
        WordCloudComponent.outer['h'] = component.h;

        component.wordCloudSvg = component.host.append('svg')
            .attr('width', component.w)
            .attr('height', component.h);
        WordCloudComponent.outer['wordCloudSvg'] = component.wordCloudSvg;

        component.wordCloudSvgVis = component.wordCloudSvg.append('g')
            .attr('transform', 'translate(' + [component.w >> 1, component.h >> 1] + ')');
        WordCloudComponent.outer['wordCloudSvgVis'] = component.wordCloudSvgVis;

        if (component.tags.length) {
            component.fontSize.domain([1, 200]);
        }

        component.wordCloudLayout = d3.layout.cloud()
            .words(component.tags)
            .timeInterval(Infinity)
            .size([component.w, component.h])
            .fontSize(function (d) {
                return WordCloudComponent.outer.fontSize(Math.floor(Math.random() * 200) + 1);
                // return fontSize(+d.interactions);
            })
            .text(function (d) {
                return d.text;
            })
            .rotate(0)
            .font('impact').spiral('archimedean')
            .on('end', component.drawWordCloud)
            .start();
    }
}
