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
        MF.navi("home/home.html")
    }
    render() {
        return (
            <div>
                <div className="ml-auto mr-auto" style={{textAlign:"center", marginTop:"20%"}}>
                    <img style={{width:"250", height:"250px"}} src="../images/success.png"/>
                </div>
                <div className="text19 ml-auto mr-auto mt-3" style={{textAlign:"center"}}>
                    投保成功<br/>保单号：123456789012345678
                </div>
                <div className="bottom text18 tc-primary">
                    <div className="ml-3 mr-auto">
                    </div>
                    <div className="mr-3" onClick={this.next.bind(this)}>
                        返回首页
                    </div>
                </div>
            </div>
		)
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})