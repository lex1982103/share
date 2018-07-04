<<<<<<< HEAD
!function(t){var e={};function a(n){if(e[n])return e[n].exports;var r=e[n]={i:n,l:!1,exports:{}};return t[n].call(r.exports,r,r.exports,a),r.l=!0,r.exports}a.m=t,a.c=e,a.d=function(t,e,n){a.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:n})},a.r=function(t){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},a.t=function(t,e){if(1&e&&(t=a(t)),8&e)return t;if(4&e&&"object"==typeof t&&t&&t.__esModule)return t;var n=Object.create(null);if(a.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var r in t)a.d(n,r,function(e){return t[e]}.bind(null,r));return n},a.n=function(t){var e=t&&t.__esModule?function(){return t.default}:function(){return t};return a.d(e,"a",e),e},a.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},a.p="",a(a.s=22)}({0:function(t,e,a){"use strict";var n=function(){function t(t,e){for(var a=0;a<e.length;a++){var n=e[a];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(t,n.key,n)}}return function(e,a,n){return a&&t(e.prototype,a),n&&t(e,n),e}}();var r=function(t){function e(){!function(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}(this,e);var t=function(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}(this,(e.__proto__||Object.getPrototypeOf(e)).call(this));return t.state={raw:{ml:80,mr:40,mt:80,mb:130,w:750,h:550,m:5,bar:20,barm:50,font:24,text:24},pos:0,chart:{age:0,data:[]}},t}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(e,React.Component),n(e,[{key:"componentDidMount",value:function(){var t=this.props.chart;this.state.axis=this.state.raw,this.state.ctx=document.getElementById(this.props.id).getContext("2d"),this.state.ctx.lineCap="round",this.state.ctx.lineJoin="round",t?(t.content.data.map(function(t){for(var e=0;e<t.data.length;e++)t.data[e]=Math.round(t.data[e])}),this.state.chart=t.content,this.setState({productName:t.productName,chart:this.state.chart})):(this.state.chart={age:0,data:[]},this.setState({productName:"",chart:this.state.chart}))}},{key:"draw",value:function(t){var e=this.state.axis,a=this.state.ctx;a.clearRect(0,0,e.w,e.h);var n=this.measure(this.state.chart.data),r=e.ml,i=e.h-e.mb,o=e.w-e.mr-e.ml,l=e.h-e.mb-e.mt;a.lineDash=null,a.lineWidth=1,a.beginPath(),a.moveTo(r,i-l),a.lineTo(r,i),a.lineTo(r+o,i),a.font=e.font+"px Arial",a.textAlign="right",a.textBaseline="middle",a.fillText("万",r,i-1.1*l);for(var c=0;c<=10;c++){a.moveTo(r,i-l*c/10),a.lineTo(r-e.m,i-l*c/10);var s=n.y*c/10/1e4;s=s<10?s.toFixed(1):Math.round(s),a.fillText(s,r-e.m-1,i-l*c/10)}a.textAlign="center",a.textBaseline="top";for(var u=0;u<n.x;u+=Math.ceil(n.x/10))a.moveTo(r+o*u/(n.x-1),i),a.lineTo(r+o*u/(n.x-1),i+e.m),a.fillText(this.state.chart.age+u,r+o*u/n.x,i+e.m);a.strokeStyle="Black",a.stroke(),a.lineWidth=3,this.state.chart.data.map(function(t){if("text"!=t.type){a.beginPath();for(var e=0;e<n.x;e++){var c=r+e*o/(n.x-1);0==e?a.moveTo(c,i-t.data[e]*l/n.y):a.lineTo(c,i-t.data[e]*l/n.y)}a.strokeStyle="#"+t.color,a.stroke()}}),a.textAlign="right",a.textBaseline="middle";var f=e.w-e.mr;this.state.chart.data.map(function(t){"text"!=t.type&&(a.fillStyle="#"+t.color,a.fillText(t.name,f,i-1.1*l),f-=a.measureText(t.name).width+e.m+e.bar,a.fillRect(f,i-1.1*l-e.bar/2,e.bar,e.bar),f-=e.m)});var h=-1;if(t){for(var p=0;p<n.x;p++){var d=r+p*o/(n.x-1);if(Math.abs(t-d)<o/n.x/2){h=p;break}}t>r+o?h=n.x-1:t<r&&(h=0)}if(a.lineWidth=1,this.state.chart.data.map(function(t){if("text"!=t.type)for(var e=5;e<n.x;e+=5)a.beginPath(),a.arc(r+e*o/(n.x-1),i-t.data[e]*l/n.y,2,0,2*Math.PI),a.fillStyle="White",a.fill(),a.strokeStyle="#"+t.color,a.stroke()}),a.fillStyle="LightGray",a.fillRect(r,e.h-e.barm-e.bar,o,e.bar),a.textAlign="center",a.textBaseline="middle",a.fillStyle="Black",a.font=e.text+"px",a.fillText("年龄",r/2,e.h-e.barm-e.bar/2),h>=0){var m=r+h*o/(n.x-1);a.beginPath(),a.lineDash=[4,4],a.lineDashOffset=e.m,a.moveTo(m,0),a.lineTo(m,e.h-e.barm-e.bar),this.state.chart.data.map(function(t){if("text"!=t.type){var e=i-t.data[h]*l/n.y;a.moveTo(m,e),a.lineTo(r,e)}}),a.strokeStyle="Gray",a.stroke(),a.fillStyle="ForestGreen",a.fillRect(r,e.h-e.barm-e.bar,m-r,e.bar),a.textAlign="center",a.textBaseline="top",a.fillStyle="Black",a.fillText(this.state.chart.age+h+"岁",m,e.h-e.barm)}h>=0&&this.setState({pos:h})}},{key:"measure",value:function(t){var e=10,a=2;return t.map(function(t){"text"!=t.type&&(t.data.map(function(t){e<t&&(e=t)}),a<t.data.length&&(a=t.data.length))}),{x:a,y:e}}},{key:"translate",value:function(t){var e=750/t.w,a={};for(var n in t)a[n]=t[n]*e;return a}},{key:"onTouch",value:function(t){this.draw(t.changedTouches[0].clientX)}},{key:"render",value:function(){var t=this,e=this.state.pos;return React.createElement("div",{className:"text13 center"},React.createElement("canvas",{id:this.props.id,style:{marginLeft:"5px",width:"720px",height:"550px"},width:"750",height:"550",onTouchStart:this.onTouch.bind(this),onTouchMove:this.onTouch.bind(this)}),React.createElement("div",{style:{display:"flex",marginLeft:"15px",lineHeight:"50px"}},React.createElement("div",{style:{width:"110px"}},"保单年度"),this.props.years.map(function(t){return React.createElement("div",{style:{width:"120px",color:0==t?"#008800":"#aaaaaa"}},e+t>=0?"第"+(e+t+1)+"年":"")})),React.createElement("div",{style:{display:"flex",marginLeft:"15px",lineHeight:"50px"}},React.createElement("div",{style:{width:"110px"}},"期初年龄"),this.props.years.map(function(a){return React.createElement("div",{style:{width:"120px",color:0==a?"#008800":"#aaaaaa"}},e+a>=0?t.state.chart.age+e+a+"岁":"")})),this.state.chart.data.map(function(a,n){return React.createElement("div",{style:{display:"flex",flexDirection:"column",lineHeight:"50px"}},React.createElement("div",{style:{display:"flex",marginLeft:"15px"}},React.createElement("div",{style:{width:"110px"}},a.name),t.props.years.map(function(t){return React.createElement("div",{style:{width:"120px",color:0==t?"#008800":"#aaaaaa"}},e+t>=0&&e+t<a.data.length?a.data[e+t]:"")})))}),React.createElement("div",{style:{height:"30px"}}))}}]),e}();t.exports=r},22:function(t,e,a){"use strict";var n,r=function(){function t(t,e){for(var a=0;a<e.length;a++){var n=e[a];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(t,n.key,n)}}return function(e,a,n){return a&&t(e.prototype,a),n&&t(e,n),e}}(),i=a(0),o=(n=i)&&n.__esModule?n:{default:n};var l=function(t){function e(){!function(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}(this,e);var t=function(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}(this,(e.__proto__||Object.getPrototypeOf(e)).call(this));return t.state={planId:common.param("planId"),mode:0,tabs:["利益图表","责任条款"],coverage:[],chart:[]},t}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(e,React.Component),r(e,[{key:"componentDidMount",value:function(){var t=this;MF.setTitle("利益演示"),APP.proposal.format(this.state.planId,"coverage,benefit_chart",function(e){t.setState({coverage:e.coverage,chart:e.benefit_chart},t.onRepaint)})}},{key:"onModeSwitch",value:function(t){this.setState({mode:t},this.onRepaint)}},{key:"onRepaint",value:function(){if(0==this.state.mode)for(var t=0;t<this.state.chart.length;t++){var e=this.refs["benefitChart"+t];e&&e.draw(400)}}},{key:"render",value:function(){var t=this;return React.createElement("div",null,React.createElement("div",{style:{display:"flex",width:"750px",position:"fixed",zIndex:"50",top:"0",backgroundColor:"#e6e6e6"}},this.state.tabs.map(function(e,a){return React.createElement("div",{className:"tab "+(a==t.state.mode?"tab-focus":"tab-blur"),key:a,style:{width:"250px"},onClick:t.onModeSwitch.bind(t,a)},React.createElement("text",{className:"text18"},e))})),1==this.state.mode?React.createElement("div",{style:{display:"flex",flexDirection:"column",marginTop:"80px"}},this.state.coverage.map(function(t,e){return React.createElement("div",{className:"pl-2 pr-2 bg-white"},React.createElement("div",{style:{marginTop:(0!=e?10:0)+"px"}},React.createElement("div",{className:"text17 eclipse",style:{width:"690px",textAlign:"center",padding:"10px",lineHeight:"60px",borderBottom:"#ddd solid 1px"}},t.productName)),t.content.map(function(t,e){return React.createElement("div",{style:{background:"#fff"}},React.createElement("div",null,React.createElement("text",{className:"text17",style:{margin:"10px",lineHeight:"60px"}},"● ",t.title)),t.content.map(function(t,e){return React.createElement("div",{className:"text16"},"　　",t.text)}),React.createElement("div",{style:{height:"10px"}}))}),React.createElement("div",{style:{height:"10px"}}))})):0==this.state.mode?React.createElement("div",{style:{display:"flex",flexDirection:"column",marginTop:"80px"}},this.state.chart.map(function(t,e){return null==t.content?null:React.createElement("div",{className:"pl-1 bg-white"},React.createElement("div",{class:"eclipse text18",style:{width:"660px",textAlign:"center",marginLeft:"35px",height:"80px",lineHeight:"80px",borderBottom:"#ddd solid 1px"}},t.productName),React.createElement(o.default,{ref:"benefitChart"+e,id:"benefitChart"+e,chart:t,years:[-2,-1,0,1,2]}),React.createElement("div",{style:{height:"10px",backgroundColor:"#e6e6e6"}}))})):null)}}]),e}();$(document).ready(function(){ReactDOM.render(React.createElement(l,null),document.getElementById("root"))})}});
=======
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

