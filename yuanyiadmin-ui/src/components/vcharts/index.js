import Vue from 'vue'
// 引入 VChart 核心模块
import { VChart } from '@visactor/vchart';
// // 引入柱状图
// import { registerBarChart } from '@visactor/vchart';
// // 引入坐标轴、Tooltip、CrossHair组件
// import { registerTooltip, registerCartesianCrossHair, registerDomTooltipHandler } from '@visactor/vchart';

// // 注册图表和组件
// VChart.useRegisters([
//     registerBarChart, 
//     registerTooltip, 
//     registerDomTooltipHandler, 
//     registerCartesianCrossHair
// ]);
Vue.use(VChart)

export default {
}