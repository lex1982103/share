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
    showCoverageDetail(i, j) {
        this.state.coverage[i].content[j].show = !this.state.coverage[i].content[j].show
        this.setState({ coverage: this.state.coverage })
    }
    render() {
        if (!this.state.proposal || !this.state.plan || !this.state.proposal.other)
            return null
        let plan = this.state.plan
        return (
            <div>
                <div>
                    <div>
                        <img src="https://lifeins.iyunbao.com/static/iyb/images/bobcardif/iyb10004banner.jpg" style={{width:"750px",height:"360px"}}/>
                    </div>
                    <div className="divx">
                        <div className="divx pl-1 pr-1 pt-2 pb-2 tc-dark" style={{width:"250px", backgroundImage:"url(../images/arrow.png)", backgroundSize:"32px 16px", backgroundRepeat:"no-repeat", backgroundPosition:"center bottom"}}>
                            <div>
                                <img src={plan.insurant.gender == "M"?"../images/male.png":"../images/female.png"} style={{width:"100px",height:"100px"}}/>
                            </div>
                            <div className="pt-1 pb-1">
                                <div className="lh-50 text16">{ plan.insurant.name ? plan.insurant.name : "被保险人" }</div>
                                <div className="lh-30 text13">{ plan.insurant.gender == "M" ? "男" : "女" } { plan.insurant.age }岁</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="bg-white">
                    <div className="divx pt-3 pl-2 pr-2 pb-2">
                        <div className="bg-primary" style={{width:"20px", height:"40px"}}></div>
                        <div className="ml-1 lh-40">我的计划</div>
                        { plan ? <div className="ml-auto lh-40">首年保费：<span className="tc-red">{plan.premium}</span>元</div> : null }
                    </div>
                    <div className="ml-1 mr-1">
                        { !plan ? null : plan.product.map((w, j) => w.parent != null ? null :
                            <div className={"bg-white"+(j==0?"":" mt-2")} style={{border:"#dddddd 1px solid"}}>
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
                                        <div className="product product-rider text16 br-tl">
                                            <div className="left">
                                                <span style={{color:"#0a0"}}>附</span>
                                            </div>
                                            <div className="middle eclipse">
                                                <span style={{color:"#000", marginRight:"10px"}}>{v.abbrName}</span>
                                                <span style={{color:"#aaa"}}></span>
                                            </div>
                                            <div className="right">
                                                <span style={{color:"#000"}}>{v.premium}元</span>
                                            </div>
                                        </div>
                                    : null
                                )}
                            </div>
                        )}
                    </div>

                    <div className="divx mt-1 p-2">
                        <div className="bg-primary" style={{width:"20px", height:"40px"}}></div>
                        <div className="ml-1 lh-40">我的保障</div>
                    </div>
                    <div className="ml-1 mr-1 pt-2 pb-2 bg-white" style={{border:"#dddddd 1px solid"}}>
                        { !this.state.liability ? null : this.state.liability.map((v, i) => !v.detail ? null :
                            <div key={i}>
                                <div className="text17 lh-60 center pl-3 pr-3 ml-3 mr-3 mt-1 mb-2 tc-primary bg-white" style={{border:"#00aff9 1px solid", borderRadius:"30px"}}>{v.name}</div>
                                { v.detail.map((w, j) =>
                                    <div className="div text17 pl-1 pr-1 pb-1" key={j}>
                                        <div className="divx">
                                            <div className="mt-1 mb-1 lh-40 center bg-primary tc-white" style={{width:"40px", borderRadius:"20px"}}>{j+1}</div>
                                            <div className="ml-1 lh-60 tc-primary">{w.name}</div>
                                        </div>
                                        { w.detail.map((x, k) =>
                                            <div className="lh-40" key={k}>
                                                <div className="divx pb-1" onClick={this.showLiabDetail.bind(this, i, j, k)}>
                                                    <img style={{width:"40px",height:"40px"}} src={x.show?"../images/arrow-7-up.png":"../images/arrow-7-down.png"}/>
                                                    <div className="pl-1" style={{width:"650px"}}>{x.text}</div>
                                                </div>
                                                { x.detail.map((y, l) => !x.show || !x.detail ? null :
                                                    <div className="divx text15 lh-30 tc-dark pb-1" key={l}>
                                                        <div className="center" style={{width:"40px"}}></div>
                                                        <div className="divx lh-40"><div className="title mr-1">{y.productAbbrName}</div> <div>{y.text}</div></div>
                                                    </div>
                                                )}
                                            </div>
                                        )}
                                    </div>
                                )}
                            </div>
                        )}
                    </div>

                    <div className="divx mt-1 p-2">
                        <div className="bg-primary" style={{width:"20px", height:"40px"}}></div>
                        <div className="ml-1 lh-40">利益图表</div>
                    </div>
                    <div className="ml-1 mr-1 pt-2 pb-2 bg-white" style={{border:"#dddddd 1px solid"}}>
                        { !this.state.chart ? null : this.state.chart.map((v, i) => !v.content ? null :
                            <div>
                                <div className="text17 lh-60 center pl-3 pr-3 ml-3 mr-3 mt-1 mb-2 tc-primary bg-white" style={{border:"#00aff9 1px solid", borderRadius:"30px"}}>{v.productName}</div>
                                <div className="divx">
                                    <BenefitChart size={750} ref={"benefitChart"+i} id={"benefitChart"+i} chart={v} years={[-2,-1,0,1,2]}/>
                                </div>
                            </div>
                        )}
                    </div>

                    <div className="divx mt-1 p-2">
                        <div className="bg-primary" style={{width:"20px", height:"40px"}}></div>
                        <div className="ml-1 lh-40">责任条款</div>
                    </div>
                    <div className="ml-1 mr-1 pt-2 pb-2 bg-white" style={{border:"#dddddd 1px solid"}}>
                      { !this.state.coverage ? null : this.state.coverage.map((v, i) =>
                        <div className="pl-2 pr-2 bg-white">
                          <div style={{marginTop:(i!=0?10:0)+"px"}}>
                            <div className="text17 lh-60 center pl-3 pr-3 ml-3 mr-3 mt-1 mb-2 tc-primary bg-white" style={{border:"#00aff9 1px solid", borderRadius:"30px"}}>{v.productName}</div>
                          </div>
                          { v.content.map((x, j) =>
                            <div style={{background:"#fff"}}>
                              <div className="divx h-60" onClick={this.showCoverageDetail.bind(this, i, j)}>
                                <div className="text17 mt-1 mb-1 lh-40 center bg-primary tc-white" style={{width:"40px", borderRadius:"20px"}}>{j+1}</div>
                                <div className="text17 ml-1 lh-60 tc-primary">{x.title}</div>
                                <div className="ml-auto mr-1 pt-1 pm-1">
                                    <img style={{width:"40px",height:"40px"}} src={x.show?"../images/arrow-7-up.png":"../images/arrow-7-down.png"}/>
                                </div>
                              </div>
                              { !x.show ? null : x.content.map((y, k) =>
                                <div className="text16" style={{textAlign:"left"}}>　　{y.text}</div>
                              )}
                              <div style={{height:"10px"}}></div>
                            </div>
                          )}
                          <div style={{height:"10px"}}></div>
                        </div>
                      )}
                    </div>

                    <div className="divx mt-1 p-2">
                        <div className="bg-primary" style={{width:"20px", height:"40px"}}></div>
                        <div className="ml-1 lh-40">温馨提示</div>
                    </div>
                    <div className="ml-1 mr-1 p-1 bg-white tc-dark text15" style={{border:"#dddddd 1px solid"}}>
                    　　免除保险公司责任条款、犹豫期、解除合同的手续及风险、费用扣除等内容，请您仔细阅读保险合同。本演示说明仅供参考，具体保险责任、免除保险公司责任的条款等内容以正式保险合同为准。
                    </div>

                    <div className="h-60"></div>
                </div>
            </div>
        )
    }
}

$(document).ready(() => {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})