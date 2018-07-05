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
/******/ 	return __webpack_require__(__webpack_require__.s = 3);
/******/ })
/************************************************************************/
/******/ ({

/***/ 3:
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

        var str = common.param("cust");
        _this.state = {
            cust: str ? JSON.parse(str) : {}
        };
        return _this;
    }

    _createClass(Main, [{
        key: "componentDidMount",
        value: function componentDidMount() {
            var _this2 = this;

            window.MF && MF.setTitle("编辑受益人");
            APP.dict("cert,relation", function (r) {
                _this2.setState({
                    certTypeDict: r.cert,
                    relationDict: r.relation
                });
            });
        }
    }, {
        key: "close",
        value: function close() {
            this.state.cust.name = this.refs.name.value;
            this.state.cust.certNo = this.refs.certNo.value;
            this.state.cust.scale = this.refs.scale.value;
            if (window.MF) {
                MF.back(JSON.stringify(this.state.cust));
            }
        }
    }, {
        key: "onValChange",
        value: function onValChange(key, val) {
            this.state.cust[key] = val;
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
                    { className: "bg-white text18", style: { height: "80px", lineHeight: "80px", textAlign: "center" } },
                    "\u53D7\u76CA\u4EBA\u4FE1\u606F"
                ),
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
                        React.createElement("input", { className: "mt-1", ref: "name", defaultValue: cust.name, placeholder: "\u8BF7\u8F93\u5165\u53D7\u76CA\u4EBA\u59D3\u540D" })
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
                        "\u4E0E\u88AB\u4FDD\u9669\u4EBA\u5173\u7CFB"
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item-widget", onClick: function onClick(v) {
                                APP.pick("select", _this3.state.relationDict, _this3.onValChange.bind(_this3, "relation"));
                            } },
                        React.createElement(
                            "div",
                            { className: (cust.relation == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                            cust.relation == null ? "请选择与被保险人关系" : this.state.relationDict[cust.relation]
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
                        "\u53D7\u76CA\u6B21\u5E8F"
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item-widget", onClick: function onClick(v) {
                                APP.pick("select", [1, 2, 3, 4, 5, 6], _this3.onValChange.bind(_this3, "sequence"));
                            } },
                        React.createElement(
                            "div",
                            { className: (cust.relation == null ? "tc-gray " : "") + "text16 ml-1 mr-auto" },
                            cust.sequence == null ? "请选择与被保险人关系" : "第" + (cust.sequence + 1) + "顺位"
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
                        "\u53D7\u76CA\u6BD4\u4F8B"
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item-widget" },
                        React.createElement("input", { className: "mt-1", ref: "scale", defaultValue: cust.scale, placeholder: "\u8BF7\u8F93\u5165\u53D7\u76CA\u6BD4\u4F8B" })
                    )
                ),
                React.createElement(
                    "div",
                    { className: "btn-fl text18 tc-white bg-primary", onClick: this.close.bind(this) },
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