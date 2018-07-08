class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            proposalId: common.param("proposalId"),
            ages: [],
            index: 0,
            now: common.dateStr(new Date())
        }
    }
    componentDidMount() {
        let ages = []
        for (let i=0;i<70;i++)
            ages.push(i)
        this.setState({ ages: ages })

        MF.setTitle("建议书")

        APP.proposal.view(this.state.proposalId, r => {
            this.setState({ proposal: r }, () => {
                this.onInsurantSwitch(0)
            })
        })
    }
    onInsurantSwitch(i) {
        if (this.state.proposal.detail.length > i) {
            let planId = this.state.proposal.detail[i];
            if (planId != null && planId != "") {
                APP.proposal.viewPlan(planId, r => {
                    this.setState({ index: i, plan: r })
                })
            }
        }
    }
    onGenderChange(e) {
        this.state.plan.insurant.gender = e
        this.refreshInsurant()
    }
    onAgeChange(e) {
        this.state.plan.insurant.age = e
        this.state.plan.insurant.birthday = null
        this.refreshInsurant()
    }
    onBirthdayChange(e) {
        this.state.plan.insurant.birthday = e
        this.refreshInsurant()
    }
    refreshInsurant() {
        APP.proposal.refreshInsurant(this.state.plan.planId, this.state.plan.insurant, r => {
            this.setState({ plan: r })
        })
    }
    addProduct() {
        APP.pop("proposal/product_list.html", 60, r => {
            if (r != null) {
                APP.proposal.addProduct(this.state.plan.planId, null, r, r => {
                    this.setState({ plan: r })
                })
            }
        })
    }
    editProduct(e) {
        APP.pop("proposal/product_editor.html?planId=" + this.state.plan.planId + "&index=" + e, 80, r => {
            APP.proposal.viewPlan(this.state.plan.planId, plan => {
                this.setState({ plan: plan })
            })
        })
    }
    deleteProduct(i) {
        APP.proposal.deleteProduct(this.state.plan.planId, i, null, r => {
            this.setState({ plan: r })
        })
    }
    showBenefit() {
        APP.pop("proposal/benefit.html?planId=" + this.state.plan.planId, 90)
    }
    popCustomer() {
        APP.pop("client/client_selector.html?pop=1", 90)
    }
    next() {
        APP.proposal.save(this.state.proposalId, r => {
            MF.navi("proposal/preview.html?proposalId=" + this.state.proposalId)
        })
    }
    render() {
        let plan = this.state.plan
        let insurant = plan ? plan.insurant : null
        return plan == null || insurant == null ? null : (
            <div>
                <div>
                    <div className="divx bg-desk" style={{position:"fixed", zIndex:"50", top:"0", width:"100%"}}>
                        { this.state.proposal.detail.map((v, i) =>
                            <div className={"tab " + (i == this.state.index ? 'tab-focus' : 'tab-blur')} key={i} style={{width:"250px"}} onClick={this.onInsurantSwitch.bind(this, i)}>
                                <text className="text18">{ "计划" + (i+1) }</text>
                            </div>
                        )}
                    </div>
                    <div className="card-content" style={{marginTop:"80px"}}>
                        <div className="card-content-line bg-white">
                            <div className="card-content-label text17">性别</div>
                            <div className="card-content-widget text17">
                                <div className={"btn-sm text17 " + (insurant.gender == "F" ? "btn-sel" : "")} onClick={this.onGenderChange.bind(this, "F")}>女</div>
                                <div className={"btn-sm text17 " + (insurant.gender == "M" ? "btn-sel" : "")} onClick={this.onGenderChange.bind(this, "M")}>男</div>
                            </div>
                        </div>
                    </div>
                    <div className="card-content">
                        <div className="card-content-line bg-white">
                            <div className="card-content-label text17">{insurant.birthday?"出生日期":"年龄"}</div>
                            <div className="card-content-widget" onClick={e => { APP.pick("select", this.state.ages, this.onAgeChange.bind(this)) }}>
                                <img className="mt-1 ml-2" style={{width:"60px", height:"60px"}} src="../images/calendar.png" onClick={e => { e.stopPropagation(); APP.pick("date", { begin: "1900-01-01", end: new Date() }, this.onBirthdayChange.bind(this)) }}/>
                                <div className="text17">{ insurant.birthday }{ insurant.birthday && insurant.age ? " / " : "" }{ insurant.age ? insurant.age + "岁" : "" }</div>
                            </div>
                        </div>
                    </div>
                    <div className="card-content" style={{marginTop:"10px"}}>
                        { plan.product.map((v,i) => [
                            v.parent == null ?
                                <div className="product product-main bg-white text16" style={{marginTop:"10px"}} onClick={this.editProduct.bind(this, i)}>
                                    <div style={{height:"70px", display:"flex"}}>
                                        <img style={{width:"60px", height:"60px", margin:"10px 10px 0 10px"}} src={plan.icons[v.vendor]}></img>
                                        <div style={{width:"600px", marginTop:"10px"}}>
                                            <text className="text20 eclipse">{v.name}</text>
                                        </div>
                                        <img className="mt-1 mr-1 mb-1 ml-1" style={{width:"50px", height:"50px", opacity:"0.4"}} src="../images/stop.png" onClick={ e => { e.stopPropagation(); this.deleteProduct(i); } }/>
                                    </div>
                                    <div style={{height:"60px", display:"flex"}}>
                                        <div className="center" style={{width:"80px"}}>
                                        </div>
                                        <div className="eclipse" style={{width:SIZE-250+"px"}}>
                                            <text>{v.purchase} / {v.insure} / {v.pay}</text>
                                        </div>
                                        <div className="right" style={{width:"150px"}}>
                                            <text style={{color:"#000"}}>{v.premium}元</text>
                                        </div>
                                    </div>
                                    <div style={{height:"10px"}}></div>
                                </div> :
                                <div className="product product-rider bg-white text16">
                                    <div className="center" style={{width:"80px"}}>
                                        <text style={{color:"#0a0"}}>附</text>
                                    </div>
                                    <div className="eclipse" style={{width:SIZE-250+"px"}}>
                                        <text style={{color:"#000", marginRight:"10px"}}>{v.abbrName}</text>
                                        <text style={{color:"#aaa"}}>{v.purchase} / {v.insure} / {v.pay}</text>
                                    </div>
                                    <div className="right" style={{width:"150px"}}>
                                        <text style={{color:"#000"}}>{v.premium}元</text>
                                    </div>
                                </div>
                            , v.rule == null ? null :
                                <div className="tc-red text12 ml-1 mr-1 pl-1 pr-1" style={{lineHeight:"32px", border:"#f00 solid 1px", backgroundColor:"#ffaaaa"}}>
                                    { !v.rule ? null : v.rule.map((w, j) =>
                                        <div>{j+1}、{w}</div>
                                    )}
                                </div>
                        ])}
                        { plan.product && plan.product.length > 0 ?
                            <div className="card-content-line bg-white" style={{padding:"0 20px 0 20px", display:"block", marginTop:"10px", textAlign:"right", color:"#09bb07"}}>
                                <text className="text16">合计：{plan.premium}元</text>
                            </div> : null
                        }
                        <div className="btn-fl text18" style={{color:"#fff", backgroundColor:"#1aad19"}} onClick={this.addProduct.bind(this)}>添加险种</div>
                    </div>
                </div>
                <div style={{height:"120px"}}></div>
                <div className="bottom text18 tc-primary">
                    <div className="ml-3 mr-auto" onClick={this.showBenefit.bind(this)}>
                        查看利益责任
                    </div>
                    <div className="mr-3" onClick={this.next.bind(this)}>
                        预览建议书
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