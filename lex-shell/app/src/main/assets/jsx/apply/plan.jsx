class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
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

        MF.setTitle("投保计划")

        APP.apply.view(this.state.orderId, r => {
            this.setState({ order: r }, () => {
                this.onInsurantSwitch(0)
            })
        })
    }
    onInsurantSwitch(i) {
        if (this.state.order.detail.insurants.length > i) {
            let planId = this.state.order.detail.insurants[i].planId;
            if (planId != null && planId != "") {
                APP.apply.viewPlan(planId, r => {
                    this.setState({ index: i, plan: r })
                })
            } else {
                APP.apply.createPlan(this.state.order.detail.applicant, this.state.order.detail.insurants[i], r => {
                    this.state.order.detail.insurants[i].planId = r.planId
                    APP.apply.save({ id: this.state.orderId, detail: { insurants: this.state.order.detail.insurants } }, v => {
                        this.setState({ index: i, plan: r })
                    })
                })
            }
        }
    }
    onGenderChange(e) {
        this.state.order.detail.insurants[this.state.index].gender = e
        this.refreshInsurant()
    }
    onAgeChange(e) {
        this.state.order.detail.insurants[this.state.index].age = e
        this.state.order.detail.insurants[this.state.index].birthday = null
        this.refreshInsurant()
    }
    onBirthdayChange(e) {
        this.state.order.detail.insurants[this.state.index].birthday = e
        this.refreshInsurant()
    }
    refreshInsurant() {
        APP.apply.refreshInsurant(this.state.plan.planId, this.state.order.detail.insurants[this.state.index], (r) => {
            this.setState({ plan: r })
        })
    }
    addProduct() {
        APP.pop("apply/product_list.html", 60, r => {
            if (r != null) {
                APP.apply.addProduct(this.state.plan.planId, null, r, r => {
                    this.setState({ plan: r })
                })
            }
        })
    }
    editProduct(e) {
        APP.pop("apply/product_editor.html?planId=" + this.state.plan.planId + "&index=" + e, 80, r => {
            APP.apply.viewPlan(this.state.plan.planId, plan => {
                console.log(JSON.stringify(plan))
                this.setState({ plan: plan })
            })
        })
    }
    deleteProduct(i) {
        APP.apply.deleteProduct(this.state.plan.planId, i, null, r => {
            this.setState({ plan: r })
        })
    }
    next() {
        MF.navi("apply/health.html?orderId=" + this.state.orderId)
    }
    showBenefit() {
        MF.pop("apply/benefit.html?planId=" + this.state.plan.planId, 90)
    }
    render() {
        let plan = this.state.plan
        let insurant = plan ? plan.insurant : null;
        return plan == null || insurant == null ? null : (
            <div>
                <div>
                    <div style={{display:"flex", position:"fixed", zIndex:"50", top:"0", backgroundColor:"#e6e6e6"}}>
                        { this.state.order.detail.insurants.map((v, i) =>
                            <div className={"tab " + (i == this.state.index ? 'tab-focus' : 'tab-blur')} key={i} style={{width:"250px"}} onClick={this.onInsurantSwitch.bind(this, i)}>
                                <text className="text18">{ v.name == null || v.name == "" ? "被保险人" + (i+1) : v.name }</text>
                            </div>
                        )}
                    </div>
                    <div className="card-content" style={{marginTop:"80px"}}>
                        <div className="card-content-line">
                            <div className="card-content-label text17">性别</div>
                            <div className="card-content-widget text17">
                                <div className={"btn-sm text17 " + (insurant.gender == "F" ? "btn-sel" : "")} onClick={this.onGenderChange.bind(this, "F")}>女</div>
                                <div className={"btn-sm text17 " + (insurant.gender == "M" ? "btn-sel" : "")} onClick={this.onGenderChange.bind(this, "M")}>男</div>
                            </div>
                        </div>
                    </div>
                    <div className="card-content">
                        <div className="card-content-line">
                            <div className="card-content-label text17">年龄</div>
                            <div className="card-content-widget">
                                <div style={{display:"flex"}} onClick={v => {APP.pick("select", this.state.ages, this.onAgeChange.bind(this))}}>
                                    <div className="text17">{insurant.age}周岁</div>
                                    <img style={{display:"none", width:"60px", height:"60px", margin:"10px 0 10px 10px"}} src="../images/arrow-7-right.png"></img>
                                </div>
                                <img style={{width:"60px", height:"60px", margin:"10px 30px 10px 10px"}} src="../images/calendar.png" onClick={v => {APP.pick("date", {}, this.onBirthdayChange.bind(this))}}/>
                            </div>
                        </div>
                    </div>
                    <div className="card-content" style={{marginTop:"10px"}}>
                        { plan.product.map((v,i) =>
                            v.parent == null ?
                                <div className="product product-main text16" style={{marginTop:"10px"}} onClick={this.editProduct.bind(this, i)}>
                                    <div style={{height:"70px", display:"flex"}}>
                                        <img style={{width:"60px", height:"60px", margin:"10px 10px 0 10px"}} src={plan.icons[v.vendor]}></img>
                                        <div style={{width:"600px", marginTop:"10px"}}>
                                            <text className="text20 eclipse">{v.name}</text>
                                        </div>
                                        <img className="mt-1 mr-1 mb-1 ml-1" style={{width:"50px", height:"50px", opacity:"0.4"}} src="../images/stop.png" onClick={this.deleteProduct.bind(this, i)}/>
                                    </div>
                                    <div style={{height:"60px", display:"flex"}}>
                                        <div className="left">
                                        </div>
                                        <div className="middle eclipse">
                                            <text>{v.purchase} / {v.insure} / {v.pay}</text>
                                        </div>
                                        <div className="right">
                                            <text style={{color:"#000"}}>{v.premium}元</text>
                                        </div>
                                    </div>
                                    <div style={{height:"10px"}}></div>
                                </div> :
                                <div className="product product-rider text16">
                                    <div className="left">
                                        <text style={{color:"#0a0"}}>附</text>
                                    </div>
                                    <div className="middle eclipse">
                                        <text style={{color:"#000", marginRight:"10px"}}>{v.abbrName}</text>
                                        <text style={{color:"#aaa"}}>{v.purchase} / {v.insure} / {v.pay}</text>
                                    </div>
                                    <div className="right">
                                        <text style={{color:"#000"}}>{v.premium}元</text>
                                    </div>
                                </div>
                        )}
                        { plan.product && plan.product.length > 0 ?
                            <div className="card-content-line" style={{padding:"0 20px 0 20px", display:"block", marginTop:"10px", textAlign:"right", color:"#09bb07"}}>
                                <text className="text16">合计：{plan.premium}元</text>
                            </div> : null
                        }
                        <div className="btn-fl text18" style={{color:"#fff", backgroundColor:"#1aad19"}} onClick={this.addProduct.bind(this)}>添加险种</div>
                    </div>
                </div>
                <div style={{height:"120px"}}></div>
                <div className="bottom text18 tc-primary">
                    <div className="ml-3 mr-0" style={{width:"300px"}} onClick={this.showBenefit.bind(this)}>
                        查看利益责任
                    </div>
                    <div className="divx" onClick={this.next.bind(this)}>
                        <div className="ml-0 mr-0" style={{width:"390px", textAlign:"right"}}>
                            健康告知
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