class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
        }
    }
    componentDidMount() {
        MF.setTitle("受益人")
        APP.apply.view(this.state.orderId, r => {
            this.setState({ order: r })
        })
    }
    onBenefitChange(i, val) {
        if (!val) {
            this.popEditor({ index: i })
        }
    }
    save() {
        APP.apply.save({ id: this.state.orderId, detail: { insurants: this.state.order.detail.insurants } }, v => {})
    }
    next() {
        this.save()
        MF.navi("apply/announce?orderId=" + this.state.orderId)
    }
    popEditor(cust) {
        APP.pop("apply/beneficiary_editor?cust=" + JSON.stringify(cust), 60, r => {
            console.log(r)
            let c = JSON.parse(r)
            let ins = this.state.order.detail.insurants[c.index]
            if (!ins.beneficiary)
                ins.beneficiary = []
            ins.beneficiary.push(c)
            this.forceUpdate()
        })
    }
    render() {
        return this.state.order == null ? null : (
            <div>
                { this.state.order.detail.insurants.map((v, i) =>
                    <div className="div bg-white mt-2">
                        <div className="divx bg-white pl-3 pr-3 pt-1" style={{height:"100px", marginTop:"20px", textAlign:"center"}}>
                            <div className="divx text18 mt-1 mr-auto" style={{height:"60px", verticalAlign:"middle", lineHeight:"60px"}}>
                                <div className="bg-primary tc-white mr-1" style={{borderRadius:"30px", width:"140px", height:"60px"}}>计划 {i+1}</div>
                                {v.name}
                            </div>
                            <div className={"btn-sm text17 " + (v.benefitLaw ? "btn-sel" : "")} onClick={this.onBenefitChange.bind(this, i, true)}>法定</div>
                            <div className={"btn-sm text17 " + (!v.benefitLaw ? "btn-sel" : "")} onClick={this.onBenefitChange.bind(this, i, false)}>{v.benefitLaw?"约定":"添加"}</div>
                        </div>
                        { !v.beneficiary ? null : v.beneficiary.map((w, j) =>
                            <div className="div text16">
                                <div>受益人：{w.name}（被保险人的{w.relation}）</div>
                                <div>证件：{w.certNo}（{w.certType}）</div>
                                <div>第{w.sequence}顺位，受益比例：{w.scale}%</div>
                            </div>
                        )}
                    </div>
                )}
                <div className="bottom text18 tc-primary">
                    <div className="ml-0 mr-0" style={{width:"690px", textAlign:"right"}} onClick={this.next.bind(this)}>
                        声明及授权
                    </div>
                    <div className="ml-1 mr-2" style={{width:"30px"}}>
                        <img className="mt-3" style={{width:"27px", height:"39px"}} src="../images/blueright.png"/>
                    </div>
                </div>
            </div>
		)
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})