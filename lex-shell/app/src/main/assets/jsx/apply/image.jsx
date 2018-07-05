class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
        }
    }
    componentDidMount() {
        window.MF&&MF.setTitle("影像")
    }
    next() {
        if(window.MF){
            // MF.navi("apply/preview.html?orderId=" + this.state.orderId)
            MF.navi("xinhua_lx/autograph_xh.html?orderId=" + this.state.orderId)
        }else{
            // location.href = "apply/preview.html?orderId=" + this.state.orderId
            location.href = "xinhua_lx/autograph_xh.html?orderId=" + this.state.orderId
        }

    }
    render() {
        return (
            <div>
                <div className="bottom text18 tc-primary">
                    <div className="ml-3 mr-0" style={{width:"300px"}}></div>
                    <div className="divx" onClick={this.next.bind(this)}>
                        <div className="ml-0 mr-0" style={{width:"390px", textAlign:"right"}}>
                            预览
                        </div>
                        <div className="ml-1 mr-2" style={{width:"30px"}}>
                            <img className="mt-3" style={{width:"27px", height:"39px"}} src="../images/blueright.png"/>
                        </div>
                    </div>
                </div>
            </div>
		)
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})