<<<<<<< HEAD
!function(e){var t={};function a(r){if(t[r])return t[r].exports;var n=t[r]={i:r,l:!1,exports:{}};return e[r].call(n.exports,n,n.exports,a),n.l=!0,n.exports}a.m=e,a.c=t,a.d=function(e,t,r){a.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:r})},a.r=function(e){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},a.t=function(e,t){if(1&t&&(e=a(e)),8&t)return e;if(4&t&&"object"==typeof e&&e&&e.__esModule)return e;var r=Object.create(null);if(a.r(r),Object.defineProperty(r,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var n in e)a.d(r,n,function(t){return e[t]}.bind(null,n));return r},a.n=function(e){var t=e&&e.__esModule?function(){return e.default}:function(){return e};return a.d(t,"a",t),t},a.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},a.p="",a(a.s=21)}({0:function(e,t,a){"use strict";var r=function(){function e(e,t){for(var a=0;a<t.length;a++){var r=t[a];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,a,r){return a&&e(t.prototype,a),r&&e(t,r),t}}();var n=function(e){function t(){!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t);var e=function(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}(this,(t.__proto__||Object.getPrototypeOf(t)).call(this));return e.state={raw:{ml:80,mr:40,mt:80,mb:130,w:750,h:550,m:5,bar:20,barm:50,font:24,text:24},pos:0,chart:{age:0,data:[]}},e}return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}(t,React.Component),r(t,[{key:"componentDidMount",value:function(){var e=this.props.chart;this.state.axis=this.state.raw,this.state.ctx=document.getElementById(this.props.id).getContext("2d"),this.state.ctx.lineCap="round",this.state.ctx.lineJoin="round",e?(e.content.data.map(function(e){for(var t=0;t<e.data.length;t++)e.data[t]=Math.round(e.data[t])}),this.state.chart=e.content,this.setState({productName:e.productName,chart:this.state.chart})):(this.state.chart={age:0,data:[]},this.setState({productName:"",chart:this.state.chart}))}},{key:"draw",value:function(e){var t=this.state.axis,a=this.state.ctx;a.clearRect(0,0,t.w,t.h);var r=this.measure(this.state.chart.data),n=t.ml,i=t.h-t.mb,l=t.w-t.mr-t.ml,c=t.h-t.mb-t.mt;a.lineDash=null,a.lineWidth=1,a.beginPath(),a.moveTo(n,i-c),a.lineTo(n,i),a.lineTo(n+l,i),a.font=t.font+"px Arial",a.textAlign="right",a.textBaseline="middle",a.fillText("万",n,i-1.1*c);for(var s=0;s<=10;s++){a.moveTo(n,i-c*s/10),a.lineTo(n-t.m,i-c*s/10);var o=r.y*s/10/1e4;o=o<10?o.toFixed(1):Math.round(o),a.fillText(o,n-t.m-1,i-c*s/10)}a.textAlign="center",a.textBaseline="top";for(var m=0;m<r.x;m+=Math.ceil(r.x/10))a.moveTo(n+l*m/(r.x-1),i),a.lineTo(n+l*m/(r.x-1),i+t.m),a.fillText(this.state.chart.age+m,n+l*m/r.x,i+t.m);a.strokeStyle="Black",a.stroke(),a.lineWidth=3,this.state.chart.data.map(function(e){if("text"!=e.type){a.beginPath();for(var t=0;t<r.x;t++){var s=n+t*l/(r.x-1);0==t?a.moveTo(s,i-e.data[t]*c/r.y):a.lineTo(s,i-e.data[t]*c/r.y)}a.strokeStyle="#"+e.color,a.stroke()}}),a.textAlign="right",a.textBaseline="middle";var d=t.w-t.mr;this.state.chart.data.map(function(e){"text"!=e.type&&(a.fillStyle="#"+e.color,a.fillText(e.name,d,i-1.1*c),d-=a.measureText(e.name).width+t.m+t.bar,a.fillRect(d,i-1.1*c-t.bar/2,t.bar,t.bar),d-=t.m)});var p=-1;if(e){for(var u=0;u<r.x;u++){var h=n+u*l/(r.x-1);if(Math.abs(e-h)<l/r.x/2){p=u;break}}e>n+l?p=r.x-1:e<n&&(p=0)}if(a.lineWidth=1,this.state.chart.data.map(function(e){if("text"!=e.type)for(var t=5;t<r.x;t+=5)a.beginPath(),a.arc(n+t*l/(r.x-1),i-e.data[t]*c/r.y,2,0,2*Math.PI),a.fillStyle="White",a.fill(),a.strokeStyle="#"+e.color,a.stroke()}),a.fillStyle="LightGray",a.fillRect(n,t.h-t.barm-t.bar,l,t.bar),a.textAlign="center",a.textBaseline="middle",a.fillStyle="Black",a.font=t.text+"px",a.fillText("年龄",n/2,t.h-t.barm-t.bar/2),p>=0){var f=n+p*l/(r.x-1);a.beginPath(),a.lineDash=[4,4],a.lineDashOffset=t.m,a.moveTo(f,0),a.lineTo(f,t.h-t.barm-t.bar),this.state.chart.data.map(function(e){if("text"!=e.type){var t=i-e.data[p]*c/r.y;a.moveTo(f,t),a.lineTo(n,t)}}),a.strokeStyle="Gray",a.stroke(),a.fillStyle="ForestGreen",a.fillRect(n,t.h-t.barm-t.bar,f-n,t.bar),a.textAlign="center",a.textBaseline="top",a.fillStyle="Black",a.fillText(this.state.chart.age+p+"岁",f,t.h-t.barm)}p>=0&&this.setState({pos:p})}},{key:"measure",value:function(e){var t=10,a=2;return e.map(function(e){"text"!=e.type&&(e.data.map(function(e){t<e&&(t=e)}),a<e.data.length&&(a=e.data.length))}),{x:a,y:t}}},{key:"translate",value:function(e){var t=750/e.w,a={};for(var r in e)a[r]=e[r]*t;return a}},{key:"onTouch",value:function(e){this.draw(e.changedTouches[0].clientX)}},{key:"render",value:function(){var e=this,t=this.state.pos;return React.createElement("div",{className:"text13 center"},React.createElement("canvas",{id:this.props.id,style:{marginLeft:"5px",width:"720px",height:"550px"},width:"750",height:"550",onTouchStart:this.onTouch.bind(this),onTouchMove:this.onTouch.bind(this)}),React.createElement("div",{style:{display:"flex",marginLeft:"15px",lineHeight:"50px"}},React.createElement("div",{style:{width:"110px"}},"保单年度"),this.props.years.map(function(e){return React.createElement("div",{style:{width:"120px",color:0==e?"#008800":"#aaaaaa"}},t+e>=0?"第"+(t+e+1)+"年":"")})),React.createElement("div",{style:{display:"flex",marginLeft:"15px",lineHeight:"50px"}},React.createElement("div",{style:{width:"110px"}},"期初年龄"),this.props.years.map(function(a){return React.createElement("div",{style:{width:"120px",color:0==a?"#008800":"#aaaaaa"}},t+a>=0?e.state.chart.age+t+a+"岁":"")})),this.state.chart.data.map(function(a,r){return React.createElement("div",{style:{display:"flex",flexDirection:"column",lineHeight:"50px"}},React.createElement("div",{style:{display:"flex",marginLeft:"15px"}},React.createElement("div",{style:{width:"110px"}},a.name),e.props.years.map(function(e){return React.createElement("div",{style:{width:"120px",color:0==e?"#008800":"#aaaaaa"}},t+e>=0&&t+e<a.data.length?a.data[t+e]:"")})))}),React.createElement("div",{style:{height:"30px"}}))}}]),t}();e.exports=n},21:function(e,t,a){"use strict";var r,n=function(){function e(e,t){for(var a=0;a<t.length;a++){var r=t[a];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,a,r){return a&&e(t.prototype,a),r&&e(t,r),t}}(),i=a(0),l=(r=i)&&r.__esModule?r:{default:r};var c=function(e){function t(){!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t);var e=function(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}(this,(t.__proto__||Object.getPrototypeOf(t)).call(this));return e.state={proposalId:common.param("proposalId"),index:0},e}return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}(t,React.Component),n(t,[{key:"componentDidMount",value:function(){var e=this;MF.setTitle("建议书预览"),APP.proposal.view(this.state.proposalId,function(t){e.setState({proposal:t},e.onInsurantSwitch.bind(e,0))})}},{key:"onInsurantSwitch",value:function(e){var t=this;APP.proposal.viewPlan(this.state.proposal.detail[e],function(a){t.setState({index:e,plan:a})}),APP.proposal.format(this.state.proposal.detail[e],"liab_graph,coverage,benefit_chart",function(e){t.setState({coverage:e.coverage,chart:e.benefit_chart,liability:e.liab_graph})})}},{key:"showLiabDetail",value:function(e,t,a){this.state.liability[e].detail[t].detail[a].show=!this.state.liability[e].detail[t].detail[a].show,this.setState({liability:this.state.liability})}},{key:"render",value:function(){var e=this;if(!this.state.proposal||!this.state.plan||!this.state.proposal.other)return null;var t=this.state.plan;return React.createElement("div",null,React.createElement("div",null,React.createElement("div",null,React.createElement("img",{src:"https://lifeins.iyunbao.com/static/iyb/images/bobcardif/iyb10004banner.jpg",style:{width:"750px",height:"360px"}})),React.createElement("div",{className:"divx"},React.createElement("div",{className:"divx pl-1 pr-1 pt-2 pb-2 tc-dark",style:{width:"250px",backgroundImage:"url(../images/arrow.png)",backgroundSize:"32px 16px",backgroundRepeat:"no-repeat",backgroundPosition:"center bottom"}},React.createElement("div",null,React.createElement("img",{src:"M"==t.insurant.gender?"../images/male.png":"../images/female.png",style:{width:"100px",height:"100px"}})),React.createElement("div",{className:"pt-1 pb-1"},React.createElement("div",{className:"lh-50 text16"},t.insurant.name?t.insurant.name:"被保险人"),React.createElement("div",{className:"lh-30 text13"},"M"==t.insurant.gender?"男":"女"," ",t.insurant.age,"岁"))))),React.createElement("div",{className:"bg-white"},React.createElement("div",{className:"divx pt-3 pl-2 pr-2 pb-2"},React.createElement("div",{className:"bg-primary",style:{width:"20px",height:"40px"}}),React.createElement("div",{className:"ml-1 lh-40"},"保障计划"),t?React.createElement("div",{className:"ml-auto lh-40"},"首年保费：",React.createElement("span",{className:"tc-red"},t.premium),"元"):null),React.createElement("div",{className:"ml-1 mr-1"},t?t.product.map(function(e,a){return null!=e.parent?null:React.createElement("div",{className:"bg-white"+(0==a?"":" mt-2"),style:{border:"#dddddd 1px solid"}},t.product.map(function(r,n){return r==e?React.createElement("div",{className:"product product-main text16"},React.createElement("div",{style:{height:"70px",display:"flex"}},React.createElement("img",{style:{width:"60px",height:"60px",margin:"10px 10px 0 10px"},src:t.icons[r.vendor]}),React.createElement("div",{style:{width:"600px",marginTop:"10px"}},React.createElement("span",{className:"text20 eclipse"},r.name))),React.createElement("div",{style:{height:"60px",display:"flex"}},React.createElement("div",{className:"left"}),React.createElement("div",{className:"middle eclipse"},React.createElement("span",null,r.purchase," / ",r.insure," / ",r.pay)),React.createElement("div",{className:"right"},React.createElement("span",{style:{color:"#000"}},r.premium,"元"))),React.createElement("div",{style:{height:"10px"}})):r.parent==a?React.createElement("div",{className:"product product-rider text16 br-tl"},React.createElement("div",{className:"left"},React.createElement("span",{style:{color:"#0a0"}},"附")),React.createElement("div",{className:"middle eclipse"},React.createElement("span",{style:{color:"#000",marginRight:"10px"}},r.abbrName),React.createElement("span",{style:{color:"#aaa"}})),React.createElement("div",{className:"right"},React.createElement("span",{style:{color:"#000"}},r.premium,"元"))):null}))}):null),React.createElement("div",{className:"divx mt-1 p-2"},React.createElement("div",{className:"bg-primary",style:{width:"20px",height:"40px"}}),React.createElement("div",{className:"ml-1 lh-40"},"保险责任")),React.createElement("div",{className:"ml-1 mr-1 pt-2 pb-2 bg-white",style:{border:"#dddddd 1px solid"}},this.state.liability?this.state.liability.map(function(t,a){return t.detail?React.createElement("div",{key:a},React.createElement("div",{className:"text17 lh-60 center pl-3 pr-3 ml-3 mr-3 mt-1 mb-2 tc-primary bg-white",style:{border:"#00aff9 1px solid",borderRadius:"30px"}},t.name),t.detail.map(function(t,r){return React.createElement("div",{className:"div text17 pl-1 pr-1 pb-1",key:r},React.createElement("div",{className:"lh-60 pl-1 tc-primary"},"★ ",t.name),t.detail.map(function(t,n){return React.createElement("div",{className:"lh-40",key:n},React.createElement("div",{className:"divx pb-1",onClick:e.showLiabDetail.bind(e,a,r,n)},React.createElement("img",{style:{width:"40px",height:"40px"},src:t.show?"../images/arrow-7-up.png":"../images/arrow-7-down.png"}),React.createElement("div",{className:"pl-1",style:{width:"650px"}},t.text)),t.detail.map(function(e,a){return t.show&&t.detail?React.createElement("div",{className:"divx text15 lh-30 tc-dark pb-1",key:a},React.createElement("div",{className:"center",style:{width:"40px"}}),React.createElement("div",{className:"divx"},React.createElement("div",{className:"title"},e.productAbbrName)," ",React.createElement("div",null,e.text))):null}))}))})):null}):null),React.createElement("div",{className:"divx mt-1 p-2"},React.createElement("div",{className:"bg-primary",style:{width:"20px",height:"40px"}}),React.createElement("div",{className:"ml-1 lh-40"},"利益图表")),React.createElement("div",{className:"ml-1 mr-1 pt-2 pb-2 bg-white",style:{border:"#dddddd 1px solid"}},this.state.chart?this.state.chart.map(function(e,t){return e.content?React.createElement("div",null,React.createElement("div",{className:"text17 lh-60 center pl-3 pr-3 ml-3 mr-3 mt-1 mb-2 tc-primary bg-white",style:{border:"#00aff9 1px solid",borderRadius:"30px"}},e.productName),React.createElement("div",{className:"divx"},React.createElement(l.default,{ref:"benefitChart"+t,id:"benefitChart"+t,chart:e,years:[-2,-1,0,1,2]}))):null}):null),React.createElement("div",{className:"divx mt-1 p-2"},React.createElement("div",{className:"bg-primary",style:{width:"20px",height:"40px"}}),React.createElement("div",{className:"ml-1 lh-40"},"温馨提示")),React.createElement("div",{className:"ml-1 mr-1 p-1 bg-white tc-dark text15",style:{border:"#dddddd 1px solid"}},"　　免除保险公司责任条款、犹豫期、解除合同的手续及风险、费用扣除等内容，请您仔细阅读保险合同。本演示说明仅供参考，具体保险责任、免除保险公司责任的条款等内容以正式保险合同为准。"),React.createElement("div",{className:"h-60"})))}}]),t}();$(document).ready(function(){ReactDOM.render(React.createElement(c,null),document.getElementById("root"))})}});
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
/******/ 	return __webpack_require__(__webpack_require__.s = 3);
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
/* 1 */,
/* 2 */,
/* 3 */
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
            proposalId: common.param("proposalId"),
            index: 0
        };
        return _this;
    }

    _createClass(Main, [{
        key: "componentDidMount",
        value: function componentDidMount() {
            var _this2 = this;

            window.MF && MF.setTitle("建议书预览");
            APP.proposal.view(this.state.proposalId, function (r) {
                _this2.setState({ proposal: r }, _this2.onInsurantSwitch.bind(_this2, 0));
            });
        }
    }, {
        key: "onInsurantSwitch",
        value: function onInsurantSwitch(index) {
            var _this3 = this;

            APP.proposal.viewPlan(this.state.proposal.detail[index], function (r) {
                _this3.setState({ index: index, plan: r });
            });
            APP.proposal.format(this.state.proposal.detail[index], "liab_graph,coverage,benefit_chart", function (r) {
                _this3.setState({ coverage: r.coverage, chart: r.benefit_chart, liability: r.liab_graph });
            });
        }
    }, {
        key: "showLiabDetail",
        value: function showLiabDetail(i, j, k) {
            this.state.liability[i].detail[j].detail[k].show = !this.state.liability[i].detail[j].detail[k].show;
            this.setState({ liability: this.state.liability });
        }
    }, {
        key: "render",
        value: function render() {
            var _this4 = this;

            if (!this.state.proposal || !this.state.plan || !this.state.proposal.other) return null;
            var plan = this.state.plan;
            return React.createElement(
                "div",
                null,
                React.createElement(
                    "div",
                    null,
                    React.createElement(
                        "div",
                        null,
                        React.createElement("img", { src: "https://lifeins.iyunbao.com/static/iyb/images/bobcardif/iyb10004banner.jpg", style: { width: "750px", height: "360px" } })
                    ),
                    React.createElement(
                        "div",
                        { className: "divx" },
                        React.createElement(
                            "div",
                            { className: "divx pl-1 pr-1 pt-2 pb-2 tc-dark", style: { width: "250px", backgroundImage: "url(../images/arrow.png)", backgroundSize: "32px 16px", backgroundRepeat: "no-repeat", backgroundPosition: "center bottom" } },
                            React.createElement(
                                "div",
                                null,
                                React.createElement("img", { src: plan.insurant.gender == "M" ? "../images/male.png" : "../images/female.png", style: { width: "100px", height: "100px" } })
                            ),
                            React.createElement(
                                "div",
                                { className: "pt-1 pb-1" },
                                React.createElement(
                                    "div",
                                    { className: "lh-50 text16" },
                                    plan.insurant.name ? plan.insurant.name : "被保险人"
                                ),
                                React.createElement(
                                    "div",
                                    { className: "lh-30 text13" },
                                    plan.insurant.gender == "M" ? "男" : "女",
                                    " ",
                                    plan.insurant.age,
                                    "\u5C81"
                                )
                            )
                        )
                    )
                ),
                React.createElement(
                    "div",
                    { className: "bg-white" },
                    React.createElement(
                        "div",
                        { className: "divx pt-3 pl-2 pr-2 pb-2" },
                        React.createElement("div", { className: "bg-primary", style: { width: "20px", height: "40px" } }),
                        React.createElement(
                            "div",
                            { className: "ml-1 lh-40" },
                            "\u4FDD\u969C\u8BA1\u5212"
                        ),
                        plan ? React.createElement(
                            "div",
                            { className: "ml-auto lh-40" },
                            "\u9996\u5E74\u4FDD\u8D39\uFF1A",
                            React.createElement(
                                "span",
                                { className: "tc-red" },
                                plan.premium
                            ),
                            "\u5143"
                        ) : null
                    ),
                    React.createElement(
                        "div",
                        { className: "ml-1 mr-1" },
                        !plan ? null : plan.product.map(function (w, j) {
                            return w.parent != null ? null : React.createElement(
                                "div",
                                { className: "bg-white" + (j == 0 ? "" : " mt-2"), style: { border: "#dddddd 1px solid" } },
                                plan.product.map(function (v, i) {
                                    return v == w ? React.createElement(
                                        "div",
                                        { className: "product product-main text16" },
                                        React.createElement(
                                            "div",
                                            { style: { height: "70px", display: "flex" } },
                                            React.createElement("img", { style: { width: "60px", height: "60px", margin: "10px 10px 0 10px" }, src: plan.icons[v.vendor] }),
                                            React.createElement(
                                                "div",
                                                { style: { width: "600px", marginTop: "10px" } },
                                                React.createElement(
                                                    "span",
                                                    { className: "text20 eclipse" },
                                                    v.name
                                                )
                                            )
                                        ),
                                        React.createElement(
                                            "div",
                                            { style: { height: "60px", display: "flex" } },
                                            React.createElement("div", { className: "left" }),
                                            React.createElement(
                                                "div",
                                                { className: "middle eclipse" },
                                                React.createElement(
                                                    "span",
                                                    null,
                                                    v.purchase,
                                                    " / ",
                                                    v.insure,
                                                    " / ",
                                                    v.pay
                                                )
                                            ),
                                            React.createElement(
                                                "div",
                                                { className: "right" },
                                                React.createElement(
                                                    "span",
                                                    { style: { color: "#000" } },
                                                    v.premium,
                                                    "\u5143"
                                                )
                                            )
                                        ),
                                        React.createElement("div", { style: { height: "10px" } })
                                    ) : v.parent == j ? React.createElement(
                                        "div",
                                        { className: "product product-rider text16 br-tl" },
                                        React.createElement(
                                            "div",
                                            { className: "left" },
                                            React.createElement(
                                                "span",
                                                { style: { color: "#0a0" } },
                                                "\u9644"
                                            )
                                        ),
                                        React.createElement(
                                            "div",
                                            { className: "middle eclipse" },
                                            React.createElement(
                                                "span",
                                                { style: { color: "#000", marginRight: "10px" } },
                                                v.abbrName
                                            ),
                                            React.createElement("span", { style: { color: "#aaa" } })
                                        ),
                                        React.createElement(
                                            "div",
                                            { className: "right" },
                                            React.createElement(
                                                "span",
                                                { style: { color: "#000" } },
                                                v.premium,
                                                "\u5143"
                                            )
                                        )
                                    ) : null;
                                })
                            );
                        })
                    ),
                    React.createElement(
                        "div",
                        { className: "divx mt-1 p-2" },
                        React.createElement("div", { className: "bg-primary", style: { width: "20px", height: "40px" } }),
                        React.createElement(
                            "div",
                            { className: "ml-1 lh-40" },
                            "\u4FDD\u9669\u8D23\u4EFB"
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "ml-1 mr-1 pt-2 pb-2 bg-white", style: { border: "#dddddd 1px solid" } },
                        !this.state.liability ? null : this.state.liability.map(function (v, i) {
                            return !v.detail ? null : React.createElement(
                                "div",
                                { key: i },
                                React.createElement(
                                    "div",
                                    { className: "text17 lh-60 center pl-3 pr-3 ml-3 mr-3 mt-1 mb-2 tc-primary bg-white", style: { border: "#00aff9 1px solid", borderRadius: "30px" } },
                                    v.name
                                ),
                                v.detail.map(function (w, j) {
                                    return React.createElement(
                                        "div",
                                        { className: "div text17 pl-1 pr-1 pb-1", key: j },
                                        React.createElement(
                                            "div",
                                            { className: "lh-60 pl-1 tc-primary" },
                                            "\u2605 ",
                                            w.name
                                        ),
                                        w.detail.map(function (x, k) {
                                            return React.createElement(
                                                "div",
                                                { className: "lh-40", key: k },
                                                React.createElement(
                                                    "div",
                                                    { className: "divx pb-1", onClick: _this4.showLiabDetail.bind(_this4, i, j, k) },
                                                    React.createElement("img", { style: { width: "40px", height: "40px" }, src: x.show ? "../images/arrow-7-up.png" : "../images/arrow-7-down.png" }),
                                                    React.createElement(
                                                        "div",
                                                        { className: "pl-1", style: { width: "650px" } },
                                                        x.text
                                                    )
                                                ),
                                                x.detail.map(function (y, l) {
                                                    return !x.show || !x.detail ? null : React.createElement(
                                                        "div",
                                                        { className: "divx text15 lh-30 tc-dark pb-1", key: l },
                                                        React.createElement("div", { className: "center", style: { width: "40px" } }),
                                                        React.createElement(
                                                            "div",
                                                            { className: "divx" },
                                                            React.createElement(
                                                                "div",
                                                                { className: "title" },
                                                                y.productAbbrName
                                                            ),
                                                            " ",
                                                            React.createElement(
                                                                "div",
                                                                null,
                                                                y.text
                                                            )
                                                        )
                                                    );
                                                })
                                            );
                                        })
                                    );
                                })
                            );
                        })
                    ),
                    React.createElement(
                        "div",
                        { className: "divx mt-1 p-2" },
                        React.createElement("div", { className: "bg-primary", style: { width: "20px", height: "40px" } }),
                        React.createElement(
                            "div",
                            { className: "ml-1 lh-40" },
                            "\u5229\u76CA\u56FE\u8868"
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "ml-1 mr-1 pt-2 pb-2 bg-white", style: { border: "#dddddd 1px solid" } },
                        !this.state.chart ? null : this.state.chart.map(function (v, i) {
                            return !v.content ? null : React.createElement(
                                "div",
                                null,
                                React.createElement(
                                    "div",
                                    { className: "text17 lh-60 center pl-3 pr-3 ml-3 mr-3 mt-1 mb-2 tc-primary bg-white", style: { border: "#00aff9 1px solid", borderRadius: "30px" } },
                                    v.productName
                                ),
                                React.createElement(
                                    "div",
                                    { className: "divx" },
                                    React.createElement(_benefit_chart2.default, { ref: "benefitChart" + i, id: "benefitChart" + i, chart: v, years: [-2, -1, 0, 1, 2] })
                                )
                            );
                        })
                    ),
                    React.createElement(
                        "div",
                        { className: "divx mt-1 p-2" },
                        React.createElement("div", { className: "bg-primary", style: { width: "20px", height: "40px" } }),
                        React.createElement(
                            "div",
                            { className: "ml-1 lh-40" },
                            "\u6E29\u99A8\u63D0\u793A"
                        )
                    ),
                    React.createElement(
                        "div",
                        { className: "ml-1 mr-1 p-1 bg-white tc-dark text15", style: { border: "#dddddd 1px solid" } },
                        "\u3000\u3000\u514D\u9664\u4FDD\u9669\u516C\u53F8\u8D23\u4EFB\u6761\u6B3E\u3001\u72B9\u8C6B\u671F\u3001\u89E3\u9664\u5408\u540C\u7684\u624B\u7EED\u53CA\u98CE\u9669\u3001\u8D39\u7528\u6263\u9664\u7B49\u5185\u5BB9\uFF0C\u8BF7\u60A8\u4ED4\u7EC6\u9605\u8BFB\u4FDD\u9669\u5408\u540C\u3002\u672C\u6F14\u793A\u8BF4\u660E\u4EC5\u4F9B\u53C2\u8003\uFF0C\u5177\u4F53\u4FDD\u9669\u8D23\u4EFB\u3001\u514D\u9664\u4FDD\u9669\u516C\u53F8\u8D23\u4EFB\u7684\u6761\u6B3E\u7B49\u5185\u5BB9\u4EE5\u6B63\u5F0F\u4FDD\u9669\u5408\u540C\u4E3A\u51C6\u3002"
                    ),
                    React.createElement("div", { className: "h-60" })
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
>>>>>>> master
