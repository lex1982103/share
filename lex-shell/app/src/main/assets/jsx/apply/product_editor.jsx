var Main = React.createClass({
    getInitialState() {
        return {
            planId: common.param("planId"),
            index: common.param("index")
        }
    },
    componentDidMount() {
        APP.apply.editProduct(this.state.planId, this.state.index, r => {
            this.setState({ cust: cust ? cust : [{}] })
        })
    },
    save() {
    },
    close() {
        MF.back()
    },
    onValChange(key, val) {
        this.state.cust[this.state.index][key] = val
        this.setState({ cust: this.state.cust })
    },
    render() {
        return (
            <div>
                { this.state.form.map((w, i) =>
                    <div class="div">
                        { i > 0 ? null : <div className="text18" style={{height:"80rpx", lineHeight:"80rpx", textAlign:"center", borderBottom:"#ddd solid 1px", backgroundColor:"#fff"}}>{w.name}</div> }
                        { i == 0 ? null : <div className="divx" style={{height:"80rpx", verticalAlign:"middle", borderBottom:"#ddd solid 1px", backgroundColor:"#fff", marginTop:"10rpx"}} onClick={this.addRider.bind(this, i, w)}>
                            <img style={{margin:"10rpx 0 0 20rpx", width:"60rpx", height:"60rpx"}} src={"./images/" + (w.form == null ? 'unchecked' : 'checked') + ".png"}/>
                            <div className="text16" style={{marginLeft:"10rpx", lineHeight:"80rpx", color:w.form == null ? "gray" : "black"}}>{w.name}</div>
                        </div>
                        { w.form.map((v, i) =>
                            <div className="form-line" style={{backgroundColor:"#fff"}}>
                                <div className="text17" style={{width:"300rpx", margin:"10rpx 0 0 20rpx"}}>{v.text}</div>
                                <div style={{width:"410rpx", margin:"10rpx 20rpx 0 0", textAlign:"right"}}>
                                    {
                                        v.widget == "number" ? <input type="number" placeholder={"请输入"+v.text}/> :
                                        v.widget == "switch" || v.widget == "select" ?
                                            <div style={{display:"flex"}} onClick={v => {APP.pick("select", v.options, this.onValChange.bind(this, i, j))}}>
                                                <div className="text17" style={{width:"350rpx", lineHeight:"60rpx"}}>{v.options[v.index]}</div>
                                                <div style={{width:"60rpx", height:"60rpx"}} src="./images/arrow-7-down.png"/>
                                            </div>
                                        : null
                                    }
                                </div>
                            </div>
                        }
                    </div>
                }
                <div className="btn-fl text18" style={{color:"#fff", backgroundColor:"#1aad19"}} onClick={this.close}>确定</div>
            </div>
        )
    }
}

$(document).ready( function() {
	ReactDOM.render(
		<Main/>, document.getElementById("root")
	);
});