var Apply = {
  create(onSucc) {
    Apply.host.req('/order/create.json', { type: 2, productType: 4, vendorId: 17 }, r => {
      onSucc(r)
    })
  },
  fold(orderId, onSucc) {
    Apply.host.req('/apply/fold.json', { orderId: orderId }, r => {
      onSucc(r)
    })
  },
  view(orderId, onSucc) {
    Apply.host.req('/order/view.json', { orderId: orderId }, r => {
      onSucc(r)
    })
  },
  save(order, onSucc) {
    Apply.host.req('/order/save.json', order, r => {
      onSucc(r)
    })
  },
  createPlan(applicant, insurant, onSucc) {
    Apply.host.req('/proposal/plan/create.json', { applicant: applicant, insurant: insurant }, r => {
      onSucc(r)
    })
  },
  refreshInsurant(planId, ins, onSucc) {
    Apply.host.req('/proposal/plan/customer.json', { planId: planId, insurant: ins }, r => {
      onSucc(r)
    })
  },
  addProduct(planId, parent, prdId, onSucc) {
    Apply.host.req('/proposal/plan/clause.json', { planId: planId, parentIndex: parent, productId: prdId }, r => {
      onSucc(r)
    })
  },
  saveProduct(planId, index, vals, onSucc) {
    Apply.host.req('/proposal/plan/clause.json', { planId: planId, index: index, detail: vals }, r => {
      onSucc(r)
    })
  },
  editProduct(planId,channelId,orgCode,productId, index, onSucc) {
    Apply.host.req('/proposal/plan/view_clause.json', { planId: planId,channelId:channelId,orgCode:orgCode,productId:productId, index: index }, r => {
      onSucc(r)
    })
  },
  deleteProduct(planId, index, productId, onSucc) {
    Apply.host.req('/proposal/plan/remove_clause.json', { planId: planId, index: index, productId: productId }, r => {
      onSucc(r)
    })
  },
  queryProduct(tag,channelId,orgCode, vendor, text, onSucc) {
    Apply.host.req('/proposal/query_clause.json', { tag: tag,channelId:channelId,orgCode:orgCode, company: vendor, text: text == "" ? null : text }, r => {
      onSucc(r)
    })
  },
  listRiders(planId,channelId,orgCode, index, onSucc) {
    Apply.host.req('/proposal/plan/list_riders.json', { planId: planId,channelId:channelId,orgCode:orgCode, index: index }, r => {
      onSucc(r)
    })
  },
  format(planId, style, onSucc) {
    Apply.host.req('/proposal/plan/format.json', { planId: planId, style: style }, r => {
      onSucc(r)
    })
  },
  viewPlan(planId, onSucc) {
    Apply.host.req('/proposal/plan/view.json', { planId: planId }, r => {
      onSucc(r)
    })
  }
}
