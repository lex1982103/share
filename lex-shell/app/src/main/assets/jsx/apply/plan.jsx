var Main = React.createClass({
    getInitialState() {
        return {
            orderId: common.param("orderId"),
            ages: [],
            index: 0,
            now: common.dateStr(new Date())
        }
    },
    componentDidMount() {
        let ages = []
        for (let i=0;i<70;i++)
            ages.push(i)
        this.setState({ ages: ages })

        MF.setTitle("建议书")

        APP.apply.view(this.state.orderId, r => {
            this.setState({ order: r }, () => {
                this.onInsurantSwitch(0)
            })
        })
    },
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
                    this.setState({ index: i, plan: r })
                })
            }
        }
    },
    onShow() {
        let opt = APP.passport();
        if (opt) {
            if (opt.productId) {
                APP.proposal.addProduct(plan.planId, null, opt.productId, (r) => {
                    this.setState({ plan: r })
                })
            }
            if (opt.proposalId) {
                APP.proposal.load(opt.proposalId, (r) => {
                    this.setState({ proposal: r, index: 0 }, this.onProposal)
                })
            }
        }
    },
    onGenderChange(e) {
        this.state.order.detail.insurants[this.state.index].gender = e
        this.refreshInsurant()
    },
    onAgeChange(e) {
        this.state.order.detail.insurants[this.state.index].age = e
        this.state.order.detail.insurants[this.state.index].birthday = null
        this.refreshInsurant()
    },
    onBirthdayChange(e) {
        this.state.order.detail.insurants[this.state.index].birthday = e.detail.value
        this.refreshInsurant()
    },
    refreshInsurant() {
        APP.apply.refreshInsurant(this.state.plan.planId, this.state.order.detail.insurants[this.state.index], (r) => {
            this.setState({ plan: r })
        })
    },
    addProduct() {
        MF.navi("proposal/product_list")
    },
    editProduct(e) {
        MF.pop("apply/product_editor.html?planId=" + this.state.plan.planId + "&index=" + e, 75)
    },
    deleteProduct(i) {
        APP.apply.deleteProduct(this.state.plan.planId, i, null, r => {
            this.setState({ plan: r })
        })
    },
    next() {
        MF.navi("apply/pay.html")
    },
    showBenefit() {
        MF.pop("apply/product_editor.html?planId=" + this.state.plan.planId + "&index=" + 0, 75)
        //MF.navi("proposal/benefit?planId=" + plan.planId)
    },
    render() {
        let plan = this.state.plan
        let insurant = this.state.order ? this.state.order.detail.insurants[this.state.index] : null;
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
                                <div style={{display:"flex"}} onClick={v => {APP.pick("select", this.state.ages, this.onAgeChange)}}>
                                    <div className="text17">{insurant.age}周岁</div>
                                    <img style={{width:"60px", height:"60px", margin:"10px 0 10px 10px"}} src="../images/arrow-7-right.png"></img>
                                </div>
                                <img style={{width:"60px", height:"60px", margin:"10px 30px 10px 10px"}} src="../images/calendar.png"/>
                            </div>
                        </div>
                    </div>
                    <div className="card-content" style={{marginTop:"10px"}}>
                        { plan.product.map((v,i) =>
                            v.parent == null ?
                                <div className="product product-main text16" style={{marginTop:"10px"}} onClick={this.editProduct.bind(this, i)}>
                                    <div style={{height:"70px", display:"flex"}}>
                                        <img style={{width:"60px", height:"60px", margin: "10px 10px 0 10px"}} src={plan.icons[v.vendor]}></img>
                                        <div style={{width:"600px", marginTop:"10px"}}>
                                            <text className="text20 eclipse">{v.name}</text>
                                        </div>
                                        <img style={{width:"50px", height:"50px", padding:"10px 10px 10px 10px", opacity:"0.4"}} src="../images/stop.png" onClick={this.deleteProduct.bind(this,i)}/>
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
                                    <div style="height:10px;"></div>
                                </div> :
                                <div className="product product-rider text16">
                                    <div className="left">
                                        <text style="color:#0a0;">附</text>
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
                        <div className="btn-fl text18" style={{color:"#fff", backgroundColor:"#1aad19"}} onClick={this.addProduct}>添加险种</div>
                    </div>
                </div>
                <div style={{height:"120px"}}></div>
                <div className="bottom">
                    <div className="btn-img" onClick={this.showBenefit}>
                        <img src="../images/md-levels-alt.png"></img>
                        <text>利益</text>
                    </div>
                    <div style={{width:"490px", padding:"6px 20px 6px 20px", lineHeight:"44px",margin:"0"}}>
                        <div>
                            <text className="text16" style={{color:"#fff"}}>首年保费：{plan.premium}元</text>
                        </div>
                        <div>
                            <text className="text16" style={{color:"#fff"}}>点击查看年度保费明细</text>
                        </div>
                    </div>
                    <div style={{width:"60px", height:"100px"}}>
                        <img style={{width:"60px", height:"60px", marginTop:"20px"}} src="../images/arrow-4-up.png"></img>
                    </div>
                    <div className="btn-img" onClick={this.next}>
                        <img src="../images/arrow-1-right.png"></img>
                        <text>继续</text>
                    </div>
                </div>
            </div>
		)
    }
})


$(document).ready( function() {
	ReactDOM.render(
		<Main/>, document.getElementById("root")
	);
});