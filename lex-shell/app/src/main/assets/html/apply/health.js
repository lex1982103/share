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
/******/ 	return __webpack_require__(__webpack_require__.s = 5);
/******/ })
/************************************************************************/
/******/ ({

/***/ 5:
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
            orderId: common.param("orderId")
        };
        return _this;
    }

    _createClass(Main, [{
        key: "componentDidMount",
        value: function componentDidMount() {
            var _this2 = this;

            window.MF && MF.setTitle("健康告知");
            APP.apply.view(this.state.orderId, function (r) {
                _this2.setState({ order: r });
            });
        }
    }, {
        key: "verify",
        value: function verify() {
            this.next();
        }
    }, {
        key: "getIdCardImg",
        value: function getIdCardImg() {
            // 证件扫描
            this.setState({
                IdCardImg: {}
            });
        }
    }, {
        key: "next",
        value: function next() {
            var everyState = JSON.parse(localStorage.everyState);
            var stateData = this.state;
            everyState.health = stateData;
            localStorage.everyState = JSON.stringify(everyState);
            if (window.MF) {
                MF.navi("apply/beneficiary.html?orderId=" + this.state.orderId);
            } else {
                "apply/beneficiary.html?orderId=" + this.state.orderId;
            }
        }
    }, {
        key: "getIdCardImg",
        value: function getIdCardImg() {
            // 证件扫描
            this.setState({
                IdCardImg: {}
            });
        }
    }, {
        key: "render",
        value: function render() {
            return React.createElement(
                "div",
                null,
                React.createElement(
                    "div",
                    { className: "text19 ml-auto mr-auto mt-3", style: { textAlign: "center" } },
                    "\u88AB\u4FDD\u9669\u4EBA\u544A\u77E5",
                    React.createElement("br", null),
                    "\u8BE6\u7EC6\u544A\u77E5\u88AB\u4FDD\u9669\u4EBA\u8EAB\u4F53\u5065\u5EB7\u72B6\u51B5"
                ),
                React.createElement(
                    "div",
                    { className: "ml-auto mr-auto mt-3", style: { textAlign: "center" } },
                    React.createElement("img", { style: { width: "203px", height: "158px" }, src: "../images/healthverify.png" })
                ),
                React.createElement(
                    "div",
                    { className: "mt-3" },
                    React.createElement(
                        "div",
                        { className: "btn-fl text18 tc-white bg-primary", onClick: this.verify.bind(this) },
                        "\u5F00\u59CB\u5F55\u5165"
                    )
                ),
                React.createElement(
                    "div",
                    { className: "tc-gray text14 ml-auto mr-auto mt-3", style: { textAlign: "center" } },
                    "\u6574\u4E2A\u8FC7\u7A0B\u4E0D\u8D85\u8FC75\u5206\u949F\uFF0C\u4E14\u5F55\u5165\u7ED3\u679C",
                    React.createElement("br", null),
                    "\u4EC5\u5BF9\u544A\u77E5\u6709\u6548\uFF0C\u8BF7\u8BA4\u771F\u5F55\u5165\u4FE1\u606F"
                ),
                React.createElement(
                    "div",
                    { className: "bottom text18 tc-primary" },
                    React.createElement(
                        "div",
                        { className: "form-item-widget" },
                        React.createElement("img", { className: "mt-1", style: { width: "220px", height: "60px" }, src: "../images/btn-scan.png", onClick: this.getIdCardImg.bind(this) })
                    ),
                    React.createElement("div", { className: "ml-3 mr-0", style: { width: "300px" } }),
                    React.createElement(
                        "div",
                        { className: "divx", onClick: this.next.bind(this) },
                        React.createElement(
                            "div",
                            { className: "ml-0 mr-0", style: { width: "390px", textAlign: "right" } },
                            "\u53D7\u76CA\u4EBA"
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