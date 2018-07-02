// const serverUrl = 'http://192.168.1.218:7666/' // save.json新增
const serverUrl = 'http://114.112.96.61:7666/' // save.json新增
class CreateClient extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
            genderDict: {"M":"男", "F":"女"},
            nationDict: {},
            marriageDict: {},
            certTypeDict: {},
            relationDict: {"00":"本人", "01":"夫妻"},
            index: 0,
            mode: 1,
            cust: common.customer('customerMsg')
        };
        this.finish = this.finish.bind(this);
    }
    componentDidMount() {
//        this.setState({cust: JSON.parse(sessionStorage.getItem("clientData"))}); // 获取单条数据显示到界面
        window.MF && MF.setTitle("新建客户")
        window.MF && APP.dict("cert,marriage,nation,occupation,relation", r => {
            let occMap = {}
            let occRank = {}
            let occDict = r.occupation.datas.map(v => {
                let c = v.smalls.map(w => {
                    occMap[w.occupationCode] = { text:w.occupationName }
                    occRank[w.occupationCode] = w.occupationLevel
                    return { code:w.occupationCode, text:w.occupationName }
                })
                occMap[v.occupationCode] = { text:v.occupationName, children:c }
                return { code:v.occupationCode, text:v.occupationName }
            })
            this.setState({
                occMap: occMap,
                occRank: occRank,
                occDict: occDict,
                nationDict: APP.toMapDict(r.nation),
                certTypeDict: r.cert,
                relationDict: r.relation,
                marriageDict: r.marriage
            })
        })
    }
    save() {
        let c = this.state.cust;
        if (this.state.mode == 1) {
            c.name = this.refs.name.value
            c.cert.certNo = this.refs.certNo.value
            /*if (!c.name || !c.name.length) {
                console.log('请填写用户姓名');
                return;
            }/!*if (!c.gender || !c.gender.length) {
                console.log('请选择性别');
                return;
            } if (!c.nation || !c.nation.length) {
                console.log('请选择国籍');
                return;
            } if (!c.birthday || !c.birthday.length) {
                console.log('请选择出生日期');
                return;
            } if (!c.marriage || !c.marriage.length) {
                console.log('请选择婚姻状况');
                return;
            }*!/ if (!c.certNo || !c.certNo.length) {
                console.log('请填写证件号');
                return;
            } /!*if (!c.certValidDate || !c.certValidDate.length) {
                console.log('请填写证件有效期');
                return;
            }*!/*/
            c.mode1 = true
        } else if (this.state.mode == 2) {
            c.company = this.refs.company.value
            c.workJob = this.refs.workJob.value
            c.income = this.refs.income.value
           /* if (!c.company || !c.company.length) {
                console.log('请填写工作单位');
                return;
            }if (!c.workJob || !c.workJob.length) {
                console.log('请填写职位');
                return;
            } /!*if (!c.occupation1 || !c.occupation1.length) {
                console.log('请选择职业大类');
                return;
            } if (!c.occupation || !c.occupation.length) {
                console.log('请选择职业小类');
                return;
            } if (!c.occupationCode || !c.occupationCode.length) {
                console.log('请填写职业代码');
                return;
            } if (!c.occupationLevel || !c.occupationLevel.length) {
                console.log('请填写职业类别');
                return;
            }*!/ if (!c.income || !c.income.length) {
                console.log('请填写年收入');
                return;
            }*/
            c.mode2 = true
        } else if (this.state.mode == 3) {
            c.address = this.refs.address.value
            c.cityText = this.refs.address1.value
            c.address2 = this.refs.address2.value
            c.telephone = this.refs.telephone.value
            c.mobile = this.refs.mobile.value
            c.qq = this.refs.qq.value
            c.wechat = this.refs.wechat.value
            c.zipcode = this.refs.zipcode.value
            c.email = this.refs.email.value
            /*if (!c.address || !c.address.length) {
                console.log('请填写联系地址');
                return;
            }if (!c.cityText || !c.cityText.length) {
                console.log('请填写乡镇(街道)');
                return;
            } if (!c.address2 || !c.address2.length) {
                console.log('请填写村(社区)');
                return;
            } if ((!c.telephone || !c.telephone.length) && (!c.mobile || !c.mobile.length)) {
                console.log('手机或者电话二者选其一');
                return;
            } if (!c.qq || !c.qq.length) {
                console.log('请填写qq号码');
                return;
            } if (!c.wechat || !c.wechat.length) {
                console.log('请填写微信号码');
                return;
            } if (!c.zipcode || !c.zipcode.length) {
                console.log('请填写邮政编码');
                return;
            } if (!c.email || !c.email.length) {
                console.log('请填写邮箱');
                return;
            }*/
            c.mode3 = true
        } else if (this.state.mode == 4) {
            c.mode4 = true
        }

        this.state.cust= c;
        this.setState({mode: 0})

    }
    finish() {
        let cust = this.state.cust;
        let postData = {
            "id": cust.id || '',
            "name": cust.name,
            "gender":cust.gender,
            "birthday":cust.birthday,
            "cert_type": cust.cert.certType,
            "cert_no": cust.cert.certNo,
            "type": '1',
            detail: {
                "mobile":cust.mobile,
                "email": cust.email,
                "city": cust.city,
                "address": cust.address,
                "birthday": cust.birthday,
                "cert": {
                    "certNo": cust.cert.certNo,
                    "certLong": true,
                    "certExpire": cust.certExpire || "",
                    "certType": cust.cert.certType
                },
                "channelId": cust.channelId || 1,
                "city": cust.city || "",
                "cityText": cust.cityText,
                "company": cust.company,
                "companyAddress": cust.companyAddress || "",
                "education": cust.education ||"",
                "gender": cust.gender,
                "marriage": cust.marriage,
                "mobile": cust.marriage,
                "phone": cust.phone,
                "name": cust.name,
                "nation": cust.nation,
                "occupation": cust.occupation,
                "owner": 1,
                "partTimeJob": cust.partTimeJob || "",
                "relation":  cust.relation ||"",
                "workDetail": cust.workDetail || "",
                "zipcode": cust.zipcode
            }
        }
        this.save();
        /*if (!cust.mode1 ||
            !cust.mode2 ||
            !cust.mode3 ||
            !cust.mode4) {
            console.log('请补充信息');
            return;
        }*/
         APP.list('/customer/save.json', postData, r => {
                 window.MF && MF.navi("client/client_list.html");
            })
    }
    newInsurant() {
        this.state.cust.push({});
        this.setState({ cust: this.state.cust })
    }
    onInsurantSwitch(i) {
        this.setState({ mode: 0, index: i })
    }
    onValChange(key, val) {
        if (key === 'certType') {
            this.state.cust.cert[key] = val;
        } else {
            this.state.cust[key] = val;
        }
        if (key == "occupation1") {
            this.state.cust.occupation = null
            this.state.cust.occupationLevel = null
        } else if (key == "occupation") {
            this.state.cust.occupationLevel = this.state.occRank[this.state.cust.occupation]
        }
        this.setState({ cust: this.state.cust })
    }

    render(){
        let cust = this.state.cust
        return (
            <div>
                <div className="divx bg-white pl-3 pr-3" style={{height:"100px", marginTop:"20px", textAlign:"center"}} onClick={v => { this.setState({ mode: this.state.mode==1?0:1 }) }}>
                    <div className="divx text18" style={{height:"60px", margin:"25px auto 0 auto", verticalAlign:"middle", lineHeight:"50px"}}>
                        <img style={{width:"50px", height:"50px", margin:"0 20px 0 65px"}} src={"../images/"+(this.state.mode==1?"sub":"add")+".png"}/>基本信息
                    </div>
                    <div style={{width:"65px"}}>{ cust.mode1 ? <img style={{width:"65px", height:"50px", marginTop:"25px", float:"right"}} src={"../images/filled.png"}/> : null }</div>
                </div>
                { this.state.mode != 1 ? null : <div className="div">
                    <div className="form-item text16">
                        <div className="form-item-label">姓名</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="name" defaultValue={cust.name} placeholder="请输入投保人姓名"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">性别</div>
                        <div className="form-item-widget" onClick={v => {APP.pick("select", this.state.genderDict, this.onValChange.bind(this, "gender"))}}>
                            <div className={(cust.gender == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{cust.gender == null ? "请选择性别" : this.state.genderDict[cust.gender]}</div>
                            <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">国籍</div>
                        <div className="form-item-widget" onClick={v => {APP.pick("select", this.state.nationDict, this.onValChange.bind(this, "nation"))}}>
                            <div className={(cust.nation == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{cust.nation == null ? "请选择国籍" : this.state.nationDict[cust.nation]}</div>
                            <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">出生日期</div>
                        <div className="form-item-widget" onClick={v => {APP.pick("date", { begin: "1900-01-01", end: new Date() }, this.onValChange.bind(this, "birthday"))}}>
                            <div className={(cust.birthday == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{cust.birthday == null ? "请选择出生日期" : cust.birthday}</div>
                            <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">婚姻状况</div>
                        <div className="form-item-widget" onClick={v => {APP.pick("select", this.state.marriageDict, this.onValChange.bind(this, "marriage"))}}>
                            <div className={(cust.marriage == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{cust.marriage == null ? "请选择婚姻状况" : this.state.marriageDict[cust.marriage]}</div>
                            <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
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
                        <div className="form-item-label">证件有效期</div>
                        <div className="form-item-widget" onClick={v => {APP.pick("date", { begin: new Date() }, this.onValChange.bind(this, "certValidDate"))}}>
                            <div className={(cust.certValidDate == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{cust.certValidDate == null ? "请选择证件有效期" : cust.certValidDate}</div>
                            <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <img className="mt-1 ml-auto mr-3" style={{width:"120px", height:"60px"}} src="../images/finish.png" onClick={this.save.bind(this)}/>
                    </div>
                </div> }
                <div className="divx bg-white pl-3 pr-3" style={{height:"100px", marginTop:"20px", textAlign:"center"}} onClick={v => { this.setState({ mode: this.state.mode==2?0:2 }) }}>
                    <div className="divx text18" style={{height:"60px", margin:"25px auto 0 auto", verticalAlign:"middle", lineHeight:"50px"}}>
                        <img style={{width:"50px", height:"50px", margin:"0 20px 0 65px"}} src={"../images/"+(this.state.mode==2?"sub":"add")+".png"}/>职业信息
                    </div>
                    <div style={{width:"65px"}}>{ cust.mode2 ? <img style={{width:"65px", height:"50px", marginTop:"25px", float:"right"}} src={"../images/filled.png"}/> : null }</div>
                </div>
                { this.state.mode != 2 ? null : <div className="div">
                    <div className="form-item text16">
                        <div className="form-item-label">工作单位</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="company" defaultValue={cust.company} placeholder="请输入工作单位"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">职务</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="workJob" defaultValue={cust.workJob} placeholder="请输入职务"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">职业大类</div>
                        <div className="form-item-widget" onClick={v => {APP.pick("select", this.state.occDict, this.onValChange.bind(this, "occupation1"))}}>
                            <div className={(cust.occupation1 == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{cust.occupation1 == null ? "请选择职业大类" : this.state.occMap[cust.occupation1].text}</div>
                            <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">职业小类</div>
                        <div className="form-item-widget" onClick={v => {APP.pick("select", this.state.occMap[cust.occupation1].children, this.onValChange.bind(this, "occupation"))}}>
                            <div className={(cust.occupation == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{cust.occupation == null ? "请选择职业小类" : this.state.occMap[cust.occupation].text}</div>
                            <img className="mt-2 mr-0" style={{width:"27px", height:"39px"}} src="../images/right.png"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">职业代码</div>
                        <div className="form-item-widget">
                            <div className={(cust.occupation == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{cust.occupation}</div>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">职业类别</div>
                        <div className="form-item-widget">
                            <div className={(cust.occupationLevel == null ? "tc-gray " : "") + "text16 ml-1 mr-auto"}>{cust.occupationLevel == null ? "" : cust.occupationLevel+"类"}</div>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">年收入(万元)</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="income" defaultValue={cust.income} placeholder="请输入年收入"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <img className="mt-1 ml-auto mr-3" style={{width:"120px", height:"60px"}} src="../images/finish.png" onClick={this.save.bind(this)}/>
                    </div>
                </div> }
                <div className="divx bg-white pl-3 pr-3" style={{height:"100px", marginTop:"20px", textAlign:"center"}} onClick={v => { this.setState({ mode: this.state.mode==3?0:3 }) }}>
                    <div className="divx text18" style={{height:"60px", margin:"25px auto 0 auto", verticalAlign:"middle", lineHeight:"50px"}}>
                        <img style={{width:"50px", height:"50px", margin:"0 20px 0 65px"}} src={"../images/"+(this.state.mode==3?"sub":"add")+".png"}/>联系方式
                    </div>
                    <div style={{width:"65px"}}>{ cust.mode3 ? <img style={{width:"65px", height:"50px", marginTop:"25px", float:"right"}} src={"../images/filled.png"}/> : null }</div>
                </div>
                { this.state.mode != 3 ? null : <div className="div">
                    <div className="form-item text16">
                        <div className="form-item-label">联系地址</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="address" defaultValue={cust.address} placeholder="请输入联系地址"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">乡镇(街道)</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="address1" defaultValue={cust.cityText} placeholder="请输入乡镇（街道）"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">村(社区)</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="address2" defaultValue={cust.address2} placeholder="请输入村（社区）"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">邮政编码</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="zipcode" defaultValue={cust.zipcode} placeholder="请输入邮政编码"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label" style={{width:"670px"}}>联系方式(手机或者电话二者选其一)</div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">电话</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="telephone" defaultValue={cust.telephone} placeholder="请输入电话 例：000-12345678"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">手机</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="mobile" defaultValue={cust.mobile} placeholder="请输入手机"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">电子邮箱</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="email" defaultValue={cust.email} placeholder="请输入电子邮箱"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">QQ号码</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="qq" defaultValue={cust.qq} placeholder="请输入QQ号码"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <div className="form-item-label">微信号码</div>
                        <div className="form-item-widget">
                            <input className="mt-1" ref="wechat" defaultValue={cust.wechat} placeholder="请输入微信号码"/>
                        </div>
                    </div>
                    <div className="form-item text16">
                        <img className="mt-1 ml-auto mr-3" style={{width:"120px", height:"60px"}} src="../images/finish.png" onClick={this.save.bind(this)}/>
                    </div>
                </div> }
                <div className="divx bg-white pl-3 pr-3" style={{height:"100px", marginTop:"20px", textAlign:"center"}} onClick={v => { this.setState({ mode: this.state.mode==4?0:4 }) }}>
                    <div className="divx text18" style={{height:"60px", margin:"25px auto 0 auto", verticalAlign:"middle", lineHeight:"50px"}}>
                        <img style={{width:"50px", height:"50px", margin:"0 20px 0 65px"}} src={"../images/"+(this.state.mode==4?"sub":"add")+".png"}/>其他信息
                    </div>
                    <div style={{width:"65px"}}>{ cust.mode4 ? <img style={{width:"65px", height:"50px", marginTop:"25px", float:"right"}} src={"../images/filled.png"}/> : null }</div>
                </div>
                { this.state.mode != 4 ? null : <div className="div">
                    <div className="form-item text16">
                        <img className="mt-1 ml-auto mr-3" style={{width:"120px", height:"60px"}} src="../images/finish.png" onClick={this.save.bind(this)}/>
                    </div>
                </div> }

                <div style={{height:"120px"}}></div>
                <div className="bottom text18 tc-primary">
                    <div className="ml-3 mr-0" style={{width:"300px"}}></div>
                    <div onClick={ () => {this.finish ()}}>
                        <div className="ml-0 mr-0" style={{width:"390px", textAlign:"right"}}>
                            完成
                        </div>

                    </div>
                </div>
            </div>
        )
    }

}
ReactDOM.render(<CreateClient/>, document.getElementById("root"))