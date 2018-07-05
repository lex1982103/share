
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
/******/ 	return __webpack_require__(__webpack_require__.s = 0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
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

        _this.state = {};
        return _this;
    }

    _createClass(Main, [{
        key: "componentDidMount",
        value: function componentDidMount() {
            MF.setTitle("新华人寿");
        }
    }, {
        key: "login",
        value: function login() {
            var _this2 = this;

            APP.login(this.refs.loginName.value, this.refs.password.value, function (r) {
                MF.setEnv("userKey", r.userKey);
                MF.navi("home/home.html");
            }, function (r) {
                _this2.setState({ login: "fail" });
            });
        }
    }, {
        key: "render",
        value: function render() {
            return React.createElement(
                "div",
                { style: { height: "100%" } },
                React.createElement(
                    "div",
                    { style: { height: "500px", background: "url('../images/logintitle.jpg')", textAlign: "center", verticalAlign: "middle" } },
                    React.createElement("img", { style: { margin: "100px auto auto auto", borderRadius: "150px", width: "300px", height: "300px", border: "10px solid white", boxShadow: "0 0 5px rgba(0, 198, 255,.6)" }, src: "../images/loginhead.jpg" })
                ),
                React.createElement(
                    "div",
                    { className: "form-item text16" },
                    React.createElement(
                        "div",
                        { className: "form-item-label", style: { width: "100px" } },
                        "\u8D26\u53F7"
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item-widget" },
                        React.createElement("input", { className: "mt-1", ref: "loginName", defaultValue: "test", placeholder: "\u8BF7\u8F93\u5165\u8D26\u53F7" })
                    )
                ),
                React.createElement(
                    "div",
                    { className: "form-item text16" },
                    React.createElement(
                        "div",
                        { className: "form-item-label", style: { width: "100px" } },
                        "\u5BC6\u7801"
                    ),
                    React.createElement(
                        "div",
                        { className: "form-item-widget" },
                        React.createElement("input", { className: "mt-1", ref: "password", type: "password", defaultValue: "123456", placeholder: "\u8BF7\u8F93\u5165\u5BC6\u7801" })
                    )
                ),
                this.state.login == "fail" ? React.createElement(
                    "div",
                    { className: "ml-2 mt-1 tc-red text14" },
                    "\u8D26\u53F7\u6216\u5BC6\u7801\u9519\u8BEF"
                ) : null,
                React.createElement(
                    "div",
                    { className: "btn-fl text18 tc-white bg-primary", onClick: this.login.bind(this) },
                    "\u767B\u5F55"
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
/******/ ]);
