<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <!-- <el-form-item label="所属会员id" prop="customerId">
        <el-input
          v-model="queryParams.customerId"
          placeholder="请输入所属会员id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="卡号" prop="cardno">
        <el-input
          v-model="queryParams.cardno"
          placeholder="请输入卡号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="卡密" prop="cardSecret">
        <el-input
          v-model="queryParams.cardSecret"
          placeholder="请输入卡密"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="会员手机号码" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          placeholder="请输入手机号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="推荐员工工号或姓名" prop="recommendEmployeeNameOrNo">
        <el-input
          v-model="queryParams.recommendEmployeeNameOrNo"
          placeholder="请输入推荐员工工号或姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['member:membercard:add']"
        >新增</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          :disabled="single"
          @click="recharge"
        >会员卡充值</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['member:membercard:edit']"
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
          v-hasPermi="['member:membercard:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['member:membercard:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="membercardList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="会员卡id" align="center" prop="id" />
      <el-table-column label="会员手机号" align="center" prop="phonenumber" />
      
      <!-- <el-table-column label="所属会员id" align="center" prop="customerId" /> -->
      <el-table-column label="卡号" align="center" prop="cardno" />
      <!-- <el-table-column label="卡密" align="center" prop="cardSecret" /> -->
      <el-table-column label="首次充值金额(元)" align="center" prop="initAmount" />
      <el-table-column label="剩余金额(元)" align="center" prop="remainingAmount" />
      <!-- <el-table-column label="会员卡等级" align="center" prop="level" /> -->
      <!-- <el-table-column label="会员卡状态" align="center" prop="status" /> -->
      <el-table-column label="上次充值时间" align="center" prop="lastRechargeTime" width="180">
        <template slot-scope="scope">
          <!-- 默认带时分秒 '{y}-{m}-{d} {h}:{i}:{s}' -->
          <span>{{ parseTime(scope.row.lastRechargeTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上次消费时间" align="center" prop="lastCostTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastCostTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <!-- <el-table-column label="推荐员工" align="center" prop="recommendEmployeeId" /> -->
      <!-- <el-table-column label="推荐会员" align="center" prop="recommendCustomerId" /> -->
      <el-table-column label="推荐员工" align="center" prop="recommendEmployeeName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['member:membercard:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['member:membercard:remove']"
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

    <employee-choose ref="employeeChooseDialog" title="选择员工" @chooseOk="employeeChooseOk"></employee-choose>

    <!-- 添加或修改会员卡信息管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <!-- <el-form-item label="所属会员id" prop="customerId">
          <el-input v-model="form.customerId" placeholder="请输入所属会员id" />
        </el-form-item> -->
        <!-- <el-form-item label="卡号" prop="cardno">
          <el-input v-model="form.cardno" placeholder="请输入卡号" />
        </el-form-item> -->
        <el-form-item label="卡密" prop="cardSecret">
          <el-input v-model="form.cardSecret" placeholder="请输入卡密" />
        </el-form-item>
        <el-form-item label="首次充值金额" prop="initAmount">
          <el-input v-model="form.initAmount" placeholder="请输入首次充值金额" />
        </el-form-item>
        <!-- <el-form-item label="剩余金额" prop="remainingAmount">
          <el-input v-model="form.remainingAmount" placeholder="请输入剩余金额" />
        </el-form-item> -->
        <!-- <el-form-item label="会员卡等级" prop="level">
          <el-input v-model="form.level" placeholder="请输入会员卡等级" />
        </el-form-item> -->
        <!-- <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
        </el-form-item> -->
        <!-- <el-form-item label="上次充值时间" prop="lastRechargeTime">
          <el-date-picker clearable
            v-model="form.lastRechargeTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择上次充值时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上次消费时间" prop="lastCostTime">
          <el-date-picker clearable
            v-model="form.lastCostTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择上次消费时间">
          </el-date-picker>
        </el-form-item> -->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="推荐员工" prop="recommendEmployeeId">
          <el-button
          type="success"
          round
          icon="el-icon-plus"
          size="medium "
          @click="handleChoose"
        >点击选择员工</el-button>
          <el-input v-model="form.recommendEmployeeId" v-show="false"/>
        </el-form-item>
        <el-form-item label="推荐员工姓名" prop="recommendEmployeeName">
            <el-input v-model="form.recommendEmployeeName"/>
        </el-form-item>
        <!-- <el-form-item label="推荐会员" prop="recommendCustomerId">
          <el-input v-model="form.recommendCustomerId" placeholder="请输入推荐人" />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    
    <!-- 会员卡充值对话框 -->
    <el-dialog :title="title" :visible.sync="rechargeopen" width="500px" append-to-body>
      <el-form ref="rechargeForm" :model="rechargeForm" :rules="rules" label-width="100px">
        
        <el-form-item label="会员手机号" prop="phonenumber">
          <el-input v-model="rechargeForm.phonenumber" :readonly="true"/>
        </el-form-item>
        <el-form-item label="卡密" prop="cardSecret">
          <el-input v-model="rechargeForm.cardSecret" :readonly="true"/>
        </el-form-item>
        <el-form-item label="当前余额(元)" prop="remainingAmount">
          <el-input v-model="rechargeForm.remainingAmount" :readonly="true"/>
        </el-form-item>
        <el-form-item label="充值金额(元)" prop="rechargeAmount">
          <el-input-number v-model="rechargeForm.rechargeAmount" :min="1" placeholder="请输入本次充值金额（元）" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRecharge">确 定</el-button>
        <el-button @click="cancelRecharge">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMembercard, getMembercard, delMembercard, addMembercard, updateMembercard,getMembercardInfo,rechargeMembercard } from "@/api/member/membercard";
import employeeChoose from '@/views/employee/employee/employee_choose'
export default {
  name: "Membercard",
  components: {
    employeeChoose
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 会员卡信息管理表格数据
      membercardList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否显示员工选择弹出层
      employeeChooseOpen:false,
      //是否显示充值弹出层
      rechargeopen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customerId: null,
        phonenumber: null,
        ifCreateCard: false,
        cardno: null,
        cardSecret: null,
        initAmount: null,
        remainingAmount: null,
        level: null,
        status: null,
        lastRechargeTime: null,
        lastCostTime: null,
      },
      // 表单参数
      form: {
        recommendEmployeeId:null,
        recommendEmployeeName:null,
      },
      //充值表单参数
      rechargeForm: {},
      // 表单校验
      rules: {
        // customerId: [
        //   { required: true, message: "所属会员id不能为空", trigger: "blur" }
        // ],
        // cardno: [
        //   { required: true, message: "卡号不能为空", trigger: "blur" }
        // ],
        // cardSecret: [
        //   { required: true, message: "卡密不能为空", trigger: "blur" }
        // ],
        // initAmount: [
        //   { required: true, message: "初始金额，首次充值金额不能为空", trigger: "blur" }
        // ],
        // remainingAmount: [
        //   { required: true, message: "剩余金额，消费结算减少剩余金额，充值增加剩余金额不能为空", trigger: "blur" }
        // ],
      }
    };
  },
  created() {
    //从会员信息页面点击会员卡信息传递过来的参数
    let params = this.$route.query;
    console.log("params:",params)
    if(params){
      let customerid = params.customerid
      let phonenumber = params.phonenumber
      let ifCreateCard = params.ifCreateCard
      //手机号
      this.queryParams.phonenumber = phonenumber
      //如果查不到会员卡是否创建
      this.queryParams.ifCreateCard = ifCreateCard
    }
    this.getList();
  },
  methods: {
    /** 查询会员卡信息管理列表 */
    getList() {
      this.loading = true;
      listMembercard(this.queryParams).then(response => {
        this.membercardList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      this.resetQueryParam()
    },
    resetQueryParam(){
      this.queryParams.ifCreateCard = false
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消充值按钮
    cancelRecharge(){
      this.rechargeopen = false;
      this.rechargeFormReset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        customerId: null,
        cardno: null,
        cardSecret: null,
        initAmount: null,
        remainingAmount: null,
        level: null,
        status: null,
        delFlag: null,
        lastRechargeTime: null,
        lastCostTime: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    //充值表单重置
    rechargeFormReset(){
      this.rechargeForm = {
        id: null,
        customerId: null,
        cardno: null,
        cardSecret: null,
        remainingAmount: null,
        rechargeAmount: null,
        phonenumber: null
      };
      this.resetForm("rechargeForm");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 会员卡充值按钮操作 */
    recharge(row){
      this.reset();
      const id = row.id || this.ids
      getMembercardInfo(id).then(response => {
        this.rechargeForm = response.data;
        this.rechargeopen = true;
        this.title = "会员卡充值";
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加会员卡信息管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMembercardInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改会员卡信息管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMembercard(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMembercard(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交充值按钮 */
    submitRecharge() {
      this.$refs["rechargeForm"].validate(valid => {
        if (valid) {
          // this.form.id
          rechargeMembercard(this.rechargeForm).then(response => {
              this.$modal.msgSuccess("充值成功");
              this.rechargeopen = false;
              this.getList();
          });
        }
      });
    },
    /** 员工选择按钮操作 */
    handleChoose() {
      this.employeeChooseOpen = true;
      this.$refs.employeeChooseDialog.show()
    },
    /** 员工选择回调函数 */
    employeeChooseOk(chooseRow){
      if(chooseRow){
        this.form.recommendEmployeeId=chooseRow.id
        this.form.recommendEmployeeName = chooseRow.employeeName
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除会员卡信息管理编号为"' + ids + '"的数据项？').then(function() {
        return delMembercard(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('member/membercard/export', {
        ...this.queryParams
      }, `membercard_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
