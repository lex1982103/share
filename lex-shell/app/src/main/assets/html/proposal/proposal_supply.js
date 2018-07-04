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
/******/ 	return __webpack_require__(__webpack_require__.s = 8);
/******/ })
/************************************************************************/
/******/ ({

/***/ 8:
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
            genderDict: { "M": "男", "F": "女" },
            cust: [{}],
            mode: -1
        };
        return _this;
    }

    _createClass(Main, [{
        key: "componentDidMount",
        value: function componentDidMount() {
            var _this2 = this;

            window.MF && MF.setTitle("信息补充");
            APP.proposal.view(this.state.proposalId, function (r) {
                var cust = [r.applicant];
                r.detail.map(function (planId) {
                    APP.proposal.viewPlan(planId, function (plan) {
                        cust.push(plan.insurant);
                        _this2.setState({ cust: cust });
                    });
                });
            });
        }
    }, {
        key: "save",
        value: function save() {
            var _this3 = this;

            APP.proposal.supply(this.state.proposalId, { other: { custs: this.state.cust } }, function (v) {
                _this3.setState({ mode: -1, cust: _this3.state.cust });
            });
        }
    }, {
        key: "next",
        value: function next() {
            var _this4 = this;

            APP.proposal.save(this.state.proposalId, function (v) {
                if (window.MF) {
                    MF.navi("proposal/preview.html?proposalId=" + _this4.state.proposalId);
                } else {
                    location.href = "proposal/preview.html?proposalId=" + _this4.state.proposalId;
                }
            });
        }
    }, {
        key: "onValChange",
        value: function onValChange(index, key, e) {
            this.state.cust[index][key] = e.value;
            this.setState({ cust: this.state.cust });
        }
    }, {
        key: "render",
        value: function render() {
            var _this5 = this;

            return React.createElement(
                "div",
                null,
                this.state.cust.map(function (v, i) {
                    return React.createElement(
                        "div",
                        null,
                        React.createElement(
                            "div",
                            { className: "divx bg-white pl-3 pr-3", style: { height: "100px", marginTop: "20px", textAlign: "center" }, onClick: function onClick(v) {
                                    _this5.setState({ mode: _this5.state.mode == i ? -1 : i });
                                } },
                            React.createElement(
                                "div",
                                { className: "divx text18", style: { height: "60px", margin: "25px auto 0 auto", verticalAlign: "middle", lineHeight: "50px" } },
                                React.createElement("img", { style: { width: "50px", height: "50px", margin: "0 20px 0 65px" }, src: "../images/" + (_this5.state.mode == i ? "sub" : "add") + ".png" }),
                                i == 0 ? "投保人" : "被保险人" + i,
                                "\u4FE1\u606F"
                            ),
                            React.createElement(
                                "div",
                                { style: { width: "65px" } },
                                v.filled ? React.createElement("img", { style: { width: "65px", height: "50px", marginTop: "25px", float: "right" }, src: "../images/filled.png" }) : null
                            )
                        ),
                        _this5.state.mode != i ? null : React.createElement(
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
                                    React.createElement("input", { className: "mt-1", defaultValue: v.name, placeholder: "\u8BF7\u8F93\u5165\u59D3\u540D", onChange: _this5.onValChange.bind(_this5, i, "name") })
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
                                            APP.pick("select", _this5.state.genderDict, _this5.onValChange.bind(_this5, i, "gender"));
                                        } },
                                    React.createElement(
                                        "div",
                                        { className: (v.gender == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                        v.gender == null ? "请选择性别" : _this5.state.genderDict[v.gender]
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
                                            APP.pick("date", { begin: "1900-01-01", end: new Date() }, _this5.onValChange.bind(_this5, i, "birthday"));
                                        } },
                                    React.createElement(
                                        "div",
                                        { className: (v.birthday == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                                        v.birthday == null ? "请选择出生日期" : v.birthday
                                    ),
                                    React.createElement("img", { className: "mt-2 mr-0", style: { width: "27px", height: "39px" }, src: "../images/right.png" })
                                )
                            ),
                            React.createElement(
                                "div",
                                { className: "form-item text16" },
                                React.createElement("img", { className: "mt-1 ml-auto mr-3", style: { width: "120px", height: "60px" }, src: "../images/finish.png", onClick: _this5.save.bind(_this5) })
                            )
                        )
                    );
                }),
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