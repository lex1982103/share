class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            proposalId: common.param("proposalId"),
            genderDict: {"M":"男", "F":"女"},
            cust: [{}],
            mode: -1
        }
    }
    componentDidMount() {
        MF.setTitle("信息补充")
        APP.proposal.view(this.state.proposalId, r => {
            let cust = [r.applicant]
            r.detail.map(planId => {
                APP.proposal.viewPlan(planId, plan => {
                    cust.push(plan.insurant)
                    this.setState({ cust: cust })
                })
            })
        })
    }
    save() {
        APP.proposal.supply(this.state.proposalId, { other: { custs: this.state.cust } }, v => {
            this.setState({ mode: -1, cust: this.state.cust })
        })
    }
    next() {
        APP.proposal.save(this.state.proposalId, v => {
            MF.navi("proposal/preview.html?proposalId=" + this.state.proposalId)
        })
    }
    onValChange(index, key, e) {
        this.state.cust[index][key] = e.value
        this.setState({ cust: this.state.cust })
    }
    render() {
        return (
            <div>
                { this.state.cust.map((v, i) =>
                    <div>
                        <div className="divx bg-white pl-3 pr-3" style={{height:"100px", marginTop:"20px", textAlign:"center"}} onClick={v => { this.setState({ mode: this.state.mode==i?-1:i }) }}>
                            <div className="divx text18" style={{height:"60px", margin:"25px auto 0 auto", verticalAlign:"middle", lineHeight:"50px"}}>
                                <img style={{width:"50px", height:"50px", margin:"0 20px 0 65px"}} src={"../images/"+(this.state.mode==i?"sub":"add")+".png"}/>{i==0?"投保人":"被保险人"+i}信息
                            </div>
                            <div style={{width:"65px"}}>{ v.filled ? <img style={{width:"65px", height:"50px", marginTop:"25px", float:"right"}} src={"../images/filled.png"}/> : null }</div>
                        </div>
                        { this.state.mode != i ? null:
                            <div className="div">
                                <div className="form-item text16">
                                    <div className="form-item-label">姓名</div>
                                    <div className="form-item-widget">
                                        <input className="mt-1" defaultValue={v.name} placeholder="请输入姓名" onChange={this.onValChange.bind(this, i, "name")}/>
                                    </div>
                                </div>
                                <div className="form-item text16">
                                    <div className="form-item-label">性别</div>
                                    <div className="form-item-widget" onClick={v => {APP.pick("select", this.state.genderDict, this.onValChange.bind(this, i, "gender"))}}>
                                        <div className={(v.gender == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{v.gender == null ? "请选择性别" : this.state.genderDict[v.gender]}</div>
                                        <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                                    </div>
                                </div>
                                <div className="form-item text16">
                                    <div className="form-item-label">出生日期</div>
                                    <div className="form-item-widget" onClick={v => {APP.pick("date", { begin: "1900-01-01", end: new Date() }, this.onValChange.bind(this, i, "birthday"))}}>
                                        <div className={(v.birthday == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{v.birthday == null ? "请选择出生日期" : v.birthday}</div>
                                        <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                                    </div>
                                </div>
                                <div className="form-item text16">
                                    <img className="mt-1 ml-auto mr-3" style={{width:"120px", height:"60px"}} src="../images/finish.png" onClick={this.save.bind(this)}/>
                                </div>
                            </div> 
                        }
                    </div> 
                )}
                <div style={{height:"120px"}}></div>
                <div className="bottom text18 tc-primary">
                    <div className="ml-3 mr-auto">
                    </div>
                    <div className="mr-3" onClick={this.next.bind(this)}>
                        预览建议书
                    </div>
                </div>
            </div>
		)
    }
}

$(document).ready(() => {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})
