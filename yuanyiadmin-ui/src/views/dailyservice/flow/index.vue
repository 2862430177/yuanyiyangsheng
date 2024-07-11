<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="150px">
      <el-form-item label="服务员工姓名或工号" prop="nameOrWorkno">
        <el-input
          v-model="queryParams.nameOrWorkno"
          placeholder="请输入服务员工姓名或工号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="服务设备id" prop="equipmentId">
        <el-input
          v-model="queryParams.equipmentId"
          placeholder="请输入服务设备id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="会员手机号" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          placeholder="请输入会员手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择服务状态" clearable>
          <el-option
            v-for="dict in dict.type.service_flow_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间"
          format="yyyy 年 MM 月 dd 日">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['dailyservice:flow:add']"
        >新建服务单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['dailyservice:flow:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['dailyservice:flow:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['dailyservice:flow:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="flowList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="服务流水id" align="center" prop="id" />
      <!-- <el-table-column label="服务员工id" align="center" prop="employeeId" /> -->
      <!-- <el-table-column label="服务设备id" align="center" prop="equipmentId" /> -->
      <!-- <el-table-column label="客户会员id" align="center" prop="customerId" /> -->
      <!-- <el-table-column label="本次服务使用会员卡id" align="center" prop="memberCardId" /> -->
      <el-table-column label="服务员工姓名" align="center" prop="employeeName" />
      <el-table-column label="服务设备名" align="center" prop="equipmentName" />
      <el-table-column label="会员名称" align="center" prop="customerName" />
      <el-table-column label="会员手机号" align="center" prop="phonenumber" />
      <el-table-column label="服务总收费" align="center" prop="serviceCharge" />
      <el-table-column label="服务状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.service_flow_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="medium"
            type="primary"
            icon="el-icon-edit"
            @click="handleSettle(scope.row)"
            v-if="scope.row.status==='0'"
          >结算</el-button>
          <el-button
            size="medium"
            type="primary"
            icon="el-icon-edit"
            @click="handleSettle(scope.row)"
            v-if="scope.row.status==='2'"
          >结算详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleReSettle(scope.row)"
            v-show="scope.row.status === '2'"
          >重新结算</el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['dailyservice:flow:edit']"
          >取消</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['dailyservice:flow:edit']"
          >结束</el-button> -->
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-if="scope.row.status==='0'"
            v-hasPermi="['dailyservice:flow:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改服务流水,一次服务一条流水记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="" prop="">
          <el-button
          type="success"
          round
          icon="el-icon-plus"
          size="medium "
          @click="handleChoose"
        >点击选择员工</el-button>
        <el-button
          type="success"
          round
          icon="el-icon-plus"
          size="medium "
          @click="handleChooseEquipment"
        >点击选择设备</el-button>
        </el-form-item>
        <el-form-item label="服务员工id" prop="employeeId" v-show="false">
          <el-input v-model="form.employeeId" />
        </el-form-item>
        <el-form-item label="服务设备id" prop="equipmentId" v-show="false">
          <el-input v-model="form.equipmentId" placeholder="请输入服务设备id" />
        </el-form-item>
        <!--
        <el-form-item label="客户会员id" prop="customerId">
          <el-input v-model="form.customerId" placeholder="请输入客户会员id" />
        </el-form-item>
        <el-form-item label="本次服务使用会员卡id" prop="memberCardId">
          <el-input v-model="form.memberCardId" placeholder="请输入本次服务使用会员卡id" />
        </el-form-item> -->
        <el-form-item label="服务员工姓名" prop="employeeName">
          <el-input v-model="form.employeeName" :readonly="true"/>
        </el-form-item>
        <el-form-item label="服务设备名" prop="equipmentName">
          <el-input v-model="form.equipmentName" :readonly="true"/>
        </el-form-item>
        <!-- <el-form-item label="客户会员名称" prop="customerName">
          <el-input v-model="form.customerName" />
        </el-form-item>
        <el-form-item label="客户会员手机号" prop="phonenumber">
          <el-input v-model="form.phonenumber"/>
        </el-form-item>
        <el-form-item label="服务总收费" prop="serviceCharge">
          <el-input v-model="form.serviceCharge" placeholder="请输入服务总收费" />
        </el-form-item> -->
        <!-- <el-form-item label="服务状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择服务状态">
            <el-option
              v-for="dict in dict.type.service_flow_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    
    <employee-choose ref="employeeChooseDialog" title="选择员工" @chooseOk="employeeChooseOk"></employee-choose>
    <equipment-choose ref="equipmentChooseDialog" title="选择设备" @chooseOk="equipmentChooseOk"></equipment-choose>
    <customer-choose ref="customerChooseDialog" title="选择会员" @chooseOk="customerChooseOk"></customer-choose>
    <service-items-choose ref="serviceItemsChooseDialog" title="选择项目" @chooseOk="serviceItemsChooseOk"></service-items-choose>
    <service-products-choose ref="serviceProductsChooseDialog" title="选择产品" @chooseOk="serviceProductsChooseOk"></service-products-choose>

    <!--结算页面-->
    <!-- :before-close="handleSettledrawerBeforeClose" -->
    <!--:wrapperClosable="false" 点击空白区域不关闭-->
    <el-drawer
      ref="settledrawer"
      title="结算页"
      :wrapperClosable="false"
      :visible.sync="settledrawerShow"
      :direction="direction"
      size="40%"
      custom-class="demo-drawer"
      @close="handleSettleDrawerClose"
      :before-close="handleSettledrawerBeforeClose"
      append-to-body>
      <div class="demo-drawer__content">
      <el-form ref="settleform" :model="settleform" :rules="rules" label-width="150px">
        <!-- <el-row :gutter="10" type="flex" class="row-bg" justify="start"> -->
        <el-row :gutter="20">
          <el-col :span="20" :offset="1">
            <el-button
            type="success"
            icon="el-icon-plus"
            size="medium"
            @click="handleCustomerChoose"
            >点击选择会员
            </el-button>
          </el-col>
        </el-row>
        <el-form-item label="服务员工id" prop="employeeId" v-show="false">
          <el-input v-model="settleform.employeeId"/>
        </el-form-item>
        <el-form-item label="服务设备id" prop="equipmentId" v-show="false">
          <el-input v-model="settleform.equipmentId" placeholder="请输入服务设备id" />
        </el-form-item>
        <el-form-item label="客户会员id" prop="customerId" v-show="false">
          <el-input v-model="settleform.customerId" placeholder="请输入客户会员id" />
        </el-form-item>
        <el-form-item label="本次服务使用会员卡id" prop="memberCardId" v-show="false">
          <el-input v-model="settleform.memberCardId" placeholder="请输入本次服务使用会员卡id" />
        </el-form-item>

        <el-form-item label="服务员工姓名" prop="employeeName">
          <el-input v-model="settleform.employeeName" :readonly="true"/>
        </el-form-item>
        <el-form-item label="服务设备名" prop="equipmentName">
          <el-input v-model="settleform.equipmentName" :readonly="true"/>
        </el-form-item>
        <el-form-item label="客户会员名称" prop="customerName">
          <el-input v-model="settleform.customerName" :readonly="true"/>
        </el-form-item>
        <el-form-item label="客户会员手机号" prop="phonenumber">
          <el-input v-model="settleform.phonenumber" :readonly="true"/>
        </el-form-item>
        
        
        <!-- <el-form-item label="服务状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择服务状态">
            <el-option
              v-for="dict in dict.type.service_flow_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="settleform.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">服务流水明细</el-divider>
        <el-row :gutter="20" class="mb8">
        <!-- <el-row :gutter="10" type="flex" class="row-bg" justify="start"> -->
          
          <el-col :span="1.5" :offset="1">
            <el-button type="success" icon="el-icon-plus" size="medium" @click="handleServiceItemsChoose">添加服务</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" icon="el-icon-plus" size="medium" @click="handleServiceProductsChoose">添加产品</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="medium" @click="handleDeleteBServiceFlowDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="bServiceFlowDetailList" :row-class-name="rowBServiceFlowDetailIndex" @selection-change="handleBServiceFlowDetailSelectionChange" 
        ref="bServiceFlowDetail">
          <el-table-column type="selection" align="center" />
          <el-table-column label="序号" align="center" prop="index"/>
          <el-table-column label="服务项类型" prop="detailType">
            <template slot-scope="scope">
              <el-select v-model="scope.row.detailType" placeholder="请选择服务项类型">
                <el-option
                  v-for="dict in dict.type.service_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="服务项内容" prop="detailName">
            <template slot-scope="scope">
              <el-input v-model="scope.row.detailName" :readonly="true" />
            </template>
          </el-table-column>
          <el-table-column label="服务项数量" prop="detailNum">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.detailNum" placeholder="请输入服务项数量" />
            </template>
          </el-table-column>
          <el-table-column label="服务项单价" prop="detailUnitPrice">
            <template slot-scope="scope">
              <el-input v-model="scope.row.detailUnitPrice" placeholder="请输入服务项单价" />
            </template>
          </el-table-column>
        </el-table>
        <el-form-item label="结算方式" prop="settlementType">
          <el-select v-model="settleform.settlementType" placeholder="请选择结算方式">
                <el-option
                  v-for="dict in dict.type.settlement_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="会员卡余额(元)" prop="remainingAmount" v-if="settleform.settlementType == '1'">
          <el-input v-model="settleform.remainingAmount" disabled />
        </el-form-item>
        <el-form-item label="服务总收费(元)" prop="serviceCharge">
          <el-input v-model="settleform.serviceCharge" placeholder="请输入服务总收费" />
        </el-form-item>
      </el-form>
      <div class="demo-drawer__footer" style="margin-left: 5%;">
        <el-button @click="cancelForm">取 消</el-button>
        <!--只有状态为服务中的服务单可以结算-->
        <el-button type="primary" @click="confirmSettle" :loading="loading" v-if="settleform.status==='0'">{{ loading ? '提交中 ...' : '确 定 结 算' }}</el-button>
        <!--只有状态为已结算的服务单可以重新结算-->
        <el-button type="primary" @click="confirmSettle" :loading="loading" v-if="(settleform.status==='2' && settleform.ifReSettle==1)">{{ loading ? '提交中 ...' : '确 定 重 新 结 算' }}</el-button>
      </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { listFlow, getFlow, delFlow, addFlow, updateFlow,settleFlow,reSettleFlow } from "@/api/dailyservice/flow";
import { getMembercardInfoByMemberinfo} from "@/api/member/membercard";
import employeeChoose from '@/views/employee/employee/employee_choose'
import equipmentChoose from '@/views/shop/equipment/equipment_choose'
import customerChoose from '@/views/member/customer/customer_choose'
import serviceItemsChoose from '@/views/serviceitems/serviceitems/serviceitems_choose'
import serviceProductsChoose from '@/views/serviceproducts/serviceproducts/products_choose'
export default {
  name: "Flow",
  dicts: ['service_flow_status', 'service_type','settlement_type'],
  components: {
    employeeChoose,
    equipmentChoose,
    customerChoose,
    serviceItemsChoose,
    serviceProductsChoose
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedBServiceFlowDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 服务流水,一次服务一条流水记录表格数据
      flowList: [],
      // 服务流水明细
      bServiceFlowDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否显示员工选择弹出层
      employeeChooseOpen:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        employeeId: null,
        equipmentId: null,
        customerId: null,
        memberCardId: null,
        serviceCharge: null,
        
        nameOrWorkno: null,
        phonenumber: null,
        status: '0',
        createTime: new Date()
      },
      // 表单参数
      form: {},
      //结算表单参数
      settleform:{
        employeeId: null,
        equipmentId: null,
        customerId: null,
        memberCardId: null,
        settlementType:'0',
        serviceCharge: null,

        employeeName:null,
        equipmentName:null,
        customerName:null,
        phonenumber:null,
        bServiceFlowDetailList:[],
        //是否重新结算
        ifReSettle:1,
      },
      // 表单校验
      rules: {
        employeeId: [
          { required: true, message: "服务员工不能为空", trigger: "blur" }
        ],
      },
      settledrawerShow: false,
      direction: 'rtl',
      multipleSelection: []
    };
  },
  created() {
    //默认展示服务状态为服务中的的服务
    this.queryParams.status = '0'
    this.queryParams.createTime = (new Date()).toISOString ().substr(0, 10) // 确保在组件挂载时是当前日期
    this.getList()
  },
  mounted() {
    
  },
  watch: {  
    bServiceFlowDetailList: {  
      handler(newVal, oldVal) {  
        // 表格数据已变化，可以在这里执行逻辑  
        console.log('Table data changed', newVal); 
        //重新计算服务总额
        this.recalculateServiceCharge()
      },  
      deep: true, // 深度监听对象内部属性的变化  
    },  
    'settleform.settlementType':{
      handler(newVal, oldVal) {  
        // 表格数据已变化，可以在这里执行逻辑  
        console.log('Table data changed', newVal); 
        // 如果是会员卡结算，并且已选择会员，重新加载会员余额
        if(newVal == '1'){
          if(this.settleform.customerId && this.settleform.phonenumber){
            let chooseRow = {}
            chooseRow.id = this.settleform.customerId
            chooseRow.phonenumber = this.settleform.phonenumber
            // console.log('reload',chooseRow); 
            this.loadMembercardInfo(chooseRow)
            this.$set(this.settleform,'remainingAmount',this.settleform.remainingAmount)
          }
        }
      },  
    }
  },
  methods: {
    /** 查询服务流水,一次服务一条流水记录列表 */
    getList() {
      this.loading = true;
      console.log("queryParams:",this.queryParams)
      listFlow(this.queryParams).then(response => {
        this.flowList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        employeeId: null,
        equipmentId: null,
        customerId: null,
        memberCardId: null,
        serviceCharge: null,
        status: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.bServiceFlowDetailList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.status = '0' //默认展示服务状态为服务中的的服务
      this.queryParams.createTime = new Date().toISOString ().substr(0, 10)// 默认为当前日期
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.multipleSelection = selection
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 员工选择按钮操作 */
    handleChoose() {
      this.employeeChooseOpen = true;
      this.$refs.employeeChooseDialog.show()
    },
    /** 员工选择回调函数 */
    employeeChooseOk(chooseRow){
      // console.log("chooseRow:",chooseRow)
      if(chooseRow){
        this.form.employeeId=chooseRow.id
        this.form.employeeName = chooseRow.employeeName
      }
    },
    /** 设备选择按钮操作 */
    handleChooseEquipment(){
      this.$refs.equipmentChooseDialog.show()
    },
    /** 设备选择回调函数 */
    equipmentChooseOk(chooseRow){
      // console.log("chooseRow:",chooseRow)
      if(chooseRow){
        this.form.equipmentId = chooseRow.id
        this.form.equipmentName = chooseRow.equipmentDesc
      }
    },
    /** 会员选择按钮操作 */
    handleCustomerChoose(){
      this.$refs.customerChooseDialog.show()
    },
    /** 会员选择回调函数 */
    customerChooseOk(chooseRow){
      console.log("chooseRow:",chooseRow)
      if(chooseRow){
        this.settleform.customerId = chooseRow.id
        this.settleform.customerName = chooseRow.customerName
        this.settleform.phonenumber = chooseRow.phonenumber
        //结算方式
        this.settleform.settlementType = '1'
        //会员卡信息自动加载
        this.loadMembercardInfo(chooseRow)
      }
    },
    //加载会员卡信息
    loadMembercardInfo(chooseRow){
      let query = {}
        query.customerId = chooseRow.id
        query.phonenumber = chooseRow.phonenumber
        getMembercardInfoByMemberinfo(query).then(response => {
          let membercardInfo = response.data;
          this.settleform.memberCardId = membercardInfo.id
          this.settleform.remainingAmount = membercardInfo.remainingAmount
          console.log("remainingAmount",this.settleform.remainingAmount)
        });
    },
    /** 服务选择按钮操作 */
    /** 点击添加服务项目 */
    handleServiceItemsChoose(){
      this.$refs.serviceItemsChooseDialog.show()
    },
    /** 服务选择回调函数 */
    serviceItemsChooseOk(chooseRow){
      console.log("chooseRow:",chooseRow)
      if(chooseRow){
        this.handleAddBServiceFlowDetail('0',chooseRow)
      }
    },
    /** 产品选择按钮操作 */
    /** 点击添加服务产品 */
    handleServiceProductsChoose(){
      this.$refs.serviceProductsChooseDialog.show()
    },
    /** 产品选择回调函数 */
    serviceProductsChooseOk(chooseRow){
      // console.log("chooseRow:",chooseRow)
      if(chooseRow){
        this.handleAddBServiceFlowDetail('1',chooseRow)
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新建服务单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      console.log("this.multipleSelection",this.multipleSelection)
      this.reset();
      const id = row.id || this.ids
      getFlow(id).then(response => {
        let data = response.data
        this.form = data;
        this.bServiceFlowDetailList = data.bServiceFlowDetailList;
        //员工
        // this.form.employeeId=row.id
        this.form.employeeName = row.employeeName || this.multipleSelection[0].employeeName
        //设备
        // this.form.equipmentId = row.id
        this.form.equipmentName = row.equipmentDesc || this.multipleSelection[0].equipmentName

        this.open = true;
        this.title = "修改服务单";
      });

    },
    /** 结算按钮操作 */
    handleSettle(row,title){
      
      console.log("row:",row)
      //结算
      this.settleform = Object.assign({}, row);
      //员工
      this.settleform.employeeId=row.employeeId
      this.settleform.employeeName = row.employeeName
      //设备
      this.settleform.equipmentId = row.equipmentId
      this.settleform.equipmentName = row.equipmentName || row.equipmentDesc
      //会员
      this.settleform.customerId = row.customerId
      this.settleform.customerName = row.customerName
      this.settleform.phonenumber = row.phonenumber
      //会员卡
      this.settleform.memberCardId = row.memberCardId
      //收费
      this.settleform.settlementType = row.settlementType||'0'
      this.settleform.serviceCharge = row.serviceCharge||0
      //状态
      this.settleform.status = row.status||'0'
      //是否重新结算
      this.settleform.ifReSettle = row.ifReSettle || 0
      //服务
      //产品
      this.reset();
      const id = row.id || this.ids
      getFlow(id).then(response => {
        let data = response.data
        // this.settleform = data;
        this.bServiceFlowDetailList = data.bServiceFlowDetailList || [];
        if(title){
          this.title= title
        }else{
          this.title= "结算页"
        }
        this.settledrawerShow = true
      });
    },
    /** 重新结算按钮操作 */
    handleReSettle(row){
      this.$confirm('确定要重新结算吗？')
        .then(_ => {
          row.ifReSettle = 1
          this.handleSettle(row,"重新结算页")
        })
        .catch(_ => {});
    },
    handleClose(done) {
      if (this.loading) {
        return;
      }
      this.$confirm('确定要提交表单吗？')
        .then(_ => {
          this.loading = true;
          this.timer = setTimeout(() => {
            done();
            // 动画关闭需要一定的时间
            setTimeout(() => {
              this.loading = false;
            }, 400);
          }, 2000);
        })
        .catch(_ => {});
    },
    /** 取消结算按钮 */
    cancelForm(){
      this.$refs.settledrawer.closeDrawer()
      // this.loading = false;
      // this.dialog = false;
      // clearTimeout(this.timer);
    },
    /** 确定结算按钮 */
    confirmSettle(){
      this.$refs["settleform"].validate(valid => {
        if (valid) {
          this.settleform.bServiceFlowDetailList = this.bServiceFlowDetailList;
          console.log("settleform",this.settleform)
          let that = this
          let ifReSettle = this.settleform.ifReSettle
          if(ifReSettle == 1){
            //重新结算
            reSettleFlow(this.settleform).then(response => {
            if(response && response.code == '200'){
              console.log("success:",response)
              that.$refs.settledrawer.closeDrawer()
              that.getList();
              that.$modal.msgSuccess("重新结算成功");
            }else{
              console.log("error:",response)
              that.$modal.msgError(response.msg);
            }
            });
          }else{
            settleFlow(this.settleform).then(response => {
            if(response && response.code == '200'){
              console.log("success:",response)
              // 关闭drawer
              // that.$confirm('结算成功').then(_ => {
              //   that.$refs.settledrawer.closeDrawer()
              // }).catch(_ => {});
              // that.settledrawerShow = false
              // that.$refs.settledrawer.close();
              that.$refs.settledrawer.closeDrawer()
              that.getList();
              that.$modal.msgSuccess("结算成功");
              // that.$nextTick(() => {
              //   // this.settledrawerShow = false
              //   that.$refs.settledrawer.closeDrawer()
              //   that.getList();
              // })
            }else{
              console.log("error:",response)
              that.$modal.msgError(response.msg);
            }
            });
          }
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.settleform.bServiceFlowDetailList = this.bServiceFlowDetailList;
          if (this.form.id != null) {
            updateFlow(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFlow(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 结算抽屉页面关闭时触发 */
    handleSettleDrawerClose(){
      //清空drawer数据
      this.settleform = {}
    },
    //结算抽屉页面关闭前触发
    handleSettledrawerBeforeClose(done) {
      done()
        // this.$confirm('确认关闭？')
        //   .then(_ => {
        //     done();
        //   })
        //   .catch(_ => {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除服务流水，编号为"' + ids + '"的数据项？').then(function() {
        return delFlow(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    
	/** 服务流水,一次服务一条流水记录序号 */
    rowBServiceFlowDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 服务流水,一次服务一条流水记录添加按钮操作 */
    handleAddBServiceFlowDetail(type,chooseRow) {
      if(chooseRow && chooseRow.length >0){
        chooseRow.forEach(element => {
          let obj = {};
          obj.detailType = type;
          obj.detailPkId = element.id;
          if(type === '0'){
            obj.detailName = element.itemName;
            obj.detailUnitPrice = element.itemChargeStandard;
          }else if(type ==='1'){
            obj.detailName = element.productName;
            obj.detailUnitPrice = element.productChargeStandard;
          }else{
            obj.detailName = '';
            obj.detailUnitPrice = 0;
          }
          obj.detailNum = 1;
          
          this.bServiceFlowDetailList.push(obj);
          //动态计算总金额
          // this.settleform.serviceCharge  = this.settleform.serviceCharge + (obj.detailNum)*(obj.detailUnitPrice||0)
        });
      }
    },
    /** 服务流水,一次服务一条流水记录删除按钮操作 */
    handleDeleteBServiceFlowDetail() {
      if (this.checkedBServiceFlowDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的服务单");
      } else {
        const bServiceFlowDetailList = this.bServiceFlowDetailList;
        const checkedBServiceFlowDetail = this.checkedBServiceFlowDetail;
        this.bServiceFlowDetailList = bServiceFlowDetailList.filter(function(item) {
          return checkedBServiceFlowDetail.indexOf(item.index) == -1
        });
        // this.bServiceFlowDetailList.forEach(element =>{
        //   //重新计算总金额
        //   this.settleform.serviceCharge  = (element.detailNum||1)*(element.detailUnitPrice||0)
        // })
      }
    },
    /** 重新计算服务总额 */
    recalculateServiceCharge(){
      this.settleform.serviceCharge = 0
      this.bServiceFlowDetailList.forEach(element =>{
          //重新计算总金额
          this.settleform.serviceCharge  = this.settleform.serviceCharge + (element.detailNum||1)*(element.detailUnitPrice||0)
        })
    },
    /** 复选框选中数据 */
    handleBServiceFlowDetailSelectionChange(selection) {
      this.checkedBServiceFlowDetail = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('dailyservice/flow/export', {
        ...this.queryParams
      }, `flow_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
