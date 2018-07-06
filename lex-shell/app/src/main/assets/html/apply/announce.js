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

        _this.state = {
            orderId: common.param("orderId")
        };
        return _this;
    }

    _createClass(Main, [{
        key: "componentDidMount",
        value: function componentDidMount() {
            window.MF && MF.setTitle("客户声明及授权");
        }
    }, {
        key: "next",
        value: function next() {
            if (window.MF) {
                MF.navi("apply/pay.html?orderId=" + this.state.orderId);
            } else {
                location.href = "apply/pay.html?orderId=" + this.state.orderId;
            }
        }
    }, {
        key: "render",
        value: function render() {
            return React.createElement(
                "div",
                { className: "bg-white" },
                React.createElement(
                    "div",
                    { className: "text19 pl-2 pr-2 pt-2" },
                    "\u4E00\u3001\u8D35\u516C\u53F8\u5DF2\u5411\u672C\u4EBA\u63D0\u4F9B\u4FDD\u9669\u6761\u6B3E\uFF0C\u8BF4\u660E\u4FDD\u9669\u5408\u540C\u5185\u5BB9\uFF0C\u7279\u522B\u63D0\u793A\u5E76\u660E\u786E\u8BF4\u660E\u4E86\u514D\u9664\u6216\u8005\u51CF\u8F7B\u4FDD\u9669\u4EBA\u8D23\u4EFB\u7684\u6761\u6B3E\uFF08\u5305\u62EC\u8D23\u4EFB\u514D\u9664\u6761\u6B3E\u3001\u514D\u8D54\u989D\u3001\u514D\u8D54\u7387\u3001\u6BD4\u4F8B\u8D54\u4ED8\u6216\u7ED9\u4ED8\u7B49\uFF09\u3002",
                    React.createElement("br", null),
                    "\u4E8C\u3001\u672C\u4EBA\u5DF2\u8BA4\u771F\u9605\u8BFB\u5E76\u5145\u5206\u7406\u89E3\u4FDD\u9669\u8D23\u4EFB\u3001\u8D23\u4EFB\u514D\u9664\u3001\u72B9\u8C6B\u671F\u3001\u5408\u540C\u751F\u6548\u3001\u5408\u540C\u89E3\u9664\u3001\u672A\u6210\u5E74\u4EBA\u8EAB\u6545\u4FDD\u9669\u91D1\u9650\u989D\u3001\u4FDD\u9669\u4E8B\u6545\u901A\u77E5\u3001\u4FDD\u9669\u91D1\u53D7\u76CA\u4EBA\u7684\u6307\u5B9A\u4E0E\u53D8\u66F4\u7B49\u4FDD\u9669\u6761\u6B3E\u7684\u5404\u9879\u6982\u5FF5\u3001\u5185\u5BB9\u53CA\u5176\u6CD5\u5F8B\u540E\u679C\uFF0C\u4EE5\u53CA\u6295\u8D44\u8FDE\u7ED3\u4FDD\u9669\u3001\u5206\u7EA2\u4FDD\u9669\u3001\u4E07\u80FD\u4FDD\u9669\u7B49\u65B0\u578B\u4EA7\u54C1\u7684\u4EA7\u54C1\u8BF4\u660E\u4E66\uFF0C\u672C\u4EBA\u81EA\u613F\u627F\u62C5\u4FDD\u5355\u5229\u76CA\u4E0D\u786E\u5B9A\u7684\u98CE\u9669\u3002",
                    React.createElement("br", null),
                    "\u4E09\u3001\u672C\u4EBA\u53CA\u88AB\u4FDD\u9669\u4EBA\u5728\u6295\u4FDD\u4E66\u4E2D\u7684\u6240\u6709\u9648\u8FF0\u548C\u544A\u77E5\u5747\u5B8C\u6574\u3001\u771F\u5B9E\uFF0C\u5DF2\u77E5\u6089\u5404\u9879\u6295\u4FDD\u8D44\u6599\u5982\u975E\u672C\u4EBA\u4EB2\u7B14\u7B7E\u540D\uFF0C\u5C06\u5BF9\u672C\u4FDD\u9669\u5408\u540C\u6548\u529B\u4EA7\u751F\u5F71\u54CD\u3002",
                    React.createElement("br", null),
                    "\u56DB\u3001\u672C\u4EBA\u5DF2\u77E5\u6653\u5E94\u771F\u5B9E\u3001\u51C6\u786E\u3001\u5B8C\u6574\u586B\u5199\u6295\u4FDD\u5404\u9879\u5185\u5BB9\uFF0C\u5C24\u5176\u662F\u6295\u88AB\u4FDD\u9669\u4EBA\u7684\u59D3\u540D\u3001\u6027\u522B\u3001\u751F\u65E5\u3001\u8BC1\u4EF6\u7C7B\u578B\u3001\u8BC1\u4EF6\u53F7\u7801\u3001\u804C\u4E1A\u3001\u8054\u7CFB\u7535\u8BDD\u53CA\u5730\u5740\u3001\u6295\u88AB\u4FDD\u9669\u4EBA\u5173\u7CFB\u7B49\uFF0C\u4EE5\u4E0A\u4FE1\u606F\u4E3B\u8981\u7528\u4E8E\u8BA1\u7B97\u4FDD\u8D39\u3001\u6838\u4FDD\u3001\u5BC4\u9001\u4FDD\u5355\u548C\u5BA2\u6237\u56DE\u8BBF\u7B49\uFF0C\u4EE5\u4FBF\u63D0\u4F9B\u66F4\u4F18\u8D28\u7684\u670D\u52A1\u3002\u5982\u4FE1\u606F\u7F3A\u5931\u3001\u4E0D\u5B9E\u5C06\u4F1A\u5BF9\u672C\u4EBA\u5229\u76CA\u4EA7\u751F\u4E0D\u5229\u5F71\u54CD\uFF0C\u540C\u65F6\u8D35\u516C\u53F8\u627F\u8BFA\u672A\u7ECF\u672C\u4EBA\u540C\u610F\uFF0C\u4E0D\u4F1A\u5C06\u672C\u4EBA\u4FE1\u606F\u7528\u4E8E\u516C\u53F8\u548C\u7B2C\u4E09\u65B9\u673A\u6784\u7684\u9500\u552E\u6D3B\u52A8\u3002",
                    React.createElement("br", null),
                    "\u4E94\u3001\u672C\u4EBA\u53CA\u88AB\u4FDD\u9669\u4EBA\u6388\u6743\u8D35\u516C\u53F8\u5728\u5FC5\u8981\u65F6\u53EF\u968F\u65F6\u5411\u6709\u5173\u673A\u6784\u6838\u5B9E\u672C\u4EBA\u53CA\u88AB\u4FDD\u9669\u4EBA\u3001\u4FDD\u9669\u91D1\u53D7\u76CA\u4EBA\u7684\u57FA\u672C\u4FE1\u606F\u6216\u5411\u88AB\u4FDD\u9669\u4EBA\u5C31\u8BCA\u7684\u533B\u9662\u6216\u533B\u5E08\u53CA\u793E\u4FDD\u3001\u519C\u5408\u3001\u5065\u5EB7\u7BA1\u7406\u4E2D\u5FC3\u7B49\u6709\u5173\u673A\u6784\u67E5\u8BE2\u6709\u5173\u8BB0\u5F55\u3001\u8BCA\u65AD\u8BC1\u660E\u3002\u672C\u4EBA\u548C\u88AB\u4FDD\u9669\u4EBA\u5BF9\u6B64\u5747\u65E0\u5F02\u8BAE\u3002",
                    React.createElement("br", null),
                    "\u516D\u3001\u672C\u4EBA\u6388\u6743\u8D35\u516C\u53F8\u59D4\u6258\u672C\u4EBA\u5F00\u6237\u94F6\u884C\u5BF9\u6307\u5B9A\u8D26\u6237\u6309\u7167\u4FDD\u9669\u5408\u540C\u7EA6\u5B9A\u7684\u65B9\u5F0F\u3001\u91D1\u989D\uFF0C\u5212\u8F6C\u9996\u671F\u3001\u7EED\u671F\u4FDD\u9669\u8D39\u53CA\u4EE5\u8F6C\u8D26\u65B9\u5F0F\u5C06\u4FDD\u9669\u91D1\u3001\u9000\u4FDD\u91D1\u3001\u9000\u8D39\u7B49\u7ED9\u4ED8\u8F6C\u5165\u6307\u5B9A\u8D26\u6237\uFF0C\u82E5\u672C\u4EBA\u6307\u5B9A\u8D26\u6237\u6216\u8054\u7CFB\u7535\u8BDD\u3001\u8054\u7CFB\u5730\u5740\u7B49\u4FE1\u606F\u53D1\u751F\u53D8\u66F4\uFF0C\u53CA\u65F6\u81F3\u8D35\u516C\u53F8\u529E\u7406\u53D8\u66F4\u624B\u7EED\uFF0C\u5982\u672A\u53CA\u65F6\u901A\u77E5\u8D35\u516C\u53F8\u53D8\u66F4\uFF0C\u56E0\u6B64\u4EA7\u751F\u7684\u76F8\u5E94\u4E0D\u5229\u540E\u679C\u7531\u672C\u4EBA\u627F\u62C5\u3002",
                    React.createElement("br", null),
                    "\u4E03\u3001\u672C\u4EBA\u5DF2\u77E5\u6089\u7535\u5B50\u6295\u4FDD\u7533\u8BF7\u786E\u8BA4\u4E66\u7B49\u6295\u4FDD\u8D44\u6599\u4E0D\u5F97\u4F5C\u4E3A\u6536\u53D6\u73B0\u91D1\u7684\u51ED\u8BC1\uFF0C\u516C\u53F8\u672A\u6388\u6743\u4FDD\u9669\u8425\u9500\u5458\u3001\u4FDD\u9669\u4E2D\u4ECB\u673A\u6784\uFF08\u94F6\u884C\u9664\u5916\uFF09\u6536\u53D61000\u5143\u4EE5\u4E0A\u7684\u73B0\u91D1\u4FDD\u9669\u8D39\u3002\u516C\u53F8\u5728\u627F\u4FDD\u4E4B\u524D\u6240\u6536\u4FDD\u8D39\u4E3A\u9884\u6536\u4FDD\u8D39\uFF0C\u4E0D\u4F5C\u4E3A\u662F\u5426\u540C\u610F\u627F\u4FDD\u7684\u4F9D\u636E\uFF0C\u5982\u4E0D\u7B26\u5408\u627F\u4FDD\u6761\u4EF6\uFF0C\u5C06\u5982\u6570\u9000\u8FD8\u3002",
                    React.createElement("br", null)
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
                            "\u652F\u4ED8\u4FE1\u606F"
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
/******/ ]);