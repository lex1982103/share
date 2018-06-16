var Host = {
    req: function(url, param, onSucc) {
        if (param != null)
            param["userKey"] = "1"
        common.req("app" + url, param, onSucc)
    }
}

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
    dict(code, onSucc) {
        Host.req("/dict/view.json", { company: "nci", name: code }, onSucc)
    }
}

if (typeof Proposal == "object") {
    Proposal.host = Host;
    APP.proposal = Proposal;
}

if (typeof Apply == "object") {
    Apply.host = Host;
    APP.apply = Apply;
}

