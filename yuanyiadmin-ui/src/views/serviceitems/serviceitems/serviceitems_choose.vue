<template>
  <div class="app-container">
    <el-dialog title="选择项目" :visible.sync="serviceItemsChooseOpen" width="50%" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="项目名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择项目状态" clearable>
          <el-option
            v-for="dict in dict.type.project_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="serviceitemsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="服务项目id" align="center" prop="id" />
      <el-table-column label="项目名称" align="center" prop="itemName" />
      <el-table-column label="项目时长" align="center" prop="itemDuration" />
      <el-table-column label="项目收费标准(每次收费)" align="center" prop="itemChargeStandard" />
      <el-table-column label="项目内容(包含)" align="center" prop="itemContent" :show-overflow-tooltip="true"/>
      <el-table-column label="项目状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.project_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
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
import { listServiceitems, getServiceitems, delServiceitems, addServiceitems, updateServiceitems } from "@/api/serviceitems/serviceitems";

export default {
  name: "ServiceitemsChoose",
  dicts: ['project_status'],
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
      // 服务项目管理表格数据
      serviceitemsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否显示员工选择弹出层
      serviceItemsChooseOpen:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemName: null,
        itemDuration: null,
        itemChargeStandard: null,
        itemContent: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
      },
      multipleSelection: []
    };
  },
  created() {
    // this.getList();
  },
  methods: {
    //外部调用显示
    show(){
      this.serviceItemsChooseOpen = true
      this.getList()
    },
    /** 查询服务项目管理列表 */
    getList() {
      this.loading = true;
      listServiceitems(this.queryParams).then(response => {
        this.serviceitemsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.serviceItemsChooseOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        itemName: null,
        itemDuration: null,
        itemChargeStandard: null,
        itemContent: null,
        status: null,
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
      this.multipleSelection = selection
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 确认选择 */
    confirmChoose(){
      this.$emit('chooseOk', this.multipleSelection)
      this.serviceItemsChooseOpen = false
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加服务项目管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getServiceitems(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改服务项目管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateServiceitems(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addServiceitems(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除服务项目管理编号为"' + ids + '"的数据项？').then(function() {
        return delServiceitems(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('serviceitems/serviceitems/export', {
        ...this.queryParams
      }, `serviceitems_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
