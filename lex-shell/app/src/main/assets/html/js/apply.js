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
  }
}
