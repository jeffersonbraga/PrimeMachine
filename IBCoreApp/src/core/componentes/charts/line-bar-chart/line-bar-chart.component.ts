import { Component, Input, OnInit } from '@angular/core';
import * as d3 from 'd3';

@Component({
    selector: 'line-bar-chart',
    templateUrl: './line-bar-chart.component.html',
    styleUrls: ['./line-bar-chart.component.css']
})
export class LineBarChartComponent implements OnInit {

    options;

    @Input()
    data;

    @Input()
    height;

    @Input()
    legends;

    ngOnInit() {
        this.options = {
            chart: {
                type: 'linePlusBarChart',
                height: this.height ? this.height : 250,
                showLegend: this.legends ? this.legends.legends : false,
                reduceXTicks: true,
                legend: {
                    color: this.legends ? this.legends.color : [],
                    margin: {
                        top: 1,
                        left: 0,
                        rigth: 0,
                        bottom: 0
                    }
                },
                staggerLabels: false,
                showValues: false,
                margin: {
                    top: 25
                },
                x: function (d) { return d.label; },
                y: function (d) { return d.value; },
                useInteractiveGuideline: false,
                showControls: false,
                xAxis: {
                    tickPadding: 15,
                },
                yAxis: {
                    tickFormat: function (d) {
                        return d3.format('.02f')(d);
                    },
                    tickPadding: 15,
                    axisLabelDistance: -10
                }
            }
        };
    }
}
