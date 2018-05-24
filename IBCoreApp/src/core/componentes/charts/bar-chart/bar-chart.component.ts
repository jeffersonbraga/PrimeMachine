import { Component, Input, OnInit } from '@angular/core';

@Component({
    selector: 'bar-chart',
    templateUrl: './bar-chart.component.html',
    styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit {

    options;

    @Input()
    data;

    @Input()
    height;

    @Input()
    isStacked: boolean;

    @Input()
    legends;

    ngOnInit() {
        this.options = {
            chart: {
                type: 'multiBarHorizontalChart',
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
                stacked: this.isStacked,
                margin: {
                    top: 0,
                    bottom: 0,
                    left: 36,
                    right: 0
                },
                x: function (d) { return d.label; },
                y: function (d) { return d.value; },
                useInteractiveGuideline: false,
                showControls: false,
                xAxis: {
                    tickPadding: 8,
                },
                showYAxis: false,
                groupSpacing: 0.25
            }
        };
    }
}
