class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
        }
    }
    componentDidMount() {
        MF.setTitle("客户声明及授权")
    }
    next() {
        MF.navi("apply/pay.html?orderId=" + this.state.orderId)
    }
    render() {
        return (
            <div className="bg-white">
                <div className="text19 pl-2 pr-2 pt-2">
一、贵公司已向本人提供保险条款，说明保险合同内容，特别提示并明确说明了免除或者减轻保险人责任的条款（包括责任免除条款、免赔额、免赔率、比例赔付或给付等）。<br/>
二、本人已认真阅读并充分理解保险责任、责任免除、犹豫期、合同生效、合同解除、未成年人身故保险金限额、保险事故通知、保险金受益人的指定与变更等保险条款的各项概念、内容及其法律后果，以及投资连结保险、分红保险、万能保险等新型产品的产品说明书，本人自愿承担保单利益不确定的风险。<br/>
三、本人及被保险人在投保书中的所有陈述和告知均完整、真实，已知悉各项投保资料如非本人亲笔签名，将对本保险合同效力产生影响。<br/>
四、本人已知晓应真实、准确、完整填写投保各项内容，尤其是投被保险人的姓名、性别、生日、证件类型、证件号码、职业、联系电话及地址、投被保险人关系等，以上信息主要用于计算保费、核保、寄送保单和客户回访等，以便提供更优质的服务。如信息缺失、不实将会对本人利益产生不利影响，同时贵公司承诺未经本人同意，不会将本人信息用于公司和第三方机构的销售活动。<br/>
五、本人及被保险人授权贵公司在必要时可随时向有关机构核实本人及被保险人、保险金受益人的基本信息或向被保险人就诊的医院或医师及社保、农合、健康管理中心等有关机构查询有关记录、诊断证明。本人和被保险人对此均无异议。<br/>
六、本人授权贵公司委托本人开户银行对指定账户按照保险合同约定的方式、金额，划转首期、续期保险费及以转账方式将保险金、退保金、退费等给付转入指定账户，若本人指定账户或联系电话、联系地址等信息发生变更，及时至贵公司办理变更手续，如未及时通知贵公司变更，因此产生的相应不利后果由本人承担。<br/>
七、本人已知悉电子投保申请确认书等投保资料不得作为收取现金的凭证，公司未授权保险营销员、保险中介机构（银行除外）收取1000元以上的现金保险费。公司在承保之前所收保费为预收保费，不作为是否同意承保的依据，如不符合承保条件，将如数退还。<br/>
                </div>
                <div style={{height:"120px"}}></div>
                <div className="bottom text18 tc-primary">
                    <div className="ml-3 mr-0" style={{width:"300px"}}></div>
                    <div className="divx" onClick={this.next.bind(this)}>
                        <div className="ml-0 mr-0" style={{width:"390px", textAlign:"right"}}>
                            支付信息
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
    ReactDOM.render(<Main/>, document.getElementById("root"))
})