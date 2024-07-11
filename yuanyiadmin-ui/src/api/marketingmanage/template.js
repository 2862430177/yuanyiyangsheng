import request from '@/utils/request'

//获取消息模版数据字典
export function getTemplateDictList(query) {
  return request({
    url: '/marketingmanage/template/getDictList',
    method: 'get',
    params: query
  })
}
// 查询消息模版（短信|微信|邮件）列表
export function listTemplate(query) {
  return request({
    url: '/marketingmanage/template/list',
    method: 'get',
    params: query
  })
}

// 查询消息模版（短信|微信|邮件）详细
export function getTemplate(id) {
  return request({
    url: '/marketingmanage/template/' + id,
    method: 'get'
  })
}

// 新增消息模版（短信|微信|邮件）
export function addTemplate(data) {
  return request({
    url: '/marketingmanage/template',
    method: 'post',
    data: data
  })
}

// 修改消息模版（短信|微信|邮件）
export function updateTemplate(data) {
  return request({
    url: '/marketingmanage/template',
    method: 'put',
    data: data
  })
}

// 删除消息模版（短信|微信|邮件）
export function delTemplate(id) {
  return request({
    url: '/marketingmanage/template/' + id,
    method: 'delete'
  })
}
