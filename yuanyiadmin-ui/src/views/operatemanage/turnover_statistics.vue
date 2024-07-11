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

    <!--统计表格-->
        <el-table
            v-loading="loading"
                ref="table"
                :data="tableDatas"
                :span-method="arraySpanMethod"
                style="width: 100%;"
                border>
            <!-- <el-table-column fixed label="序号" type="index" :index="indexMethod" /> -->
            <el-table-column fixed label="日期" width="200" align="center" prop="tDate" >
                <!-- <template slot-scope="scope">
                    <span v-if="scope.row.name !== '合计'">{{newIndex(scope.$index)}}</span>
                    <span v-else class="f14 txt_primary fb">合 计：共 {{scope.row.uni_no}} 人</span> 
                </template>-->
            </el-table-column>
            <!--动态列 align:left/center/right-->
            <el-table-column
                    v-if="column_width>0"
                    :min-width="column_width"
                    v-for="(col,index) in tableColumns"
                    :prop="col.colPro"
                    :key="col.colKey"
                    :label="col.columName"
                    :show-overflow-tooltip="true"
                    :resizable="false"
                    :align="align"
            >
                <!-- <template slot-scope="scope">
                    <span v-if="col.columnType === 'A'">{{formatAmount(scope.row[col.colPro])}}</span>
                    <span v-else>{{scope.row[col.colPro]}}</span>
                </template> -->
            </el-table-column>
        </el-table>
</div>
</template>

<script>
import { listTurnoverStatistics,getDatesBetween } from "@/api/operatemanage/operatemanage";
export default {
    name: "turnoverStatistics",
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
            //动态表格列
            tableColumns: [{
                colPro:"",//列属性名，同后台返回属性名
                colKey:"",//列标识
                columName:null,//列名，日期
            }],
            column_width: 0
        }
    },
    created(){
        this.queryParams.startTime = new Date().toISOString ().substr(0, 10)// 默认为当前日期
        this.queryParams.endTime = new Date().toISOString ().substr(0, 10)// 默认为当前日期
        this.getList()
    },
    mounted(){
        
    },
    methods:{
        /** 查询服务项目管理列表 */
        getList() {
            this.loading = true;
            const startTime = this.queryParams.startTime
            const endTime = this.queryParams.endTime
            //获取开始日期到结束日期之间的所有日期
            const allDates = getDatesBetween(startTime,endTime)
            this.tableColumns = []
            let tableData = {}
            this.tableDatas = []
            for (const date of allDates) {
                let tableCloumn = {
                    colPro: date,
                    colKey: date,
                    columName: date,
                    columnType: 'B'
                }
                tableData[date] = "0.00"
                this.tableColumns.push(tableCloumn)
            }
            // console.log("tableColumns",this.tableColumns)
            this.column_width = this.tableColumns.length
            //普通的行
            const performanShowNames = ['项目金额(当日)','产品金额(当日)','总金额(当日)']
            const performanShowPros = ['itemAmount','productAmount','serviceCharge']
            //合并的行
            const performanMergeShowNames = ['项目金额汇总','产品金额汇总','总金额汇总']
            const performanMergeShowPros = ['totalItemAmount','totalProductAmount','totalServiceCharge']
           
            listTurnoverStatistics(this.queryParams).then(response => {
                console.log("response",response)
                const data = response.data;
                // for (let i=0;i<data.length;i++) {
                    //第一层
                    const totalItemAmount = data.totalItemAmount
                    const totalProductAmount = data.totalProductAmount
                    const totalServiceCharge = data.totalServiceCharge
                    //第二层
                    const map = data.dateResultVoMap
                    // console.log("map",map)
                    for(let j=0;j<performanShowNames.length;j++){
                        let tableData = {}
                        tableData.tDate = performanShowNames[j]
                        let pro = performanShowPros[j]
                        //日期列
                        for(let k=0;k< this.tableColumns.length;k++){
                            let tableColumn = this.tableColumns[k]
                            let date = tableColumn.colPro
                            let value = map[date]
                            if(value){
                                    tableData[date] = value[pro]
                                }else{
                                    tableData[date] = 0
                                }
                        }
                        this.tableDatas.push(tableData)
                    }
                    for(let j=0;j<performanMergeShowNames.length;j++){
                        let tableData = {}
                        tableData.tDate = performanMergeShowNames[j]
                        let pro = performanMergeShowPros[j]
                        //日期列
                        for(let k=0;k< this.tableColumns.length;k++){
                            let tableColumn = this.tableColumns[k]
                            let date = tableColumn.colPro
                            let value = map[date]
                            //如果是汇总行
                            if(pro == 'totalItemAmount'){
                                tableData[date] = totalItemAmount
                            }else if(pro == 'totalProductAmount' ){
                                tableData[date] = totalProductAmount
                            }else if(pro == 'totalServiceCharge'){
                                tableData[date] = totalServiceCharge
                            }
                        }
                        this.tableDatas.push(tableData)
                    }
                // }
                // console.log("tableDatas",this.tableDatas)
                this.loading = false;
         });
        },
        indexMethod(index) {
            // return index * 2;
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
            this.getList();
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
            // if (rowIndex === this.tableDatas.length-1) {
            //     if (columnIndex === 0) {
            //         return [1, 3];
            //     } else if (columnIndex === 1 || columnIndex === 2) {
            //         return [0, 0];
            //     }
            // }
            //第4.5.6行，合并列
            if(rowIndex === 3||rowIndex === 4||rowIndex === 5){
                if(columnIndex >0){
                    return {
                    rowspan: 1,
                    colspan: 60
                    };
                }
            }
        }
    }
}

</script>