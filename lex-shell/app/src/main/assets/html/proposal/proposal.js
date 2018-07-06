
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
