<<<<<<< HEAD
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
=======
/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 1);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */,
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var CreateClient = function (_React$Component) {
    _inherits(CreateClient, _React$Component);

    function CreateClient() {
        _classCallCheck(this, CreateClient);

        var _this = _possibleConstructorReturn(this, (CreateClient.__proto__ || Object.getPrototypeOf(CreateClient)).call(this));

        _this.state = {
            orderId: common.param("orderId"),
            genderDict: { "M": "男", "F": "女" },
            nationDict: {},
            marriageDict: {},
            certTypeDict: {},
            relationDict: { "00": "本人", "01": "夫妻" },
>>>>>>> master
            index: 0,
            mode: 1,
            cust: common.customer('customerMsg')
        };
<<<<<<< HEAD
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
=======
        _this.finish = _this.finish.bind(_this);
        return _this;
    }

    _createClass(CreateClient, [{
        key: "componentDidMount",
        value: function componentDidMount() {
            var _this2 = this;

            if (Object.keys(this.state.cust).length > 0) {
                window.MF && MF.setTitle("客户编辑");
            } else {
                window.MF && MF.setTitle("新建客户");
            }

            window.MF && APP.dict("cert,marriage,nation,occupation,relation", function (r) {
                var occMap = {};
                var occRank = {};
                var occDict = r.occupation.datas.map(function (v) {
                    var c = v.smalls.map(function (w) {
                        occMap[w.occupationCode] = { text: w.occupationName };
                        occRank[w.occupationCode] = w.occupationLevel;
                        return { code: w.occupationCode, text: w.occupationName };
                    });
                    occMap[v.occupationCode] = { text: v.occupationName, children: c };
                    return { code: v.occupationCode, text: v.occupationName };
                });

                _this2.setState({
                    occMap: occMap,
                    occRank: occRank,
                    occDict: occDict,
                    nationDict: APP.toMapDict(r.nation),
                    certTypeDict: r.cert,
                    relationDict: r.relation,
                    marriageDict: r.marriage
                });
            });
        }
    }, {
        key: "save",
        value: function save() {
            var c = this.state.cust;
            if (this.state.mode == 1) {
                c.name = this.refs.name.value;
                c.certNo = this.refs.certNo.value;
                if (!c.name || !c.name.length) {
                    console.log('请填写用户姓名');
                    return;
                }if (!c.gender || !c.gender.length) {
                    console.log('请选择性别');
                    return;
                }if (!c.nation || !c.nation.length) {
                    console.log('请选择国籍');
                    return;
                }if (!c.birthday || !c.birthday.length) {
                    console.log('请选择出生日期');
                    return;
                }if (!c.marriage || !c.marriage.length) {
                    console.log('请选择婚姻状况');
                    return;
                }if (!c.certNo || !c.certNo.length) {
                    console.log('请填写证件号');
                    return;
                }if (!c.certValidDate || !c.certValidDate.length) {
                    console.log('请填写证件有效期');
                    return;
                }
                c.mode1 = true;
            } else if (this.state.mode == 2) {
                c.company = this.refs.company.value;
                c.workJob = this.refs.workJob.value;
                c.income = this.refs.income.value;
                if (!c.company || !c.company.length) {
                    console.log('请填写工作单位');
                    return;
                }if (!c.workJob || !c.workJob.length) {
                    console.log('请填写职位');
                    return;
                }if (!c.occupation1 || !c.occupation1.length) {
                    console.log('请选择职业大类');
                    return;
                }if (!c.occupation || !c.occupation.length) {
                    console.log('请选择职业小类');
                    return;
                }if (!c.occupation || !c.occupation.length) {
                    console.log('请填写职业代码');
                    return;
                }if (!c.occupationLevel || !c.occupationLevel.length) {
                    console.log('请填写职业类别');
                    return;
                }if (!c.income || !c.income.length) {
                    console.log('请填写年收入');
                    return;
                }
                c.mode2 = true;
            } else if (this.state.mode == 3) {
                c.address = this.refs.address.value;
                c.cityText = this.refs.address1.value;
                c.address2 = this.refs.address2.value;
                c.telephone = this.refs.telephone.value;
                c.mobile = this.refs.mobile.value;
                c.qq = this.refs.qq.value;
                c.wechat = this.refs.wechat.value;
                c.zipcode = this.refs.zipcode.value;
                c.email = this.refs.email.value;
                if (!c.address || !c.address.length) {
                    console.log('请填写联系地址');
                    return;
                } /*if (!c.cityText || !c.cityText.length) {
                     console.log('请填写乡镇(街道)');
                     return;
                  } if (!c.address2 || !c.address2.length) {
                     console.log('请填写村(社区)');
                     return;
                  } */if ((!c.telephone || !c.telephone.length) && (!c.mobile || !c.mobile.length)) {
                    console.log('手机或者电话二者选其一');
                    return;
                } /*if (!c.qq || !c.qq.length) {
                    console.log('请填写qq号码');
                    return;
                  } if (!c.wechat || !c.wechat.length) {
                    console.log('请填写微信号码');
                    return;
                  } */if (!c.zipcode || !c.zipcode.length) {
                    console.log('请填写邮政编码');
                    return;
                }if (!c.email || !c.email.length) {
                    console.log('请填写邮箱');
                    return;
                }
                c.mode3 = true;
            } else if (this.state.mode == 4) {
                c.mode4 = true;
            }

            this.state.cust = c;
            this.setState({ mode: 0 });
        }
    }, {
        key: "finish",
        value: function finish() {
            var _detail;

            var cust = this.state.cust;
            var postData = {
                "id": cust.id || '',
                "name": cust.name,
                "gender": cust.gender,
                "birthday": cust.birthday,
                "cert_type": cust.certType,
                "cert_no": cust.certNo,
                "type": '1',
                detail: (_detail = {
                    "mobile": cust.mobile,
                    "email": cust.email,
                    "city": cust.city,
                    "address": cust.address,
                    "birthday": cust.birthday,
                    "cert": {
                        "certNo": cust.certNo,
                        "certLong": true,
                        "certExpire": cust.certExpire || "",
                        "certType": cust.certType
                    },
                    "channelId": cust.channelId || 1
                }, _defineProperty(_detail, "city", cust.city || ""), _defineProperty(_detail, "cityText", cust.cityText), _defineProperty(_detail, "company", cust.company), _defineProperty(_detail, "companyAddress", cust.companyAddress || ""), _defineProperty(_detail, "education", cust.education || ""), _defineProperty(_detail, "gender", cust.gender), _defineProperty(_detail, "marriage", cust.marriage), _defineProperty(_detail, "mobile", cust.marriage), _defineProperty(_detail, "phone", cust.phone), _defineProperty(_detail, "name", cust.name), _defineProperty(_detail, "nation", cust.nation), _defineProperty(_detail, "occupation", cust.occupation), _defineProperty(_detail, "owner", 1), _defineProperty(_detail, "partTimeJob", cust.partTimeJob || ""), _defineProperty(_detail, "relation", cust.relation || ""), _defineProperty(_detail, "workDetail", cust.workDetail || ""), _defineProperty(_detail, "zipcode", cust.zipcode), _detail)
            };
            this.save();
            if (!cust.mode1 || !cust.mode2 || !cust.mode3 || !cust.mode4) {
                console.log('请补充信息');
                return;
            }
            APP.list('/customer/save.json', postData, function (r) {
                window.MF && MF.navi("client/client_list.html");
            });
        }
    }, {
        key: "newInsurant",
        value: function newInsurant() {
            this.state.cust.push({});
            this.setState({ cust: this.state.cust });
        }
    }, {
        key: "onInsurantSwitch",
        value: function onInsurantSwitch(i) {
            this.setState({ mode: 0, index: i });
        }
    }, {
        key: "onValChange",
        value: function onValChange(key, val) {
            if (key === 'certType') {
                this.state.cust[key] = val;
            } else {
                this.state.cust[key] = val;
            }
            if (key == "occupation1") {
                this.state.cust.occupation = null;
                this.state.cust.occupationLevel = null;
            } else if (key == "occupation") {
                this.state.cust.occupationLevel = this.state.occRank[this.state.cust.occupation];
            }
            this.setState({ cust: this.state.cust });
        }
    }, {
        key: "render",
        value: function render() {
            var _this3 = this;

            var cust = this.state.cust;
            return React.createElement(
                "div",
                null,
                React.createElement(
                    "div",
                    { className: "divx bg-white pl-3 pr-3", style: { height: "100px", marginTop: "20px", textAlign: "center" }, onClick: function onClick(v) {
                            _this3.setState({ mode: _this3.state.mode == 1 ? 0 : 1 });
                        } },
                    React.createElement(
                        "div",
                        { className: "divx text18", style: { height: "60px", margin: "25px auto 0 auto", verticalAlign: "middle", lineHeight: "50px" } },
                        React.createElement("img", { style: { width: "50px", height: "50px", margin: "0 20px 0 65px" }, src: "../images/" + (this.state.mode == 1 ? "sub" : "add") + ".png" }),
                        "\u57FA\u672C\u4FE1\u606F"
                    ),
                    React.createElement(
                        "div",
                        { style: { width: "65px" } },
                        cust.mode1 ? React.createElement("img", { style: { width: "65px", height: "50px", marginTop: "25px", float: "right" }, src: "../images/filled.png" }) : null
                    )
                ),
                this.state.mode != 1 ? null : React.createElement(
                    "div",
                    { className: "div" },
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u59D3\u540D"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "name", defaultValue: cust.name, placeholder: "\u8BF7\u8F93\u5165\u6295\u4FDD\u4EBA\u59D3\u540D" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u6027\u522B"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget", onClick: function onClick(v) {
                                    APP.pick("select", _this3.state.genderDict, _this3.onValChange.bind(_this3, "gender"));
                                } },
                            React.createElement(
                                "div",
                                { className: (cust.gender == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                cust.gender == null ? "请选择性别" : this.state.genderDict[cust.gender]
                            ),
                            React.createElement("img", { className: "mt-2 mr-0", style: { width: "27px", height: "39px" }, src: "../images/right.png" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u56FD\u7C4D"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget", onClick: function onClick(v) {
                                    APP.pick("select", _this3.state.nationDict, _this3.onValChange.bind(_this3, "nation"));
                                } },
                            React.createElement(
                                "div",
                                { className: (cust.nation == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                cust.nation == null ? "请选择国籍" : this.state.nationDict[cust.nation]
                            ),
                            React.createElement("img", { className: "mt-2 mr-0", style: { width: "27px", height: "39px" }, src: "../images/right.png" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u51FA\u751F\u65E5\u671F"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget", onClick: function onClick(v) {
                                    APP.pick("date", { begin: "1900-01-01", end: new Date() }, _this3.onValChange.bind(_this3, "birthday"));
                                } },
                            React.createElement(
                                "div",
                                { className: (cust.birthday == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                cust.birthday == null ? "请选择出生日期" : cust.birthday
                            ),
                            React.createElement("img", { className: "mt-2 mr-0", style: { width: "27px", height: "39px" }, src: "../images/right.png" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u5A5A\u59FB\u72B6\u51B5"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget", onClick: function onClick(v) {
                                    APP.pick("select", _this3.state.marriageDict, _this3.onValChange.bind(_this3, "marriage"));
                                } },
                            React.createElement(
                                "div",
                                { className: (cust.marriage == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                cust.marriage == null ? "请选择婚姻状况" : this.state.marriageDict[cust.marriage]
                            ),
                            React.createElement("img", { className: "mt-2 mr-0", style: { width: "27px", height: "39px" }, src: "../images/right.png" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u8BC1\u4EF6\u7C7B\u578B"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget", onClick: function onClick(v) {
                                    APP.pick("select", _this3.state.certTypeDict, _this3.onValChange.bind(_this3, "certType"));
                                } },
                            React.createElement(
                                "div",
                                { className: (cust.certType == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                cust.certType == null ? "请选择证件类型" : this.state.certTypeDict[cust.certType]
                            ),
                            React.createElement("img", { className: "mt-2 mr-0", style: { width: "27px", height: "39px" }, src: "../images/right.png" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u8BC1\u4EF6\u53F7\u7801"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "certNo", defaultValue: cust.certNo, placeholder: "\u8BF7\u8F93\u5165\u8BC1\u4EF6\u53F7\u7801" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u8BC1\u4EF6\u6709\u6548\u671F"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget", onClick: function onClick(v) {
                                    APP.pick("date", { begin: new Date() }, _this3.onValChange.bind(_this3, "certValidDate"));
                                } },
                            React.createElement(
                                "div",
                                { className: (cust.certValidDate == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                cust.certValidDate == null ? "请选择证件有效期" : cust.certValidDate
                            ),
                            React.createElement("img", { className: "mt-2 mr-0", style: { width: "27px", height: "39px" }, src: "../images/right.png" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement("img", { className: "mt-1 ml-auto mr-3", style: { width: "120px", height: "60px" }, src: "../images/finish.png", onClick: this.save.bind(this) })
                    )
                ),
                React.createElement(
                    "div",
                    { className: "divx bg-white pl-3 pr-3", style: { height: "100px", marginTop: "20px", textAlign: "center" }, onClick: function onClick(v) {
                            _this3.setState({ mode: _this3.state.mode == 2 ? 0 : 2 });
                        } },
                    React.createElement(
                        "div",
                        { className: "divx text18", style: { height: "60px", margin: "25px auto 0 auto", verticalAlign: "middle", lineHeight: "50px" } },
                        React.createElement("img", { style: { width: "50px", height: "50px", margin: "0 20px 0 65px" }, src: "../images/" + (this.state.mode == 2 ? "sub" : "add") + ".png" }),
                        "\u804C\u4E1A\u4FE1\u606F"
                    ),
                    React.createElement(
                        "div",
                        { style: { width: "65px" } },
                        cust.mode2 ? React.createElement("img", { style: { width: "65px", height: "50px", marginTop: "25px", float: "right" }, src: "../images/filled.png" }) : null
                    )
                ),
                this.state.mode != 2 ? null : React.createElement(
                    "div",
                    { className: "div" },
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u5DE5\u4F5C\u5355\u4F4D"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "company", defaultValue: cust.company, placeholder: "\u8BF7\u8F93\u5165\u5DE5\u4F5C\u5355\u4F4D" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u804C\u52A1"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "workJob", defaultValue: cust.workJob, placeholder: "\u8BF7\u8F93\u5165\u804C\u52A1" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u804C\u4E1A\u5927\u7C7B"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget", onClick: function onClick(v) {
                                    APP.pick("select", _this3.state.occDict, _this3.onValChange.bind(_this3, "occupation1"));
                                } },
                            React.createElement(
                                "div",
                                { className: (cust.occupation1 == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                cust.occupation1 == null ? "请选择职业大类" : this.state.occMap[cust.occupation1] ? this.state.occMap[cust.occupation1].text : "请选择职业大类"
                            ),
                            React.createElement("img", { className: "mt-2 mr-0", style: { width: "27px", height: "39px" }, src: "../images/right.png" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u804C\u4E1A\u5C0F\u7C7B"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget", onClick: function onClick(v) {
                                    APP.pick("select", _this3.state.occMap[cust.occupation1].children, _this3.onValChange.bind(_this3, "occupation"));
                                } },
                            React.createElement(
                                "div",
                                { className: (cust.occupation == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                cust.occupation == null ? "请选择职业小类" : this.state.occMap[cust.occupation] ? this.state.occMap[cust.occupation].text : "请选择职业小类"
                            ),
                            React.createElement("img", { className: "mt-2 mr-0", style: { width: "27px", height: "39px" }, src: "../images/right.png" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u804C\u4E1A\u4EE3\u7801"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement(
                                "div",
                                { className: (cust.occupation == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                cust.occupation
                            )
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u804C\u4E1A\u7C7B\u522B"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement(
                                "div",
                                { className: (cust.occupationLevel == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                cust.occupationLevel == null ? "" : cust.occupationLevel + "类"
                            )
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u5E74\u6536\u5165(\u4E07\u5143)"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "income", defaultValue: cust.income, placeholder: "\u8BF7\u8F93\u5165\u5E74\u6536\u5165" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement("img", { className: "mt-1 ml-auto mr-3", style: { width: "120px", height: "60px" }, src: "../images/finish.png", onClick: this.save.bind(this) })
                    )
                ),
                React.createElement(
                    "div",
                    { className: "divx bg-white pl-3 pr-3", style: { height: "100px", marginTop: "20px", textAlign: "center" }, onClick: function onClick(v) {
                            _this3.setState({ mode: _this3.state.mode == 3 ? 0 : 3 });
                        } },
                    React.createElement(
                        "div",
                        { className: "divx text18", style: { height: "60px", margin: "25px auto 0 auto", verticalAlign: "middle", lineHeight: "50px" } },
                        React.createElement("img", { style: { width: "50px", height: "50px", margin: "0 20px 0 65px" }, src: "../images/" + (this.state.mode == 3 ? "sub" : "add") + ".png" }),
                        "\u8054\u7CFB\u65B9\u5F0F"
                    ),
                    React.createElement(
                        "div",
                        { style: { width: "65px" } },
                        cust.mode3 ? React.createElement("img", { style: { width: "65px", height: "50px", marginTop: "25px", float: "right" }, src: "../images/filled.png" }) : null
                    )
                ),
                this.state.mode != 3 ? null : React.createElement(
                    "div",
                    { className: "div" },
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u8054\u7CFB\u5730\u5740"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "address", defaultValue: cust.address, placeholder: "\u8BF7\u8F93\u5165\u8054\u7CFB\u5730\u5740" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u4E61\u9547(\u8857\u9053)"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "address1", defaultValue: cust.address1, placeholder: "\u8BF7\u8F93\u5165\u4E61\u9547\uFF08\u8857\u9053\uFF09" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u6751(\u793E\u533A)"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "address2", defaultValue: cust.address2, placeholder: "\u8BF7\u8F93\u5165\u6751\uFF08\u793E\u533A\uFF09" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u90AE\u653F\u7F16\u7801"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "zipcode", defaultValue: cust.zipcode, placeholder: "\u8BF7\u8F93\u5165\u90AE\u653F\u7F16\u7801" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label", style: { width: "670px" } },
                            "\u8054\u7CFB\u65B9\u5F0F(\u624B\u673A\u6216\u8005\u7535\u8BDD\u4E8C\u8005\u9009\u5176\u4E00)"
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u7535\u8BDD"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "telephone", defaultValue: cust.telephone, placeholder: "\u8BF7\u8F93\u5165\u7535\u8BDD \u4F8B\uFF1A000-12345678" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u624B\u673A"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "mobile", defaultValue: cust.mobile, placeholder: "\u8BF7\u8F93\u5165\u624B\u673A" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u7535\u5B50\u90AE\u7BB1"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "email", defaultValue: cust.email, placeholder: "\u8BF7\u8F93\u5165\u7535\u5B50\u90AE\u7BB1" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "QQ\u53F7\u7801"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "qq", defaultValue: cust.qq, placeholder: "\u8BF7\u8F93\u5165QQ\u53F7\u7801" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u5FAE\u4FE1\u53F7\u7801"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "wechat", defaultValue: cust.wechat, placeholder: "\u8BF7\u8F93\u5165\u5FAE\u4FE1\u53F7\u7801" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement("img", { className: "mt-1 ml-auto mr-3", style: { width: "120px", height: "60px" }, src: "../images/finish.png", onClick: this.save.bind(this) })
                    )
                ),
                React.createElement(
                    "div",
                    { className: "divx bg-white pl-3 pr-3", style: { height: "100px", marginTop: "20px", textAlign: "center" }, onClick: function onClick(v) {
                            _this3.setState({ mode: _this3.state.mode == 4 ? 0 : 4 });
                        } },
                    React.createElement(
                        "div",
                        { className: "divx text18", style: { height: "60px", margin: "25px auto 0 auto", verticalAlign: "middle", lineHeight: "50px" } },
                        React.createElement("img", { style: { width: "50px", height: "50px", margin: "0 20px 0 65px" }, src: "../images/" + (this.state.mode == 4 ? "sub" : "add") + ".png" }),
                        "\u5176\u4ED6\u4FE1\u606F"
                    ),
                    React.createElement(
                        "div",
                        { style: { width: "65px" } },
                        cust.mode4 ? React.createElement("img", { style: { width: "65px", height: "50px", marginTop: "25px", float: "right" }, src: "../images/filled.png" }) : null
                    )
                ),
                this.state.mode != 4 ? null : React.createElement(
                    "div",
                    { className: "div" },
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement("img", { className: "mt-1 ml-auto mr-3", style: { width: "120px", height: "60px" }, src: "../images/finish.png", onClick: this.save.bind(this) })
                    )
                ),
                React.createElement("div", { style: { height: "120px" } }),
                React.createElement(
                    "div",
                    { className: "bottom text18 tc-primary" },
                    React.createElement("div", { className: "ml-3 mr-0", style: { width: "300px" } }),
                    React.createElement(
                        "div",
                        { onClick: function onClick() {
                                _this3.finish();
                            } },
                        React.createElement(
                            "div",
                            { className: "ml-0 mr-0", style: { width: "390px", textAlign: "right" } },
                            "\u5B8C\u6210"
                        )
                    )
                )
            );
        }
    }]);

    return CreateClient;
}(React.Component);

$(document).ready(function () {
    ReactDOM.render(React.createElement(CreateClient, null), document.getElementById("root"));
});

/***/ })
/******/ ]);
>>>>>>> master
