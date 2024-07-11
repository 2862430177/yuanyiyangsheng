import request from '@/utils/request'

// 查询房间设备管理列表
export function listEquipment(query) {
  return request({
    url: '/shop/equipment/list',
    method: 'get',
    params: query
  })
}

// 查询房间设备管理详细
export function getEquipment(id) {
  return request({
    url: '/shop/equipment/' + id,
    method: 'get'
  })
}

// 新增房间设备管理
export function addEquipment(data) {
  return request({
    url: '/shop/equipment',
    method: 'post',
    data: data
  })
}

// 修改房间设备管理
export function updateEquipment(data) {
  return request({
    url: '/shop/equipment',
    method: 'put',
    data: data
  })
}

// 删除房间设备管理
export function delEquipment(id) {
  return request({
    url: '/shop/equipment/' + id,
    method: 'delete'
  })
}
