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

var ClientList = function (_React$Component) {
    _inherits(ClientList, _React$Component);

    function ClientList() {
        _classCallCheck(this, ClientList);

        var _this = _possibleConstructorReturn(this, (ClientList.__proto__ || Object.getPrototypeOf(ClientList)).call(this));

        _this.state = {
            // clientCount: 4,
            clientList: [],
            mockData: [],
            pageNumber: 1
        };
        return _this;
    }

    _createClass(ClientList, [{
        key: "componentDidMount",
        value: function componentDidMount() {
            window.MF && MF.setTitle("客户管理");
            this.fetchClientList();
        }
    }, {
        key: "fetchClientList",
        value: function fetchClientList() {
            var _this2 = this;

            /** 按首字母分组
             * [{
             *     A:[ {
             *       name: ''
             *     }]
             * }]
             * */
            APP.list("/customer/list.json", { from: 0, number: 20 }, function (r) {
                _this2.setState({ mockData: r.list }, function () {
                    var data = _this2.state.mockData.map(function (d) {
                        var pinyinStr = pinyinUtil.getFirstLetter(d.name, false);
                        var firstAlpha = pinyinStr.substring(0, 1);
                        // console.log('speel', firstAlpha.toUpperCase());
                        d.alpha = firstAlpha.toUpperCase();
                        return d;
                    });
                    /** 按首字母排序 */
                    for (var i = 0; i < data.length; i++) {
                        for (var j = i + 1; j < data.length; j++) {
                            if (data[i].alpha > data[j].alpha) {
                                var temp = data[i];
                                data[i] = data[j];
                                data[j] = temp;
                            }
                        }
                    }
                    /**根据字母分组*/
                    var sortArr = [];
                    data.map(function (d) {
                        // 检查该字母是否已处理过
                        if (!sortArr.filter(function (item) {
                            return item.alpha === d.alpha;
                        }).length) {
                            var alphaObj = {
                                alpha: d.alpha
                            };
                            var arr = data.filter(function (item) {
                                return item.alpha === d.alpha;
                            });
                            alphaObj.list = arr;
                            sortArr.push(alphaObj);
                        }
                    });
                    _this2.setState({
                        clientList: sortArr
                    });
                });
            });
        }
    }, {
        key: "onAlphaClick",
        value: function onAlphaClick(id) {
            this.props.onAlphaClick && this.props.onAlphaClick(key);
            var el = document.getElementById(id);
            el.scrollIntoView();
        }
        /*编辑客户操作*/

    }, {
        key: "editClient",
        value: function editClient(data) {
            APP.list('/customer/view.json', { "customerId": data.id }, function (r) {
                window.MF && MF.navi("client/create_client.html?customerMsg=" + JSON.stringify(r));
            });
        }
        /*删除客户操作*/

    }, {
        key: "deleteClient",
        value: function deleteClient(data) {
            var _this3 = this;

            APP.alert("注意", "确定删除吗？", function (r) {
                APP.list('/customer/delete.json', { "customerId": data.id }, function (r) {
                    _this3.fetchClientList(); //刷新
                });
            }, function (r) {});
        }
        /*获取性别函数*/

    }, {
        key: "getSex",
        value: function getSex(code) {
            return code == "M" ? "男" : "女";
        }
    }, {
        key: "render",
        value: function render() {
            var _this4 = this;

            return React.createElement(
                "div",
                { className: "client-container" },
                React.createElement(
                    "div",
                    { className: "remind-wrap" },
                    React.createElement(
                        "a",
                        { className: "remind-birthday" },
                        React.createElement("img", { src: "../images/client/remind-birthday.png" }),
                        React.createElement(
                            "span",
                            null,
                            "\u751F\u65E5\u63D0\u9192"
                        ),
                        React.createElement(
                            "em",
                            null,
                            "2"
                        )
                    ),
                    React.createElement("span", null),
                    React.createElement(
                        "a",
                        { className: "remind-renew" },
                        React.createElement("img", { src: "../images/client/remind-renew.png" }),
                        React.createElement(
                            "span",
                            null,
                            "\u7EED\u671F\u63D0\u9192"
                        )
                    )
                ),
                React.createElement(
                    "div",
                    { className: "c-list" },
                    React.createElement(
                        "div",
                        { className: "cl-title" },
                        React.createElement(
                            "h3",
                            null,
                            "\u5F53\u524D\u9875\u5BA2\u6237",
                            React.createElement(
                                "i",
                                null,
                                this.state.mockData && this.state.mockData.length,
                                "\u4EBA"
                            )
                        )
                    ),
                    this.state.clientList.map(function (item) {
                        return React.createElement(
                            "dl",
                            { className: "cl-section list-group-item", id: 'sec' + item.alpha },
                            React.createElement(
                                "dt",
                                null,
                                item.alpha
                            ),
                            item.list.map(function (c) {
                                return React.createElement(
                                    "dd",
                                    null,
                                    React.createElement(
                                        "a",
                                        null,
                                        React.createElement(
                                            "span",
                                            null,
                                            c.name
                                        ),
                                        React.createElement(
                                            "i",
                                            null,
                                            _this4.getSex(c.gender)
                                        ),
                                        React.createElement(
                                            "em",
                                            null,
                                            c.birthday
                                        )
                                    ),
                                    React.createElement(
                                        "span",
                                        null,
                                        React.createElement(
                                            "a",
                                            { onClick: function onClick() {
                                                    _this4.editClient(c);
                                                } },
                                            "\u7F16\u8F91"
                                        ),
                                        React.createElement(
                                            "a",
                                            { onClick: function onClick() {
                                                    _this4.deleteClient(c);
                                                } },
                                            "\u5220\u9664"
                                        )
                                    ),
                                    React.createElement("span", { className: "cl-line" })
                                );
                            })
                        );
                    })
                ),
                React.createElement(
                    "div",
                    { className: "c-alphabet" },
                    this.state.clientList.map(function (c) {
                        return React.createElement(
                            "a",
                            { href: "javascript:void(0)", onClick: function onClick() {
                                    return _this4.onAlphaClick('sec' + c.alpha);
                                } },
                            c.alpha
                        );
                    })
                ),
                React.createElement(
                    "div",
                    { className: "c-footer" },
                    React.createElement(
                        "a",
                        { onClick: function onClick() {
                                window.MF && MF.navi("client/create_client.html?customerMsg=" + JSON.stringify({}));
                            } },
                        "\u65B0\u5EFA\u5BA2\u6237"
                    )
                )
            );
        }
    }]);

    return ClientList;
}(React.Component);

$(document).ready(function () {
    ReactDOM.render(React.createElement(ClientList, null), document.getElementById("root"));
});

/***/ })
/******/ ]);