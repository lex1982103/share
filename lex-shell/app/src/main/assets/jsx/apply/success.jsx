class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
        }
    }
    componentDidMount() {
        MF.setTitle("投保结果")
    }
    next() {
        MF.navi("home/home")
    }
    render() {
        return (
            <div>
                <div className="ml-auto mr-auto" style={{textAlign:"center", marginTop:"20%"}}>
                    <img style={{width:"300", height:"300px"}} src="../images/success.png"/>
                </div>
                <div className="text19 ml-auto mr-auto mt-3" style={{textAlign:"center"}}>
                    投保成功<br/>保单号：123456789012345678
                </div>
                <div className="bottom text18 tc-primary">
                    <div className="ml-0 mr-0" style={{width:"690px", textAlign:"right"}} onClick={this.next.bind(this)}>
                        返回首页
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