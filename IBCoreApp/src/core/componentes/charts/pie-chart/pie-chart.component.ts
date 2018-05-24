import { Component, Input, OnInit, ViewChild } from '@angular/core';
import * as d3 from 'd3';

@Component({
    selector: 'pie-chart',
    templateUrl: './pie-chart.component.html',
    styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit {

    @ViewChild('pieChart')
    chart: any;

    options;

    @Input()
    data;

    @Input()
    height;

    @Input()
    width;

    @Input()
    isDonut: boolean;

    @Input()
    legends;

    ngOnInit() {
        this.options = {
            chart: {
                type: 'pieChart',
                height: this.height ? this.height : 250,
                width: this.width ? this.width : null,
                showLegend: this.legends ? this.legends.legends : false,
                legend: {
                    color: this.legends ? this.legends.color : [],
                    margin: {
                        top: 1,
                        left: 0,
                        rigth: 0,
                        bottom: 0
                    }
                },
                showLabels: false,
                margin: {
                    right: 0,
                    left: 0,
                    top: 0,
                    bottom: 0
                },
                x: function (d) { return d.label; },
                y: function (d) { return d.value; },
                labelType: 'value',
                useInteractiveGuideline: false,
                donut: this.isDonut,
                donutRatio: 0.4,
                duration: 0,
                xAxis: {
                    // tickPadding: 15,
                },
                yAxis: {
                    tickFormat: function (d) {
                        return d3.format('.02f')(d);
                    },
                    // tickPadding: 15,
                    axisLabelDistance: -10
                }
            }
        };
    }
}
