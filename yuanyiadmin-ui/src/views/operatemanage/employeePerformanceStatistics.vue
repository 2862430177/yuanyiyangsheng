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
                :span-method="objectSpanMethod"
                style="width: 100%;"
                border>
            <el-table-column fixed label="序号" type="index" :index="indexMethod" />
            <el-table-column fixed label="员工姓名" width="200" align="center" prop="employeeName"/>
            <el-table-column fixed label="统计指标|日期" width="200" align="center" prop="tDate">
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
import { listEmployeePerformanceStatistics,getDatesBetween } from "@/api/operatemanage/operatemanage";
export default {
    name: "employeePerformanceStatistics",
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
            this.tableDatas = []
            for (const date of allDates) {
                let tableCloumn = {
                    colPro: date,
                    colKey: date,
                    columName: date,
                    columnType: 'B'
                }
                this.tableColumns.push(tableCloumn)
            }
            console.log("tableColumns",this.tableColumns)
            this.column_width = this.tableColumns.length
            const performanShowNames = ['项目金额','产品金额','总金额=项目金额+产品金额','实际消费金额','会员办卡提成','项目提成','产品提成','提成总计','提成合计']
            const performanShowPros = ['itemAmount','productAmount','totalAmount','actualServiceCharge','cardCommissionAmount','itemCommissionAmount','productCommissionAmount','commissionTotalAmount','totalPerformanceAmount']
            const showNamesSize = performanShowNames.size;
            listEmployeePerformanceStatistics(this.queryParams).then(response => {
                console.log("response",response)
                const data = response.data;
                for (let i=0;i<data.length;i++) {
                    //第一层
                    const existDate = data[i]
                    // console.log("existDate",existDate)
                    //第二层
                    const map = existDate.performanceindicatorResultVoMap
                    // console.log("map",map)
                    for(let j=0;j<performanShowNames.length;j++){
                        let tableData = {}
                        tableData.employeeName = existDate.employeeName
                        tableData.tDate = performanShowNames[j]
                        //日期列
                        for(let k=0;k< this.tableColumns.length;k++){
                            let tableColumn = this.tableColumns[k]
                            let date = tableColumn.colPro
                            let value = map[date]
                            //如果是最后一个总计
                            if(performanShowPros[j] == 'totalPerformanceAmount'){
                                tableData[date] = existDate.totalPerformanceAmount
                            }else{
                                if(value){
                                    tableData[date] = value[performanShowPros[j]]
                                }else{
                                    tableData[date] = 0
                                }
                            }
                        }
                        this.tableDatas.push(tableData)
                    }
                }
                // this.tableData = response.rows;
                // this.total = response.total;
                this.loading = false;
         });
        },
        indexMethod(index) {
            // console.log("index",index)
            // return index * 2;
            if(index == 0){
                return 1;
            }else{
                return (index+1)%9 +1;
            }
            // return "总金额";
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
        // 合并列
        // 合计行自定义合并单元格
        arraySpanMethod({ row, column, rowIndex, columnIndex }) {
            if (rowIndex === this.tableDatas.length-1) {
                if (columnIndex === 0) {
                    return [1, 3];
                } else if (columnIndex === 1 || columnIndex === 2) {
                    return [0, 0];
                }
            }
        },
        // 合并行
        objectSpanMethod({ row, column, rowIndex, columnIndex }) {
            //第一列
            if (columnIndex === 0||columnIndex === 1) {
                if (rowIndex % 9 === 0) {
                    return {
                    rowspan: 9,
                    colspan: 1
                    };
                } else {
                    return {
                    rowspan: 0,
                    colspan: 0
                    };
                }
            }
            //每合计行
            if((rowIndex+1)%9===0){
                if(columnIndex === 3){
                    return {
                    rowspan: 1,
                    colspan: 30
                    };
                }
            }
        }
    }
}

</script>