<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="120px">
      <el-form-item label="任务名称" prop="taskName">
        <el-input
          v-model="queryParams.taskName"
          placeholder="请输入任务名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计划执行时间" prop="planExcuteDatetime">
        <el-date-picker clearable
          v-model="queryParams.planExcuteDatetime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择计划执行时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="发送状态" prop="sendStatus">
        <el-select v-model="queryParams.sendStatus" placeholder="请选择发送状态" clearable>
          <el-option
            v-for="dict in dict.type.batch_send_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="使用模版id" prop="useTemplateId">
        <el-input
          v-model="queryParams.useTemplateId"
          placeholder="请输入使用模版id"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['marketingmanage:batch:add']"
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
          v-hasPermi="['marketingmanage:batch:edit']"
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
          v-hasPermi="['marketingmanage:batch:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['marketingmanage:batch:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="batchList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="任务名称" align="center" prop="taskName" />
      <el-table-column label="任务类型" align="center" prop="taskType" />
      <el-table-column label="计划执行时间" align="center" prop="planExcuteDatetime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.planExcuteDatetime, '{y}-{m}-{d} {h}:{m}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发送状态" align="center" prop="sendStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.batch_send_status" :value="scope.row.sendStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="使用模版id" align="center" prop="useTemplateId" />
      <el-table-column label="使用模版内容" align="center" prop="useTemplateContent" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['marketingmanage:batch:edit']"
            v-if="scope.row.sendStatus === '0'"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['marketingmanage:batch:remove']"
            v-if="scope.row.sendStatus === '0'"
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

    <customerWithCardChoose ref="customerWithCardChooseDialog" title="选择会员" @chooseOk="customerWithCardChooseOk"></customerWithCardChoose>

    <!-- 添加或修改短信消息批量发送定时执行状态对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="129px">
        <el-form-item label="任务名称" prop="taskName">
          <el-input v-model="form.taskName" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="计划执行时间" prop="planExcuteDatetime">
          <el-date-picker clearable
            v-model="form.planExcuteDatetime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择计划执行时间">
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="发送状态" prop="sendStatus">
          <el-select v-model="form.sendStatus" placeholder="请选择发送状态">
            <el-option
              v-for="dict in dict.type.batch_send_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item> -->
        <!-- <el-form-item label="使用模版id" prop="useTemplateId">
          <el-input v-model="form.useTemplateId" placeholder="请输入使用模版id" />
        </el-form-item> -->
        <el-form-item label="使用模版" prop="sendStatus">
          <el-select v-model="form.useTemplateId" placeholder="请选择使用的模版" @change="templateSelected">
            <el-option
              v-for="dict in messageTemplateDictList"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="使用模版内容">
          <editor v-model="form.useTemplateContent" :min-height="192"/>
        </el-form-item>
        <!-- <el-form-item label="过滤条件" prop="filterConditions">
          <el-input v-model="form.filterConditions" placeholder="请输入过滤条件" />
        </el-form-item> -->
        <el-form-item label="发送给会员">
          <el-button
            type="success"
            icon="el-icon-plus"
            size="medium"
            @click="handleCustomerWithCardChoose"
            >点击选择会员
            </el-button>
           <span>已选择{{ chooseTotal }}条</span> 
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBatch, getBatch, delBatch, addBatch, updateBatch } from "@/api/marketingmanage/batch";
import {getTemplateDictList,getTemplate} from "@/api/marketingmanage/template";
import customerWithCardChoose from '@/views/member/customer/customerWithCardchoose'

export default {
  name: "Batch",
  dicts: ['batch_send_status'],
  components: {
    customerWithCardChoose
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
      //已选择会员条数
      chooseTotal:0,
      // 短信消息批量发送定时执行状态表格数据
      batchList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskName: null,
        taskType: 'SMS',
        planExcuteDatetime: null,
        sendStatus: null,
        useTemplateId: null,
        useTemplateContent: null,
        filterConditions: ''
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        taskName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
        // taskType: [
        //   { required: true, message: "任务类型不能为空", trigger: "change" }
        // ],
        planExcuteDatetime: [
          { required: true, message: "计划执行时间不能为空", trigger: "blur" }
        ],
      },
      // 消息模版数据字典
      messageTemplateDictList:[]
    };
  },
  created() {
    this.getList();
    //初始化数据字典
    this.initMessageTemplate()
  },
  methods: {
    /** 查询短信消息批量发送定时执行状态列表 */
    getList() {
      this.loading = true;
      listBatch(this.queryParams).then(response => {
        this.batchList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //初始化消息模版数据字典
    initMessageTemplate(messageType){
      if(!messageType){
        messageType = '1'
      }
      getTemplateDictList(messageType).then(response => {
        let messageTemplateList = response.data;
        if(messageTemplateList){
          this.messageTemplateDictList = []
          messageTemplateList.forEach(element => {
            let labelValue = {}
            labelValue.value = element.id
            labelValue.label = element.templateCode+element.templateName
            this.messageTemplateDictList.push(labelValue)
          });
          // console.log("messageTemplateDictList",messageTemplateDictList)
        }
      });
    },
    //模版选择事件
    templateSelected(value){
      console.log("value",value)
      if(value){
        getTemplate(value).then(response => {
          let data = response.data
          if(data){
            this.form.useTemplateContent = data.templateContent
          }
        });
      }
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
        taskName: null,
        taskType: 'SMS',
        planExcuteDatetime: null,
        sendStatus: null,
        useTemplateId: null,
        useTemplateContent: null,
        filterConditions: ''
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加短信发送计划";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBatch(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改短信发送计划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBatch(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBatch(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除短信发送计划,编号为"' + ids + '"的数据项？').then(function() {
        return delBatch(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('marketingmanage/batch/export', {
        ...this.queryParams
      }, `batch_${new Date().getTime()}.xlsx`)
    },
     /** 会员选择按钮操作 */
     handleCustomerWithCardChoose(){
      this.$refs.customerWithCardChooseDialog.show()
    },
    /** 会员选择回调函数 */
    customerWithCardChooseOk(queryParams,total){
      console.log("queryParams:",queryParams)
      console.log("total:",total)
      if(queryParams){
        //json对象转字符串
        this.form.filterConditions = JSON.stringify(queryParams)
      }
      if(total){
        this.chooseTotal = total
      }
    },
  }
};
</script>
