import request from '@/utils/request'

// 查询服务产品管理列表
export function listServiceproducts(query) {
  return request({
    url: '/serviceproducts/serviceproducts/list',
    method: 'get',
    params: query
  })
}

// 查询服务产品管理详细
export function getServiceproducts(id) {
  return request({
    url: '/serviceproducts/serviceproducts/' + id,
    method: 'get'
  })
}

// 新增服务产品管理
export function addServiceproducts(data) {
  return request({
    url: '/serviceproducts/serviceproducts',
    method: 'post',
    data: data
  })
}

// 修改服务产品管理
export function updateServiceproducts(data) {
  return request({
    url: '/serviceproducts/serviceproducts',
    method: 'put',
    data: data
  })
}

// 删除服务产品管理
export function delServiceproducts(id) {
  return request({
    url: '/serviceproducts/serviceproducts/' + id,
    method: 'delete'
  })
}
