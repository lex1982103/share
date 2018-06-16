class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
        }
    }
    componentDidMount() {
        MF.setTitle("投保成功")
    }
    next() {
        MF.navi("home/home.html")
    }
    render() {
        return (
            <div>
                <div className="bottom text18 tc-primary">
                    <div style={{paddingLeft:"30px", width:"600px"}}></div>
                    <div style={{textAlign:"right", paddingRight:"30px", width:"150px"}} onClick={this.next.bind(this)}>返回</div>
                </div>
            </div>
		)
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})