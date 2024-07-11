<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="150px">
            <el-form-item label="开始时间" prop="startTime">
                <el-date-picker clearable
                v-model="queryParams.startTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择开始时间"
                format="yyyy 年 MM 月 dd 日">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="结束时间" prop="endTime">
                <el-date-picker clearable
                v-model="queryParams.endTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择开始时间"
                format="yyyy 年 MM 月 dd 日">
                </el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 为 vchart 准备一个具备大小（宽高）的 DOM，当然你也可以在 spec 配置中指定 -->
        <div id="chart" style="width: 600px;height:400px;"></div>
    </div>
</template>

<script>
import { VChart } from '@visactor/vchart';
import { listProductsDimensionalStatistics } from "@/api/operatemanage/operatemanage";
let spec = {
    data: [
        {
            id: 'barData',
            values: []
        }
    ],
    type: 'bar',
    xField: 'productName',
    yField: 'sales'
};
// 创建 vchart 实例
/**
* 说明：cdn 方式引入的时候，VChart 的引用方式需要注意：
* const vchart = new VChart.default(spec, { dom: 'chart' });
*/
let vchart;
export default {
    name: "Item-dimensionalStatistics",
    data() {
        return{
            // 遮罩层
            loading: true,
            // 显示搜索条件
            showSearch: true,
            // 查询参数
            queryParams: {
                startTime:null,
                endTime:null
            },
            align: 'left',
            //表格数据
            tableDatas: [],
        }
    },
    created(){
        this.queryParams.startTime = new Date().toISOString ().substr(0, 10)// 默认为当前日期
        this.queryParams.endTime = new Date().toISOString ().substr(0, 10)// 默认为当前日期
    },
    mounted(){
        this.drawBar()
        this.getBarChartData()
    },
    watch:{
        tableDatas: {  
            handler(newVal, oldVal) {  
                // 表格数据已变化，可以在这里执行逻辑  
                console.log('Table data changed', newVal); 
                //重新绘制图表
                vchart.updateDataSync('barData',newVal)
                // setTimeout(()=>{
                //     vchart.updateDataSync('barData',newVal)
                // },2000)
            },  
            deep: true, // 深度监听对象内部属性的变化  
        },  
    },
    methods:{
        /** 获取柱状图数据 */
        getBarChartData() {
            this.loading = true;
            const startTime = this.queryParams.startTime
            const endTime = this.queryParams.endTime
            listProductsDimensionalStatistics(this.queryParams).then(response => {
                const data = response.data;
                console.log("data",data)
                if(data && data.length>0){
                    this.tableDatas = data
                }
                this.loading = false;
         });
        },
        /**绘制柱状图 */
        drawBar(){
            vchart = new VChart(spec, { dom: 'chart' });
            // 绘制
            vchart.renderSync();
        },
        indexMethod(index) {
            // return index * 2;
            return "总金额";
        },
        // 取消按钮
        cancel() {
            this.open = false;
            this.reset();
        },
        // 表单重置
        reset() {
            this.form = {};
            this.bServiceFlowDetailList = [];
            this.resetForm("form");
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.getBarChartData();
        },
        /** 重置按钮操作 */
        resetQuery() {
            this.resetForm("queryForm");
            this.queryParams.startTime = new Date().toISOString ().substr(0, 10)// 默认为当前日期
            this.queryParams.endTime = new Date().toISOString ().substr(0, 10)// 默认为当前日期
            this.handleQuery();
        },
        // 合计行自定义合并单元格
        arraySpanMethod({ row, column, rowIndex, columnIndex }) {
            if (rowIndex === this.tableDatas.length-1) {
                if (columnIndex === 0) {
                    return [1, 3];
                } else if (columnIndex === 1 || columnIndex === 2) {
                    return [0, 0];
                }
            }
        }
    }
}

</script>