import { Component, Input, OnInit } from '@angular/core';
import * as d3 from 'd3';

@Component({
    selector: 'column-chart',
    templateUrl: './column-chart.component.html',
    styleUrls: ['./column-chart.component.css']
})
export class ColumnChartComponent implements OnInit {

    options;

    @Input()
    data;

    @Input()
    height: number;

    @Input()
    legends;

    @Input()
    rotateLabels;

    ngOnInit() {
        this.options = {
            chart: {
                type: 'discreteBarChart',
                height: this.height ? this.height : 250,
                showLegend: this.legends ? this.legends.legends : false,
                reduceXTicks: true,
                staggerLabels: false,
                showValues: false,
                stacked: false,
                x: function (d) { return d.label; },
                y: function (d) { return d.value; },
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
                    // right: 45,
                    // left: 60,
                    top: 25
                },
                useInteractiveGuideline: false,
                showControls: false,
                xAxis: {
                    tickPadding: 15,
                    rotateLabels: this.rotateLabels ? this.rotateLabels : 0
                },
                yAxis: {
                    tickFormat: d3.format('d'),
                    tickPadding: 15,
                    axisLabelDistance: -10
                }
            }
        };
    }
}