/***/ }),
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _benefit_chart = __webpack_require__(0);

var _benefit_chart2 = _interopRequireDefault(_benefit_chart);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Main = function (_React$Component) {
  _inherits(Main, _React$Component);

  function Main() {
    _classCallCheck(this, Main);

    var _this = _possibleConstructorReturn(this, (Main.__proto__ || Object.getPrototypeOf(Main)).call(this));

    _this.state = {
      planId: common.param("planId"),
      mode: 0,
      tabs: ["利益图表", "责任条款"],
      coverage: [],
      chart: []
    };
    return _this;
  }

  _createClass(Main, [{
    key: "componentDidMount",
    value: function componentDidMount() {
      var _this2 = this;

      window.MF && MF.setTitle("利益演示");
      APP.proposal.format(this.state.planId, "coverage,benefit_chart", function (r) {
        _this2.setState({ coverage: r.coverage, chart: r.benefit_chart }, _this2.onRepaint);
      });
    }
  }, {
    key: "onModeSwitch",
    value: function onModeSwitch(i) {
      this.setState({ mode: i }, this.onRepaint);
    }
  }, {
    key: "onRepaint",
    value: function onRepaint() {
      if (this.state.mode == 0) {
        for (var i = 0; i < this.state.chart.length; i++) {
          var chart = this.refs["benefitChart" + i];
          if (chart) chart.draw(400);
        }
      }
    }
  }, {
    key: "render",
    value: function render() {
      var _this3 = this;

      return React.createElement(
        "div",
        null,
        React.createElement(
          "div",
          { style: { display: "flex", width: "750px", position: "fixed", zIndex: "50", top: "0", backgroundColor: "#e6e6e6" } },
          this.state.tabs.map(function (v, i) {
            return React.createElement(
              "div",
              { className: "tab " + (i == _this3.state.mode ? 'tab-focus' : 'tab-blur'), key: i, style: { width: "250px" }, onClick: _this3.onModeSwitch.bind(_this3, i) },
              React.createElement(
                "text",
                { className: "text18" },
                v
              )
            );
          })
        ),
        this.state.mode == 1 ? React.createElement(
          "div",
          { style: { display: "flex", flexDirection: "column", marginTop: "80px" } },
          this.state.coverage.map(function (v, i) {
            return React.createElement(
              "div",
              { className: "pl-2 pr-2 bg-white" },
              React.createElement(
                "div",
                { style: { marginTop: (i != 0 ? 10 : 0) + "px" } },
                React.createElement(
                  "div",
                  { className: "text17 eclipse", style: { width: "690px", textAlign: "center", padding: "10px", lineHeight: "60px", borderBottom: "#ddd solid 1px" } },
                  v.productName
                )
              ),
              v.content.map(function (x, j) {
                return React.createElement(
                  "div",
                  { style: { background: "#fff" } },
                  React.createElement(
                    "div",
                    null,
                    React.createElement(
                      "text",
                      { className: "text17", style: { margin: "10px", lineHeight: "60px" } },
                      "\u25CF ",
                      x.title
                    )
                  ),
                  x.content.map(function (y, k) {
                    return React.createElement(
                      "div",
                      { className: "text16" },
                      "\u3000\u3000",
                      y.text
                    );
                  }),
                  React.createElement("div", { style: { height: "10px" } })
                );
              }),
              React.createElement("div", { style: { height: "10px" } })
            );
          })
        ) : this.state.mode == 0 ? React.createElement(
          "div",
          { style: { display: "flex", flexDirection: "column", marginTop: "80px" } },
          this.state.chart.map(function (v, i) {
            return v.content == null ? null : React.createElement(
              "div",
              { className: "pl-1 bg-white" },
              React.createElement(
                "div",
                { "class": "eclipse text18", style: { width: "660px", textAlign: "center", marginLeft: "35px", height: "80px", lineHeight: "80px", borderBottom: "#ddd solid 1px" } },
                v.productName
              ),
              React.createElement(_benefit_chart2.default, { ref: "benefitChart" + i, id: "benefitChart" + i, chart: v, years: [-2, -1, 0, 1, 2] }),
              React.createElement("div", { style: { height: "10px", backgroundColor: "#e6e6e6" } })
            );
          })
        ) : null
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
>>>>>>> master
