var env = {};

var common = {};

common.url = function(url) {
	return "http://www.lerrain.com:7666/" + url;
	var host = location.host;
	var server;
	if (host.startsWith("sv")) {
        host = "api" + host.substr(2);
        server = location.protocol + "//" + host;
    // } else if (host.startsWith("localhost")) {
    //     return "https://api-test.iyb.tm/" + url;
	} else if (host.startsWith("lifeins")) {
        server = location.protocol + "//";
        if (location.pathname.startsWith("/rel/"))
            server += "api.iyb.tm";
        else if (location.pathname.startsWith("/uat/"))
            server += "api-uat.iyb.tm";
    } else if (host.indexOf("lerrain") >= 0) {
        return "http://www.lerrain.com:7666/" + url;
	} else if (host.indexOf("local") >= 0) {
        return "http://localhost:7666/" + url;
	} else if (host.indexOf("dingl") >= 0) {
        return "http://dingl.51vip.biz:60004/" + url;
    }
	return server + "/" + url;
};

common.link = function(link) {
	var server = location.protocol + "//" + location.host;
	if (location.pathname.startsWith("/rel/"))
		server += "/rel";
	else if (location.pathname.startsWith("/uat/"))
		server += "/uat";
	return server + "/" + link;
};

common.postOther = function(url, val, callback, failback) {
    $.ajax({url:url, type:"POST", data:JSON.stringify(val), xhrFields: { withCredentials: true }, contentType:'application/json;charset=UTF-8', success:function(r) {
        if(callback)callback(r);
    }, fail: function(r) {
        if(failback)failback(r.reason);
    }, dataType:"json"});
};

common.post = function(url, val, callback, failback) {
    $.ajax({url:url, type:"POST", data:JSON.stringify(val), xhrFields: { withCredentials: true }, contentType:'application/json;charset=UTF-8', success:function(r) {
        if (r.result == "success") {
            if(callback)callback(r.content);
        } else if (failback == null) {
            alert("失败 - " + r.reason);
        } else {
            if(failback)failback(r.reason);
        }
    }, fail: function(r) {
        alert("访问服务器失败");
    }, dataType:"json"});
};

common.postSync = function(url, val, callback, failback) {
    $.ajax({url:url, type:"POST", data:JSON.stringify(val), async: false, xhrFields: { withCredentials: true }, contentType:'application/json;charset=UTF-8', success:function(r) {
        if (r.result == "success") {
            if(callback)callback(r.content);
        } else if (failback == null) {
            alert("失败 - " + r.reason);
        } else {
            if(failback)failback(r.reason);
        }
    }, fail: function(r) {
        alert("访问服务器失败");
    }, dataType:"json"});
};

common.req = function(url, val, callback, failback) {
	common.post(common.url(url), val, callback, failback);
};
common.reqSync = function(url, val, callback, failback) {
	common.postSync(common.url(url), val, callback, failback);
};

common.param = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

common.copy = function(a, b) {
	if (b != null)
		for (var x in b)
			if (b[x] != null)
				a[x] = b[x];
	return a;
}

String.prototype.startsWith = function (prefix) {
	return this.slice(0, prefix.length) === prefix;
};

String.prototype.endsWith = function (str) {
	return this.slice(this.length - str.length) === str;
};

Date.prototype.format = function(format){
	 //eg:format="yyyy-MM-dd hh:mm:ss";
	 var o = {
	 "M+" : this.getMonth()+1, //month
	 "d+" : this.getDate(),   //day
	 "h+" : this.getHours(),  //hour
	   "m+" : this.getMinutes(), //minute
	   "s+" : this.getSeconds(), //second
	   "q+" : Math.floor((this.getMonth()+3)/3), //quarter
	   "S" : this.getMilliseconds() //millisecond
	  }
	  
	  if(/(y+)/.test(format)) {
	  format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	  }
	  
	  for(var k in o) {
	  if(new RegExp("("+ k +")").test(format)) {
	   format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
	  }
	  }
	 return format;
	}

common.getFormData = function(formId) {
	var data = {};
	$("#" + formId).find("input,select").each(function() {
		var name = $(this).attr("name");
		if (name) {
			var type = $(this).attr("type");
			var str = $(this).val();
			if (type == "checkbox") {
				if (!$(this).is(':checked'))
					str = null;
			} else if (type == "radio") {
				if (!$(this).is(':checked'))
					str = null;
			}
			if (str != null)
				data[name] = data[name] == null ? str : data[name] + "," + str;
		}
	});
	return data;
}

