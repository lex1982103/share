class Main extends React.Component {
    constructor() {
        super()
        let str = common.param("cust")
        this.state = {
            cust: str ? JSON.parse(str) : {}
        }
    }
    componentDidMount() {
        MF.setTitle("编辑受益人")
        APP.dict("cert,relation", r => {
            this.setState({
                certTypeDict: r.cert,
                relationDict: r.relation
            })
        })
    }
    close() {
        this.state.cust.name = this.refs.name.value
        this.state.cust.certNo = this.refs.certNo.value
        this.state.cust.scale = this.refs.scale.value
        MF.back(JSON.stringify(this.state.cust))
    }
    onValChange(key, val) {
        this.state.cust[key] = val
        this.setState({ cust: this.state.cust })
    }
    render() {
        let cust = this.state.cust
        return (
            <div>
                <div className="bg-white text18" style={{height:"80px", lineHeight:"80px", textAlign:"center"}}>
                    受益人信息
                </div>
                <div className="form-item text16">
                    <div className="form-item-label">姓名</div>
                    <div className="form-item-widget">
                        <input className="mt-1" ref="name" defaultValue={cust.name} placeholder="请输入受益人姓名"/>
                    </div>
                </div>
                <div className="form-item text16">
                    <div className="form-item-label">证件类型</div>
                    <div className="form-item-widget" onClick={v => {APP.pick("select", this.state.certTypeDict, this.onValChange.bind(this, "certType"))}}>
                        <div className={(cust.certType == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{cust.certType == null ? "请选择证件类型" : this.state.certTypeDict[cust.certType]}</div>
                        <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                    </div>
                </div>
                <div className="form-item text16">
                    <div className="form-item-label">证件号码</div>
                    <div className="form-item-widget">
                        <input className="mt-1" ref="certNo" defaultValue={cust.certNo} placeholder="请输入证件号码"/>
                    </div>
                </div>
                <div className="form-item text16">
                    <div className="form-item-label">与被保险人关系</div>
                    <div className="form-item-widget" onClick={v => {APP.pick("select", this.state.relationDict, this.onValChange.bind(this, "relation"))}}>
                        <div className={(cust.relation == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{cust.relation == null ? "请选择与被保险人关系" : this.state.relationDict[cust.relation]}</div>
                        <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                    </div>
                </div>
                <div className="form-item text16">
                    <div className="form-item-label">受益次序</div>
                    <div className="form-item-widget" onClick={v => {APP.pick("select", [1,2,3,4,5,6], this.onValChange.bind(this, "sequence"))}}>
                        <div className={(cust.relation == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{cust.sequence == null ? "请选择与被保险人关系" : "第" + (cust.sequence+1) + "顺位"}</div>
                        <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                    </div>
                </div>
                <div className="form-item text16">
                    <div className="form-item-label">受益比例</div>
                    <div className="form-item-widget">
                        <input className="mt-1" ref="scale" defaultValue={cust.scale} placeholder="请输入受益比例"/>
                    </div>
                </div>
                <div className="btn-fl text18 tc-white bg-primary" onClick={this.close.bind(this)}>确定</div>
          </div>
		)
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})