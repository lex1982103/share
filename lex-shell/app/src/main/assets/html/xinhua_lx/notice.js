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
/******/ 	return __webpack_require__(__webpack_require__.s = 1);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */,
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Notice = function (_React$Component) {
    _inherits(Notice, _React$Component);

    function Notice() {
        _classCallCheck(this, Notice);

        var _this = _possibleConstructorReturn(this, (Notice.__proto__ || Object.getPrototypeOf(Notice)).call(this));

        _this.state = {
            ce: '111'
        };
        return _this;
    }

    _createClass(Notice, [{
        key: 'componentDidMount',
        value: function componentDidMount() {
            window.MF && MF.setTitle("通知书");
        }
    }, {
        key: 'testPopupDialog1',
        value: function testPopupDialog1(id, isT) {
            // var oHead = document.getElementsByTagName('HEAD').item(0);
            // var oScript= document.createElement("script");
            // oScript.type = "text/javascript";
            // oScript.src="qianming.js";
            // oHead.appendChild( oScript);
            sessionStorage.clear('ist');
            sessionStorage.setItem('ist', isT);
            testPopupDialog(id);
        }
    }, {
        key: 'submit',
        value: function submit() {
            console.log("提交");
        }
    }, {
        key: 'render',
        value: function render() {
            return React.createElement(
                'div',
                { className: 'notice-wrap' },
                React.createElement(
                    'div',
                    { id: 'other' },
                    React.createElement(
                        'div',
                        { className: 'notice-title' },
                        '\u5951\u7EA6\u5185\u5BB9\u53D8\u66F4\u901A\u77E5\u4E66'
                    ),
                    React.createElement(
                        'div',
                        { className: 'bar-code' },
                        React.createElement(
                            'div',
                            { className: 'bar-code-left' },
                            React.createElement(
                                'p',
                                null,
                                '\u56FE\u7247'
                            ),
                            React.createElement(
                                'p',
                                null,
                                '\u53F7\u7801'
                            )
                        ),
                        React.createElement(
                            'div',
                            { className: 'bar-code-rigth' },
                            React.createElement(
                                'p',
                                null,
                                '\u56FE\u7247'
                            ),
                            React.createElement(
                                'p',
                                null,
                                '\u53F7\u7801'
                            )
                        )
                    ),
                    React.createElement(
                        'div',
                        { className: 'odd-numbers' },
                        React.createElement(
                            'div',
                            null,
                            '\u6295\u4FDD\u5355\u53F7\uFF1A',
                            React.createElement(
                                'span',
                                null,
                                '44565545445'
                            )
                        ),
                        React.createElement(
                            'div',
                            null,
                            '\u5317\u4EAC\u76F4\u5C5E\u5206\u516C\u53F8'
                        )
                    ),
                    React.createElement(
                        'div',
                        { className: 'salesman-msg' },
                        React.createElement(
                            'div',
                            { className: 'salesman-left' },
                            React.createElement(
                                'p',
                                null,
                                '\u6295\u4FDD\u4EBA\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    '\u963F\u8428\u5FB7'
                                )
                            ),
                            React.createElement(
                                'p',
                                null,
                                '\u88AB\u4FDD\u4EBA\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    '\u963F\u5927\u4F7F'
                                )
                            )
                        ),
                        React.createElement(
                            'ul',
                            { className: 'salesman-right' },
                            React.createElement(
                                'li',
                                null,
                                '\u94F6\u884C\u53CA\u50A8\u84C4\u6240(\u4EE3\u7406\u4E13\u7528):'
                            ),
                            React.createElement(
                                'li',
                                null,
                                '\u4E1A\u4F59\u5206\u90E8\u53CA\u4E1A\u52A1\u7EC4(\u4EE3\u7406\u4E13\u7528)\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    'ff444'
                                )
                            ),
                            React.createElement(
                                'li',
                                null,
                                '\u8425\u9500\u670D\u52A1\u90E8\u53CA\u8425\u4E1A\u90E8\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    '\u671D\u9633\u8425\u9500\u670D\u52A1\u90E8 \u540C\u521B\u90E8'
                                )
                            ),
                            React.createElement(
                                'li',
                                { className: 'salesman-name' },
                                React.createElement(
                                    'p',
                                    null,
                                    '\u4E1A\u52A1\u5458\u59D3\u540D\uFF1A',
                                    React.createElement(
                                        'span',
                                        null,
                                        '\u6881\u9759\u8339'
                                    )
                                ),
                                React.createElement(
                                    'p',
                                    null,
                                    '\u4E1A\u52A1\u5440un\u7F16\u53F7\uFF1A',
                                    React.createElement(
                                        'span',
                                        null,
                                        '554544'
                                    )
                                )
                            )
                        )
                    ),
                    React.createElement(
                        'ul',
                        { className: 'policy-information' },
                        React.createElement(
                            'li',
                            null,
                            '\u5C0A\u656C\u7684',
                            React.createElement(
                                'span',
                                null,
                                '\u5730\u65B9\u6CD5'
                            ),
                            '\u5148\u751F/\u5973\u58EB\uFF1A'
                        ),
                        React.createElement(
                            'li',
                            { className: 'textIndex' },
                            '\u975E\u5E38\u611F\u8C22\u60A8\u5BF9\u6211\u516C\u53F8\u7684\u4FE1\u8D56\uFF01\u7ECF\u6211\u516C\u53F8\u5BA1\u614E\u8BC4\u4F30\uFF0C\u7531\u4E8E\u88AB\u4FDD\u9669\u4EBA\uFF1A',
                            React.createElement(
                                'span',
                                null,
                                '\u5FC3\u7535\u56FE\u5F02\u5E38\uFF0C\u6574\u4F53\u5065\u5EB7\u98CE\u9669\u9AD8\u4E8E\u6211\u516C\u53F8\u5236\u5B9A\u7684\u6807\u51C6\u4FDD\u8D39\u7387\uFF0C\u9700\u8981\u52A0\u8D39\u627F\u4FDD\u3002'
                            )
                        ),
                        React.createElement(
                            'li',
                            null,
                            '\u52A0\u8D39\u91D1\u989D\u5982\u4E0B\uFF1A'
                        ),
                        React.createElement(
                            'li',
                            { className: 'textIndex' },
                            '\u88AB\u4FDD\u9669\u4EBA\u5065\u5EB7\u52A0\u8D39\uFF1A'
                        ),
                        React.createElement(
                            'li',
                            { className: 'policy-additional' },
                            React.createElement(
                                'p',
                                null,
                                '\u9669\u79CD1\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    '13121'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u5065\u5EB7\u589E\u989D'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\uFFE544545/\u5E74'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u52A0\u8D39\u671F\u540C\u672C\u9669\u79CD\u4EA4\u8D39\u671F\u3002'
                                )
                            )
                        ),
                        React.createElement(
                            'li',
                            null,
                            '\u5408\u8BA1\u5E94\u7F34\u8D39\u91D1\u989D\uFF1A\uFFE5',
                            React.createElement(
                                'span',
                                null,
                                '75454.00'
                            )
                        ),
                        React.createElement(
                            'li',
                            null,
                            '\u4EA4\u8D39\u671F\u95F4\uFF1A',
                            React.createElement(
                                'span',
                                null,
                                '\u8DB8\u4EA4'
                            )
                        ),
                        React.createElement(
                            'li',
                            null,
                            '\u5982\u60A8\u5BF9\u6211\u4EEC\u7684\u670D\u52A1\u6709\u4EFB\u4F55\u7591\u95EE\u6216\u4E00\u952E\uFF0C\u8BF7\u8054\u7CFB\u60A8\u7684\u4FDD\u9669\u8425\u9500\u5458\u6216\u62E8\u6253\u6211\u606D\u9001\u5BA2\u670D\u70ED\u7EBF95567'
                        ),
                        React.createElement(
                            'li',
                            { className: 'salesman-data' },
                            React.createElement(
                                'p',
                                null,
                                '\u6838\u4FDD\u5458\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    'g4545'
                                )
                            )
                        ),
                        React.createElement(
                            'li',
                            { className: 'salesman-data' },
                            React.createElement(
                                'p',
                                null,
                                '\u65E5  \u671F\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    '2018\u5E746\u670829\u65E5'
                                )
                            )
                        )
                    ),
                    React.createElement(
                        'ul',
                        { className: 'opinion-wrap' },
                        React.createElement(
                            'li',
                            null,
                            '\u60A8\u7684\u610F\u89C1\uFF1A'
                        ),
                        React.createElement(
                            'li',
                            { className: 'textIndex opinion-consider' },
                            '\u8003\u8651\u8003\u8651'
                        ),
                        React.createElement(
                            'li',
                            { className: 'textIndex' },
                            '\u4EA4\u8D39\u65B9\u5F0F\u9009\u62E9\uFF1A'
                        ),
                        React.createElement(
                            'li',
                            { className: 'textIndex' },
                            '\u2585 \u4EE5\u73B0\u91D1/\u652F\u7968\u65B9\u5F0F\u4EA4\u8D39\u3002'
                        ),
                        React.createElement(
                            'li',
                            { className: 'textIndex' },
                            '\u25A1 \u540C\u610F\u6309\u539F\u94F6\u884C\u5212\u6B3E\u8D26\u6237\u4EA4\u8D39\u3002\u94F6\u884C\u8D26\u6237\u4E3A',
                            React.createElement(
                                'span',
                                null,
                                '454545'
                            ),
                            '\u3002'
                        ),
                        React.createElement(
                            'li',
                            { className: 'textIndex opinion-other' },
                            '\u25A1 \u5176\u4ED6\uFF0C\u8BE6\u8FF0\uFF1A',
                            React.createElement('span', { className: 'textUndeline' })
                        ),
                        React.createElement(
                            'li',
                            { className: 'textIndex opinion-sign' },
                            React.createElement(
                                'p',
                                null,
                                '\u6295\u4FDD\u4EBA\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    React.createElement('img', { id: 'xss_20', src: 'data:image/gif;base64,R0lGODlhhwBIAJECAL6+vtHR0f///wAAACH5BAEAAAIALAAAAACHAEgAAAL/jI+py+0Po5y02ouz3rz7Dx7CSJbmiabqyrbuC6NLTNf2jddKzvf+r9oBh8TiS2hMKo3IpfOJa0KnVJa0is0Krtruk+sNMxPi8hJsTufQ6jaN7Y634PK6jGzPu+j6PL9f9wcYJzjYVmiYhphYtngCAMkDUDOJEklzCeRokolTCdNZEuoyyrPJaRnz+dL5uSrwahnbcyr6WJoyuwLJywvb28sCPKw7hyc5THmbOxLryqqCu3ecU3xLTBz9TLIqza3rbYzgYw1t3jw5Wo4bLi7S81qeSor6+yhszylvRQ2KDYyJnixYqr6J2sdvnLJdC+dp40YqnkFa/c4xu/YvY7FQ7O1yIZymMCBDkST1fUQ3xNG6kfVsacv4KxizjjFU4nvYcqI+f8I00jSh0ucuZ/d2FnXZwle0GzaH4kQKkSXBgz3zXdRR8Ym6o0tRepXlytrJEovAQTV6dqrTZlFZiWWaVWorqstysk0aday3sSQQSdSplm7afUTTonX4Jq7HnK1mZcsG1ifAlzBtCNra9QZfzZvdGWCUqBZoLKJHUyltGgrq1E5Ws1bi+vWYkLIJKa4tJjZuTbd3d9HtmyLt4I16E68C/Djc4cp/G2/+5Tn0M9KnJ0lu/Uj17ERmcEceIrz48eTLmz+PPr368gUAADs=', onClick: this.testPopupDialog1.bind(this, 20, 0) })
                                )
                            ),
                            React.createElement(
                                'p',
                                null,
                                '\u88AB\u4FDD\u9669\u4EBA\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    React.createElement('img', { id: 'xss_21', src: 'data:image/gif;base64,R0lGODlhhwBIAJECAL6+vtHR0f///wAAACH5BAEAAAIALAAAAACHAEgAAAL/jI+py+0Po5y02ouz3rz7Dx7CSJbmiabqyrbuC6NLTNf2jddKzvf+r9oBh8TiS2hMKo3IpfOJa0KnVJa0is0Krtruk+sNMxPi8hJsTufQ6jaN7Y634PK6jGzPu+j6PL9f9wcYJzjYVmiYhphYtngCAMkDUDOJEklzCeRokolTCdNZEuoyyrPJaRnz+dL5uSrwahnbcyr6WJoyuwLJywvb28sCPKw7hyc5THmbOxLryqqCu3ecU3xLTBz9TLIqza3rbYzgYw1t3jw5Wo4bLi7S81qeSor6+yhszylvRQ2KDYyJnixYqr6J2sdvnLJdC+dp40YqnkFa/c4xu/YvY7FQ7O1yIZymMCBDkST1fUQ3xNG6kfVsacv4KxizjjFU4nvYcqI+f8I00jSh0ucuZ/d2FnXZwle0GzaH4kQKkSXBgz3zXdRR8Ym6o0tRepXlytrJEovAQTV6dqrTZlFZiWWaVWorqstysk0aday3sSQQSdSplm7afUTTonX4Jq7HnK1mZcsG1ifAlzBtCNra9QZfzZvdGWCUqBZoLKJHUyltGgrq1E5Ws1bi+vWYkLIJKa4tJjZuTbd3d9HtmyLt4I16E68C/Djc4cp/G2/+5Tn0M9KnJ0lu/Uj17ERmcEceIrz48eTLmz+PPr368gUAADs=', onClick: this.testPopupDialog1.bind(this, 20, 2) })
                                )
                            ),
                            React.createElement(
                                'p',
                                null,
                                '\u65E5\u671F\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    '2018\u5E746\u670829\u65E5'
                                )
                            )
                        )
                    ),
                    React.createElement(
                        'p',
                        { className: 'prompt-msg' },
                        '\u91CD\u8981\u63D0\u793A\uFF1A\u8BF7\u60A8\u6536\u5230\u672C\u901A\u77E5\u4E66\u540E\u5C3D\u5FEB\u529E\u7406\uFF0C\u4EE5\u514D\u5EF6\u8BEF\u4FDD\u9669\u5408\u540C\u751F\u6548\u3002'
                    )
                ),
                React.createElement(
                    'div',
                    { id: 'dialog', style: { display: 'none' } },
                    React.createElement(
                        'div',
                        { id: 'anysign_title', style: { color: '#333333' }, width: '100%', height: '10%' },
                        '\u8BF7\u6295\u4FDD\u4EBA',
                        React.createElement(
                            'span',
                            { style: { fontize: '20pt' } },
                            ' \u674E \u5143 '
                        ),
                        '\u7B7E\u540D'
                    ),
                    React.createElement(
                        'div',
                        { id: 'container', onmousedown: 'return false;' },
                        React.createElement('canvas', { id: 'anysignCanvas', width: '2' })
                    ),
                    React.createElement(
                        'div',
                        { id: 'comment_dialog', style: { display: 'none' } },
                        React.createElement(
                            'div',
                            { id: 'leftView' },
                            React.createElement('p', { id: 'comment_title' }),
                            React.createElement('div', { id: 'signImage', className: 'signImagecss' })
                        ),
                        React.createElement(
                            'div',
                            { id: 'tmpcanvascss', className: 'tmpcanvascss' },
                            React.createElement('div', { id: 'signTitle' }),
                            React.createElement('canvas', { id: 'comment_canvas' })
                        ),
                        React.createElement(
                            'div',
                            { id: 'comment_btnContainerInner', className: 'comment_btncontainer' },
                            React.createElement('input', { id: 'comment_ok', type: 'button', className: 'button orange', value: '\u786E \u5B9A' }),
                            React.createElement('input', { id: 'comment_back', type: 'button', className: 'button orange', value: '\u540E\u9000' }),
                            React.createElement('input', { id: 'comment_cancel', type: 'button', className: 'button orange', value: '\u53D6 \u6D88' })
                        )
                    ),
                    React.createElement(
                        'div',
                        { id: 'single_scrollbar', style: { textAlign: 'center', verticalAlign: 'middle' }, width: '100%' },
                        React.createElement(
                            'p',
                            null,
                            React.createElement(
                                'span',
                                { id: 'single_scroll_text' },
                                ' *\u6ED1\u52A8\u64CD\u4F5C\uFF1A'
                            )
                        ),
                        React.createElement(
                            'p',
                            null,
                            React.createElement('input', { id: 'single_scrollbar_up', type: 'button', className: 'button orange', value: '\u5DE6\u79FB' }),
                            React.createElement('input', { id: 'single_scrollbar_down', type: 'button', className: 'button orange', value: '\u53F3\u79FB' })
                        )
                    ),
                    React.createElement(
                        'div',
                        { id: 'btnContainerOuter', width: '100%' },
                        React.createElement(
                            'div',
                            { id: 'btnContainerInner', style: { textAlign: 'center', fontSize: '5pt' }, width: '100%' },
                            React.createElement('input', { id: 'btnOK', type: 'button', className: 'button orange', value: '\u786E \u5B9A', onClick: sign_confirm }),
                            React.createElement('input', { id: 'btnClear', type: 'button', className: 'button orange', value: '\u6E05 \u5C4F', onClick: clear_canvas }),
                            React.createElement('input', { id: 'btnCancel', type: 'button', className: 'button orange', value: '\u53D6 \u6D88', onClick: cancelSign })
                        )
                    )
                ),
                React.createElement(
                    'div',
                    { className: 'bottom text18 tc-primary' },
                    React.createElement('div', { className: 'ml-3 mr-0', style: { width: "300px" } }),
                    React.createElement(
                        'div',
                        { className: 'divx', onClick: this.submit.bind(this) },
                        React.createElement(
                            'div',
                            { className: 'ml-0 mr-0', style: { width: "390px", textAlign: "right" } },
                            '\u63D0\u4EA4'
                        ),
                        React.createElement(
                            'div',
                            { className: 'ml-1 mr-2', style: { width: "30px" } },
                            React.createElement('img', { className: 'mt-3', style: { width: "27px", height: "39px" }, src: '../images/blueright.png' })
                        )
                    )
                )
            );
        }
    }]);

    return Notice;
}(React.Component);

$(document).ready(function () {
    ReactDOM.render(React.createElement(Notice, null), document.getElementById("notice"));
});

/***/ })
/******/ ]);