var orderId = common.param("orderId")
if (orderId != null && orderId != "") {
    APP.apply.fold(orderId, r => {
        MF.direct("applicant?orderId=" + r)
    })
} else {
    APP.apply.create(r => {
        MF.direct("applicant?orderId=" + r.id)
    })
}
