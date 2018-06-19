class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            list: []
        }
    }
    componentDidMount() {
        APP.apply.queryProduct(null, "fosun", null, r => {
          this.setState({ list: r })
        })
    }
    addToPlan(productId) {
        APP.back(productId)
    }
    close() {
        APP.back()
    }
    render() {
        return (
            <div>
                { this.state.list.map((v, i) =>
                    <div className="list-item" onClick={this.addToPlan.bind(this, v.code)} key={i}>
                        <div className="list-item-icon">
                            <img src={v.icon}></img>
                        </div>
                        <div className="list-item-content">
                            <div className="text18" style={{height:"45px", lineHeight:"45px"}}>{v.name}</div>
                            <div className="text12" style={{height:"35px", lineHeight:"35px", color:"gray"}}>{v.name}</div>
                        </div>
                    </div>
                )}
            </div>
		)
    }
}

$(document).ready( function() {
	ReactDOM.render(<Main/>, document.getElementById("root"))
})