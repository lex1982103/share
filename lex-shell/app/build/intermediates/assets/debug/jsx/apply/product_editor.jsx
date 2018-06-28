class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            planId: common.param("planId"),
            index: common.param("index"),
            form: []
        }
    }
    componentDidMount() {
        this.state.form = []
        APP.apply.editProduct(this.state.planId, this.state.index, r => {
            this.state.form.push({
                name: r.name,
                form: this.formOf(r.factors)
            })
            this.setState({ form: this.state.form })
        })
        APP.apply.viewPlan(this.state.planId, plan => {
            APP.apply.listRiders(this.state.planId, this.state.index, r => {
                r.map(v => {
                    let prdForm = {
                        name: v.name,
                        productId: v.code,
                        form: null
                    }
                    plan.product.map((r3, i) => {
                        if (r3.productId == v.code && r3.parent == this.state.index) {
                            APP.apply.editProduct(this.state.planId, i, r4 => {
                                prdForm.form = this.formOf(r4.factors)
                                this.setState({ form: this.state.form })
                            })
                        }
                    })
                    this.state.form.push(prdForm)
                })
                this.setState({ form: this.state.form })
            })
        })
    }
    addRider(prdIndex, prd) {
        let productId = prd.productId
        let riderForm = this.state.form[prdIndex]
        if (riderForm.form == null) {
            APP.apply.addProduct(this.state.planId, this.state.index, productId, r => {
                r.product.map((r2, i) => {
                    if (r2.productId == productId && r2.parent == this.state.index) 
                        APP.apply.editProduct(this.state.planId, i, r1 => {
                            riderForm.form = this.formOf(r1.factors)
                            this.setState({ form: this.state.form })
                        })
                })
            })
        } else {
            APP.apply.deleteProduct(this.state.planId, this.state.index, productId, r => {
                riderForm.form = null
                this.setState({ form: this.state.form })
            })
        }
    }
    close() {
        APP.back()
    }
    formOf(f) {
        return f.map(w => {
            let r = {}
            if (w.detail) w.detail.map(v => {
                r[v[0]] = v[1]
            })
            return {
                widget: w.widget,
                label: w.label,
                detail: r,
                value: w.value
            }
        })
    }
    onValChange(opt, prdIndex, formIndex, val) {
        let vals = {};
        vals[opt.name] = opt.detail[val]
        APP.apply.saveProduct(this.state.planId, prdIndex, vals, r => {
            this.state.form[prdIndex].form[formIndex].value = val
            this.setState({ form: this.state.form })
        })
    }
    render() {
        return (
            <div>
                { this.state.form.map((w, i) =>
                    <div class="div">
                        { i > 0 ? null : <div className="text18" style={{height:"80px", lineHeight:"80px", textAlign:"center", borderBottom:"#ddd solid 1px", backgroundColor:"#fff"}}>{w.name}</div> }
                        { i == 0 ? null : <div className="divx" style={{height:"80px", verticalAlign:"middle", borderBottom:"#ddd solid 1px", backgroundColor:"#fff", marginTop:"10px"}} onClick={this.addRider.bind(this, i, w)}>
                            <img style={{margin:"10px 0 0 20px", width:"60px", height:"60px"}} src={"../images/" + (w.form == null ? 'unchecked' : 'checked') + ".png"}/>
                            <div className="text16" style={{marginLeft:"10px", lineHeight:"80px", color:w.form == null ? "gray" : "black"}}>{w.name}</div>
                        </div> }
                        { w.form == null ? null : w.form.map((v, j) =>
                            <div className="form-item" style={{backgroundColor:"#fff"}}>
                                <div className="form-item-label text17" style={{width:"300px", margin:"10px 0 0 20px"}}>{v.label}</div>
                                <div className="form-item-widget" style={{width:"410px", margin:"10px 20px 0 0", textAlign:"right"}}>
                                    {
                                        v.widget == "number" ? <input type="number" placeholder={"请输入"+v.label}/> :
                                        v.widget == "switch" || v.widget == "select" ?
                                            <div style={{display:"flex"}} onClick={x => { APP.pick("select", v.detail, this.onValChange.bind(this, v, i, j)) }}>
                                                <div className="text17" style={{width:"350px", lineHeight:"60px"}}>{v.detail[v.value]}</div>
                                                <img style={{width:"60px", height:"60px"}} src="../images/arrow-7-down.png"/>
                                            </div>
                                        : null
                                    }
                                </div>
                            </div>
                        )}
                    </div>
                )}
                <div className="btn-fl text18" style={{color:"#fff", backgroundColor:"#1aad19"}} onClick={this.close.bind(this)}>确定</div>
            </div>
        )
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})