common.setFormData = function(formId, vals) {
	$("#" + formId).find("input,select").each(function() {
		var name = $(this).attr("name");
		if (name) {
			var type = $(this).attr("type");
			var val = vals[name];
			if (type == "checkbox") {
				var str = $(this).val();
				if ((","+val+",").indexOf(","+str+",")>=0)
					$(this).attr("checked",true);
			} else if (type == "radio") {
				var str = $(this).val();
				if (str == val)
					$(this).attr("checked",true);
			} else if (val) {
				$(this).val(val);
			}
		}
	});
}

common.ifNull = function(v1, v2) {
	return v1 == null ? v2 : v1;
}

common.save = function(k, v) {
	window.sessionStorage.setItem(k, v);
	window.sessionStorage.setItem(k + "/time", new Date().getTime());
}

common.load = function(k, overtime) {
	if (overtime == null)
		return window.sessionStorage.getItem(k);
	var t = window.sessionStorage.getItem(k + "/time");
	if (t != null && new Date().getTime() - t < overtime)
		return window.sessionStorage.getItem(k);
	return null;
}


common.age = function(strBirthdayArr) {
	var returnAge;

	var birthYear = Number(strBirthdayArr.substr(0,4));
	var birthMonth = Number(strBirthdayArr.substr(5,2));
	var birthDay = Number(strBirthdayArr.substr(8,2));

	var d = new Date();
	var nowYear = d.getFullYear();
	var nowMonth = d.getMonth() + 1;
	var nowDay = d.getDate();

	if(nowYear == birthYear) {
		returnAge = 0;//同年 则为0岁
	}  else {
		var ageDiff = nowYear - birthYear;
		if(ageDiff > 0) {
			if(nowMonth == birthMonth) {
				var dayDiff = nowDay - birthDay;
				if(dayDiff < 0)
					returnAge = ageDiff - 1;
				else
					returnAge = ageDiff ;
			} else {
				var monthDiff = nowMonth - birthMonth;
				if(monthDiff < 0)
					returnAge = ageDiff - 1;
				else
					returnAge = ageDiff ;
			}
		} else {
			returnAge = -1;
		}
	}

	return returnAge;
}

common.formatJson = function (json, options) {
	var reg = null,
		formatted = '',
		pad = 0,
		PADDING = '    ';
	options = options || {};
	options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
	options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;
	if (typeof json !== 'string') {
		json = JSON.stringify(json);
	} else {
		json = JSON.parse(json);
		json = JSON.stringify(json);
	}
	reg = /([\{\}])/g;
	json = json.replace(reg, '\r\n$1\r\n');
	reg = /([\[\]])/g;
	json = json.replace(reg, '\r\n$1\r\n');
	reg = /(\,)/g;
	json = json.replace(reg, '$1\r\n');
	reg = /(\r\n\r\n)/g;
	json = json.replace(reg, '\r\n');
	reg = /\r\n\,/g;
	json = json.replace(reg, ',');
	if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
		reg = /\:\r\n\{/g;
		json = json.replace(reg, ':{');
		reg = /\:\r\n\[/g;
		json = json.replace(reg, ':[');
	}
	if (options.spaceAfterColon) {
		reg = /\:/g;
		json = json.replace(reg, ':');
	}
	(json.split('\r\n')).forEach(function (node, index) {
			var i = 0,
				indent = 0,
				padding = '';

			if (node.match(/\{$/) || node.match(/\[$/)) {
				indent = 1;
			} else if (node.match(/\}/) || node.match(/\]/)) {
				if (pad !== 0) {
					pad -= 1;
				}
			} else {
				indent = 0;
			}

			for (i = 0; i < pad; i++) {
				padding += PADDING;
			}

			formatted += padding + node + '\r\n';
			pad += indent;
		}
	);
	return formatted;
};

common.initForm = function(url, params, method){
	method = method == null ? "get" : method;

	var f = document.createElement("form");
	f.action = url;
	f.method = method;
	f.id = "_js_form"+Math.random();
	document.body.appendChild(f);

	if(params != null){
		for(var k in params){
			var h = document.createElement("input");
			h.type = "hidden";
			h.name = k;
			h.value = params[k];
			f.appendChild(h);
		}
	}

	return f;
};

common.isWeixin = function() {
    var ua = window.navigator.userAgent.toLowerCase();
    return ua.match(/MicroMessenger/i) == 'micromessenger';
};

common.isAPP = function() {
    var UA = window.navigator.userAgent.toLowerCase();
    return !!~UA.indexOf('iyunbao') || (typeof iHealthBridge !== 'undefined');
};

common.dateStr = function(t) {
	if (t == null)
		return null;
    return new Date(t).format("yyyy-MM-dd");
};

common.round = function(v, s) {
	return v == null || v === "" ? null : Number(v).toFixed(s);
};

common.timeStr = function(t) {
    if (t == null)
        return null;
    return new Date(t).format("yyyy-MM-dd hh:mm:ss");
};