import BenefitChart from './benefit_chart.jsx'

class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            proposalId: common.param("proposalId"),
            index: 0
        }
    }
    componentDidMount() {
        MF.setTitle("建议书预览")
        APP.proposal.view(this.state.proposalId, r => {
            this.setState({ proposal: r }, this.onInsurantSwitch.bind(this, 0))
        })
    }
    onInsurantSwitch(index) {
        APP.proposal.viewPlan(this.state.proposal.detail[index], r => {
          this.setState({ index:index, plan:r })
        })
        APP.proposal.format(this.state.proposal.detail[index], "liab_graph,coverage,benefit_chart", r => {
          this.setState({ coverage:r.coverage, chart:r.benefit_chart, liability:r.liab_graph })
        })
    }
    showLiabDetail(i, j, k) {
        this.state.liability[i].detail[j].detail[k].show = !this.state.liability[i].detail[j].detail[k].show
        this.setState({ liability: this.state.liability })
    }
    render() {
        if (!this.state.proposal || !this.state.plan || !this.state.proposal.other)
            return null
        let plan = this.state.plan
        return (
            <div className="bg-white">
                <div className="bg-dark">
                    <div style={{height:"30px"}}></div>
                    <div className="bg-white ml-3 mr-3" style={{width:"690px", height:"200px", border:"#aaaaaa solid 1px", borderRadius:"16px"}}>
                    </div>
                    <div className="divx">
                        <div className="pt-2 pb-3 center tc-white" style={{width:"250px", backgroundImage:"url(../images/arrow.png)", backgroundSize:"32px 16px", backgroundRepeat:"no-repeat", backgroundPosition:"center bottom"}}>
                            <div className="lh-60 text17">{ plan.insurant.name ? plan.insurant.name : "被保险人" }</div>
                            <div className="lh-40 text14">{ plan.insurant.gender == "M" ? "男" : "女" } { plan.insurant.age }周岁</div>
                        </div>
                    </div>
                </div>

                <div className="ml-1 mr-1 pt-1">
                    { !plan ? null : plan.product.map((w, j) => w.parent != null ? null : 
                        <div className="bg-white mt-1" style={{border:"#aaaaaa solid 1px", borderRadius:"10px"}}>
                            { plan.product.map((v, i) =>
                                v == w ?
                                    <div className="product product-main text16">
                                        <div style={{height:"70px", display:"flex"}}>
                                            <img style={{width:"60px", height:"60px", margin:"10px 10px 0 10px"}} src={plan.icons[v.vendor]}></img>
                                            <div style={{width:"600px", marginTop:"10px"}}>
                                                <span className="text20 eclipse">{v.name}</span>
                                            </div>
                                        </div>
                                        <div style={{height:"60px", display:"flex"}}>
                                            <div className="left">
                                            </div>
                                            <div className="middle eclipse">
                                                <span>{v.purchase} / {v.insure} / {v.pay}</span>
                                            </div>
                                            <div className="right">
                                                <span style={{color:"#000"}}>{v.premium}元</span>
                                            </div>
                                        </div>
                                        <div style={{height:"10px"}}></div>
                                    </div> 
                                : v.parent == j ?
                                    <div className="product product-rider text16 br-tl-gray">
                                        <div className="left">
                                            <span style={{color:"#0a0"}}>附</span>
                                        </div>
                                        <div className="middle eclipse">
                                            <span style={{color:"#000", marginRight:"10px"}}>{v.abbrName}</span>
                                            <span style={{color:"#aaa"}}>{v.purchase} / {v.insure} / {v.pay}</span>
                                        </div>
                                        <div className="right">
                                            <span style={{color:"#000"}}>{v.premium}元</span>
                                        </div>
                                    </div>
                                : null
                            )}
                        </div>
                    )}
                    { !plan ? null :
                        <div className="text16 lh-80 h-80 tc-dark pr-2 right">
                            合计：{plan.premium}元
                        </div>
                    }
                </div>

                <div className="ml-1 mr-1 mt-1 pt-2 pb-2 bg-white" style={{border:"#aaaaaa solid 1px", borderRadius:"10px"}}>
                    { !this.state.liability ? null : this.state.liability.map((v, i) => !v.detail ? null :
                        <div key={i}>
                            <div className="text17 lh-60 tc-gray center pl-3 pr-3 ml-3 mr-3 mt-1 mb-2 bg-primary tc-white" style={{borderRadius:"30px"}}>{v.name}</div>
                            { v.detail.map((w, j) =>
                                <div className="div text17 pl-1 pr-1 pb-1" key={j}>
                                    <div className="lh-60 pl-1 tc-primary">★ {w.name}</div>
                                    { w.detail.map((x, k) => 
                                        <div className="lh-40" key={k}>
                                            <div className="divx pb-1" onClick={this.showLiabDetail.bind(this, i, j, k)}>
                                                <img style={{width:"40px",height:"40px"}} src={x.show?"../images/arrow-7-up.png":"../images/arrow-7-down.png"}/>
                                                <div className="pl-1" style={{width:"650px"}}>{x.text}</div>
                                            </div>
                                            { x.detail.map((y, l) => !x.show || !x.detail ? null :
                                                <div className="divx text15 lh-30 tc-dark pb-1" key={l}>
                                                    <div className="center" style={{width:"40px"}}></div>
                                                    <div className="divx"><div className="title">{y.productAbbrName}</div> <div>{y.text}</div></div>
                                                </div>
                                            )}
                                        </div>
                                    )}
                                </div>
                            )}
                        </div>
                    )}
                </div>

                <div className="ml-1 mr-1 mt-1 pt-2 pb-2 bg-white" style={{border:"#aaaaaa solid 1px", borderRadius:"10px"}}>
                    { !this.state.chart ? null : this.state.chart.map((v, i) => !v.content ? null :
                        <div>
                            <div className="text17 lh-60 tc-gray center pl-3 pr-3 ml-3 mr-3 mt-1 mb-2 bg-primary tc-white" style={{borderRadius:"30px"}}>{v.productName}</div>
                            <div className="divx">
                                <BenefitChart ref={"benefitChart"+i} id={"benefitChart"+i} chart={v} years={[-2,-1,0,1,2]}/> 
                            </div>
                        </div>
                    )}
                </div>

                <div className="tc-dark p-2 text16">温馨提示：免除保险公司责任条款、犹豫期、解除合同的手续及风险、费用扣除等内容，请您仔细阅读保险合同。本演示说明仅供参考，具体保险责任、免除保险公司责任的条款等内容以正式保险合同为准。</div>
            </div>
        )
    }
}

$(document).ready(() => {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})