const serverUrl = 'http://47.104.13.159/boot';
class Main extends React.Component {
    constructor() {
        super();
        this.state = {
            images: [],
            searchInputting: false,
            searchText: '',
            products: [],
            apiInstance:'',
        }
    }
    componentDidMount() {
        window.MF && MF.setTitle("保单签收回执");
    }
    testPopupDialog1(id){
    // var oHead = document.getElementsByTagName('HEAD').item(0);
    // var oScript= document.createElement("script");
    // oScript.type = "text/javascript";
    // oScript.src="qianming.js";
    // oHead.appendChild( oScript);
    testPopupDialog(id)
}

    submit() {
         console.log("提交");
    }
    render() {
        return (
            <div className="home-container">
            <div id="other">
                <div className="receipt-header">保险单签收回执</div>
                <div className="receipt-block1">
                    <div className="left-list">
                        <h1>UN004</h1>
                        <ul>
                            <li>保单号：880001100003</li>
                            <li>划款协议书号：99992014103006</li>
                            <li>销售机构号：0102120006</li>
                            <li>业务员：张海宾 36272261</li>
                        </ul>
                    </div>
                    <div className="right-box">
                        为确保您的保单权益，请及时拨打本公司服务电话95567、登陆网站 http://www.newchinalife.com， 或到公司客服中心进行查询，核实保单信息。
                    </div>
                </div>

                <div className="receipt-block2">
                    <h1>188投保先生：</h1>
                    <p>　　非常感谢您选择我公司为您提供人生旅途的风险保障，为加强与您的沟通，使我公司能够为您提供更为优质的服务，请您在收到正式保险合同后认真核对并就以下内容予以确认，并在“投保人签名”处签字后交业务人员返还我公司。谢谢您的合作！</p>
                    <ul>
                        <li><span className="num">1.</span>已收到保险单及收据，并亲笔签收本回执。</li>
                        <li><span className="num">2.</span>已确认保单册的材料齐全，投保人、被保险人、受益人、投保产品、交费金额及期间等信息无误， 投保书影像件中各项告知与实际情况相符。</li>
                        <li><span className="num">3.</span>已清楚了解所投保产品的保险责任及责任免除。</li>
                        <li><span className="num">4.</span>已认真阅读了有关所投保产品的保险条款、产品说明和投保提示等说明材料。</li>
                    </ul>
                </div>

                <div className="receipt-block3">
                    <h1>特别提示：</h1>
                    <p>　　非常感谢您选择我公司为您提供人生旅途的风险保障，为加强与您的沟通，使我公司能够为您提供更为优质的服务，请您在收到正式保险合同后认真核对并就以下内容予以确认，并在“投保人签名”处签字后交业务人员返还我公司。谢谢您的合作！</p>
                    <ul>
                        <li className="item"><span className="num">1.</span>在您收到保险单并书面签收本《保险单签收回执》之日起十日的期间为犹豫期。如果您在此期间内退保，我公司将在扣除一定工本费后将实际交纳的保险费退还给您，保险合同终止；如果在此期间后退保，我公司将退还您保险单的现金价值，保险合同终止。</li>
                        <li className="item"><span className="num">2.</span>根据保监会的要求，为了保障您的权益，我公司在您签收保险单回执后将安排客服人员对您进行电话回访，请您注意接听。</li>
                        <li>为保持与您的良好沟通，提供便捷的后续服务，请您再次核对以下信息是否正确：</li>
                        <li>投保人：</li>
                        <li>联系电话：13652632521　　　续期划款账号：600100000000000</li>
                        <li>通讯地址：北京市东城区是的广泛地鬼地方个（110000）</li>
                        <li>□ 变更地址及邮编：</li>
                        <li>□ 变更联系电话：</li>
                        <li>□ 变更交费形式（如需变更此项，请您提供存折、身份证的复印件）</li>
                        <li>
                            <form name="from1">
                                <input type="radio" value="客户自交" name="chose"/><label>客户自交</label>
                                <input type="radio" value="上门收取" name="chose"/><label>上门收取</label>
                                <input type="radio" value="委托银行转账" name="chose"/><label>委托银行转账</label>
                            </form>
                        </li>
                        <li className="line">户名：<b>Davie</b>　　开户银行：<b>北京银行</b></li>
                        <li className="line">账号：<b>600100000000000</b></li>
                    </ul>
                    <p>　　声明：投保人同意委托   （身份证号码：130199009098877     ）办理上述信息变更，并同意新华人寿保险股份有限公司将续期保费或保全保费从投保人指定的活期储蓄存款账户划至新华人寿保险股份有限公司银行存款账户。</p>
                    <p>　　为确保您的基本信息发生变化时，我公司能及时和您取得联系，送达续期交费凭证、分红业绩报告书等重要资料，请填写您最信赖的或最有爱心和责任心的两位亲友的信息：</p>
                    <ul>
                        <li>姓名：<b>friend1</b>   性别：<b>男</b>  年龄：<b>25</b>     电话：<b>13333333333</b></li>
                        <li>姓名：<b>friend2</b>   性别：<b>女</b>  年龄：<b>36</b>     电话：<b>13344445555</b></li>
                    </ul>
                    <p>　　再次感谢您的支持！若您的地址、电话、邮编等发生变更或有任何疑问和建议，请随时拨打我公司的服务热线 95567，我们将以最为优质的服务来答谢您的信任！</p>
                    <p>　　祝您工作顺利、身体健康、万事如意！</p>
                </div>

                <div className="receipt-block4">
                    <ul>
                        <li>投保人签名：<img id="xss_20"  src="data:image/gif;base64,R0lGODlhhwBIAJECAL6+vtHR0f///wAAACH5BAEAAAIALAAAAACHAEgAAAL/jI+py+0Po5y02ouz3rz7Dx7CSJbmiabqyrbuC6NLTNf2jddKzvf+r9oBh8TiS2hMKo3IpfOJa0KnVJa0is0Krtruk+sNMxPi8hJsTufQ6jaN7Y634PK6jGzPu+j6PL9f9wcYJzjYVmiYhphYtngCAMkDUDOJEklzCeRokolTCdNZEuoyyrPJaRnz+dL5uSrwahnbcyr6WJoyuwLJywvb28sCPKw7hyc5THmbOxLryqqCu3ecU3xLTBz9TLIqza3rbYzgYw1t3jw5Wo4bLi7S81qeSor6+yhszylvRQ2KDYyJnixYqr6J2sdvnLJdC+dp40YqnkFa/c4xu/YvY7FQ7O1yIZymMCBDkST1fUQ3xNG6kfVsacv4KxizjjFU4nvYcqI+f8I00jSh0ucuZ/d2FnXZwle0GzaH4kQKkSXBgz3zXdRR8Ym6o0tRepXlytrJEovAQTV6dqrTZlFZiWWaVWorqstysk0aday3sSQQSdSplm7afUTTonX4Jq7HnK1mZcsG1ifAlzBtCNra9QZfzZvdGWCUqBZoLKJHUyltGgrq1E5Ws1bi+vWYkLIJKa4tJjZuTbd3d9HtmyLt4I16E68C/Djc4cp/G2/+5Tn0M9KnJ0lu/Uj17ERmcEceIrz48eTLmz+PPr368gUAADs=" onClick={this.testPopupDialog1.bind(this,20)}/><br/>签收日期：</li>
                    </ul>
                </div>

                <div className="receipt-block5">
                    <ul>
                        <li>
                            <span>保单号：880001100003</span>
                            <span>业务员姓名：张海宾</span>
                            <span>回单日期：2018-05-22</span>
                        </li>
                        <li>
                            <span>投保人：188投保</span>
                            <span>业务员工号：36272261</span>
                            <span> </span>
                        </li>

                    </ul>
                </div>
                <div className="receipt-aside"></div>
                    <div className="p-content">
                        {
                            this.state.products.map(prod=>{
                                return (
                                    <a className="prod-item">
                                        <img src={ prod.logo ? (serverUrl + prod.logo) :  "../images/home/default_img.png"}/>
                                        <span>{prod.abbrName}</span>
                                        <i>{prod.name}</i>
                                    </a>
                                )
                            })
                        }
                    </div>
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
                    <span id="single_scroll_text"> *滑动操作：</span>
                    <input id="single_scrollbar_up" type="button" className="button orange" value="左移" />
                    <input id="single_scrollbar_down" type="button" className="button orange" value="右移" />
                </div>

                <div id="btnContainerOuter" width="100%">

                    <div id="btnContainerInner" style={{textAlign: 'center',   fontSize:'5pt'}} width="100%">
                        <input id="btnOK" type="button" className="button orange" value="确 定" onClick={sign_confirm} />
                        <input id="btnClear" type="button" className="button orange" style={{display:'none'}} value="清 屏" onClick={clear_canvas}/>
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
    ReactDOM.render(<Main/>, document.getElementById("root"))
})