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

var Autograph = function (_React$Component) {
    _inherits(Autograph, _React$Component);

    function Autograph() {
        _classCallCheck(this, Autograph);

        var _this = _possibleConstructorReturn(this, (Autograph.__proto__ || Object.getPrototypeOf(Autograph)).call(this));

        _this.state = {
            autographlistTop: [// 基本信息
            {
                name: '姓名',
                value: 'name'
            }, {
                name: '性别',
                value: 'gender'
            }, {
                name: '出生日期',
                value: 'birthday'
            }, {
                name: '证件类型',
                value: 'certType'
            }, {
                name: '证件号码',
                value: 'certNo'
            }, {
                name: '证件有效期限',
                value: 'certValidDate'
            }, {
                name: '国籍',
                value: 'nation'
            }, {
                name: '婚姻及子女情况',
                value: 'marriage'
            }, {
                name: '与被保险人关系',
                value: 'relation'
            }, {
                name: '工作单位',
                value: 'company'
            }, {
                name: '职位名称',
                value: 'workJob'
            }, {
                name: '职业编码',
                value: 'occupation'
            }, {
                name: '移动电话',
                value: 'mobile'
            }, {
                name: '固定电话',
                value: 'telephone'
            }],
            autographlistBom: [// 基本信息
            {
                name: '通讯(常住)地址',
                value: 'address'
            }, {
                name: '邮编',
                value: 'zipcode'
            }, {
                name: '每年可支配收入',
                value: 'income'
            }, {
                name: '居民类型',
                value: 'myType'
            }, {
                name: '是否参加基本医保保障',
                value: 'hospital'
            }, {
                name: '税收居民身份',
                value: '测试'
            }],
            beneficiaryList: [1, 2, 3], // 身故保险金受益人
            especiallyList: [1, 2, 3, 4, 5, 6, 7, 8], // 税收居民身份
            informList: [// 投保告知
            {
                name: '身高(厘米)',
                value: '170'
            }, {
                name: '体重(公斤)',
                value: '59'
            }, {
                name: '您是否有吸烟习惯？',
                value: '否'
            }, {
                name: '您是否有每天饮自洒的习惯？',
                value: '否'
            }, {
                name: '在过去的2年中，您是否在国外持续展住超过6个月成准备在1年内出国?',
                value: '否'
            }, {
                name: '您足否参与任何危险的运动或赛，(调水、跳伞、滑用、高.长攀岩,探险、武术比赛、摔跤比赛、特技表演、赛马、赛车、驾驶或乘坐非民航客机的私人飞行活动)?',
                value: '否'
            }, {
                name: '您是否有被保险公司拒绝承保，或加费承保.或延期承保，或附加特别约定承保的经历?',
                value: '否'
            }, {
                name: '您是否以被保险人的身份投保过或正在申济其他保险公司人寿保险?',
                value: '否'
            }, {
                name: '您的父毋、兄弟、如妹是否患有恶性肿痛、A症、白血病、冠心14、心肌病、麟尿病、中风(脑出血、脑便塞)、任何遗传性疾病?',
                value: '否'
            }, {
                name: '您是否患有或曾经患仃高血压、冠心病、心肌病、中风(脑出血、脑使坳、动脉瘤、箱尿病、胰腺炎、慢性支气管炎 哮峭?',
                value: '否'
            }, {
                name: '您是否患有或曾经患有甲状膝结节、甲状膝功能亢进或减退、肝炎或肝炎病毒倪带者、肝砚化、肾炎、肾病综合征、丹功能不全、帕金森病、系统性红斑娘疮、艾滋病?',
                value: '否'
            }, {
                name: '您是否患有或曾经患有任何肿翻或痛症、原位燎、结肠息肉、白血病、任何身体成智力残疾、铭痛或挤神障双打',
                value: '否'
            }, {
                name: '在过去的5年内，您足否因上述告知情况以外的疾病住院洽疗，或被医生建议佳院治疗，或因疾病连续服药超过l个月',
                value: '否'
            }, {
                name: '.在过去2年内，您是否接受过X光，超声，CT，核班，心.川侧，内窥镜，病理检汽，血液、尿液投代，及儿他待殊价 夜JI枪夜结染异常',
                value: '否'
            }, {
                name: '在过去的1年内，您是否因出现症状或身体不适而接受治疗或被医生建议治疗，或因此连续服药超过1个月?',
                value: '否'
            }],
            isElectronics: true, // 是否电子签名
            cust: {}
        };
        return _this;
    }

    _createClass(Autograph, [{
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
        key: 'componentDidMount',
        value: function componentDidMount() {
            var _this2 = this;

            window.MF && MF.setTitle("投保单预览");
            APP.apply.view(common.param("orderId"), function (r) {
                console.log(JSON.stringify(r.detail));
                _this2.setState({
                    cust: r.detail
                });
            });
        }
    }, {
        key: 'submit',
        value: function submit() {
            this.next();
        }
    }, {
        key: 'next',
        value: function next() {
            if (windwo.MF) {
                MF.navi("apply/success.html?orderId=" + this.state.orderId);
            } else {
                location.href = "../apply/success.html?orderId=" + this.state.orderId;
            }
        }
    }, {
        key: 'render',
        value: function render() {
            var cust = this.state.cust;

            return React.createElement(
                'div',
                { className: 'autograph-table' },
                React.createElement(
                    'div',
                    { id: 'other' },
                    React.createElement(
                        'div',
                        { className: 'table-head' },
                        React.createElement(
                            'div',
                            null,
                            React.createElement(
                                'p',
                                null,
                                '\u7535\u5B50\u6295\u4FDD\u4E66'
                            ),
                            React.createElement(
                                'p',
                                null,
                                'EUA001'
                            )
                        ),
                        React.createElement(
                            'div',
                            null,
                            '\u5BF9\u5E94\u7535\u5B50\u6295\u4FDD\u7533\u8BF7\u786E\u8BA4\u53F7\u7801\uFF1A',
                            React.createElement(
                                'span',
                                null,
                                '4454454554'
                            )
                        )
                    ),
                    React.createElement(
                        'div',
                        { className: 'autograph-top' },
                        React.createElement(
                            'div',
                            { className: 'autograph-topLeft' },
                            '\u57FA\u672C\u4FE1\u606F'
                        ),
                        React.createElement(
                            'div',
                            { className: 'autograph-topRight' },
                            React.createElement(
                                'p',
                                null,
                                React.createElement(
                                    'span',
                                    null,
                                    '\u4E2A\u4EBA\u8D44\u6599'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u6295\u4FDD\u4EBA'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u88AB\u4FDD\u9669\u4EBA'
                                )
                            ),
                            React.createElement(
                                'ul',
                                null,
                                React.createElement(
                                    'li',
                                    null,
                                    this.state.autographlistTop.map(function (item) {
                                        return React.createElement(
                                            'p',
                                            null,
                                            item.name
                                        );
                                    })
                                ),
                                React.createElement(
                                    'li',
                                    null,
                                    this.state.autographlistTop.map(function (item) {
                                        return React.createElement(
                                            'p',
                                            null,
                                            Object.keys(cust).length && cust.applicant[item.value]
                                        );
                                    })
                                ),
                                React.createElement(
                                    'li',
                                    null,
                                    this.state.autographlistTop.map(function (item) {
                                        return React.createElement(
                                            'p',
                                            null,
                                            Object.keys(cust).length && cust.insurants[0][item.value]
                                        );
                                    })
                                )
                            ),
                            React.createElement(
                                'p',
                                { className: 'autograph-email' },
                                React.createElement(
                                    'span',
                                    null,
                                    '\u7535\u5B50\u90AE\u7BB1'
                                ),
                                React.createElement(
                                    'div',
                                    null,
                                    React.createElement(
                                        'p',
                                        null,
                                        React.createElement(
                                            'span',
                                            null,
                                            '\u7535\u5B50\u4FDD\u5355\u670D\u52A1  \u9009\u62E9\u201C\u7535\u5B50\u4FDD\u5355\u670D\u52A1\u201D\u4E0D\u5F71\u54CD\u7EB8\u8D28\u4FDD\u9669\u5355\u7684\u9001\u8FBE\u3002'
                                        )
                                    ),
                                    React.createElement(
                                        'p',
                                        null,
                                        React.createElement(
                                            'span',
                                            null,
                                            '\u7535\u5B50\u4FDD\u5355\u670D\u52A1  \u9009\u62E9\u201C\u7535\u5B50\u4FDD\u5355\u670D\u52A1\u201D\u4E0D\u5F71\u54CD\u7EB8\u8D28\u4FDD\u9669\u5355\u7684\u9001\u8FBE\u3002'
                                        )
                                    )
                                )
                            ),
                            this.state.autographlistBom.map(function (item) {
                                return React.createElement(
                                    'p',
                                    null,
                                    React.createElement(
                                        'span',
                                        null,
                                        item.name
                                    ),
                                    React.createElement(
                                        'span',
                                        null,
                                        item.value
                                    ),
                                    React.createElement(
                                        'span',
                                        null,
                                        '11'
                                    )
                                );
                            })
                        )
                    ),
                    React.createElement(
                        'div',
                        { className: 'autograph-mid' },
                        React.createElement(
                            'div',
                            { className: 'autograph-topLeft' },
                            '\u25A0'
                        ),
                        React.createElement(
                            'div',
                            null,
                            React.createElement(
                                'p',
                                null,
                                React.createElement(
                                    'span',
                                    null,
                                    '\u8EAB\u6545\u4FDD\u9669\u91D1\u53D7\u76CA\u4EBA\u4E3A\u88AB\u4FDD\u4EBA\u7684\u6CD5\u5B9A\u7EE7\u627F\u4EBA'
                                )
                            )
                        )
                    ),
                    React.createElement(
                        'div',
                        { className: 'autograph-beneficiary' },
                        React.createElement(
                            'div',
                            { className: 'beneficiary-left' },
                            '\u8EAB\u6545\u4FDD\u9669\u91D1\u53D7\u76CA\u4EBA'
                        ),
                        React.createElement(
                            'ul',
                            { className: 'beneficiary-right' },
                            this.state.beneficiaryList.map(function (item) {
                                return React.createElement(
                                    'li',
                                    null,
                                    React.createElement(
                                        'div',
                                        null,
                                        item
                                    ),
                                    React.createElement(
                                        'div',
                                        null,
                                        React.createElement(
                                            'p',
                                            null,
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u59D3\u540D:',
                                                React.createElement('span', null)
                                            )
                                        ),
                                        React.createElement(
                                            'p',
                                            null,
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u8BC1\u4EF6\u7C7B\u578B:',
                                                React.createElement('span', null)
                                            ),
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u8BC1\u4EF6\u53F7\u7801:',
                                                React.createElement('span', null)
                                            )
                                        ),
                                        React.createElement(
                                            'p',
                                            null,
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u4E0E\u88AB\u4FDD\u4EBA\u5173\u7CFB:',
                                                React.createElement('span', null)
                                            ),
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u56FD\u7C4D:',
                                                React.createElement('span', null)
                                            )
                                        ),
                                        React.createElement(
                                            'p',
                                            { className: 'beneficiary-address' },
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u901A\u8BAF(\u5E38\u4F4F)\u5730\u5740:',
                                                React.createElement('span', null)
                                            ),
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u90AE\u653F\u7F16\u7801:',
                                                React.createElement('span', null)
                                            )
                                        )
                                    ),
                                    React.createElement(
                                        'div',
                                        null,
                                        React.createElement(
                                            'p',
                                            null,
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u51FA\u751F\u65E5\u671F:',
                                                React.createElement('span', null)
                                            ),
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u6027\u522B:',
                                                React.createElement('span', null)
                                            )
                                        ),
                                        React.createElement(
                                            'p',
                                            null,
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u8BC1\u4EF6\u6709\u6548\u671F\u9650:',
                                                React.createElement('span', null)
                                            )
                                        ),
                                        React.createElement(
                                            'p',
                                            null,
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u53D7\u76CA\u987A\u5E8F:',
                                                React.createElement('span', null)
                                            ),
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u53D7\u76CA\u4EFD\u989D%',
                                                React.createElement('span', null)
                                            )
                                        ),
                                        React.createElement(
                                            'p',
                                            null,
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u804C\u4E1A\u540D\u79F0/\u7F16\u7801:',
                                                React.createElement('span', null)
                                            )
                                        ),
                                        React.createElement(
                                            'p',
                                            null,
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u8054\u7CFB\u7535\u8BDD:',
                                                React.createElement('span', null)
                                            )
                                        )
                                    )
                                );
                            })
                        )
                    ),
                    React.createElement(
                        'div',
                        { className: 'matter-main' },
                        React.createElement(
                            'div',
                            { className: 'matter-left' },
                            '\u6295\u4FDD\u4E8B\u9879'
                        ),
                        React.createElement(
                            'ul',
                            null,
                            React.createElement(
                                'li',
                                null,
                                React.createElement(
                                    'span',
                                    null,
                                    '\u4FDD\u9669\u540D\u79F0'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u9669\u79CD\u4EE3\u7801'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u4FDD\u969C\u8BA1\u5212\u7C7B\u522B'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u4FDD\u969C\u671F\u95F4'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u4FDD\u9669\u91D1\u989D/\u6295\u4FDD\u4EFD\u6570'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u4EA4\u8D39\u65B9\u5F0F'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u4EA4\u8D39\u671F\u95F4',
                                    React.createElement(
                                        'p',
                                        null,
                                        '(\u5E74\u6216\u81F3\u5468\u5C81)'
                                    )
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u4FDD\u9669\u8D39',
                                    React.createElement(
                                        'p',
                                        null,
                                        '(\u671F\u4EA4\u4EC5\u6307\u9996\u671F)'
                                    )
                                )
                            ),
                            React.createElement(
                                'li',
                                null,
                                React.createElement(
                                    'span',
                                    null,
                                    '\u5B9E\u65BD\u7B2C\u4E09\u65B9'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '3456787'
                                ),
                                React.createElement('span', null),
                                React.createElement(
                                    'span',
                                    null,
                                    '15'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '343546'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u4E00\u6B21\u4EA4\u6E05'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '\u4E00\u6B21\u4EA4\u6E05'
                                ),
                                React.createElement(
                                    'span',
                                    null,
                                    '345565'
                                )
                            ),
                            React.createElement(
                                'li',
                                null,
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null)
                            ),
                            React.createElement(
                                'li',
                                null,
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null)
                            ),
                            React.createElement(
                                'li',
                                null,
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null)
                            ),
                            React.createElement(
                                'li',
                                null,
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null)
                            ),
                            React.createElement(
                                'li',
                                null,
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null)
                            ),
                            React.createElement(
                                'li',
                                null,
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null),
                                React.createElement('span', null)
                            ),
                            React.createElement(
                                'li',
                                { className: 'matter-total' },
                                React.createElement(
                                    'div',
                                    null,
                                    React.createElement(
                                        'h6',
                                        null,
                                        '\u4FDD\u969C\u8D39\u5408\u8BA1: ',
                                        React.createElement(
                                            'b',
                                            null,
                                            '\u963F\u5FB7\u662F\u51E1\u8FBE\u65AF'
                                        )
                                    ),
                                    React.createElement(
                                        'h6',
                                        null,
                                        '(\u5C0F\u5199) ',
                                        React.createElement(
                                            'b',
                                            null,
                                            '\uFFE5\uFF1A5455'
                                        )
                                    )
                                )
                            ),
                            React.createElement(
                                'li',
                                { className: 'matter-total' },
                                React.createElement(
                                    'div',
                                    null,
                                    React.createElement(
                                        'h6',
                                        null,
                                        '\u4FDD\u969C\u8D39\u5408\u8BA1: ',
                                        React.createElement(
                                            'b',
                                            null,
                                            '\u963F\u5FB7\u662F\u51E1\u8FBE\u65AF'
                                        )
                                    ),
                                    React.createElement(
                                        'h6',
                                        null,
                                        '\u5C0F\u5199) ',
                                        React.createElement(
                                            'b',
                                            null,
                                            '\uFFE5\uFF1A5455'
                                        )
                                    )
                                )
                            ),
                            React.createElement(
                                'ol',
                                { className: 'matter-bom' },
                                React.createElement(
                                    'li',
                                    null,
                                    React.createElement(
                                        'div',
                                        null,
                                        '\u9996\u671F'
                                    ),
                                    React.createElement(
                                        'div',
                                        null,
                                        React.createElement(
                                            'div',
                                            null,
                                            React.createElement(
                                                'h6',
                                                null,
                                                '\u4EA4\u8D39\u5F62\u5F0F:',
                                                React.createElement(
                                                    'b',
                                                    null,
                                                    '\u963F\u5FB7\u662F\u51E1\u8FBE\u65AF'
                                                )
                                            ),
                                            React.createElement(
                                                'h6',
                                                null,
                                                '\u6307\u5B9A\u8D26\u6237\u540D\u5B57:',
                                                React.createElement(
                                                    'b',
                                                    null,
                                                    '\u7231\u7684'
                                                )
                                            )
                                        ),
                                        React.createElement(
                                            'div',
                                            null,
                                            React.createElement(
                                                'h6',
                                                null,
                                                '\u5F00\u6237\u884C:',
                                                React.createElement(
                                                    'b',
                                                    null,
                                                    '\u519C\u4E1A\u94F6\u884C'
                                                )
                                            ),
                                            React.createElement(
                                                'h6',
                                                null,
                                                '\u8D26\u6237:',
                                                React.createElement(
                                                    'b',
                                                    null,
                                                    '4554655655646'
                                                )
                                            )
                                        )
                                    )
                                ),
                                React.createElement(
                                    'li',
                                    null,
                                    React.createElement(
                                        'div',
                                        null,
                                        '\u7EED\u671F'
                                    ),
                                    React.createElement(
                                        'div',
                                        null,
                                        React.createElement(
                                            'div',
                                            null,
                                            React.createElement(
                                                'h6',
                                                null,
                                                '\u4EA4\u8D39\u5F62\u5F0F:',
                                                React.createElement(
                                                    'b',
                                                    null,
                                                    '\u963F\u5FB7\u662F\u51E1\u8FBE\u65AF'
                                                )
                                            ),
                                            React.createElement(
                                                'h6',
                                                null,
                                                '\u6307\u5B9A\u8D26\u6237\u540D\u5B57:',
                                                React.createElement(
                                                    'b',
                                                    null,
                                                    '\u7231\u7684'
                                                )
                                            )
                                        ),
                                        React.createElement(
                                            'div',
                                            null,
                                            React.createElement(
                                                'h6',
                                                null,
                                                '\u5F00\u6237\u884C:',
                                                React.createElement(
                                                    'b',
                                                    null,
                                                    '\u519C\u4E1A\u94F6\u884C'
                                                )
                                            ),
                                            React.createElement(
                                                'h6',
                                                null,
                                                '\u8D26\u6237:',
                                                React.createElement(
                                                    'b',
                                                    null,
                                                    '4554655655646'
                                                )
                                            )
                                        )
                                    )
                                ),
                                React.createElement(
                                    'li',
                                    null,
                                    React.createElement(
                                        'div',
                                        null,
                                        '\u9886\u53D6\u4FE1\u606F'
                                    ),
                                    React.createElement(
                                        'div',
                                        { className: 'receive-msg' },
                                        React.createElement(
                                            'div',
                                            null,
                                            React.createElement(
                                                'div',
                                                { className: 'receive-scj' },
                                                '\u751F\u5B58\u91D1'
                                            ),
                                            React.createElement(
                                                'div',
                                                { className: 'receive-price' },
                                                React.createElement(
                                                    'div',
                                                    null,
                                                    '\u9886\u53D6\u5E74\u9F84\uFF1A',
                                                    React.createElement(
                                                        'span',
                                                        null,
                                                        '\u5468\u5C81'
                                                    )
                                                ),
                                                React.createElement(
                                                    'div',
                                                    null,
                                                    '\u9886\u53D6\u671F\u9650\uFF1A',
                                                    React.createElement('span', null)
                                                ),
                                                React.createElement(
                                                    'div',
                                                    null,
                                                    '\u9886\u53D6\u65B9\u5F0F\uFF1A',
                                                    React.createElement('span', null)
                                                ),
                                                React.createElement(
                                                    'div',
                                                    null,
                                                    '\u9886\u53D6\u9891\u7387\uFF1A',
                                                    React.createElement('span', null)
                                                )
                                            )
                                        ),
                                        React.createElement(
                                            'div',
                                            null,
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u73B0\u91D1\u7EA2\u5229'
                                            ),
                                            React.createElement(
                                                'div',
                                                null,
                                                '\u9886\u53D6\u65B9\u5F0F:'
                                            )
                                        ),
                                        React.createElement(
                                            'div',
                                            null,
                                            '\u63D0\u793A:\u82E5\u6295\u4FDD\u4EBA\u9669\u79CD\u4E94\u7EA6\u5B9A\u9886\u53D6\uFF0C\u4E0D\u6D89\u53CA\u4E0A\u8FF0\u9886\u53D6\u4FE1\u606F\u3002'
                                        )
                                    )
                                )
                            )
                        )
                    ),
                    React.createElement(
                        'div',
                        { className: 'especially-main' },
                        React.createElement(
                            'div',
                            { className: 'especially-left' },
                            '\u7279\u522B\u7533\u8BF7'
                        ),
                        React.createElement(
                            'ul',
                            { className: 'especially-right' },
                            React.createElement(
                                'li',
                                null,
                                '1\u3001\u672C\u4EBA\u6295\u4FDD\u7684\u9669\u79CD\uFF0C\u6548\u529B\u56E0\u53D1\u751F\u8D23\u4EFB\u514D\u9664\u3001\u89E3\u9664\u7B49\u4E8B\u9879\u7EC8\u6B62\u65F6\uFF0C\u9669\u79CD\uFF0C\u7684\u9644\u52A0\u9669\uFF0C\u5176\u6548\u529B\u4E0D\u53D7\u672C\u4FDD\u9669\u5355\u7684\u5176\u4ED6\u9669\u79CD\u6548\u529B\u7684\u5F71\u54CD\u3002'
                            ),
                            React.createElement(
                                'li',
                                null,
                                React.createElement(
                                    'p',
                                    null,
                                    '\u672C\u4EBA\u7533\u6E05\u8D35\u516C\u53F8\u5728\u672C\u4EBA\u751F\u5B58\u81F3\u5468\u5C81\u4FDD\u5355\u751F\u6548\u5BF9\u5E94\u65E5\u65F6\u5C06\u201C\u2019\u57FA\u672C\u4FDD\u9669\u91D1\u989D'
                                ),
                                React.createElement(
                                    'p',
                                    null,
                                    '\u5728\u8BE5\u4FDD\u5355\u751F\u6548\u5BF9\u5E94\u65E5\uFF0C\u672C\u4FDD\u9669\u5355\u53CA\u6240\u9644\u6279\u5355\u4E0B\u6240\u6709\u4FDD\u9669\u671F\u95F4\u4E3A\u4E00\u5E74\u7684\u9669\u79CD\u7684\u7D2F\u8BA1\u4FDD\u9669\u91D1\u989D\u3002'
                                )
                            )
                        )
                    ),
                    this.state.isElectronics ? React.createElement(
                        'div',
                        null,
                        React.createElement(
                            'div',
                            { className: 'text-center' },
                            '\u7A0E\u6536\u5C45\u6C11\u8EAB\u4EFD'
                        ),
                        React.createElement(
                            'div',
                            { className: 'text-left' },
                            '\u6295\u4FDD\u4EBA: ',
                            React.createElement(
                                'span',
                                null,
                                '\u672C\u8EAB'
                            )
                        ),
                        React.createElement(
                            'ul',
                            { className: 'especially-list' },
                            this.state.especiallyList.map(function (item) {
                                return React.createElement(
                                    'li',
                                    null,
                                    React.createElement(
                                        'div',
                                        null,
                                        item
                                    ),
                                    React.createElement(
                                        'div',
                                        null,
                                        '\u51FA\u751F\u5730',
                                        React.createElement(
                                            'span',
                                            null,
                                            '\u4E1C\u65B9\u8235\u624B'
                                        )
                                    )
                                );
                            })
                        )
                    ) : '',
                    React.createElement(
                        'ul',
                        { className: 'inform-main' },
                        React.createElement(
                            'div',
                            { className: 'text-center' },
                            '\u6295\u4FDD\u544A\u77E5'
                        ),
                        React.createElement(
                            'div',
                            { className: 'text-left' },
                            '\u88AB\u4FDD\u4EBA: ',
                            React.createElement(
                                'span',
                                null,
                                '\u672C\u8EAB'
                            )
                        ),
                        this.state.informList.map(function (item, index) {
                            return React.createElement(
                                'li',
                                null,
                                React.createElement(
                                    'div',
                                    null,
                                    index + 1,
                                    '.',
                                    item.name
                                ),
                                React.createElement(
                                    'div',
                                    null,
                                    '\u7B54\u6848\uFF1A',
                                    React.createElement(
                                        'span',
                                        null,
                                        item.value
                                    )
                                )
                            );
                        })
                    ),
                    React.createElement(
                        'div',
                        { className: 'text-center' },
                        '\u516C\u53F8\u58F0\u660E'
                    ),
                    React.createElement(
                        'ul',
                        { className: 'statement-list' },
                        React.createElement(
                            'li',
                            null,
                            '\u672C\u516C\u53F8\u672C\u7740\u6700\u5927\u8BDA\u4FE1\u7684\u539F\u5219\uFF0C\u5411\u60A8\u660E\u786E\u5404\u4FDD\u9669\u4E8B\u9879\u3002\u4E00\u5207\u4E0E\u4FDD\u9669\u5408\u540C\u76F8\u8FDD\u80CC\u7684\u4EFB\u4F55\u5F62\u5F0F\u7684\u8BF4\u660E\u5747\u5C5E\u65E0\u6548\uFF0C\u4E00\u5207\u6743\u76CA\u5747\u4EE5\u4FDD\u9669\u5408\u540C\u4E3A\u51ED\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'b',
                                null,
                                '\u6295\u4FDD\u987B\u77E5\uFF1A'
                            )
                        ),
                        React.createElement(
                            'li',
                            null,
                            '\u6295\u4FDD\u65F6\uFF0C\u5411\u60A8\u63D0\u4F9B\u4FDD\u9669\u6761\u6B3E\u3001\u8BF4\u660E\u4FDD\u9669\u5408\u540C\u5185\u5BB9\u3001\u63D0\u793A\u5E76\u660E\u786E\u8BF4\u660E\u514D\u9664\u4FDD\u9669\u4EBA\u8D23\u4EFB\u7684\u6761\u6B3E\u662F\u672C\u516C\u53F8\u7684\u6CD5\u5B9A\u4E49\u52A1\uFF0C\u672C\u516C\u53F8\u5C06\u5207\u5B9E\u884C\u3002\u60A8\u53EF\u4EE5\u5BF9\u4E0D\u7406\u89E3\u7684\u4FDD\u9669\u5408\u540C\u5185\u5BB9\u8FDB\u884C\u8BE2\u95EE\u3002\u4E3A\u7EF4\u62A4\u60A8\u7684\u5408\u6CD5\u6743\u76CA\uFF0C\u8BF7\u5728\u9605\u8BFB\u5E76\u7406\u89E3\u4FDD\u9669\u6761\u6B3E\u53CA\u65B0\u578B\u4FDD\u9669\u4EA7\u54C1\u8BF4\u660E\u4E66\u7684\u5404\u9879\u5185\u5BB9\u540E\u9009\u62E9\u9002\u5408\u7684\u4FDD\u9669\u91D1\u989D\u548C\u4FDD\u9669\u671F\u95F4\u65B9\u53EF\u6295\u4FDD\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'b',
                                null,
                                '\u586B\u5199\u987B\u77E5\uFF1A'
                            ),
                            ' \u8BF7\u60A8\u771F\u5B9E\u3001\u51C6\u786E\u3001\u5B8C\u6574\u586B\u5199\u6295\u4FDD\u4E66\u5404\u9879\u5185\u5BB9\uFF0C\u5C24\u5176\u662F\u6295\u88AB\u4FDD\u9669\u4EBA\u7684\u59D3\u540D\u3001\u6027\u522B\u3001\u751F\u65E5\u3001\u8BC1\u4EF6\u7C7B\u578B\u3001\u8BC1\u4EF6\u53F7\u7801\u3001\u804C\u4E1A\u3001\u8054\u7CFB\u7535\u8BDD\u53CA\u5730\u5740\u3001\u6295\u88AB\u4FDD\u9669\u4EBA\u5173\u7CFB\u3001\u94F6\u884C\u8D26\u53F7\u3001\u7A0E\u6536\u5C45\u6C11\u8EAB\u4EFD\u4FE1\u606F\u7B49\uFF0C\u4EE5\u4E0A\u4FE1\u606F\u4E3B\u8981\u7528\u4E8E\u8BA1\u7B97\u5E76\u6536\u53D6\u4FDD\u8D39\u3001\u6838\u4FDD\u3001\u5BC4\u9001\u4FDD\u5355\u3001\u5BA2\u6237\u56DE\u8BBF\u548C\u7A0E\u6536\u5C45\u6C11\u8EAB\u4EFD\u4FE1\u606F\u6536\u96C6\u7B49\uFF0C\u4EE5\u4FBF\u4E3A\u60A8\u63D0\u4F9B\u66F4\u4F18\u8D28\u7684\u670D\u52A1\u3002\u5982\u4FE1\u606F\u7F3A\u5931\u3001\u4E0D\u5B9E\u5C06\u4F1A\u5BF9\u60A8\u7684\u5229\u76CA\u4EA7\u751F\u4E0D\u5229\u5F71\u54CD\uFF0C(nJ\u65F6\u672C\u516C\u53F8\u627F\u8BFA\u672A\u7ECF\u60A8\u7684\u540C\u610F\uFF0C\u4E0D\u4F1A\u5C06\u60A8\u7684\u4FE1\u606F\u7528\u4E8E\u4EBA\u8EAB\u4FDD\u9669\u516C\u53F8\u548C\u7B2C\u4E09\u65B9\u673A\u6784\u7684\u9500\u54A8\u6D3B\u52A8\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'b',
                                null,
                                '\u4EA4\u8D39\u987B\u77E5\uFF1A'
                            ),
                            ' \u6295\u4FDD\u4EBA\u5E94\u6839\u636E\u81EA\u8EAB\u8D22\u52A1\u72B6\u51B5\uFF0C\u786E\u5B9A\u9009\u62E9\u9002\u5408\u7684\u4EA4\u8D39\u671F\u95F4\u548C\u4EA4\u8D39\u91D1\u989D\uFF0C\u5982\u672A\u80FD\u6309\u671F\u8DB3\u989D\u4EA4\u7EB3\u4FDD\u9669\u8D39\uFF0C\u6709\u53EF\u80FD\u5BFC\u81F4\u4FDD\u9669\u5408\u540C\u6548\u529B\u4E2D\u6B62\u6216\u88AB\u89E3\u9664\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'b',
                                null,
                                '\u672A\u6210\u5E74\u4EBA\u6295\u4FDD\uFF1A'
                            ),
                            '\u7236\u6BCD\u4E3A\u5176\u672A\u6210\u5E74\u5B50\u5973\u6295\u4FDD\u7684\u4EBA\u8EAB\u4FDD\u9669\uFF0C\u56E0\u88AB\u4FDD\u9669\u4EBA\u8EAB\u6545\u7ED9\u4ED8\u7684\u4FDD\u9669\u91D1\u603B\u548C\u4E0D\u5F97\u8D85\u8FC7\u56FD\u52A1\u9662\u4FDD\u9669\u76D1\u7C7D\u4EF6\u7406\u673A\u6784\u89C4\u5B9A\u7684\u9650\u989D\uFF0C\u8EAB\u6545\u7ED9\u4ED8\u7684\u4FDD\u9669\u91D1\u989D\u603B\u548C\u7EA6\u5B9A\u4E5F\u4E0D\u5F97\u8D85\u8FC7\u524D\u8FF0\u9650\u989D\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'b',
                                null,
                                '\u5408\u540C\u751F\u6548\uFF1A'
                            ),
                            '\u6295\u4FDD\u540E\uFF0C\u672C\u516C\u53F8\u53EF\u80FD\u4F1A\u901A\u77E5\u4F53\u68C0\u6216\u8865\u5145\u5176\u4ED6\u8D44\u6599\uFF0C\u89C6\u4F53\u68C0\u7ED3\u679C\u548C\u5177\u4F53\u60C5\u51B5\u51B3\u5B9A\u662F\u5426\u627F\u4FDD\uFF0C\u60A8\u5E94\u8BE5\u5C3D\u5FEB\u914D\u5408\uFF0C\u5426\u5219\u5C06\u4F1A\u5F71\u54CD\u6216\u5EF6\u8BEF\u5408\u540C\u751F\u6548\uFF0C\u4F53\u68C0\u4E0D\u5F71\u54CD\u6295\u4FDD\u4EBA\u7684\u5982\u5B9E\u544A\u77E5\u4E49\u52A1\u3002\u672C\u516C\u53F8\u627F\u4FDD\u540E\uFF0C\u4FDD\u9669\u8D23\u4EFB\u5C06\u6309\u7167\u4FDD\u9669\u6761\u6B3E\u7684\u89C4\u5B9A\u751F\u6548\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'b',
                                null,
                                '\u4FDD\u5355\u9001\u8FBE\uFF1A'
                            ),
                            '\u672C\u516C\u53F8\u5C06\u5728\u627F\u4FDD\u540E\u51FA\u5177\u5E76\u9001\u8FBE\u4FDD\u9669\u5408\u540C\uFF0C\u6295\u4FDD\u4EBA\u5E94\u8BA4\u771F\u6838\u5BF9\u5E76\u5728\u4FDD\u9669\u5408\u540C\u7B7E\u6536\u56DE\u6267\u4E0A\u4EB2\u7B14\u7B7E\u5B57\u786E\u8BA4\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'b',
                                null,
                                '\u72B9\u8C6B\u671F\uFF1A'
                            ),
                            '\u9664\u53E6\u6709\u7EA6\u5B9A\u5916\uFF0C\u4FDD\u9669\u671F\u95F4\u5728\u4E00\u5E74\u671F(\u4E0D\u542B\u4E00\u5E74\u671F)\u4EE5\u4E0A\u7684\u5408\u540C\u8BBE\u6709\u72B9\u8C6B\u671F\uFF0C\u5373\u81EA\u6295\u4FDD\u4EBA\u6536\u5230\u4FDD\u9669\u5408\u540C\u5E76\u4E66\u9762\u7B7E\u6536\u4FDD\u5355\u4E4B\u65E5\u8D77\u4E00\u5B9A\u7684\u671F\u95F4\uFF0C\u8BE6\u89C1\u4EBA\u8EAB\u4FDD\u9669\u6295\u4FDD\u63D0\u793A\u4E66\u548C\u4EA7\u54C1\u5229\u76CA\u6761\u6B3E\u3002\u6295\u4FDD\u4EBA\u5E94\u5F53\u5145\u5206\u7406\u89E3\u72B9\u8C6B\u671F\u5C0F\u5B9C\uFF1A\u5728\u72B9\u8C6B\u671F\u5185\u6295\u4FDD\u4EBA\u5DFE\u8BF7\u9000\u4FDD\u7684\uFF0C\u672C\u516C\u53F8\u6536\u5230\u9000\u4FDD\u4E2D\u8BF7\u540E\uFF0C\u4FDD\u9669\u5408\u540C\u7EC8IF.\uFF0C\u5E76\u5728\u6263\u9664\u4E00\u5B9A\u5DE5\u6728\u8D39\u540E\u5C06\u5269\u4F59\u4FDD\u9669\u8D39\u9000\u8FD8\u6295\u4FDD\u4EBA\u3002\u72B9\u8C6B\u671F\u8FC7\u540E\u6295\u4FDD\u4EBA\u4E2D\u8BF7\u9000\u4FDD\u7684\uFF0C\u672C\u516C\u53F8\u6536\u5230\u9000\u4FDD\u4E2D\u8BF7\u540E\uFF0C\u4FDD\u9669\u5408\u540C\u7EC8\u6B62\uFF0C\u5E76\u5C06\u4FDD\u9669\u5408\u540C\u7684\u73B0\u91D1\u4EF7\u503C\u9000\u4FDD\u6295\u4FDD\u4EBA\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'b',
                                null,
                                '\u81EA\u52A8\u7EED\u4FDD\uFF1A'
                            ),
                            '\u5BF9\u4E8E\u4E00\u5E74\u671F\u4E3B\u9669/\u9644\u52A0\u9669\u5305\u542B\u6709\u7EED\u4FDD\u6761\u6B3E\u7684\uFF0C\u5982\u60A8\u540C\u610F\u81EA\u52A8\u7EED\u4FDD\uFF0C\u5408\u540C\u4FDD\u9669\u671F\u95F4\u5C4A\u6EE1\u524D\uFF0C\u672C\u516C\u53F8\u5C06\u901A\u77E5\u60A8\uFF0C\u5982\u60A8\u672A\u5411\u672C\u4E48\u53F8\u8868\u793A\u4E0D\u7EED\u4FDD\uFF0C\u5219\u89C6\u4E3A\u60A8\u7533\u8BF7\u7EED\u4FDD\uFF0C\u672C\u516C\u53F8\u5C06\u5BF9\u88AB\u4FDD\u9669\u4EBA\u505A\u7EED\u4FDD\u5BA1\u6838\u3002\u7ECF\u672C\u516C\u53F8\u5BA1\u6838\u540C\u610F\uFF0C\u4E14\u60A8\u5DF2\u4EA4\u7EB3\u7EED\u4FDD\u4FDD\u9669\u8D39\uFF0C\u5408\u540C\u6548\u529B\u5EF6\u7EED\u4E00\u5E74;\u672C\u516C\u53F8\u5BA1\u6838\u4E0D\u540C\u610F\u7684\uFF0C\u5C06\u4E66\u9762\u901A\u77E5\u60A8\u3002\u5982\u60A8\u4E0D\u540C\u610F\u81EA\u52A8\u7EED\u4FDD\uFF0C\u5408\u540C\u4FDD\u9669\u671F\u95F4\u5C4A\u6EE1\u7684\uFF0C\u5408\u540C\u7EC8\u6B62\uFF0C\u672C\u516C\u53F8\u5C06\u4E0D\u518D\u53E6\u884C\u901A\u77E5\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'b',
                                null,
                                '\u5408\u540C\u6548\u529B\u6062\u590D\uFF1A'
                            ),
                            '\u5408\u540C\u6548\u529B\u4E2D\u6B62\u540E\u4E8C\u5E74\u5185\uFF0C\u60A8\u53EF\u4EE5\u7533\u8BF7\u6062\u590D\u5408\u540C\u6548\u529B\uFF0C\u5728\u6EE1\u8DB3\u5408\u540C\u7EA6\u5B9A\u7684\u6548\u529B\u6062\u590D\u6761\u4EF6\u540E\uFF0C\u5408\u540C\u6548\u529B\u6062\u590D\u3002\u5408\u540C\u6548\u529B\u4E2D\u6B62\u6EE1\u4E8C\u5E74\u53CC\u65B9\u672A\u8FBE\u6210\u590D\u6548\u534F\u8BAE\u7684\uFF0C\u672C\u516C\u53F8\u6709\u6743\u89E3\u9664\u5408\u540C\uFF0C\u5E76\u6839\u636E\u5408\u540C\u7EA6\u5B9A\u9000\u8FD8\u4FDD\u9669\u5408\u540C\u7684\u73B0\u91D1\u4EF7\u503C\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'b',
                                null,
                                '\u7EA2\u5229\u5206\u914D\u65B9\u5F0F:'
                            ),
                            '\u672C\u516C\u53F8\u5206\u7EA2\u4FDD\u9669\u4EA7\u54C1\u91C7\u7528\u589E\u989D\u7EA2\u5229\u6216\u73B0\u91D1\u7EA2\u5229\u7684\u65B9\u5F0F\u8FDB\u884C\u5206\u7EA2\uFF0C\u5176\u4F53\u5206\u914D\u65B9\u5F0F\u8BF7\u53C2\u9605\u6761\u6B3E\u53CA\u4EA7\u54C1\u8BF4\u660E\u4E66\u3002'
                        )
                    ),
                    React.createElement(
                        'div',
                        { className: 'text-center' },
                        '\u5BA2\u6237\u6295\u4FDD\u58F0\u660E'
                    ),
                    React.createElement(
                        'ul',
                        { className: 'statement-list-kh' },
                        React.createElement(
                            'li',
                            null,
                            '1\u3001\u8D35\u516C\u53F8\u5DF2\u5411\u672C\u4EBA\u63D0\u4F9B\u4FDD\u9669\u6761\u6B3E\uFF0C\u8BF4\u660E\u4FDD\u9669\u5408\u540C\u5185\u5BB9\uFF0C\u7279\u522B\u63D0\u793A\u5E76\u660E\u786E\u8BF4\u660E\u4E86\u514D\u9664\u6216\u8005\u51CF\u8F7B\u4FDD\u9669\u4EBA\u8D23\u4EFB\u7684\u6761\u6B3E(\u5305\u62EC\u8D23\u4EFB\u514D\u9664\u6761\u6B3E\u3001\u514D\u8D54\u989D\u3001\u514D\u8D54\u7387\u3001\u6BD4\u4F8B\u8D54\u4ED8\u6216\u7ED9\u4ED8\u7B49)\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            '2\u3001\u672C\u4EBA\u5DF2\u8BA4\u771F\u9605\u8BFB\u5E76\u5145\u5206\u7406\u89E3\u4FDD\u9669\u8D23\u4EFB\u3001\u8D23\u4EFB\u514D\u9664\u3001\u72B9\u8C6B\u671F\u3001\u5408\u540C\u751F\u6548\u3001\u5408\u540C\u89E3\u9664\u3001\u672A\u6210\u5E74\u4EBA\u8EAB\u6545\u4FDD\u9669\u91D1\u9650\u989D\u3001\u4FDD\u9669\u4E8B\u6545\u901A\u77E5\u3001\u4FDD\u9669\u91D1\u53D7\u76CA\u4EBA\u7684\u6307\u5B9A\u4E0E\u53D8\u66F4\u7B49\u4FDD\u9669\u6761\u6B3E\u7684\u5404\u9879\u6982\u5FF5\u3001\u5185\u5BB9\u53CA\u5176\u6CD5\u5F8B\u540E\u679C\uFF0C\u4EE5\u53CA\u6295\u8D44\u8FDE\u63A5\u4FDD\u9669\u3001\u5206\u7EA2\u4FDD\u9669\u3001\u4E07\u80FD\u4FDD\u9669\u7B49\u65B0\u578B\u4EA7\u54C1\u7684\u4EA7\u54C1\u8BF4\u660E\u4E66\uFF0C\u672C\u4EBA\u81EA\u613F\u627F\u62C5\u4FDD\u5355\u5229\u76CA\u4E0D\u786E\u5B9A\u7684\u98CE\u9669\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            '3\u3001\u672C\u4EBA\u53CA\u88AB\u4FDD\u9669\u4EBA\u5728\u6295\u4FDD\u4E66\u4E2D\u7684\u6240\u6709\u9648\u8FF0\u548C\u544A\u77E5\u5747\u5B8C\u6574\u3001\u771F\u5B9E\u3001\u51C6\u786E\uFF0C\u5DF2\u77E5\u6089\u672C\u6295\u4FDD\u4E66\u5982\u975E\u672C\u4EBA\u4EB2\u7B14\u7B7E\u540D\uFF0C\u5C06\u5BF9\u672C\u4FDD\u9669\u5408\u540C\u6548\u529B\u4EA7\u751F\u5F71\u54CD\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            '4\u3001\u672C\u4EBA\u53CA\u88AB\u4FDD\u9669\u4EBA\u6388\u6743\u8D35\u516C\u53F8\u5728\u5FC5\u8981\u65F6\u53EF\u968F\u65F6\u5411\u6709\u5173\u673A\u6784\u6838\u5B9E\u672C\u4EBA\u53CA\u88AB\u4FDD\u9669\u4EBA\u3001\u4FDD\u9669\u91D1\u53D7\u76CA\u4EBA\u7684\u57FA\u672C\u4FE1\u606F\u6216\u5411\u88AB\u4FDD\u9669\u4EBA\u5C31\u8BCA\u7684\u533B\u9662\u6216\u533B\u5E08\u53CA\u793E\u4FDD\u3001\u519C\u5408\u3001\u5065\u5EB7\u7BA1\u7406\u4E2D\u5FC3\u7B49\u6709\u5173\u673A\u6784\u67E5\u8BE2\u6709\u5173\u8BB0\u5F55\u3001\u8BCA\u65AD\u8BC1\u660E\u3002\u672C\u4EBA\u548C\u88AB\u4FDD\u9669\u4EBA\u5BF9\u6B64\u5747\u65E0\u5F02\u8BAE\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            '5\u3001\u672C\u4EBA\u6388\u6743\u8D35\u516C\u53F8\u59D4\u6258\u672C\u4EBA\u5F00\u6237\u94F6\u884C\u5BF9\u6307\u5B9A\u8D26\u6237\u6309\u7167\u4FDD\u9669\u5408\u540C\u7EA6\u5B9A\u7684\u65B9\u5F0F\u3001\u91D1\u989D\uFF0C\u5212\u8F6C\u9996\u671F\u3001\u7EED\u671F\u4FDD\u9669\u8D39\u53CA\u4EE5\u8F6C\u8D26\u65B9\u5F0F\u5C06\u4FDD\u9669\u91D1\u3001\u9000\u4FDD\u91D1\u3001\u9000\u8D39\u7B49\u7ED9\u4ED8\u8F6C\u5165\u6307\u5B9A\u8D26\u6237\uFF0C\u82E5\u672C\u4EBA\u6307\u5B9A\u8D26\u6237\u6216\u8054\u7CFB\u7535\u8BDD\u3001\u8054\u7CFB\u5730\u5740\u3001\u7A0E\u6536\u5C45\u6C11\u8EAB\u4EFD\u7B49\u4FE1\u606F\u53D1\u751F\u53D8\u66F4\uFF0C\u5C06\u572830\u65E5\u5185\u81F3\u8D35\u516C\u53F8\u529E\u7406\u53D8\u66F4\u624B\u7EED\uFF0C\u5982\u672A\u53CA\u65F6\u901A\u77E5\u8D35\u516C\u53F8\u53D8\u66F4\uFF0C\u56E0\u6B64\u4EA7\u751F\u7684\u76F8\u5E94\u4E0D\u5229\u540E\u679C\u7531\u672C\u4EBA\u627F\u62C5\u3002'
                        ),
                        React.createElement(
                            'li',
                            null,
                            '6\u3001\u672C\u4EBA\u5DF2\u77E5\u6089\u672C\u6295\u4FDD\u4E66\u4E0D\u5F97\u4F5C\u4E3A\u6536\u53D6\u73B0\u91D1\u7684\u51ED\u8BC1\uFF0C\u516C\u53F8\u672A\u6388\u6743\u4FDD\u9669\u8425\u9500\u5458\u3001\u4FDD\u9669\u4E2D\u4ECB\u673A\u6784(\u94F6\u884C\u9664\u5916)\u6536\u53D61000\u5143\u4EE5\u4E0A\u7684\u73B0\u91D1\u4FDD\u9669\u8D39\u3002\u516C\u53F8\u5728\u627F\u4FDD\u4E4B\u524D\u6240\u6536\u4FDD\u8D39\u4E3A\u9884\u6536\u4FDD\u8D39\uFF0C\u4E0D\u4F5C\u4E3A\u662F\u5426\u540C\u610F\u627F\u4FDD\u7684\u4F9D\u636E\uFF0C\u5982\u4E0D\u7B26\u5408\u627F\u4FDD\u6761\u4EF6\uFF0C\u5C06\u5982\u6570\u9000\u8FD8\u3002'
                        )
                    ),
                    this.state.isElectronics ? React.createElement(
                        'div',
                        { className: 'copy-mian' },
                        React.createElement(
                            'p',
                            null,
                            '\u6839\u636E\u4FDD\u9669\u4E1A\u76D1\u7BA1\u8981\u6C42\uFF0C\u82E5\u6295\u4FDD\u7684\u9669\u79CD\u6237\u5305\u542B\u5206\u7EA2\u9669\u3001\u4E07\u7816\u4FDD\u9669\u6234\u6295\u8D44\u8FDE\u7ED3\u4FDD\u9669\u7B49\u65B0v\u4EA7\u54C1 ,\u6295\u4FDD\u4EBA\u987B\u6284\u5199\u5982\u4E0B\u5185\u5BB9:'
                        ),
                        React.createElement(
                            'p',
                            null,
                            '\u662F\u7684\u98CE\u683C\u548C\u56DE\u8D2D\u5206'
                        ),
                        React.createElement(
                            'p',
                            null,
                            '\u5907\u6CE8:\u201C\u4FDD\u5355\u5229\u76CA\u7684\u4E0D\u786E\u5B9A\u6027\u201D\u4EC5\u94BE\u5BF9\u6295\u8D44\u8FDE\u7ED3\u4FDD\u9669\u3001\u4E07\u80FD\u4FDD\u9669\u548C\u5206\u7EA2\u4FDD\u9669\u7B49\u65B0\u578B\u4EA7\u54C1\u7684\u975E\u4FDD\u8BC1\u5229\u76CA\u90E8\u5206\uFF0C\u5373\u5206\u7EA2\u4FDD\u9669\u7684\u7EA2\u5229\u5206\u914D\u3001\u4E07\u80FD\u4FDD\u9669\u7ED3\u7B97\u5229\u7387\u8D85\u8FC7\u6700\u4F4E\u4FDD\u8BC1\u5229\u7387\u7684\u90E8\u5206\uFF0C\u4EE5\u53CA\u6295\u8D44\u8FDE\u7ED3\u4FDD\u9669\u7684\u6295\u8D44\u8D26\u6237\u5355\u4F4D\u4EF7\u503C\u7B49\u3002\u4E0D\u5F71\u54CD\u6295\u4FDD\u4EBA\u3001\u88AB\u4FDD\u9669\u4EBA\u548C\u53D7\u76CA\u4EBA\u6309\u7167\u4FDD\u9669\u5408\u540C\u53EF\u4EE5\u4EAB\u6709\u7684\u786E\u5B9A\u5229\u76CA\u3002'
                        )
                    ) : '',
                    React.createElement(
                        'div',
                        { className: 'separate-box' },
                        React.createElement(
                            'div',
                            null,
                            '\u7D27\u6025\u8054\u7CFB\u4EBA'
                        ),
                        React.createElement(
                            'ol',
                            null,
                            React.createElement(
                                'li',
                                null,
                                '\u59D3\u540D\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    '\u963F\u8428\u5FB7'
                                )
                            ),
                            React.createElement(
                                'li',
                                null,
                                '\u624B\u673A\u53F7\u7801\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    '4565444'
                                )
                            ),
                            React.createElement(
                                'li',
                                null,
                                '\u5173\u7CFB\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    '\u591A\u5C11'
                                )
                            )
                        )
                    ),
                    React.createElement(
                        'div',
                        { className: 'separate-box' },
                        React.createElement(
                            'div',
                            null,
                            '\u7535\u8BDD\u56DE\u8BBF'
                        ),
                        React.createElement(
                            'ol',
                            null,
                            React.createElement(
                                'li',
                                null,
                                '\u60A8\u9009\u62E9\u7684\u56DE\u8BBF\u65F6\u95F4\u662F\uFF1A',
                                React.createElement(
                                    'span',
                                    null,
                                    '454545:5451'
                                )
                            )
                        )
                    ),
                    this.state.isElectronics ? React.createElement(
                        'ul',
                        { className: 'sign-box' },
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'span',
                                null,
                                '\u6295\u4FDD\u4EBA\u7B7E\u540D:'
                            ),
                            React.createElement(
                                'span',
                                null,
                                React.createElement('img', { id: 'xss_20', src: 'data:image/gif;base64,R0lGODlhhwBIAJECAL6+vtHR0f///wAAACH5BAEAAAIALAAAAACHAEgAAAL/jI+py+0Po5y02ouz3rz7Dx7CSJbmiabqyrbuC6NLTNf2jddKzvf+r9oBh8TiS2hMKo3IpfOJa0KnVJa0is0Krtruk+sNMxPi8hJsTufQ6jaN7Y634PK6jGzPu+j6PL9f9wcYJzjYVmiYhphYtngCAMkDUDOJEklzCeRokolTCdNZEuoyyrPJaRnz+dL5uSrwahnbcyr6WJoyuwLJywvb28sCPKw7hyc5THmbOxLryqqCu3ecU3xLTBz9TLIqza3rbYzgYw1t3jw5Wo4bLi7S81qeSor6+yhszylvRQ2KDYyJnixYqr6J2sdvnLJdC+dp40YqnkFa/c4xu/YvY7FQ7O1yIZymMCBDkST1fUQ3xNG6kfVsacv4KxizjjFU4nvYcqI+f8I00jSh0ucuZ/d2FnXZwle0GzaH4kQKkSXBgz3zXdRR8Ym6o0tRepXlytrJEovAQTV6dqrTZlFZiWWaVWorqstysk0aday3sSQQSdSplm7afUTTonX4Jq7HnK1mZcsG1ifAlzBtCNra9QZfzZvdGWCUqBZoLKJHUyltGgrq1E5Ws1bi+vWYkLIJKa4tJjZuTbd3d9HtmyLt4I16E68C/Djc4cp/G2/+5Tn0M9KnJ0lu/Uj17ERmcEceIrz48eTLmz+PPr368gUAADs=', onClick: this.testPopupDialog1.bind(this, 20, 0) })
                            )
                        ),
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'span',
                                null,
                                '\u88AB\u4FDD\u9669\u4EBA(\u6CD5\u5B9A\u76D1\u62A4\u4EBA)\u7B7E\u540D:'
                            ),
                            React.createElement(
                                'span',
                                null,
                                React.createElement('img', { id: 'xss_21', src: 'data:image/gif;base64,R0lGODlhhwBIAJECAL6+vtHR0f///wAAACH5BAEAAAIALAAAAACHAEgAAAL/jI+py+0Po5y02ouz3rz7Dx7CSJbmiabqyrbuC6NLTNf2jddKzvf+r9oBh8TiS2hMKo3IpfOJa0KnVJa0is0Krtruk+sNMxPi8hJsTufQ6jaN7Y634PK6jGzPu+j6PL9f9wcYJzjYVmiYhphYtngCAMkDUDOJEklzCeRokolTCdNZEuoyyrPJaRnz+dL5uSrwahnbcyr6WJoyuwLJywvb28sCPKw7hyc5THmbOxLryqqCu3ecU3xLTBz9TLIqza3rbYzgYw1t3jw5Wo4bLi7S81qeSor6+yhszylvRQ2KDYyJnixYqr6J2sdvnLJdC+dp40YqnkFa/c4xu/YvY7FQ7O1yIZymMCBDkST1fUQ3xNG6kfVsacv4KxizjjFU4nvYcqI+f8I00jSh0ucuZ/d2FnXZwle0GzaH4kQKkSXBgz3zXdRR8Ym6o0tRepXlytrJEovAQTV6dqrTZlFZiWWaVWorqstysk0aday3sSQQSdSplm7afUTTonX4Jq7HnK1mZcsG1ifAlzBtCNra9QZfzZvdGWCUqBZoLKJHUyltGgrq1E5Ws1bi+vWYkLIJKa4tJjZuTbd3d9HtmyLt4I16E68C/Djc4cp/G2/+5Tn0M9KnJ0lu/Uj17ERmcEceIrz48eTLmz+PPr368gUAADs=', onClick: this.testPopupDialog1.bind(this, 20, 2) })
                            )
                        ),
                        React.createElement(
                            'li',
                            null,
                            React.createElement(
                                'span',
                                null,
                                '\u6295\u4FDD\u65E5\u671F'
                            ),
                            React.createElement(
                                'span',
                                null,
                                '2018-02-20'
                            )
                        )
                    ) : '',
                    React.createElement(
                        'div',
                        { className: 'separate-box' },
                        React.createElement(
                            'div',
                            null,
                            '\u4FDD\u9669\u516C\u53F8'
                        ),
                        React.createElement(
                            'ol',
                            null,
                            React.createElement(
                                'li',
                                null,
                                '\u4E1A\u52A1\u5458\u59D3\u540D:',
                                React.createElement(
                                    'span',
                                    null,
                                    '\u963F\u8428\u5FB7'
                                )
                            ),
                            React.createElement(
                                'li',
                                null,
                                '\u4E1A\u52A1\u5458\u53F7:',
                                React.createElement(
                                    'span',
                                    null,
                                    '4565444'
                                )
                            ),
                            React.createElement(
                                'li',
                                null,
                                '\u624B\u673A\u53F7\u7801:',
                                React.createElement(
                                    'span',
                                    null,
                                    '52463576'
                                )
                            ),
                            React.createElement(
                                'li',
                                null,
                                '\u6240\u5C5E\u673A\u6784:',
                                React.createElement(
                                    'span',
                                    null,
                                    '132545'
                                )
                            )
                        )
                    ),
                    React.createElement(
                        'ul',
                        { className: 'adress-msg' },
                        React.createElement(
                            'li',
                            null,
                            '\u5168\u56FD\u7EDF\u4E00\u5BA2\u670D\u7535\u8BDD:95567\u7F51\u5740:www.newchinalife.com\u5B98\u65B9\u5FAE\u4FE1\u3001\u79FB\u52A8\u4EBApp\u670D\u52A1\uFF0C\u8BF7\u626B\u63CF\u4E8C\u7EF4\u7801'
                        ),
                        React.createElement(
                            'li',
                            null,
                            '\u4E8C\u7EF4\u7801\u56FE\u7247'
                        )
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

    return Autograph;
}(React.Component);

$(document).ready(function () {
    ReactDOM.render(React.createElement(Autograph, null), document.getElementById("autograph"));
});

/***/ })
/******/ ]);