class Main extends React.Component {
    constructor() {
        super()

        this.state = {
            orderId: common.param("orderId"),
            genderDict: {"M":"男", "F":"女"},
            nationDict: {},
            marriageDict: {},
            certTypeDict: {},
            relationDict: {"00":"本人", "01":"夫妻"},
            mode: 0,
            cust: {}
        }
    }
    componentDidMount() {
        MF.setTitle("投保人")

        APP.dict("cert,marriage", r => {
            this.setState({
                nationDict: r.nation,
                certTypeDict: r.cert,
                marriageDict: r.marriage
            })
        })

        APP.apply.view(this.state.orderId, r => {
            let cust = r.detail ? r.detail.applicant : null
            this.setState({ cust: cust ? cust : {} })
        })
    }
    save() {
        let st = { mode: 0 }
        let c = this.state.cust

        if (this.state.mode == 1) {
            c.name = this.refs.name.value
            c.certNo = this.refs.certNo.value
            st.mode1 = true
        } else if (this.state.mode == 2) {
            st.mode2 = true
        } else if (this.state.mode == 3) {
            st.mode3 = true
        } else if (this.state.mode == 4) {
            st.mode4 = true
        }

        st.cust = c
        APP.apply.save({ id: this.state.orderId, detail: { applicant: c } }, v => {
            this.setState(st)
        })
    }
    next() {
        this.save()
        MF.navi("apply/insurant.html?orderId=" + this.state.orderId)
    }
    onValChange(key, val) {
        this.state.cust[key] = val
        this.setState({ cust: this.state.cust })
    }
    render() {
        let cust = this.state.cust;
        return (
            <div>
                <div className="div bg-white" style={{height:"100px", marginTop:"20px", textAlign:"center"}} onClick={v => { this.setState({ mode: 1 }) }}>
                    <div className="divx text18" style={{height:"60px", margin:"25px auto 0 auto", verticalAlign:"middle", lineHeight:"50px"}}>
                        <img style={{width:"50px", height:"50px", marginRight:"20px"}} src="../images/add.png"/>基本信息
                    </div>
                </div>
                { this.state.mode != 1 ? null : <div className="div bg-white">
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>投保人姓名</div>
                        <div style={{width:"410px", height:"60px"}}>
                            <input ref="name" defaultValue={cust.name} placeholder="请输入投保人姓名"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>性别</div>
                        <div style={{width:"370px", height:"60px"}} onClick={v => {APP.pick("select", this.state.genderDict, this.onValChange.bind(this, "gender"))}}>
                            <div className="tc-gray text16 ml-1">{cust.gender == null ? "请选择性别" : this.state.genderDict[cust.gender]}</div>
                        </div>
                        <img style={{width:"27px", height:"39px", marginTop:"10px"}} src="../images/right.png"/>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>国籍</div>
                        <div style={{width:"410px", height:"60px"}} onClick={v => {APP.pick("select", this.state.nationDict, this.onValChange.bind(this, "nation"))}}>
                            <div className="tc-gray text16 ml-1">{cust.nation == null ? "请选择国籍" : cust.nation}</div>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>出生日期</div>
                        <div style={{width:"410px", height:"60px"}} onClick={v => {APP.pick("date", { begin: "1900-01-01", end: new Date() }, this.onValChange.bind(this, "birthday"))}}>
                            <div className="tc-gray text16 ml-1">{cust.birthday == null ? "请选择出生日期" : cust.birthday}</div>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>婚姻状况</div>
                        <div style={{width:"410px", height:"60px"}} onClick={v => {APP.pick("select", this.state.marriageDict, this.onValChange.bind(this, "marriage"))}}>
                            <div className="tc-gray text16 ml-1">{cust.marriage == null ? "请选择婚姻状况" : this.state.marriageDict[cust.marriage]}</div>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>证件类型</div>
                        <div style={{width:"410px", height:"60px"}} onClick={v => {APP.pick("select", this.state.certTypeDict, this.onValChange.bind(this, "certType"))}}>
                            <div className="tc-gray text16 ml-1">{cust.certType == null ? "请选择证件类型" : this.state.certTypeDict[cust.certType]}</div>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>证件号码</div>
                        <div style={{width:"410px", height:"60px"}}>
                            <input ref="certNo" defaultValue={cust.certNo} placeholder="请输入证件号码"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>证件有效期</div>
                        <div style={{width:"410px", height:"60px"}} onClick={v => {APP.pick("date", { begin: new Date() }, this.onValChange.bind(this, "certValidDate"))}}>
                            <div className="tc-gray text16 ml-1">{cust.certValidDate == null ? "请选择证件有效期" : cust.certValidDate}</div>
                        </div>
                    </div>
                    <div className="divx text16" style={{height:"80px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"700px", height:"65px", textAlign:"right"}}>
                            <img style={{width:"100px", height:"50px", marginTop:"15px"}} src="../images/finish.png" onClick={this.save.bind(this)}/>
                        </div>
                    </div>
                </div> }
                <div className="div bg-white" style={{height:"100px", marginTop:"20px", textAlign:"center"}} onClick={v => { this.setState({ mode: 2 }) }}>
                    <div className="divx text18" style={{height:"60px", margin:"25px auto 0 auto", verticalAlign:"middle", lineHeight:"50px"}}>
                        <img style={{width:"50px", height:"50px", marginRight:"20px"}} src="../images/add.png"/>职业信息
                    </div>
                </div>
                { this.state.mode != 2 ? null : <div className="div bg-white">
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>工作单位</div>
                        <div style={{width:"410px", height:"60px"}}>
                            <input ref="company" defaultValue={cust.name} placeholder="请输入工作单位"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>职务</div>
                        <div style={{width:"370px", height:"60px"}}>
                            <input ref="workJob" defaultValue={cust.name} placeholder="请输入职务"/>
                        </div>
                        <img style={{width:"27px", height:"39px", marginTop:"10px"}} src="../images/right.png"/>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>职业大类</div>
                        <div style={{width:"410px", height:"60px"}} onClick={v => {APP.pick("select", this.state.occupationDict1, this.onValChange.bind(this, "occupation1"))}}>
                            <div className="tc-gray text16 ml-1">{cust.occupation1 == null ? "请选择职业大类" : cust.occupation1}</div>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>职业小类</div>
                        <div style={{width:"410px", height:"60px"}} onClick={v => {APP.pick("select", this.state.occupationDict2, this.onValChange.bind(this, "occupation"))}}>
                            <div className="tc-gray text16 ml-1">{cust.occupation == null ? "请选择职业小类" : cust.occupation}</div>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>职业代码</div>
                        <div style={{width:"410px", height:"60px"}}>
                            <div className="tc-gray text16 ml-1">{cust.occupation}</div>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>职业类别</div>
                        <div style={{width:"410px", height:"60px"}}>
                            <div className="tc-gray text16 ml-1">{cust.occupationLevel}</div>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>年收入（万元）</div>
                        <div style={{width:"410px", height:"60px"}}>
                            <input ref="income" defaultValue={cust.income} placeholder="请输入年收入"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{height:"80px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"700px", height:"65px", textAlign:"right"}}>
                            <img style={{width:"100px", height:"50px", marginTop:"15px"}} src="../images/finish.png" onClick={this.save.bind(this)}/>
                        </div>
                    </div>
                </div> }
                <div className="div bg-white" style={{height:"100px", marginTop:"20px", textAlign:"center"}} onClick={v => { this.setState({ mode: 3 }) }}>
                    <div className="divx text18" style={{height:"60px", margin:"25px auto 0 auto", verticalAlign:"middle", lineHeight:"50px"}}>
                        <img style={{width:"50px", height:"50px", marginRight:"20px"}} src="../images/add.png"/>联系方式
                    </div>
                </div>
                { this.state.mode != 3 ? null : <div className="div bg-white">
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>联系地址</div>
                        <div style={{width:"410px", height:"60px"}}>
                            <input ref="address" defaultValue={cust.name} placeholder="请输入联系地址"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>乡镇（街道）</div>
                        <div style={{width:"370px", height:"60px"}}>
                            <input ref="address1" defaultValue={cust.name} placeholder="请输入乡镇（街道）"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>村（社区）</div>
                        <div style={{width:"370px", height:"60px"}}>
                            <input ref="address2" defaultValue={cust.name} placeholder="请输入村（社区）"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>邮政编码</div>
                        <div style={{width:"370px", height:"60px"}}>
                            <input ref="zipcode" defaultValue={cust.name} placeholder="请输入邮政编码"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"670px", height:"60px"}}>联系方式（手机或者电话二者选其一）</div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>电话</div>
                        <div style={{width:"370px", height:"60px"}}>
                            <input ref="telephone" defaultValue={cust.name} placeholder="请输入电话 例：000-12345678"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>手机</div>
                        <div style={{width:"370px", height:"60px"}}>
                            <input ref="mobile" defaultValue={cust.name} placeholder="请输入手机"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>电子邮箱</div>
                        <div style={{width:"370px", height:"60px"}}>
                            <input ref="email" defaultValue={cust.name} placeholder="请输入电子邮箱"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>QQ号码</div>
                        <div style={{width:"370px", height:"60px"}}>
                            <input ref="qq" defaultValue={cust.name} placeholder="请输入QQ号码"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{padding:"10px 20px 10px 20px", height:"80px", lineHeight:"60px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"300px", height:"60px"}}>微信号码</div>
                        <div style={{width:"370px", height:"60px"}}>
                            <input ref="wechat" defaultValue={cust.name} placeholder="请输入微信号码"/>
                        </div>
                    </div>
                    <div className="divx text16" style={{height:"80px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"700px", height:"65px", textAlign:"right"}}>
                            <img style={{width:"100px", height:"50px", marginTop:"15px"}} src="../images/finish.png" onClick={this.save.bind(this)}/>
                        </div>
                    </div>
                </div> }
                <div className="div bg-white" style={{height:"100px", marginTop:"20px", textAlign:"center"}} onClick={v => { this.setState({ mode: 4 }) }}>
                    <div className="divx text18" style={{height:"60px", margin:"25px auto 0 auto", verticalAlign:"middle", lineHeight:"50px"}}>
                        <img style={{width:"50px", height:"50px", marginRight:"20px"}} src="../images/add.png"/>其他信息
                    </div>
                </div>
                { this.state.mode != 4 ? null : <div className="div bg-white">
                    <div className="divx text16" style={{height:"80px", borderTop:"#e6e6e6 solid 1px"}}>
                        <div style={{width:"700px", height:"65px", textAlign:"right"}}>
                            <img style={{width:"100px", height:"50px", marginTop:"15px"}} src="../images/finish.png" onClick={this.save.bind(this)}/>
                        </div>
                    </div>
                </div> }
                <div style={{height:"120px"}}></div>
                <div className="bottom text18 tc-primary">
                    <div style={{paddingLeft:"30px", width:"600px"}}></div>
                    <div style={{textAlign:"right", paddingRight:"30px", width:"150px"}} onClick={this.next.bind(this)}>下一步</div>
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