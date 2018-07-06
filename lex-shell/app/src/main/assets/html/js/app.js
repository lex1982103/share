<<<<<<< HEAD
var Host = {
    req: function(url, param, onSucc, onFail) {
        if (param == null)
            param = {}
        APP.onSucc = null
        var userKey = MF.env(key)
        if (userKey) {
            param.userKey = userKey
            common.req("app" + url, param, onSucc, onFail)
        } else {
            APP.onSucc = function(userKey) {
                param.userKey = userKey
                common.req("app" + url, param, onSucc, onFail)
            }
        }
    }
}

var APP = {
    env(key, onSucc) {
        APP.onSucc = onSucc
        MF.env(key)
    },
    callback(val) {
        if (APP.onSucc)
            APP.onSucc(val)
    },
    pick(type, vals, onSucc) {
        APP.onSucc = onSucc
        MF.pick(type, JSON.stringify(vals))
    },
    pop(uri, height, onSucc) {
        APP.onSucc = onSucc
        MF.pop(uri, height)
    },
    alert(title, text, onYes, onNo) {
        APP.onSucc = function(r) { if (r) { onYes() } else { onNo() }}
        MF.alert(title, text, onNo ? "是" : "确定", onNo ? "否" : null)
    },
    back(val) {
        MF.back(val)
    },
    login(loginName, pwd, onSucc, onFail) {
        Host.req("/user/login.json", { loginName: loginName, password: pwd }, onSucc, onFail)
    },
    dict(code, onSucc) {
        Host.req("/dict/view.json", { company: "nciapp", name: code }, onSucc)
    },
    toMapDict(dict) {
        if (dict instanceof Array) {
            var r = {}
            dict.map(v => {
                r[v.code] = v.text
            })
            return r
        } else {
            return dict
        }
    }
}

if (typeof Customer == "object") {
    Customer.host = Host;
    APP.customer = Customer;
}

if (typeof Proposal == "object") {
    Proposal.host = Host;
    APP.proposal = Proposal;
}

if (typeof Apply == "object") {
    Apply.host = Host;
    APP.apply = Apply;
}

=======
var Host = {
    req: function(url, param, onSucc, onFail) {
        if (param != null) {
            param.userKey = MF.env("userKey")
        } else {
            param = { userKey: MF.env("userKey") }
        }
        common.req("app" + url, param, onSucc, onFail)
    }
}

//var MF = {
//    env: () => {},
//    setTitle: () => {}
//}

var APP = {
    callback(val) {
        if (APP.onSucc)
            APP.onSucc(val)
    },
    pick(type, vals, onSucc) {
        APP.onSucc = onSucc
        MF.pick(type, JSON.stringify(vals))
    },
    pop(uri, height, onSucc) {
        APP.onSucc = onSucc
        MF.pop(uri, height)
    },
    back(val) {
        MF.back(val)
    },
    login(loginName, pwd, onSucc, onFail) {
        Host.req("/user/login.json", { loginName: loginName, password: pwd }, onSucc, onFail)
    },
    dict(code, onSucc) {
        Host.req("/dict/view.json", { company: "nciapp", name: code }, onSucc)
    },
   list(url, postData, onSucc) {
      Host.req(url, postData, onSucc)
    },
    openApply(url, postData, onSucc) {
        Host.req(url, postData, onSucc)
    },
    toMapDict(dict) {
        if (dict instanceof Array) {
            var r = {}
            dict.map(v => {
                r[v.code] = v.text
            })
            return r
        } else {
            return dict
        }
    }
}

if (typeof Customer == "object") {
    Customer.host = Host;
    APP.customer = Customer;
}

if (typeof Proposal == "object") {
    Proposal.host = Host;
    APP.proposal = Proposal;
}

if (typeof Apply == "object") {
    Apply.host = Host;
    APP.apply = Apply;
}

>>>>>>> 37947285f21262d89038e08995bd9660e99a6d6f
