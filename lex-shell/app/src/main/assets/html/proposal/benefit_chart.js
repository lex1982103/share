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

var BenefitChart = function (_React$Component) {
  _inherits(BenefitChart, _React$Component);

  function BenefitChart() {
    _classCallCheck(this, BenefitChart);

    var _this = _possibleConstructorReturn(this, (BenefitChart.__proto__ || Object.getPrototypeOf(BenefitChart)).call(this));

    _this.state = {
      raw: {
        ml: 80,
        mr: 40,
        mt: 80,
        mb: 130,
        w: 750,
        h: 550,
        m: 5, //坐标尺刻度长短
        bar: 20, //bar宽度
        barm: 50, //bar空白
        font: 24,
        text: 24
      },
      pos: 0,
      chart: {
        age: 0,
        data: []
      }
    };
    return _this;
  }

  _createClass(BenefitChart, [{
    key: 'componentDidMount',
    value: function componentDidMount() {
      var chart = this.props.chart;
      this.state.axis = this.state.raw;
      this.state.ctx = document.getElementById(this.props.id).getContext("2d");
      this.state.ctx.lineCap = 'round';
      this.state.ctx.lineJoin = 'round';

      if (chart) {
        chart.content.data.map(function (v) {
          for (var j = 0; j < v.data.length; j++) {
            v.data[j] = Math.round(v.data[j]);
          }
        });
        this.state.chart = chart.content;
        this.setState({ productName: chart.productName, chart: this.state.chart });
      } else {
        this.state.chart = { age: 0, data: [] };
        this.setState({ productName: "", chart: this.state.chart });
      }
    }
  }, {
    key: 'draw',
    value: function draw(cx) {
      var ax = this.state.axis;
      var ctx = this.state.ctx;
      ctx.clearRect(0, 0, ax.w, ax.h);

      var xy = this.measure(this.state.chart.data);
      var x0 = ax.ml;
      var y0 = ax.h - ax.mb;
      var w = ax.w - ax.mr - ax.ml;
      var h = ax.h - ax.mb - ax.mt;

      //画坐标系标尺
      ctx.lineDash = null;
      ctx.lineWidth = 1;
      ctx.beginPath();
      ctx.moveTo(x0, y0 - h);
      ctx.lineTo(x0, y0);
      ctx.lineTo(x0 + w, y0);
      ctx.font = ax.font + "px Arial";
      ctx.textAlign = 'right';
      ctx.textBaseline = 'middle';
      ctx.fillText("万", x0, y0 - h * 1.1);
      for (var i = 0; i <= 10; i++) {
        ctx.moveTo(x0, y0 - h * i / 10);
        ctx.lineTo(x0 - ax.m, y0 - h * i / 10);

        var v = xy.y * i / 10 / 10000;
        if (v < 10) {
          v = v.toFixed(1);
        } else {
          v = Math.round(v);
        }
        ctx.fillText(v, x0 - ax.m - 1, y0 - h * i / 10);
      }
      ctx.textAlign = 'center';
      ctx.textBaseline = 'top';
      for (var _i = 0; _i < xy.x; _i += Math.ceil(xy.x / 10)) {
        ctx.moveTo(x0 + w * _i / (xy.x - 1), y0);
        ctx.lineTo(x0 + w * _i / (xy.x - 1), y0 + ax.m);
        ctx.fillText(this.state.chart.age + _i, x0 + w * _i / xy.x, y0 + ax.m);
      }
      ctx.strokeStyle = "Black";
      ctx.stroke();

      //画线
      ctx.lineWidth = 3;
      this.state.chart.data.map(function (v1) {
        if (v1.type == "text") return;
        ctx.beginPath();
        for (var _i2 = 0; _i2 < xy.x; _i2++) {
          var _x = x0 + _i2 * w / (xy.x - 1);
          if (_i2 == 0) {
            ctx.moveTo(_x, y0 - v1.data[_i2] * h / xy.y);
          } else {
            ctx.lineTo(_x, y0 - v1.data[_i2] * h / xy.y);
          }
        }
        ctx.strokeStyle = "#" + v1.color;
        ctx.stroke();
      });

      //画顶部的介绍
      ctx.textAlign = 'right';
      ctx.textBaseline = 'middle';
      var x = ax.w - ax.mr;
      this.state.chart.data.map(function (v1) {
        if (v1.type == "text") return;
        ctx.fillStyle = "#" + v1.color;
        ctx.fillText(v1.name, x, y0 - h * 1.1);
        x -= ctx.measureText(v1.name).width + ax.m + ax.bar;
        ctx.fillRect(x, y0 - h * 1.1 - ax.bar / 2, ax.bar, ax.bar);
        x -= ax.m;
      });

      //计算点击的位置
      var pos = -1;
      if (cx) {
        for (var _i3 = 0; _i3 < xy.x; _i3++) {
          var _x2 = x0 + _i3 * w / (xy.x - 1);
          if (Math.abs(cx - _x2) < w / xy.x / 2) {
            pos = _i3;
            break;
          }
        }
        if (cx > x0 + w) pos = xy.x - 1;else if (cx < x0) pos = 0;
      }

      //画节点圈
      ctx.lineWidth = 1;
      this.state.chart.data.map(function (v1) {
        if (v1.type == "text") return;
        for (var _i4 = 5; _i4 < xy.x; _i4 += 5) {
          ctx.beginPath();
          ctx.arc(x0 + _i4 * w / (xy.x - 1), y0 - v1.data[_i4] * h / xy.y, 2, 0, Math.PI * 2);
          ctx.fillStyle = "White";
          ctx.fill();
          ctx.strokeStyle = "#" + v1.color;
          ctx.stroke();
        }
      });

      //画下部的年龄条
      ctx.fillStyle = "LightGray";
      ctx.fillRect(x0, ax.h - ax.barm - ax.bar, w, ax.bar);
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillStyle = "Black";
      ctx.font = ax.text + "px";
      ctx.fillText("年龄", x0 / 2, ax.h - ax.barm - ax.bar / 2);

      //画选择线和年龄条的进度
      if (pos >= 0) {
        var posx = x0 + pos * w / (xy.x - 1);
        ctx.beginPath();
        ctx.lineDash = [4, 4];
        ctx.lineDashOffset = ax.m;
        ctx.moveTo(posx, 0);
        ctx.lineTo(posx, ax.h - ax.barm - ax.bar);
        this.state.chart.data.map(function (v1) {
          if (v1.type == "text") return;
          var y = y0 - v1.data[pos] * h / xy.y;
          ctx.moveTo(posx, y);
          ctx.lineTo(x0, y);
        });
        ctx.strokeStyle = "Gray";
        ctx.stroke();

        ctx.fillStyle = "ForestGreen";
        ctx.fillRect(x0, ax.h - ax.barm - ax.bar, posx - x0, ax.bar);

        ctx.textAlign = 'center';
        ctx.textBaseline = 'top';
        ctx.fillStyle = "Black";
        ctx.fillText(this.state.chart.age + pos + "岁", posx, ax.h - ax.barm);
      }

      if (pos >= 0) this.setState({ pos: pos });
    }
  }, {
    key: 'measure',
    value: function measure(vals) {
      var y = 10;
      var x = 2;
      vals.map(function (v1) {
        if (v1.type == "text") return;
        v1.data.map(function (v2) {
          if (y < v2) y = v2;
        });
        if (x < v1.data.length) x = v1.data.length;
      });
      return { x: x, y: y };
    }
  }, {
    key: 'translate',
    value: function translate(ox) {
      var ww = 750;
      var s = ww / ox.w;
      var r = {};
      for (var i in ox) {
        r[i] = ox[i] * s;
      }return r;
    }
  }, {
    key: 'onTouch',
    value: function onTouch(e) {
      this.draw(e.changedTouches[0].clientX);
    }
  }, {
    key: 'render',
    value: function render() {
      var _this2 = this;

      var pos = this.state.pos;
      return React.createElement(
        'div',
        { className: 'text13 center' },
        React.createElement('canvas', { id: this.props.id, style: { marginLeft: "5px", width: "720px", height: "550px" }, width: '750', height: '550', onTouchStart: this.onTouch.bind(this), onTouchMove: this.onTouch.bind(this) }),
        React.createElement(
          'div',
          { style: { display: "flex", marginLeft: "15px", lineHeight: "50px" } },
          React.createElement(
            'div',
            { style: { width: "110px" } },
            '\u4FDD\u5355\u5E74\u5EA6'
          ),
          this.props.years.map(function (w) {
            return React.createElement(
              'div',
              { style: { width: "120px", color: w == 0 ? '#008800' : '#aaaaaa' } },
              pos + w >= 0 ? "第" + (pos + w + 1) + "年" : ""
            );
          })
        ),
        React.createElement(
          'div',
          { style: { display: "flex", marginLeft: "15px", lineHeight: "50px" } },
          React.createElement(
            'div',
            { style: { width: "110px" } },
            '\u671F\u521D\u5E74\u9F84'
          ),
          this.props.years.map(function (w) {
            return React.createElement(
              'div',
              { style: { width: "120px", color: w == 0 ? '#008800' : '#aaaaaa' } },
              pos + w >= 0 ? _this2.state.chart.age + pos + w + "岁" : ""
            );
          })
        ),
        this.state.chart.data.map(function (v, i) {
          return React.createElement(
            'div',
            { style: { display: "flex", flexDirection: "column", lineHeight: "50px" } },
            React.createElement(
              'div',
              { style: { display: "flex", marginLeft: "15px" } },
              React.createElement(
                'div',
                { style: { width: "110px" } },
                v.name
              ),
              _this2.props.years.map(function (w) {
                return React.createElement(
                  'div',
                  { style: { width: "120px", color: w == 0 ? '#008800' : '#aaaaaa' } },
                  pos + w >= 0 && pos + w < v.data.length ? v.data[pos + w] : ""
                );
              })
            )
          );
        }),
        React.createElement('div', { style: { height: "30px" } })
      );
    }
  }]);

  return BenefitChart;
}(React.Component);

module.exports = BenefitChart;

/***/ })
/******/ ]);