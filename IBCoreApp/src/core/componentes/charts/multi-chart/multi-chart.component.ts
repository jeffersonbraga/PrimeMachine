import { Component, Input, OnInit } from '@angular/core';
import * as d3 from 'd3';

@Component({
    selector: 'multi-chart',
    templateUrl: './multi-chart.component.html',
    styleUrls: ['./multi-chart.component.css']
})
export class MultiChartComponent implements OnInit {

    options;

    @Input()
    data;

    @Input()
    heigth;

    @Input()
    legends;

    constructor() { }

    ngOnInit() {
        this.options = {
            chart: {
                type: 'multiChart',
                height: this.heigth ? this.heigth : 250,
                showLegend: this.legends ? this.legends.legends : false,
                staggerLabels: false,
                showValues: false,
                color: ['#20ffa3', '#f7554e'],
                margin: {
                    right: 45,
                    left: 60,
                    top: 15
                },
                legend: {
                    color: this.legends ? this.legends.color : [],
                    margin: {
                        top: 1,
                        left: 0,
                        rigth: 0,
                        bottom: 0
                    }
                },
                useInteractiveGuideline: false,
                showControls: false,
                xAxis: {
                    tickPadding: 15,
                    tickFormat: function (d) {
                        return d3.time.format('%x')(new Date(d));
                    }
                },
                yAxis: {
                    tickFormat: function (d) {
                        return d3.format('.02f')(d);
                    },
                    tickPadding: 15,
                    axisLabelDistance: -10
                },
                bars1: {
                    stacked: true
                },
                bars2: {
                    stacked: true
                }
            }
        };
    }
}
