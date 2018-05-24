import { Component, Input, OnInit } from '@angular/core';
import * as d3 from 'd3';

@Component({
    selector: 'area-chart',
    templateUrl: './area-chart.component.html',
    styleUrls: ['./area-chart.component.css']
})
export class AreaChartComponent implements OnInit {

    options;

    @Input()
    data;

    @Input()
    isArea: boolean;

    @Input()
    height: number;

    @Input()
    legends;

    ngOnInit() {
        this.options = {
            chart: {
                type: 'lineChart',
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
                isArea: this.isArea,
                margin: {
                    top: 25
                },
                x: function (d) { return d.x; },
                y: function (d) { return d.y; },
                useInteractiveGuideline: false,
                xAxis: {
                    tickPadding: 15,
                    tickFormat: function (d) { return d3.time.format('%m/%d')(new Date(d)); }
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
