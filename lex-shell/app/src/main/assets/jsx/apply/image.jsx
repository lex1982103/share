class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
        }
    }
    componentDidMount() {
        MF.setTitle("影像")
    }
    next() {
        MF.navi("apply/preview?orderId=" + this.state.orderId)
    }
    render() {
        return (
            <div>
                <div className="bottom text18 tc-primary">
                    <div className="ml-0 mr-0" style={{width:"690px", textAlign:"right"}} onClick={this.next.bind(this)}>
                        预览
                    </div>
                    <div className="ml-1 mr-2" style={{width:"30px"}}>
                        <img className="mt-3" style={{width:"27px", height:"39px"}} src="../images/blueright.png"/>
                    </div>
                </div>
            </div>
		)
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})