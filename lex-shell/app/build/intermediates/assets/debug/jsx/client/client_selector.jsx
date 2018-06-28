class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            pop: common.param("pop"),
            from: 0,
            total: 0,
            list: []
        }
    }
    componentDidMount() {
        MF.setTitle("我的客户")
        APP.customer.query({from:this.state.from, number:20}, r => {
            this.setState({ list: r.list, total: r.total })
        })
    }
    select(customerId) {
        if (this.state.pop) {
            APP.back(customerId)
        } else {
            MF.navi("customer/customer.html?customerId=" + customerId)
        }
    }
    close() {
        APP.back()
    }
    render() {
        return (
            <div>
                { this.state.list.map((v, i) =>
                    <div className="list-item" onClick={this.select.bind(this, v.id)} key={i}>
                        <div className="list-item-icon">
                            <img src="../images/customer.png"></img>
                        </div>
                        <div className="list-item-content">
                            <div className="text18" style={{height:"45px", lineHeight:"45px"}}>{ v.name }</div>
                            <div className="text12" style={{height:"35px", lineHeight:"35px", color:"gray"}}>{ (v.gender == "M" ? "男" : v.gender == "F" ? "女" : "") + " " + (v.birthday ? v.birthday : "") }</div>
                        </div>
                    </div>
                )}
            </div>
		)
    }
}

$(document).ready(() => {
	ReactDOM.render(<Main/>, document.getElementById("root"))
})