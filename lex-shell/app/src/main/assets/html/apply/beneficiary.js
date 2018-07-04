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
/******/ 	return __webpack_require__(__webpack_require__.s = 2);
/******/ })
/************************************************************************/
/******/ ({

/***/ 2:
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
            certTypeDict: {},
            relationDict: {}
        };
        return _this;
    }

    _createClass(Main, [{
        key: "componentDidMount",
        value: function componentDidMount() {
            var _this2 = this;

            window.MF && MF.setTitle("受益人");
            APP.dict("cert,relation", function (r) {
                _this2.setState({
                    certTypeDict: r.cert,
                    relationDict: r.relation
                });
            });
            APP.apply.view(this.state.orderId, function (r) {
                _this2.setState({ order: r });
            });
        }
    }, {
        key: "onBenefitChange",
        value: function onBenefitChange(i, val) {
            if (!val) {
                this.popEditor({ index: i });
            }
            if (this.state.order.detail.insurants[i].benefitLaw != val) {
                this.state.order.detail.insurants[i].benefitLaw = val;
                this.forceUpdate();
            }
        }
    }, {
        key: "save",
        value: function save() {
            APP.apply.save({ id: this.state.orderId, detail: { insurants: this.state.order.detail.insurants } }, function (v) {});
        }
    }, {
        key: "next",
        value: function next() {
            this.save();
            if (window.MF) {
                MF.navi("apply/announce.html?orderId=" + this.state.orderId);
            } else {
                location.href = "apply/announce.html?orderId=" + this.state.orderId;
            }
        }
    }, {
        key: "delete",
        value: function _delete(i, j) {
            this.state.order.detail.insurants[i].beneficiary.splice(j, 1);
            this.forceUpdate();
        }
    }, {
        key: "popEditor",
        value: function popEditor(cust) {
            var _this3 = this;

            APP.pop("apply/beneficiary_editor.html?cust=" + JSON.stringify(cust), 60, function (r) {
                console.log(r);
                var c = JSON.parse(r);
                var ins = _this3.state.order.detail.insurants[c.index];
                if (!ins.beneficiary) ins.beneficiary = [];
                ins.beneficiary.push(c);
                _this3.forceUpdate();
            });
        }
    }, {
        key: "render",
        value: function render() {
            var _this4 = this;

            return this.state.order == null ? null : React.createElement(
                "div",
                null,
                this.state.order.detail.insurants.map(function (v, i) {
                    var law = v.benefitLaw || v.beneficiary == null || v.beneficiary.length == 0;
                    return React.createElement(
                        "div",
                        { className: "div bg-white mt-2" },
                        React.createElement(
                            "div",
                            { className: "divx bg-white pl-3 pr-3 pt-1 pb-1", style: { height: "100px", textAlign: "center" } },
                            React.createElement(
                                "div",
                                { className: "divx text18 mt-1 mr-auto", style: { height: "60px", verticalAlign: "middle", lineHeight: "60px" } },
                                React.createElement(
                                    "div",
                                    { className: "bg-primary tc-white mr-1", style: { borderRadius: "30px", width: "140px", height: "60px" } },
                                    "\u8BA1\u5212 ",
                                    i + 1
                                ),
                                v.name
                            ),
                            React.createElement(
                                "div",
                                { className: "btn-sm text17 " + (law ? "btn-sel" : ""), onClick: _this4.onBenefitChange.bind(_this4, i, true) },
                                "\u6CD5\u5B9A"
                            ),
                            React.createElement(
                                "div",
                                { className: "btn-sm text17 " + (!law ? "btn-sel" : ""), onClick: _this4.onBenefitChange.bind(_this4, i, false) },
                                law ? "约定" : "添加"
                            )
                        ),
                        law ? null : v.beneficiary.map(function (w, j) {
                            return React.createElement(
                                "div",
                                { className: "divx" },
                                React.createElement(
                                    "div",
                                    { className: "text40", style: { width: "210px", height: "140px", background: "url(../images/seq1.png) no-repeat top left", backgroundSize: "140px 140px", lineHeight: "140px", textAlign: "right" } },
                                    w.scale,
                                    "%"
                                ),
                                React.createElement(
                                    "div",
                                    { className: "div text16 pl-3 pr-3 pt-1 pb-1", style: { width: "470px", borderTop: "1px solid #e8e8e8" } },
                                    React.createElement(
                                        "div",
                                        { style: { height: "60px", lineHeight: "60px" } },
                                        w.name,
                                        "\uFF08\u88AB\u4FDD\u9669\u4EBA\u7684",
                                        _this4.state.relationDict[w.relation],
                                        "\uFF09"
                                    ),
                                    React.createElement(
                                        "div",
                                        { style: { height: "60px", lineHeight: "60px" } },
                                        _this4.state.certTypeDict[w.certType],
                                        "\uFF1A",
                                        w.certNo
                                    )
                                ),
                                React.createElement("img", { className: "mt-1 mr-1 ml-1", style: { width: "50px", height: "50px", opacity: "0.4" }, src: "../images/stop.png", onClick: _this4.delete.bind(_this4, i, j) })
                            );
                        })
                    );
                }),
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
                            "\u58F0\u660E\u53CA\u6388\u6743"
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