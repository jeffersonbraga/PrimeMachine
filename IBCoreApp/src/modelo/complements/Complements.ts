export interface IAsideTextIcon {
	icon?: {
        icon: string,
        color: string
    },
    title?: string,
    value: string,
    text?: string, 
    smallText?: string,
    iconUrl?: string
    hasGrown?: boolean
}

export interface IAsidePieChart{
    icon: {
        icon: string,
        color: string
    },
    chart: {
        data: any,
        height: number,
        width?: number,
        isDonut: boolean,
        legend: Object[]
    }
    title: string,
    text?: string,
    orientation: string
}

export interface IAsideSmallLineChart {
    icon: {
        icon: string,
        color: string
    },
    chart: {
        data: any,
        height: number
    }
    title: string,
    value: number,
    smallText?: string,
    hasGrown: boolean
}

export interface IAsidePieChartDouble {
    icon?: {
        icon: string,
        color: string
    },
    chart: {
        data: any,
        height: number,
        width?: number,
        isDonut: boolean
    }
    title?: string,
    leftContent: {
        title: string,
        value: number,
        smallText?: string,
        valueColor?: string
    },
    rightContent: {
        title: string,
        value: number,
        smallText?: string,
        valueColor?: string
    }
}

export interface IAsideProgressBar {
    title: string,
    smallText: string,
    value: number,
    hasGrown: boolean,
    leftContent: {
        title: string,
        value: number,
        percentage: number,
        smallText: string,
        color: string
    },
    rightContent: {
        title: string,
        value: number,
        percentage: number,
        smallText: string,
        color: string
    }
}