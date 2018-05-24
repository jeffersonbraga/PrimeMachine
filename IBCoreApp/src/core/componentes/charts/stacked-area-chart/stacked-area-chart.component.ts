import { Component, Input, OnInit } from '@angular/core';
import * as d3 from 'd3';

@Component({
    selector: 'stacked-area-chart',
    templateUrl: './stacked-area-chart.component.html',
    styleUrls: ['./stacked-area-chart.component.css']
})
export class StackedAreaChartComponent implements OnInit {

    options;

    @Input()
    data;

    @Input()
    height;

    @Input()
    legends;

    @Input()
    format: string;

    @Input()
    color;

    @Input()
    tickValues;

    @Input()
    rotateLabels;

    @Input()
    isLogScale;

    @Input()
    forceY;

    @Input()
    tickValuesY;

    ngOnInit() {
        const component = this;

        this.options = {
            chart: {
                type: 'stackedAreaChart',
                height: this.height ? this.height : 250,
                showLegend: this.legends ? this.legends.legends : false,
                reduceXTicks: true,
                staggerLabels: false,
                color: this.color ? this.color : ['#00d6f6', '#00aed9'],
                legend: {
                    color: this.legends ? this.legends.color : [],
                    margin: {
                        top: 1,
                        left: 0,
                        rigth: 0,
                        bottom: 0
                    }
                },
                margin: {
                    top: 25
                },
                x: function (d) { return d[0]; },
                y: function (d) { return d[1]; },
                yScale: this.isLogScale ? d3.scale.log() : d3.scale.linear(),
                forceY: this.forceY ? this.forceY : null,
                useInteractiveGuideline: false,
                showControls: false,
                xAxis: {
                    tickPadding: 15,
                    tickFormat: function (d) {
                        return d3.time.format(component.format ? component.format : '%m/%d')(new Date(d));
                    },
                    showMaxMin: false,
                    tickValues: this.tickValues ? this.tickValues : null,
                    rotateLabels: this.rotateLabels ? this.rotateLabels : 0
                },
                yAxis: {
                    tickFormat: d3.format('d'),
                    tickPadding: 15,
                    axisLabelDistance: -10,
                    tickValues: this.tickValuesY ? this.tickValuesY : null,
                    showMaxMin: true,
                }
            }
        };
    }
}
