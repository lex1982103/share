class Insurance extends React.Component {
    constructor() {
        super();
        this.state = {
            edit: false,
            insuranceList: [{
                "applicant": {
                    "address": "啦啦啦啦啦啦啦啦啦啦",
                    "birthday": "1992-07-01",
                    "certName": "身份证",
                    "certNo": "21021219920701102X",
                    "certType": "1",
                    "certValidate": {
                        "certExpire": "",
                        "certLong": true
                    },
                    "city": {
                        "code": "110105",
                        "text": "北京市朝阳区"
                    },
                    "cityName": "北京市朝阳区",
                    "email": "656688312@qq.com",
                    "gender": "F",
                    "genderName": "女",
                    "mobile": "17604093071",
                    "name": "爱九二九"
                },
                "beneficiaryDeathType": "law",
                "beneficiaryLiveType": "insurant",
                "factors": {
                    "AMOUNT": "600000",
                    "A_BIRTHDAY": "1992-07-01",
                    "A_GENDER": "F",
                    "BIRTHDAY": "1992-07-01",
                    "GENDER": "F",
                    "INSURE": "term_20",
                    "RELATION": "self",
                    "ZONE": "110105",
                    "packId": "8"
                },
                "packCode": "90000001",
                "packDesc": [{
                    "name": "首年保额",
                    "text": "60万",
                    "val": "600000"
                }, {
                    "name": "保障期间",
                    "text": "20年期",
                    "val": "term_20"
                }, {
                    "name": "缴费期间",
                    "text": "交15年",
                    "val": "交15年"
                }, {
                    "name": "基本保额",
                    "text": "3万",
                    "val": "3万"
                }],
                "packId": "8",
                "pay": {
                    "bank": "02",
                    "bankCard": "19",
                    "bankText": "工商银行"
                },
                "photo": 0,
                "premium": 170.4,
                "read": {
                    "保险条款": "https://lifeins.iyunbao.com/rel/doc/bobcardif/iyb10004Clause.html",
                    "扣款知情同意书": "https://lifeins.iyunbao.com/rel/doc/bobcardif/applicantPayNotice.html",
                    "投保须知": "https://lifeins.iyunbao.com/rel/doc/bobcardif/ApplyNotice.html",
                    "网销投保人声明": "https://lifeins.iyunbao.com/rel/doc/bobcardif/netApplicat.html"
                },
                "smsCode": "123456",
                "smsKey": "17604093071",
                "vendor": {
                    "code": "bobcardif",
                    "id": 6,
                    "logo": "https://lifeins.iyunbao.com/images/logo/bobcardif.png",
                    "name": "中荷人寿"
                }
            },
                {
                    "applicant": {
                        "address": "啦啦啦啦啦啦啦啦啦啦",
                        "birthday": "1992-07-01",
                        "certName": "身份证",
                        "certNo": "21021219920701102X",
                        "certType": "1",
                        "certValidate": {
                            "certExpire": "",
                            "certLong": true
                        },
                        "city": {
                            "code": "110105",
                            "text": "北京市朝阳区"
                        },
                        "cityName": "北京市朝阳区",
                        "email": "656688312@qq.com",
                        "gender": "F",
                        "genderName": "女",
                        "mobile": "17604093071",
                        "name": "爱九二九"
                    },
                    "beneficiaryDeathType": "law",
                    "beneficiaryLiveType": "insurant",
                    "factors": {
                        "AMOUNT": "600000",
                        "A_BIRTHDAY": "1992-07-01",
                        "A_GENDER": "F",
                        "BIRTHDAY": "1992-07-01",
                        "GENDER": "F",
                        "INSURE": "term_20",
                        "RELATION": "self",
                        "ZONE": "110105",
                        "packId": "8"
                    },
                    "packCode": "90000001",
                    "packDesc": [{
                        "name": "首年保额",
                        "text": "60万",
                        "val": "600000"
                    }, {
                        "name": "保障期间",
                        "text": "20年期",
                        "val": "term_20"
                    }, {
                        "name": "缴费期间",
                        "text": "交15年",
                        "val": "交15年"
                    }, {
                        "name": "基本保额",
                        "text": "3万",
                        "val": "3万"
                    }],
                    "packId": "8",
                    "pay": {
                        "bank": "02",
                        "bankCard": "19",
                        "bankText": "工商银行"
                    },
                    "photo": 0,
                    "premium": 170.4,
                    "read": {
                        "保险条款": "https://lifeins.iyunbao.com/rel/doc/bobcardif/iyb10004Clause.html",
                        "扣款知情同意书": "https://lifeins.iyunbao.com/rel/doc/bobcardif/applicantPayNotice.html",
                        "投保须知": "https://lifeins.iyunbao.com/rel/doc/bobcardif/ApplyNotice.html",
                        "网销投保人声明": "https://lifeins.iyunbao.com/rel/doc/bobcardif/netApplicat.html"
                    },
                    "smsCode": "123456",
                    "smsKey": "17604093071",
                    "vendor": {
                        "code": "bobcardif",
                        "id": 6,
                        "logo": "https://lifeins.iyunbao.com/images/logo/bobcardif.png",
                        "name": "中荷人寿"
                    }
                }]

        }
    }
    componentDidMount() {
        window.MF && MF.setTitle("投保单");
        this.fetchClientList();
    }
    fetchClientList(){
       /* let that = this
        ajax('/order/list.json',{
            type: '2',
            from: 0,
            number: 10,
            userKey:localStorage.userKey
        },res=>{
            that.setState({
                insuranceList: res.content.list
            })
        })*/
        APP.openApply('/order/list.json', {type: '2', from: 0, number: 10},(res) => {
            this.setState({
                applicant: res.list
            }, () => {
                console.log(JSON.stringify(res.list))
            })
        })
    }
    /*编辑操作*/
    editClient (data) {}
    /*获取性别函数*/
    getSex(code) {
        return code == "M"? "男" : "女";
    }
    getLocalTime(nS) {
        return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
    }
    render(){
        const {
            edit
        } = this.state;
        return (
            <div className="insuranceMain">
                <ul>
                    {
                        this.state.insuranceList.map((prod,index)=>{
                            return(
                                <li className="insuranceBox">
                                    <div className="insuranceTile">
                                        <img src="../images/user.png" alt=""/>
                                        {prod.applicant.name && <font>{prod.applicant.name}</font>}
                                        {prod.applicant.createTime && <span>{prod.applicant.createTime}</span>}
                                        <p>标体通过</p>
                                    </div>

                                    <section>
                                        <dl>
                                            <dt>
                                                {prod.applicant.img && <div></div>}

                                            </dt>
                                            <dd>
                                                {prod.applicant.productName && <h2>百万安行</h2> }
                                                {prod.applicant.productContent && <p>百万安行个人阿斯兰的那卡斯达拉斯阿斯兰的纽卡斯淡蓝色的拉到你是看得见你</p>}
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>
                                                <div></div>
                                            </dt>
                                            <dd>
                                                <h2>百万安行</h2>
                                                <p>百万安行个人阿斯兰的那卡斯达拉斯阿斯兰的纽卡斯淡蓝色的拉到你是看得见你</p>
                                            </dd>
                                        </dl>
                                    </section>

                                    <div className="insurancePremium">
                                        <font>首付保险费合计: {prod.premium && <b>{prod.premium}</b>}元</font>
                                    </div>

                                    <div className="insuranceButton">
                                        <span onClick={() => {
                                            if (window.MF) {
                                                MF.navi("receipt/receipt.html");
                                            } else {
                                                location.href = "../receipt/receipt.html";
                                            }
                                        }}>查看</span>
                                        <span onClick={() => {
                                            if (window.MF) {
                                                MF.navi("xinhua_lx/notice_xh.html");
                                            } else {
                                                location.href = "../xinhua_lx/notice_xh.html";
                                            }
                                        }}>签单</span>
                                        <span onClick={() => {
                                            if (window.MF) {
                                                MF.navi("xinhua_lx/autograph_xh.html");
                                            } else {
                                                location.href = "../xinhua_lx/autograph_xh.html";
                                            }
                                        }}>支付</span>
                                        <span>续期投保</span>
                                    </div>

                                </li>
                            )
                        })
                    }
                </ul>
            </div>
        )
    }
}


ReactDOM.render(<Insurance/>, document.getElementById("root"))
