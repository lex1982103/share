var Customer = {
  query(c, onSucc) {
    Customer.host.req('/customer/list.json', c, r => {
      onSucc(r)
    })
  }
}
