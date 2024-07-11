<template>
  <div class="app-container">
    <el-dialog title="选择员工" :visible.sync="employeeChooseOpen" width="50%" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="员工姓名" prop="employeeName">
        <el-input
          v-model="queryParams.employeeName"
          placeholder="请输入员工姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="员工昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入员工昵称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="工号(唯一)" prop="jobid">
        <el-input
          v-model="queryParams.jobid"
          placeholder="请输入工号(唯一)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="员工技能等级" prop="skillLevel">
        <el-input
          v-model="queryParams.skillLevel"
          placeholder="请输入员工技能等级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="员工手机号码" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          placeholder="请输入员工手机号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!--单选：highlight-current-row-->
    <!--多选选中事件 @selection-change="handleSelectionChange"-->
    <el-table v-loading="loading" :data="employeeList" 
    ref="singleTable"
    highlight-current-row
    style="width: 100%"
    @current-change="handleCurrentChange"
    >
      <el-table-column type="index" width="50"></el-table-column>
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="员工id" align="center" prop="id" />
      <el-table-column label="员工姓名" align="center" prop="employeeName" />
      <el-table-column label="员工昵称" align="center" prop="nickName" />
      <el-table-column label="工号(唯一)" align="center" prop="jobid" />
      <el-table-column label="员工手机号码" align="center" prop="phonenumber" />
      <el-table-column label="员工性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column label="员工技能等级" align="center" prop="skillLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.skill_level" :value="scope.row.skillLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="员工工作状态" align="center" prop="workingStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.working_status" :value="scope.row.workingStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="员工在职状态" align="center" prop="employmentStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.employment_status" :value="scope.row.employmentStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmChoose">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listEmployee, getEmployee, delEmployee, addEmployee, updateEmployee } from "@/api/employee/employee";

export default {
  name: "EmployeeChoose",
  dicts: ['skill_level', 'employment_status', 'sys_user_sex', 'working_status'],
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
      // 员工信息管理表格数据
      employeeList: [],
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
        employeeName: null,
        nickName: null,
        jobid: null,
        skillLevel: null,
        phonenumber: null,
        sex: null,
        workingStatus: null,
        employmentStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        employeeName: [
          { required: true, message: "员工姓名不能为空", trigger: "blur" }
        ],
      },
      currentRow: null
    };
  },
  created() {
  },
  methods: {
    //外部调用显示
    show(){
      this.employeeChooseOpen = true
      this.getList()
    },
    /** 查询员工信息管理列表 */
    getList() {
      this.loading = true;
      listEmployee(this.queryParams).then(response => {
        this.employeeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.employeeChooseOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        employeeName: null,
        nickName: null,
        jobid: null,
        skillLevel: null,
        phonenumber: null,
        sex: null,
        workingStatus: null,
        employmentStatus: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    //单选框
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
    },
    handleCurrentChange(val) {
      this.currentRow = val;
    },
    /** 确认选择 */
    confirmChoose(){
      this.$emit('chooseOk', this.currentRow)
      this.employeeChooseOpen = false
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加员工信息管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEmployee(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工信息管理";
      });
    },
    
  }
};
</script>
