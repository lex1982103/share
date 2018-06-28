class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
            applicant: {},
            pay: null
        }
    }
    componentDidMount() {
        MF.setTitle("缴费信息")
        APP.dict("pay", r => {
            let bankMap = {}
            let payDict = r.pay.datas.map(v => {
                let c = v.bankList.map(w => {
                    bankMap[w.bankCode] = { text:w.bankName }
                    return { code:w.bankCode, text:w.bankName }
                })
                bankMap[v.code] = { text:v.name, children:c }
                return { code:v.code, text:v.name }
            })
            this.setState({
                bankMap: bankMap,
                payDict: payDict
            })
        })
        APP.apply.view(this.state.orderId, r => {
            let pay = r.extra && r.extra.pay ? r.extra.pay : {}
            this.setState({ applicant: r.detail.applicant, pay: pay })
        })
    }
    save() {
        this.state.pay.applyNo = this.refs.applyNo.value
        this.state.pay.bankCard = this.refs.bankCard.value
        APP.apply.save({ id: this.state.orderId, extra: { pay: this.state.pay } }, r => {
            this.setState({ pay: r.extra.pay })
        })
    }
    next() {
        this.save()
        MF.navi("apply/image.html?orderId=" + this.state.orderId)
    }
    onValChange(key, val) {
        if (key == "payMode")
            this.state.pay[key] = val
        this.state.pay.bank = null
        this.setState({ pay: this.state.pay })
    }
    render() {
        let pay = this.state.pay;
        return pay == null ? null : (
            <div>
                <div className="form-item text16 mt-2">
                    <div className="form-item-label">投保单号</div>
                    <div className="form-item-widget">
                        <input className="mt-1" ref="applyNo" defaultValue={pay.applyNo} placeholder="请输入投保单号"/>
                    </div>
                </div>
                <div className="div" style={{marginTop:"20px"}}>
                    <div className="form-item text16">
                        <div className="form-item-label">银行卡影像</div>
                        <div className="form-item-widget">
                            <img className="mt-1" style={{width:"220px", height:"60px"}} src="../images/btn-scan.png" onClick={v => {APP.pick("scan", null, this.onValChange.bind(this, "bankCardImg"))}}/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">银行卡号</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="bankCard" defaultValue={pay.bankCard} placeholder="请输入银行卡号"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">首期缴费方式</div>
                        <div className="form-item-widget" onClick={v => {APP.pick("select", this.state.payDict, this.onValChange.bind(this, "payMode"))}}>
                            <div className={(pay.payMode == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{pay.payMode == null ? "请选择首期缴费方式" : this.state.bankMap[pay.payMode].text}</div>
                            <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">开户名</div>
                        <div className="form-item-widget pl-1">{this.state.applicant.name}</div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">开户银行</div>
                        <div className="form-item-widget" onClick={v => {APP.pick("select", this.state.bankMap[pay.payMode].children, this.onValChange.bind(this, "bank"))}}>
                            <div className={(pay.bank == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{pay.bank == null ? "请选择开户银行" : this.state.bankMap[pay.bank].text}</div>
                            <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">续期缴费方式</div>
                        <div className="form-item-widget" onClick={v => {APP.pick("select", this.state.payDict, this.onValChange.bind(this, "renewPayMode"))}}>
                            <div className={(pay.renewPayMode == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{pay.renewPayMode == null ? "请选择续期缴费方式" : this.state.bankMap[pay.renewPayMode].text}</div>
                            <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                        </div>
                    </div>
                </div>
                <div style={{height:"120px"}}></div>
                <div className="bottom text18 tc-primary">
                    <div className="ml-3 mr-0" style={{width:"300px"}}></div>
                    <div className="divx" onClick={this.next.bind(this)}>
                        <div className="ml-0 mr-0" style={{width:"390px", textAlign:"right"}}>
                            确认支付
                        </div>
                        <div className="ml-1 mr-2" style={{width:"30px"}}>
                            <img className="mt-3" style={{width:"27px", height:"39px"}} src="../images/blueright.png"/>
                        </div>
                    </div>
                </div>
            </div>
		)
    }
}

$(document).ready( function() {
	ReactDOM.render(
		<Main/>, document.getElementById("root")
	);
});