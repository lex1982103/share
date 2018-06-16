class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            applicant: {
                name: "王志军"
            },
            pay: {}
        }
    }
    componentDidMount() {
        MF.setTitle("缴费信息")
    }
    next() {
        MF.navi("apply/success.html")
    }
    onValChange(key, val) {
        this.state.pay[key] = val
        this.setState({ pay: this.state.pay })
    }
    render() {
        let pay = this.state.pay;
        return (
            <div>
                <div className="divx text16 bg-white pt-2 pb-2 pr-2 pl-2" style={{height:"100px", marginTop:"20px"}}>
                    <div style={{width:"300px", height:"60px"}}>投保单号</div>
                    <div style={{width:"410px", height:"60px"}}>
                        <input ref="applyNo" defaultValue={pay.applyNo} placeholder="请输入投保单号"/>
                    </div>
                </div>
                <div className="div bg-white" style={{marginTop:"20px"}}>
                    <div className="divx text16 pt-2 pb-2 pr-2 pl-2" style={{height:"100px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>银行卡影像</div>
                        <div style={{width:"370px", height:"60px"}}>
                            <img style={{width:"220px", height:"60px"}} src="../images/btn-scan.png" onClick={v => {APP.pick("scan", null, this.onValChange.bind(this, "bankCardImg"))}}/>
                        </div>
                    </div>
                    <div className="divx text16 pt-2 pb-2 pr-2 pl-2" style={{height:"100px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>银行卡号</div>
                        <div style={{width:"410px", height:"60px"}}>
                            <input ref="name" defaultValue={pay.bankCard} placeholder="请输入银行卡号"/>
                        </div>
                    </div>
                    <div className="divx text16 pt-2 pb-2 pr-2 pl-2" style={{height:"100px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>首期缴费方式</div>
                        <div style={{width:"370px", height:"60px"}} onClick={v => {APP.pick("select", this.state.payModeDict, this.onValChange.bind(this, "payMode"))}}>
                            <div className="tc-gray text16 ml-1">{pay.payMode == null ? "请选择首期缴费方式" : pay.payMode}</div>
                        </div>
                        <img style={{width:"27px", height:"39px", marginTop:"10px"}} src="../images/right.png"/>
                    </div>
                    <div className="divx text16 pt-2 pb-2 pr-2 pl-2" style={{height:"100px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>开户名</div>
                        <div style={{width:"370px", height:"60px"}}>{this.state.applicant.name}</div>
                    </div>
                    <div className="divx text16 pt-2 pb-2 pr-2 pl-2" style={{height:"100px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>开户银行</div>
                        <div style={{width:"370px", height:"60px"}} onClick={v => {APP.pick("select", this.state.bankDict, this.onValChange.bind(this, "bank"))}}>
                            <div className="tc-gray text16 ml-1">{pay.bank == null ? "请选择开户银行" : pay.bank}</div>
                        </div>
                        <img style={{width:"27px", height:"39px", marginTop:"10px"}} src="../images/right.png"/>
                    </div>
                    <div className="divx text16 pt-2 pb-2 pr-2 pl-2" style={{height:"100px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>续期缴费方式</div>
                        <div style={{width:"370px", height:"60px"}} onClick={v => {APP.pick("select", this.state.renewPayModeDict, this.onValChange.bind(this, "renewPayMode"))}}>
                            <div className="tc-gray text16 ml-1">{pay.renewPayMode == null ? "请选择续期缴费方式" : pay.renewPayMode}</div>
                        </div>
                        <img style={{width:"27px", height:"39px", marginTop:"10px"}} src="../images/right.png"/>
                    </div>
                </div>
                <div style={{height:"120px"}}></div>
                <div className="bottom text18 tc-primary">
                    <div style={{textAlign:"right", paddingRight:"30px", width:"750px"}} onClick={this.next.bind(this)}>下一步</div>
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