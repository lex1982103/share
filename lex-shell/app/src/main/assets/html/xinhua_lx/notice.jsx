class Notice extends React.Component {
    constructor() {
        super()
        this.state = {
            ce: '111'
        }
    }
    componentDidMount() {
        window.MF && MF.setTitle("通知书");
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
    render() {
        return (
            <div className="notice-wrap">
                <div id="other">
                    <div className="notice-title">
                        契约内容变更通知书
                    </div>
                    <div className="bar-code">
                        <div className="bar-code-left">
                            <p>图片</p>
                            <p>号码</p>
                        </div>
                        <div className="bar-code-rigth">
                            <p>图片</p>
                            <p>号码</p>
                        </div>
                    </div>
                    <div className="odd-numbers">
                        <div>
                            投保单号：<span>44565545445</span>
                        </div>
                        <div>
                            北京直属分公司
                        </div>
                    </div>
                    <div className="salesman-msg">
                        <div className="salesman-left">
                            <p>投保人：<span>阿萨德</span></p>
                            <p>被保人：<span>阿大使</span></p>
                        </div>
                        <ul className="salesman-right">
                            <li>银行及储蓄所(代理专用):</li>
                            <li>业余分部及业务组(代理专用)：<span>ff444</span></li>
                            <li>营销服务部及营业部：<span>朝阳营销服务部 同创部</span></li>
                            <li className="salesman-name">
                                <p>业务员姓名：<span>梁静茹</span></p>
                                <p>业务呀un编号：<span>554544</span></p>
                            </li>
                        </ul>
                    </div>
                    <ul className="policy-information">
                        <li>尊敬的<span>地方法</span>先生/女士：</li>
                        <li className="textIndex">非常感谢您对我公司的信赖！经我公司审慎评估，由于被保险人：<span>心电图异常，整体健康风险高于我公司制定的标准保费率，需要加费承保。</span></li>
                        <li>加费金额如下：</li>
                        <li className="textIndex">被保险人健康加费：</li>
                        <li className="policy-additional">
                            <p>险种1：
                                <span>13121</span>
                                <span>健康增额</span>
                                <span>￥44545/年</span>
                                <span>加费期同本险种交费期。</span>
                            </p>
                        </li>
                        <li>
                            合计应缴费金额：￥<span>75454.00</span>
                        </li>
                        <li>交费期间：<span>趸交</span></li>
                        <li>
                            如您对我们的服务有任何疑问或一键，请联系您的保险营销员或拨打我恭送客服热线95567
                        </li>
                        <li className="salesman-data">
                            <p>核保员：<span>g4545</span></p>
                        </li>
                        <li className="salesman-data">
                            <p>日  期：<span>2018年6月29日</span></p>
                        </li>
                    </ul>
                    <ul className="opinion-wrap">
                        <li>您的意见：</li>
                        <li className="textIndex opinion-consider">考虑考虑</li>
                        <li className="textIndex">交费方式选择：</li>
                        <li className="textIndex">▅ 以现金/支票方式交费。</li>
                        <li className="textIndex">□ 同意按原银行划款账户交费。银行账户为<span>454545</span>。</li>
                        <li className="textIndex opinion-other">□ 其他，详述：<span className="textUndeline"></span></li>
                        <li className="textIndex opinion-sign">
                            <p>投保人：<span><img id="xss_20"  src="data:image/gif;base64,R0lGODlhhwBIAJECAL6+vtHR0f///wAAACH5BAEAAAIALAAAAACHAEgAAAL/jI+py+0Po5y02ouz3rz7Dx7CSJbmiabqyrbuC6NLTNf2jddKzvf+r9oBh8TiS2hMKo3IpfOJa0KnVJa0is0Krtruk+sNMxPi8hJsTufQ6jaN7Y634PK6jGzPu+j6PL9f9wcYJzjYVmiYhphYtngCAMkDUDOJEklzCeRokolTCdNZEuoyyrPJaRnz+dL5uSrwahnbcyr6WJoyuwLJywvb28sCPKw7hyc5THmbOxLryqqCu3ecU3xLTBz9TLIqza3rbYzgYw1t3jw5Wo4bLi7S81qeSor6+yhszylvRQ2KDYyJnixYqr6J2sdvnLJdC+dp40YqnkFa/c4xu/YvY7FQ7O1yIZymMCBDkST1fUQ3xNG6kfVsacv4KxizjjFU4nvYcqI+f8I00jSh0ucuZ/d2FnXZwle0GzaH4kQKkSXBgz3zXdRR8Ym6o0tRepXlytrJEovAQTV6dqrTZlFZiWWaVWorqstysk0aday3sSQQSdSplm7afUTTonX4Jq7HnK1mZcsG1ifAlzBtCNra9QZfzZvdGWCUqBZoLKJHUyltGgrq1E5Ws1bi+vWYkLIJKa4tJjZuTbd3d9HtmyLt4I16E68C/Djc4cp/G2/+5Tn0M9KnJ0lu/Uj17ERmcEceIrz48eTLmz+PPr368gUAADs=" onClick={this.testPopupDialog1.bind(this,20,0)}/></span></p>
                            <p>
                                被保险人：<span><img id="xss_21"  src="data:image/gif;base64,R0lGODlhhwBIAJECAL6+vtHR0f///wAAACH5BAEAAAIALAAAAACHAEgAAAL/jI+py+0Po5y02ouz3rz7Dx7CSJbmiabqyrbuC6NLTNf2jddKzvf+r9oBh8TiS2hMKo3IpfOJa0KnVJa0is0Krtruk+sNMxPi8hJsTufQ6jaN7Y634PK6jGzPu+j6PL9f9wcYJzjYVmiYhphYtngCAMkDUDOJEklzCeRokolTCdNZEuoyyrPJaRnz+dL5uSrwahnbcyr6WJoyuwLJywvb28sCPKw7hyc5THmbOxLryqqCu3ecU3xLTBz9TLIqza3rbYzgYw1t3jw5Wo4bLi7S81qeSor6+yhszylvRQ2KDYyJnixYqr6J2sdvnLJdC+dp40YqnkFa/c4xu/YvY7FQ7O1yIZymMCBDkST1fUQ3xNG6kfVsacv4KxizjjFU4nvYcqI+f8I00jSh0ucuZ/d2FnXZwle0GzaH4kQKkSXBgz3zXdRR8Ym6o0tRepXlytrJEovAQTV6dqrTZlFZiWWaVWorqstysk0aday3sSQQSdSplm7afUTTonX4Jq7HnK1mZcsG1ifAlzBtCNra9QZfzZvdGWCUqBZoLKJHUyltGgrq1E5Ws1bi+vWYkLIJKa4tJjZuTbd3d9HtmyLt4I16E68C/Djc4cp/G2/+5Tn0M9KnJ0lu/Uj17ERmcEceIrz48eTLmz+PPr368gUAADs=" onClick={this.testPopupDialog1.bind(this,20,2)}/></span>
                            </p>
                            <p>
                                日期：<span>2018年6月29日</span>
                            </p>
                        </li>
                    </ul>
                    <p className="prompt-msg">重要提示：请您收到本通知书后尽快办理，以免延误保险合同生效。</p>
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
            </div>
		)
    }
}

$(document).ready( function() {
    ReactDOM.render(<Notice/>, document.getElementById("notice"));
})