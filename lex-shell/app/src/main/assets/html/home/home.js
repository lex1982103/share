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

var _Swiper = __webpack_require__(1);

var _Swiper2 = _interopRequireDefault(_Swiper);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var serverUrl = 'http://114.112.96.61:7666/';

var Main = function (_React$Component) {
    _inherits(Main, _React$Component);

    function Main() {
        _classCallCheck(this, Main);

        var _this = _possibleConstructorReturn(this, (Main.__proto__ || Object.getPrototypeOf(Main)).call(this));

        _this.state = {
            images: [],
            searchInputting: false,
            searchText: '',
            products: [],
            footNav: ['首页', '个人管理', '团队管理', '我的'],
            LabelDta: '',
            orgId: common.param("orgId")
        };
        return _this;
    }

    _createClass(Main, [{
        key: 'componentDidMount',
        value: function componentDidMount() {
            var that = this;
            window.MF && MF.setTitle("首页");
            this.fetchBanner();
            this.fetchProduct();

            $.ajax({
                url: 'http://114.112.96.61:7666/app/user/qrymodule.json',
                type: "POST",
                data: {
                    "orgId": 10200
                },
                success: function success(data) {

                    that.setState({
                        LabelDta: data.content
                    });
                },
                fail: function fail(r) {},
                dataType: "json"
            });
        }
    }, {
        key: 'fetchProduct',
        value: function fetchProduct() {
            var _this2 = this;

            $.ajax({
                url: serverUrl + 'app/cms/article/list.json',
                type: "POST",
                data: JSON.stringify({
                    "from": 0,
                    "number": 10,
                    "state": 1 }),
                success: function success(r) {
                    _this2.setState({
                        products: r.content.list || []
                    });
                },
                fail: function fail(r) {},
                dataType: "json"
            });
        }
    }, {
        key: 'fetchBanner',
        value: function fetchBanner() {
            var _this3 = this;

            $.ajax({
                url: serverUrl + 'app/cms/adver/list.json',
                type: "POST",
                data: JSON.stringify({
                    "from": 0,
                    "number": 10,
                    "state": 1 }),
                success: function success(r) {
                    if (r.result == "success") {
                        var imageData = r.content.list.map(function (row) {
                            console.log(row);
                            return {
                                url: serverUrl + row.url,
                                link: serverUrl + row.link
                            };
                        });
                        _this3.setState({
                            images: imageData
                        });
                    }
                },
                fail: function fail(r) {},
                dataType: "json"
            });
        }
    }, {
        key: 'openApply',
        value: function openApply() {
            if (window.MF) {
                MF.navi("insurance/insurance.html");
            } else {
                location.href = "../insurance/insurance.html";
            }
        }
    }, {
        key: 'newApply',
        value: function newApply() {
            if (window.MF) {
                MF.navi("apply/start.html");
            } else {
                location.href = "../apply/start.html";
            }
        }
    }, {
        key: 'openProposal',
        value: function openProposal() {
            if (window.MF) {
                MF.navi("proposal/start.html");
            } else {
                location.href = "../proposal/start.html";
            }
        }
    }, {
        key: 'openCustomer',
        value: function openCustomer() {
            if (window.MF) {
                MF.navi("client/client_list.html");
            } else {
                location.href = "../client/client_list.html";
            }
        }
    }, {
        key: 'productDetail',
        value: function productDetail(prod) {
            localStorage.productData = JSON.stringify(prod);
            location.href = '../productDetail/productDetail.html';
        }
    }, {
        key: 'toPage',
        value: function toPage(index) {
            if (index == 3) {
                location.href = 'mine.html';
            }
        }
    }, {
        key: 'toFunPage',
        value: function toFunPage(url) {
            location.href = url + '.html';
        }
    }, {
        key: 'render',
        value: function render() {
            var _this4 = this;

            return React.createElement(
                'div',
                { className: 'home-container' },
                React.createElement(
                    'div',
                    { className: 'banner-wrap' },
                    React.createElement(_Swiper2.default, { images: this.state.images })
                ),
                React.createElement(
                    'div',
                    { className: 'shortcut-row1' },
                    this.state.LabelDta && this.state.LabelDta.map(function (prod) {
                        return React.createElement(
                            'a',
                            { className: 'srow-item', href: prod.link },
                            React.createElement(
                                'div',
                                null,
                                React.createElement('img', { src: prod.img })
                            ),
                            React.createElement(
                                'span',
                                null,
                                prod.name
                            )
                        );
                    })
                ),
                React.createElement(
                    'div',
                    { className: 'shortcut-row2' },
                    React.createElement(
                        'a',
                        { className: 'sr2-left', onClick: this.toFunPage.bind(this, 'CompanyIntroduction') },
                        React.createElement('img', { src: '../images/home/company.png' }),
                        React.createElement(
                            'span',
                            null,
                            '\u516C\u53F8\u4ECB\u7ECD'
                        )
                    ),
                    React.createElement(
                        'div',
                        { className: 'sr2-right', onClick: this.toFunPage.bind(this, 'productIntroduction') },
                        React.createElement(
                            'a',
                            { 'class': 'sr2-top' },
                            React.createElement('img', { src: '../images/home/product.png' }),
                            React.createElement(
                                'span',
                                null,
                                '\u4EA7\u54C1\u4ECB\u7ECD'
                            )
                        ),
                        React.createElement(
                            'div',
                            { 'class': 'sr2-bottom' },
                            React.createElement(
                                'a',
                                null,
                                React.createElement(
                                    'span',
                                    null,
                                    '\u81EA\u4E3B\u7ECF\u8425'
                                ),
                                React.createElement('img', { src: '../images/home/independent.png' })
                            ),
                            React.createElement(
                                'a',
                                null,
                                React.createElement(
                                    'span',
                                    null,
                                    '\u5C55\u793A\u5939'
                                ),
                                React.createElement('img', { src: '../images/home/showHome.png' })
                            )
                        )
                    )
                ),
                React.createElement(
                    'div',
                    { className: 'promote' },
                    React.createElement(
                        'div',
                        { className: 'p-title' },
                        React.createElement(
                            'span',
                            null,
                            '\u70ED\u9500\u63A8\u8350'
                        )
                    ),
                    React.createElement(
                        'div',
                        { className: 'p-content' },
                        this.state.products.map(function (prod) {
                            return React.createElement(
                                'a',
                                { className: 'prod-item', onClick: _this4.productDetail.bind(_this4, prod) },
                                React.createElement('img', { src: prod.cover ? serverUrl + prod.cover : "../images/home/default_img.png" }),
                                React.createElement(
                                    'i',
                                    null,
                                    prod.title
                                )
                            );
                        })
                    )
                ),
                React.createElement(
                    'ul',
                    { className: 'footNav' },
                    this.state.footNav.map(function (prod, index) {
                        if (index == 0) {
                            return React.createElement(
                                'li',
                                { className: 'actHome', onClick: _this4.toPage.bind(_this4, index) },
                                prod
                            );
                        } else if (index == 3) {
                            return React.createElement(
                                'li',
                                { className: 'actHome', onClick: _this4.toPage.bind(_this4, index) },
                                prod
                            );
                        } else {
                            return React.createElement(
                                'li',
                                null,
                                prod
                            );
                        }
                    })
                )
            );
        }
    }]);

    return Main;
}(React.Component);

