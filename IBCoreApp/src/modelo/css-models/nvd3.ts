export class Nvd3Model {
	public static NVD3MODEL = [
		{
			selector: '.nvd3 g.nv-groups path.nv-line',
			properties: {
				'stroke-width': '3px !important'
			}
		},
		{
			selector: '.nvd3 text',
			properties: {
				'font-family': '"Segoe UI", "Helvetica Neue", Helvetica, Arial, sans-serif!important',
				'font-size': '10px !important',
				'fill': '#9a9a9a !important'
			}
		},
		{
			selector: '.nvd3 .nv-axis .nv-axisMaxMin text',
			properties: {
				'font-weight': '400 !important'
			}
		},
		{
			selector: '.nvd3 .nv-axis .nv-axisMaxMin path',
			properties: {
				'stroke': 'transparent !important',
			}
		},
		{
			selector: '.nvd3 .nv-axis path.domain',
			properties: {
				'opacity': '0 !important',
			}
		},
		{
			selector: '.nvd3 .nv-background',
			properties: {
				'fill': 'transparent !important',
			}
		},
		{
			selector: '.line-chart .nvd3 line.nv-guideline',
			properties: {
				'stroke-width': '30px',
				'stroke-opacity': .04,
				'stroke': '#000',
			}
		},
		{
			selector: '.line-chart .nvd3 .nv-groups path.nv-area',
			properties: {
				'fill-opacity': .1,
			}
		},
		{
			selector: '.line-chart .nvd3 .nv-groups path.nv-line',
			properties: {
				'stroke-opacity': .3,
			}
		},
		{
			selector: '.line-chart .nvd3 .nv-axis line',
			properties: {
				'stroke-opacity': .5,
			}
		},
		{
			selector: '.line-chart[data-x-grid="false"] .nv-x .tick line',
			properties: {
				'display': 'none',
			}
		},
		{
			selector: '.line-chart[data-y-grid="false"] .nv-y .tick line',
			properties: {
				'display': 'none',
			}
		},
		{
			selector: '.line-chart .domain',
			properties: {
				opacity: 0,
			}
		},
		{
			selector: '.line-chart[data-points="true"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'fill-opacity': 1,
				'stroke-opacity': .5,
			}
		},
		{
			selector: '.line-chart .nvtooltip table td.legend-color-guide div',
			properties: {
				'border-radius': '100px',
			}
		},
		{
			selector: '.line-chart thead .nv-pointer-events-none strong',
			properties: {
				'color': '#afafaf',
			}
		},
		{
			selector: '.line-chart .nv-pointer-events-none',
			properties: {
				'font-size': '12px',
			}
		},
		{
			selector: '.line-chart .nv-pointer-events-none .value.nv-pointer-events-none',
			properties: {
				'font-family': 'Montserrat',
				'font-weight': 'normal',
				'font-size': '11px',
				'color': '#afafaf',
			}
		},
		{
			selector: '.line-chart .nvtooltip table',
			properties: {
				'margin': '12px 10px 14px 15px',
			}
		},
		{
			selector: '.line-chart .nvtooltip.xy-tooltip.nv-pointer-events-none',
			properties: {
				'border-color': 'rgba(0, 0, 0, 0.12)',
				'box-shadow': '0 0 6px rgba(0, 0, 0, 0.1)',
			}
		},
		{
			selector: '.line-chart[data-stroke-width="1"] .nvd3 .nv-groups path.nv-line',
			properties: {
				'stroke-width': '1px',
			}
		},
		{
			selector: '.line-chart[data-stroke-width="1"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'stroke-width': '2px',
			}
		},
		{
			selector: '.line-chart[data-stroke-width="2"] .nvd3 .nv-groups path.nv-line',
			properties: {
				'stroke-width': '2px',
			}
		},
		{
			selector: '.line-chart[data-stroke-width="2"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'stroke-width': '3px',
			}
		},
		{
			selector: '.line-chart[data-stroke-width="3"] .nvd3 .nv-groups path.nv-line',
			properties: {
				'stroke-width': '3px',
			}
		},
		{
			selector: '.line-chart[data-stroke-width="3"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'stroke-width': '4px',
			}
		},
		{
			selector: '.line-chart .tick text, .line-chart .nvd3 .nv-axis .nv-axisMaxMin text',
			properties: {
				'fill': 'rgba(98, 98, 98, 0.5)',
				'font-family': "Montserrat",
				'font-size': '11px',
				'text-transform': 'uppercase',
				'font-weight': 'normal',
			}
		},
		{
			selector: '.line-chart .nvd3.nv-scatter .nv-groups .nv-point.hover, .line-chart .nvd3 .nv-groups .nv-point.hover',
			properties: {
				'fill': 'inherit !important',
				'stroke': 'inherit !important',
			}
		},
		{
			selector: '.line-chart[data-line-color="master"] .nvd3 line.nv-guideline, .line-chart[data-line-color="master"] .nvd3 .nv-groups path.nv-line, .line-chart[data-line-color="master"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'stroke': '#626262',
			}
		},
		{
			selector: '.line-chart[data-line-color="success"] .nvd3 line.nv-guideline, .line-chart[data-line-color="success"] .nvd3 .nv-groups path.nv-line, .line-chart[data-line-color="success"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'stroke': '#10cfbd',
			}
		},
		{
			selector: '.line-chart[data-line-color="primary"] .nvd3 line.nv-guideline, .line-chart[data-line-color="primary"] .nvd3 .nv-groups path.nv-line, .line-chart[data-line-color="primary"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'stroke': '#00deff',
			}
		},
		{
			selector: '.line-chart[data-line-color="info"] .nvd3 line.nv-guideline, .line-chart[data-line-color="info"] .nvd3 .nv-groups path.nv-line, .line-chart[data-line-color="info"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'stroke': '#3b4752',
			}
		},
		{
			selector: '.line-chart[data-line-color="complete"] .nvd3 line.nv-guideline, .line-chart[data-line-color="complete"] .nvd3 .nv-groups path.nv-line, .line-chart[data-line-color="complete"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'stroke': '#48b0f7',
			}
		},
		{
			selector: '.line-chart[data-line-color="warning"] .nvd3 line.nv-guideline, .line-chart[data-line-color="warning"] .nvd3 .nv-groups path.nv-line, .line-chart[data-line-color="warning"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'stroke': '#f8d053',
			}
		},
		{
			selector: '.line-chart[data-line-color="danger"] .nvd3 line.nv-guideline, .line-chart[data-line-color="danger"] .nvd3 .nv-groups path.nv-line, .line-chart[data-line-color="danger"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'stroke': '#f55753',
			}
		},
		{
			selector: '.line-chart[data-line-color="white"] .nvd3 line.nv-guideline, .line-chart[data-line-color="white"] .nvd3 .nv-groups path.nv-line, .line-chart[data-line-color="white"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'stroke': '#fff',
			}
		},
		{
			selector: '.line-chart[data-line-color="black"] .nvd3 line.nv-guideline, .line-chart[data-line-color="black"] .nvd3 .nv-groups path.nv-line, .line-chart[data-line-color="black"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'stroke': '#000',
			}
		},
		{
			selector: '.line-chart[data-area-color="master"] .nvd3 .nv-groups path.nv-area',
			properties: {
				'fill': '#626262',
			}
		},
		{
			selector: '.line-chart[data-area-color="success"] .nvd3 .nv-groups path.nv-area',
			properties: {
				'fill': '#10cfbd',
			}
		},
		{
			selector: '.line-chart[data-area-color="info"] .nvd3 .nv-groups path.nv-area',
			properties: {
				'fill': '#3b4752',
			}
		},
		{
			selector: '.line-chart[data-area-color="complete"] .nvd3 .nv-groups path.nv-area',
			properties: {
				'fill': '#48b0f7',
			}
		},
		{
			selector: '.line-chart[data-area-color="primary"] .nvd3 .nv-groups path.nv-area',
			properties: {
				'fill': '#00deff',
			}
		},
		{
			selector: '.line-chart[data-area-color="warning"] .nvd3 .nv-groups path.nv-area',
			properties: {
				'fill': '#f8d053',
			}
		},
		{
			selector: '.line-chart[data-area-color="danger"] .nvd3 .nv-groups path.nv-area',
			properties: {
				'fill': '#f55753',
			}
		},
		{
			selector: '.line-chart[data-area-color="white"] .nvd3 .nv-groups path.nv-area',
			properties: {
				'fill': '#fff',
			}
		},
		{
			selector: '.line-chart[data-area-color="black"] .nvd3 .nv-groups path.nv-area',
			properties: {
				'fill': '#000',
			}
		},
		{
			selector: '.line-chart[data-point-color="master"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'fill': '#626262',
			}
		},
		{
			selector: '.line-chart[data-point-color="success"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'fill': '#10cfbd',
			}
		},
		{
			selector: '.line-chart[data-point-color="info"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'fill': '#3b4752',
			}
		},
		{
			selector: '.line-chart[data-point-color="complete"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'fill': '#48b0f7',
			}
		},
		{
			selector: '.line-chart[data-point-color="primary"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'fill': '#00deff',
			}
		},
		{
			selector: '.line-chart[data-point-color="warning"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'fill': '#f8d053',
			}
		},
		{
			selector: '.line-chart[data-point-color="danger"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'fill': '#f55753',
			}
		},
		{
			selector: '.line-chart[data-point-color="white"] .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'fill': '#fff',
			}
		},
		{
			selector: '#nvd3-line svg, #nvd3-line2 svg, #nvd3-area svg',
			properties: {
				'height': '500px',
			}
		},
		{
			selector: '.nvd3 .nv-axis line, .nvd3 .nv-axis path',
			properties: {
				'fill': 'none',
				'shape-rendering': 'crispEdges'
			}
		},
		{
			selector: '.nv-brush .extent, .nvd3 .background path, .nvd3 .nv-axis line, .nvd3 .nv-axis path',
			properties: {
				'shape-rendering': 'crispEdges'
			}
		},
		{
			selector: '.nv-distx, .nv-disty, .nv-noninteractive, .nvd3 .nv-axis, .nvd3.nv-pie .nv-label, .nvd3.nv-sparklineplus g.nv-hoverValue',
			properties: {
				'pointer-events': 'none'
			}
		},
		{
			selector: '.nvtooltip, svg.nvd3-svg',
			properties: {
				'display': 'block',
				'-webkit-touch-callout': 'none',
				'-khtml-user-select': 'none'
			}
		},
		{
			selector: '.nvd3 .nv-axis',
			properties: {
				'opacity': 1
			}
		},
		{
			selector: '.nvd3 .nv-axis.nv-disabled, .nvd3 .nv-controlsWrap .nv-legend .nv-check-box .nv-check',
			properties: {
				'opacity': 0
			}
		},
		{
			selector: '.nvd3 .nv-axis path',
			properties: {
				'stroke': '#000',
				'stroke-opacity': .75
			}
		},
		{
			selector: '.nvd3 .nv-axis path.domain',
			properties: {
				'stroke-opacity': .75
			}
		},
		{
			selector: '.nvd3 .nv-axis.nv-x path.domain',
			properties: {
				'stroke-opacity': 0
			}
		},
		{
			selector: '.nvd3 .nv-axis line',
			properties: {
				'stroke': '#e5e5e5'
			}
		},
		{
			selector: '.nvd3 .nv-axis .zero line, .nvd3 .nv-axis line.zero',
			properties: {
				'stroke-opacity': .75
			}
		},
		{
			selector: '.nvd3 .nv-axis .nv-axisMaxMin text',
			properties: {
				'font-weight': 700
			}
		},
		{
			selector: '.nvd3 .x .nv-axis .nv-axisMaxMin text, .nvd3 .x2 .nv-axis .nv-axisMaxMin text, .nvd3 .x3 .nv-axis .nv-axisMaxMin text',
			properties: {
				'text-anchor': 'middle'
			}
		},
		{
			selector: '.nvd3 .nv-bars rect',
			properties: {
				'fill-opacity': .75,
				'transition': 'fill-opacity 250ms linear',
				'-moz-transition': 'fill-opacity 250ms linear',
				'-webkit-transition': 'fill-opacity 250ms linear'
			}
		},
		{
			selector: '.nvd3 .nv-bars rect.hover',
			properties: {
				'fill-opacity': 1
			}
		},
		{
			selector: '.nvd3 .nv-bars .hover rect',
			properties: {
				'fill': '#add8e6'
			}
		},
		{
			selector: '.nvd3 .nv-bars text',
			properties: {
				'fill': 'transparent'
			}
		},
		{
			selector: '.nvd3 .nv-bars .hover text',
			properties: {
				'fill': 'rgba(0, 0, 0, 1)'
			}
		},
		{
			selector: '.nvd3 .nv-discretebar .nv-groups rect, .nvd3 .nv-multibar .nv-groups rect, .nvd3 .nv-multibarHorizontal .nv-groups rect',
			properties: {
				'stroke-opacity': 0,
				'transition': 'fill-opacity 250ms linear',
				'-moz-transition': 'fill-opacity 250ms linear',
				'-webkit-transition': 'fill-opacity 250ms linear'
			}
		},
		{
			selector: '.nvd3 .nv-candlestickBar .nv-ticks rect:hover, .nvd3 .nv-discretebar .nv-groups rect:hover, .nvd3 .nv-multibar .nv-groups rect:hover, .nvd3 .nv-multibarHorizontal .nv-groups rect:hover',
			properties: {
				'fill-opacity': 1
			}
		},
		{
			selector: '.nvd3 .nv-discretebar .nv-groups text, .nvd3 .nv-multibarHorizontal .nv-groups text',
			properties: {
				'font-weight': 700,
				'fill': 'rgba(0, 0, 0, 1)',
				'stroke': 'transparent'
			}
		},
		{
			selector: '.nvd3 .nv-boxplot circle',
			properties: {
				'fill-opacity': .5
			}
		},
		{
			selector: '.nvd3 .nv-boxplot circle:hover, .nvd3 .nv-boxplot rect:hover',
			properties: {
				'fill-opacity': 1
			}
		},
		{
			selector: '.nvd3 line.nv-boxplot-median',
			properties: {
				'stroke': '#000'
			}
		},
		{
			selector: '.nv-boxplot-tick:hover',
			properties: {
				'stroke-width': '2.5px'
			}
		},
		{
			selector: '.nvd3.nv-bullet',
			properties: {
				'font': '10px sans-serif'
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-measure',
			properties: {
				'fill-opacity': .8
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-measure:hover',
			properties: {
				'fill-opacity': 1
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-marker',
			properties: {
				'stroke': '#000',
				'stroke-width': '2px'
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-markerTriangle',
			properties: {
				'stroke': '#000',
				'fill': '#fff',
				'stroke-width': '1.5px'
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-markerLine',
			properties: {
				'stroke': '#000',
				'stroke-width': '1.5px'
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-tick line',
			properties: {
				'stroke': '#666',
				'stroke-width': '.5px'
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-range.nv-s0',
			properties: {
				'fill': '#eee'
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-range.nv-s1',
			properties: {
				'fill': '#ddd'
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-range.nv-s2',
			properties: {
				'fill': '#ccc'
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-title',
			properties: {
				'font-size': '14px',
				'font-weight': 700
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-subtitle',
			properties: {
				'fill': '#999'
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-range',
			properties: {
				'fill': '#bababa',
				'fill-opacity': .4
			}
		},
		{
			selector: '.nvd3.nv-bullet .nv-range:hover',
			properties: {
				'fill-opacity': .7
			}
		},
		{
			selector: '.nvd3.nv-candlestickBar .nv-ticks .nv-tick',
			properties: {
				'stroke-width': '1px'
			}
		},
		{
			selector: '.nvd3.nv-candlestickBar .nv-ticks .nv-tick.hover',
			properties: {
				'stroke-width': '2px'
			}
		},
		{
			selector: '.nvd3.nv-candlestickBar .nv-ticks .nv-tick.positive rect',
			properties: {
				'stroke': '#2ca02c',
				'fill': '#2ca02c'
			}
		},
		{
			selector: '.nvd3.nv-candlestickBar .nv-ticks .nv-tick.negative rect',
			properties: {
				'stroke': '#d62728',
				'fill': '#d62728'
			}
		},
		{
			selector: '.with-transitions .nv-candlestickBar .nv-ticks .nv-tick',
			properties: {
				'transition': 'stroke-width 250ms linear, stroke-opacity 250ms linear',
				'-moz-transition': 'stroke-width 250ms linear, stroke-opacity 250ms linear',
				'-webkit-transition': 'stroke-width 250ms linear, stroke-opacity 250ms linear'
			}
		},
		{
			selector: '.nvd3.nv-candlestickBar .nv-ticks line',
			properties: {
				'stroke': '#333'
			}
		},
		{
			selector: '.nv-force-node',
			properties: {
				'stroke': '#fff',
				'stroke-width': '1.5px'
			}
		},
		{
			selector: '.nv-force-link',
			properties: {
				'stroke': '#999',
				'stroke-opacity': .6
			}
		},
		{
			selector: '.nv-force-node text',
			properties: {
				'stroke-width': 0
			}
		},
		{
			selector: '.nvd3 .nv-check-box .nv-box',
			properties: {
				'fill-opacity': 0,
				'stroke-width': 2
			}
		},
		{
			selector: '.nvd3 .nv-check-box .nv-check',
			properties: {
				'fill-opacity': 0,
				'stroke-width': 4
			}
		},
		{
			selector: '.nvd3 .nv-series.nv-disabled .nv-check-box .nv-check',
			properties: {
				'fill-opacity': 0,
				'stroke-opacity': 0
			}
		},
		{
			selector: '.nvd3.nv-linePlusBar .nv-bar rect',
			properties: {
				'fill-opacity': .75
			}
		},
		{
			selector: '.nvd3.nv-linePlusBar .nv-bar rect:hover',
			properties: {
				'fill-opacity': 1
			}
		},
		{
			selector: '.nvd3 .nv-groups path.nv-line',
			properties: {
				'fill': 'none'
			}
		},
		{
			selector: '.nvd3 .nv-groups path.nv-area',
			properties: {
				'stroke': 'none'
			}
		},
		{
			selector: '.nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point',
			properties: {
				'fill-opacity': 0,
				'stroke-opacity': 0
			}
		},
		{
			selector: '.nvd3.nv-scatter.nv-single-point .nv-groups .nv-point',
			properties: {
				'fill-opacity': '.5!important',
				'stroke-opacity': '.5!important'
			}
		},
		{
			selector: '.with-transitions .nvd3 .nv-groups .nv-point',
			properties: {
				'transition': 'stroke-width 250ms linear, stroke-opacity 250ms linear',
				'-moz-transition': 'stroke-width 250ms linear, stroke-opacity 250ms linear',
				'-webkit-transition': 'stroke-width 250ms linear, stroke-opacity 250ms linear'
			}
		},
		{
			selector: '.nvd3 .nv-groups .nv-point.hover, .nvd3.nv-scatter .nv-groups .nv-point.hover',
			properties: {
				'stroke-width': '7px',
				'fill-opacity': '.95!important',
				'stroke-opacity': '.95!important'
			}
		},
		{
			selector: '.nvd3 .nv-point-paths path',
			properties: {
				'stroke': '#aaa',
				'stroke-opacity': 0,
				'fill': '#eee',
				'fill-opacity': 0
			}
		},
		{
			selector: '.nvd3 .nv-indexLine',
			properties: {
				'cursor': 'ew-resize'
			}
		},
		{
			selector: 'svg.nvd3-svg',
			properties: {
				'-webkit-user-select': 'none',
				'-ms-user-select': 'none',
				'-moz-user-select': 'none',
				'user-select': 'none',
				'width': '100%',
				'fill': 'transparent !important'
			}
		},
		{
			selector: '.nvtooltip.with-3d-shadow, .with-3d-shadow .nvtooltip',
			properties: {
				'-moz-box-shadow': '0 5px 10px rgba(0, 0, 0, .2)',
				'-webkit-box-shadow': '0 5px 10px rgba(0, 0, 0, .2)',
				'box-shadow': '0 5px 10px rgba(0, 0, 0, .2)',
				'-webkit-border-radius': '5px',
				'-moz-border-radius': '5px',
				'border-radius': '5px'
			}
		},
		{
			selector: '.nvd3 text',
			properties: {
				'font': '400 12px Arial'
			}
		},
		{
			selector: '.nvd3 .title',
			properties: {
				'font': '700 14px Arial'
			}
		},
		{
			selector: '.nvd3 .nv-background',
			properties: {
				'fill': '#fff',
				'fill-opacity': 0
			}
		},
		{
			selector: '.nvd3.nv-noData',
			properties: {
				'font-size': '14px',
				'font-weight': 400
			}
		},
		{
			selector: '.nv-brush .extent',
			properties: {
				'fill-opacity': .125
			}
		},
		{
			selector: '.nv-brush .resize path',
			properties: {
				'fill': '#eee',
				'stroke': '#666'
			}
		},
		{
			selector: '.nvd3 .nv-legend .nv-series',
			properties: {
				'cursor': 'pointer'
			}
		},
		{
			selector: '.nvd3 .nv-legend .nv-disabled circle',
			properties: {
				'fill-opacity': 0
			}
		},
		{
			selector: '.nvd3 .nv-brush .extent',
			properties: {
				'fill-opacity': '0!important'
			}
		},
		{
			selector: '.nvd3 .nv-brushBackground rect',
			properties: {
				'stroke': '#000',
				'stroke-width': .4,
				'fill': '#fff',
				'fill-opacity': .7
			}
		},
		{
			selector: '.nvd3 text',
			properties: {
				'stroke-width': 0,
				'fill-opacity': 1
			}
		},
		{
			selector: '.nvd3.nv-ohlcBar .nv-ticks .nv-tick',
			properties: {
				'stroke-width': '1px'
			}
		},
		{
			selector: '.nvd3.nv-ohlcBar .nv-ticks .nv-tick.hover',
			properties: {
				'stroke-width': '2px'
			}
		},
		{
			selector: '.nvd3.nv-ohlcBar .nv-ticks .nv-tick.positive',
			properties: {
				'stroke': '#2ca02c'
			}
		},
		{
			selector: '.nvd3.nv-ohlcBar .nv-ticks .nv-tick.negative',
			properties: {
				'stroke': '#d62728'
			}
		},
		{
			selector: '.nvd3 .background path',
			properties: {
				'fill': 'none',
				'stroke': '#EEE',
				'stroke-opacity': .4
			}
		},
		{
			selector: '.nvd3 .foreground path',
			properties: {
				'fill': 'none',
				'stroke-opacity': .7
			}
		},
		{
			selector: '.nvd3 .nv-parallelCoordinates-brush .extent',
			properties: {
				'fill': '#fff',
				'fill-opacity': .6,
				'stroke': 'gray',
				'shape-rendering': 'crispEdges'
			}
		},
		{
			selector: '.nvd3 .nv-parallelCoordinates .hover',
			properties: {
				'fill-opacity': 1,
				'stroke-width': '3px'
			}
		},
		{
			selector: '.nvd3 .missingValuesline line',
			properties: {
				'fill': 'none',
				'stroke': '#000',
				'stroke-width': 1,
				'stroke-opacity': 1,
				'stroke-dasharray': '5, 5'
			}
		},
		{
			selector: '.nvd3.nv-pie .nv-pie-title',
			properties: {
				'font-size': '24px',
				'fill': 'rgba(19, 196, 249, .59)'
			}
		},
		{
			selector: '.nvd3.nv-pie .nv-slice text',
			properties: {
				'stroke': '#000',
				'stroke-width': 0
			}
		},
		{
			selector: '.nvd3.nv-pie path',
			properties: {
				'transition': 'fill-opacity 250ms linear, stroke-width 250ms linear, stroke-opacity 250ms linear',
				'-moz-transition': 'fill-opacity 250ms linear, stroke-width 250ms linear, stroke-opacity 250ms linear',
				'-webkit-transition': 'fill-opacity 250ms linear, stroke-width 250ms linear, stroke-opacity 250ms linear',
				'stroke': '#fff',
				'stroke-width': '1px',
				'stroke-opacity': 1,
				'fill-opacity': .7
			}
		},
		{
			selector: '.nvd3.nv-pie .hover path',
			properties: {
				'fill-opacity': 1
			}
		},
		{
			selector: '.nvd3.nv-pie .nv-label rect',
			properties: {
				'fill-opacity': 0,
				'stroke-opacity': 0
			}
		},
		{
			selector: '.nvd3 .nv-groups .nv-point.hover',
			properties: {
				'stroke-width': '20px',
				'stroke-opacity': .5
			}
		},
		{
			selector: '.nvd3 .nv-scatter .nv-point.hover',
			properties: {
				'fill-opacity': 1
			}
		},
		{
			selector: '.nvd3.nv-sparkline path',
			properties: {
				'fill': 'none'
			}
		},
		{
			selector: '.nvd3.nv-sparklineplus .nv-hoverValue line',
			properties: {
				'stroke': '#333',
				'stroke-width': '1.5px'
			}
		},
		{
			selector: '.nvd3.nv-sparklineplus, .nvd3.nv-sparklineplus g',
			properties: {
				'pointer-events': 'all'
			}
		},
		{
			selector: '.nvd3 .nv-interactiveGuideLine, .nvtooltip',
			properties: {
				'pointer-events': 'none'
			}
		},
		{
			selector: '.nvd3 .nv-hoverArea',
			properties: {
				'fill-opacity': 0,
				'stroke-opacity': 0
			}
		},
		{
			selector: '.nvd3.nv-sparklineplus .nv-xValue, .nvd3.nv-sparklineplus .nv-yValue',
			properties: {
				'stroke-width': 0,
				'font-size': '.9em',
				'font-weight': 400
			}
		},
		{
			selector: '.nvd3.nv-sparklineplus .nv-yValue',
			properties: {
				'stroke': '#f66'
			}
		},
		{
			selector: '.nvd3.nv-sparklineplus .nv-maxValue',
			properties: {
				'stroke': '#2ca02c',
				'fill': '#2ca02c'
			}
		},
		{
			selector: '.nvd3.nv-sparklineplus .nv-minValue',
			properties: {
				'stroke': '#d62728',
				'fill': '#d62728'
			}
		},
		{
			selector: '.nvd3.nv-sparklineplus .nv-currentValue',
			properties: {
				'font-weight': 700,
				'font-size': '1.1em'
			}
		},
		{
			selector: '.nvtooltip h3, .nvtooltip table td.key',
			properties: {
				'font-weight': 400
			}
		},
		{
			selector: '.nvd3.nv-stackedarea path.nv-area',
			properties: {
				'fill-opacity': .7,
				'stroke-opacity': 0,
				'transition': 'fill-opacity 250ms linear, stroke-opacity 250ms linear',
				'-moz-transition': 'fill-opacity 250ms linear, stroke-opacity 250ms linear',
				'-webkit-transition': 'fill-opacity 250ms linear, stroke-opacity 250ms linear'
			}
		},
		{
			selector: '.nvd3.nv-stackedarea path.nv-area.hover',
			properties: {
				'fill-opacity': .9
			}
		},
		{
			selector: '.nvd3.nv-stackedarea .nv-groups .nv-point',
			properties: {
				'stroke-opacity': 0,
				'fill-opacity': 0
			}
		},
		{
			selector: '.nvtooltip',
			properties: {
				'position': 'absolute',
				'color': 'rgba(0, 0, 0, 1)',
				'padding': '1px',
				'z-index': 10000,
				'font-family': 'Arial',
				'font-size': '13px',
				'text-align': 'left',
				'white-space': 'nowrap',
				'-webkit-user-select': 'none',
				'-moz-user-select': 'none',
				'-ms-user-select': 'none',
				'user-select': 'none',
				'background': 'rgba(255, 255, 255, .8)',
				'border': '1px solid rgba(0, 0, 0, .5)',
				'border-radius': '4px'
			}
		},
		{
			selector: '.nvtooltip h3, .nvtooltip p',
			properties: {
				'margin': 0,
				'text-align': 'center'
			}
		},
		{
			selector: '.nvtooltip.with-transitions, .with-transitions .nvtooltip',
			properties: {
				'transition': 'opacity 50ms linear',
				'-moz-transition': 'opacity 50ms linear',
				'-webkit-transition': 'opacity 50ms linear',
				'transition-delay': '200ms',
				'-moz-transition-delay': '200ms',
				'-webkit-transition-delay': '200ms'
			}
		},
		{
			selector: '.nvtooltip.x-nvtooltip, .nvtooltip.y-nvtooltip',
			properties: {
				'padding': '8px'
			}
		},
		{
			selector: '.nvtooltip h3',
			properties: {
				'padding': '4px 14px',
				'line-height': '18px',
				'background-color': 'rgba(247, 247, 247, .75)',
				'color': 'rgba(0, 0, 0, 1)',
				'border-bottom': '1px solid #ebebeb',
				'-webkit-border-radius': '5px 5px 0 0',
				'-moz-border-radius': '5px 5px 0 0',
				'border-radius': '5px 5px 0 0'
			}
		},
		{
			selector: '.nvtooltip p',
			properties: {
				'padding': '5px 14px'
			}
		},
		{
			selector: '.nvtooltip span',
			properties: {
				'display': 'inline-block',
				'margin': '2px 0'
			}
		},
		{
			selector: '.nvtooltip table',
			properties: {
				'margin': '6px',
				'border-spacing': 0
			}
		},
		{
			selector: '.nvtooltip table td',
			properties: {
				'padding': '2px 9px 2px 0',
				'vertical-align': 'middle'
			}
		},
		{
			selector: '.nvtooltip table td.key.total',
			properties: {
				'font-weight': 700
			}
		},
		{
			selector: '.nvtooltip table td.value',
			properties: {
				'text-align': 'right',
				'font-weight': 700
			}
		},
		{
			selector: '.nvtooltip table td.percent',
			properties: {
				'color': '#a9a9a9'
			}
		},
		{
			selector: '.nvtooltip table tr.highlight td',
			properties: {
				'padding': '1px 9px 1px 0',
				'border-bottom-style': 'solid',
				'border-bottom-width': '1px',
				'border-top-style': 'solid',
				'border-top-width': '1px'
			}
		},
		{
			selector: '.nvtooltip table td.legend-color-guide div',
			properties: {
				'vertical-align': 'middle',
				'width': '12px',
				'height': '12px',
				'border': '1px solid #999'
			}
		},
		{
			selector: '.nvtooltip .footer',
			properties: {
				'padding': '3px',
				'text-align': 'center'
			}
		},
		{
			selector: '.nvtooltip-pending-removal',
			properties: {
				'pointer-events': 'none',
				'display': 'none'
			}
		},
		{
			selector: '.nvd3 line.nv-guideline',
			properties: {
				'stroke': '#ccc'
			}
		}
	]
}