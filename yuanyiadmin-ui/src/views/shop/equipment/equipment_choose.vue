<template>
  <div class="app-container">
    <el-dialog title="选择设备" :visible.sync="equipmentChooseOpen" width="50%" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="120px">
      <el-form-item label="设备自定义编号" prop="equipmentNo">
        <el-input
          v-model="queryParams.equipmentNo"
          placeholder="请输入设备自定义编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择设备类型" clearable>
          <el-option
            v-for="dict in dict.type.device_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="开始使用时间" prop="startUseTime">
        <el-date-picker clearable
          v-model="queryParams.startUseTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择开始使用时间">
        </el-date-picker>
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!--单选：highlight-current-row-->
    <!--多选选中事件 @selection-change="handleSelectionChange"-->
    <el-table v-loading="loading" :data="equipmentList" 
    ref="singleTable"
    highlight-current-row
    style="width: 100%"
    @current-change="handleCurrentChange"
    >
      <el-table-column type="index" width="50"></el-table-column>
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="设备id" align="center" prop="id" />
      <el-table-column label="设备自定义编号" align="center" prop="equipmentNo" />
      <el-table-column label="设备类型" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.device_type" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="设备描述" align="center" prop="equipmentDesc" />
      <el-table-column label="设备使用状态" align="center" prop="useStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.use_status" :value="scope.row.useStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="开始使用时间" align="center" prop="startUseTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startUseTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束使用时间" align="center" prop="endUseTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endUseTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="持续使用时间" align="center" prop="continueUsageTime" />
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
import { listEquipment, getEquipment, delEquipment, addEquipment, updateEquipment } from "@/api/shop/equipment";

export default {
  name: "Equipment",
  dicts: ['device_type','use_status'],
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
      // 房间设备管理表格数据
      equipmentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否显示设备选择弹出层
      equipmentChooseOpen:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentNo: null,
        status: null,
        equipmentDesc: null,
        useStatus: null,
        startUseTime: null,
        endUseTime: null,
        continueUsageTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    // this.getList();
  },
  methods: {
    //外部调用显示
    show(){
      this.equipmentChooseOpen = true
      this.getList()
    },
    /** 查询房间设备管理列表 */
    getList() {
      this.loading = true;
      listEquipment(this.queryParams).then(response => {
        this.equipmentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.equipmentChooseOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        equipmentNo: null,
        status: null,
        equipmentDesc: null,
        useStatus: null,
        startUseTime: null,
        endUseTime: null,
        continueUsageTime: null,
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
      this.equipmentChooseOpen = false
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加房间设备管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEquipment(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改房间设备管理";
      });
    },
  }
};
</script>
