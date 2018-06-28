class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
            certTypeDict: {},
            relationDict: {}
        }
    }
    componentDidMount() {
        MF.setTitle("受益人")
        APP.dict("cert,relation", r => {
            this.setState({
                certTypeDict: r.cert,
                relationDict: r.relation
            })
        })
        APP.apply.view(this.state.orderId, r => {
            this.setState({ order: r })
        })
    }
    onBenefitChange(i, val) {
        if (!val) {
            this.popEditor({ index: i })
        }
        if (this.state.order.detail.insurants[i].benefitLaw != val) {
            this.state.order.detail.insurants[i].benefitLaw = val
            this.forceUpdate()
        }
    }
    save() {
        APP.apply.save({ id: this.state.orderId, detail: { insurants: this.state.order.detail.insurants } }, v => {})
    }
    next() {
        this.save()
        MF.navi("apply/announce.html?orderId=" + this.state.orderId)
    }
    delete(i, j) {
        this.state.order.detail.insurants[i].beneficiary.splice(j, 1)
        this.forceUpdate()
    }
    popEditor(cust) {
        APP.pop("apply/beneficiary_editor.html?cust=" + JSON.stringify(cust), 60, r => {
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
                { this.state.order.detail.insurants.map((v, i) => {
                    let law = v.benefitLaw || v.beneficiary == null || v.beneficiary.length == 0;
                    return <div className="div bg-white mt-2">
                        <div className="divx bg-white pl-3 pr-3 pt-1 pb-1" style={{height:"100px", textAlign:"center"}}>
                            <div className="divx text18 mt-1 mr-auto" style={{height:"60px", verticalAlign:"middle", lineHeight:"60px"}}>
                                <div className="bg-primary tc-white mr-1" style={{borderRadius:"30px", width:"140px", height:"60px"}}>计划 {i+1}</div>
                                {v.name}
                            </div>
                            <div className={"btn-sm text17 " + (law ? "btn-sel" : "")} onClick={this.onBenefitChange.bind(this, i, true)}>法定</div>
                            <div className={"btn-sm text17 " + (!law ? "btn-sel" : "")} onClick={this.onBenefitChange.bind(this, i, false)}>{law?"约定":"添加"}</div>
                        </div>
                        { law ? null : v.beneficiary.map((w, j) =>
                            <div className="divx">
                                <div className="text40" style={{width:"210px", height:"140px", background:"url(../images/seq1.png) no-repeat top left", backgroundSize:"140px 140px", lineHeight:"140px", textAlign:"right"}}>
                                    {w.scale}%
                                </div>
                                <div className="div text16 pl-3 pr-3 pt-1 pb-1" style={{width:"470px", borderTop:"1px solid #e8e8e8"}}>
                                    <div style={{height:"60px", lineHeight:"60px"}}>{w.name}（被保险人的{this.state.relationDict[w.relation]}）</div>
                                    <div style={{height:"60px", lineHeight:"60px"}}>{this.state.certTypeDict[w.certType]}：{w.certNo}</div>
                                </div>
                                <img className="mt-1 mr-1 ml-1" style={{width:"50px", height:"50px", opacity:"0.4"}} src="../images/stop.png" onClick={this.delete.bind(this, i, j)}/>
                            </div>
                        )}
                    </div>
                })}
                <div className="bottom text18 tc-primary">
                    <div className="ml-3 mr-0" style={{width:"300px"}}></div>
                    <div className="divx" onClick={this.next.bind(this)}>
                        <div className="ml-0 mr-0" style={{width:"390px", textAlign:"right"}}>
                            声明及授权
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
    ReactDOM.render(<Main/>, document.getElementById("root"))
})