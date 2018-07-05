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
/******/ 	return __webpack_require__(__webpack_require__.s = 9);
/******/ })
/************************************************************************/
/******/ ({

/***/ 9:
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
            orderId: common.param("orderId"),
            applicant: {},
            pay: null
        };
        return _this;
    }

    _createClass(Main, [{
        key: "componentDidMount",
        value: function componentDidMount() {
            var _this2 = this;

            window.MF && MF.setTitle("缴费信息");
            APP.dict("pay", function (r) {
                var bankMap = {};
                var payDict = r.pay.datas.map(function (v) {
                    var c = v.bankList.map(function (w) {
                        bankMap[w.bankCode] = { text: w.bankName };
                        return { code: w.bankCode, text: w.bankName };
                    });
                    bankMap[v.code] = { text: v.name, children: c };
                    return { code: v.code, text: v.name };
                });
                _this2.setState({
                    bankMap: bankMap,
                    payDict: payDict
                });
            });
            APP.apply.view(this.state.orderId, function (r) {
                var pay = r.extra && r.extra.pay ? r.extra.pay : {};
                _this2.setState({ applicant: r.detail.applicant, pay: pay });
            });
        }
    }, {
        key: "save",
        value: function save() {
            var _this3 = this;

            this.state.pay.applyNo = this.refs.applyNo.value;
            this.state.pay.bankCard = this.refs.bankCard.value;
            APP.apply.save({ id: this.state.orderId, extra: { pay: this.state.pay } }, function (r) {
                _this3.setState({ pay: r.extra.pay });
            });
        }
    }, {
        key: "next",
        value: function next() {
            this.save();
            if (window.MF) {
                MF.navi("apply/image.html?orderId=" + this.state.orderId);
            } else {
                location.href = "apply/image.html?orderId=" + this.state.orderId;
            }
        }
    }, {
        key: "onValChange",
        value: function onValChange(key, val) {
            if (key == "payMode") this.state.pay[key] = val;
            this.state.pay.bank = null;
            this.setState({ pay: this.state.pay });
        }
    }, {
        key: "render",
        value: function render() {
            var _this4 = this;

            var pay = this.state.pay;
            return pay == null ? null : React.createElement(
                "div",
                null,
                React.createElement(
                    "div",
                    { className: "form-item text16 mt-2" },
                    React.createElement(
                        "div",
                        { className: "form-item-label" },
                        "\u6295\u4FDD\u5355\u53F7"
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item-widget" },
                        React.createElement("input", { className: "mt-1", ref: "applyNo", defaultValue: pay.applyNo, placeholder: "\u8BF7\u8F93\u5165\u6295\u4FDD\u5355\u53F7" })
                    )
                ),
                React.createElement(
                    "div",
                    { className: "div", style: { marginTop: "20px" } },
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u94F6\u884C\u5361\u5F71\u50CF"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("img", { className: "mt-1", style: { width: "220px", height: "60px" }, src: "../images/btn-scan.png", onClick: function onClick(v) {
                                    APP.pick("scan", null, _this4.onValChange.bind(_this4, "bankCardImg"));
                                } })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u94F6\u884C\u5361\u53F7"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget" },
                            React.createElement("input", { className: "mt-1", ref: "bankCard", defaultValue: pay.bankCard, placeholder: "\u8BF7\u8F93\u5165\u94F6\u884C\u5361\u53F7" })
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u9996\u671F\u7F34\u8D39\u65B9\u5F0F"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget", onClick: function onClick(v) {
                                    APP.pick("select", _this4.state.payDict, _this4.onValChange.bind(_this4, "payMode"));
                                } },
                            React.createElement(
                                "div",
                                { className: (pay.payMode == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                pay.payMode == null ? "请选择首期缴费方式" : this.state.bankMap[pay.payMode].text
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
                            "\u5F00\u6237\u540D"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget pl-1" },
                            this.state.applicant.name
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item text16" },
                        React.createElement(
                            "div",
                            { className: "form-item-label" },
                            "\u5F00\u6237\u94F6\u884C"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget", onClick: function onClick(v) {
                                    APP.pick("select", _this4.state.bankMap[pay.payMode].children, _this4.onValChange.bind(_this4, "bank"));
                                } },
                            React.createElement(
                                "div",
                                { className: (pay.bank == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                pay.bank == null ? "请选择开户银行" : this.state.bankMap[pay.bank].text
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
                            "\u7EED\u671F\u7F34\u8D39\u65B9\u5F0F"
                        ),
                        React.createElement(
                            "div",
                            { className: "form-item-widget", onClick: function onClick(v) {
                                    APP.pick("select", _this4.state.payDict, _this4.onValChange.bind(_this4, "renewPayMode"));
                                } },
                            React.createElement(
                                "div",
                                { className: (pay.renewPayMode == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                pay.renewPayMode == null ? "请选择续期缴费方式" : this.state.bankMap[pay.renewPayMode].text
                            ),
                            React.createElement("img", { className: "mt-2 mr-0", style: { width: "27px", height: "39px" }, src: "../images/right.png" })
                        )
                    )
                ),
                React.createElement("div", { style: { height: "120px" } }),
                React.createElement(
                    "div",
                    { className: "bottom text18 tc-primary" },
                    React.createElement("div", { className: "ml-3 mr-0", style: { width: "300px" } }),
                    React.createElement(
                        "div",
                        { className: "divx", onClick: this.next.bind(this) },
                        React.createElement(
                            "div",
                            { className: "ml-0 mr-0", style: { width: "390px", textAlign: "right" } },
                            "\u786E\u8BA4\u652F\u4ED8"
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