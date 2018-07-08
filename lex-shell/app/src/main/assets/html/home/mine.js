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
            userMsglist: [{
                name: '客户管理',
                icon: '../images/mine/khgl.png',
                link: '../client/client_list.html'
            }, {
                name: '我的建议书',
                icon: '../images/mine/wdjys.png',
                link: '../proposal/start.html'
            }, {
                name: '投保单',
                icon: '../images/mine/tbd.png',
                link: '../insurance/insurance.html'
            }, {
                name: '电子保单',
                icon: '../images/mine/dzbd.png',
                link: 'xxxx'
            }, {
                name: '投保进度查询',
                icon: '../images/mine/tbjdcx.png',
                link: 'xxxx'
            }, {
                name: '在线保全',
                icon: '../images/mine/xzbq.png',
                link: 'xxxx'
            }, {
                name: '理赔服务',
                icon: '../images/mine/lpfw.png',
                link: 'xxxx'
            }]
        };
        return _this;
    }

    _createClass(Main, [{
        key: 'componentDidMount',
        value: function componentDidMount() {
            window.MF && MF.setTitle("新华人寿");
        }
    }, {
        key: 'listEnter',
        value: function listEnter(item) {
            // 列表点击
            switch (item.name) {
                case "客户管理":
                    location.href = item.link;
                    break;
                case "我的建议书":
                    location.href = item.link;
                    break;
                case "投保单":
                    location.href = item.link;
                    break;
            }
        }
    }, {
        key: 'myCard',
        value: function myCard() {
            // 我的名片
            location.href = "./myCard.html";
        }
    }, {
        key: 'render',
        value: function render() {
            var _this2 = this;

            return React.createElement(
                'div',
                { className: 'mine-wrap' },
                React.createElement(
                    'dl',
                    { onClick: this.myCard.bind(this), className: 'user-msg' },
                    React.createElement(
                        'dd',
                        null,
                        React.createElement(
                            'h6',
                            null,
                            '\u6F14\u793A\u7684'
                        ),
                        React.createElement(
                            'p',
                            null,
                            '\u5DE5\u53F7\uFF1A',
                            React.createElement(
                                'span',
                                null,
                                '2121211'
                            )
                        ),
                        React.createElement(
                            'p',
                            null,
                            '\u804C\u7EA7\uFF1A',
                            React.createElement(
                                'span',
                                null,
                                '\u90E8\u95E8\u7ECF\u7406'
                            )
                        )
                    ),
                    React.createElement(
                        'dt',
                        null,
                        React.createElement('img', { src: '../images/male.png', alt: '\u5934\u50CF' })
                    )
                ),
                React.createElement(
                    'ul',
                    { className: 'user-list' },
                    this.state.userMsglist && this.state.userMsglist.map(function (item, index) {
                        return React.createElement(
                            'li',
                            { onClick: _this2.listEnter.bind(_this2, item), key: index },
                            React.createElement(
                                'p',
                                null,
                                React.createElement('img', { src: item.icon, alt: '\u51FA\u9519' })
                            ),
                            React.createElement(
                                'p',
                                null,
                                React.createElement(
                                    'span',
                                    null,
                                    item.name
                                ),
                                React.createElement(
                                    'b',
                                    null,
                                    '\uFF1E'
                                )
                            )
                        );
                    })
                )
            );
        }
    }]);

    return Main;
}(React.Component);

ReactDOM.render(React.createElement(Main, null), document.getElementById("root"));

/***/ })
/******/ ]);