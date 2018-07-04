<<<<<<< HEAD
!function(e){var t={};function a(n){if(t[n])return t[n].exports;var l=t[n]={i:n,l:!1,exports:{}};return e[n].call(l.exports,l,l.exports,a),l.l=!0,l.exports}a.m=e,a.c=t,a.d=function(e,t,n){a.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},a.r=function(e){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},a.t=function(e,t){if(1&t&&(e=a(e)),8&t)return e;if(4&t&&"object"==typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(a.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var l in e)a.d(n,l,function(t){return e[t]}.bind(null,l));return n},a.n=function(e){var t=e&&e.__esModule?function(){return e.default}:function(){return e};return a.d(t,"a",t),t},a.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},a.p="",a(a.s=25)}({25:function(e,t,a){"use strict";var n=function(){function e(e,t){for(var a=0;a<t.length;a++){var n=t[a];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,n.key,n)}}return function(t,a,n){return a&&e(t.prototype,a),n&&e(t,n),t}}();var l=function(e){function t(){!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t);var e=function(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}(this,(t.__proto__||Object.getPrototypeOf(t)).call(this));return e.state={proposalId:common.param("proposalId"),ages:[],index:0,now:common.dateStr(new Date)},e}return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}(t,React.Component),n(t,[{key:"componentDidMount",value:function(){for(var e=this,t=[],a=0;a<70;a++)t.push(a);this.setState({ages:t}),MF.setTitle("建议书"),APP.proposal.view(this.state.proposalId,function(t){e.setState({proposal:t},function(){e.onInsurantSwitch(0)})})}},{key:"onInsurantSwitch",value:function(e){var t=this;if(this.state.proposal.detail.length>e){var a=this.state.proposal.detail[e];null!=a&&""!=a&&APP.proposal.viewPlan(a,function(a){t.setState({index:e,plan:a})})}}},{key:"onGenderChange",value:function(e){this.state.plan.insurant.gender=e,this.refreshInsurant()}},{key:"onAgeChange",value:function(e){this.state.plan.insurant.age=e,this.state.plan.insurant.birthday=null,this.refreshInsurant()}},{key:"onBirthdayChange",value:function(e){this.state.plan.insurant.birthday=e,this.refreshInsurant()}},{key:"refreshInsurant",value:function(){var e=this;APP.proposal.refreshInsurant(this.state.plan.planId,this.state.plan.insurant,function(t){e.setState({plan:t})})}},{key:"addProduct",value:function(){var e=this;APP.pop("proposal/product_list.html",60,function(t){null!=t&&APP.proposal.addProduct(e.state.plan.planId,null,t,function(t){e.setState({plan:t})})})}},{key:"editProduct",value:function(e){var t=this;APP.pop("proposal/product_editor.html?planId="+this.state.plan.planId+"&index="+e,80,function(e){APP.proposal.viewPlan(t.state.plan.planId,function(e){t.setState({plan:e})})})}},{key:"deleteProduct",value:function(e){var t=this;APP.proposal.deleteProduct(this.state.plan.planId,e,null,function(e){t.setState({plan:e})})}},{key:"showBenefit",value:function(){APP.pop("proposal/benefit.html?planId="+this.state.plan.planId,90)}},{key:"popCustomer",value:function(){APP.pop("client/client_selector.html?pop=1",90)}},{key:"next",value:function(){var e=this;APP.proposal.save(this.state.proposalId,function(t){MF.navi("proposal/preview.html?proposalId="+e.state.proposalId)})}},{key:"render",value:function(){var e=this,t=this.state.plan,a=t?t.insurant:null;return null==t||null==a?null:React.createElement("div",null,React.createElement("div",null,React.createElement("div",{className:"divx",style:{position:"fixed",zIndex:"50",top:"0",backgroundColor:"#dddddd",width:"100%"}},this.state.proposal.detail.map(function(t,a){return React.createElement("div",{className:"tab "+(a==e.state.index?"tab-focus":"tab-blur"),key:a,style:{width:"250px"},onClick:e.onInsurantSwitch.bind(e,a)},React.createElement("text",{className:"text18"},"计划"+(a+1)))})),React.createElement("div",{className:"card-content",style:{marginTop:"80px"}},React.createElement("div",{className:"card-content-line bg-white"},React.createElement("div",{className:"card-content-label text17"},"性别"),React.createElement("div",{className:"card-content-widget text17"},React.createElement("div",{className:"btn-sm text17 "+("F"==a.gender?"btn-sel":""),onClick:this.onGenderChange.bind(this,"F")},"女"),React.createElement("div",{className:"btn-sm text17 "+("M"==a.gender?"btn-sel":""),onClick:this.onGenderChange.bind(this,"M")},"男")))),React.createElement("div",{className:"card-content"},React.createElement("div",{className:"card-content-line bg-white"},React.createElement("div",{className:"card-content-label text17"},a.birthday?"出生日期":"年龄"),React.createElement("div",{className:"card-content-widget",onClick:function(t){APP.pick("select",e.state.ages,e.onAgeChange.bind(e))}},React.createElement("img",{className:"mt-1 ml-2",style:{width:"60px",height:"60px"},src:"../images/calendar.png",onClick:function(t){t.stopPropagation(),APP.pick("date",{begin:"1900-01-01",end:new Date},e.onBirthdayChange.bind(e))}}),React.createElement("div",{className:"text17"},a.birthday,a.birthday&&a.age?" / ":"",a.age?a.age+"周岁":"")))),React.createElement("div",{className:"card-content",style:{marginTop:"10px"}},t.product.map(function(a,n){return null==a.parent?React.createElement("div",{className:"product product-main bg-white text16",style:{marginTop:"10px"},onClick:e.editProduct.bind(e,n)},React.createElement("div",{style:{height:"70px",display:"flex"}},React.createElement("img",{style:{width:"60px",height:"60px",margin:"10px 10px 0 10px"},src:t.icons[a.vendor]}),React.createElement("div",{style:{width:"600px",marginTop:"10px"}},React.createElement("text",{className:"text20 eclipse"},a.name)),React.createElement("img",{className:"mt-1 mr-1 mb-1 ml-1",style:{width:"50px",height:"50px",opacity:"0.4"},src:"../images/stop.png",onClick:function(t){t.stopPropagation(),e.deleteProduct(n)}})),React.createElement("div",{style:{height:"60px",display:"flex"}},React.createElement("div",{className:"left"}),React.createElement("div",{className:"middle eclipse"},React.createElement("text",null,a.purchase," / ",a.insure," / ",a.pay)),React.createElement("div",{className:"right"},React.createElement("text",{style:{color:"#000"}},a.premium,"元"))),React.createElement("div",{style:{height:"10px"}})):React.createElement("div",{className:"product product-rider bg-white text16"},React.createElement("div",{className:"left"},React.createElement("text",{style:{color:"#0a0"}},"附")),React.createElement("div",{className:"middle eclipse"},React.createElement("text",{style:{color:"#000",marginRight:"10px"}},a.abbrName),React.createElement("text",{style:{color:"#aaa"}},a.purchase," / ",a.insure," / ",a.pay)),React.createElement("div",{className:"right"},React.createElement("text",{style:{color:"#000"}},a.premium,"元")))}),t.product&&t.product.length>0?React.createElement("div",{className:"card-content-line bg-white",style:{padding:"0 20px 0 20px",display:"block",marginTop:"10px",textAlign:"right",color:"#09bb07"}},React.createElement("text",{className:"text16"},"合计：",t.premium,"元")):null,React.createElement("div",{className:"btn-fl text18",style:{color:"#fff",backgroundColor:"#1aad19"},onClick:this.addProduct.bind(this)},"添加险种"))),React.createElement("div",{style:{height:"120px"}}),React.createElement("div",{className:"bottom text18 tc-primary"},React.createElement("div",{className:"ml-3 mr-0",style:{width:"300px"},onClick:this.showBenefit.bind(this)},"查看利益责任"),React.createElement("div",{className:"divx",onClick:this.next.bind(this)},React.createElement("div",{className:"ml-0 mr-0",style:{width:"390px",textAlign:"right"}},"预览建议书"),React.createElement("div",{className:"ml-1 mr-2",style:{width:"30px"}},React.createElement("img",{className:"mt-3",style:{width:"27px",height:"39px"},src:"../images/blueright.png"})))))}}]),t}();$(document).ready(function(){ReactDOM.render(React.createElement(l,null),document.getElementById("root"))})}});
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
/******/ 	return __webpack_require__(__webpack_require__.s = 6);
/******/ })
/************************************************************************/
/******/ ({

/***/ 6:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Main = function (_React$Component) {
    _inherits(Main, _React$Component);

    function Main() {
        _classCallCheck(this, Main);

        var _this = _possibleConstructorReturn(this, (Main.__proto__ || Object.getPrototypeOf(Main)).call(this));

        _this.state = {
            proposalId: common.param("proposalId"),
            ages: [],
            index: 0,
            now: common.dateStr(new Date())
        };
        return _this;
    }

    _createClass(Main, [{
        key: "componentDidMount",
        value: function componentDidMount() {
            var _this2 = this;

            var ages = [];
            for (var i = 0; i < 70; i++) {
                ages.push(i);
            }this.setState({ ages: ages });

            window.MF && MF.setTitle("建议书");

            APP.proposal.view(this.state.proposalId, function (r) {
                _this2.setState({ proposal: r }, function () {
                    _this2.onInsurantSwitch(0);
                });
            });
        }
    }, {
        key: "onInsurantSwitch",
        value: function onInsurantSwitch(i) {
            var _this3 = this;

            if (this.state.proposal.detail.length > i) {
                var planId = this.state.proposal.detail[i];
                if (planId != null && planId != "") {
                    APP.proposal.viewPlan(planId, function (r) {
                        _this3.setState({ index: i, plan: r });
                    });
                }
            }
        }
    }, {
        key: "onGenderChange",
        value: function onGenderChange(e) {
            this.state.plan.insurant.gender = e;
            this.refreshInsurant();
        }
    }, {
        key: "onAgeChange",
        value: function onAgeChange(e) {
            this.state.plan.insurant.age = e;
            this.state.plan.insurant.birthday = null;
            this.refreshInsurant();
        }
    }, {
        key: "onBirthdayChange",
        value: function onBirthdayChange(e) {
            this.state.plan.insurant.birthday = e;
            this.refreshInsurant();
        }
    }, {
        key: "refreshInsurant",
        value: function refreshInsurant() {
            var _this4 = this;

            APP.proposal.refreshInsurant(this.state.plan.planId, this.state.plan.insurant, function (r) {
                _this4.setState({ plan: r });
            });
        }
    }, {
        key: "addProduct",
        value: function addProduct() {
            var _this5 = this;

            APP.pop("proposal/product_list.html", 60, function (r) {
                if (r != null) {
                    APP.proposal.addProduct(_this5.state.plan.planId, null, r, function (r) {
                        _this5.setState({ plan: r });
                    });
                }
            });
        }
    }, {
        key: "editProduct",
        value: function editProduct(e) {
            var _this6 = this;

            APP.pop("proposal/product_editor.html?planId=" + this.state.plan.planId + "&index=" + e, 80, function (r) {
                APP.proposal.viewPlan(_this6.state.plan.planId, function (plan) {
                    _this6.setState({ plan: plan });
                });
            });
        }
    }, {
        key: "deleteProduct",
        value: function deleteProduct(i) {
            var _this7 = this;

            APP.proposal.deleteProduct(this.state.plan.planId, i, null, function (r) {
                _this7.setState({ plan: r });
            });
        }
    }, {
        key: "showBenefit",
        value: function showBenefit() {
            APP.pop("proposal/benefit.html?planId=" + this.state.plan.planId, 90);
        }
    }, {
        key: "popCustomer",
        value: function popCustomer() {
            APP.pop("client/client_selector.html?pop=1", 90);
        }
    }, {
        key: "next",
        value: function next() {
            var _this8 = this;

            APP.proposal.save(this.state.proposalId, function (r) {
                if (window.MF) {
                    MF.navi("proposal/preview.html?proposalId=" + _this8.state.proposalId);
                } else {
                    location.href = "proposal/preview.html?proposalId=" + _this8.state.proposalId;
                }
            });
        }
    }, {
        key: "render",
        value: function render() {
            var _this9 = this;

            var plan = this.state.plan;
            var insurant = plan ? plan.insurant : null;
            return plan == null || insurant == null ? null : React.createElement(
                "div",
                null,
                React.createElement(
                    "div",
                    null,
                    React.createElement(
                        "div",
                        { className: "divx", style: { position: "fixed", zIndex: "50", top: "0", backgroundColor: "#dddddd", width: "100%" } },
                        this.state.proposal.detail.map(function (v, i) {
                            return React.createElement(
                                "div",
                                { className: "tab " + (i == _this9.state.index ? 'tab-focus' : 'tab-blur'), key: i, style: { width: "250px" }, onClick: _this9.onInsurantSwitch.bind(_this9, i) },
                                React.createElement(
                                    "text",
                                    { className: "text18" },
                                    "计划" + (i + 1)
                                )
                            );
                        })
                    ),
                    React.createElement(
                        "div",
                        { className: "card-content", style: { marginTop: "80px" } },
                        React.createElement(
                            "div",
                            { className: "card-content-line bg-white" },
                            React.createElement(
                                "div",
                                { className: "card-content-label text17" },
                                "\u6027\u522B"
                            ),
                            React.createElement(
                                "div",
                                { className: "card-content-widget text17" },
                                React.createElement(
                                    "div",
                                    { className: "btn-sm text17 " + (insurant.gender == "F" ? "btn-sel" : ""), onClick: this.onGenderChange.bind(this, "F") },
                                    "\u5973"
                                ),
                                React.createElement(
                                    "div",
                                    { className: "btn-sm text17 " + (insurant.gender == "M" ? "btn-sel" : ""), onClick: this.onGenderChange.bind(this, "M") },
                                    "\u7537"
                                )
                            )
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "card-content" },
                        React.createElement(
                            "div",
                            { className: "card-content-line bg-white" },
                            React.createElement(
                                "div",
                                { className: "card-content-label text17" },
                                insurant.birthday ? "出生日期" : "年龄"
                            ),
                            React.createElement(
                                "div",
                                { className: "card-content-widget", onClick: function onClick(e) {
                                        APP.pick("select", _this9.state.ages, _this9.onAgeChange.bind(_this9));
                                    } },
                                React.createElement("img", { className: "mt-1 ml-2", style: { width: "60px", height: "60px" }, src: "../images/calendar.png", onClick: function onClick(e) {
                                        e.stopPropagation();APP.pick("date", { begin: "1900-01-01", end: new Date() }, _this9.onBirthdayChange.bind(_this9));
                                    } }),
                                React.createElement(
                                    "div",
                                    { className: "text17" },
                                    insurant.birthday,
                                    insurant.birthday && insurant.age ? " / " : "",
                                    insurant.age ? insurant.age + "周岁" : ""
                                )
                            )
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "card-content", style: { marginTop: "10px" } },
                        plan.product.map(function (v, i) {
                            return v.parent == null ? React.createElement(
                                "div",
                                { className: "product product-main bg-white text16", style: { marginTop: "10px" }, onClick: _this9.editProduct.bind(_this9, i) },
                                React.createElement(
                                    "div",
                                    { style: { height: "70px", display: "flex" } },
                                    React.createElement("img", { style: { width: "60px", height: "60px", margin: "10px 10px 0 10px" }, src: plan.icons[v.vendor] }),
                                    React.createElement(
                                        "div",
                                        { style: { width: "600px", marginTop: "10px" } },
                                        React.createElement(
                                            "text",
                                            { className: "text20 eclipse" },
                                            v.name
                                        )
                                    ),
                                    React.createElement("img", { className: "mt-1 mr-1 mb-1 ml-1", style: { width: "50px", height: "50px", opacity: "0.4" }, src: "../images/stop.png", onClick: function onClick(e) {
                                            e.stopPropagation();_this9.deleteProduct(i);
                                        } })
                                ),
                                React.createElement(
                                    "div",
                                    { style: { height: "60px", display: "flex" } },
                                    React.createElement("div", { className: "left" }),
                                    React.createElement(
                                        "div",
                                        { className: "middle eclipse" },
                                        React.createElement(
                                            "text",
                                            null,
                                            v.purchase,
                                            " / ",
                                            v.insure,
                                            " / ",
                                            v.pay
                                        )
                                    ),
                                    React.createElement(
                                        "div",
                                        { className: "right" },
                                        React.createElement(
                                            "text",
                                            { style: { color: "#000" } },
                                            v.premium,
                                            "\u5143"
                                        )
                                    )
                                ),
                                React.createElement("div", { style: { height: "10px" } })
                            ) : React.createElement(
                                "div",
                                { className: "product product-rider bg-white text16" },
                                React.createElement(
                                    "div",
                                    { className: "left" },
                                    React.createElement(
                                        "text",
                                        { style: { color: "#0a0" } },
                                        "\u9644"
                                    )
                                ),
                                React.createElement(
                                    "div",
                                    { className: "middle eclipse" },
                                    React.createElement(
                                        "text",
                                        { style: { color: "#000", marginRight: "10px" } },
                                        v.abbrName
                                    ),
                                    React.createElement(
                                        "text",
                                        { style: { color: "#aaa" } },
                                        v.purchase,
                                        " / ",
                                        v.insure,
                                        " / ",
                                        v.pay
                                    )
                                ),
                                React.createElement(
                                    "div",
                                    { className: "right" },
                                    React.createElement(
                                        "text",
                                        { style: { color: "#000" } },
                                        v.premium,
                                        "\u5143"
                                    )
                                )
                            );
                        }),
                        plan.product && plan.product.length > 0 ? React.createElement(
                            "div",
                            { className: "card-content-line bg-white", style: { padding: "0 20px 0 20px", display: "block", marginTop: "10px", textAlign: "right", color: "#09bb07" } },
                            React.createElement(
                                "text",
                                { className: "text16" },
                                "\u5408\u8BA1\uFF1A",
                                plan.premium,
                                "\u5143"
                            )
                        ) : null,
                        React.createElement(
                            "div",
                            { className: "btn-fl text18", style: { color: "#fff", backgroundColor: "#1aad19" }, onClick: this.addProduct.bind(this) },
                            "\u6DFB\u52A0\u9669\u79CD"
                        )
                    )
                ),
                React.createElement("div", { style: { height: "120px" } }),
                React.createElement(
                    "div",
                    { className: "bottom text18 tc-primary" },
                    React.createElement(
                        "div",
                        { className: "ml-3 mr-0", style: { width: "300px" }, onClick: this.showBenefit.bind(this) },
                        "\u67E5\u770B\u5229\u76CA\u8D23\u4EFB"
                    ),
                    React.createElement(
                        "div",
                        { className: "divx", onClick: this.next.bind(this) },
                        React.createElement(
                            "div",
                            { className: "ml-0 mr-0", style: { width: "390px", textAlign: "right" } },
                            "\u9884\u89C8\u5EFA\u8BAE\u4E66"
                        ),
                        React.createElement(
                            "div",
                            { className: "ml-1 mr-2", style: { width: "30px" } },
                            React.createElement("img", { className: "mt-3", style: { width: "27px", height: "39px" }, src: "../images/blueright.png" })
                        )
                    )
                )
            );
        }
    }]);

    return Main;
}(React.Component);

$(document).ready(function () {
    ReactDOM.render(React.createElement(Main, null), document.getElementById("root"));
});

/***/ })

/******/ });
>>>>>>> master
