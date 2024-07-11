<template>
  <div class="app-container">
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['employee:employee:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['employee:employee:edit']"
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
          v-hasPermi="['employee:employee:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['employee:employee:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info "
          plain
          icon="el-icon-plus"
          size="small"
          @click="handleCommissionCardSetting"
          v-hasPermi="['employee:employee:edit']"
        >会员卡提成设置</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="employeeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['employee:employee:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['employee:employee:remove']"
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

    <!-- 添加或修改员工信息管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="员工姓名" prop="employeeName">
          <el-input v-model="form.employeeName" placeholder="请输入员工姓名" />
        </el-form-item>
        <el-form-item label="员工昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入员工昵称" />
        </el-form-item>
        <el-form-item label="工号(唯一)" prop="jobid">
          <el-input v-model="form.jobid" placeholder="请输入工号(唯一)" />
        </el-form-item>
        <el-form-item label="员工手机号码" prop="phonenumber">
          <el-input v-model="form.phonenumber" placeholder="请输入员工手机号码" />
        </el-form-item>
        <el-form-item label="员工性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择员工性别">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="员工技能等级" prop="skillLevel">
          <el-select v-model="form.skillLevel" placeholder="请选择员工技能等级">
            <el-option
              v-for="dict in dict.type.skill_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="员工工作状态" prop="workingStatus">
          <el-select v-model="form.workingStatus" placeholder="请选择员工工作状态">
            <el-option
              v-for="dict in dict.type.working_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="员工在职状态" prop="employmentStatus">
          <el-select v-model="form.employmentStatus" placeholder="请选择员工在职状态">
            <el-option
              v-for="dict in dict.type.employment_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
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

    <!-- 员工会员卡提成设置 -->
    <el-dialog :title="title" :visible.sync="commissionCardSettingOpen" width="50%" append-to-body>
      <el-form ref="commissionCardSettingForm" :model="commissionCardSettingForm" :rules="rules" label-width="40%">
        <el-form-item label="固定提成金额(元)" prop="fixedAmount">
          <el-input-number v-model="commissionCardSettingForm.fixedAmount" placeholder="请输入固定提成金额(元)" />
        </el-form-item>
        <el-form-item label="首次办卡提成比例(百分比)" prop="firstMemebercard">
          <el-input-number v-model="commissionCardSettingForm.firstMemebercard" placeholder="首次办卡提成比例(百分比)" />
        </el-form-item>
        <el-form-item label="续卡提成比例(百分比)" prop="continueMemebercard">
          <el-input-number v-model="commissionCardSettingForm.continueMemebercard" placeholder="续卡提成比例(百分比)" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="commissionCardSettingConfirm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listEmployee, getEmployee, delEmployee, addEmployee, updateEmployee } from "@/api/employee/employee";
import { getConfigKey, updateConfigByKey } from "@/api/system/config";
export default {
  name: "Employee",
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
      // 是否显示会员卡提成设置弹出层
      commissionCardSettingOpen : false,
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
      // 固定提成金额设置表单参数
      commissionCardSettingForm: {
        fixedAmount: 0.00,
        firstMemebercard : 0.00,
        continueMemebercard: 0.00
      },
      // 表单校验
      rules: {
        employeeName: [
          { required: true, message: "员工姓名不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
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
      this.open = false;
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
    /** 会员卡提成设置 */
    handleCommissionCardSetting(){
      const configKey = "commission:membercard:employee"
      getConfigKey(configKey).then(response => {
        let data = response.msg;
        let jsonObj = JSON.parse(data);

        this.commissionCardSettingForm.fixedAmount = jsonObj.commission_fixed_amount
        this.commissionCardSettingForm.firstMemebercard = jsonObj.first_memebercard_commission
        this.commissionCardSettingForm.continueMemebercard = jsonObj.continue_membercard_commission
        this.commissionCardSettingOpen = true;
        this.title = "会员卡提成设置";
      });
      
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
    /** 修改会员卡提成后提交按钮 */
    commissionCardSettingConfirm(){
      this.$refs["commissionCardSettingForm"].validate(valid => {
        if (valid) {
          let newConfigValueObj = {
            "commission_fixed_amount":this.commissionCardSettingForm.fixedAmount,
            "first_memebercard_commission":this.commissionCardSettingForm.firstMemebercard,
            "continue_membercard_commission":this.commissionCardSettingForm.continueMemebercard
          }
          let newForm = {}
          newForm.configKey = "commission:membercard:employee"
          newForm.configValue = JSON.stringify(newConfigValueObj)

          updateConfigByKey(newForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.commissionCardSettingOpen = false;
          });
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEmployee(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEmployee(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除员工信息管理编号为"' + ids + '"的数据项？').then(function() {
        return delEmployee(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('employee/employee/export', {
        ...this.queryParams
      }, `employee_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
