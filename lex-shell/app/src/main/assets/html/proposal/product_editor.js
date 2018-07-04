<<<<<<< HEAD
!function(e){var t={};function n(r){if(t[r])return t[r].exports;var o=t[r]={i:r,l:!1,exports:{}};return e[r].call(o.exports,o,o.exports,n),o.l=!0,o.exports}n.m=e,n.c=t,n.d=function(e,t,r){n.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:r})},n.r=function(e){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},n.t=function(e,t){if(1&t&&(e=n(e)),8&t)return e;if(4&t&&"object"==typeof e&&e&&e.__esModule)return e;var r=Object.create(null);if(n.r(r),Object.defineProperty(r,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var o in e)n.d(r,o,function(t){return e[t]}.bind(null,o));return r},n.n=function(e){var t=e&&e.__esModule?function(){return e.default}:function(){return e};return n.d(t,"a",t),t},n.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},n.p="",n(n.s=24)}({24:function(e,t,n){"use strict";var r=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}();var o=function(e){function t(){!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t);var e=function(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}(this,(t.__proto__||Object.getPrototypeOf(t)).call(this));return e.state={planId:common.param("planId"),index:common.param("index"),form:[]},e}return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}(t,React.Component),r(t,[{key:"componentDidMount",value:function(){var e=this;this.state.form=[],APP.proposal.editProduct(this.state.planId,this.state.index,function(t){e.state.form.push({name:t.name,form:e.formOf(t.factors)}),e.setState({form:e.state.form})}),APP.proposal.viewPlan(this.state.planId,function(t){APP.proposal.listRiders(e.state.planId,e.state.index,function(n){n.map(function(n){var r={name:n.name,productId:n.code,form:null};t.product.map(function(t,o){t.productId==n.code&&t.parent==e.state.index&&APP.proposal.editProduct(e.state.planId,o,function(t){r.form=e.formOf(t.factors),e.setState({form:e.state.form})})}),e.state.form.push(r)}),e.setState({form:e.state.form})})})}},{key:"addRider",value:function(e,t){var n=this,r=t.productId,o=this.state.form[e];null==o.form?APP.proposal.addProduct(this.state.planId,this.state.index,r,function(e){e.product.map(function(e,t){e.productId==r&&e.parent==n.state.index&&APP.proposal.editProduct(n.state.planId,t,function(e){o.form=n.formOf(e.factors),n.setState({form:n.state.form})})})}):APP.proposal.deleteProduct(this.state.planId,this.state.index,r,function(e){o.form=null,n.setState({form:n.state.form})})}},{key:"close",value:function(){APP.back()}},{key:"formOf",value:function(e){return e.map(function(e){var t={};return e.detail&&e.detail.map(function(e){t[e[0]]=e[1]}),{widget:e.widget,varName:e.name,label:e.label,detail:t,value:e.value}})}},{key:"onValChange",value:function(e,t,n,r){var o=this;console.log(JSON.stringify(e)),console.log(JSON.stringify(r));var a={};a[e.varName]=r,APP.proposal.saveProduct(this.state.planId,t,a,function(e){o.state.form[t].form[n].value=r,o.setState({form:o.state.form})})}},{key:"render",value:function(){var e=this;return React.createElement("div",null,this.state.form.map(function(t,n){return React.createElement("div",{class:"div"},n>0?null:React.createElement("div",{className:"text18",style:{height:"80px",lineHeight:"80px",textAlign:"center",borderBottom:"#ddd solid 1px",backgroundColor:"#fff"}},t.name),0==n?null:React.createElement("div",{className:"divx",style:{height:"80px",verticalAlign:"middle",borderBottom:"#ddd solid 1px",backgroundColor:"#fff",marginTop:"10px"},onClick:e.addRider.bind(e,n,t)},React.createElement("img",{style:{margin:"10px 0 0 20px",width:"60px",height:"60px"},src:"../images/"+(null==t.form?"unchecked":"checked")+".png"}),React.createElement("div",{className:"text16",style:{marginLeft:"10px",lineHeight:"80px",color:null==t.form?"gray":"black"}},t.name)),null==t.form?null:t.form.map(function(t,r){return React.createElement("div",{className:"form-item",style:{backgroundColor:"#fff"}},React.createElement("div",{className:"form-item-label text17",style:{width:"300px",margin:"10px 0 0 20px"}},t.label),React.createElement("div",{className:"form-item-widget",style:{width:"410px",margin:"10px 20px 0 0",textAlign:"right"}},"number"==t.widget?React.createElement("input",{type:"number",placeholder:"请输入"+t.label}):"switch"==t.widget||"select"==t.widget?React.createElement("div",{style:{display:"flex"},onClick:function(o){APP.pick("select",t.detail,e.onValChange.bind(e,t,n,r))}},React.createElement("div",{className:"text17",style:{width:"350px",lineHeight:"60px"}},t.detail[t.value]),React.createElement("img",{style:{width:"60px",height:"60px"},src:"../images/arrow-7-down.png"})):null))}))}),React.createElement("div",{className:"btn-fl text18",style:{color:"#fff",backgroundColor:"#1aad19"},onClick:this.close.bind(this)},"确定"))}}]),t}();$(document).ready(function(){ReactDOM.render(React.createElement(o,null),document.getElementById("root"))})}});
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
/******/ 	return __webpack_require__(__webpack_require__.s = 4);
/******/ })
/************************************************************************/
/******/ ({

/***/ 4:
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
            planId: common.param("planId"),
            index: common.param("index"),
            form: []
        };
        return _this;
    }

    _createClass(Main, [{
        key: "componentDidMount",
        value: function componentDidMount() {
            var _this2 = this;

            this.state.form = [];
            APP.proposal.editProduct(this.state.planId, this.state.index, function (r) {
                _this2.state.form.push({
                    name: r.name,
                    form: _this2.formOf(r.factors)
                });
                _this2.setState({ form: _this2.state.form });
            });
            APP.proposal.viewPlan(this.state.planId, function (plan) {
                APP.proposal.listRiders(_this2.state.planId, _this2.state.index, function (r) {
                    r.map(function (v) {
                        var prdForm = {
                            name: v.name,
                            productId: v.code,
                            form: null
                        };
                        plan.product.map(function (r3, i) {
                            if (r3.productId == v.code && r3.parent == _this2.state.index) {
                                APP.proposal.editProduct(_this2.state.planId, i, function (r4) {
                                    prdForm.form = _this2.formOf(r4.factors);
                                    _this2.setState({ form: _this2.state.form });
                                });
                            }
                        });
                        _this2.state.form.push(prdForm);
                    });
                    _this2.setState({ form: _this2.state.form });
                });
            });
        }
    }, {
        key: "addRider",
        value: function addRider(prdIndex, prd) {
            var _this3 = this;

            var productId = prd.productId;
            var riderForm = this.state.form[prdIndex];
            if (riderForm.form == null) {
                APP.proposal.addProduct(this.state.planId, this.state.index, productId, function (r) {
                    r.product.map(function (r2, i) {
                        if (r2.productId == productId && r2.parent == _this3.state.index) APP.proposal.editProduct(_this3.state.planId, i, function (r1) {
                            riderForm.form = _this3.formOf(r1.factors);
                            _this3.setState({ form: _this3.state.form });
                        });
                    });
                });
            } else {
                APP.proposal.deleteProduct(this.state.planId, this.state.index, productId, function (r) {
                    riderForm.form = null;
                    _this3.setState({ form: _this3.state.form });
                });
            }
        }
    }, {
        key: "close",
        value: function close() {
            APP.back();
        }
    }, {
        key: "formOf",
        value: function formOf(f) {
            return f.map(function (w) {
                var r = {};
                if (w.detail) w.detail.map(function (v) {
                    r[v[0]] = v[1];
                });
                return {
                    widget: w.widget,
                    varName: w.name,
                    label: w.label,
                    detail: r,
                    value: w.value
                };
            });
        }
    }, {
        key: "onValChange",
        value: function onValChange(opt, prdIndex, formIndex, val) {
            var _this4 = this;

            console.log(JSON.stringify(opt));
            console.log(JSON.stringify(val));
            var vals = {};
            vals[opt.varName] = val;
            APP.proposal.saveProduct(this.state.planId, prdIndex, vals, function (r) {
                _this4.state.form[prdIndex].form[formIndex].value = val;
                _this4.setState({ form: _this4.state.form });
            });
        }
    }, {
        key: "render",
        value: function render() {
            var _this5 = this;

            return React.createElement(
                "div",
                null,
                this.state.form.map(function (w, i) {
                    return React.createElement(
                        "div",
                        { "class": "div" },
                        i > 0 ? null : React.createElement(
                            "div",
                            { className: "text18", style: { height: "80px", lineHeight: "80px", textAlign: "center", borderBottom: "#ddd solid 1px", backgroundColor: "#fff" } },
                            w.name
                        ),
                        i == 0 ? null : React.createElement(
                            "div",
                            { className: "divx", style: { height: "80px", verticalAlign: "middle", borderBottom: "#ddd solid 1px", backgroundColor: "#fff", marginTop: "10px" }, onClick: _this5.addRider.bind(_this5, i, w) },
                            React.createElement("img", { style: { margin: "10px 0 0 20px", width: "60px", height: "60px" }, src: "../images/" + (w.form == null ? 'unchecked' : 'checked') + ".png" }),
                            React.createElement(
                                "div",
                                { className: "text16", style: { marginLeft: "10px", lineHeight: "80px", color: w.form == null ? "gray" : "black" } },
                                w.name
                            )
                        ),
                        w.form == null ? null : w.form.map(function (v, j) {
                            return React.createElement(
                                "div",
                                { className: "form-item", style: { backgroundColor: "#fff" } },
                                React.createElement(
                                    "div",
                                    { className: "form-item-label text17", style: { width: "300px", margin: "10px 0 0 20px" } },
                                    v.label
                                ),
                                React.createElement(
                                    "div",
                                    { className: "form-item-widget", style: { width: "410px", margin: "10px 20px 0 0", textAlign: "right" } },
                                    v.widget == "number" ? React.createElement("input", { type: "number", placeholder: "请输入" + v.label }) : v.widget == "switch" || v.widget == "select" ? React.createElement(
                                        "div",
                                        { style: { display: "flex" }, onClick: function onClick(x) {
                                                APP.pick("select", v.detail, _this5.onValChange.bind(_this5, v, i, j));
                                            } },
                                        React.createElement(
                                            "div",
                                            { className: "text17", style: { width: "350px", lineHeight: "60px" } },
                                            v.detail[v.value]
                                        ),
                                        React.createElement("img", { style: { width: "60px", height: "60px" }, src: "../images/arrow-7-down.png" })
                                    ) : null
                                )
                            );
                        })
                    );
                }),
                React.createElement(
                    "div",
                    { className: "btn-fl text18", style: { color: "#fff", backgroundColor: "#1aad19" }, onClick: this.close.bind(this) },
                    "\u786E\u5B9A"
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