$(document).ready(function () {
    ReactDOM.render(React.createElement(Main, null), document.getElementById("root"));
});

/***/ }),
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var PolySwiper = function (_React$Component) {
    _inherits(PolySwiper, _React$Component);

    function PolySwiper(props) {
        _classCallCheck(this, PolySwiper);

        var _this = _possibleConstructorReturn(this, (PolySwiper.__proto__ || Object.getPrototypeOf(PolySwiper)).call(this, props));

        _this.state = {
            images: props.images || [],
            initialized: false
        };
        return _this;
    }

    _createClass(PolySwiper, [{
        key: 'componentDidMount',
        value: function componentDidMount() {
            this.initSwiper();
        }
    }, {
        key: 'initSwiper',
        value: function initSwiper() {
            if (this.state.images.length > 0 && !this.state.initialized) {
                var mySwiper = new Swiper('.swiper-container', {
                    loop: true,
                    pagination: {
                        el: '.swiper-pagination',
                        dynamicBullets: true
                    }
                });
                this.setState({
                    initialized: true
                });
            }
        }
    }, {
        key: 'componentWillReceiveProps',
        value: function componentWillReceiveProps(nextProps) {
            if (nextProps['images'] !== undefined && nextProps['images'] !== null) {
                this.setState({
                    images: nextProps.images || ''
                }, function () {
                    this.initSwiper();
                });
            }
        }
    }, {
        key: 'render',
        value: function render() {
            return React.createElement(
                'div',
                { className: 'swiper-container' },
                React.createElement(
                    'div',
                    { className: 'swiper-wrapper' },
                    this.state.images.map(function (item, index) {
                        return React.createElement(
                            'div',
                            { className: 'swiper-slide', key: 'banner' + index },
                            React.createElement(
                                'a',
                                { href: item.link || '' },
                                React.createElement('img', { src: item.url })
                            )
                        );
                    })
                ),
                React.createElement('div', { className: 'swiper-pagination' })
            );
        }
    }]);

    return PolySwiper;
}(React.Component);

exports.default = PolySwiper;

/***/ })
/******/ ]);