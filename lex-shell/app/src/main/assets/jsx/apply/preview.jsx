class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
        }
    }
    componentDidMount() {
        MF.setTitle("投保单预览")
        APP.apply.view(this.state.orderId, r => {
            this.setState({ order: r })
        })
    }
    submit() {
        MF.navi("apply/pay.html")
    }
    next() {
        MF.navi("apply/pay.html")
    }
    render() {
        return (
            <div>
                <div className="bottom text18 tc-primary">
                    <div style={{paddingLeft:"30px", width:"600px"}}></div>
                    <div style={{textAlign:"right", paddingRight:"30px", width:"150px"}} onClick={this.submit.bind(this)}>提交</div>
                </div>
            </div>
		)
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})