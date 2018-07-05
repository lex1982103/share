class Autograph extends React.Component {
    constructor() {
        super()
        this.state = {
            autographlistTop: [  // 基本信息
                {
                    name: '姓名',
                    value: 'name'
                },
                {
                    name: '性别',
                    value: 'gender'
                },
                {
                    name: '出生日期',
                    value: 'birthday'
                },
                {
                    name: '证件类型',
                    value: 'certType'
                },
                {
                    name: '证件号码',
                    value: 'certNo'
                },
                {
                    name: '证件有效期限',
                    value: 'certValidDate'
                },
                {
                    name: '国籍',
                    value: 'nation'
                },
                {
                    name: '婚姻及子女情况',
                    value: 'marriage'
                },
                {
                    name: '与被保险人关系',
                    value: 'relation'
                },
                {
                    name: '工作单位',
                    value: 'company'
                },
                {
                    name: '职位名称',
                    value: 'workJob'
                },
                {
                    name: '职业编码',
                    value: 'occupation'
                },
                {
                    name: '移动电话',
                    value: 'mobile'
                },
                {
                    name: '固定电话',
                    value: 'telephone'
                }
            ],
            autographlistBom: [  // 基本信息
                {
                    name: '通讯(常住)地址',
                    value: 'address'
                },
                {
                    name: '邮编',
                    value: 'zipcode'
                },
                {
                    name: '每年可支配收入',
                    value: 'income'
                },
                {
                    name: '居民类型',
                    value: 'myType'
                },
                {
                    name: '是否参加基本医保保障',
                    value: 'hospital'
                },
                {
                    name: '税收居民身份',
                    value: '测试'
                },
            ],
            beneficiaryList: [1,2,3], // 身故保险金受益人
            especiallyList: [1,2,3,4,5,6,7,8],  // 税收居民身份
            informList: [   // 投保告知
                {
                    name: '身高(厘米)',
                    value: '170'
                },
                {
                    name: '体重(公斤)',
                    value: '59'
                },
                {
                    name: '您是否有吸烟习惯？',
                    value: '否'
                },
                {
                    name: '您是否有每天饮自洒的习惯？',
                    value: '否'
                },
                {
                    name: '在过去的2年中，您是否在国外持续展住超过6个月成准备在1年内出国?',
                    value: '否'
                },
                {
                    name: '您足否参与任何危险的运动或赛，(调水、跳伞、滑用、高.长攀岩,探险、武术比赛、摔跤比赛、特技表演、赛马、赛车、驾驶或乘坐非民航客机的私人飞行活动)?',
                    value: '否'
                },
                {
                    name: '您是否有被保险公司拒绝承保，或加费承保.或延期承保，或附加特别约定承保的经历?',
                    value: '否'
                },
                {
                    name: '您是否以被保险人的身份投保过或正在申济其他保险公司人寿保险?',
                    value: '否'
                },
                {
                    name: '您的父毋、兄弟、如妹是否患有恶性肿痛、A症、白血病、冠心14、心肌病、麟尿病、中风(脑出血、脑便塞)、任何遗传性疾病?',
                    value: '否'
                },
                {
                    name: '您是否患有或曾经患仃高血压、冠心病、心肌病、中风(脑出血、脑使坳、动脉瘤、箱尿病、胰腺炎、慢性支气管炎 哮峭?',
                    value: '否'
                },
                {
                    name: '您是否患有或曾经患有甲状膝结节、甲状膝功能亢进或减退、肝炎或肝炎病毒倪带者、肝砚化、肾炎、肾病综合征、丹功能不全、帕金森病、系统性红斑娘疮、艾滋病?',
                    value: '否'
                },
                {
                    name: '您是否患有或曾经患有任何肿翻或痛症、原位燎、结肠息肉、白血病、任何身体成智力残疾、铭痛或挤神障双打',
                    value: '否'
                },
                {
                    name: '在过去的5年内，您足否因上述告知情况以外的疾病住院洽疗，或被医生建议佳院治疗，或因疾病连续服药超过l个月',
                    value: '否'
                },
                {
                    name: '.在过去2年内，您是否接受过X光，超声，CT，核班，心.川侧，内窥镜，病理检汽，血液、尿液投代，及儿他待殊价 夜JI枪夜结染异常',
                    value: '否'
                },
                {
                    name: '在过去的1年内，您是否因出现症状或身体不适而接受治疗或被医生建议治疗，或因此连续服药超过1个月?',
                    value: '否'
                }
            ],
            isElectronics: true, // 是否电子签名
            cust: {}
        }
    }
    testPopupDialog1(id,isT){
        // var oHead = document.getElementsByTagName('HEAD').item(0);
        // var oScript= document.createElement("script");
        // oScript.type = "text/javascript";
        // oScript.src="qianming.js";
        // oHead.appendChild( oScript);
        sessionStorage.clear('ist');
        sessionStorage.setItem('ist',isT)
        testPopupDialog(id)
    }
    componentDidMount() {
        window.MF && MF.setTitle("投保单预览");
        APP.apply.view(common.param("orderId"), r => {
            console.log(JSON.stringify(r.detail))
            this.setState({
                cust: r.detail
            })
        })
    }

    submit() {
        this.next()
    }
    next() {
        if(window.MF){
            MF.navi("apply/success.html?orderId=" + this.state.orderId)
        }else{
            location.href = "../apply/success.html?orderId=" + this.state.orderId
        }

    }
    render() {
        const { cust } = this.state;
        return (
            <div className="autograph-table">
                <div id="other">
                    <div className="table-head">
                        <div>
                            <p>电子投保书</p>
                            <p>EUA001</p>
                        </div>
                        <div>对应电子投保申请确认号码：<span>4454454554</span></div>
                    </div>
                    <div className="autograph-top">
                        <div className="autograph-topLeft">
                            基本信息
                        </div>
                        <div className="autograph-topRight">
                            <p>
                                <span>个人资料</span>
                                <span>投保人</span>
                                <span>被保险人</span>
                            </p>
                            <ul>
                                <li>
                                    {
                                        this.state.autographlistTop.map(item => {
                                            return (
                                                <p>{item.name}</p>
                                            )
                                        })
                                    }
                                </li>
                                <li>
                                    {
                                        this.state.autographlistTop.map(item => {
                                            return (
                                                <p>{Object.keys(cust).length && cust.applicant[item.value] || '无'}</p>
                                            )
                                        })
                                    }
                                </li>
                                <li>
                                    {
                                        this.state.autographlistTop.map(item => {
                                            return (
                                                <p>{Object.keys(cust).length && cust.insurants[0][item.value]}</p>
                                            )
                                        })
                                    }
                                </li>
                            </ul>

                            <p className="autograph-email">
                                <span>
                                    电子邮箱
                                </span>
                                <div>
                                    <p><span>电子保单服务  选择“电子保单服务”不影响纸质保险单的送达。</span></p>
                                    <p><span>电子保单服务  选择“电子保单服务”不影响纸质保险单的送达。</span></p>
                                </div>
                            </p>
                            {
                                this.state.autographlistBom.map(item => {
                                return (
                                        <p>
                                            <span>{item.name}</span>
                                            <span>{item.value}</span>
                                            <span>11</span>
                                        </p>
                                )
                            }) 
                            }

                        </div>
                    </div>
                    <div className="autograph-mid">
                        <div className="autograph-topLeft">
                            ■
                        </div>
                        <div>
                            <p>
                                <span>身故保险金受益人为被保人的法定继承人</span>
                            </p>
                        </div>
                    </div>
                    <div className="autograph-beneficiary">
                        <div className='beneficiary-left'>
                            身故保险金受益人
                        </div>
                        <ul className='beneficiary-right'>
                            {
                                this.state.beneficiaryList.map(item => {
                                return (
                                        <li>
                                            <div>{item}</div>
                                            <div>
                                                <p>
                                                    <div>姓名:<span></span></div>
                                                </p>
                                                <p>
                                                    <div>证件类型:<span></span></div>
                                                    <div>证件号码:<span></span></div>
                                                </p>
                                                <p>
                                                    <div>与被保人关系:<span></span></div>
                                                    <div>国籍:<span></span></div>
                                                </p>
                                                <p className="beneficiary-address">
                                                    <div>通讯(常住)地址:<span></span></div>
                                                    <div>邮政编码:<span></span></div>
                                                </p>
                                            </div>
                                            <div>
                                                <p>
                                                    <div>出生日期:<span></span></div>
                                                    <div>性别:<span></span></div>
                                                </p>
                                                <p>
                                                    <div>证件有效期限:<span></span></div>
                                                </p>
                                                <p>
                                                    <div>受益顺序:<span></span></div>
                                                    <div>受益份额%<span></span></div>
                                                </p>
                                                <p>
                                                    <div>职业名称/编码:<span></span></div>
                                                </p>
                                                <p>
                                                    <div>联系电话:<span></span></div>
                                                </p>
                                            </div>
                                        </li>
                                )
                            }) 
                            }
                        </ul>
                    </div>
                    <div className="matter-main">
                        <div className='matter-left'>
                            投保事项
                        </div>
                        <ul>
                            <li>
                                <span>保险名称</span>
                                <span>险种代码</span>
                                <span>保障计划类别</span>
                                <span>保障期间</span>
                                <span>保险金额/投保份数</span>
                                <span>交费方式</span>
                                <span>交费期间
                                    <p>(年或至周岁)</p>
                                </span>
                                <span>
                                    保险费
                                    <p>(期交仅指首期)</p>
                                </span>
                            </li>
                            <li>
                                <span>实施第三方</span>
                                <span>3456787</span>
                                <span></span>
                                <span>15</span>
                                <span>343546</span>
                                <span>一次交清</span>
                                <span>一次交清</span>
                                <span>345565</span>
                            </li>
                            <li>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                            </li>
                            <li>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                            </li>
                            <li>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                            </li>
                            <li>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                            </li>
                            <li>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                            </li>
                            <li>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                            </li>
                            <li className="matter-total">
                                <div>
                                    <h6>保障费合计: <b>阿德是凡达斯</b></h6>
                                    <h6>(小写) <b>￥：5455</b></h6>
                                </div>
                            </li>
                            <li className="matter-total">
                                <div>
                                    <h6>保障费合计: <b>阿德是凡达斯</b></h6>
                                    <h6>小写) <b>￥：5455</b></h6>
                                </div>
                            </li>
                            <ol className="matter-bom">
                                <li>
                                    <div>首期</div>
                                    <div>
                                        <div>
                                            <h6>交费形式:<b>阿德是凡达斯</b></h6>
                                            <h6>指定账户名字:<b>爱的</b></h6>
                                        </div>
                                        <div>
                                            <h6>开户行:<b>农业银行</b></h6>
                                            <h6>账户:<b>4554655655646</b></h6>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div>续期</div>
                                    <div>
                                        <div>
                                            <h6>交费形式:<b>阿德是凡达斯</b></h6>
                                            <h6>指定账户名字:<b>爱的</b></h6>
                                        </div>
                                        <div>
                                            <h6>开户行:<b>农业银行</b></h6>
                                            <h6>账户:<b>4554655655646</b></h6>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div>领取信息</div>
                                    <div className="receive-msg">
                                        <div>
                                            <div className="receive-scj">生存金</div>
                                            <div className="receive-price">
                                                <div>
                                                    领取年龄：<span>周岁</span>
                                                </div>
                                                <div>
                                                    领取期限：<span></span>
                                                </div>
                                                <div>
                                                    领取方式：<span></span>
                                                </div>
                                                <div>
                                                    领取频率：<span></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div>
                                            <div>现金红利</div>
                                            <div>领取方式:</div>
                                        </div>
                                        <div>
                                            提示:若投保人险种五约定领取，不涉及上述领取信息。
                                        </div>
                                    </div>
                                </li>
                            </ol>
                        </ul>
                    </div>
                    <div className="especially-main">
                        <div className='especially-left'>
                            特别申请
                        </div>
                        <ul className='especially-right'>
                            <li><p>1、本人投保的险种，效力因发生责任免除、解除等事项终止时，险种，的附加险，其效力不受本保险单的其他险种效力的影响。</p></li>
                            <li>
                                <p>本人申清贵公司在本人生存至周岁保单生效对应日时将“’基本保险金额</p>
                                <p>在该保单生效对应日，本保险单及所附批单下所有保险期间为一年的险种的累计保险金额。</p>
                            </li>
                        </ul>
                    </div>
                    {
                        this.state.isElectronics ? 
                            <div>
                                <div className="text-center">税收居民身份</div>
                                <div className="text-left">
                                    投保人: <span>本身</span>
                                </div>
                                <ul className="especially-list">
                                    {
                                        this.state.especiallyList.map(item => {
                                            return (
                                                <li>
                                                    <div>{item}</div>
                                                    <div>
                                                        出生地
                                                        <span>东方舵手</span>
                                                    </div>
                                                </li>
                                            )
                                        })
                                    }
                                </ul> 
                            </div>
                            : ''
                    }
                    <ul className="inform-main">
                        <div className="text-center">投保告知</div>
                        <div className="text-left">
                            被保人: <span>本身</span>
                        </div>
                        {
                            this.state.informList.map( (item,index) => {
                                return (
                                    <li>
                                        <div>
                                        {index + 1}.{item.name}
                                        </div>
                                        <div>
                                            答案：<span>{item.value}</span>
                                        </div>
                                    </li>
                                )
                            })
                        }
                    </ul>
                    <div className="text-center">公司声明</div>
                    <ul className="statement-list">
                        <li>本公司本着最大诚信的原则，向您明确各保险事项。一切与保险合同相违背的任何形式的说明均属无效，一切权益均以保险合同为凭。</li>
                        <li><b>投保须知：</b></li>
                        <li>投保时，向您提供保险条款、说明保险合同内容、提示并明确说明免除保险人责任的条款是本公司的法定义务，本公司将切实行。您可以对不理解的保险合同内容进行询问。为维护您的合法权益，请在阅读并理解保险条款及新型保险产品说明书的各项内容后选择适合的保险金额和保险期间方可投保。</li>
                        <li><b>填写须知：</b> 请您真实、准确、完整填写投保书各项内容，尤其是投被保险人的姓名、性别、生日、证件类型、证件号码、职业、联系电话及地址、投被保险人关系、银行账号、税收居民身份信息等，以上信息主要用于计算并收取保费、核保、寄送保单、客户回访和税收居民身份信息收集等，以便为您提供更优质的服务。如信息缺失、不实将会对您的利益产生不利影响，(nJ时本公司承诺未经您的同意，不会将您的信息用于人身保险公司和第三方机构的销咨活动。</li>
                        <li><b>交费须知：</b> 投保人应根据自身财务状况，确定选择适合的交费期间和交费金额，如未能按期足额交纳保险费，有可能导致保险合同效力中止或被解除。</li>
                        <li><b>未成年人投保：</b>父母为其未成年子女投保的人身保险，因被保险人身故给付的保险金总和不得超过国务院保险监籽件理机构规定的限额，身故给付的保险金额总和约定也不得超过前述限额。</li>
                        <li><b>合同生效：</b>投保后，本公司可能会通知体检或补充其他资料，视体检结果和具体情况决定是否承保，您应该尽快配合，否则将会影响或延误合同生效，体检不影响投保人的如实告知义务。本公司承保后，保险责任将按照保险条款的规定生效。</li>
                        <li><b>保单送达：</b>本公司将在承保后出具并送达保险合同，投保人应认真核对并在保险合同签收回执上亲笔签字确认。</li>
                        <li><b>犹豫期：</b>除另有约定外，保险期间在一年期(不含一年期)以上的合同设有犹豫期，即自投保人收到保险合同并书面签收保单之日起一定的期间，详见人身保险投保提示书和产品利益条款。投保人应当充分理解犹豫期小宜：在犹豫期内投保人巾请退保的，本公司收到退保中请后，保险合同终IF.，并在扣除一定工木费后将剩余保险费退还投保人。犹豫期过后投保人中请退保的，本公司收到退保中请后，保险合同终止，并将保险合同的现金价值退保投保人。</li>
                        <li><b>自动续保：</b>对于一年期主险/附加险包含有续保条款的，如您同意自动续保，合同保险期间届满前，本公司将通知您，如您未向本么司表示不续保，则视为您申请续保，本公司将对被保险人做续保审核。经本公司审核同意，且您已交纳续保保险费，合同效力延续一年;本公司审核不同意的，将书面通知您。如您不同意自动续保，合同保险期间届满的，合同终止，本公司将不再另行通知。</li>
                        <li><b>合同效力恢复：</b>合同效力中止后二年内，您可以申请恢复合同效力，在满足合同约定的效力恢复条件后，合同效力恢复。合同效力中止满二年双方未达成复效协议的，本公司有权解除合同，并根据合同约定退还保险合同的现金价值。</li>
                        <li><b>红利分配方式:</b>本公司分红保险产品采用增额红利或现金红利的方式进行分红，其体分配方式请参阅条款及产品说明书。</li>
                    </ul>
                    <div className="text-center">客户投保声明</div>
                    <ul className="statement-list-kh">
                        <li>1、贵公司已向本人提供保险条款，说明保险合同内容，特别提示并明确说明了免除或者减轻保险人责任的条款(包括责任免除条款、免赔额、免赔率、比例赔付或给付等)。</li>
                        <li>2、本人已认真阅读并充分理解保险责任、责任免除、犹豫期、合同生效、合同解除、未成年人身故保险金限额、保险事故通知、保险金受益人的指定与变更等保险条款的各项概念、内容及其法律后果，以及投资连接保险、分红保险、万能保险等新型产品的产品说明书，本人自愿承担保单利益不确定的风险。</li>
                        <li>3、本人及被保险人在投保书中的所有陈述和告知均完整、真实、准确，已知悉本投保书如非本人亲笔签名，将对本保险合同效力产生影响。</li>
                        <li>4、本人及被保险人授权贵公司在必要时可随时向有关机构核实本人及被保险人、保险金受益人的基本信息或向被保险人就诊的医院或医师及社保、农合、健康管理中心等有关机构查询有关记录、诊断证明。本人和被保险人对此均无异议。</li>
                        <li>5、本人授权贵公司委托本人开户银行对指定账户按照保险合同约定的方式、金额，划转首期、续期保险费及以转账方式将保险金、退保金、退费等给付转入指定账户，若本人指定账户或联系电话、联系地址、税收居民身份等信息发生变更，将在30日内至贵公司办理变更手续，如未及时通知贵公司变更，因此产生的相应不利后果由本人承担。</li>
                        <li>6、本人已知悉本投保书不得作为收取现金的凭证，公司未授权保险营销员、保险中介机构(银行除外)收取1000元以上的现金保险费。公司在承保之前所收保费为预收保费，不作为是否同意承保的依据，如不符合承保条件，将如数退还。</li>
                    </ul>
                    {
                        this.state.isElectronics ? 
                        <div className="copy-mian">
                            <p>根据保险业监管要求，若投保的险种户包含分红险、万砖保险戴投资连结保险等新v产品 ,投保人须抄写如下内容:</p>
                            <p>是的风格和回购分</p>
                            <p>备注:“保单利益的不确定性”仅钾对投资连结保险、万能保险和分红保险等新型产品的非保证利益部分，即分红保险的红利分配、万能保险结算利率超过最低保证利率的部分，以及投资连结保险的投资账户单位价值等。不影响投保人、被保险人和受益人按照保险合同可以享有的确定利益。</p>
                        </div> : ''
                    }
                    <div className="separate-box">
                        <div>紧急联系人</div>
                        <ol>
                            <li>姓名：<span>阿萨德</span></li>
                            <li>手机号码：<span>4565444</span></li>
                            <li>关系：<span>多少</span></li>
                        </ol>
                    </div>
                    <div className="separate-box">
                        <div>电话回访</div>
                        <ol>
                            <li>您选择的回访时间是：<span>454545:5451</span></li>
                        </ol>
                    </div>
                    {
                        this.state.isElectronics ? 
                            <ul className="sign-box">
                                <li>
                                    <span>投保人签名:</span>
                                    <span><img id="xss_20"  src="data:image/gif;base64,R0lGODlhhwBIAJECAL6+vtHR0f///wAAACH5BAEAAAIALAAAAACHAEgAAAL/jI+py+0Po5y02ouz3rz7Dx7CSJbmiabqyrbuC6NLTNf2jddKzvf+r9oBh8TiS2hMKo3IpfOJa0KnVJa0is0Krtruk+sNMxPi8hJsTufQ6jaN7Y634PK6jGzPu+j6PL9f9wcYJzjYVmiYhphYtngCAMkDUDOJEklzCeRokolTCdNZEuoyyrPJaRnz+dL5uSrwahnbcyr6WJoyuwLJywvb28sCPKw7hyc5THmbOxLryqqCu3ecU3xLTBz9TLIqza3rbYzgYw1t3jw5Wo4bLi7S81qeSor6+yhszylvRQ2KDYyJnixYqr6J2sdvnLJdC+dp40YqnkFa/c4xu/YvY7FQ7O1yIZymMCBDkST1fUQ3xNG6kfVsacv4KxizjjFU4nvYcqI+f8I00jSh0ucuZ/d2FnXZwle0GzaH4kQKkSXBgz3zXdRR8Ym6o0tRepXlytrJEovAQTV6dqrTZlFZiWWaVWorqstysk0aday3sSQQSdSplm7afUTTonX4Jq7HnK1mZcsG1ifAlzBtCNra9QZfzZvdGWCUqBZoLKJHUyltGgrq1E5Ws1bi+vWYkLIJKa4tJjZuTbd3d9HtmyLt4I16E68C/Djc4cp/G2/+5Tn0M9KnJ0lu/Uj17ERmcEceIrz48eTLmz+PPr368gUAADs=" onClick={this.testPopupDialog1.bind(this,20,0)}/></span>
                                </li>
                                <li>
                                    <span>被保险人(法定监护人)签名:</span>
                                    <span><img id="xss_21"  src="data:image/gif;base64,R0lGODlhhwBIAJECAL6+vtHR0f///wAAACH5BAEAAAIALAAAAACHAEgAAAL/jI+py+0Po5y02ouz3rz7Dx7CSJbmiabqyrbuC6NLTNf2jddKzvf+r9oBh8TiS2hMKo3IpfOJa0KnVJa0is0Krtruk+sNMxPi8hJsTufQ6jaN7Y634PK6jGzPu+j6PL9f9wcYJzjYVmiYhphYtngCAMkDUDOJEklzCeRokolTCdNZEuoyyrPJaRnz+dL5uSrwahnbcyr6WJoyuwLJywvb28sCPKw7hyc5THmbOxLryqqCu3ecU3xLTBz9TLIqza3rbYzgYw1t3jw5Wo4bLi7S81qeSor6+yhszylvRQ2KDYyJnixYqr6J2sdvnLJdC+dp40YqnkFa/c4xu/YvY7FQ7O1yIZymMCBDkST1fUQ3xNG6kfVsacv4KxizjjFU4nvYcqI+f8I00jSh0ucuZ/d2FnXZwle0GzaH4kQKkSXBgz3zXdRR8Ym6o0tRepXlytrJEovAQTV6dqrTZlFZiWWaVWorqstysk0aday3sSQQSdSplm7afUTTonX4Jq7HnK1mZcsG1ifAlzBtCNra9QZfzZvdGWCUqBZoLKJHUyltGgrq1E5Ws1bi+vWYkLIJKa4tJjZuTbd3d9HtmyLt4I16E68C/Djc4cp/G2/+5Tn0M9KnJ0lu/Uj17ERmcEceIrz48eTLmz+PPr368gUAADs=" onClick={this.testPopupDialog1.bind(this,20,2)}/></span>
                                </li>
                                <li>
                                    <span>投保日期</span>
                                    <span>2018-02-20</span>
                                </li>
                            </ul> : ''
                    }
                    <div className="separate-box">
                        <div>保险公司</div>
                        <ol>
                            <li>业务员姓名:<span>阿萨德</span></li>
                            <li>业务员号:<span>4565444</span></li>
                            <li>手机号码:<span>52463576</span></li>
                            <li>所属机构:<span>132545</span></li>
                        </ol>
                    </div>
                    <ul className="adress-msg">
                        <li>
                            全国统一客服电话:95567网址:www.newchinalife.com官方微信、移动人pp服务，请扫描二维码
                        </li>
                        <li>
                            二维码图片
                        </li>
                    </ul>
                </div>
                <div id="dialog" style={{ display:'none'}}>
                    <div id="anysign_title" style={{color:'#333333'}} width="100%" height="10%">请投保人<span style={{fontize:'20pt'}}> 李 元 </span>签名</div>
                    <div id="container" onmousedown="return false;">
                        <canvas id="anysignCanvas" width="2"></canvas>
                    </div>

                    <div id="comment_dialog" style={{display:'none'}}>

                        <div id="leftView">
                            <p id="comment_title"></p>
                            <div id="signImage" className="signImagecss"></div>
                        
                        </div>
                        <div id="tmpcanvascss" className="tmpcanvascss">
                            <div id="signTitle"></div>
                            <canvas id="comment_canvas"></canvas>
                        </div>

                        <div id="comment_btnContainerInner" className="comment_btncontainer">
                            <input id="comment_ok" type="button" className="button orange" value="确 定"/>
                            <input id="comment_back" type="button" className="button orange" value="后退"/>
                            <input id="comment_cancel" type="button" className="button orange" value="取 消"/>
                        </div>

                    </div>
                    <div id="single_scrollbar" style={{textAlign: 'center',  verticalAlign:'middle'}}  width="100%">
                        <p><span id="single_scroll_text"> *滑动操作：</span></p>
                        <p>
                            <input id="single_scrollbar_up" type="button" className="button orange" value="左移" />
                            <input id="single_scrollbar_down" type="button" className="button orange" value="右移" />
                        </p>
                    </div>

                    <div id="btnContainerOuter" width="100%">

                        <div id="btnContainerInner" style={{textAlign: 'center',   fontSize:'5pt'}} width="100%">
                            <input id="btnOK" type="button" className="button orange" value="确 定" onClick={sign_confirm} />
                            <input id="btnClear" type="button" className="button orange"  value="清 屏" onClick={clear_canvas}/>
                            <input id="btnCancel" type="button" className="button orange" value="取 消" onClick={cancelSign}/>
                        </div>

                    </div>
                </div>
                <div className="bottom text18 tc-primary">
                    <div className="ml-3 mr-0" style={{width:"300px"}}></div>
                    <div className="divx" onClick={this.submit.bind(this)}>
                        <div className="ml-0 mr-0" style={{width:"390px", textAlign:"right"}}>
                            提交
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
    ReactDOM.render(<Autograph/>, document.getElementById("autograph"))
})